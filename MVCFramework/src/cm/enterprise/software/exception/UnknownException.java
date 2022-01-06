package cm.enterprise.software.exception;

import cm.enterprise.software.helper.Application;
import cm.enterprise.software.helper.ExceptionResource;

/**
 * Reprensente une erreur inconnu par le programme. Avant de lancer cette option, on devrait
 * utiliser InitiatorOfUnknownException.init();
 * 
 * @author Charles Mouté
 * @version 1.0.1, 23/06/2010
 */
public class UnknownException extends ProgramException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Construit une instance de UnknownException selon les preferences de l'utilisateur.
	 * Pour cela assurez vous d'avoir au moins appelez une fois la fonction init frr InitiatorException.
	 */
	public UnknownException(){
		super(Application.getResource(ExceptionResource.class).getValueOf("unknownException"));
	}

}
