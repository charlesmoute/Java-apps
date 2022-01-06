package model.ciphering;

import java.math.*;

import helper.RSAUtil;
import model.base.Key;

/**
 * @author Charles
 * <p>Classe implementant l'algorithme de chiffrement Rivest Shamir Adleman (RSA) </p>
 */
public class RSA {
	
	/**
	 * Cle publique ou cle de chiffrement
	 */
	private Key Ke ;
	
	/**
	 * Cle privee ou cle de dechiffrement
	 */
	private Key Kd ;
	
	/**
	 * Construit une instance de RSA avec les deux parametres comme cle publique et privee. 
	 * @param Ke Cle publique de l'instance
	 * @param Kd Cle privee de l'instance
	 * @throws Exception Erreur lancee lorsque l'execution du code ne s'est pas deroule correctement.
	 * @see {@link #setKeys(Key, Key)}
	 */
	public RSA(Key Ke, Key Kd) throws Exception {		
		setKeys(Ke,Kd);		
	}
	
	/**
	 * Cosntruit une instance de RSA avec (e,n) comme cle publique et (d,n) comme cle privee.
	 * @param e Exposant de chiffrement.
	 * @param n Modulo
	 * @param d Exposant de dechiffrement
	 * @throws Exception Erreur lancee lorsque l'execution du code ne s'est pas deroule correctement.
	 * @see {@link #setKeys(Key, Key)}
	 */
	public RSA(BigInteger e, BigInteger n , BigInteger d) throws Exception{
		setKeys(new Key(e,n),new Key(d,n));
	}
	
	/**
	 * <p>Affecte les deux parametres comme nouvelles cles utilisees par l'instance courante</p>
	 * @param Ke Nouvelle cle publique
	 * @param Kd Nouvelle cle privee
	 * @throws Exception Erreur lancee lorsque l'execution du code ne s'est pas deroule correctement.
	 */
	public void setKeys(Key Ke, Key Kd) throws Exception {
		if(Ke==null || Kd==null) throw new Exception("L'une ou l'autre des deux cles est/sont incorrecte(s)");		  
		if(Ke.getModulo()!=Kd.getModulo()) throw new Exception("Les deux cles n'ont pas le meme modulo");
		BigInteger phi = RSAUtil.phi(Ke.getModulo()) ;
		if(Ke.getExponent().compareTo(BigInteger.ONE)<=0 || Ke.getExponent().compareTo(phi)>=0) throw new Exception("L'exposant de la cle publique est incorrect");
		if(Kd.getExponent().compareTo(BigInteger.ONE)<=0 || Kd.getExponent().compareTo(phi)>=0) throw new Exception("L'exposant de la cle privee est incorrect");
		this.Ke = Ke ;
		this.Kd = Kd ;
	}
	
	/**
	 * <p>Affecte (e,n) comme nouvelle cle publique et (d,n) comme nouvelle cle privee.</p>
	 * @param e Exposant de chiffrement.
	 * @param n Modulo
	 * @param d Exposant de dechiffrement.
	 * @throws Exception Erreur lancee lorsque l'execution du code ne s'est pas deroule correctement.
	 * @see {@link #setKeys(Key, Key)}
	 */
	public void setKeys(BigInteger e, BigInteger n, BigInteger d) throws Exception {
		setKeys(new Key(e,n),new Key(d,n));
	}
	
	/**
	 * @return La cle publique utilisee par l'instance de RSA.
	 */
	public Key getPublicKey() { return this.Ke; }
	
	/**
	 * @return La cle private utilisee par l'instance de RSA
	 */
	public Key getPrivateKey() { return this.Kd; }
		
