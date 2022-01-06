package cm.ringo.dialer.component.controller.connection;

import java.beans.PropertyChangeListener;

import cm.enterprise.software.component.controller.Controller;
import cm.enterprise.software.component.model.base.Model;
import cm.enterprise.software.component.model.listener.Listener;
import cm.enterprise.software.exception.ProgramException;
import cm.ringo.dialer.component.model.base.connection.EntryModel;
import cm.ringo.dialer.component.view.connection.entry.EntryDefaultView;
import cm.ringo.dialer.component.view.connection.entry.EntryStatusView;

/**
 * Represente le model de controleur d'une entree de connexion
 * @author Charles Moute
 * @version 1.0.1, 5/8/2010
 */
public class EntryController extends Controller {

	@SuppressWarnings("unchecked")
	public EntryController(EntryModel model) throws ProgramException {
		super(model);
		
		//definition de quelques classes de vues controllables
		this.addClassOfView(EntryDefaultView.class,EntryStatusView.class);
		
	}

	@Override
	protected <T extends Listener> void addListenerToModel(T listener) {

		if(listener instanceof EntryStatusView){
			EntryModel m = (EntryModel)model;
			PropertyChangeListener l = (PropertyChangeListener) listener;
			m.addStatusChangeListener(l);
			return;
		}
		
		if(listener instanceof EntryDefaultView){
			EntryModel m = (EntryModel)model;
			PropertyChangeListener l = (PropertyChangeListener) listener;
			m.addTimeChangeListener(l);
			m.addEntryNameChangeListener(l);
			m.addBytesReceivedChangeListener(l);
			m.addBytesTransmittedChangeListener(l);
			m.addConnectionSpeedChangeListener(l);
			return;
		}
		
		if(listener instanceof PropertyChangeListener){
			EntryModel m = (EntryModel)model;
			PropertyChangeListener l = (PropertyChangeListener) listener;
			m.addTimeChangeListener(l);
			m.addStatusChangeListener(l);
			m.addEntryNameChangeListener(l);
			m.addBytesReceivedChangeListener(l);
			m.addBytesTransmittedChangeListener(l);
			m.addConnectionSpeedChangeListener(l);
		}
	}

	@Override
	public boolean isControllableModel(Model model) {
		return (model instanceof EntryModel);
	}

	@Override
	public void notifyEvent(Object param) {
		/*
		 * cette fonction est le moyen que les vues on de communiquer avec le controleur qui
		 * definira selon param l'action a entreprendre aupres du model
		 * Dans ce cas je ne pense pas qu'il servira a grand chose
		 */
	}

	@Override
	protected <T extends Listener> void removeListenerOfModel(T listener) {
		
		if(listener instanceof EntryStatusView){
			EntryModel m = (EntryModel)model;
			PropertyChangeListener l = (PropertyChangeListener) listener;
			m.removeStatusChangeListener(l);
		}
		
		if(listener instanceof EntryDefaultView){
			EntryModel m = (EntryModel)model;
			PropertyChangeListener l = (PropertyChangeListener) listener;
			m.removeTimeChangeListener(l);
			m.removeEntryNameChangeListener(l);
			m.removeBytesReceivedChangeListener(l);
			m.removeBytesTransmittedChangeListener(l);
			m.removeConnectionSpeedChangeListener(l);
		}
		
		/*if(listener instanceof PropertyChangeListener){
			EntryModel m = (EntryModel)model;
			PropertyChangeListener l = (PropertyChangeListener) listener;
			m.removeTimeChangeListener(l);
			m.removeEntryNameChangeListener(l);
			m.removeBytesReceivedChangeListener(l);
			m.removeBytesTransmittedChangeListener(l);
			m.removeSpeedConnectionChangeListener(l);
		}*/
		
	}

}
