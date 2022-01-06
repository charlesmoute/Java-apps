package cm.ringo.dialer.startup.connection;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import cm.ringo.dialer.interfaces.Connection;
import cm.ringo.dialer.startup.ModuleConnexion;

/**
 * Represente la classe de test d'une applcation utilisant le module
 * de connexion
 * 
 * @author Charles Moute
 * @version 1.0.1, 4/7/2010
 */
public class Startup{

	public Startup(){

			//Recuperation du module que l'on veut utiliser dans notre application
			Connection module = ModuleConnexion.getModule();
			
			//Installation/initiation de la vue infosView du module de connexion
			module.installInfosView();
			
			//Lancement de la vue Infos du module de connexion
			module.showInfosView();
			
			/*
			 * Installation et utilisation de la vue par defaut du module connexion
			 */
			JFrame frame = new JFrame("Test");
			
			//Installe la vue par defaut de la connexion
			module.installDefaultView();
			
			//Ajoute le contenu de la vue par defaut du module de connexion
			frame.add(module.getContentOfDefaultView());
			
			frame.setResizable(false);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocation(50, 50);
			frame.setVisible(true);
	}
	
	public static void main(String[] arg){		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new Startup();
			}
		});
		
	}
	
}
