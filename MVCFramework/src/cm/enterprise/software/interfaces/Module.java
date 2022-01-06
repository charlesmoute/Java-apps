package cm.enterprise.software.interfaces;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Set;

import cm.enterprise.software.component.controller.Controller;
import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.util.MultilingualResource;
import cm.enterprise.software.util.Parameter;
import cm.enterprise.software.util.Resource;

/**
 * La classe Module permet d'implementer le concept de composant reutilisable.
 * Ceci en demanant, a l'auteur d'un module de fournir certaines informations
 * jugees importante pour l'utilisation de son module par des tiers.
 * @author Charles Moute
 * @version 1.0.1, 25/07/2010
 */
public abstract class Module {
	
	/**
	 * Liste des parametres du module
	 */
	private java.util.Map<String,Parameter> parameters = new java.util.HashMap<String, Parameter>();
	
	/**
	 * Represente la liste des langages disponibles pour le module.
	 */
	private final java.util.ArrayList<String> languages = new java.util.ArrayList<String>();
	
	/**
	 * Represente les differentes ressources du module.
	 */
	private final java.util.ArrayList<Resource> resources = new java.util.ArrayList<Resource>();
	
	/**
	 * Represente la  liste des controleurs que le module possede
	 */
	private final java.util.ArrayList<Controller> controller = new java.util.ArrayList<Controller>();
	

	public Module(){
		//Definition de quelques parametres que doit avoir le module.
		try {
			definesParameter("author","name","about","defaultLanguage","currentLanguage","resourcePath");			
		} catch (ProgramException e) { e.printStackTrace();	}
	}
	
