package cm.enterprise.software.util;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import cm.enterprise.software.exception.ProgramException;

/**
 * Une super ressource multilingue, est une ressource constituee a partir de plusieurs autres 
 * ressources, qui peuvent etre des ressources multilingues simples ou des supers ressources 
 * multilingues.
 * @author Charles Moute.
 * @version 1.0.1, 16/07/2010
 */
public abstract class SuperMultilingualResource extends MultilingualResource {

	/**
	 * Liste des classes constituant la super resource
	 */
	private java.util.ArrayList<Class<? extends MultilingualResource>> classes = new java.util.ArrayList<Class<? extends MultilingualResource>>();
	
	/**
	 * Liste des resources composant la super resource.
	 */
	private java.util.ArrayList<MultilingualResource> resources = new java.util.ArrayList<MultilingualResource>();
			
	/**
	 * Instancie une super resource.
	 * @throws ProgramException
	 */
	public SuperMultilingualResource() throws ProgramException {
		super();
	}

	
	/**
	 * Ajoute une ou plusieurs classes de resources comme types des composantes de la super ressource 
	 * @param classOfMultilingualResource Les types de classes a ajouter.
	 */
	public void addClassOfMultilingualResource(Class< ? extends MultilingualResource>... classOfMultilingualResource){
		for(Class <? extends MultilingualResource> classe:classOfMultilingualResource) if(!classes.contains(classe)) classes.add(classe);
	}
	
	/**
	 * Permet d'obtenir la liste des types de classe des composants de la super ressource. 
	 * @return Tableau des classes des composantes de la super ressource.
	 */
	@SuppressWarnings("unchecked")
	public Object[] getClassOfMultilingualResourceList() {
		Class[] result = new Class[classes.size()];
		int count = 0 ;
		for(Class<? extends MultilingualResource> type:classes) result[count++]=type;
		return result; 
	}
	
	/**
	 * Permet de savoir si la classe en parametre est une classe est admise comme classe de composant 
	 * de la super ressource. 
	 * @param classOfMultilingualResource Classe a verifier
	 * @return true si la classe en parametre est un type de classe de composant de la ressource
	 */
	public boolean isValidClassOfMultilingualResource(Class<? extends MultilingualResource> classOfMultilingualResource){
		return classes.contains(classOfMultilingualResource);
	}
	
	/**
	 * Efface le parametre de la liste des classes de ressources composant la super ressource.
	 * @param classOfMultilingualResource Classe a supprimer.
	 */
	public void removeClassOfMultilingualResource(Class<? extends MultilingualResource> classOfMultilingualResource){
		classes.remove(classOfMultilingualResource);
	}
	
	/**
	 * Permet de savoir si une instance de la classe en parametre existe deja.
	 * @param classOfMultilingualResource Classe de l'instance a trouver
	 * @return true si une instance du parametre existe deja.
	 */
	public boolean  isntanceOfMultilingualResourceExists(Class<? extends MultilingualResource> classOfMultilingualResource){
		if(classOfMultilingualResource==null) return false;
		boolean exists = false;
		for(MultilingualResource resource: resources ){
			if( classOfMultilingualResource.isInstance(resource)){
				exists = true;
				break;
			}
		}
		return exists;
	}
	
