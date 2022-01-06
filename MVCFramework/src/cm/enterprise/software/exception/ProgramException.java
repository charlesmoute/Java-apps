package cm.enterprise.software.exception;

/**
 * Represente l'ensemble des erreurs connus pouvant intervenir dans le programme.
 * 
 *  @author Charles Mouté
 *  @version 1.0.1, 23/06/2010
 */
public class ProgramException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Construit un <code>ProgramException</code> avec aucun message d'erreur
	 */
	
	public ProgramException(){
		super();
	}
	
	/**
	 * Construit une instance de ProgramException avec le parametre comme message d'erreur. 
	 * @param message
	 */
	public ProgramException(String message){
		super(message);
	}
}
