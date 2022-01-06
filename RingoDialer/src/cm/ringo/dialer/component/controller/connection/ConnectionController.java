package cm.ringo.dialer.component.controller.connection;

import java.beans.PropertyChangeListener;

import cm.enterprise.software.component.controller.SuperController;
import cm.enterprise.software.component.model.base.Model;
import cm.enterprise.software.component.model.listener.Listener;
import cm.enterprise.software.exception.ProgramException;
import cm.ringo.dialer.component.model.base.connection.ConnectionModel;
import cm.ringo.dialer.component.model.base.connection.EntryModel;
import cm.ringo.dialer.component.view.connection.ConnectionDefaultView;
import cm.ringo.dialer.component.view.connection.ConnectionInfosView;
import cm.ringo.dialer.helper.connection.constants.ConnectionConstants;

/**
 * Represente le modele du controlleur du modele de connection.
 * @author Charles Moute 
 * @version 1.0.1, 5/7/2010
 */
public class ConnectionController extends SuperController {
		
	@SuppressWarnings("unchecked")
	public ConnectionController(ConnectionModel model) throws ProgramException {
		super(model);
		
		//Definition des classes de vues permises.
		this.addClassOfView(ConnectionDefaultView.class,ConnectionInfosView.class);
		
		//Definition des sous controleurs.
		this.addClassOfController(EntryController.class);
		
		//Instanciation du controlleur du sous modele
		this.installSubController(EntryController.class, model.getSubModel(EntryModel.class));
	}

	@Override
	protected <T extends Listener> void addListenerToModel(T listener) {
		
		if(listener instanceof ConnectionDefaultView){
			ConnectionModel m = (ConnectionModel)model;
			PropertyChangeListener l = (PropertyChangeListener) listener;
			// m.addDialProcessListener(l);
			m.addUsernameChangeListener(l);
			m.addPasswordChangeListener(l);
			m.addStateChangeListener(l);
			m.addRememberMeChangeListener(l);
			return;
		}
		
		if(listener instanceof ConnectionInfosView){
			ConnectionModel m = (ConnectionModel)model;
			PropertyChangeListener l = (PropertyChangeListener) listener;
			m.addStateChangeListener(l);
			return;
		}
		
		if(listener instanceof PropertyChangeListener){
			ConnectionModel m = (ConnectionModel)model;
			PropertyChangeListener l = (PropertyChangeListener) listener;
			// m.addDialProcessListener(l);
			m.addUsernameChangeListener(l);
			m.addPasswordChangeListener(l);
			m.addStateChangeListener(l);
			m.addRememberMeChangeListener(l);
		}
	}

	@Override
	public boolean isControllableModel(Model model) {
		return (model instanceof ConnectionModel);
	}

	/**
	 * Affecte le parametre comme nom d'utilisateur de la connexion
	 * @param username Nom utilisateur
	 */
	public void setUsername(String username){
		ConnectionModel model = (ConnectionModel)this.getModel();
		model.setUsername(username);
	}
	
	/**
	 * Permet d'obtenir le nom utilisateur associe a la connexion
	 * @return Le nom d'utilisateur associe a la connexion
	 */
	public String getUsername() {
		ConnectionModel model = (ConnectionModel)this.getModel();
		return model.getUsername();
	}
	
	/**
	 * Permet d'affecter le mot de passe a utiliser pour l'authebtification de l'utilisateur de la connexion
	 * @param password Mot de passe a utiliser pour l'authentification de l'utilisateur
	 */
	public void setPassword(String password){
		ConnectionModel model = (ConnectionModel)this.getModel();
		model.setPassword(password);
	}
	
	/**
	 * Permet d'obtenir le mot de passe associe a la connexion
	 * @return Le mot de passe associe a la connexion
	 */
	public String getPassword(){
		ConnectionModel model = (ConnectionModel)this.getModel();
		return model.getPassword();
	}
	
	/**
	 * Permet d'obtenir le mot de passe associe a la connexion
	 * @return Le mot de passe associe a la connexion
	 */
	public void setRememberMe(Boolean rememberMe){
		ConnectionModel model = (ConnectionModel)this.getModel();
		model.setRememberMe(rememberMe);
	}

	public boolean getRememberMe(){
		ConnectionModel model = (ConnectionModel)this.getModel();
		return model.getRememberMe();
	}	
	
	/**
	 * Affecte les parametres de la connexion
	 * @param username Nom utilisateur
	 * @param password Mot de passe utilisateur.
	 */
	public void setParameters(String username,String password){
		this.setUsername(username);
		this.setPassword(password);
	}
	
	/**
	 * Permet d'obtenir l'etat de la connexion. Les valeurs de retour
	 * <ul>
	 * 	<li>0 : connexion inactive</li>
	 * 	<li>1 : connexion en cours d'etablissement</li>
	 * 	<li>2 : connexion active</li>
	 * </ul>
	 * @return Permet d'obtenir l'etat de la connection
	 */
	public int getState(){
		ConnectionModel model = (ConnectionModel)this.getModel();
		return model.getState();
	}
	
	/**
	 * Permet de connaitre l'etat d'activite de la connexion.
	 * @return true si la connexion est active et false dans le cas contraire
	 */
	public boolean isActive(){
		ConnectionModel model = (ConnectionModel)this.getModel();
		return model.isActive();
	}
	
	@Override
	public void notifyEvent(Object param) {
		
		if(param instanceof Integer){
			
			Integer value = (Integer)param;
			ConnectionModel model = (ConnectionModel)this.getModel();
			
			if(value == ConnectionConstants.DIALUP_HANGUP_ACTION ){
				if(model.isDialing() || model.isActive()) model.hangup();
				else model.dial();
				return;
			}
			
			if(value == ConnectionConstants.DIALUP_ACTION){
				model.dial();
				return;
			}
			
			if(value == ConnectionConstants.HANGUP_ACTION){
				model.hangup();
				return;
			}
			
		}
		
	}

	@Override
	protected <T extends Listener> void removeListenerOfModel(T listener) {
		
		if(listener instanceof PropertyChangeListener){
			ConnectionModel m = (ConnectionModel)model;
			PropertyChangeListener l = (PropertyChangeListener) listener;
			m.removeDialProcessListener(l);
			m.removeUsernameChangeListener(l);
			m.removePasswordChangeListener(l);
			m.removeRememberMeChangeListener(l);
		}
	}

}
