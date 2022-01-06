package cm.enterprise.software.test.multilingualresource.divers;

import java.util.Arrays;
import java.util.Locale;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.enterprise.software.helper.ExceptionResource;


/**
 * Test du concept de ressource multilingue
 * @author Charles Moute
 * @version 1.0.1, 19/07/2010
 */
public class TestMultilingualResource extends Application {

	MR resource ; 
	
	public TestMultilingualResource(){
		super();
		try {
			//Ajout d'une ressource multilingue a l'application, ici TestMultilingualResource
			addResource(MR2.class);
		} catch (ProgramException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Fonction de test
	 * @throws ProgramException 
	 */
	public void test() throws ProgramException{

		//Obtention des langues supportees par la resouce ExceptionResource
		ExceptionResource rsrc = getResource(ExceptionResource.class);
		//rsrc.removeParameter("unknownException");
		//rsrc.clear();
		System.out.println("\n--------------- Informations sur la ressource "+rsrc.getName()+" ---------------");
		String languages =  Arrays.toString(rsrc.getAvailableLanguages());
		System.out.println("Ressource "+rsrc.getName()+": Langues disponibles = "+languages);
		System.out.println("Ressource "+rsrc.getName()+": Langue Courante = "+rsrc.getCurrentLanguage());		
		System.out.println("Ressource "+rsrc.getName()+": Langue par defaut= "+rsrc.getDefaultLanguage());
		//Obtention des valeurs de la ressource ExceptionResource en fonction de la langue courante		
		System.out.println("Ressource "+rsrc.getName()+": UNKNOWN_MESSAGE = "+rsrc.getValueOf("unknownException"));
		System.out.println("Ressource "+rsrc.getName()+": NOTSUPPORTED_MESSAGE = "+rsrc.getValueOf("notSupportedException"));
		System.out.println("Ressource "+rsrc.getName()+": ERRONEOUSPARAMETER_MESSAGE = "+rsrc.getValueOf("erroneousParameterException"));
		System.out.println("Ressource "+rsrc.getName()+": GENERAL_EXCEPTION_MESSAGE = "+rsrc.getValueOf("generalException"));
		System.out.println("Ressource "+rsrc.getName()+": IO_OPERATIONFAILURE_MESSAGE = "+rsrc.getValueOf("ioException"));
		
		//Obtention des langues supportees par la resouce ExceptionResource
		MR2 rsrc2 = getResource(MR2.class); 
		System.out.println("\n--------------- Informations sur la ressource "+rsrc2.getName()+" ---------------");
		languages =  Arrays.toString(rsrc2.getAvailableLanguages());
		System.out.println("Ressource "+rsrc2.getName()+": Langues disponibles = "+languages);
		System.out.println("Ressource "+rsrc2.getName()+": Langue Courante = "+rsrc2.getCurrentLanguage());		
		System.out.println("Ressource "+rsrc2.getName()+": Langue par defaut= "+rsrc2.getDefaultLanguage());
		//Obtention des valeurs de la ressource MR2
		System.out.println("Ressource "+rsrc2.getName()+": VALUE_LABEL = "+rsrc2.VALUE_LABEL);
		System.out.println("Ressource "+rsrc2.getName()+": ATTRIBUT_LABEL = "+rsrc2.ATTRIBUT_LABEL);
		
		
		//Changement de la langue courante en langue francaise
		setCurrentLanguage(Locale.FRANCE.getLanguage());
		
		System.out.println("\n--------------- Affichage de la resource "+rsrc.getName()+" en la langue "+rsrc.getCurrentLanguage()+" ---------------");
		//Obtention des valeurs de la ressource ExceptionResource en fonction de la langue courante		
		System.out.println("Ressource "+rsrc.getName()+": UNKNOWN_MESSAGE = "+rsrc.getValueOf("unknownException"));
		System.out.println("Ressource "+rsrc.getName()+": NOTSUPPORTED_MESSAGE = "+rsrc.getValueOf("notSupportedException"));
		System.out.println("Ressource "+rsrc.getName()+": ERRONEOUSPARAMETER_MESSAGE = "+rsrc.getValueOf("erroneousParameterException"));
		System.out.println("Ressource "+rsrc.getName()+": GENERAL_EXCEPTION_MESSAGE = "+rsrc.getValueOf("generalException"));
		System.out.println("Ressource "+rsrc.getName()+": IO_OPERATIONFAILURE_MESSAGE = "+rsrc.getValueOf("ioException"));
		
		System.out.println("\n--------------- Affichage de la resource "+rsrc2.getName()+" en la langue "+rsrc2.getCurrentLanguage()+" ---------------");
		//Obtention des valeurs de la ressource MR2
		System.out.println("Ressource "+rsrc2.getName()+": VALUE_LABEL = "+rsrc2.VALUE_LABEL);
		System.out.println("Ressource "+rsrc2.getName()+": ATTRIBUT_LABEL = "+rsrc2.ATTRIBUT_LABEL);
		
		//NB : Si vous essayez d'affecter une langue non prise en charge par une ressource c'est la langue par defaut qui sera chargee
		
		System.out.println("------------------------------------------------------------------------------");
		
		//Definition et configuration d'une super Ressource.
		resource = new MR();
		
		/*
		 * La configuration de cette ressource resource se fera en deux etapes 
		 * la premiere on definira trois parametres multilingues pour notre nouvelle resource
		 * pour deux langues disons l'anglais et l'italien.
		 * on affecte la super resource precedente
		 * la deuxieme on lui ajoutera la resource precedement cree et on verifiera si le changement
		 * s'opperent de facon satisfaisante. 
		 */
		
		/*
		 * Avant toute chose on definira le nom de la resource, ensuite les langues qui seront 
		 * disponibles pour la ressource, par la suite la langue par defaut, apres les parametres
		 * multilingues de la ressource et enfin on passera au premier test.
		 */
		//Definition du nom de la super ressource
		resource.setName("MR");
		//Definition des langues supportees par la super ressource
		resource.addSupportedLanguage(Locale.FRANCE.getLanguage());
		resource.addSupportedLanguage(Locale.US.getLanguage());
		//Defintion de la langue par defaut
		resource.setDefaultLanguage(Locale.US.getLanguage());
		//definition des parametres mutlilingues 
		resource.definesParameter("nameLabel","countryLabel","addressLabel");
		//Definition des valeurs des parametres multitlingues
		// -- En langue francaise
		resource.setValueTo("nameLabel", Locale.FRANCE.getLanguage(), "Nom");
		resource.setValueTo("countryLabel", Locale.FRANCE.getLanguage(), "Pays");
		resource.setValueTo("addressLabel", Locale.FRANCE.getLanguage(), "Adresse");
		// -- En langue anglaise
		resource.setValueTo("nameLabel", Locale.US.getLanguage(), "Name");
		resource.setValueTo("countryLabel", Locale.US.getLanguage(), "Country");
		resource.setValueTo("addressLabel", Locale.US.getLanguage(), "Address");
		
		//Premier etapes verification des parametres multilingues.
		System.out.println("\n------------------ Informations sur la Resource "+resource.getName()+"------------------");
		languages =  Arrays.toString(resource.getAvailableLanguages());
		System.out.println("Ressource "+resource.getName()+": Langues disponibles = "+languages);
		System.out.println("Ressource "+resource.getName()+": Langue Courante = "+resource.getCurrentLanguage());		
		System.out.println("Ressource "+resource.getName()+": Langue par defaut= "+resource.getDefaultLanguage());
		System.out.println("Liste des parametres multilingues de "+resource.getName()+" : "+Arrays.toString(resource.getParameterNameList()));
		
		//Liste des valeurs des parametres multilingues en langue en
		resource.setCurrentLanguage(Locale.US.getLanguage());
		System.out.println("\n--------------------- Liste des parametres multilingues de "+resource.getName()+" en langues "+resource.getCurrentLanguage()+" ---------------------");
		System.out.println(resource.getName()+": nameLabel = "+resource.getValueOf("nameLabel"));
		System.out.println(resource.getName()+": countryLabel = "+resource.getValueOf("countryLabel"));
		System.out.println(resource.getName()+": addressLabel = "+resource.getValueOf("addressLabel"));
		
		//Liste des valeurs des parametres multilingues en langue fr
		resource.setCurrentLanguage(Locale.FRANCE.getLanguage());
		System.out.println("\n--------------------- Liste des parametres multilingues "+resource.getName()+" en langues "+resource.getCurrentLanguage()+" ---------------------");
		System.out.println(resource.getName()+": nameLabel = "+resource.getValueOf("nameLabel"));
		System.out.println(resource.getName()+": countryLabel = "+resource.getValueOf("countryLabel"));
		System.out.println(resource.getName()+": addressLabel = "+resource.getValueOf("addressLabel"));
				
	}
	public static void main(String[] args) throws ProgramException {
		
		TestMultilingualResource soft = new TestMultilingualResource();
		soft.test();
	}

}
