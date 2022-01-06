package cm.enterprise.software.test.util;

/**
 * Exemple de message que peut recevoir un modele au travers de sa
 * methode anyChanged.
 * @author Charles Moute
 * @version 1.0.1, 20/07/2010
 */
public class MessageOfSuperModelTest {

	/**
	 * Entier signalant que la notification est a destination du Model1
	 */
	public final static int MODEL1_NOTIFICATION = 0 ;
	
	/**
	 * Entier signalant que la notification est à destination du Model2 
	 */
	public final static int MODEL2_NOTIFICATION = 1 ;
	
	/**
	 * Entier signalant que la notification est à destination du Super Model
	 */
	public final static int SUPERMODEL_NOTIFICATION = 2 ;
	
	
	/**
	 * Represente la notification
	 */
	private int notification = -1 ;
	
	/**
	 * Contenu du message
	 */
	private Object messageContent = null;
	
	/**
	 * Instancie un objet MessageOfSuperModelTest avec les parametres ci-dessous.
	 * <ul>
	 * 	<li>MODEL1_NOTIFICATION = 0 : signifie que la notification est destinee au model1</li>
	 * 	<li>MODEL2_NOTIFICATION = 1 : signifie que la notification est destinee au model2</li>
	 * 	<li>SUPERMODEL_NOTIFICATION = 2 : signifie que la notification est destinee au super model.</li>
	 * </ul>
	 * @param notification Notification
	 * @param messageContent Contenu du message.
	 */
	public MessageOfSuperModelTest(int notification,Object messageContent){
		this.notification = notification ;
		this.messageContent = messageContent ;
	}
	
	/**
	 *  Permet d'obtenir la notification a qui le message est destine.
	 * @return la notification
	 */
	public int getNotification() {
		return notification;
	}

	/**
	 * Permet d'obtenir le contenu du message
	 * @return Contenu du message.
	 */
	public Object getMessageContent() {
		return messageContent;
	}
}
