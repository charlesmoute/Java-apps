package cm.ringo.dialer.startup;


import java.util.Locale;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.ringo.dialer.interfaces.Connection;

/**
 * Classe d'acces au Module de connexion. Permet l'utilisation en standalone du module de connexion
 * 
 * @author Charles Mouté
 * @version 1.0.1, 11/08/2010
 */
public class ModuleConnexion {

	static{

		try {
			
			//Ajout du module en charge de la manipulation de la connexion
			Application.addModule(Connection.class);
			
			//Initialisation des modules de notre application
			Application.initModules();
			
			//Affectation de la langue courante du SE a l'application, en attendant de definir un module de preference
			Application.setCurrentLanguage(Locale.getDefault().getLanguage());
			
		} catch (ProgramException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Permet d'obtenir l'instance du module de connexion
	 * @return L'instance du module de connexion
	 */
	public static Connection getModule(){
		return Application.getModule(Connection.class) ;
	}
}
