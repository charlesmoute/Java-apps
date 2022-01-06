package model.base;

import java.math.*;
/**
 * @author Charles
 * <p>
 * 	Cette classe represente un nombre sous la forme d'un nombre premier puissance un valeur.
 *  Elle sera utilise pour la factorisation d'un nombre en nombre premiers.   
 *  Ainsi par exemple 16 sera 2^4, ou 2 est le facteur premier, ^ l'elevation a la puissance et 4 l'exposant 
 *  de la puissance.  
 * </p>
 */
public class Number {

	/**
	 * Le nombre premier
	 **/
	private BigInteger prime ;
	
	/**
	 * L'exposant de la puissance
	 **/
	private int e ;
	
	/**
	 * Cree une instance de Nombre avec un unique facteur premier
	 * @param prime Facteur premier
	 * @param e Exposant de la puissance
	 * @throws Exception Erreur generee lorsque prime n'est pas un nombre premier.
	 */
	public Number(BigInteger prime, int e) throws Exception {
		this.setPrime(prime);
		this.setExponent(e);
	}
	
	/**
	 * Affecte le parametre comme le nouveau facteur
	 * @param p Nouveau facteur
	 * @throws Exception Erreur generee lorsque le parametre n'est pas un nombre premier.
	 */
	public void setPrime(BigInteger p)throws Exception {
		if(!helper.RSAUtil.isPrime(p)) throw new Exception("Le facteur n'est pas un nombre premier");
		this.prime = p ;
	}
	
	/**
	 * @return Le facteur du nombre
	 */
	public BigInteger getPrime() { return this.prime; }
	
	/**
	 * Affecte le parametre comme le nouvel exposant de la puissance
	 * @param e Nouvel exposant de la puissance
	 */
	public void setExponent(int e) { this.e = e ; }
	
	/**
	 * @return L'exposant de la puissance.
	 */
	public int getExponent() { return this.e; }
	
	public String toString(){ return prime+"^"+e; }
}
