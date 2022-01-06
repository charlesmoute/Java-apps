package cm.ringo.dialer.helper.connection.multilingual;

import java.util.Locale;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.util.MultilingualResource;

/**
 * Represente la ressource des parametres multilingues d'une entree de
 * connexion.
 * @author Charles Moute
 * @version 1.0.1, 5/7/2010
 */
public class EntryMultilingualResource extends MultilingualResource {

	public EntryMultilingualResource() throws ProgramException {
		super();
		//definition des langues disponibles
		this.addSupportedLanguage(Locale.FRENCH.getLanguage());
		this.addSupportedLanguage(Locale.US.getLanguage());
		
		//Defintion de la langue par defaut
		this.setDefaultLanguage(Locale.US.getLanguage());
		//definition des parametres mutlilingues 
		this.definesParameter("bytesReceivedLabel","bytesTransmittedLabel","speedconnectionLabel","timeLabel");
		this.definesParameter("connectedMessage","dialingMessage","disconnectedMessage");
		this.definesParameter("unit");
		//Definition des valeurs des parametres multitlingues
		
		// -- En langue francaise
		this.setValueTo("bytesReceivedLabel", Locale.FRANCE.getLanguage(), "Paquets reçus");
		this.setValueTo("bytesTransmittedLabel", Locale.FRANCE.getLanguage(), "Paquets transmis");
		this.setValueTo("speedconnectionLabel", Locale.FRANCE.getLanguage(), "Vitesse connexion");
		this.setValueTo("timeLabel", Locale.FRANCE.getLanguage(), "Temps connexion");
		this.setValueTo("connectedMessage", Locale.FRANCE.getLanguage(), "Connecté");
		this.setValueTo("dialingMessage", Locale.FRANCE.getLanguage(), "Connexion en cours");
		this.setValueTo("disconnectedMessage", Locale.FRANCE.getLanguage(), "Déconnecté");
		this.setValueTo("unit", Locale.FRANCE.getLanguage(), "0ctet(s)");
		
		// -- En langue anglaise
		this.setValueTo("bytesReceivedLabel", Locale.US.getLanguage(), "Bytes Received");
		this.setValueTo("bytesTransmittedLabel", Locale.US.getLanguage(), "Bytes Transmitted");
		this.setValueTo("speedconnectionLabel", Locale.US.getLanguage(), "Connection Speed");
		this.setValueTo("timeLabel", Locale.US.getLanguage(), "Connection Time");
		this.setValueTo("connectedMessage", Locale.US.getLanguage(), "Connected");
		this.setValueTo("dialingMessage", Locale.US.getLanguage(), "Dialing");
		this.setValueTo("disconnectedMessage", Locale.US.getLanguage(), "Disconnected");
		this.setValueTo("unit", Locale.US.getLanguage(), "Byte(s)");
		
		//Definition de la langue courante de la ressource a la langue par defaut
		this.setCurrentLanguage(this.getDefaultLanguage());
	}

	@Override
	protected void load(String language) throws ProgramException {}

}
