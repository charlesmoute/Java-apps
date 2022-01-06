package cm.enterprise.software.exception;

//import cm.enterprise.software.helper.Constants;
import cm.enterprise.software.helper.Application;
import cm.enterprise.software.helper.ExceptionResource;

/**
 * Erreur lancee lorsqu'un parametre est errone.
 * 
 * @author Charles Mouté
 * @version 1.0.1, 24/06/2010
 */
public class ErroneousParameterException extends ProgramException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Construit une instance d'ErroneousParameterException avec le message d'erreur approprie. 
	 */
	public ErroneousParameterException(){
		super(Application.getResource(ExceptionResource.class).getValueOf("erroneousParameterException"));
	}
	
}
