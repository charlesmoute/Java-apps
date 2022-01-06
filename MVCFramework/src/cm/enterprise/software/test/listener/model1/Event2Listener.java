package cm.enterprise.software.test.listener.model1;

import cm.enterprise.software.component.model.listener.Listener;
import cm.enterprise.software.test.event.model1.Event2;

/**
 * Exemple d'ecouteur de l'evenement 2
 * @author Charles Moute
 * @version 1.0.1, 17/07/2010
 */
public interface Event2Listener extends Listener {

	/**
	 * Ecouteur de l'evenemet 2
	 * @param evt Evenemet de 2
	 */
	public void event2Changed(Event2 evt);
}
