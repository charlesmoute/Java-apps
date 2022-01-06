package model.base;

import helper.Constant;

/**
 * @author Charles
 * <p> Cette classe represente une sortie de dictionnaire, en terme de paires (rang,caractere)</p>
 */
public class Output {

	private int position ;
	private char character ;
	
	public Output(int position,char character ){
		this.position = position ;
		this.character = character ;
	}
	
	/**
	 * Affecte un nouveau rang a la sortie.
	 * @param position Nouveau rang du caractère
	 */
	public void setPosition(int position){ this.position = position;}
	
	/**
	 * @return La position de la sortie.
	 */
	public int getPosition() { return this.position; }
	
	/**
	 * Affecte un nouveau carctere a la sortie
	 * @param character Nouveau caractere
	 */
	public void setCharacter(char character) { this.character = character; }
	
	/**
	 * @return Le caractere associe a la sortie.
	 */
	public char getCharacter() { return this.character; }
	
	public String toString() {
		String c = (character==' ')? Constant.SPACE:""+character;
		return "("+position+","+c+")";
	}
}
