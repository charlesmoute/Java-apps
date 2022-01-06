package cm.enterprise.software.test.view;

import cm.enterprise.software.component.view.View;
import cm.enterprise.software.test.controller.model2.ControllerOfModel2;
import cm.enterprise.software.test.listener.model2.Event1Listener;

/**
 * Classe parente des exemples de vues pour le modele 2
 * @author Charles Moute
 * @version 1.0.1, 17/07/2010
 */
public abstract class ViewModel2 extends View implements Event1Listener {

	public ViewModel2(ControllerOfModel2 controller) {
		super(controller);
	}
}
