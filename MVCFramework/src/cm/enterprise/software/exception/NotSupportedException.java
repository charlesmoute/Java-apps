package cm.enterprise.software.exception;

import cm.enterprise.software.helper.Application;
import cm.enterprise.software.helper.ExceptionResource;

/**
 * Exception lancee lorsqu'une erreur non prise en charge survient.
 * 
 * @author Charles Mouté
 * @version 1.0.1, 24/06/2010
 */

public class NotSupportedException extends ProgramException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Construit une instance de NotSupportedException avec le message approprie.
	 */
	public NotSupportedException(){	
		super(Application.getResource(ExceptionResource.class).getValueOf("notSupportedException"));
	}

}
