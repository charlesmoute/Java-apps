package cm.enterprise.software.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cm.enterprise.software.exception.ProgramException;

/**
 * MultilingualParameter represente un parametre d'une ressource mulltilingue.
 * Ce parametre a un nom, une valeur pour chaque langue supportee par le parametre 
 * et la valeur qui est directement attachee est sa langue par defaut. 
 * Lors de la recuperation de la valeur du parametre de la ressource multilingue,
 * en une langue donnee, si cette derniére n'existe pas la valeur en la langue par 
 * defaut du parametre est retournee.
 *
 * @author Charles Moute
 * @version 1.0.1, 19/07/2010
 */
public abstract class MultilingualParameter extends Parameter {

	/**
	 * Represente l'ensemble des valeurs que peut prendre la ressource
	 * selon la langue.	
	 */
	protected Map<String,String> values = new HashMap<String,String>();
	
	/**
	 * Cree un parametre de nom le parametre name. Avec comme la langue par defaut
	 * defaultLanguage et comme valeur associee a cette langue le parametre defaultValue 
	 * @param name Nom du parametre
	 * @param defaultLanguage Langue par defaut du parametre
	 * @param defaultValue Valeur du parametre en la langue par defaut.
	 * @throws ProgramException
	 */
	public MultilingualParameter(String name,String defaultLanguage,String defaultValue) throws ProgramException{
		super(name,defaultLanguage);
		if(value==null || value.isEmpty())
			throw new ProgramException("Pour le parametre vous devez specifier un nom et une langue par defaut");
		values.put(value, defaultValue);
	}
	
	/**
	 * Ajoute au parametre de la ressource multiingue une valeur value pour la langue language.
	 * Si la valeur pour cette langue avait deja ete definie, la valeur est modifiee par celle 
	 * en parametre, si au contraire la valeur pour la langue en parametre n'avait pas encore
	 * ete definie, elle est defini.
	 * @param language Langue de la valeur a ajouter
	 * @param value Valeur a ajouter.
	 */
	public void addValueToParamter(String language,String value){
		if(language!=null && !language.isEmpty()) values.put(language, value);
	}
	
	/**
	 * Permet de savoir si la valeur du parametre pour la langue en parametre a deja ete definie.
	 * @param language Langue a verifier
	 * @return true si la valeur du parametre pour la langue en parametre est deja definie
	 */
	public boolean valueIsDefinedFor(String language){		
		return values.containsKey(language);
	}
	
	/**
	 * Dit si le parametre a au moins une valeur definie
	 * @return true si le parametre a au moins une valeur de definie
	 */
	public boolean isEmpty(){
		return values.isEmpty();
	}
	
	/**
	 * Permet d'obtenir la valeur du parametre en la langue language
	 * @param language La langue en laquelle on souhaite avoir la valeur du parametre.
	 * @return La valeur du parametre de la ressource multilingue.
	 */
	public String getValueOfParameter(String language){
		String result =  values.get(language);
		if(result==null || result.isEmpty()) result = values.get(value);
		return result;
	}
	
	/**
	 * Efface le couple (langue,valeur) de l'ensemble des informations du parametre
	 * @param language Langue dont les informations doivent etre supprimees
	 */
	public void remove(String language){
		values.remove(language);
	}
	
	/**
	 * Nettoie le parametre, en effacant toutes les valeurs qui lui ont ete associe.
	 */
	public void clear(){
		values.clear();
	}
	
	/**
	 * Permet d'obtenir l'ensemble des langues pour lesquelles le parametre a une valeur de definie.
	 * @return Langues supportees par le parametre.
	 */
	public String[] getAvailableLanguages(){
		Set<String> languagesSet = values.keySet();
		String[] result = null;
		if(languagesSet!=null){
			Object[] objects = languagesSet.toArray();
			if(objects!=null){
				result = new String[objects.length];
				int count = 0 ;
				for(Object obj:objects) result[count++] = obj.toString();
			}
		}
		return result;
	}
	
	/**
	 * Permet de savoir si la langue en parametre est supportee par la ressource.
	 * Supportee, ici, revient a dire que langue a ete definit par la fonction
	 * addValueToParameter(). 
	 * @param language Langue a verifier
	 * @return true si la langue est supportee.
	 * @see #addValueToParamter(String, String)
	 */
	public boolean isSupportedLanguage(String language){
		return values.containsKey(language);
	}
	
	/**
	 * Permet d'obtenir la langue par defaut.
	 * @return La langue par defaut du parametre multilingue.
	 */
	public String getDefaultLanguage(){
		return this.value;
	}
	
	/**
	 * Affecte une nouvelle langue comme langue par defaut du multiparametre.
	 * Si et seulement si la langue en parametre est supportee.
	 * @param defaultLanguage
	 */
	public void setDefaultLanguage(String defaultLanguage){
		if(!this.isSupportedLanguage(defaultLanguage)) return;
		this.value = defaultLanguage;
	}
	
	/**
	 * Permet d'obtenir la liste des valeurs definies pour le parametre
	 * @return Liste des valeurs associees au parametre.
	 */
	public String[] getAvailableValues(){
		Collection<String> valuesCollection = values.values();
		String[] result = null;
		if(valuesCollection!=null){
			Object[] objects = valuesCollection.toArray();
			if(objects!=null){
				result = new String[objects.length];
				int count = 0 ;
				for(Object obj:objects) result[count++] = obj.toString();
			}
		}
		return result;
	}
}
