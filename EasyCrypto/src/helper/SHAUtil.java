package helper;

/**
 * @author Charles Moute
 *	<p>Classe regroupant l'ensemble des fonctions utilitaires de SHA( Secure Hash Algorithm 1)</p>
 */
public class SHAUtil {

	/**
	 * Converti un tableau de byte en une chaine de cractere.
	 * @param  Tableau de bytes a convertir
	 * @return Chaine de caractere representant le parametre
	 */
	public static final String bytesToString(byte[] param){	return new String(param); }
	
	/**
	 * Convertit un entier en une suite de bytes
	 * @param param Entier a convertir
	 * @return Suite de bytes correspondant au parametre.
	 */
	public static final byte[] intToBytes(int param){
		
		byte[] answer = new byte[4];
		answer[0] = ( (byte) (param>>>24));
		answer[1] = ( (byte) (param>>>16));
		answer[2] = ( (byte) (param>>>8));
		answer[3] = ( (byte) (param));
		return answer;
	}
	
	/**
	 * Converti un tableau de 4 bytes en un entier
	 * @param param Tableau de 4 bytes a convertir
	 * @return Un entier correspondant a 4 bytes.
	 **/
	public static final int bytesToInt(byte[] param){
		//if(param==null || param.length!=4) throw new Exception("Ne peut convertir la suite de bytes en un entier");
		return (param[0] & 0xFF)<<24 | (param[1] & 0xFF)<<16 | (param[2] & 0xFF)<<8 |(param[3] & 0xFF) ;
		
	}
	
	/**
	 * Compte le nombre de bit utilise pour representer param.
	 * @param param Parametre dont le nombre de bit doit etre evalue.
	 * @return Le nombre de bit du parametre
	 */
	public static final int getBitCount(int param) {
		int size = 0 ;
		 int input = ((int) Math.abs(param));
		 for (; input > 0 ; input /= 2 ) size++ ;		   
		return size ;		
	}
	
	public static void main(String[] arg) throws Exception {
		byte[] hash = new byte[4];
		int E = 0x10325476;
		hash[0] = (byte)E;
	    hash[1] = (byte)(E >> 8);
	    hash[2] = (byte)(E >> 16);
	    hash[3] = (byte)(E >> 24);
	    
	    int val = (hash[0]<<24)|(hash[1]<<16)|(hash[2]<<8)|(hash[3]) ; 	    
	    
	    byte[] aux = SHAUtil.intToBytes(E);
	    
	    for(int i=0;i<4;i++) System.out.println("hash["+i+"] ="+hash[i]+" ; aux["+i+"] = "+aux[i]);
	    System.out.println("val = "+val+"; ans = "+SHAUtil.bytesToInt(aux)+" E = "+E);
	}
}
