

import java.math.*;
import java.util.*;

/**
 * @author Charles Mouté
 * <p>
 *   Cette classe contient la plupart des fonctions utilisees dans RSA
 *   de Rivest Shamir Adleman.
 * </p>
 */
public class RSAUtil {
	
	/**
	 * <p>Calcule la puissance modulaire de  a^e mod n</p>
	 * @param a Parametre 1
	 * @param e parametre 2
	 * @param n parametre 3
	 * @return (a puissance e) modulo n
	 */
	
	public static final BigInteger modularPower(BigInteger a, BigInteger e, BigInteger n) throws Exception {
		if(a==null || e==null || n==null) throw new Exception("Un des parametres n'a pas ete initialise");
		return a.modPow(e, n);
	}
	
	/*
	public static final long modularPower(long a, long e, long n){
		  long result = 1;
	       while (e > 0) {
	           if ((e & 1) == 1)  result = (result * a) % n;
	           e = e >> 1;
	           a = (a * a) % n;
	       }
	       return result;
	}
	 */
	
	/**
	 * Calcule a puissance b
	 * @param a Nombre a elever a la puissance
	 * @param b Exposant de la puissance
	 * @return a puissance b
	 * @throws Exception Erreur generee lorsque le code ne s'est pas deroule correctement.
	 */
	public static final BigInteger pow(BigInteger a, int b) throws Exception {
		if(a==null) throw new Exception("Le parametre a eleve a la puissance n'a pas ete initialise");
		return (a.pow(b));
	}
	/*public static final long pow(long a, long b) throws Exception {
	    if (b==0) return 1;
	    if (b==1) return a;
	    if ( (b & 0x01 ) == 0){
	        long c = RSAUtil.pow(a, b >> 1);
	        return c * c;
	    }else{
	        return a * RSAUtil.pow(a, b - 1);
	    }
	}*/
	
	/**
	 * <p>Dit si le parametre est un nombre premier ou pas</p>
	 * @param p Nombre a tester la primalite.
	 * @return True si le parametre est premier, false dans le cas contraire.
	 */
	public  static final boolean isPrime(BigInteger p){
		if(p==null) return false ;
		return p.isProbablePrime(5);
	}
	
	
	/*public  static final boolean isPrime(long p){
		
		if ( p < 2 ) return false ;
	    if ((p == 2) || (p == 3)) return true ;
	    if ((p % 2) == 0) return false ;
	    if ((p % 3) == 0) return false ;
	    
		boolean isPrimary = true ;
		long value = Math.abs(p);
		long count = (long)Math.sqrt(value);
		for(long i=2;i<count;i++){
			if( (value%i)==0 ){
				isPrimary = false;
				break;
			}
		}		
		return isPrimary;
	}*/
	
	/**
	 * Calcule le nombre de bits qui constitue le parametre
	 * @param param Parametre dont le nombre de bits doit etre retourne
	 * @return -1 si le parametre est incorrecte sinon le nombre de  bits constituant le parametre.
	 */	
	public static final int getSizeOf(BigInteger param) {
		if(param==null) return -1;
		return param.bitLength();
	}
	
	/*public static final int getSizeOf(long param){
		 int size = 0 ;
		 long input = ((long) Math.abs(param));
		 for (; input > 0 ; input /= 2 ) size++ ;		   
		return size ;
	}*/
	
	/**
	 * Renvoie un nombre aleatoire dans [2^size, (2^(size+1)-1)]
	 * @param size Nombre de bit du nombre a generer
	 * @return Un nombre de size bits.
	 */
	public static final BigInteger random(int size){
		BigInteger output = BigInteger.ONE ; 
	    for ( --size ; size > 0 ; --size )
	    	output = (output.multiply(BigInteger.valueOf(2))).add(( BigInteger.valueOf((long)(Math.round(Math.random())))));	    
		return output ;
	}
	/*
	public static final long random(int size){
		long output = 1 ; 
	    for ( --size ; size > 0 ; --size )
	    	output = output*2 + ((long)(Math.round(Math.random())));	    
		return output ;
	}*/
	
	
	/**
	 * Genere un nombre aleatoire compris dans l'intervalle fermee [min,max]
	 * @param min Minimum de l'intervalle
	 * @param max Maximum de l'intervalle
	 * @return Un nombre compris entre min et max
	 * @throws Exception Erreur generee lorsque le code ne s'est pas deroule correctement.
	 */
	public static final BigInteger random(BigInteger min, BigInteger max) throws Exception {
		if(min==null || max==null) throw new Exception("L'une des valeurs, min ou max est incorrecte");
		if(min.compareTo(max) >=0 ) throw new Exception("L'intervalle de generation est incorrect car le minimun = "+min+">= maximun = "+max);
		BigDecimal  alea = new BigDecimal(Math.random());
		BigDecimal _max = new BigDecimal(max), _min = new BigDecimal(min);
		return  ((alea.multiply(_max)).add(_min)).toBigInteger();
	}
	
