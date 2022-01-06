package cm.ringo.dialer.component.view.connection;

import java.beans.PropertyChangeListener;

import cm.enterprise.software.component.view.SuperView;
import cm.enterprise.software.exception.ProgramException;
import cm.ringo.dialer.component.controller.connection.ConnectionController;

public abstract class ConnectionView extends SuperView implements PropertyChangeListener {

	public ConnectionView(ConnectionController controller) throws ProgramException {
		super(controller);
	}
}
