package cm.enterprise.software.test.multilingualresource.divers;

import java.util.Locale;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.ExceptionResource;
import cm.enterprise.software.util.SuperMultilingualResource;

/**
 * Classe de test du concept SuperMultilingualRessource.
 * Cette ressource est defini a partir des ressources ExceptionResource et MR2.
 * La ressource MR lui sera attribue apres qu'elle soit configurer lors du test de 
 * la super ressource et on ajoutera une champ value comme parametre multilingue de 
 * la super ressource. 
 * @author Charles Moute
 * @version 1.0.1, 22/07/2010
 */
public class SuperMR extends SuperMultilingualResource {

	/**
	 * Instancie un objet de SuperMR
	 * @throws ProgramException
	 */
	@SuppressWarnings("unchecked")
	public SuperMR() throws ProgramException {
		super();
		
		//Definition des langues que supporte la super ressource
		this.addSupportedLanguage(Locale.FRANCE.getLanguage());
		this.addSupportedLanguage(Locale.US.getLanguage());
		
		/*
		 * Definition des ressources constituants la super ressource.
		 */
		this.addClassOfMultilingualResource(ExceptionResource.class,MR2.class);
		
		/*
		 * Instanciation des ressources constituants la super ressource.
		 * Ceci aurait pu se faire à l'exterieur, et peut encore se faire
		 * a condition que vous effaciez les instances deja definies. 
		 */
		this.addSubMultilingualResource(ExceptionResource.class);
		this.addSubMultilingualResource(MR2.class);
		
		/*
		 * Definition de la langue par defaut. Ne jamais oublie de 
		 * la definir, apres la definition des langues supportees 
		 * par la ressource et avant la definition de la langue courante.
		 */
		this.setDefaultLanguage(Locale.US.getLanguage());
		
		/*
		 * On definit le langage courant comme etant la langue par defaut. 
		 * On le fait a ce niveau pour que les sous ressources puissent
		 * considerer que la langue courante, n'est pas leur langue par
		 * defaut mais plutot la langue par defaut de la super ressource
		 * les contenants. Ceci n'empeche que si la langue par defaut de
		 * la super ressource n'est pas prise en compte, c'est la langue par
		 * defaut de l'application qui demeure.
		 */
		this.setCurrentLanguage(this.getDefaultLanguage());
		
		/**
		 * La super ressource est prête est operationnelle, avant de pouvoir l'utiliser a tou pour de champ
		 * vous devez avant tout autre chose configurer votre application pour qu'elle  la prenne en charge.
		 * ceci en faisant : Helper.addMultilingualResource(SuperMR.class); et pour obtenir l'instance manipuler
		 * par votre application : Helper.getMultilingualResource(SuperMR.class);
		 */
	}

	@Override
	protected void load(String language) throws ProgramException {
		this.loadSubMultilingualRessource(language);
	}

}
