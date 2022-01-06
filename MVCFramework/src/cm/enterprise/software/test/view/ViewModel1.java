package cm.enterprise.software.test.view;

import cm.enterprise.software.component.view.View;
import cm.enterprise.software.test.controller.model1.ControllerOfModel1;
import cm.enterprise.software.test.listener.model1.Event2Listener;

/**
 * La classe parente de tous les exemples de vue du modele Model1
 * @author Charles Moute
 * @version 1.0.1, 17/07/2010
 */
public abstract class ViewModel1 extends View implements Event2Listener {

	public ViewModel1(ControllerOfModel1 controller) {
		super(controller);
	}
}