	/**
	 * Chiffre le message et retourne son chiffre.
	 * @param message Message a chiffrer.
	 * @return Chiffre du parametre
	 * @throws Exception Erreur lancee lorsque l'execution du code ne s'est pas deroule correctement.
	 */
	public BigInteger encrypt(BigInteger message) throws Exception{
		//if(message.compareTo(BigInteger.ZERO)<0 || message.compareTo(Ke.getModulo().subtract(BigInteger.ONE))>0) throw new Exception("Le message ne peut etre chiffre, car "+message+" n'est pas dans l'intervalle ferme [0,"+(Ke.getModulo().subtract(BigInteger.ONE))+"]");
		if(message.compareTo(BigInteger.ZERO)<0) throw new Exception("Le message ne peut etre chiffre, car il est negatif ");
		if(message.compareTo(Ke.getModulo())<0)
			return RSAUtil.modularPower(message, Ke.getExponent(), Ke.getModulo()) ;
		else{
			int incr = Ke.getModulo().toString().length() -1 ;
			if(incr<=0) throw new Exception("Le message ne peut etre decoupe de telle sorte qu'il soit dans [0,"+Ke.getModulo().subtract(BigInteger.ONE)+"]");
			String param = message.toString();
			if((param.length()%incr)!=0){
				int count = incr - (param.length()%incr) ;
				for(int i=1;i<=count;i++) param = "0"+param ;
			}
			int begin = 0 ;
			String result = "";
			//System.out.print("algo crypted : ");
			while(begin<param.length()){
				int end = ( (begin+incr)<param.length())? (begin+incr):param.length();
				BigInteger aux = new BigInteger(param.substring(begin, end));
				String value = (RSAUtil.modularPower(aux, Ke.getExponent(), Ke.getModulo())).toString();
				if(value.length()>(incr+1)) throw new Exception("Erreur Conception Interne");
				int size = (incr+1) - value.length();
				for(int i=1;i<=size;i++) value = "0"+value;
				//System.out.print("("+aux+","+value+") ");
				result+=value;
				begin+=incr;								
			}
			//System.out.println();
			return (new BigInteger(result));
		}
	}
	
	public String toString(){
		return ("[ Public Key = "+Ke+","+" Private Key = "+Kd+" ]");
	}
	
	/**
	 * Dechiffre le message et retourne le message en clair.
	 * @param message Message a chiffrer.
	 * @return Dechiffre du parametre
	 * @throws Exception Erreur lancee lorsque l'execution du code ne s'est pas deroule correctement.
	 */
	public BigInteger decrypt(BigInteger message) throws Exception {
		//if(message.compareTo(BigInteger.ZERO)<0 || message.compareTo(Kd.getModulo().subtract(BigInteger.ONE))>0) throw new Exception("Le message ne peut etre dechiffre, car "+message+" n'est pas dans l'intervalle ferme [0,"+(Kd.getModulo().subtract(BigInteger.ONE))+"]");
		if(message.compareTo(BigInteger.ZERO)<0) throw new Exception("Le message ne peut etre chiffre, car il est negatif ");
		if(message.compareTo(Kd.getModulo())<0)
			return RSAUtil.modularPower(message, Kd.getExponent(), Kd.getModulo()) ;
		else{
			int incr = Ke.getModulo().toString().length() ;
			if(incr<=0) throw new Exception("Le message ne peut etre decoupe de telle sorte qu'il soit dans [0,"+Ke.getModulo().subtract(BigInteger.ONE)+"]");
			String param = message.toString();
			if( (param.length()%incr)!=0){
				int count = incr - (param.length()%incr) ;
				for(int i=1;i<=count;i++) param = "0"+param;
			}
			int begin = 0 ;
			String result = "";
			//System.out.print("algo decrypted : ");
			while(begin<param.length()){
				BigInteger aux = new BigInteger(param.substring(begin, begin+incr));
				//BigInteger value = RSAUtil.modularPower(aux, Kd.getExponent(), Kd.getModulo()) ;
				String value = (RSAUtil.modularPower(aux, Kd.getExponent(), Kd.getModulo())).toString() ;
				if(value.length()<(incr-1)){
					int count = incr-1 - value.length() ;
					for(int i=1;i<=count;i++) value = "0"+value;
				}
				//System.out.print("("+aux+","+value+") ");
				result += value; 
				begin+=incr;
			}
			//System.out.println();			
			return (new BigInteger(result));
		}
	}
	
	public static void main(String[] arg) throws Exception {
		
		
		String message = "<document> <titre>Test</titre> <content>Le reste n'a rien avoir et je suis épuisé</content> </document>";
		System.out.println("Message a chiffrer = "+message);
		
		BigInteger number = RSAUtil.stringToNumber(message);		
		System.out.println("number = "+number);
		
		BigInteger[] primes = RSAUtil.generateTwoPrimes(5);
		System.out.println("p = "+primes[0]+"; q="+primes[1]);
		
		model.base.Key[] keys = RSAUtil.buildKeysOf(primes[0], primes[1]);
		model.ciphering.RSA rsa = new model.ciphering.RSA(keys[0],keys[1]);
		System.out.println(rsa);
		
		BigInteger crypted =  rsa.encrypt(number);
		System.out.println("crypted = "+crypted);
		
		String answer = RSAUtil.numberToString(crypted);
		System.out.println("Valeur ascii crypted = "+answer);
		
		BigInteger decrypted = rsa.decrypt(crypted);
		System.out.println("decrypted = "+decrypted);
		
		//System.out.println("(decrypted == number) = "+(decrypted.compareTo(number)==0));
		
		String ans = RSAUtil.numberToString(decrypted);
		System.out.println("Valeur Ascii decrypted = "+ans);
	}
}
