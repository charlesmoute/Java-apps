package model.compression;

import helper.Constant;

import java.util.*;
import model.base.*;

/**
 * @author Charles
 * <p>Implementation de l'algorithme a dictionnaire LZ78, qui succede a LZ77 plus complique</p>
 */
public class LZ78 {
		
	
	/**
	 * Compresse le texte en parametre
	 * @param text Texte a compresser
	 * @return Liste des sorties
	 */
	public static ArrayList<Output> zip(String text){
		ArrayList<Output> out = null;
		if(text!=null && text.length()>0){
			out = new ArrayList<Output>();
			ArrayList<String> dictionary = new ArrayList<String>();
			dictionary.add(null);
			String param = text ;
			while(param!=null){
				int index = LZ78.longestFactorOf(dictionary, param);
				int count = 0 ;
				if(index==-1){
					count = 1 ;
					char c = param.charAt(count-1);
					out.add(new Output(0,c));
					dictionary.add(""+c);					
				}else{
					count = dictionary.get(index).length()+1;
					char c = param.charAt(count-1);
					out.add(new Output(index,c));
					dictionary.add(param.substring(0,count));										
				}	
				param = (param.length()>count)? param.substring(count):null;
			}
			//System.out.println("dictionary = "+dictionary);
		}
		return out;
	}
	
	/**
	 * Decompresse le parametre et retourne le texte avant compression.
	 * @param param Ensemble de paires (position,caractere)
	 * @return Texte decompresse.
	 * @throws Exception Erreur lance lorsque le code ne s'execute pas correctement.
	 */
	public static String unzip(ArrayList<Output> param) throws Exception{
		String text = null;
		if(param!=null){
			ArrayList<String> dictionary = LZ78.buildDictionary(param);
			text="";
			for(int i=0;i<dictionary.size();i++){
				String value = dictionary.get(i);
				text+=(value==null)? "":value;
			}
		}
		return text;
	}
	
	
	/**
	 * Construit un dictionnaire a partir fu parametre.
	 * @param param Ensemble de paires (position,caractere)
	 * @return Un dictionnaire construit a partir de param
	 * @throws Exception Erreur lance lorsque le code ne s'execute pas correctement.
	 */	
	public static ArrayList<String> buildDictionary(ArrayList<Output> param) throws Exception {
		ArrayList<String> dictionary = null;
		if(param!=null && param.size()>0){
			dictionary = new ArrayList<String>();
			dictionary.add(null);
			for(int i=0;i<param.size();i++) {
				Output out = param.get(i) ;
				if(out.getPosition()>=(i+1)) throw new Exception("Erreur lors de la construction du dictionnaire");
				String value = ( (dictionary.get(out.getPosition())==null)? "":dictionary.get(out.getPosition()))+ out.getCharacter() ;
				dictionary.add(value);
			}
		}
		return dictionary;
	}
	
	/**
	 * Recherche le plus grand facteur du deuxieme parametre, dans le premier parametre.
	 * @param dictionary Ensemble de facteurs
	 * @param text Texte dont on cherche le plus grand facteur.
	 * @return L'index de ce plus grand facteur dans dictionnary, -1 si pas trouve.
	 */
	public static int longestFactorOf(ArrayList<String> dictionary,String text){
		if(dictionary==null || text==null) return -1;
		int index = -1 ;
		for(int i=0;i<dictionary.size();i++){
			
			int pos = (dictionary.get(i)!=null)? text.indexOf(dictionary.get(i)):-1;
			if(pos==0){
				if(index==-1) index = i;
				else{
					if(dictionary.get(i).length()>=dictionary.get(index).length())
						index = i ;
				}
			}
		}
		return index;
	}
	
	public static void main(String[] arg) throws Exception {
		
		/*String text = "abb";
		ArrayList<String> dictionary = new ArrayList<String>();
		dictionary.add(null);
		dictionary.add("a");
		dictionary.add("ab");
		dictionary.add("b");
		dictionary.add("aba");
		dictionary.add("ba");
		dictionary.add("bb");
		dictionary.add("bba");
		dictionary.add("bbb");
		dictionary.add("abb");
		
		int index = LZ78.longestFactorOf(dictionary, text);
		
		System.out.println(index);*/
		
		/*ArrayList<Output> out = new ArrayList<Output>();
		out.add(new Output(0,'a'));
		out.add(new Output(1,'b'));
		out.add(new Output(0,'b'));
		out.add(new Output(2,'a'));
		out.add(new Output(3,'a'));
		out.add(new Output(3,'b'));
		out.add(new Output(6,'a'));
		out.add(new Output(6,'b'));
		out.add(new Output(2,'b'));
		
		ArrayList<String> dictionary = LZ78.buildDictionary(out);
		for(String val:dictionary) System.out.println(val);*/
		
		
		String text="reseaux application multimedia";
		
		System.out.println("Text initial = "+text);
		ArrayList<Output> out = LZ78.zip(text);		
		System.out.println("Sortie = "+out);
		
		ArrayList<String> dictionary = LZ78.buildDictionary(out);
		System.out.print("Dictionnaire =  [");
		int i=0;
		for(String value:dictionary){
			String aux = (value!=null)? value.replaceAll(" ", Constant.SPACE):value;			
			if(i< (dictionary.size()-1))aux+=", ";
			System.out.print(aux);
			i++;
		}
		System.out.println("]");
		//System.out.println("Dictionnaire = "+dictionary);
		
		String txt = LZ78.unzip(out);
		System.out.println("Text = "+txt);
	}
}
