package cm.ringo.dialer.startup;

import java.util.Locale;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.ringo.dialer.interfaces.Dialer;

/**
 * 
 * Classes d'acces au module du Dialer
 * 
 * @author Charles Mouté
 * @version 1.0.1, 11/08/2010
 */
public class ModuleDialer {
	
	static{
		
		try {
			
			//Ajout du module en charge de la manipulation du dialer a notre application
			Application.addModule(Dialer.class);
			
			//Initialisation des modules de notre application
			Application.initModules();
			
			/*
			 * Affectation de la langue courante du SE a l'application. 
			 * En attendant de definir un module de preference, duquel on obtiendra 
			 * la langue de l'application.
			 */
			Application.setCurrentLanguage(Locale.getDefault().getLanguage());
			
		} catch (ProgramException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Permet d'obtenir l'instance du module Dialer
	 * @return L'instance du module Dialer
	 */
	public static Dialer getModule(){
		return Application.getModule(Dialer.class) ;
	}

}
