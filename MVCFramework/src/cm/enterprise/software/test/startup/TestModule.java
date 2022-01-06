package cm.enterprise.software.test.startup;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.enterprise.software.test.interfaces.TModule;
import cm.enterprise.software.test.view.model1.View1Model1;


/**
 * Classe de test du module representant le super modele SuperModelTest comme un 
 * composant unique. Ici nous montrerons comme sans grande connaissance de SuperModelTest
 * et de tous les principes d'implementations de MVC Application l'on manipule SuperModelTest 
 * qui est desormais un composant autonome. Bien évidement, l'utilisateur doit avoir une 
 * documentation détaillé de vous. Vous pouvez encore ajoutez d'autres fonctionnalites a 
 * la classe representant le module, a vous de voire.
 * 
 * Ainsi TesModule, represente une application on montrera comme, ajoutez le module dans
 * l'application courante si ce souhaite utilisez le framework accompagnant le module.
 * Sinon on vous montrera comment utiliser directement le module avec simplement l'instance
 * du dit module.
 * 
 * @author Charles Moute
 * @version 1.0.1, 25/07/2010
 */
public class TestModule extends Application {

	public static void main(String[] arg) throws ProgramException{
		
		/*
		 *  Exemple d'ajout d'un module a l'application courante.
		 *  Si vous n'utilisez pas le framework MVC, vous devez
		 *  instancier directement la classe correspondante au module
		 *  ou composant externe a l'application.
		 *  Ceci en faisant par exemple : TModule module = new TModule();
		 *  au lieu de Helper.addModule(TModule.class);
		 *  et en remplacant toute les instances de Helper.getModule(TModule.class).XXX
		 *  par module.XX 
		 */
		addModule(TModule.class);
		
		
		/*
		 * On peut initialiser tous les modules de l'application avec
		 * Helper.initModules();  ou dans notre cas les initialiser un a un.
		 */
		
		getModule(TModule.class).initModule();
		
		System.out.println("------------ Infos du module -------------");
		System.out.println(getModule(TModule.class));
		System.out.println(" License = "+getModule(TModule.class).getLicence());
		
		TModule module = getModule(TModule.class);
		
		/*
		 * Nous allons instancier une vue de ce composant, n'oubliez pas que c'est a vous,
		 * au travers d'une documentation de votre module de dire quel sont les models
		 * les vues disponibles et comment les manipuler. Dans notre hate nous ne l'avons pas
		 * fait, et n'avons pas definit une autre methode d'acces, que celle du framework, mais
		 * en temps normale, vous devez definir un temps de methode dans votre modtude de tel sorte
		 * que vous encapsulez le principe d'utilisation du Application. L'utilisateur n'aura plus a connaitre
		 * les bases de MVCFramework, mais juste les entrees que vous lui mettrez a disposition.
		 * Dans notre cas nous avons considerez que vous connaissez les concepts de bases de MVCFramework
		 * et sa logique. 
		 */
		
		// Pour avoir a instancier une vue existante pour le modele represente par le module
		module.getDefaultController().installView(View1Model1.class, module.getDefaultController());
		//On rend la vue recement cree visible au cas ou
		module.getDefaultController().showView(View1Model1.class);
		
		// Test de la vue
		JFrame frame = new JFrame("Vue 1 Model 1");
		//Ajout de la vue du module
		frame.getContentPane().add((JPanel)module.getDefaultController().getContentOf(View1Model1.class));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true); 
		  
		/*
		 * La vue du model1 etant un JPanel, vous devez encore l'introduire dans un JFrame
		 * Mais supposez que vous avez une vue XFrame parmi les vues de votre modèle alors
		 * pour la rendre visible.La ligne suivante est utile :
		 * module.getDefaultController().showView(XFrame.class);
		 * Si dans votre controller, vous avez ajoutez d'autres possibilites, tout ces possibilites
		 * deviennet accessible par module.getDefaultController(). Vous pouvez abstraite tout cette
		 * complexite du framework, en definissant directement des methodes dans votre module
		 * du genre pour afficher la vue XFrame, vous ferez :
		 * module.showXFrame(); et pour l'instancier vous pouvez creeer une fonction  createXFrame() dans
		 * votre module et l'acces devriendrait module.createXFrame(). Trop de possibilites vous sont offertes
		 * pour que nous puissions les lister.  
		 */
		
		
		
		/*
		 * Vous pouvez faire tout ce qui vous passe par la tete. Regarder les quelques possibilites
		 * developpe dans TestModule. Et essayez de vous dire que c'est TestModule qui est votre composant
		 * a la place de model, imaginez comment vous pouvez manipuler le composant sans en connaitre 
		 * grand chose. Magnifique 
		 */
		
		//Suppression du dit Module de l'application courante
		//Helper.removeModule(TModule.class);
		//System.out.println("Instance de TModule exists = "+Helper.instanceOfModuleExists(TModule.class));
	}

}
