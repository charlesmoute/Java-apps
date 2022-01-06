package model.base;

import helper.*;

/**
 * 
 * @author Charles
 * <p> Cette Classe represente un Symbole d'un alphabet avec sa probabilite. </p>
 */
public class Symbol {	
	
	/**
	 * symbol est un element de l'aphabet.
	 */
	private String symbol;
	
	/**
	 * probability est la probabilite d'occurence du symbole. 
	 */
	private double probability;
	
	/**
	 * <p>
	 *  Construit une instance de Symbol avec la probabilité d'occurence probability
	 *  et le parametre symbol comme element de l'alaphabet.  
	 * </p>
	 * @param symbol Element de l'alaphabet.
	 * @param probability  Probabilite d'occurence du symbole.
	 * @throws Exception Erreur lancee lorsque la probabilite n'est pas dans [0,1]
	 */
	public Symbol(String symbol, double probability) throws Exception{
		if(probability<0 || probability>1 ) throw new Exception("La probabilite n'est pas dans [0,1]");
		this.symbol = symbol ;
		this.probability = probability;
	}
	
	/**
	 * <p>
	 * 	 Construit une instance de Symbol avec comme probabilite d'occurence 0.0 et
	 *   comme element de l'alphabet le parametre symbol.
	 * </p>
	 * @param symbol Element de l'alaphabet.
	 * @see #Symbol(String, double)
	 * @throws Exception Erreur lancee lorsque la probabilite n'est pas dans [0,1]
	 */
	public Symbol(String symbol) throws Exception{
		this(symbol,0.0);
	}
	
	/**
	 * <p>
	 * 	Contruit une instance de Symbol avec comme probabilite d'occurence probability et
	 *  comme element de l'alaphabet la chaine vide.
	 * </p>
	 * @param probability Probabilite d'occurence.
	 * @throws Exception Erreur lancee lorsque la probabilite n'est pas dans [0,1] 
	 */
	public Symbol(double probability) throws Exception{
		this(null,probability);
	}
	
	/**
	 * <p>Contruit une instance de Symbol avec comme probabilite d'occurence 0.0 et
	 * comme element de l'alphabet la chaine vide.</p>
	 * @throws Exception Erreur lancee lorsque la probabilite n'est pas dans [0,1] 
	 */
	public Symbol() throws Exception{
		this(null,0.0);
	}	
	
	/**
	 * <p>Affecte le parametre comme element de l'alaphabet.</p>
	 * @param symbol Element de l'alphabet.
	 * @see #getSymbol()
	 */
	public void setSymbol(String symbol) { this.symbol = symbol; }
	
	/**
	 * 
	 * @return Un symbole.
	 * @see #setSymbol(String)
	 */
	public String getSymbol() { return this.symbol; }
	
	
	/**
	 * <p>Affecte probability comme probabilite d'occurence du symbol</p>
	 * @param probability
	 * @see #getProbability() 
	 * @throws Exception Erreur lancee lorsque la probabilite n'est pas dans [0,1]
	 */
	public void setProbability(double probability) throws Exception	{
		if(probability<0 || probability>1) throw new Exception("La probabilite n'est pas dans [0,1]");
		this.probability = probability; 
	}
	
	/**
	 * 
	 * @return La probabilite d'occurence du Symbole.
	 * @see #setProbability(double)
	 */
	public double getProbability() { return this.probability; }
	
	/**
	 * <p>Ajoute a la probabilite du symbole courant proba.</p>
	 * @param probability Probabilite a ajouter.
	 * @throws Exception Erreur lancee lorsque la probabilite obtenue n'est pas dans [0,1]
	 */
	public void addProbability(double probability)throws Exception {
		this.setProbability(this.probability+probability);		
	}
	
	public String toString() {
		String str = symbol;
		if(this.symbol.compareTo(" ")==0) str = Constant.SPACE;
		return str+" == > "+Util.round(this.probability,2); 
	}
}
