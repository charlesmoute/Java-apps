package cm.ringo.dialer.component.model.listener;

import cm.enterprise.software.component.model.listener.Listener;
import cm.ringo.dialer.component.model.event.LanguageChangedEvent;

/**
 * Ecouteur de changement de langue.
 * @author Charles Moute
 * @version 1.0.1, 4/8/2010
 */
public interface LanguageListener extends Listener {

	/**
	 * Invoquer lorsque un evenement de  changement de langue est lance.
	 * @param evt Evenement de changement de langue
	 */
	public void languageChanged(LanguageChangedEvent evt);
}
