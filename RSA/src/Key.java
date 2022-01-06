

import java.math.*;

/**
 * @author Charles Mouté
 * <p>
 * 	Cette classe represente une cle en terme de paires (e,n) ou e peut etre l'exposant de chiffrement ou de
 *  dechiffrement et n est le modulo. Elle est utilisee particulierement dans RSA.
 * </p>
 */
public class Key {
	
	/**
	 * Exposant de chiffrement ou de dechiffrement.
	 */
	private BigInteger e ;
	
	/**
	 * Modulo
	 */
	private BigInteger n ;

	public Key(BigInteger e, BigInteger n){
		this.e = e ;
		this.n = n ;
	}
	
	/**
	 * Affecte un nouvel exposant a la cle.
	 * @param e Nouvel exposant
	 */
	public void setExponent(BigInteger e){ this.e = e; }
	
	/**
	 * @return L'exposant associe a la cle.
	 */
	public BigInteger getExponent() { return this.e; }
	
	/**
	 * Affecte un nouveau modulo a la cle.
	 * @param n Nouveau modulo.
	 */
	public void setModulo(BigInteger n) { this.n = n ; }
	
	/**
	 * @return Le modulo associe a la cle.
	 */
	public BigInteger getModulo() { return this.n; }
	
	public String toString() { return "("+e+","+n+")"; }
}
