package cm.enterprise.software.component.model.event;

import java.util.EventObject;
import cm.enterprise.software.component.model.base.Model;

/**
 * Event est l'ancetre de tous les evenements que pourront generer une classe heritant de Model.
 * @author Charles Moute
 * @version 28/06/2010
 */
public abstract class Event extends EventObject {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Instancie un objet de la classe Event 
	 * @param source Source de l'evenement.
	 */
	public Event(Model source) {super(source);}	
}
