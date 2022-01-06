package startup;

import cm.ringo.dialer.interfaces.Dialer;
import cm.ringo.dialer.startup.ModuleDialer;

/**
 * Represente la classe de lancement de notre application
 * @author Charles Moute
 * @version 1.0.1, 4/7/2010
 */
public class Startup{
	
	private static Dialer module  = null;
	static{
		
		//recuperation du module dialer
		module = ModuleDialer.getModule() ;
		
		//Initialisation de la vue que l'on souhaite utilisez
		module.installDefaultView();
	}
		
	public static synchronized void launch() {
		//Lancement de la vue par defaut
		module.showDefaultView();
	}
	
	/**
	 * Permet de savoir si une et une seul instance de l'application est permise
	 * @param args Arguments a passer en parametre.
	 * @return true Une seul instance de l'application est permise.
	 */
	public static boolean isOneInstance(String[] args){
		return true;
	}
	
	public static void main(String[] arg){		
		Startup.launch();
	}
}
