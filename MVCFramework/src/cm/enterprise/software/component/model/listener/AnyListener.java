package cm.enterprise.software.component.model.listener;

/**
 * Represente l'ecouteur de l'evenemet AnyChangeEvent.
 * @see cm.enterprise.software.component.model.event.AnyChangedEvent
 * @author Charles Moute
 * @version 1.0.1, 2/07/2010
 */
public interface AnyListener extends Listener {

	/**
	 * Invoquer lorsque n'importe quoi a change.
	 * @param event Evenement lance lors du changement de n'importe quoi.
	 */
	public void anyChanged(cm.enterprise.software.component.model.event.AnyChangedEvent event);
}
