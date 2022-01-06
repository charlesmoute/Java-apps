package model.compression;

import model.base.*;
import java.util.Map;

/**
 * @author Charles Moute
 * <p>
 *  Implementation de Lempel-Ziv-Welch. Il consiste a ne coder que l'indice n dans le dictionnaire;
 *  de plus le fichier est lu bit par bit. 
 * </p>
 */

public class LZW {

	Dictionary dictionary = null;
	Dictionary result = null ;
	
	/**
	 * Cree une instance de LZW avec comme dictionnnaire initiale la table Ascii decimal.	 
	 * @throws Exception Erreur genere lorsque le code ne s'execute pas correctement.
	 */
	public LZW() throws Exception {
		setDefaultDictionary();		
	}
	
	/**
	 * Cree une instance de LZW avec comme dictionnnaire initiale le parametre.
	 * @param dictionary Dictionnaire initiale.
	 * @throws Exception Erreur genere lorsque le le code ne s'execute pas correctement.
	 */
	public LZW(Dictionary dictionary) throws Exception {		
		setDictionary(dictionary);		
	}
	
	
	/**
	 * Affecte le parametre comme nouveau dictionnaire. 
	 * @param dictionary Nouveau dictionaire
	 * @throws Exception Erreur genere lorsque le dictionnaire est vide. 
	 */
	public void setDictionary(Dictionary dictionary) throws Exception {
		if(dictionary==null || dictionary.size()==0) throw new Exception("Le dictionaire n'est pas valide");
		this.dictionary = dictionary;
		this.result = null;
	}
	
	/**
	 * Affecte la table Ascii decimal comme le dictionnaire par defaut .
	 * @throws Exception Erreur genere lorsque le dictionnaire est vide. 
	 */
	public void setDefaultDictionary() throws Exception {
		
		Dictionary dico = new Dictionary();		
		for(int i=helper.Constant.MIN_ASCII;i<=helper.Constant.MAX_ASCII;i++){
			int count = (""+helper.Constant.MAX_ASCII).length() - (""+i).length() ;
			String code = (""+i);
			for(int j=1;j<=count;j++) code="0"+code;
			if(!dico.addWord(code,""+((char)i))) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		}
		
		this.dictionary = dico;
		this.result = null;
	}
	
	/**
	 * @return Le dictionnaire  original utilisee par l'instance courante.
	 */
	public Dictionary getDictionary() { return this.dictionary; }
	
	/**
	 * @return Le resultat de la derniere operation, zip/unzip executee.
	 * @see #zip(String)
	 * @see #unzip(java.util.ArrayList)
	 */
	public Dictionary getResultDictionary() { return this.result; }
	
	
	private String getNewCode() throws Exception{
		String newCode ;
		boolean containsLetter = false ;
		
		for(Map.Entry<String,String> entry: dictionary.wordSet()){
			String key = entry.getKey();
			for(int i=0;i<key.length();i++){
				if(key.charAt(i)<0||key.charAt(i)>'9'){
					containsLetter = true ;
					break;
				}
			}
		}
		
		String firstKey = result.firstKey();
		String lastKey = result.lastKey() ;
		
		int count = firstKey.length() ;
		
		if(!containsLetter){
			do{
				newCode = ""+(Integer.parseInt(lastKey)+1);
				if(newCode.length()>firstKey.length()) throw new Exception("L'algo ne peut generer un nouveau code fiable");
				int add = count - newCode.length() ;
				for(int i=1;i<=add;i++) newCode = "0"+newCode;
				lastKey = ""+(Integer.parseInt(lastKey)+1);
			}while(result.containsCode(newCode));
		}else{
			
			newCode = "" ;
			for(int i=(count-1);i>=0;i--){					
				if(i==0 && lastKey.charAt(i)=='F') throw new Exception("L'algo ne peut generer un nouveau code fiable");
				if(lastKey.charAt(i)=='9'){
					 String aux = (i==0)? "":lastKey.substring(0, i);
					 newCode = aux+'A'+newCode;
					 break;
				}else if(lastKey.charAt(i)=='F'){						 
					 newCode = '0'+newCode;						 
				}else{
					String aux = (i==0)? "":lastKey.substring(0, i);
					char val = ((char)(lastKey.charAt(i)+1));						
					newCode = aux+val+newCode ; 
					break;
				}
			}
		}
		
		return newCode;		
	}
	
	
	/**
	 * Compresse le parametre et retourne son compresse.
	 * @param message Message a compresser
	 * @return Compresse du parametre, null si il n'a pas pu compresse
	 * @throws Exception Erreur lance lorsque le code ne s'execute pas correctement.
	 */
	public String zip(String message) throws Exception {
		String answer = null;
		java.util.ArrayList<String> values = zip2(message);
		if(values!=null){
			answer="";
			for(int i=0;i<values.size();i++) answer+=values.get(i);			
		}
		return answer;
	}
	
	/**
	 * Compresse le parametre et retourne son compresse.
	 * @param message Message a compresser
	 * @return Compresse du parametre, null si il n'a pas pu compresse
	 * @throws Exception Erreur lance lorsque le code ne s'execute pas correctement.
	 */
	public java.util.ArrayList<String> zip2(String message) throws Exception {
		java.util.ArrayList<String> value = null;
		result = null;
		if(message!=null && !message.isEmpty()){
			value=new java.util.ArrayList<String>();
			String c,aux = "";			
			result = dictionary.clone();
			
			for(int i=0;i<message.length();i++){
				c = ""+message.charAt(i);
				if(result.containsValue(aux+c)) aux = aux+c;
				else{
					if(result.getCodeOf(aux)==null) 
						throw new Exception("Tous les caracteres du message ne sont pas definies dans le dictionnaire");
					value.add(result.getCodeOf(aux));
					result.addWord(getNewCode(), aux+c);
					aux = c ;
				}
			}
			if(aux.compareTo("")!=0) value.add(result.getCodeOf(aux));
		}
		return value;
	}
	
