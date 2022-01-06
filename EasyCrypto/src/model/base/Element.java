package model.base;

/**
 * 
 * @author Charles
 * <p> Cette Classe Represente un Symbole de l'alphabet auquel on associe un code. </p>
 */

public class Element extends Symbol {

	/**
	 * <p>Code est le codage associe a l'element.</p>
	 */
	private String code;
	
	/**
	 * <p>Constuit une instance d'Element avec les parametres ci-dessous.</p>
	 * @param symbol Element de l'alphabet.
	 * @param probability Probabilite d'occurence.
	 * @param code Code associe a l'element de l'alphabet.
	 * @throws Exception Erreur lancee lorsque la probabilite n'est pas dans [0,1]
	 */
	public Element(String symbol, double probability,String code) throws Exception {
		super(symbol, probability);
		this.code = code ;
	}

	
	/**
	 * <p>Construit une instance d'Element avec les parametres ci-dessous et avec comme code 
	 * la chaine vide.</p> 
	 * @param symbol Elementy de l'alphabet.
	 * @param probability Probabilite d'occurence.
	 * @see #Element(String, double, String)
	 * @throws Exception Erreur lancee lorsque la probabilite n'est pas dans [0,1]
	 */
	public Element(String symbol, double probability) throws Exception {
		this(symbol, probability,null);		
	}
	
	/**
	 * <p>Construit une instance d'Element avec le paramétre ci-dessous et
	 * comme probabilite d'occcurence 0.0 et code associe a l'element la chaine vide.</p>
	 * @param symbol Element de l'alphabet.
	 * @see #Element(String, double, String)
	 * @throws Exception Erreur lancee lorsque la probabilite n'est pas dans [0,1]
	 */
	public Element(String symbol) throws Exception {
		this(symbol,0.0,null);
	}
	
	/**
	 * <p>Construit une instance d'Element avec comme probabilite d'occurence probability,
	 * le symbole et le code associe sont tous les deux la chaine vide.</p>
	 * @param probability Probabilite d'occurence.
	 * @see #Element(String, double, String)
	 * @throws Exception Erreur lancee lorsque la probabilite n'est pas dans [0,1]
	 */
	public Element(double probability) throws Exception {
		this(null, probability,null);
	}

	/**
	 * <p>Construit une instance d'Element avec comme symbole la chaine vide, comme probabilite
	 * d'occurence 0.0 et comme code associe au symbole la chaine vide.</p>
	 * @see #Element(String, double, String)
	 * @throws Exception Erreur lancee lorsque la probabilite n'est pas dans [0,1]
	 */
	public Element() throws Exception{
		this(null,0.0,null);
	}
	
	/**
	 * <p>Affecte le parametre comme codage du symbole de l'element de l'alaphabet.</p>
	 * @param code Code a associer au Symbole.
	 */
	public void setCode(String code){ this.code = code; }
	
	/**
	 * 
	 * @return Le code associe au symbole.
	 */
	public String getCode() { return this.code; }
	
	public String toString() {		
		return super.toString()+" == > "+this.code;
	}

}
