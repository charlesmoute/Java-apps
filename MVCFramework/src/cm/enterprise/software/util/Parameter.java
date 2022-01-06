package cm.enterprise.software.util;

import cm.enterprise.software.exception.ErroneousParameterException;
import cm.enterprise.software.exception.ProgramException;

/**
 * Parameter represente la notion de parametre pour une ressource.
 * Ce parametre a un nom et une valeur. Un parametre d'une ressource
 * representant un ensemble de fichier image peut avoir comme parametre
 * une image de nom le nom de l'image et de valeur le chemin a cette image.
 * Seul notre imagination est notre limite.
 * 
 * @author Charles Moute
 * @version 1.0.1, 24/07/2010 
 */
public abstract class Parameter {
	
	/**
	 * Nom du parametre de la ressource 
	 */
	protected String name = null ;
	
	/**
	 * Valeur du parametre
	 */
	protected String value = null;
		
	/**
	 * Construit une instance vide de parametrer avec un nom par defaut et une valeur
	 * vide.
	 */
	public Parameter(){
		this.name = this.getClass().getCanonicalName();
		this.value = this.getClass().toString();
	}
	/**
	 * Contruit une instance de Parameter avec un nom par defaut
	 * et la valeur le paremtre
	 * @param value valeur du parametre
	 */
	public Parameter(String value){
		this.name = this.getClass().getSimpleName();
		this.setValue(value);
	}
	/**
	 * Construit une Instance de parametre name et de valeur value.
	 * @param name Nom de la ressource
	 * @param value Valeur de la ressource.
	 * @throws ProgramException
	 */
	public Parameter(String name,String value) throws ProgramException{
		this.setName(name);
		this.setValue(value);
	}
	
	/**
	 * Affecte un nouveau nom a l'instance courante.
	 * @param name Nouveau nom de l'instance courante.
	 * @throws ErroneousParameterException 
	 */
	public void setName(String name) throws ErroneousParameterException{
		if(name==null || name.isEmpty()) throw new ErroneousParameterException();
		this.name = name ;
	}
	
	/**
	 * Permet d'obtenir le nom de l'instance courante 
	 * @return Nom de l'instance courante.
	 */
	public String getName(){
		return this.name ;
	}

	/**
	 * Affecte une nouvelle valeur a l'instance courante.
	 * @param value Nouvelle valeur de l'instance courante.
	 */
	public void setValue(String value){
		this.value = value ;
	}
	
	/**
	 * Permet d'obtenir la valeur de l'instance courante.
	 * @return La valeur de l'instance courante.
	 */
	public String getValue(){
		return this.value;
	}	
}
