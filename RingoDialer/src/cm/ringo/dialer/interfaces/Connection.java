package cm.ringo.dialer.interfaces;

import java.beans.PropertyChangeListener;
import java.util.Locale;

import javax.swing.JPanel;
import javax.swing.JWindow;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.interfaces.Module;
import cm.ringo.dialer.component.controller.connection.ConnectionController;
import cm.ringo.dialer.component.model.base.connection.ConnectionModel;
import cm.ringo.dialer.component.view.connection.ConnectionDefaultView;
import cm.ringo.dialer.component.view.connection.ConnectionInfosView;
import cm.ringo.dialer.helper.connection.constants.ConnectionConstants;
import cm.ringo.dialer.helper.connection.multilingual.ConnectionMultilingualResource;
import cm.ringo.dialer.helper.connection.multilingual.EntryMultilingualResource;

/*
 * avec le temps definir les concepts gavitant autour du module plus parametrables de
 * l'exterieur, dont redefinir les fonctions de tel sorte que l'on ait des valeurs par
 * defaut on debut, mais que les fonctions se refere tjrs au config
 * exterieur.
 */


/**
 * Represente le composant en charge de la manipulation de la connection
 * @author Charles Moute
 * @version 1.0.1, 5/7/2010
 */
public class Connection extends Module {

