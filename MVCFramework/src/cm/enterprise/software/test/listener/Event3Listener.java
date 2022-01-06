package cm.enterprise.software.test.listener;

import cm.enterprise.software.component.model.listener.Listener;
import cm.enterprise.software.test.event.Event3;

/**
 * Exemple d'un ecouteur pour l'evenement Event3
 * @author Charles Moute
 * @version 1.0.1, 17/07/2010
 */
public interface Event3Listener extends Listener {

	/**
	 * Ecouteur de l'evenement 
	 * @param event Evenement 3
	 */
	public void event3Changed(Event3 event);
}