	/**
	 * Decompresse le parametre
	 * @param message Message a decompresser
	 * @return Decompresser du message ou null si le message n'a pu etre decompresse.
	 * @throws Exception Erreur lance lorsque le code ne s'execute pas correctement.
	 */
	public String unzip(String message) throws Exception {
		if(message!=null && !message.isEmpty()){
			
			int count = dictionary.firstKey().length();
			if((message.length()%count)!=0) throw new Exception("Le message ne peut etre decompresse avec le dictionnaire courant");
			
			java.util.ArrayList<String> aux = new java.util.ArrayList<String>();
			int begin = 0;
			
			while(begin<message.length()){
				aux.add(new String(message.substring(begin,begin+count)));
				begin+=count;
			}
			
			return unzip2(aux);			
		}
		return null ;
	}
	
	/**
	 * Decompresse le parametre
	 * @param message Message a decompresser
	 * @return Decompresser du message ou null si le message n'a pu etre decompresse.
	 * @throws Exception Erreur lance lorsque le code ne s'execute pas correctement.
	 */
	public String unzip2(java.util.ArrayList<String> message) throws Exception {
		String value = null;
		result = null;
		if(message!=null && !message.isEmpty()){
			String prec = null;
			String aux = null,c=null;
			result = dictionary.clone();
			value = "";			
			for(int i=0;i<message.size();i++){
				c = message.get(i);				
				if(result.containsCode(c)){
					aux = result.getValueOf(c);
				}else{
					c = (aux!=null)? (""+aux.charAt(0)):"";
					aux = (prec==null)? "":result.getValueOf(prec);
					aux+=c;
				}
				value+=aux;
				String word = ((prec==null)? "":result.getValueOf(prec))+aux.charAt(0);
				result.addWord(getNewCode(), word);
				prec = c ;
			}
		}
		return value;
	}
	
	
	public String toString(){ return dictionary.toString() ;}
	
	public static void main(String[] arg) throws Exception {
		
		Dictionary dico = new Dictionary();
		
		if(!dico.addWord("00","NUL")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("01","SOH")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("02","STX")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("03","ETX")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("04","EOT")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("05","ENQ")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("06","ACK")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("07","BEL")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("08","BS")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("09","HT")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("0A","LF")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("0B","VT")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("0C","FF")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("0D","CR")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("0E","SO")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("0F","SI")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		
		if(!dico.addWord("10","DLE")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("11","DC1")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("12","DC2")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("13","DC3")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("14","DC4")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("15","NAK")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("16","SYN")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("17","ETB")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("18","CAN")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("19","EM")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("1A","SUB")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("1B","ESC")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("1C","FS")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("1D","GS")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("1E","RS")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("1F","US")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		
		if(!dico.addWord("20","SP")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("21","!")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("22","\"")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("23","#")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("24","$")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("25","%")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("26","&")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("27","'")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("28","(")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("29",")")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("2A","*")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("2B","+")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("2C",",")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("2D","-")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("2E",".")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("2F","/")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		
		if(!dico.addWord("30","0")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("31","1")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("32","2")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("33","3")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("34","4")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("35","5")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("36","6")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("37","7")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("38","8")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("39","9")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("3A","~:")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("3B","~;")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("3C","<")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("3D","=")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("3E",">")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("3F","?")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		
		if(!dico.addWord("40","@")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("41","A")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("42","B")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("43","C")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("44","D")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("45","E")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("46","F")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("47","G")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("48","H")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("49","I")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("4A","J")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("4B","K")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("4C","L")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("4D","M")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("4E","N")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("4F","O")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		
		if(!dico.addWord("50","P")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("51","Q")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("52","R")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("53","S")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("54","T")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("55","U")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("56","V")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("57","W")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("58","X")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("59","Y")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("5A","Z")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("5B","[")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("5C","\\")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("5D","]")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("5E","^")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("5F","_")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		
		if(!dico.addWord("60","`")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("61","a")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("62","b")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("63","c")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");		
		if(!dico.addWord("64","d")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");		
		if(!dico.addWord("65","e")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("66","f")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("67","g")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("68","h")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("69","i")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("6A","j")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("6B","k")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("6C","l")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");		
		if(!dico.addWord("6D","m")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("6E","n")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("6F","o")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		
		if(!dico.addWord("70","p")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("71","q")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("72","r")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("73","s")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("74","t")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("75","u")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("76","v")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("77","w")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("78","x")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("79","y")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("7A","z")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("7B","{")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("7C","|")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("7D","}")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("7E","~")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		if(!dico.addWord("7F","DEL")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		//if(!dico.addWord("9F","Test")) throw new Exception("Erreur lors de l'ajout d'un mot dans le dico");
		
		LZW lzw = new LZW(dico);
		
		
		String message ="BLEBLBLBA" ;
		java.util.ArrayList<String> codes = lzw.zip2(message);
		System.out.println("Codes = "+codes);
		
		String compressed = lzw.zip(message);
		System.out.println("Compressed = "+compressed);
		
		/*Dictionary d = lzw.getResultDictionary();
		System.out.println(d);
		System.out.println("Message compresse : ");
		for(String code:codes){
			System.out.println(d.getValueOf(code)+" ==> "+code);
		}*/
		
		String values = lzw.unzip2(codes);
		System.out.println("Message decompresse : "+values);
		
		String uncompressed = lzw.unzip(compressed);
		System.out.println("uncompresse = "+uncompressed);
		
	}
}
