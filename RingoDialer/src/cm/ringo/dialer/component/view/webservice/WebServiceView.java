package cm.ringo.dialer.component.view.webservice;

import java.beans.PropertyChangeListener;

import cm.enterprise.software.component.view.View;
import cm.ringo.dialer.component.controller.webservice.WebServiceController;

/**
 * La classe parente des vues des web-services
 * @author Charles Mouté
 * @version 1.0.1, 12/12/2010 
 */
public abstract class WebServiceView extends View implements PropertyChangeListener {

	public WebServiceView(WebServiceController controller) {
		super(controller);
	}


}
