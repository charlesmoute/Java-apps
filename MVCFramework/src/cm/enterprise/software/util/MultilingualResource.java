package cm.enterprise.software.util;

import java.util.Collection;
import java.util.Set;

import cm.enterprise.software.exception.ProgramException;

/**
 * Classe representant une resource multilingue. Comme exemple nous 
 * pouvons citer exception comme une ressource interne au framework
 * et dont la classe manageant son multilinguisme est ConstantsOfException.
 * Chaque classe heritant de MultilingualResource, doit imperativement
 * definir un constructeur vide.
 * @see cm.enterprise.software.helper.ExceptionResource
 * @author Charles Moute
 * @version 1.0.1, 12/07/2010
 */
public abstract class MultilingualResource extends Resource {
		
	/**
	 * Represente le langage courant utilise par la ressource.
	 */
	private String currentLanguage = null;
	
	/**
	 * Le langage par defaut lorsqu'une langue n'est pas supported par une ressource.
	 */
	private String defaultLanguage = null ;
	
	/**
	 * Liste des langages supported par la ressource.
	 */
	private java.util.ArrayList<String> supportedLanguages = new java.util.ArrayList<String>();
	
	/**
	 * Represente la liste des parametres de la ressource multilingue.
	 */
	private java.util.Map<String, MultilingualParameter> parameters = new java.util.HashMap<String, MultilingualParameter>();
	
	/**
	 * Construit une instance de MultilingualResource de nom le parametre. En affectant
	 * le nom de la classe comme nom de la resource multilingue et le path comme le chemin
	 * d'acces a la classe de la ressource multilingue.
	 */
	public MultilingualResource() throws ProgramException {
		name = this.getClass().getSimpleName();
		value = this.getClass().getCanonicalName();		
	}
			
	/**
	 * Affecte le parametre comme chemin de la ressource
	 * @param path Chemin de la ressource
	 */
	public void setResourcePath(String path){
		this.value = path ;
	}
	
	/**
	 * Permet d'obtenir le chemin de la ressource.
	 * @return Le chemin d'acces a la ressource.
	 */
	public String getResourcePath(){ return this.value; }
	
	/**
	 * Permet d'obtenir la langue courante de la ressource.
	 * @return Le langage courant utilise par la ressource.
	 */
	public String getCurrentLanguage() {return this.currentLanguage; }
	
	/**
	 * Affecte le parametre comme langue par defaut de la super ressource.
	 * Si la langue n'est pas supporte c'est la langue par defaut qui est 
	 * affectee. Une erreur est lancee si le chargement de la ressource
     = en fonction de la langue ne s'est pas deroule correctement.
	 * @param language Langue a affecter
	 * @throws ProgramException 
	 */
	public void setCurrentLanguage(String language) throws ProgramException{
		String lang = language ;
		if(!this.isSupportedLanguage(language)) lang = this.defaultLanguage;
		this.currentLanguage = lang;
		this.load(currentLanguage);		
	}
	
	/**
	 * Permet d'obtenir la langue par defaut de la ressource multilingue.
	 * @return Langage utilise par defaut lorsqu'un langage n'est pas supporte.
	 */
	public String getDefaultLanguage() { return this.defaultLanguage; }
	
	/**
	 * Affecte le parametre comme langue par defaut uniquement si il est supporte par la ressource.
	 * Avant d'utiliser cette fonction assurez vous d'avoir ajoute le langage que vous voulez definir comme
	 * celui par defaut. Sinon, aucun ajout ne se fera.
	 * @param newDefaultLanguage Le nouveau langage a consider comme la langue par defaut lorsqu'une langue n'est pas supportee.
	 */
	public void setDefaultLanguage(String newDefaultLanguage){
		if(!this.isSupportedLanguage(newDefaultLanguage)) return ;
		this.defaultLanguage = newDefaultLanguage;
	}
	
	/**
	 * Ajoute le parametre a liste des langages supportes par la ressource.
	 * @param language Langage supporte par la ressource multilingue. 
	 */
	public void addSupportedLanguage(String language){
		if(!this.supportedLanguages.contains(language))
			this.supportedLanguages.add(language);
	}
	
	/**
	 * Efface le parametre de la liste des langues supportees par la ressource multilingue. 
	 * @param language La langue a effacer.
	 */
	public void removeSupportedLanguage(String language){
		this.supportedLanguages.remove(language);
	}
	
	/**
	 * dit si le parametre est une langue supportee par la ressource.
	 * @param language Langage a verifier
	 * @return true si le langage est pris en charge par la ressource.
	 */
	public boolean isSupportedLanguage(String language){
		return supportedLanguages.contains(language);
	}
	
	/**
	 * Permet d'obtenir la liste des langues supportees par la ressource multilingue.
	 * @return La liste des langues disponibles pour la ressource multilingue.
	 */
	public final String[] getAvailableLanguages(){
		Object[] objects = supportedLanguages.toArray();
		String[] result = null;
		if(objects!=null){
			result = new String[objects.length];
			int count = 0 ;
			for(Object obj:objects) result[count++] = obj.toString();
		}
		return result;
	}

