package cm.ringo.dialer.helper.connection.multilingual;


import java.util.Locale;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.util.MultilingualResource;

/**
 * Ressource multilingue d'une connexion
 * @author Charles Moute
 * @version 1.0.1, 8/8/2010
 */
public class ConnectionMultilingualResource extends MultilingualResource {

	public ConnectionMultilingualResource() throws ProgramException {
		super(); 
		//definition des langues disponibles
		this.addSupportedLanguage(Locale.FRENCH.getLanguage());
		this.addSupportedLanguage(Locale.US.getLanguage());
		
		//Defintion de la langue par defaut
		this.setDefaultLanguage(Locale.US.getLanguage());
		//definition des parametres mutlilingues 
		this.definesParameter("nameLabel","passwordLabel","connectionLabel","disconnectionLabel", "hideLabel", "rememberMeLabel");
		
		//Definition des valeurs des parametres multitlingues
		
		// -- En langue francaise
		this.setValueTo("nameLabel", Locale.FRANCE.getLanguage(), "Login");
		this.setValueTo("passwordLabel", Locale.FRANCE.getLanguage(), "Mot de passe");
		this.setValueTo("rememberMeLabel", Locale.FRANCE.getLanguage(), "Mémoriser");
		this.setValueTo("connectionLabel", Locale.FRANCE.getLanguage(), "Connexion");
		this.setValueTo("disconnectionLabel", Locale.FRANCE.getLanguage(), "Déconnexion");
		this.setValueTo("hideLabel", Locale.FRANCE.getLanguage(), "Cacher");
		
		// -- En langue anglaise
		this.setValueTo("nameLabel", Locale.US.getLanguage(), "Login");
		this.setValueTo("passwordLabel", Locale.US.getLanguage(), "Password");
		this.setValueTo("rememberMeLabel", Locale.US.getLanguage(), "Remember my password");
		this.setValueTo("connectionLabel", Locale.US.getLanguage(), "Connection");
		this.setValueTo("disconnectionLabel", Locale.US.getLanguage(), "Disconnection");
		this.setValueTo("hideLabel", Locale.US.getLanguage(), "Hide");
		
		//Definition de la langue courante de la ressource a la langue par defaut
		this.setCurrentLanguage(this.getDefaultLanguage());
	}
	
	/**
	 * Permet d'obtenir la valeur du label indiquant la zone du nom utilisateur.
	 * @return La valeur du label indiquant la zone du nom utilisateur.
	 */
	public String getUserNameLabel() { return this.getValueOf("nameLabel"); }
	
	/**
	 * Permet d'obtenir la valeur du label indiquant la zone du mot de passe.
	 * @return La valeur du label indiquant la zone du mot de passe.
	 */
	public String getPasswordLabel() { return this.getValueOf("passwordLabel"); }
	
	public String getRememberMeLabel(){ return this.getValueOf("rememberMeLabel"); }
	
	@Override
	protected void load(String language) throws ProgramException {}

}