	/**
	 * Instancie une ressource de la classe en parametre si et seulement si le parametre est
	 * une classe de ressource multilingue valide et si il n'existe pas d'instance de ce type
	 * de classe.
	 * @param classOfMultilingualResource Classe de ressource multilingue a instancier
	 * @param params Les parametres a passer au constructeur de la classe.
	 * @throws ProgramException
	 */
	@SuppressWarnings("unchecked")
	public void addSubMultilingualResource(Class<? extends MultilingualResource> classOfMultilingualResource,Object... params) throws ProgramException{
		
		if(!this.isValidClassOfMultilingualResource(classOfMultilingualResource)) return ;
		if(this.isntanceOfMultilingualResourceExists(classOfMultilingualResource)) return;
		
		Class[] typesOfClass = null ;
		
		if(params!=null){
			typesOfClass = new Class [params.length];
			int i = 0 ;
			for(Object param:params){
				typesOfClass[i] = param.getClass();
				i++;
			}
		}
		
		try {
			
			Constructor<? extends MultilingualResource> constructor;
			constructor = classOfMultilingualResource.getConstructor(typesOfClass);
			MultilingualResource resource = constructor.newInstance(params);
			resources.add(resource);
			
		} catch (Exception e) {
			throw new ProgramException(e.getMessage());
		} 			
	}
	
	/**
	 * Retourne la premier instance de ressource multilingue de type de classe le parametre
	 * @param classOfMultilingualResource Classe de la ressource a retourner.
	 * @return La ressource si elle existe, null dans le cas contraire.
	 */
	@SuppressWarnings("unchecked")
	public <T extends MultilingualResource> T getSubMultilingualResource(Class<T> classOfMultilingualResource){
		if(!this.isValidClassOfMultilingualResource(classOfMultilingualResource)) return null;
		for(MultilingualResource resource:resources){
			if(classOfMultilingualResource.isInstance(resource)) return (T)resource;
		}
		return null;
	}
	
	/**
	 * Permet d'obtenir la liste complete des ressources composant la super ressource multingue 
	 * @return La liste des resources composants de la super resource.
	 */
	public MultilingualResource[] getSubMultilingualResourceList(){
		MultilingualResource[] result = new MultilingualResource[resources.size()];
		int i = 0 ;
		for(MultilingualResource resource: resources) result[i++] = resource;
		return result ;
	}
	
	/**
	 * Permet d'obtenir la liste des ressources multilingues composantes de type de classe classOfMultilingualResource
	 * @param classOfMultilingualResource Classe des ressources composantes a retourner
	 * @return L'ensemble des sous ressources de la classe le parametre
	 */
	@SuppressWarnings("unchecked")
	public <T extends MultilingualResource> T[] getSubMultilingualResourceList(Class<T> classOfMultilingualResource){
		
		if(!this.isValidClassOfMultilingualResource(classOfMultilingualResource)) return null;
		java.util.ArrayList<MultilingualResource> sublist = new java.util.ArrayList<MultilingualResource>();
		
		for(MultilingualResource resource:resources){
			if(classOfMultilingualResource.isInstance(resource)) sublist.add(resource);
		}		
		
		T[] result = (T[])Array.newInstance(classOfMultilingualResource, sublist.size());
		int i = 0 ;
		for(MultilingualResource resource: sublist) result[i++] =(T)resource; 
		return result;
	}
	
	/**
	 * Efface un resource composante de la liste des resources composant la super resource.
	 * @param resource Ressource composante a effacer
	 */
	public void removeSubMultilingualResource(MultilingualResource resource){
		resources.remove(resource);
	}
	
	/**
	 * Efface toutes les ressources composantes de type de classe la classe en parametre
	 * @param classOfMultilingualResource Type de classe des ressources composantes a effacer.
	 */
	public void removeSubMultilingualResource(Class<? extends MultilingualResource> classOfMultilingualResource){
		if(!this.isValidClassOfMultilingualResource(classOfMultilingualResource)) return;
		for(MultilingualResource resource:resources){
			if(classOfMultilingualResource.isInstance(resource)) resources.remove(resource);			
		}
	}
	
	/**
	 * Efface l'ensemble des resources composant la super resource.
	 */
	public void removeAllSubMultilingualResource(){
		for(int i= (resources.size()-1);i>=0;i--) resources.remove(i);
	}
		
