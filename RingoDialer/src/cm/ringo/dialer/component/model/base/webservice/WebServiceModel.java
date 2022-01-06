package cm.ringo.dialer.component.model.base.webservice;

import cm.enterprise.software.component.model.base.Model;

/**
 * Modéle abstract d'un web-service.
 * @author Charles Mouté
 * @version 1.0.1, 12/12/2010
 */
public abstract class WebServiceModel extends Model {
	
	/**
	 * Representent l'url d'accés au service
	 */
	protected String serviceURL = null;
	
	/**
	 * Code erreur emis par le webservice
	 */
	protected String errorCode = "";
	/**
	 * Methode a definir au niveau de chaque web-service 
	 */
	public abstract void executeService();
	
	/**
	 * Affecte l'url d'accés au service
	 * @param url Chaine de caractére representant l'url du service.
	 */
	public void setServiceURL(String serviceURL){
		this.serviceURL = serviceURL ;
	}

	/**
	 * Permet d'obtenir l'url d'accés au service.
	 * @return L'url d'accés services au service.
	 */
	public String getServiceURL() {
		return this.serviceURL ;
	}
	
	/**
	 * Permet d'obtenir le code erreur emis par un web-service.
	 * @return
	 */
	public String getErrorCode() {
		return this.errorCode ;
	}
	
	/**
	 * Permet d'obtenir le message associe au code d'erreur emis pas le webservice.
	 * @return La description du code erreur retourne par le webservice.
	 */
	public String getErrorText() {
		//Ici on doit définir l'appel au fichier de message à lisant directement
		//l'erreur dans le fichier adéquate.
		return "";
	}
}