	public Connection(){
				
		try {
			
			// Definition de quelques parametres pour le module
			this.definesParameter("copyright","version","imagePath");
			
			//Initialisation des parmetres au valeurs par defauts
			constantInitialization();
			this.setResourcePath("cm/ringo/dialer/component/resource/connection");
			
			this.setValueTo("imagePath", this.getResourcePath()+"img/");
			
			//Definition des langues prises en compte par le module			
			this.addLanguage(Locale.FRENCH.getLanguage(),Locale.US.getLanguage());
			
			//Definition des ressources Constantes
			this.addResource(ConnectionConstants.class);
			
			//Definition des ressources multilingues
			this.addResource(EntryMultilingualResource.class);
			this.addResource(ConnectionMultilingualResource.class);
			
			//Definition du controleur du dialer
			this.addController(ConnectionController.class, new ConnectionModel());
			
			//Definition de la langue par defaut de la ressource.
			this.setDefaultLanguage(Locale.ENGLISH.getLanguage());
			
		} catch (ProgramException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initModule() {
		
		try {
			
			//Initialisation des parametres selon vos configurations
			constantInitialization();
			//obligation que les images soit dans un dossier  img de notre chemin de ressource
			this.setValueTo("imagePath", this.getResourcePath()+"img/");

			//Definition de la langue courante a la langue par defaut.
			this.setCurrentLanguage(this.getDefaultLanguage());
			
						
		} catch (ProgramException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet d'obtenir le chemin d'acces aux ressources images du module
	 * @return Le chemin d'acces au dossier des ressources images du module
	 */
	public String getImagePath(){ return this.getValueOf("imagePath"); }
	
	/**
	 * Etablit une connexion
	 */
	public void dial(){
		ConnectionController controller = this.getController(ConnectionController.class);
		controller.notifyEvent(ConnectionConstants.DIALUP_ACTION);
	}
	
	/**
	 * Interrompe la connexion
	 */
	public void hangup(){
		ConnectionController controller = this.getController(ConnectionController.class);
		controller.notifyEvent(ConnectionConstants.HANGUP_ACTION);
	}
	
	
	/**
	 * Selon que la connexion est active ou non, elle opere l'operation inverse a l'etat d'activite de la connexion.
	 */
	public void dialHangup(){
		ConnectionController controller = this.getController(ConnectionController.class);
		controller.notifyEvent(ConnectionConstants.DIALUP_HANGUP_ACTION);
	}
	
	/**
	 * Affecte le parametre comme nom d'utilisateur associe a la connexion
	 * @param username Nom d'utilisateur de la connexion
	 */
	public void setUsername(String username){
		this.getController(ConnectionController.class).setUsername(username);
	}
	
	/**
	 * Permet d'obtenir le nom de l'utilisateur de la connexion
	 * @return Le nom de l'utilisateur de la connexion
	 */
	public String getUsername(){
		return this.getController(ConnectionController.class).getUsername();
	}
	
	/**
	 * Permet d'affecter un mot de passe a utiliser pour l'authentification de l'utilisateur de la connexion
	 * @param password Mot de passe de la connexion
	 */
	public void setPassword(String password){
		this.getController(ConnectionController.class).setPassword(password);
	}
	
	/**
	 * Permet d'obtenir le mot de passe utilise pour l'authentification
	 * @return Mot de passe utilise pour l'authentification de l'utilisateur de la connexion
	 */
	public String getPassword(){
		return this.getController(ConnectionController.class).getPassword();
	}
	
	/**
	 * Permet d'affecter les parametres de la connexion d'un appel.
	 * @param username Nom d'utilisateur de la connexion
	 * @param password Mot de passe pour l'authentification de l'utilisateur de la connexion
	 */
	public void setParameters(String username, String password){
		this.getController(ConnectionController.class).setParameters(username, password);
	}
	
	/**
	 * Permet de savoir si la connexion est active ou non.
	 * @return Permet de savoir si la connexion est active ou non.
	 */
	public boolean isActive(){
		return this.getController(ConnectionController.class).isActive();
	}
	
	/**
	 * Ajoute un ecouteur du changement de nom utilisateur
	 * @param listener Ecouteur a ajouter.
	 */
	public void addUsernameChangeListener(PropertyChangeListener listener){
		ConnectionModel model = (ConnectionModel) this.getController(ConnectionController.class).getModel();
		model.addUsernameChangeListener(listener);
	}
	
	/**
	 * Efface l'ecouteur du changement de nom utilisateur
	 * @param listener Ecouteur a effacer
	 */
	public void removeUsernameChangeListener(PropertyChangeListener listener){
		ConnectionModel model = (ConnectionModel) this.getController(ConnectionController.class).getModel();
		model.removeUsernameChangeListener(listener);
	}
	
	/**
	 * Ajoute un ecouteur du changement de mot de passe
	 * @param listener Ecouteur a ajouter.
	 */
	public void addPasswordChangeListener(PropertyChangeListener listener){
		ConnectionModel model = (ConnectionModel) this.getController(ConnectionController.class).getModel();
		model.addPasswordChangeListener(listener);
	}
	
	/**
	 * Efface l'ecouteur du changement de mot de passe
	 * @param listener Ecouteur a effacer
	 */
	public void removePasswordChangeListener(PropertyChangeListener listener){
		ConnectionModel model = (ConnectionModel) this.getController(ConnectionController.class).getModel();
		model.removePasswordChangeListener(listener);
	}
	
	/** remember me */
	public void addRememberMeChangeListener(PropertyChangeListener listener){
		ConnectionModel model = (ConnectionModel) this.getController(ConnectionController.class).getModel();
		model.addRememberMeChangeListener(listener);
	}

	public void removeRememberMeChangeListener(PropertyChangeListener listener){
		ConnectionModel model = (ConnectionModel) this.getController(ConnectionController.class).getModel();
		model.removeRememberMeChangeListener(listener);
	}	
	
	/**
	 * Ajoute un ecouteur du changement d'etat de la connexion
	 * @param listener Ecouteur a ajouter.
	 */
	public void addStateChangeListener(PropertyChangeListener listener){
		ConnectionModel model = (ConnectionModel) this.getController(ConnectionController.class).getModel();
		model.addStateChangeListener(listener);
	}
	
	/**
	 * Efface l'ecouteur du changement d'etat en parametre
	 * @param listener Ecouteur a effacer
	 */
	public void removeStateChangeListener(PropertyChangeListener listener){
		ConnectionModel model = (ConnectionModel) this.getController(ConnectionController.class).getModel();
		model.removeStateChangeListener(listener);
	}
	

	/**
	 * Permet d'initialiser la vue par defaut. Cette operation permettra
	 * l'appel aux differentes fonctions XXXDefaultView() et defaultViewXXX()
	 */
	public void installDefaultView(){
		try {
			ConnectionController controller = this.getController(ConnectionController.class);
			controller.installView(ConnectionDefaultView.class, controller);
		} catch (ProgramException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet la suppression de la vue par defaut. Cette operation invalidera
	 * l'appel aux differentes fonctions XXXDefaultView() et defaultViewXXX()
	 */
	public void uninstallDefaultView(){
		ConnectionController controller = this.getController(ConnectionController.class);
		controller.uninstallView(ConnectionDefaultView.class);
	}
	
	/**
	 * Permet de rendre visible la vue par defaut.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a initDefaultView et jamais a uninstallDefaultView() apres le precedent 
	 * appel.
	 * @see #installDefaultView()
	 * @see #uninstallDefaultView()
	 */
	public void showDefaultView(){
		ConnectionController controller = this.getController(ConnectionController.class);
		//Juste pour prevenir d'eventuels changement de langue
		controller.getContentOf(ConnectionDefaultView.class);
		controller.showView(ConnectionDefaultView.class);
	}
	
	/**
	 * Permet de rendre invisible la vue par defaut.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a initDefaultView et jamais a uninstallDefaultView() apres le precedent 
	 * appel.
	 * @see #installDefaultView()
	 * @see #uninstallDefaultView()
	 */
	public void hideDefaultView(){
		ConnectionController controller = this.getController(ConnectionController.class);
		controller.hideView(ConnectionDefaultView.class);
	}

	/**
	 * Permet les interactions avec la vue par defaut.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a initDefaultView et jamais a uninstallDefaultView() apres le precedent 
	 * appel.
	 * @see #installDefaultView()
	 * @see #uninstallDefaultView()
	 */
	public void enableDefaultView(){
		ConnectionController controller = this.getController(ConnectionController.class);
		controller.enableView(ConnectionDefaultView.class);
	}
	
	/**
	 * Annule les interactions avec la vue par defaut.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a initDefaultView et jamais a uninstallDefaultView() apres le precedent 
	 * appel.
	 * @see #installDefaultView()
	 * @see #uninstallDefaultView()
	 */
	public void disableDefaultView(){
		ConnectionController controller = this.getController(ConnectionController.class);
		controller.disableView(ConnectionDefaultView.class);
	}
	
	/**
	 * Permet de connaitre l'etat de visibilite de la vue par defaut.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a initDefaultView et jamais a uninstallDefaultView() apres le precedent 
	 * appel.
	 * @see #installDefaultView()
	 * @see #uninstallDefaultView()
	 */
	public boolean defaultViewIsVisible(){
		ConnectionController controller = this.getController(ConnectionController.class);
		return controller.getViewOfClass(ConnectionDefaultView.class).isVisible();
	}
	
	/**
	 * Permet de connaitre l'etat d'interactivite de la vue par defaut. Si elle est permise ou non.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a initDefaultView et jamais a uninstallDefaultView() apres le precedent 
	 * appel.
	 * @see #installDefaultView()
	 * @see #uninstallDefaultView()
	 */
	public boolean defaultViewIsEnable(){
		ConnectionController controller = this.getController(ConnectionController.class);
		return controller.getViewOfClass(ConnectionDefaultView.class).isEnable();
	}
	
	/**
	 * Permet d'obtenir le contenu de la vue par defaut.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a initDefaultView et jamais a uninstallDefaultView() apres le precedent 
	 * appel.
	 * @see #installDefaultView()
	 * @see #uninstallDefaultView()
	 * @return Le contenu de la vue par defaut.
	 */
	public JPanel getContentOfDefaultView(){
		ConnectionController controller = this.getController(ConnectionController.class);
		if (controller.viewInstanceExists(ConnectionDefaultView.class)){
			return (JPanel) controller.getContentOf(ConnectionDefaultView.class);
		}
		return null;
	}
	
	/**
	 * Permet d'initialiser la vue des parametres de connexion. Cette operation permettra
	 * l'appel aux differentes fonctions XXXInfosView() et infosViewXXX()
	 */
	public void installInfosView(){
		try {
			ConnectionController controller = this.getController(ConnectionController.class);
			controller.installView(ConnectionInfosView.class, controller);
		} catch (ProgramException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet la suppression de la vue des parametres de connexion. Cette operation invalidera
	 * l'appel aux differentes fonctions XXXInfosView() et infosViewXXX()
	 */
	public void uninstallInfosView(){
		ConnectionController controller = this.getController(ConnectionController.class);
		controller.uninstallView(ConnectionInfosView.class);
	}
	
	/**
	 * Permet de rendre visible la vue des parametres de connexion.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a installInfosView et jamais a uninstallInfosView() apres le precedent 
	 * appel.
	 * @see #installInfosView()
	 * @see #uninstallInfosView()
	 */
	public void showInfosView(){
		ConnectionController controller = this.getController(ConnectionController.class);
		//Juste pour prendre en compte le chargement eventuel de langue.
		controller.getContentOf(ConnectionInfosView.class);
		controller.showView(ConnectionInfosView.class);
	}
	
	/**
	 * Permet de rendre invisible la vue des parametres de connexion.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a installInfosView et jamais a uninstallInfosView() apres le precedent 
	 * appel.
	 * @see #installInfosView()
	 * @see #uninstallInfosView()
	 */
	public void hideInfosView(){
		ConnectionController controller = this.getController(ConnectionController.class);
		controller.hideView(ConnectionInfosView.class);
	}

	/**
	 * Permet de permettre les interactions avec la vue des parametres de connexion.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a installInfosView et jamais a uninstallInfosView() apres le precedent 
	 * appel.
	 * @see #installInfosView()
	 * @see #uninstallInfosView()
	 */
	public void enableInfosView(){
		ConnectionController controller = this.getController(ConnectionController.class);
		controller.enableView(ConnectionInfosView.class);
	}
	
	/**
	 * Permet d'annuler les interactions avec la vue des parametres de connexion.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a installInfosView et jamais a uninstallInfosView() apres le precedent 
	 * appel.
	 * @see #installInfosView()
	 * @see #uninstallInfosView()
	 */
	public void disableInfosView(){
		ConnectionController controller = this.getController(ConnectionController.class);
		controller.disableView(ConnectionInfosView.class);
	}
	
	
	/**
	 * Permet de connaitre l'etat de visibilite de la vue des parametres de connexion.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a installInfosView et jamais a uninstallInfosView() apres le precedent 
	 * appel.
	 * @see #installInfosView()
	 * @see #uninstallInfosView()
	 * @return true si la vue est visible et false dans le cas contraire
	 */
	public boolean infosViewIsVisible(){
		ConnectionController controller = this.getController(ConnectionController.class);
		return controller.getViewOfClass(ConnectionInfosView.class).isVisible();
	}
	
	/**
	 * Permet de savoir si les interactions avec la vue des parametres de connexion sont permises ou non.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a installInfosView et jamais a uninstallInfosView() apres le precedent 
	 * appel.
	 * @see #installInfosView()
	 * @see #uninstallInfosView()
	 * @return true si les interactions avec la vue sont permise et false dans le cas contraire
	 */
	public boolean infosViewIsEnable(){
		ConnectionController controller = this.getController(ConnectionController.class);
		return controller.getViewOfClass(ConnectionInfosView.class).isEnable();
	}
	
	/**
	 * Permet d'obtenir le contenu de la vue des parametres de connexion
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a installInfosView et jamais a uninstallInfosView() apres le precedent 
	 * appel.
	 * @return La vue des parametres de connexion
	 */
	public JWindow getContentOfInfosView(){
		ConnectionController controller = this.getController(ConnectionController.class);
		if (controller.viewInstanceExists(ConnectionInfosView.class)){
			return (JWindow) controller.getContentOf(ConnectionInfosView.class);
		}
		return null;
	}
	
	/**
	 * Permet d'iniatiliser certains champs, a des valeurs supposes statiques
	 */
	private void constantInitialization(){
		//Affectation des valeurs statique du module
		this.setModuleAuthor("Charles Moute");
		this.setModuleName("Connexion");
		this.setAbout("Module de connexion a un serveur PPPOE");
		
		//Affectation des valeurs au parametre definies
		this.setValueTo("copyright", "Tout droit reservé a Ringo S.A");
		this.setValueTo("version", "1.0.1");
	}

	public void setRememberMe(boolean rememberMe) {
		ConnectionModel model = (ConnectionModel) this.getController(ConnectionController.class).getModel();
		model.setRememberMe(rememberMe);
	}
	
	public boolean getRememberMe(){
		
		ConnectionModel model = (ConnectionModel) this.getController(ConnectionController.class).getModel();
		return model.getRememberMe();
	}
}
