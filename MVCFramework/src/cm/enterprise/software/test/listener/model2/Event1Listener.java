package cm.enterprise.software.test.listener.model2;

import cm.enterprise.software.component.model.listener.Listener;
import cm.enterprise.software.test.event.model2.Event1;

/**
 * Exemple d'ecouteur de l'evenement 1
 * @author Charles Moute
 * @version 1.0.1, 17/07/2010
 */
public interface Event1Listener extends Listener {

	/**
	 * Ecouteur du changement de l'evenement 1
	 * @param event Event 1
	 */
	public void event1Changed(Event1 event);
}
