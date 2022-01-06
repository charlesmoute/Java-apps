package cm.ringo.dialer.component.view;

import cm.enterprise.software.component.view.SuperView;
import cm.enterprise.software.exception.ProgramException;
import cm.ringo.dialer.component.controller.DialerController;
import cm.ringo.dialer.component.model.listener.LanguageListener;

/**
 * Classe parente des vues du dialer
 * @author Charles Moute
 * @version 1.0.1, Juillet 2010
 */
public abstract class DialerView extends SuperView implements LanguageListener  {

	public DialerView(DialerController controller) throws ProgramException {
		super(controller);
	}
}
