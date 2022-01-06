package cm.ringo.dialer.component.view.connection.entry;

import java.beans.PropertyChangeEvent;

import javax.swing.*;

import cm.enterprise.software.component.model.event.AnyChangedEvent;
import cm.ringo.dialer.component.controller.connection.EntryController;
import cm.ringo.dialer.component.model.base.connection.EntryModel;
import cm.ringo.dialer.helper.connection.EntryHelper;

/**
 * Vue representant le status de la connexion
 * @author Charles Mouté
 * @version 1.0.1, 7/8/2010
 */
public class EntryStatusView extends EntryView {

	private JLabel status;
	
	public EntryStatusView(EntryController controller) {
		super(controller);
		status = new JLabel();		
		getContent();//Juste pour initialiser avt la premiere utilisation
	}

	@Override
	public void disable() {
		status.setEnabled(false);
	}

	@Override
	public void enable() {
		status.setEnabled(true);
	}

	@Override
	public Object getContent() {
		int  value = ((EntryModel)controller.getModel()).getStatus(); 
		status.setText(EntryHelper.getMessageOfStatus(value));
		status.setIcon(new ImageIcon(EntryHelper.getPictureOfStatus(value)));
		return status;
	}

	@Override
	public void hide() {
		status.setVisible(false);
	}

	@Override
	public void show() {
		status.setVisible(true);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		/*
		 * La verif ci-dessous pas necessaire vu que le controleur
		 * n'ajoute que les vues de ce type comme ecouteur de changement de status.
		 * Mais bof par prudence. 
		 */
		if(("status").compareTo(evt.getPropertyName())==0){
			Integer value = (Integer)evt.getNewValue();
			status.setText(EntryHelper.getMessageOfStatus(value));
			status.setIcon(new ImageIcon(EntryHelper.getPictureOfStatus(value)));
		}
	}

	@Override
	//cette fonction represente ce que dise les autres vues.
	public void anyChanged(AnyChangedEvent event) {}

}
