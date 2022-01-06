package cm.ringo.dialer.component.view.connection.entry;

import java.beans.PropertyChangeListener;

import cm.enterprise.software.component.view.View;
import cm.ringo.dialer.component.controller.connection.EntryController;

/**
 * La vue parente des vues d'entree de connexion
 * 
 * @author Charles Mouté
 * @version 1.0.1, 7/8/2010
 */
public abstract class EntryView extends View implements PropertyChangeListener {

	public EntryView(EntryController controller) {
		super(controller);
	}


}