	/**
	 * Charge l'ensemble des sous ressources de la super ressource en fonction du parametre. 
	 * Fonction permettant de gerer le multilinguisme des sous ressources de la super ressource.
	 * @param language Langue a utilise pour le chargement des sous ressources la super ressource.
	 * @throws ProgramException
	 */
	protected void loadSubMultilingualRessource(String language) throws ProgramException {
		for(MultilingualResource resource:resources) resource.load(language);
	}
	
	/**
	 * Permet d'obtenir le nombre de ressource constituant la super ressource.
	 * @return Le nombre de sous ressource composant la super ressource.
	 */
	public int getSubMultilingualResourceCount(){
		return resources.size() ;
	}
	
	/**
	 * Permet d'obtenir le nombre d'instance de ressource de classe le paramtre
	 * @param classOfMultilingualResource Classe des instances a compter
	 * @return Le nombre d'instance de classe le parametre composant la super ressource.
	 */
	public int getSubMultilingualResourceCount(Class<? extends MultilingualResource> classOfMultilingualResource){
		if(!this.isValidClassOfMultilingualResource(classOfMultilingualResource)) return 0;
		int count = 0 ;
		for(MultilingualResource resource:resources) if(classOfMultilingualResource.isInstance(resource)) count++;
		return count ;
	}
	
	/**
	 * Invoque methodName de la premiere instance de classe classOfMultilingualResource avec comme parametre params.
	 * @param classOfMultilingualResource Classe de l'instance contenant la methode a invoquer.
	 * @param methodName Nom de la methode a invoquer
	 * @param params Parametre a passer a la methode invoquer
	 * @return La valeur de retour de la methode, si elle en possede, sinon la valeur null est retournee.
	 * @throws ProgramException
	 */
	public <T extends MultilingualResource> Object invokeMethodOfSubMultilingualResource(Class<T> classOfMultilingualResource,String methodName,Object... params) throws ProgramException{
		 return this.invokeMethodOfSubMultilingualResource(classOfMultilingualResource, 1, methodName, params);
	}
	
	/**
	 * Invoque methodName de l'instance de numero instanceNumber de classe classOfMultilingualResource avec comme parametre params.
	 * @param classOfMultilingualResource Classe de l'instance contenant la methode a invoquer
	 * @param instanceNumber Numero de l'instance contenant la methode a invoquer
	 * @param methodName Nom de la methode a invoquer
	 * @param params Parametre de la methode a invoquer
	 * @return La valeur de retour de la methode invoquee, si elle en a, sinon la valeur null est retournee.
	 * @throws ProgramException
	 */
	@SuppressWarnings("unchecked")
	public <T extends MultilingualResource> Object invokeMethodOfSubMultilingualResource(Class<T> classOfMultilingualResource,int instanceNumber,String methodName,Object... params) throws ProgramException{
		
		if(!this.isValidClassOfMultilingualResource(classOfMultilingualResource)) return null;
		if(instanceNumber<1 || instanceNumber>this.getSubMultilingualResourceCount(classOfMultilingualResource)) return null;
		
		Class[] parameterTypes = null ;
		if(params!=null){
			parameterTypes = new Class [params.length];
			int index = 0 ;
			for(Object param:params){
				parameterTypes[index] = param.getClass();
				index++;
			}
		}

		try {
			Method method = classOfMultilingualResource.getMethod(methodName, parameterTypes);
			MultilingualResource resource = this.getSubMultilingualResourceList(classOfMultilingualResource)[instanceNumber-1];
			return method.invoke(resource, params);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new ProgramException(e.getMessage());
		}
	}
		
	@Override
	public void setCurrentLanguage(String language) throws ProgramException{
		super.setCurrentLanguage(language);
		for(MultilingualResource resource:resources) resource.setCurrentLanguage(language);
	}
	
	@Override
	public void setDefaultLanguage(String newDefaultLanguage){		
		super.setDefaultLanguage(newDefaultLanguage);
		for(MultilingualResource resource:resources) resource.setDefaultLanguage(newDefaultLanguage);
	}
}
