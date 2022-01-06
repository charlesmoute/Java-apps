package cm.enterprise.software.test.multilingualresource.divers;

import java.util.Arrays;
import java.util.Locale;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.enterprise.software.helper.ExceptionResource;
import cm.enterprise.software.util.MultilingualResource;

/**
 * Classe de test de la super ressource multilingue SuperMR
 * @author Charles Moute
 * @version 1.0.1, 22/07/2010
 */

public class TestSuperMR extends Application {
	
	SuperMR smr ;

	/**
	 * Cree une instance de test de la classe SuperMR
	 * @throws ProgramException
	 */
	@SuppressWarnings("unchecked")
	public TestSuperMR() throws ProgramException{
		super();
		/*
		 * A fin de le faire dans l'esprit d'une application 
		 * nous allons directement mettre notre super ressource
		 * a dispositon de l'application globale.
		 */
		addResource(SuperMR.class);
		
		//On recupere l'instance manipuler par l'application
		smr = getResource(SuperMR.class);
		
		//On va d'abord afficher le contenu de la ressource
		System.out.println("\n-------------------------- Informations de la super ressource "+smr.getName()+"  --------------------------");
		System.out.println(" Liste des langues supportees : "+Arrays.toString(smr.getAvailableLanguages()));
		System.out.println(" Langue par defaut : "+smr.getDefaultLanguage());
		System.out.println(" Langue courante : "+smr.getCurrentLanguage());
		System.out.println(" Les ressources constituants la super Ressource : ");
		for(MultilingualResource resource:smr.getSubMultilingualResourceList()) System.out.println("  - "+resource.getName());
		System.out.println(" Liste des parametres multilingues de la super ressource en langue courante");
		ExceptionResource rsrc  = smr.getSubMultilingualResource(ExceptionResource.class);
		System.out.println("Ressource "+rsrc.getName()+": UNKNOWN_MESSAGE = "+rsrc.getValueOf("unknownException"));
		System.out.println("Ressource "+rsrc.getName()+": NOTSUPPORTED_MESSAGE = "+rsrc.getValueOf("notSupportedException"));
		System.out.println("Ressource "+rsrc.getName()+": ERRONEOUSPARAMETER_MESSAGE = "+rsrc.getValueOf("erroneousParameterException"));
		System.out.println("Ressource "+rsrc.getName()+": GENERAL_EXCEPTION_MESSAGE = "+rsrc.getValueOf("generalException"));
		System.out.println("Ressource "+rsrc.getName()+": IO_OPERATIONFAILURE_MESSAGE = "+rsrc.getValueOf("ioException"));
		
		/*System.out.println("  - Sous ressource "+rsrc.getResourceName()+": UNKNOWN_MESSAGE = "+rsrc.getMessageOfUnknownException());
		System.out.println("  - Sous ressource "+rsrc.getResourceName()+": NOTSUPPORTED_MESSAGE = "+rsrc.getMessageOfNotSupportedException());
		System.out.println("  - Sous ressource "+rsrc.getResourceName()+": ERRONEOUSPARAMETER_MESSAGE = "+rsrc.getMessageOfErroneousParamaterException());
		System.out.println("  - Sous ressource "+rsrc.getResourceName()+": GENERAL_EXCEPTION_MESSAGE = "+rsrc.getMessageOfGeneralException());
		System.out.println("  - Sous ressource "+rsrc.getResourceName()+": IO_OPERATIONFAILURE_MESSAGE = "+rsrc.getMessageOfIOFailureException());*/
		MR2 mr2= smr.getSubMultilingualResource(MR2.class);		
		System.out.println("  - Sous ressource "+mr2.getName()+": VALUE_LABEL = "+mr2.VALUE_LABEL);
		System.out.println("  - Sous ressource "+mr2.getName()+": ATTRIBUT_LABEL = "+mr2.ATTRIBUT_LABEL);
		
		
		System.out.println("\n-------------------------- Ajout d'un parametre multilingue a la super ressource "+smr.getName()+"  --------------------------");
		System.out.println(" Le nouveau parametre sera params.\n Configuration de ce nouveau parametre.");
		smr.definesParameter("params");//definition du nouveau parametre
		//parametrage des valeurs du parametre multilingue au langues de la super ressource.
		smr.setValueTo("params", Locale.FRANCE.getLanguage(), "Parametre");
		smr.setValueTo("params", Locale.US.getLanguage(), "Parameter");
		System.out.println(" Le parametre multilingue params de la super ressource en langue courante a pour valeur = "+smr.getValueOf("params"));
		
		
		System.out.println("\n-------------------------- Ajout d'une nouvelle ressource a la super ressource "+smr.getName()+" --------------------------");
		//Ajout d'une nouvelle ressource
		smr.addClassOfMultilingualResource(MR.class);
		//Instanciation de cette nouvelle ressource
		smr.addSubMultilingualResource(MR.class);
		//Recuperation de l'instance
		MR mr = smr.getSubMultilingualResource(MR.class);
		System.out.println(" La ressource de nom "+mr.getName()+" a ete ajoutee a la super ressource "+smr.getName());
		
		
		System.out.println("\n-------------------------- Inormations sur la ressource "+smr.getName()+" apres configuration de "+mr.getName()+" --------------------------");
		mr.addSupportedLanguage(Locale.FRANCE.getLanguage());
		mr.addSupportedLanguage(Locale.US.getLanguage());
		//Defintion de la langue par defaut
		mr.setDefaultLanguage(Locale.US.getLanguage());
		//definition des parametres mutlilingues 
		mr.definesParameter("nameLabel","countryLabel","addressLabel");
		//Definition des valeurs des parametres multitlingues
		// -- En langue francaise
		mr.setValueTo("nameLabel", Locale.FRANCE.getLanguage(), "Nom");
		mr.setValueTo("countryLabel", Locale.FRANCE.getLanguage(), "Pays");
		mr.setValueTo("addressLabel", Locale.FRANCE.getLanguage(), "Adresse");
		// -- En langue anglaise
		mr.setValueTo("nameLabel", Locale.US.getLanguage(), "Name");
		mr.setValueTo("countryLabel", Locale.US.getLanguage(), "Country");
		mr.setValueTo("addressLabel", Locale.US.getLanguage(), "Address");
		//Affectation de la langue courante de l'application a la ressource
		mr.setCurrentLanguage(Locale.getDefault().getLanguage());
		
		System.out.println(" Les ressources constituants la super Ressource : ");
		for(MultilingualResource resource:smr.getSubMultilingualResourceList()) System.out.println("  - "+resource.getName());
		
		System.out.println("\n-------------------------- Acces a la ressource "+mr.getName()+" depuis "+smr.getName()+" avec la methode invoke  --------------------------");
		//Liste des informations sur la nouvelle ressource cree 
		String resourceName = (String) smr.invokeMethodOfSubMultilingualResource(MR.class, "getName");
		String languages = Arrays.toString((String[])smr.invokeMethodOfSubMultilingualResource(MR.class, "getAvailableLanguages")) ;//mr.getAvailableLanguages()
		String currentLanguage =(String)smr.invokeMethodOfSubMultilingualResource(MR.class, "getCurrentLanguage");
		String defaultLanguage = (String)smr.invokeMethodOfSubMultilingualResource(MR.class, "getDefaultLanguage");
		String params = Arrays.toString((String[])smr.invokeMethodOfSubMultilingualResource(MR.class, "getParameterNameList")); 
	
		System.out.println(" Ressource "+resourceName+": Langues disponibles = "+languages);
		System.out.println(" Ressource "+resourceName+": Langue Courante = "+currentLanguage);		
		System.out.println(" Ressource "+resourceName+": Langue par defaut= "+defaultLanguage);
		System.out.println(" Liste des parametres multilingues de "+resourceName+" : "+params);		
		//Liste des valeurs des parametres multilingues en langue courante
		System.out.println(" Liste des valeurs des parametres multilingues en langues "+currentLanguage);
		for(String param:mr.getParameterNameList()){		
			String val = (String)smr.invokeMethodOfSubMultilingualResource(MR.class, "getValueOf", param);
			System.out.println("   - "+smr.getName()+": "+param+" = "+val);
		}
	}
	
	public void test(){
		
	}
	
	public static void main(String[] args) throws ProgramException {
		TestSuperMR soft = new TestSuperMR();
		soft.test();
	}

}