	/**
	 * Charge la ressource en fonction du parametre. Fonction permettant
	 * de gerer le multilinguisme de la ressource.
	 * @param language Langue a utilise pour le chargement de la ressource.
	 * @throws ProgramException
	 */
	protected abstract void load(String language) throws ProgramException ;
	
	/**
	 * Definit un ou plusieurs parametres pour la ressource multilingue. Avec comme
	 * valeur par defaut la chaine vide pour la langue par defaut de la ressource. 
	 * NB: Assurez vous d'avoir definir la langue par defaut de la ressource, avant
	 * d'appeler cette methode. Si non aucun parametre ne sera defini.
	 * De plus ne definissez q'une seule fois un nom de parametre, de peur de perdre
	 * les valeurs qui lui sont affectees lors d'un n-ieme definition. n etant plus grand
	 * que un.
	 * @param params Les parametres a definir.
	 * @throws ProgramException 
	 */
	public void definesParameter(String... params) throws ProgramException{
		if(this.defaultLanguage==null || defaultLanguage.isEmpty()) throw new ProgramException("defaultLanguage = null ");
		for(String param:params){
			try {
				MultilingualParameter paramValues = new MultilingualParameter(param,this.defaultLanguage,""){};
				parameters.put(param, paramValues);
			} catch (ProgramException e) { e.printStackTrace();	}
		}
	}
	
	/**
	 * Permet de savoir si un parametre donne a deja ete defini pour la ressource
	 * @param parameterName Nom de parametre a verifier l'existence.
	 * @return true si un parametre repondant a ce nom a deja ete defini.
	 */
	public boolean parameterIsDefined(String parameterName){
		return parameters.containsKey(parameterName);
	}
	
	/**
	 * Affecte une valeur value au parametre parameterName, uniquement si
	 * ce parametre a ete defini. 
	 * @param parameterName Nom du parametre auquel la valeur sera affectee
	 * @param value Valeur a affecter au parametre de nom parameterName
	 */
	public void setValueTo(String parameterName,String value){
		this.setValueTo(parameterName, this.getCurrentLanguage(), value);
	}
	
	/**
	 * Affecte la valeur value en langue language au parametre parameterName.
	 * @param parameterName Nom du parametre dont la valeur sera ajoutee ou modifiee
	 * @param language Langue de la valeur a affecter
	 * @param value Valeur du parameterName pour language
	 */
	public void setValueTo(String parameterName,String language,String value){
		MultilingualParameter param = parameters.get(parameterName);
		if(param!=null){
			param.addValueToParamter(language, value);
		}
	}
	
	/**
	 * Permet d'obtenir la valeur du parametre de nom parameterName pour la langue courante.
	 * @param parameterName Nom du parametre dont on veut obtenir la valeur 
	 * @return valeur du parametre en la langue courante.
	 */
	public String getValueOf(String parameterName){
		return this.getValueOf(parameterName, this.currentLanguage);
	}
	
	/**
	 * Permet d'obtenir la valeur du parametre de nom parameterName en la langue language.
	 * @param parameterName Nom du parametre dont on veut la valeur
	 * @param language Langue en laquelle on souhaite avoir la valeur du parametre.
	 * @return La valeur du premier parametre en la langue specifie par le second parametre
	 */
	public String getValueOf(String parameterName,String language){
		MultilingualParameter param = parameters.get(parameterName);
		if(param!=null){
			return param.getValueOfParameter(language);
		}
		return null;
	}
	
	/**
	 * Permet d'obtenir la liste des noms des parametres multilingues definies 
	 * pour la ressource multilingue courante.
	 * @return La liste des noms des parametres multilingues.
	 */
	public String[] getParameterNameList(){
		Set<String> list = parameters.keySet();
		String[] result = null;
		if(list!=null){
			Object[] objects = list.toArray();
			if(objects!=null){
				result = new String[objects.length];
				int count = 0 ;
				for(Object obj:objects)	result[count++] = obj.toString();
				
			}
		}
		return result;
	}
	
	/**
	 * Permet d'obtenir la liste des parametres multilingues de la ressource multilingue
	 * @return La liste des parametres multililingues de la resource.
	 */
	public MultilingualParameter[] getParameterList(){
		Collection<MultilingualParameter> list = parameters.values();
		MultilingualParameter[] result = null ;
		if(list!=null){
			Object[] objects = list.toArray();
			if(objects!=null){
				result = new MultilingualParameter[objects.length];
				int count = 0 ;
				for(Object obj:objects) result[count++] = (MultilingualParameter)obj;
			}
		}
		return result;
	}
	
	/**
	 * Efface le parametre de nom parameterName
	 * @param parameterName Nom du parametre a effacer.
	 */
	public void removeParameter(String parameterName){
		parameters.remove(parameterName);
	}
	
	/**
	 * Nettoie la ressource de tous ses parametres en les effacant tous.
	 */
	public void clear(){
		parameters.clear();
	}
}
