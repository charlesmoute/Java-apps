package cm.ringo.dialer.helper;

import java.util.Locale;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.util.SuperMultilingualResource;

/**
 * Definit l'ensemble des ressources multilingues de l'application Dialer.
 * 
 * @author Charles Moute.
 * @version 1.0.1, 4/8/2010
 *
 */
public class DialerMultilingualResource extends SuperMultilingualResource {

	public DialerMultilingualResource() throws ProgramException {
		super();
		//Definition des langues que supporte la super ressource
		this.addSupportedLanguage(Locale.FRANCE.getLanguage());
		this.addSupportedLanguage(Locale.US.getLanguage());
		
		//Definition de la langue par defaut
		setDefaultLanguage(Locale.US.getLanguage());
		
		//Definition des parametres mutlilingues de la super ressource
		definesParameter("fileMenu","editionMenu","languageMenu","ringoMenu","helpMenu");
		definesParameter("exitItem");
		definesParameter("frenchItem","englishItem","preferenceItem");
		definesParameter("connectionStatus", "ringoWeb", "clientSpace", "createNewAccount");//penser à définir les valeurs 
		definesParameter("dialerUpdates", "manualItem","onlineItem","aboutItem");
		definesParameter("visibilityOption","connectionOption");
		
		//Definition des valeurs des parametres multitlingues
		
		// -- En langue francaise
		setValueTo("fileMenu", Locale.FRANCE.getLanguage(), "Fichier");
		setValueTo("exitItem", Locale.FRANCE.getLanguage(), "Quitter");
		setValueTo("editionMenu", Locale.FRANCE.getLanguage(), "Edition");
		setValueTo("languageMenu", Locale.FRANCE.getLanguage(), "Langues");
		setValueTo("frenchItem", Locale.FRANCE.getLanguage(), "Français");
		setValueTo("englishItem", Locale.FRANCE.getLanguage(), "Anglais");
		setValueTo("preferenceItem", Locale.FRANCE.getLanguage(), "Préférence");
		setValueTo("ringoMenu", Locale.FRANCE.getLanguage(), "Espace Ringo");		
		setValueTo("clientSpace", Locale.FRANCE.getLanguage(), "Espace Client");
		setValueTo("connectionStatus", Locale.FRANCE.getLanguage(), "Statut de Connexion");
		setValueTo("createNewAccount", Locale.FRANCE.getLanguage(), "Créer un Nouveau Compte");
		setValueTo("ringoWeb", Locale.FRANCE.getLanguage(), "Site Web");
		setValueTo("helpMenu", Locale.FRANCE.getLanguage(), "Aide");
		setValueTo("dialerUpdates", Locale.FRANCE.getLanguage(), "Mises à Jour RingoDialer");
		setValueTo("manualItem", Locale.FRANCE.getLanguage(), "Manuel Utilisateur");
		setValueTo("onlineItem", Locale.FRANCE.getLanguage(), "Ringo Online");
		setValueTo("aboutItem", Locale.FRANCE.getLanguage(), "A propos...");
		setValueTo("visibilityOption", Locale.FRANCE.getLanguage(), "Afficher / Cacher la fenêtre principale");
		setValueTo("connectionOption", Locale.FRANCE.getLanguage(), "Lancer / Arreter la connexion");
				
		// -- En langue anglaise
		setValueTo("fileMenu", Locale.US.getLanguage(), "File");
		setValueTo("exitItem", Locale.US.getLanguage(), "Exit");
		setValueTo("editionMenu", Locale.US.getLanguage(), "Edition");
		setValueTo("languageMenu", Locale.US.getLanguage(), "Languages");
		setValueTo("frenchItem", Locale.US.getLanguage(), "French");
		setValueTo("englishItem", Locale.US.getLanguage(), "English");
		setValueTo("preferenceItem", Locale.US.getLanguage(), "Preference");
		setValueTo("ringoMenu", Locale.US.getLanguage(), "Ringo Space");
		setValueTo("connectionStatus", Locale.US.getLanguage(), "Connection Status");
		setValueTo("createNewAccount", Locale.US.getLanguage(), "Create a New Account");
		setValueTo("ringoWeb", Locale.US.getLanguage(), "Website");
		setValueTo("clientSpace", Locale.US.getLanguage(), "Client Space");
		setValueTo("helpMenu", Locale.US.getLanguage(), "Help");
		setValueTo("dialerUpdates", Locale.US.getLanguage(), "Program Updates");
		setValueTo("manualItem", Locale.US.getLanguage(), "User Manual");
		setValueTo("onlineItem", Locale.US.getLanguage(), "Ringo Online");
		setValueTo("aboutItem", Locale.US.getLanguage(), "About...");
		setValueTo("visibilityOption", Locale.US.getLanguage(), "Show / Hide Rhe Main Frame");
		setValueTo("connectionOption", Locale.US.getLanguage(), "Start / Stop Connection");
		
		//Affectation de la langue courante
		setCurrentLanguage(Locale.getDefault().getLanguage());
		
		
	}

	@Override
	protected void load(String language) throws ProgramException {}
	
}
