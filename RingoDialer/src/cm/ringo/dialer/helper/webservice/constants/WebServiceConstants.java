package cm.ringo.dialer.helper.webservice.constants;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.enterprise.software.helper.Helper;
import cm.enterprise.software.util.Resource;
import cm.ringo.dialer.helper.webservice.WebServiceHelper;

/**
 * Classe de regroupement de l'ensemble des constantes utilisées pour la manipulation d'un service.
 * 
 * @author Charles Mouté
 * @version 1.0.1, 12/12/2010
 */
public class WebServiceConstants extends Resource {

	public WebServiceConstants() {
		super();
				
		try {
			//Definitions des constantes utilisées par tous les web-services.
			this.definesParameter("login","password","errorCode");
			this.definesParameter("characterEncoding","concatCharacter","messagePath","prefixNameFile","extension");
			
			//Initialisation par defaut des parametres.
			this.setValueTo("login", "");
			this.setValueTo("password", "");
			this.setValueTo("errorCode", "");			
			this.setValueTo("characterEncoding", "UTF-8");
			this.setValueTo("concatCharacter", "&");
			this.setValueTo("messagePath", "cm/ringo/dialer/component/util/webservice/");
			this.setValueTo("prefixNameFile", "messages_");
			this.setValueTo("extension", "properties");
			//this.setValueTo("", "");
			
		} catch (ProgramException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Permet d'affecter un login qui sera utilise le cas echeant pour l'authentification avant utilisation
	 * d'un web-service.
	 * @param login Le nom utilisateur a utiliser.
	 */
	public void setLogin(String login){
		this.setValueTo("login", login);
	}
	
	/**
	 * Permet d'obtenir le nom utilisateur des web-services
	 * @return Le nom utilisateur a utiliser pour les authentifications des web-services
	 */
	public String getLogin() {
		return this.getValueOf("login") ;
	}
	
	/**
	 * Permet de savoir si le login est vide ou pas.
	 * @return true si le nom d'utilisateur est vide.
	 */
	public boolean loginIsEmpty() {
		return this.getValueOf("login")==null || this.getValueOf("login").isEmpty() ;
	}
	
	/**
	 * Indique le chemin d'acces aux messages des web-services. Notez que le dit fichier est suppose
	 * accessible uniquement en lecture et se trouvant par consequent a l'interieur du package de
	 * l'application, a l'abri des regards indiscrets.
	 * 
	 * @param path Chemin d'acces au fichier de paquetage.
	 */
	public void setMessagePath(String path) {
		String aux = path ;
		if(aux!=null && !aux.isEmpty() && !aux.endsWith("/")) aux.concat("/");
		this.setValueTo("messagePath", aux);
	}

	/**
	 * Permet d'obtenir le chemin d'acces des fichiers des messages des web-services.
	 * @return Le chemin d'acces des fichiers des messages des web-services.
	 */
	public String getMessagePath() {
		return this.getValueOf("messagePath");
	}
	
	/**
	 * Affecte le parametre comme prefixe des fichiers de langue des messages des web-services.
	 * Exemple : message_EN et message_FR auront comme prefixe message_ 
	 * @param prefixNameFile prefix commun des noms de fichiers
	 */
	public void setPrefixNameFile(String prefixNameFile){
		this.setValueTo("prefixNameFile", prefixNameFile);
	}
	
	/**
	 * Permet d'obtenir le prefix des noms des fichiers des messages.
	 * @return Le prefix de noms des fichiers des messages de web-services.
	 */
	public String getPrefixNameFile() {
		return this.getValueOf("prefixNameFile");
	}
	
	
	/**
	 * Affecte le parametre comme extension des fichiers des messages des web-services.
	 * @param extension Nom d'extension du fichier.
	 */
	public void setExtension(String extension) {
		this.setValueTo("extension", extension);
	}
	
	/**
	 * Permet d'obtenir le fichier des messages correspondant à la langue du systeme de l'utilisateur.
	 * @return Le chemin d'acces au fichier des messages des web-services en fonction de la langue utilisateur.
	 */
	public String getFilePath() {
		return this.getValueOf("messagePath")+this.getValueOf("prefixNameFile")+WebServiceHelper.getCurrentLanguage()+"."+this.getValueOf("extension");
	}
	
	public static void main(String[] arg) {
	
		try {
			Application.addResource(WebServiceConstants.class);
			WebServiceConstants r = Application.getResource(WebServiceConstants.class);
			String a = Helper.getInternalPropertiesOfFile("dialer.password.required", r.getFilePath());
			System.out.println(a);
			
		} catch (ProgramException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
