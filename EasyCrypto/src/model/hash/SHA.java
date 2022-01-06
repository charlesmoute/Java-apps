package model.hash;

/**
 * @author Charles
 * <p>Classe gerant le hachage selon Secure Hash Algorithm 1 (SHA1)</p>
 */

import helper.SHAUtil;
import helper.Constant;

public class SHA {

	/**
	 * Registres A,B,c,D,E
	 */
	private static int A,B,C,D,E;
	
	/**
	 * Initialise les registres A,B,C,D et E avec des constantes 
	 */
	private static void initializeRegiters(){
		A = 0x67452301 ;
		B = 0xEFCDAB89 ;
		C = 0x98BADCFE ;
		D = 0x10325476 ;
		E = 0xC3D2E1F0 ;
	}
	
	/**
	 * Complete le parametre de tel sorte que sa longueur soit un multiple de 512bits.
	 * Un byte tient sur 8bits soit un octet. Or notre taille donne le nombre de bytes
	 * donc le nombre d'octet. Ainsi donc la longueur un multiple de 512bits revient a dire
	 * qu'il est un multiple de 64 octects
	 * @param param
	 * @return Un tableau donc la longueur est un multiple de 64 octets
	 */
	private static byte[] adaptParam(byte[] param){
		byte[] answer = new byte[param.length + (Constant.SIXTYFOUR_BIT- (param.length%Constant.SIXTYFOUR_BIT)) ];
		/*copie de param*/
		for(int i=0;i<param.length;i++) answer[i] = param[i];
		/*ajoute d'un bit a 1 puis un certian nombre de zero. pour ca (0x80)en vase 16 = (128) en base 10 = (10000000) en base 2*/
		answer[param.length] = (byte)0x80;
		/*
		 * length est un entier devant tenir sur 64bits. Un long tient sur 64bits. et la taille de param represente le nombre de
		 * byte contenu dans param. Or un byte tient sur 8bits donc sur un octect. donc la taille de param est en fait le nombre
		 * d'octet contenu dans param, soit (8*param.length) bits contenu dans param. Donc length representera la longueur du parametre
		 * en nombre de bits utilise pour la representation  en binaire de la taille de param soit param.length.
		 */
		long length = param.length *8;
		//ajout de cette entier sur les 8 derniers bytes, un byte tient sur 8bits donc l'entier tiendra sur 64bits
		answer[param.length-1] = (byte) (length) ;
		answer[param.length-2] = (byte) (length>>8) ;
		answer[param.length-3] = (byte) (length>>16) ;
		answer[param.length-4] = (byte) (length>>24) ;
		answer[param.length-5] = (byte) (length>>32) ;
		answer[param.length-6] = (byte) (length>>40) ;
		answer[param.length-7] = (byte) (length>>48) ;
		answer[param.length-8] = (byte) (length>>46) ;
		return answer;
	}
	
	/**
	 * Calcule l'empreinte du parametre.
	 * @param param Parametre dont l'empreinte sera calculee.
	 * @return L'empreinte du parametre tenant sur 160 bits soit 20 bytes.
	 */
	public static byte[] hash(byte[] param){
		byte[] answer = null;
		if(param!=null){
			byte[] aux = SHA.adaptParam(param);
			SHA.initializeRegiters();
			
			//Block de 512 bits soit de 64 bytes
			byte[] block = new byte[64];
			int AA,BB,CC,DD,EE ;
			
			for(int i=0;i<aux.length;){
				//On initialise le block de 64
				block[i%64] = aux[i++];
				if( (i%64)==0){
					//on traite le block de 64 que l'on vient de finir d'intialiser					
					AA = A ; BB = B ; CC = C ;	DD = D ; EE = E ;
					SHA.applyCompression(block);
					A+=AA; B+=BB; C+=CC; D+=DD; E+=EE; 
				}
			}
			
			/*
			 * Resultat devant tenir sur 160bits, soit 20bytes et etant la concatenation de A,B,C,D et E.
			 * Chacun des registres : A, B,C, D et E, tient sur 32bits soit 4 bytes.
			 */
			answer = new byte[20];
			int index = 0 ;
			byte[] hash = SHAUtil.intToBytes(A);			
			for(int i=0;i<hash.length;i++) answer[index++] = hash[i];
			
			hash = SHAUtil.intToBytes(B);			
			for(int i=0;i<hash.length;i++) answer[index++] = hash[i];
			
			hash = SHAUtil.intToBytes(C);			
			for(int i=0;i<hash.length;i++) answer[index++] = hash[i];
			
			hash = SHAUtil.intToBytes(D);			
			for(int i=0;i<hash.length;i++) answer[index++] = hash[i];
			
			hash = SHAUtil.intToBytes(E);			
			for(int i=0;i<hash.length;i++) answer[index++] = hash[i];			
		}
		return answer;
	}
	
