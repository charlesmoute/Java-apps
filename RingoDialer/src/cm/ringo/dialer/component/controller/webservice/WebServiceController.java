package cm.ringo.dialer.component.controller.webservice;

import cm.enterprise.software.component.controller.Controller;
import cm.enterprise.software.component.model.base.Model;
import cm.enterprise.software.component.model.listener.Listener;
import cm.enterprise.software.exception.ProgramException;
import cm.ringo.dialer.component.model.base.webservice.WebServiceModel;

/**
 * Controleur des web-services.
 * @author Charles Mouté
 * @version 1.0.1, 12/12/2010
 */
public class WebServiceController extends Controller {

	public WebServiceController(WebServiceModel model) throws ProgramException {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected <T extends Listener> void addListenerToModel(T listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isControllableModel(Model model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void notifyEvent(Object param) {
		// TODO Auto-generated method stub

	}

	@Override
	protected <T extends Listener> void removeListenerOfModel(T listener) {
		// TODO Auto-generated method stub

	}

}
