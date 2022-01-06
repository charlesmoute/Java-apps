package cm.ringo.dialer.startup;

import cm.ringo.dialer.interfaces.Dialer;

/**
 * Represente la classe de test d'une application utilisant le module Dialer
 * @author Charles Moute
 * @version 1.0.1, 4/7/2010
 */
public class Startup{

	public Startup(){
		
		//recuperation du module dialer
		Dialer module = ModuleDialer.getModule() ;
		
		//Initialisation de la vue que l'on souhaite utilisez
		module.installDefaultView();
		
		//Lancement de la vue par defaut
		module.showDefaultView();
		
		//System.out.println(module);
	}
	
	public static void main(String[] arg){		
		new Startup();
	}
	
}