	/**
	 * Calcule l'empreinte du parametre.
	 * @param param Parametre dont l'empreinte sera calculee.
	 * @return L'empreinte du parametre tenat sur 160 bits.
	 * @see #hash(byte[])
	 */
	public static String hash(String param){
		if(param==null) return null;
		return SHAUtil.bytesToString(SHA.hash(param.getBytes()));
	}

	/**
	 * Applique la fonction de compression sur un block de 512 bits, soit de 64 bytes. 
	 * @param block Block de taille de 64
	 * @return Un tableau de 5 variables d'etats
	 */
	private static void applyCompression(byte[] block){
		int[] subset = SHA.split(block) ;
		int[] words = SHA.expansion(subset);
		for(int i=0;i<80;i++){
			int temp = words[i]+ SHA.shift(A, 5) + SHA.f(i, B, C, D) + E + SHA.k(i);
			E = D ;
			D = C ;
			C = SHA.shift(B, 30);
			B = A ;
			A = temp;			
		}
	}
	
	/**
	 * Scinde un block de 512 bits soit de 64 bytes, car un byte tient sur 8bits, en 16 mots de 32bits chacun 
	 * soit de 16 entiers car un entier tient su 32 bits soit 4 bytes.
	 * @param block Block a scinder
	 * @return Tableau de 16 mots de 32bits chacun.
	 */
	private static int[] split(byte[] block){
		int[] answer = new int[16];
		int begin = 0, incr = 4,index=0;
		while(begin<block.length){
			byte[] aux = new byte[incr];
			for(int i=0;i<incr;i++) aux[i] = block[begin+i];
			answer[index] = SHAUtil.bytesToInt(aux);
			begin+=incr;
			index++;
		}
		return answer;
	}
	
	/**
	 * Realise une expansion de 16 mots de 32bits en 80 mots de 32bits
	 * @param word Ensemble de 16 mots.
	 * @return Ensemble de 80 mots.
	 */
	private static int[] expansion(int[] word){
		int[] answer = new int[80];
		for(int i=0;i<16;i++) answer[i] = word[i];
		for(int i=16;i<80;i++) answer[i] = SHA.shift(answer[i-3]^answer[i-8]^answer[i-14]^answer[i-16], 1);
		return answer;
	}
	
	
	/**
	 * Realise un decalage circulaire, de k bits a gauche, sur param tenant sur 32bits   
	 * @param param Parametre sur lequel la rotation circulaire doit s'operer.
	 * @param k Nombre de bits a decaler
	 * @return Resultat de la rotation de k bits de param.
	 */
	private static int shift(int param,int k){
		return  (param <<k )|( param >>> (Constant.THIRTYTWO_BIT-k) );
	}
	
	private static int f(int index,int x,int y,int z){
		if(index<20) return (x&y)| ((~x)&z);
		if(index<40) return x^y^z ;
		if(index<60) return (x&y)|(x&z)|(y&z) ;
		return x^y^z ;
	}
	
	private static int k(int index){
		if(index<20) return 0x5A827999;
		if(index<40) return 0x6ED9EBA1;
		if(index<60) return 0x8F1BBCDC;
		return 0xCA62C1D6 ;
	}
	
	public static void main(String[] arg){
		String sTest= "Test de conneries";
	    System.out.println(SHAUtil.bytesToString(sTest.getBytes()));
	    System.out.println(SHAUtil.bytesToString(SHA.hash(sTest.getBytes())));
	}
}