	/*public static final long random(long min,long max) throws Exception {
		if(min>=max) throw new Exception("L'intervalle de generation est incorrect car le minimun = "+min+">= maximun = "+max);
		return ( (long)(Math.random()*max+min) );
	}*/
	
	/**
	 * Dit si le parametre est impair ou pas.
	 * @param p Parametre dont la parite sera testee.
	 * @return true si le parametre est de parite impaire, false dans le cas contraire.
	 */
	public static final boolean isOdd(BigInteger p){
		if(p==null) return false;		
		return (p.and(BigInteger.ONE).compareTo(BigInteger.ONE)==0) ;		
	}
	/*public static final boolean isOdd(long p){
		return  ( (p&1)==1 );
	}*/
	
	/**
	 * Genere un nombre premier de size bits.
	 * @param size Taille du nombre premiers a generer.
	 * @return un nombre premier dans [2^size, (2^(size+1)-1)] , si size >=0 bits
	 * @throws Exception Erreur due a un mauvaise execution du code.
	 */
	public static final BigInteger findPrime(int size) throws Exception {
		BigInteger count = (size<=0)? BigInteger.ONE: BigInteger.valueOf(2).pow(size) ;			
		BigInteger value  = RSAUtil.random(size);
		
		if(!RSAUtil.isOdd(value)) value = value.add(BigInteger.ONE);
		while(!RSAUtil.isPrime(value)&&(count.compareTo(BigInteger.ZERO)>0)){
			value = value.add(BigInteger.valueOf(2));
			count = count.subtract(BigInteger.ONE);			
		}
		String info = "["+(BigInteger.valueOf(2).pow(size))+", "+(BigInteger.valueOf(2).pow(size+1).subtract(BigInteger.ONE))+"]";
		if(!RSAUtil.isPrime(value)) throw new Exception("Il n'existe aucun nombres premiers dans "+info);
		return value;
	}
	/*public static final long findPrime(int size) throws Exception{
		long count = (size<=0)? 1:Math.round(Math.pow(2, size)) ;
		long value  = RSAUtil.random(size);
		if(!RSAUtil.isOdd(value)) value++;
		while(!RSAUtil.isPrime(BigInteger.valueOf(value))&&(count>0)){
			value+=2;
			count--;
		}
		String info = "["+((long) Math.pow(2, size))+", "+((long) Math.pow(2, size+1)-1)+"]";
		if(!RSAUtil.isPrime(BigInteger.valueOf(value))) throw new Exception("Il n'existe aucun nombres premiers dans "+info);
		return value;
	}*/
	
	/**
	 * Genere deux nombres premiers de size bits.
	 * @param size Nombre de bits des nombres a generer.
	 * @return Une paire de deux nombres premiers distincts
	 * @throws Exception Erreur due a un mauvaise execution du code.
	 */
	public static final BigInteger[] generateTwoPrimes(int size) throws Exception {
		if(size<3) throw new Exception("Impossible de trouver deux nombres premiers de taille "+size);
		BigInteger p = RSAUtil.findPrime(size);
		BigInteger q ;
		do{ q = RSAUtil.findPrime(size); } while(p.equals(q));
		BigInteger[] result = new BigInteger[2];
		result[0] = p ;
		result[1] = q ;
		return result;
	}
	/*public static final long[] generateTwoPrimes(int size) throws Exception {
		if(size<3) throw new Exception("Impossible de trouver deux nombres premiers de taille "+size);
		long p = RSAUtil.findPrime(size);
		long q ;
		do{ q = RSAUtil.findPrime(size); } while(p==q);
		long[] result = new long[2];
		result[0] = p ;
		result[1] = q ;
		return result;
	}*/
	
