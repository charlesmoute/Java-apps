package cm.enterprise.software.util;

import java.util.Collection;
import java.util.Set;

import cm.enterprise.software.exception.ProgramException;

/**
 * Une ressource, peut etre constituee de plusieurs parametres. Exemple: la ressource de
 * feux de signalisation, la valeur est sont etat courant. La dite ressource peut 
 * etre vert, rouge ou orange chacun de ses etats representent en fait un parametre de
 * la ressource et sa valeur en un moment donnee sera une de ces valeurs. Bien evidement
 * une ressource peut avoir aussi un parametre qui est une ressource. Seul notre imagination
 * imposera une limite.
 * @author Charles Moute.
 * @version 1.0.1, 24/07/2010
 */
public abstract class Resource extends Parameter {

	/**
	 * Liste des parametres de la ressource
	 */
	private java.util.Map<String,Parameter> parameters = new java.util.HashMap<String, Parameter>();
	
	/**
	 * Construit une instance vide de ressource avec un nom par defaut
	 * et une valeur par defaut.
	 */
	public Resource(){
		super();
	}
			
	/**
	 * Construit une instance de Ressource avec pour nom le parametre.
	 * @param value Nom de la ressource.
	 * @throws ProgramException
	 */
	public Resource(String value) throws ProgramException{
		super(value);
	}
	
	/**
	 * Construit une instance de la ressource avec pour nom  name et pour valeur value.
	 * @param name Nom de la ressource.
	 * @param value Valeur de la ressource.
	 * @throws ProgramException
	 */
	public Resource(String name, String value) throws ProgramException {
		super(name, value);
	}
	
	/**
	 * Permet la definition des parametres de la ressource.
	 * @param params Liste des parametres de la ressource
	 * @throws ProgramException
	 */
	public void definesParameter(String... params) throws ProgramException{
		for(String param:params){
			try {
				Parameter paramValues = new Parameter(param,""){};
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
	 * Permet d'obtenir la liste des parametres de la ressource.
	 * @return La liste des parametres de la ressource.
	 */
	public Parameter[] getParameterList(){
		Collection<Parameter> list = parameters.values();
		Parameter[] result = null ;
		if(list!=null){
			Object[] objects = list.toArray();
			if(objects!=null){
				result = new Parameter[objects.length];
				int count = 0 ;
				for(Object obj:objects) result[count++] = (Parameter)obj;
			}
		}
		return result;
	}
	
	/**
	 * Permet d'obtenir la liste des noms des parametres de la ressource
	 * @return Liste des noms des parametres de la ressource.
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
	 * Affecte une valeur value au parametre parameterName, uniquement si
	 * ce parametre a ete defini. 
	 * @param parameterName Nom du parametre auquel la valeur sera affectee
	 * @param value Valeur a affecter au parametre de nom parameterName
	 */
	public void setValueTo(String parameterName,String value){
		Parameter param = parameters.get(parameterName);
		if(param!=null)	param.setValue(value);
	}
	
	/**
	 * Permet d'obtenir la valeur du parametre de la ressource portant le nom parameterName
	 * @param parameterName Nom du parametre dont on veut obtenir la valeur.
	 * @return La valeur du parametre.
	 */
	public String getValueOf(String parameterName){
		Parameter param = parameters.get(parameterName);
		if(param!=null)	return param.getValue();		
		return null;
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
