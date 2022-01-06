package cm.ringo.dialer.helper.connection.constants;

import java.net.URL;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.enterprise.software.helper.Helper;
import cm.enterprise.software.util.Resource;
import cm.ringo.dialer.interfaces.Connection;

/**
 * Constantes d'entree de connexion
 * 
 * @author Charles Moute
 * @version 1.0.1, 5/7/2010 
 */
public class ConnectionConstants extends Resource {

	/*-----------------------------------     Constantes pour le statut de la connexion     -----------------------------------*/
	/**
	 * Etat d'entree de connexion inactive
	 */
	public static final int INACTIVE_STATE =  0 ;
	
	/**
	 * Etat d'entree de connexion en cours d'appel
	 */
	public static final int DIALING_STATE = 1 ;
	
	/**
	 * Etat d'entree de connnexion active
	 */
	public static final int ACTIVE_STATE = 2 ;
	
	
	/*-----------------------------------     Constantes pour les notifications des vues au controleur     -----------------------------------*/
	
	
	/**
	 * La vue d'origine  transmet une requete de connexion de l'utilisateur au controleur 
	 */
	public static final int DIALUP_ACTION = 3 ;
	
	/**
	 * La vue d'origine transmet une demande de deconnexion en provenance de l'utilisateur
	 */
	public static final int HANGUP_ACTION = 4 ;
	
	/**
	 * Laisse le soin au controleur de choisir l'action approprie
	 */
	public static final int DIALUP_HANGUP_ACTION = DIALUP_ACTION | HANGUP_ACTION  ;
	
	public ConnectionConstants(){
		super();
		
		try {
			//Definition des parametres qui seront affecte plus tard
			this.definesParameter("activatePicture","dialingPicture","inactivePicture","logoPicture","logoSplash");
		} catch (ProgramException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * Construit le chemin d'acces au ressources images
	 * @return Le chemin d'acces aux ressources.
	 */
	private String buildImagePath(){
		String path = "cm/ringo/dialer/component/resource/connection/img/";
		if(Application.instanceOfModuleExists(Connection.class)){
			path = Application.getModule(Connection.class).getImagePath();
		}
		return path ;
	}
	
	/**
	 * Permet d'obtenir l'URL de l'image representant l'etat actif
	 * @return L'URL de l'image representant l'etat actif
	 */
	public URL getActivatePicture(){
		/*
		 * Avec le temps on aura setActivatePicture et getActivate retournera tout simplement la valeur
		 * pourra des lors etre indpts de celui definit par le module, bref on verra
		 */
		String path = buildImagePath();		
		this.setValueTo("activatePicture", path+"connected.png");
		return this.getClass().getClassLoader().getResource(this.getValueOf("activatePicture"));
	}
	
	/**
	 * Permet d'obtenir l'URL de l'image representant l'etat d'appel en cours
	 * @return L'URL de l'image representant l'etat d'appel en cours
	 */
	public URL getDialingPicture(){
		String path = buildImagePath();
		this.setValueTo("dialingPicture", path+"dialing.png");
		return Helper.getClassLoaderForResources().getResource(this.getValueOf("dialingPicture"));
	}
	
	/**
	 * Permet d'obtenir l'URL de l'image representant l'etat d'appel en cours
	 * @return L'URL de l'image representant l'etat d'appel en cours
	 */
	public URL getInactivePicture(){
		String path = buildImagePath();
		this.setValueTo("inactivePicture", path+"disconnected.png");
		return Helper.getClassLoaderForResources().getResource(this.getValueOf("inactivePicture"));
	}
	
	/**
	 * Permet d'obtenir l'URL du logo du module
	 * @return L'URL du logo de l'application
	 */
	public URL getLogoPicture(){
		String path = buildImagePath();
		this.setValueTo("logoPicture", path+"logo.png");
		return Helper.getClassLoaderForResources().getResource(this.getValueOf("logoPicture"));
		//return this.getClass().getClassLoader().getResource(this.getValueOf("logoPicture"));
	}
	
	/**
	 * Permet d'obtenir l'URL du logo Splash du module
	 * @return L'URL du logo Splash du module
	 */
	public URL getSplashLogoPicture(){
		String path = buildImagePath();
		this.setValueTo("logoSplash", path+"logo-ringo.png");
		return Helper.getClassLoaderForResources().getResource(this.getValueOf("logoSplash"));
	}
}
