package cm.enterprise.software.test.multilingualresource.model2;

import java.util.Locale;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.util.MultilingualResource;

/**
 * Resource multilingue du model 2
 * @author Charles Moute
 * @version 1.0.1, 19/07/2010
 */
public class ResourceModel2 extends MultilingualResource {

	public ResourceModel2() throws ProgramException {
		super();
		/*
		 * assurez vous de donner des noms uniques a vos nom de ressource
		 * afin de prevenir toute confusion lors du chargement des parametres
		 * multilingues d'une ressource. Par defaut l'appel de super()
		 * initialise le nom de la resource au nom de la classe et le chemin
		 * a celui d'acces a la classe.
		 */
		  		
		//Definition des langues supportees par la ressource
		this.addSupportedLanguage(Locale.FRANCE.getLanguage());
		this.addSupportedLanguage(Locale.US.getLanguage());
		//Defintion de la langue par defaut
		this.setDefaultLanguage(Locale.US.getLanguage());
		//definition des parametres mutlilingues 
		this.definesParameter("valueLabel","validLabel");
		//Definition des valeurs des parametres multitlingues
		// -- En langue francaise
		this.setValueTo("valueLabel", Locale.FRANCE.getLanguage(), "Valeur Modele 2");
		this.setValueTo("validLabel", Locale.FRANCE.getLanguage(), "Validez");
		// -- En langue anglaise
		this.setValueTo("valueLabel", Locale.US.getLanguage(), "Value Of Model 2");
		this.setValueTo("validLabel", Locale.US.getLanguage(), "Apply");
		
		//Affectation de la langue courante de la ressource a la langue par defaut.
		this.setCurrentLanguage(this.getDefaultLanguage());
	}

	@Override
	protected void load(String language) throws ProgramException {
		/*
		 * La definition du corps de cette fonction n'est pas necessaire
		 * vu que les parmatres  multilingues ne sont pas stockes dans 
		 * des fichiers externes, mais sont plutot directement rediges a la main. 
		 */
	}

}
