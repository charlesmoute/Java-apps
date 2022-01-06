package cm.ringo.dialer.component.model.event;

import cm.enterprise.software.component.model.event.Event;
import cm.ringo.dialer.component.model.base.DialerModel;

/**
 * Evenemet de changement de langue.
 * @author Charles Moute
 * @version 1.0.1, 4/8/2010
 */
public class LanguageChangedEvent extends Event {

	private static final long serialVersionUID = 1L;
	
	private String newLanguage ;

	public LanguageChangedEvent(DialerModel source, String newLanguage) {
		super(source);
		this.newLanguage = newLanguage ;
	}
	
	/**
	 * Permet d'obtenir la nouvelle langue courante.
	 * @return La nouvelle langue.
	 */
	public String getNewLanguage() { return this.newLanguage; }

}
