package cm.enterprise.software.test.multilingualresource.divers;

import java.util.Locale;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.util.MultilingualResource;

/**
 * MR2 est une resource multilingue de test.
 * @author Charles Moute
 * @version 1.0.1, 19/07/2010
 */
public class MR2 extends MultilingualResource {

	
	/**
	 * Parametre multilingue representant la valeur du label d'une demande de
	 * saisie de valeur. 
	 */
	public String VALUE_LABEL = null;
	
	/**
	 * Parametre multilingue representant la valeur du label specifiant la demande de la saisie du
	 * nom d'un attribut.
	 */
	public String ATTRIBUT_LABEL = null;
	
	
	public MR2() throws ProgramException {
		super();
		//this.name = "testMR";
		//this.addSupportedLanguage(Locale.US.getLanguage());
		this.addSupportedLanguage(Locale.FRANCE.getLanguage());
		this.addSupportedLanguage(Locale.GERMAN.getLanguage());
		this.setDefaultLanguage(Locale.GERMAN.getLanguage());
		this.setCurrentLanguage(this.getDefaultLanguage());
	}	

	@Override
	protected void load(String language) throws ProgramException {
		if(language!=null && language.compareTo(Locale.FRANCE.getLanguage())==0){
			VALUE_LABEL = "Entrez une valeur";
			ATTRIBUT_LABEL = "Entrez un nom d'attribut";
		}/*else if(language!=null &&  language.compareTo(Locale.US.getLanguage())==0){
			VALUE_LABEL = "Put a value";
			ATTRIBUT_LABEL = " Put a name of attribute";			
		}*/else{
			VALUE_LABEL = "Setzen Sie einen Wert";
			ATTRIBUT_LABEL = "Setzen Sie einen Namen von Eigenschaft";			
		}
	}

}
