package cm.enterprise.software.exception;

/**
 * Exception lance lorsqu'une operation a echoue.
 * 
 * @author Charles Mouté
 * @version 1.0.1, 24/06/2010
 */

public class OperationFailureException extends ProgramException {

	private static final long serialVersionUID = 1L;

	/**
	 * Construit une instance de OperationFailureException avec le parametre comme message d'erreur.
	 * Voire les constantes de InitiatorOperationFailureException pour obtenir la liste des messages.
	 * @param message Message d'erreur
	 */
	public OperationFailureException(String message){
		super(message);
	}
}
