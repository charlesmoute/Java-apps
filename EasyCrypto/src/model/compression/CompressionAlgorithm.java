package model.compression;

import helper.Util;

/**
 * @author Charles
 * <p>CompressionAlgorithm regroupe certains algorithmes de compression</p>
 */

public class CompressionAlgorithm {
	
	/**
	 * @param text Texte a compresser.
	 * @return Text compresse selon le principe d'un fax.
	 */
	public static final String zipFax(String text){
		String compressed=null;
		if(Util.isFaxText(text)){
			compressed = "";
			char c = text.charAt(0);
			int count = 1 ;
			for(int i=1;i<text.length();i++){
				if(text.charAt(i)==c) count++;
				else{
					compressed+=count+""+c;
					c = text.charAt(i);
					count = 1;
				}
			}
			compressed+=count+""+c;
		}
		return compressed;
	}
	
	
	/**
	 * @param text Texte a decompresser.
	 * @return Text decompresse selon le principe d'un fax.
	 */
	public static final String unzipFax(String text){
		String str = null ;
		if(text!=null){
			str="";
			int count = 0;
			for(int i=0;i<text.length();i++){
				int j;
				for(j=i;j<text.length();j++){
					if(!(text.charAt(j)>=48 && text.charAt(j)<=57)) break;
				}
				count = Integer.parseInt(text.substring(i, j));
				for(int k=1;k<=count;k++) str+=""+text.charAt(j);
				i=j;
			}
		}
		return str;
	}
	
	
	/**
	 * @param text Texte a compresser.
	 * @param code Code spécifiant une redondance.
	 * @return Text compresse suivant l'algorithme de compression RLE.
	 */
	public static final String zipRLE (String text, char code){
		String compressed = null;
		if(text!=null){
			compressed = "";
			int i,count =1;
			for(i=0;i<(text.length()-1);i++){
				if(text.charAt(i)==code){
					compressed+=code+""+code;
					count = 1;
				}else{
					if((text.charAt(i)==text.charAt(i+1))){
						count++;
						if(count>9){compressed+=code+count+text.charAt(i); count=1;}
					}else{
						if(count>2)	compressed+=code+""+count ;
						else if(count==2) compressed+=""+text.charAt(i);
						compressed+=""+text.charAt(i);
						count = 1;
					}
				}
			}
			if(count>2) compressed+=code+""+count;
			else if(text.charAt(i)==code) compressed+=""+code;			
			compressed+=""+text.charAt(i);
		}
		return compressed;
	}
	
	/**
	 * @param text Text compresse.
	 * @param code code specifiant une redondance.
	 * @return Text decompresse selon l'algorithme RLE(run length encoding).
	 */
	public static final String unzipRLE(String text,char code){
		String result = null ;
		if(text!=null){
			result = "";
			int i;
			for(i=0;i<(text.length()-1);i++){
				if(text.charAt(i)==code){
					
					if(text.charAt(i+1)==code){
						result+=code;
						i++;
					}else{
						int count = text.charAt(i+1) - 48;
						for(int j=1;j<=count;j++) result+=text.charAt(i+2);
						i+=2;
					}
					
				}else result+=""+text.charAt(i);
			}			
			if(i==(text.length()-1))result+=text.charAt(i);
		}
		return result;
	}
	
	public static void main(String[] arg){
		
		String text = "NNBNNNBBBBN";
		System.out.println( text+" est fax =  "+Util.isFaxText(text));
		System.out.println("compresse de "+text+" = "+ CompressionAlgorithm.zipFax(text));
		System.out.println("Decompresse de "+CompressionAlgorithm.zipFax(text)+" = "+CompressionAlgorithm.unzipFax(CompressionAlgorithm.zipFax(text)));
		
		text = "3C1111478B00000A5322@1";
		System.out.println("compresse RLE de "+text+" = "+ CompressionAlgorithm.zipRLE(text,'@'));
		System.out.println("Decompresse RLE de "+CompressionAlgorithm.zipRLE(text,'@')+" = "+CompressionAlgorithm.unzipRLE(CompressionAlgorithm.zipRLE(text,'@'),'@') );
		System.out.println("Decompresse RLE de @41 = "+CompressionAlgorithm.unzipRLE("@41",'@') );
		
	}
}
