package cm.enterprise.software.helper;

import java.util.Locale;

import cm.enterprise.software.exception.*;
import cm.enterprise.software.util.MultilingualResource;

/**
 * La classe ExceptionResource regroupe l'ensemble des constantes utilisees 
 * par les exceptions du framework. Elle represente la resource multilingue qu'
 * est exception. Son but est de gerer les messages d'erreurs en fonction de la
 * langue soit le Français, soit l'Anglais. Dans le cas ou la langue, n'est pas
 * prise en compte, la langue par defaut est l'Anglais.
 * 
 * @author Charles Moute
 * @version 1.0.1, 12/07/2010
 */
public class ExceptionResource extends MultilingualResource {

	/**
	 * Instancie un objet de type ExceptionResource.
	 * @throws ProgramException
	 */
	public ExceptionResource() throws ProgramException {
		super();
		String defaultLanguage = Locale.US.getLanguage();
		this.addSupportedLanguage(defaultLanguage);
		this.addSupportedLanguage(Locale.FRANCE.getLanguage());
		this.setDefaultLanguage(defaultLanguage);
		
		//Definitions des parametres de la ressource.
		this.definesParameter("unknownException","notSupportedException","erroneousParameterException");
		this.definesParameter("generalException","ioException");
		
		//Affectation de valeur par defaut.
		this.setValueTo("unknownException",defaultLanguage, "Unknown Exception");
		this.setValueTo("notSupportedException",defaultLanguage, "Exception not supported");
		this.setValueTo("erroneousParameterException",defaultLanguage,"Erroneous Parameter(s)");
		this.setValueTo("generalException",defaultLanguage, "Failure operation");
		this.setValueTo("ioException",defaultLanguage, "Failure operation input/output");
		
		// -- Affectation de valeur En langue francaise
		this.setValueTo("unknownException",Locale.FRANCE.getLanguage(), "Erreur inconnue");
		this.setValueTo("notSupportedException",Locale.FRANCE.getLanguage(), "Operation non prise en charge");
		this.setValueTo("erroneousParameterException",Locale.FRANCE.getLanguage(),"Parametre(s) errone(s)");
		this.setValueTo("generalException",Locale.FRANCE.getLanguage(), "Echec Operation");
		this.setValueTo("ioException",Locale.FRANCE.getLanguage(), "Echec operation ecriture/lecture");
		
		 
		
		//Affecte le langage courant
		this.setCurrentLanguage(this.getDefaultLanguage());
	}
	
	@Override
	protected void load(String language) throws ProgramException  {		
		/*setResourcePath(Application.getFileResourcePath()+"config/"+this.getCurrentLanguage()+"/exception.properties");
		java.util.Properties properties = Helper.readInternalProperties(this.getResourcePath());
		
		this.setValueTo("unknownException",language, properties.getProperty("unknownException"));
		this.setValueTo("notSupportedException",language, properties.getProperty("notSupportedException") );
		this.setValueTo("erroneousParameterException",language,properties.getProperty("erroneousParameterException"));
		this.setValueTo("generalException",language, properties.getProperty("generalException"));
		this.setValueTo("ioException",language, properties.getProperty("ioException"));*/
	}	
}