	/**
	 * Generer une cle publique et une cle privee a partir des deux nombres premiers en parametre.
	 * @param p Premier nombre premier
	 * @param q Deuxieme nombre premier
	 * @return Un couple cle publique et cle privee.
	 * @throws Exception Erreur due a un mauvaise execution du code.
	 */
	public static final  Key[] buildKeysOf(BigInteger p,BigInteger q) throws Exception {
		if(!RSAUtil.isPrime(p)) throw new Exception("Le premier parametre n'est pas un nombre premier");
		if(!RSAUtil.isPrime(q)) throw new Exception("Le deuxieme parametre n'est pas un nombre premier");
		Key[] keys = new Key[2];
		BigInteger n = p.multiply(q) ;
		BigInteger phi =  (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		BigInteger e =  RSAUtil.random(BigInteger.ONE, phi.subtract(BigInteger.ONE));
		BigInteger[] r = RSAUtil.extendedEuclide(e, phi);
		while (r[0].compareTo(BigInteger.ONE)!=0||(r[1].compareTo(BigInteger.ONE)<=0 || r[1].compareTo(phi)>=0) || e.equals(r[1])){			
			e = RSAUtil.random(BigInteger.ONE, phi.subtract(BigInteger.ONE));			
			r = RSAUtil.extendedEuclide(e, phi);
		}
		BigInteger d = r[1]; 
		keys[0] = new Key(e,n);
		keys[1] = new Key(d,n);
		return keys;
	}
	/*public static final  Key[] buildKeysOf(long p,long q) throws Exception {
		if(!RSAUtil.isPrime(BigInteger.valueOf(p))) throw new Exception("Le premier parametre n'est pas un nombre premier");
		if(!RSAUtil.isPrime(BigInteger.valueOf(q))) throw new Exception("Le deuxieme parametre n'est pas un nombre premier");
		//if(Math.abs(RSAUtil.getSizeOf(p)-RSAUtil.getSizeOf(q))>1) throw new Exception("Les deux parametres n'ont pas la meme taille");
		Key[] keys = new Key[2];
		long n = p*q ;
		long phi = (p-1)*(q-1);
		long e =  RSAUtil.random(1, phi-1); //(long)(Math.random()*(phi-1)+1);
		long[] r = RSAUtil.extendedEuclide(e, phi);
		while (r[0]!=1 ||(r[1]<=1 || r[1]>=phi) || e==r[1] ){
			e = RSAUtil.random(1, phi-1);//(long)(Math.random()*(phi-1)+1);
			r = RSAUtil.extendedEuclide(e, phi);
		}
		long d = r[1]; 
		//if(d<=1 || d>=phi) throw new Exception("Erreur lors de la generation de la cle privee.");
		keys[0] = new Key(BigInteger.valueOf(e),BigInteger.valueOf(n));
		keys[1] = new Key(BigInteger.valueOf(d),BigInteger.valueOf(n));
		return keys;
	}*/
	
	/**
	 * <p>
	 * 	Retourne un triplet (d,x,y) tel que ax+by = d, ou d est le pgcd de a et b et 
	 * 	x et y sont les nombres de Bezout.
	 * </p>
	 * @param a Premier parametre
	 * @param b Deuxieme parametre
	 * @return un triplet (d,x,y)
	 */
	public static final BigInteger[] extendedEuclide(BigInteger a, BigInteger b){
		
		BigInteger[] result = null;
		
		if(a!=null && b!=null){			
			BigInteger u0 = BigInteger.ONE , v0 = BigInteger.ZERO ;
			BigInteger u = BigInteger.ZERO , v = BigInteger.ONE;
			BigInteger e0 = a ,pgcd = b ;
			BigInteger q, r,aux ;
			 
			q = e0.divide(pgcd) ;
			r = e0.remainder(pgcd);
			 
			while(r.compareTo(BigInteger.ZERO)>0){
				aux = pgcd ;
				pgcd =  e0.subtract(q.multiply(pgcd));
				e0 = aux ;
				
				aux = u ;
				u = u0.subtract(q.multiply(u));
				u0 = aux ;
				
				aux = v ;
				v = v0.subtract(q.multiply(v));
				v0 = aux ;
				
				q = e0.divide(pgcd);
				r = e0.remainder(pgcd);			
			}
			
			result = new BigInteger[3];
			result[0] = pgcd ;
			result[1] = u;
			result[2] = v ;
		}
		return result;
	}
	 /*public static final long[] extendedEuclide(long a, long b){
		
		 long u0 = 1 , v0 = 0 ;
		 long u = 0 , v = 1;
		 long e0 = a ,pgcd = b ;
		 long q, r,aux ;
		 
		 q = e0 / pgcd ;
		 r = e0%pgcd;
		 
		 while(r>0){
			aux = pgcd ;
			pgcd = e0 - q*pgcd ;
			e0 = aux ;
			
			aux = u ;
			u = u0 -q*u ;
			u0 = aux ;
			
			aux = v ;
			v = v0 -q*v ;
			v0 = aux ;
			
			q = e0/pgcd ;
			r = e0%pgcd ;			
		 }				
		long[] result = new long[3];
		result[0] = pgcd ;
		result[1] = u;
		result[2] = v ;
		return result;
	}*/
	
	 /**
	  * <p>
	  *  Calcule l'inverse de a modulo b. En realite la valeur retourne correspond a la valeur absolue de l'element 
	  *  se trouvant a la position 1 du resultat de extendedEuclide(a,b)
	  *  </p>
	  * @param a Premier parametre
	  * @param b Deuxieme parametre
	  * @return L'inverse de a modulo b
	  * @see #extendedEuclide(long, long)
	  */
	public static final BigInteger inverse (BigInteger a , BigInteger b ) {		 
		 BigInteger val = null;
		 if(a!=null && b!=null){
			 val = a.modInverse(b);
			 /*BigInteger[] res = RSAUtil.extendedEuclide(a, b);
			 val = res[1];
			 while( val.compareTo(BigInteger.ONE)<0) val = val.add(b);*/
		 }
		 return val ;
	 }
	 /*public static final long inverse (long a , long b ) {		 
		 long[] res = RSAUtil.extendedEuclide(a, b);
		 long val = res[1]; 
		 while ( val < 1 ) val += b ;
		 return val ;
	 }*/

	 /**
	  * Decompose un nombre strictement positif en produit de facteurs premiers.
	  * @param n Nombre a decomposer
	  * @return La decomposition sous forme de liste de Nombre de la forme (nombre premier,exposant) Ex: (2,4) = 2^4 est la decomposition de 16
	  * @throws Exception Erreur generer lorsque le code ne se deroule pas correctement.
	  */
	public static final ArrayList<Number> decompose(BigInteger n) throws Exception {
		 
		if(n==null || n.compareTo(BigInteger.ZERO)<=0) throw new Exception("La decomposition de nombre <=0 n'est pas possible");
		 ArrayList<Number> res = new ArrayList<Number> (); 
		 if(n.compareTo(BigInteger.ONE)==0){
			 Number number = new Number(BigInteger.valueOf(2),0);
			 res.add(number);
			 return res;
		 }
		 BigInteger param=n,prime = BigInteger.ONE;
		 int e=0 ;
		 while(true){			 
		 	
			 while(!RSAUtil.isPrime(prime)) prime= prime.add(BigInteger.ONE);
			 e=0 ;
			 if(param.compareTo(BigInteger.ONE)==0) break;
			 BigInteger q = param.divide(prime);
			 BigInteger r = param.remainder(prime);
			 while(r.compareTo(BigInteger.ZERO)==0){
				 e++;
				 param = q ;
				 q = param.divide(prime);
				 r = param.remainder(prime);
			 }
			 if(e>0){
				 Number number = new Number(prime,e);
				 res.add(number);
			 }			 
			 prime = prime.add(BigInteger.ONE);
		 }
		 return res;
	 }
	 /*public static final ArrayList<Number> decompose(long n) throws Exception {
		 if(n<=0) throw new Exception("La decomposition de nombre <=0 n'est pas possible");
		 ArrayList<Number> res = new ArrayList<Number> (); 
		 if(n==1){
			 Number number = new Number(BigInteger.valueOf(2),0);
			 res.add(number);
			 return res;
		 }
		 long param=n,prime = 1;
		 int e=0 ;
		 while(true){			 
		 	
			 while(!RSAUtil.isPrime(BigInteger.valueOf(prime))) prime++;
			 e=0 ;
			 if(param==1) break;
			 long q = param/prime;
			 long r = param%prime;
			 while(r==0){
				 e++;
				 param = q ;
				 q = param/prime;
				 r = param%prime;
			 }
			 if(e>0){
				 Number number = new Number(BigInteger.valueOf(prime),e);
				 res.add(number);
			 }			 
			 prime++;
		 }
		 return res;
	 }*/
	 
	 /**
	  * <p>Calcule l'indicatrice d'Euler</p>
	  * @param n Modulo
	  * @return L'indicatrice du parametre 
	  * @throws Exception Erreur generer lorsque le code ne se deroule pas correctement.
	  */
	 public static final BigInteger phi(BigInteger n) throws Exception{
		 ArrayList<Number> factors = RSAUtil.decompose(n);
		 BigInteger phi = BigInteger.ONE ;
		 for(int i=0;i<factors.size();i++){
			 Number number = factors.get(i);
			 phi= phi.multiply( (number.getPrime().subtract(BigInteger.ONE)).multiply(RSAUtil.pow(number.getPrime(), number.getExponent()-1)) );
		 }
		 return phi ;
	 }
	/* public static final long phi(long n) throws Exception{
		 ArrayList<Number> factors = RSAUtil.decompose(n);
		 long phi = 1 ;
		 for(int i=0;i<factors.size();i++){
			 Number number = factors.get(i);
			 phi*= (number.getPrime()-1)*RSAUtil.pow(BigInteger.valueOf(number.getPrime()), number.getExponent()-1);
		 }
		 return phi ;
	 }*/
	 
	 /**
	  * Trouve l'autre cle a partir de la cle, generalement cle publique, passe en parametre
	  * @param key Cle publique
	  * @return Cle secrete
	  * @throws Exception Erreur generer lorsque le code ne se deroule pas correctement.
	  */
	 public static final Key findOtherKey(Key key) throws Exception {
		 if(key==null) throw new Exception("La cle est incorrecte");
		 BigInteger phi = RSAUtil.phi(key.getModulo());
		 if(key.getExponent().compareTo(BigInteger.ONE)<=0 || key.getExponent().compareTo(phi)>=0) throw new Exception("L'exposant n'est pas dans l'intervalle ]1,phi("+key.getModulo()+")[");		
		return (new Key(RSAUtil.inverse(key.getExponent(), phi),key.getModulo())) ;			
	 }
	 /*public static final Key findOtherKey(Key key) throws Exception {
		if(key==null) throw new Exception("La cle est incorrecte");
		long phi = RSAUtil.phi(key.getModulo());
		if(key.getExponent()<=1 || key.getExponent()>=phi) throw new Exception("L'exposant n'est pas dans l'intervalle ]1,phi("+key.getModulo()+")[");		
		return (new Key(RSAUtil.inverse(key.getExponent(), phi),key.getModulo())) ;
		
	 }*/
	 
	 /**
	  * Converti le parametre en chaine de caractere binaire
	  * @param number Nombre a convertir.
	  * @return Chaine de caractere representant la conversion en nombre binaire du parametre
	  * @throws Exception Erreur generer lorsque le code ne se deroule pas correctement.
	  */
	 public static String bigIntegerToBinaryString(BigInteger number) throws Exception {
		 if(number==null || number.compareTo(BigInteger.ZERO)<0) throw new Exception("Le nombre ne peut etre converti en binaire");
		 String result = null;
		 if(number.compareTo(BigInteger.ZERO)>=0 ){
			 result="";
			 BigInteger val = number;			 
			 while(val.compareTo(BigInteger.ONE)>0){
				 result=  val.mod(BigInteger.valueOf(2))+ result;
				 val= val.divide(BigInteger.valueOf(2));				 
			 }
			 result= val+result ;
			 if((result.length()%8)!=0){
				 int size = 8-(result.length()%8);
				 for(int i=1;i<=size;i++) result="0"+result;
			 }
		 }
		 return result;		 
	 }
	 
	 /**
	  * Converti une chaine binaire en un nombre
	  * @param param Chaine binaire
	  * @return La conversion en decimal du parametre
	  * @throws Exception Erreur generer lorsque le code ne se deroule pas correctement.
	  */
	 public static BigInteger binaryStringToBigInteger(String param) throws Exception {
		 if(!Binary.isBinaryRepresentation(param)) throw new Exception("Le parametre n'est pas une chaine binaire");
		 int pow =0;
		 BigInteger result = BigInteger.ZERO ;			
		 for(int i=param.length()-1;i>=0;i--) {
			 BigInteger aux =  (param.charAt(i)=='1'? BigInteger.ONE:BigInteger.ZERO);
			 BigInteger value = BigInteger.valueOf(2).pow(pow);
			 result = result.add(aux.multiply(value));
			 pow++;
		 }
		 return result ;		 
	 }
	 
	 /**
	  * Converti la chaine de caractere  en un nombre
	  * @param message Chaine de caractere a convertir
	  * @return Un nombre identifiant la chaine de caractere
	  * @throws Exception Erreur generer lorsque le code ne se deroule pas correctement.
	  */
	 public static BigInteger stringToNumber(String message) throws Exception {
		 if(message==null || message.length()==0) throw new Exception("Le message ne peut etre transforme en un nombre unique");
		 String binary ="";
		 for(int i=0;i<message.length();i++){
			 int aux = message.charAt(i);
			 binary += Binary.intToBinaryString(aux);
		 }
		 return RSAUtil.binaryStringToBigInteger(binary);
	 }
	 
	 /**
	  * Si possible converti un nombre en une chaine de caractere Ascii.
	  * @param number Nombre a transformer
	  * @return Une chaine de caractere Ascii
	  * @throws Exception Erreur generer lorsque le code ne se deroule pas correctement.
	  */
	 public static String numberToString(BigInteger number) throws Exception {
		 String  binary = RSAUtil.bigIntegerToBinaryString(number);
		 if(!Binary.isBinaryRepresentation(binary)) throw new Exception("L'entier ne peut etre converti en chaine de caractere");
		 String param = binary ;
		 if( (param.length()%8)!=0){
			 int count = 8-(param.length()%8);
			 for(int i=1;i<=count;i++) param="0"+param;			 
		 }
		 
		 int begin = 0, incr =8;
		 String result = "";
		 while(begin<param.length()){
			 String aux =param.substring(begin, begin+incr);
			 int value = Binary.binaryStringToInt(aux);
			 if(value<0 || value>255)  throw new Exception("L'entier ne peut etre converti en chaine de caractere ASCii");
			 result+=((char)value);
			 begin+=incr;
		 } 
		 return result;
	 }
	 
	
	public static void main(String[] arg) throws Exception {
		
		BigInteger[] primes = RSAUtil.generateTwoPrimes(5);// >3 c'est bon pour buildKeys[]; et >2 c'est bon pour generateTwoPrimes
		System.out.println("p = "+primes[0]+" q = "+primes[1]+" ; taille p = "+RSAUtil.getSizeOf(primes[0])+" et taille q = "+RSAUtil.getSizeOf(primes[1]));
		
		Key[] keys = RSAUtil.buildKeysOf(primes[0], primes[1]);
		
		System.out.println("Public Key = "+keys[0]+"; Private Key = "+keys[1]);
		
		BigInteger m = BigInteger.valueOf(255);//(primes[0].multiply(primes[1])).add(BigInteger.valueOf(5));
		
		System.out.println("Message a chiffrer = "+m);
		
		RSA rsa = new RSA(keys[0],keys[1]);
		BigInteger crypted =  rsa.encrypt(m);
		BigInteger decrypted = rsa.decrypt(crypted);
		System.out.println("crypted = "+crypted+"; decrypted = "+decrypted);
		
		
		BigInteger a = BigInteger.valueOf(81) ;
		BigInteger b = BigInteger.valueOf(40) ;
		BigInteger r [] = RSAUtil.extendedEuclide(a, b) ;
		System.out.println("pgcd ("+a+","+b+")="+r[0]+"\nNombre de Bezout de "+a+" = "+r[1]+"\nNombre de Bezout de "+b+" = "+r[2]);
		
		System.out.println("Inverse de 81 modulo 40 = "+RSAUtil.inverse(a, b));
		System.out.println("Inverse de 40 modulo 81 = "+RSAUtil.inverse(b, a));
		
		BigInteger factor = BigInteger.valueOf(845);
		ArrayList<Number> res = RSAUtil.decompose(factor);
		System.out.println("Decomposition en facteurs premiers de "+factor+" = "+res);
		
		BigInteger n = BigInteger.valueOf(533);
		BigInteger e = BigInteger.valueOf(91);
		Key key = RSAUtil.findOtherKey(new Key(e,n));		
		System.out.println("cle publique =("+e+","+n+"); cle privee="+key);
		
	}

}