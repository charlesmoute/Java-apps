package cm.ringo.dialer.helper.webservice;

import java.util.Locale;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.ringo.dialer.helper.webservice.constants.WebServiceConstants;

/**
 * Classe regroupant l'ensemble des fonctions utilitaires pour la manipulation des 
 * webservices.
 * 
 * @author Charles Mouté
 * @vesion 1.0.1, 12/12/2010
 */
public class WebServiceHelper {
	
	/**
	 * Langue anglaise
	 */
	public static final String ENGLISH_LANGUAGE = "EN" ;
	
	/**
	 * Langue française.
	 */
	public static final String FRENCH_LANGUAGE = "FR" ;
	
	/**
	 * Separateur du nom du fichier et de l'extension
	 */
	public static final String NAME_SEPARATOR = "." ; 
	
	/**
	 * Permet d'obtenir la langue courante de l'utilisateur si elle est le français ou l'anglais.
	 * Si aucune de ces langues n'est celle de l'utilisateur la langue anglaise est retournée.
	 *  
	 * @return La langue courante de l'utilisateur.
	 */
	public static String  getCurrentLanguage() {
		return (Locale.getDefault().getLanguage().compareTo(Locale.FRENCH.getLanguage())==0) ? FRENCH_LANGUAGE : ENGLISH_LANGUAGE ;
	}

	
	/**
	 * Retourne la description du code erreur en parametre.
	 * @param code Code erreur dont ont la description.
	 * @return Description du code Erreur en parametre.
	 */
	public static String getErrorDescription(String code) throws ProgramException {
		//S'assurer que la ressource a été ajout
		if(!Application.instanceOfResourceExists(WebServiceConstants.class))
			throw new ProgramException("Instance WebServiceConstants Not Found In Application");
		WebServiceConstants r = Application.getResource(WebServiceConstants.class);
		return null ;
	}
	
}