	/**
	 * Permet la definition des parametres du module.
	 * @param params Liste des parametres du module
	 * @throws ProgramException
	 */
	protected void definesParameter(String... params) throws ProgramException{
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
	protected boolean parameterIsDefined(String parameterName){
		return parameters.containsKey(parameterName);
	}
		
	/**
	 * Permet d'obtenir la liste des noms des parametres du module
	 * @return Liste des noms des parametres du module.
	 */
	protected String[] getParameterNameList(){
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
	protected void setValueTo(String parameterName,String value){
		Parameter param = parameters.get(parameterName);
		if(param!=null)	param.setValue(value);
	}
	
	/**
	 * Permet d'obtenir la valeur du parametre du module portant le nom parameterName
	 * @param parameterName Nom du parametre dont on veut obtenir la valeur.
	 * @return La valeur du parametre.
	 */
	protected String getValueOf(String parameterName){
		Parameter param = parameters.get(parameterName);
		if(param!=null)	return param.getValue();		
		return null;
	}
	
	/**
	 * Efface le parametre de nom parameterName
	 * @param parameterName Nom du parametre a effacer.
	 */
	protected void removeParameter(String parameterName){ parameters.remove(parameterName); }
	
	/**
	 * Nettoie le module de tous ses parametres en les effacant tous.
	 */
	protected void clear(){	parameters.clear();	}
	
	/**
	 * Permet l'initialisation du module. Entre autre la definition du module dans 
	 * le Helper de l'application et l'initialisation du controller avec les bons 
	 * parametres.
	 */
	public abstract void initModule();
	
	/**
	 * Affecte un nom au module
	 * @param name Nom du module
	 */
	protected void setModuleName(String name){
		this.setValueTo("name", name);
	}
	
	/**
	 * Permet d'obtenir le nom du module 
	 * @return Le nom du module
	 */
	public String getModuleName() { return this.getValueOf("name"); }
	
	/**
	 * Affecte le parametre comme nom de l'auteur du module
	 * @param author Auteur du module.
	 */
	protected void setModuleAuthor(String author){
		this.setValueTo("author", author);
	}
	
	/**
	 * Permet d'obtenir le nom de l'auteur du module
	 * @return Nom de l'auteur du module
	 */
	public String getModuleAuthor() { return this.getValueOf("author"); }
	
	/**
	 * Affecte une description du module 
	 * @param moduleDescription Description du module.
	 */
	protected void setAbout(String moduleDescription){
		this.setValueTo("about", moduleDescription);
	}
	
	
	/**
	 * Permet a l'auteur du module de donner une description du module.
	 * @return La description du module.
	 */
	public String getAbout() { return this.getValueOf("about"); }
	
	/**
	 * Affecte le chemin d'acces en parametre comme chemin d'acces aux resources du module.
	 * @param resourcePath Chemin d'acces aux ressources
	 */
	protected void setResourcePath(String resourcePath){
		if(resourcePath!=null && !resourcePath.endsWith("/")) resourcePath+="/";	
		this.setValueTo("resourcePath", resourcePath);
	}
	
	/**
	 * Permet d'obtenir le chemin d'acces aux ressources du module.
	 * @return Le chemin d'acces ax ressources du module.
	 */
	public String getResourcePath(){
		return this.getValueOf("resourcePath");
	}
	
	/**
	 * Ajoute une ou plusieurs langues au module
	 * @param languages Liste des langues a ajouter.
	 */
	protected void addLanguage(String... languages){
		for(String language:languages){
			if(language!=null && !language.isEmpty()&& !this.languages.contains(language)){
				this.languages.add(language);
			}
		}
	}
	
	/**
	 * Efface la liste des langues en parametre de la liste des langues disponibles pour l'application.
	 * @param languages Langues a supprimer.
	 */
	protected void removeLanguage(String... languages){
		for(String language:languages) this.languages.remove(language);
	}
	
	/**
	 * Permet d'obtenir la liste des langues supportees par le module.
	 * @return La liste des langues supportees par le module
	 */
	public String[] getSupportedLanguages(){
		Object[] objects = languages.toArray();
		String[] result = null;
		if(objects!=null){
			result = new String[objects.length];
			int count = 0 ;
			for(Object obj:objects) result[count++] = obj.toString();
		}
		return result;
	}
	
	
	/**
	 * Dit si le langage en parametre est supporte par le module.
	 * @param language Langage dont on veut verifier la disponibilite.
	 * @return true, si le langage est pris en compte par le module.
	 */
	public boolean isSupportedLanguage(String language){
		if(languages==null) return false;		
		boolean isAvailable = false ;		
		for(String lang:languages) {
			if(lang.compareTo(language)==0){
				isAvailable = true ;
				break;
			}
		}
		return isAvailable;
	}
	
	/**
	 * Avant d'utiliser cette fonction assurez vous d'avoir definir les langues
	 * supportees par le module. Car si la langue en parametre n'est pas une langue
	 * supportee par le module alors elle ne sera pas prise en compte.
	 * @param language Langue par defaut du module
	 */
	protected void setDefaultLanguage(String language){
		if(!this.isSupportedLanguage(language)) return;
		this.setValueTo("defaultLanguage", language);
		for(MultilingualResource resource:this.getMultilingualResourceList())
			resource.setDefaultLanguage(language);
	}
	/**
	 * Permet d'obtenir la langue par defaut du module
	 * @return La langue par defaut du module.
	 */
	public String getDefaultLanguage() { return this.getValueOf("defaultLanguage"); };
		
	/**
	 * Permet de changer la langue courante du module en la langue en parametre. 
	 * @param language Nouvelle langue du module.
	 * @throws ProgramException 
	 */
	public void setCurrentLanguage(String language) throws ProgramException{
		boolean isSupportedLanguage = languages.contains(language);		
		String currentLanguage = (!isSupportedLanguage)? this.getDefaultLanguage():language;
		this.setValueTo("currentLanguage", currentLanguage);
		for(MultilingualResource resource:this.getMultilingualResourceList()) 
			resource.setCurrentLanguage(language);
	}
	
	/**
	 * Permet d'obtenir la langue courante du module
	 * @return La langue courante du module
	 */
	public String getCurrentLanguage(){
		return this.getValueOf("currentLanguage");
	}
			
	/**
	 * Dit s'il existe une instance de la classe de ressource en parametre.
	 * @param classOfResource Classe de l'instance a trouver.
	 * @return true si une instance de cette classe a ete trouve.
	 */
	public boolean instanceOfResourceExists(Class<? extends Resource> classOfResource){
		if(classOfResource==null) return false;
		boolean exists = false;
		for(Resource resource:resources){
			if(classOfResource.isInstance(resource)){
				exists = true;
				break;
			}
		}
		return exists;
	}
	
	/**
	 * Permet de definir et d'ajouter, une ressource de classe classOfResource et de parametre de
	 * construction params.
	 * @param classOfResource Classe de la ressource a dfinir et a ajouter.
	 * @param params Parametre a passer au constructeur de la ressource.
	 * @throws ProgramException
	 */
	@SuppressWarnings("unchecked")
	protected void addResource(Class<? extends Resource> classOfResource,Object...params) throws ProgramException{
		if(!instanceOfResourceExists(classOfResource)){
			Class[] parameterTypes = null ;
			if(params!=null){
				parameterTypes = new Class [params.length];
				int index = 0 ;
				for(Object param:params){
					parameterTypes[index] = param.getClass();
					index++;
				}
			}
			Constructor<? extends Resource> constructor;
			try {
				constructor = classOfResource.getConstructor(parameterTypes);
				Resource resource = constructor.newInstance(params);
				resources.add(resource);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ProgramException(e.getMessage());
			}
		}
	}
	
	/**
	 * Efface les instances de ressources, de type les classes en parametres.
	 * @param classOfResource Classes des instances a supprimer.
	 */
	protected void removeResource(Class<? extends Resource>... classOfResource){
		for(Class<? extends Resource> classe:classOfResource){
			if(instanceOfResourceExists(classe)){
				for(Resource resource:resources){
					if(classe.isInstance(resource)){
						resources.remove(resource);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Efface toutes les ressources d'une application.
	 */
	protected void removeAllResource(){
		resources.clear();		
	}
	
	/**
	 * Permet d'obtenir la liste de toutes les ressources du module.
	 * @return Liste de toutes les ressources definies.
	 */
	public Resource[] getResourceList(){
		Object[] objects = resources.toArray();
		Resource[] result = null;
		if(objects!=null){
			result = new Resource[objects.length];
			int count = 0 ;
			for(Object obj:objects) result[count++]=(Resource) obj;
		}
		return result; 
	}
	
	/**
	 * Retourne la liste des ressources multilingues prises en charge.
	 * @return Un ensemble d'objet representant des ressources multilingues.
	 */
	public MultilingualResource[] getMultilingualResourceList(){
		
		java.util.ArrayList<MultilingualResource> subList = new java.util.ArrayList<MultilingualResource>();
		for(Resource resource:resources){
			if(MultilingualResource.class.isInstance(resource)) subList.add((MultilingualResource)resource);
		}		
		MultilingualResource[] result = new MultilingualResource[subList.size()];
		int count = 0 ;
		for(MultilingualResource resource:subList) result[count++]= resource;
		return result; 
	}
	
	/**
	 * Permet d'obtenir l'instance de la classe en parametre. 
	 * @param classeOfResource Classe de l'instance a retourner
	 * @return L'instance de type de classe le parametre, si elle existe sinon null est retourne.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Resource> T getResource(Class<T> classeOfResource){
		if(!this.instanceOfResourceExists(classeOfResource)) return null;
		for(Resource resource:resources){
			if(classeOfResource.isInstance(resource)){
				return (T)resource;
			}
		}
		return null;
	}
	
	
	/**
	 * Permet d'obtenir le nombre de ressource multilingue definie
	 * @return Le nombre de ressource multilingue
	 */
	public int getMultilingualResourceCount(){
		int count = 0 ;
		for(Resource resource:resources) if(resource instanceof MultilingualResource) count++;
		return count;
	}
	
	/**
	 * Permet d'obtenir le nombre total des ressources associees a l'application
	 * @return Le nombre de ressource definie
	 */
	public int getResourceCount(){		
		return resources.size();
	}
	
	/**
	 * Instancie et ajoute un controleur de classe classOfController et de parametres params a votre module.
	 * @param classOfController Classe du controlleur a Instancier
	 * @param params Parametre a passer lors de l'instanciation du controleur de classe classOfController
	 * @throws ProgramException
	 */
	@SuppressWarnings("unchecked")
	protected void addController(Class<? extends Controller> classOfController,Object...params) throws ProgramException{
		try {
			
			Class[] typesOfClass = null ;
			if(params!=null){
				typesOfClass = new Class [params.length];
				int i = 0 ;
				for(Object param:params){
					typesOfClass[i] = param.getClass();
					i++;
				}
			}
			
			Constructor<? extends Controller> constructor = classOfController.getConstructor(typesOfClass);				
			Controller subcontroller = constructor.newInstance(params);
			controller.add(subcontroller);
							
		} catch (NoSuchMethodException e) {
			String value = "";
			int count = 0 ;
			for(Object param:params){
				value+= param.getClass().getSimpleName();
				if(count<(params.length-1)) value+=", ";
				count++;
			}
			throw new ProgramException("Le constructeur public "+classOfController.getName()+"("+value+") n'existe pas.");			
		}catch(Exception err){
			err.printStackTrace();
			throw new ProgramException(err.getMessage()); 
		}
	}
	
	/**
	 * Efface toutes les instances de controleurs de classe l'un des parametres 
	 * @param classOfController Liste des classes des instances de controleur a effacer.
	 */
	protected void removeController(Class<? extends Controller>... classOfController){
		for(Class<? extends Controller> classe :classOfController ){
			for(Controller param:controller ){
				if(classe.isInstance(param)) controller.remove(param);
			}
		}
	}
	
	/**
	 * Permet d'obtenir la liste des controleurs du module
	 * @return La liste des controleurs du module.
	 */
	protected Controller[] getListOfController(){
		Controller[] result = new Controller[controller.size()];
		int i = 0 ;
		for(Controller param: controller) result[i++] = param;
		return result ;
	}
	
	/**
	 * Permet d'obtenir la liste des controleurs du module de classe classOfController 
	 * @param classOfController Classe des instances des controleurs du module a retourner
	 * @return Les controleurs d'instance de classe le parametre.
	 */
	@SuppressWarnings("unchecked")
	protected <T extends Controller> T[] getListOfController(Class<T> classOfController){
		java.util.ArrayList<Controller> sublist = new java.util.ArrayList<Controller>();
		for(Controller param:controller){
			if(classOfController.isInstance(param)) sublist.add(param);
		}		
		T[] result = (T[])Array.newInstance(classOfController,sublist.size());
		int i = 0 ;
		for(Controller param: sublist) result[i++] = (T)param; 
		return result;
	}
	
	/**
	 * Permet d'obtenir l'instance du controleur de numero numInstance et de classe classOfController.
	 * @param classOfController Classe de l'instance du controleur a retourner.
	 * @param numInstance Numero de l'instance du controlleur de 1 a getControllerCount(classOfController)
	 * @return L'instance du controleur du module de classe classOfController.
	 * @see #getControllerCount(Class)
	 */
	protected <T extends Controller> T getController(Class<T> classOfController,int numInstance){
		T[] result = this.getListOfController(classOfController);
		if(numInstance<1 || numInstance>result.length) return null;
		return result[numInstance-1];
	}
	
	/**
	 * Permet d'obtenir la premiere instance de controleur  de classe classOfController
	 * @param classOfController Classe du controleur a retourner 
	 * @return La premier instance de classe classOfController
	 */
	protected <T extends Controller> T getController(Class<T> classOfController){
		return this.getController(classOfController,1);
	}
	
	/**
	 * Permet d'obtenir le nombre de controleur defini par le module.
	 * @return Le nombre de controleur defini par le module.
	 */
	protected int getControllerCount(){
		return controller.size();
	}
	
	/**
	 * Permet d'obtenir le nombre de controleur de classe classOfController defini par le 
	 * module. 
	 * @param classOfController Classe des instances de controlleur a compter
	 * @return Le nombre des instances de classe le parametre
	 */
	@SuppressWarnings("null")
	protected int getControllerCount(Class<? extends Controller> classOfController){
		int count = 0 ;
		if(classOfController!=null) return count;
		for(Controller param:controller) if(classOfController.isInstance(param)) count++;
		return count;
	}
	
	public String toString(){
		String infos = "";
		infos+=" Auteur : "+this.getModuleAuthor()+"\n";
		infos+=" Module : "+this.getModuleName()+"\n";
		infos+=" Liste des Langues : "+ Arrays.toString(this.getSupportedLanguages())+"\n";
		infos+=" Langue par defaut : "+this.getDefaultLanguage()+"\n";
		infos+=" Langue courante : "+this.getCurrentLanguage()+"\n";
		infos+=" La Liste des parametres du modules : "+Arrays.toString(this.getParameterNameList())+"\n";
		infos+=" A propos : "+this.getAbout();
		return infos;
	}
}
