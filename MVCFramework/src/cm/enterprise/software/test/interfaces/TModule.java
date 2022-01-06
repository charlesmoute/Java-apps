package cm.enterprise.software.test.interfaces;


import java.util.Locale;

import cm.enterprise.software.component.controller.Controller;
import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.interfaces.Module;
import cm.enterprise.software.test.controller.model1.ControllerOfModel1;
import cm.enterprise.software.test.model.model1.Model1;
import cm.enterprise.software.test.multilingualresource.model1.ResourceModel1;

/**
 * Classe de test du concept de module. L'on definira le
 * model 1 comme un composant à part entier. On definira
 * juste un parametre licence qui sera associe au module.
 * Mais vous pouvez en definir autant qu'il vous plaira.
 * Vous pouvez aller plus loin, en definissant des parametres
 * modifiables, permettant ainsi de parametre votre module
 * depuis l'exterieur. Le soin vous ait laisse de definir l'ensemble
 * des interfaces de communication, getter et setter, permettant ses
 * modifications et la lecture des valeurs de ces parametres.
 * 
 * @author Charles Moute
 * @version 1.0.1, 25/07/2010
 */
public class TModule extends Module {

	/**
	 * Permet d'obtenir l'instance pa defaut du contrleur du module courant
	 * @return L'instance par defaut du controleur
	 */
	public Controller getDefaultController() {
		return this.getController(ControllerOfModel1.class);
	}

	@Override
	public void initModule() {
		this.setModuleAuthor("Charles Moute");
		this.setModuleName("TModule");
		this.setAbout("TModule est un module de test de composant reutilisable. Le composant reutilisable ici considere est le model 1.");
			
		try {
			//Definition d'autres parametres
			this.definesParameter("license");
			//Affectation d'une valeur a ce parametre.
			this.setValueTo("license", "Tout droit reserve");
			/*
			 * Definition des langues que le module accepte.
			 * Etant donne que l'on se base sur model1, nous avions definis deux langues pour ce module
			 * Anglais et Francais. 
			 */
			this.addLanguage(Locale.FRANCE.getLanguage(),Locale.US.getLanguage());
			
			/*
			 * Definition des ressources de la ressource
			 */
			
			this.addResource(ResourceModel1.class);
			
			/*
			 * Definition la langue courante des ressources et du module. 
			 */
			this.setDefaultLanguage(Locale.US.getLanguage());
			
			/*
			 * Definition de la langue courante du module a la langue par defaut
			 */
			this.setCurrentLanguage(this.getDefaultLanguage());
			
			/*
			 * Definition des controleurs du module. Ici un seul controleur, car le model1 qui est a 
			 * l'origine de notre module et qui en fait definit notre module ne definit qu'un seul
			 * controleur.
			 */
			this.addController(ControllerOfModel1.class, new Model1());
		
		} catch (ProgramException e) { e.printStackTrace();	}
		
	}
	
	/**
	 * Permet d'obtenir la licence associee au module
	 * @return La licence associee au module
	 */
	public String getLicence(){
		return this.getValueOf("license");
	}

	
}
