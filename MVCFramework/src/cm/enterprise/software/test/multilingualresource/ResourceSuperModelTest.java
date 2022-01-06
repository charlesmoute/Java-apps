package cm.enterprise.software.test.multilingualresource;

import java.util.Locale;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.enterprise.software.test.multilingualresource.model1.ResourceModel1;
import cm.enterprise.software.test.multilingualresource.model2.ResourceModel2;
import cm.enterprise.software.util.SuperMultilingualResource;

/**
 * Resource du super modele de test. Il est vrai que l'on pourrait penser
 * qu'il sera de bonne augure pour l'application que la ressource
 * du super model definisse les ressources de ses sous models, ce
 * serait avantageux si les sous models en question n'ont pas de
 * vue qui font appel directement a leurs ressources.
 * Dans notre cas, se serait un desavantage, et impliquerait même
 * d'aller retoucher les vues des models que l'on utilse dans 
 * notre SuperModel; ce qui n'est pas le but du Application.
 * Alors dans notre cas La super ressource du Model definira
 * les instances des ressources de ses models qu'elle manipule
 * directement au niveau de l'application et nous on aura qu'a definir
 * la resource nous mêmes. Il le fera unique si la ressource n'existe pas encore.
 * @author Charles Moute.
 * @version 1.0.1, 19/07/2010
 */
public class ResourceSuperModelTest extends SuperMultilingualResource {

	public ResourceSuperModelTest() throws ProgramException {
		super(); // Permet l'initialisation automatique de name et de path
		
		//Definition des langues supportees par la super ressource
		addSupportedLanguage(Locale.FRANCE.getLanguage());
		addSupportedLanguage(Locale.US.getLanguage());

		/*
		 * Vu que chacune des vues des models que l'on utilise font directement appel aux ressources
		 * de leur modele et que l'on souhaite utiliser ses vues sans aucune redefinition.
		 * Nous allons ajoutez directement les ressources à l'application si elle n'existe pas
		 * encore. Nous definissons ces ressources avant l'utilisation de setDefaultLanguage 
		 * et setCurrentLanguage, pour que lors de l'utilisation de ces fonctions que 
		 * les sous ressources de la super ressource puisse etre prise en compte dans l'affectation
		 * de la langue par defaut et de la langue courante.
		 */
		 if(!Application.instanceOfResourceExists(ResourceModel1.class))			 
			 Application.addResource(ResourceModel1.class);
		 
		 if(!Application.instanceOfResourceExists(ResourceModel2.class))
			 Application.addResource(ResourceModel2.class);
		
		/*
		 * Avant de definir la langue par defautt nous devons definir les
		 * langues supportees par la ressource est la langue par defaut devrait faire
		 * parti de ces langues, si ce n'est pas le cas, aucune affectation ne sera
		 * faite, et la langue par defaut conservera sa valeur precedente, ici null.
		 */	
		setDefaultLanguage(Locale.US.getLanguage());
			
		/*
		 * Avant de definir les parametres multilingues
		 * d'une ressource, vous devez definir la langue par defaut de
		 * la ressource, sinon aucun des parametres que vous definirez 
		 * ne saurons prise en compte
		 */
		//definition des parametres mutlilingues de la ressource
		definesParameter("attributeLabel","validateLabel");
		//Definition des valeurs des parametres multitlingues
		// -- En langue francaise
		setValueTo("attributeLabel", Locale.FRANCE.getLanguage(), "Attribut de "+this.name);
		setValueTo("validateLabel", Locale.FRANCE.getLanguage(), "Validez Tout");
		
		// -- En langue anglaise
		setValueTo("attributeLabel", Locale.US.getLanguage(), "Attribute Of "+this.name);
		setValueTo("validateLabel", Locale.US.getLanguage(), "Apply All");
		
		/*
		 * Si les vues des models de notre sous modele ne faisant pas appel, directement, aux ressources
		 * de leur modele, ou si on avait pas l'intention d'utiliser ses vues mais plutot d'en redefinir
		 * d'autres avec notre propre conception alors les instructions ci dessous commentees
		 * deviendrait valable et celle de dessus pourrait toujours subsister.
		 */
		
		/*
		 * Vu que le SuperModelTest a les model Model1 et Model2 comme sous modeles.
		 *  Sa ressource est definie comme combinaison des ressources de ces deux sous 
		 *  model ResourceModel1 et ResourceModel2
		 */
				
		//On definit les classes des sous ressources prises en compte par la super ressource
		//this.addClassOfMultilingualResource(ResourceModel1.class,ResourceModel2.class);
		
		/*
		 * On instancie les ressources composants la super ressource.
		 * Vu que les constructeurs de ResourceModel1 et ResourceModel2 n'ont pas de parametres
		 * il n'y a pas necessite de les ajouter.
		 * Mais si on voulait on autrait pu ecrire :
		 * this.addSubMultilingualResource(ResourceModel1.class, new Object[]{});
		 * this.addSubMultilingualResource(ResourceModel2.class, new Object[]{}); 
		 */		
		//this.addSubMultilingualResource(ResourceModel1.class);
		//this.addSubMultilingualResource(ResourceModel2.class);
		
		/* 
		 *  Vu que tous les parametres multilingues ont ete definis, nous pouvons
		 *  maintenant definir la langue courante.
		 *  NB: si vous le faites avant vous devrez encore pour chacune des ressources
		 *  definies appelez les fonctions ci-dessous. Definissez toujours le langage par
		 *  defaut avant le langage courant ceci dans le but que si le langage defini
		 *  comme courant n'est pas prise en charge que ce soit le langage par defaut qui soit
		 *  pris en compte. 
		 */
		setCurrentLanguage(this.getDefaultLanguage());
		
	}

	@Override
	protected void load(String language) throws ProgramException {
		this.loadSubMultilingualRessource(language);
		
	}
}
