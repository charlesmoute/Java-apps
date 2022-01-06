package cm.enterprise.software.test.multilingualresource.model1;

import java.util.Locale;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.util.MultilingualResource;

/**
 * Classe des parametres multilingues de la ressource model1
 * @author Charles Moute
 * @version 1.0.1, 19/07/2010
 */
public class ResourceModel1 extends MultilingualResource {

	public ResourceModel1() throws ProgramException {
		super();		
		//Definition des langues supportees par la ressource
		this.addSupportedLanguage(Locale.FRANCE.getLanguage());
		this.addSupportedLanguage(Locale.US.getLanguage());
		//Defintion de la langue par defaut
		this.setDefaultLanguage(Locale.US.getLanguage());
		//definition des parametres mutlilingues 
		this.definesParameter("attributeLabel","valueLabel","valueToolTip","validLabel");
		//Definition des valeurs des parametres multitlingues
		// -- En langue francaise
		this.setValueTo("attributeLabel", Locale.FRANCE.getLanguage(), "Attribut");
		this.setValueTo("valueLabel", Locale.FRANCE.getLanguage(), "Valeur");
		this.setValueTo("valueToolTip", Locale.FRANCE.getLanguage(), "La Valeur doit etre un entier");
		this.setValueTo("validLabel", Locale.FRANCE.getLanguage(), "Valide");
		// -- En langue anglaise
		this.setValueTo("attributeLabel", Locale.US.getLanguage(), "Attribute");
		this.setValueTo("valueLabel", Locale.US.getLanguage(), "Value");
		this.setValueTo("valueToolTip", Locale.US.getLanguage(), "Value must to be an Integer");
		this.setValueTo("validLabel", Locale.US.getLanguage(), "Apply");
		
		//Definition de la langue courante de la ressource a la langue par defaut
		this.setCurrentLanguage(this.getDefaultLanguage());
	}

	@Override
	protected void load(String language) throws ProgramException {
		
		/*  Pas necessaire de le definir ici car les parametres
		 *  multilingues ne sont pas stockes dans des fichiers.  
		 */
	}
}
