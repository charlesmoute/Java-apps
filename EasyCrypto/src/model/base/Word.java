package model.base;

/**
 * @author Charles
 * <p> Represente un mot de la table du dictionnaire. La concatenation index de la ligne et index de la colonne
 * represente le code associe au mot.
 * </p>
 */
public class Word {

	/**
	 * Code associe au mot.
	 */
	private String code = null;
	
	/**
	 * Valeur du mot
	 */
	private String value = null;
		
	/**
	 * Cree une instance de mot de dictionnaire. La concatenation de row et column donne le code du mot 
	 * @param code Represente le code associe au mot
	 * @param value Represente la value du mot de dictionnaire
	 * @throws Exception Erreur lance lorsque le code ne s'execute pas correctement.
	 */
	public Word(String code,String value) {
		this.code = code ;
		this.value = value ;
	}
	
	/**
	 * Affecte un nouveau code au mot
	 * @param code Nouveau code.
	 * @throws Exception Erreur generee lorsque le code est incorrect
	 */
	public void setCode(String code) {
		this.code = code; 
	}
	
	/**
	 * @return Le code associe au mot du dictionnaire.
	 */
	public String getCode(){ return code ; }
	
	
	/** 
	 * Affecte une nouvelle valeur au mot du dictionnaire
	 * @param value
	 */
	public void setValue(String value) { this.value = value ; }
	
	/**
	 * @return La valeur du mot du dictionnaire.
	 */
	public String getValue() { return this.value; }
	
	
	/**
	 * Verifie si le mot courant a la meme valeur que le mot en parametre
	 * @param word Mot comparatif
	 * @return this.getValue().compareTo(word.getValue())
	 */
	public boolean hasSameValue(Word word){
		if(word==null) return false;
		return ( this.value.compareTo(word.getValue())==0 );
	}
	
	/**
	 * Verifie si le mot courant a le meme code que le mot en parametre
	 * @param word Mot comparatif
	 * @return True si ils ont le meme code et false dans le cas contraire.
	 */
	public boolean hasSameCode(Word word){
		if(word==null) return false;
		return ( this.code.compareTo(word.getCode())==0 );
	}
	
	/**
	 * Verifie si le mot courant est identique en tout point au mot en parametre
	 * @param word Mot comparatif
	 * @return true si les mots sont identiques.
	 */
	public boolean equals(Word word){
		if(word==null) return false;		
		return ( this.value.compareTo(word.getValue())==0 && this.code.compareTo(word.getCode())==0 );
	}
	
	public String toString() {		
		return value;
	}
}
