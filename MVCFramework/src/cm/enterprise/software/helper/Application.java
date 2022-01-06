package cm.enterprise.software.helper;

import java.lang.reflect.Constructor;
import java.util.Locale;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.interfaces.Module;
import cm.enterprise.software.util.MultilingualResource;
import cm.enterprise.software.util.Resource;

/**
 * Application est l'instance abstraite de votre Application. 
 * Il a pour vocation premiere de vous aider dans la definition, la manipulation, le partage 
 * et la suppression de tous les types de ressource de votre application. Par definition
 * vous ne pouvez avoir q'une seul isnstance abstraite pour votre application. Si vous en definissez 
 * plusieurs elles partageront toutes les mêmes valeurs.
 * 
 * @author Charles Moute
 * @version 1.0.1, 4/8/2010
 */
public class Application {

	/**
	 * Chemin d'acces aux  ressources du Framework
	 */
	private final static String FILE_RESSOURCE_PATH = "cm/enterprise/software/resource/" ; 
	
	
	/**
	 * Permet d'obtenir le chemin d'acces aux ressources du framework
	 * @return Chemin d'acces aux ressources fichiers du framework. 
	 */
	public final static String getFileResourcePath() { return Application.FILE_RESSOURCE_PATH; }
	
	
	/**
	 * Represente la liste des langages disponibles pour l'application.
	 */
	private static final java.util.ArrayList<String> languages = new java.util.ArrayList<String>();
	static{
		languages.add(Locale.US.getLanguage());
		languages.add(Locale.FRANCE.getLanguage());
	}
	
	/**
	 * Represente le langage courant de l'application
	 */
	private static String currentLanguage = languages.get(0);
	
	/**
	 * Represente les differentes ressources de l'application.
	 */
	private static final java.util.ArrayList<cm.enterprise.software.util.Resource> resources = new java.util.ArrayList<cm.enterprise.software.util.Resource>();
	static{
		try {
			Application.addResource(Constants.class);
			Application.addResource(ExceptionResource.class);
		} catch (ProgramException e) { e.printStackTrace();	}
	}
	
	/**
	 * Represente les differents modules ajoutes à l'application 
	 */
	private static final java.util.ArrayList<cm.enterprise.software.interfaces.Module> modules = new java.util.ArrayList<cm.enterprise.software.interfaces.Module>();
	
	/**
	 * Dit si le langage en parametre est disponible pour l'application.
	 * @param language Langage dont on veut verifier la disponibilite.
	 * @return true, si le langage est pris en compte par l'application.
	 */
	public static boolean isAvailableLanguage(String language){
		if(languages==null) return false;		
		boolean isAvailable = false ;		
		for(int i = 0;i<languages.size();i++) {
			if(languages.get(i).compareTo(language)==0){
				isAvailable = true ;
				break;
			}
		}
		return isAvailable;
	}
	
	/**
	 * Retourne la liste des langages prises en compte par l'application.
	 * @return La liste des langages disponibles pour l'application.
	 */
	public static String[] getLanguagesList() {
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
	 * Ajoute une nouvelle langue a l'application
	 * @param language Nouvelle Langue.
	 */
	public static void addLanguage(String language){
		if(language!=null && !language.isEmpty()&& !languages.contains(language)){
			languages.add(language);
		}
	}
	
	/**
	 * Efface la langue en parametre de la liste des langues disponibles pour l'application.
	 * @param language Langue a supprimer.
	 */
	public static void removeLanguage(String language){
		boolean isErasable = Locale.FRANCE.getLanguage().compareTo(language)!=0 && Locale.US.getLanguage().compareTo(language)!=0 ;
		if(!isErasable) return;
		languages.remove(language);
	}
	
	/**
	 * Permet d'obtenir La langue courante de l'application.
	 * @return Langue actuellement utilisee. 
	 */
	public static String getCurrentLanguage(){
		return currentLanguage;
	}
	
	/**
	 * Reinitialise les variables de l'application selon la langue en parametre.
	 * Ceci se fait uniquement si la langue en parametre est prise en compte.	
	 * @param language Langage a considerer comme langage courant.
	 * @throws ProgramException 
	 */	
	public static void setCurrentLanguage(String language) throws ProgramException{
		if(isAvailableLanguage(language)){
			currentLanguage = language;
			for(MultilingualResource resource:Application.getMultilingualResourceList()){
				resource.setCurrentLanguage(currentLanguage);
			}
			for(Module module:modules) module.setCurrentLanguage(currentLanguage);
		}
	}
	
		
	/**
	 * Dit s'il existe une instance de la classe de ressource en parametre.
	 * @param classOfResource Classe de l'instance a trouver.
	 * @return true si une instance de cette classe a ete trouve.
	 */
	public static boolean instanceOfResourceExists(Class<? extends Resource> classOfResource){
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
	 * Ajoute une et une seule instance pour chacune des classes en parametre. Si une autre
	 * instance existe deja aucune autre instance n'est ajoutee. La liste des classes, dont on
	 * souhaite ajouter une instance sont considere comme possedant tous un constructeur vide 
	 * si ce n'est pas le cas preferer la methode addMultilingualResource(Class, Object...).
	 * @param classOfResource Liste de classe de ressource a ajouter.
	 * @see #addResource(Class, Object...)
	 * @throws ProgramException 
	 * 
	 */
	public static void addResources(Class<? extends Resource>... classOfResource) throws ProgramException{
		for(Class<? extends Resource> classe : classOfResource){
			if(!instanceOfResourceExists(classe)){
				Constructor<? extends Resource> constructor;
				try {
					constructor = classe.getConstructor();
					Resource resource = constructor.newInstance();
					resources.add(resource);
				} catch (Exception e) {
					e.printStackTrace();
					throw new ProgramException(e.getMessage());
				}
			}
		}
				
	}
	
	/**
	 * Ajoute une et une instance de classOfResource, avec les parametres params.
	 * Si une instance existe deja aucune autre instance n'est construite
	 * @param classOfResource La classe de la ressource a instancier.
	 * @param params Parametres de l'instance a construire.
	 * @throws ProgramException 
	 */
	@SuppressWarnings("unchecked")
	public static void addResource(Class<? extends Resource> classOfResource,Object...params) throws ProgramException{
		
		if(!Application.instanceOfResourceExists(classOfResource)){
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
	public static void removeResource(Class<? extends Resource>... classOfResource){
		for(Class<? extends Resource> classe:classOfResource){
			boolean isErasable = !classe.equals(Constants.class) && !classe.equals(ExceptionResource.class);
			if(isErasable && instanceOfResourceExists(classe)){
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
	public static void removeAllResource(){
		resources.clear();
		try {
			Application.addResource(Constants.class);
			Application.addResource(ExceptionResource.class);
		} catch (ProgramException e) { e.printStackTrace();}
	}
	
	/**
	 * Permet d'obtenir la liste de toutes les ressources de l'application.
	 * @return Liste de toutes les ressources definies.
	 */
	public static Resource[] getResourceList(){
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
	public static MultilingualResource[] getMultilingualResourceList(){
		
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
	public static <T extends Resource> T getResource(Class<T> classeOfResource){
		if(!Application.instanceOfResourceExists(classeOfResource)) return null;
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
	public static int getMultilingualResourceCount(){
		int count = 0 ;
		for(Resource resource:resources) if(resource instanceof MultilingualResource) count++;
		return count;
	}
	
	/**
	 * Permet d'obtenir le nombre total des ressources associees a l'application
	 * @return Le nombre de ressource definie
	 */
	public static int getResourceCount(){		
		return resources.size();
	}
	
	/**
	 * Permet de dire si il existe une instance de module de la classe en parametre, dans l'application
	 * @param classOfModule Classe de l'instance de module a decouvrir l'existence
	 * @return true s'il existe une instance de  module de la classe en parametre, false dans le cas contraire.
	 */
	public static boolean instanceOfModuleExists(Class<? extends Module> classOfModule){
		if(classOfModule==null) return false;
		boolean exists = false;
		for(Module module:modules){
			if(classOfModule.isInstance(module)){
				exists = true;
				break;
			}
		}
		return exists;
	}
	
	/**
	 * Permet d'ajouter un module de classe classOfModule et de parametres de constructeur params.
	 * @param classOfModule Classe de l'instance a creer
	 * @param params Parametres a passeer au constructeur de la classe
	 * @throws ProgramException 
	 */
	@SuppressWarnings("unchecked")
	public static void addModule(Class<? extends Module> classOfModule,Object...params) throws ProgramException{
	
		if(!instanceOfModuleExists(classOfModule)){
			Class[] parameterTypes = null ;
			if(params!=null){
				parameterTypes = new Class [params.length];
				int index = 0 ;
				for(Object param:params){
					parameterTypes[index] = param.getClass();
					index++;
				}
			}
			Constructor<? extends Module> constructor;
			try {
				constructor = classOfModule.getConstructor(parameterTypes);
				Module module = constructor.newInstance(params);
				modules.add(module);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ProgramException(e.getMessage());
			}
		}
	}
	
	/**
	 * Efface les instances des classes de modules en parametres.
	 * @param classOfModule LIste des classes des instances de modules a effacer.
	 */
	public static void removeModule(Class<? extends Module>... classOfModule){
		for(Class<? extends Module> classe:classOfModule){
			if(instanceOfModuleExists(classe)){
				for(Module module:modules){
					if(classe.isInstance(module)){
						modules.remove(module);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Efface tous les modules de l'application
	 */
	public static void removeAllModule(){
		modules.clear();
	}
	
	/**
	 * Permet d'obtenir la liste des modules definis pour l'application courante.
	 * @return La liste des modules de l'application
	 */
	public static Module[] getModuleList(){
		Object[] objects = modules.toArray();
		Module[] result = null;
		if(objects!=null){
			result = new Module[objects.length];
			int count = 0 ;
			for(Object obj:objects) result[count++]=(Module) obj;
		}
		return result; 
	}
	
	/**
	 * Permet d'obtenir l'instance d'un module.
	 * @param classOfModule Classe de l'instance de module que l'on souhaite obtenir
	 * @return L'instance de classe classOfModule
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Module> T getModule(Class<T> classOfModule){
		if(!Application.instanceOfModuleExists(classOfModule)) return null;
		for(Module module:modules){
			if(classOfModule.isInstance(module)){
				return (T)module;
			}
		}
		return null;
	}
	
	/**
	 * Permet d'obtenir le nombre de module affecte a l'application
	 * @return Nombre de module de l'application
	 */
	public static int getModuleCount(){
		return modules.size();
	}
	
	/**
	 * Initialise tous les modules de l'application.
	 */
	public static void initModules(){
		for(Module module:modules) module.initModule();
	}


	/**
	 * Affecte le chemin d'acces au dossier des ressources externes. Par defaut ce chemin est ./config/
	 * @param path Le nouveau chemin d'acces au ressource
	 */
	public static void setResourcePath(String path){
		if(path!=null && !path.endsWith("/")) path+="/";
		Application.getResource(Constants.class).setValueTo("resourcePath2", path);
	}
	
	/**
	 * Retourne le chemin d'acces au dossier des ressources.
	 * @return Le chemin d'acces au dossier des resources.
	 */
	public static String getResourcePath() { return Application.getResource(Constants.class).getValueOf("resourcePath2"); }
	
	/**
	 * Retourne le chemin d'acces, par defaut, au dossier des ressources
	 * @return Le chemin d'acces au dossier de resource par defaut.
	 */
	public static String getDefaultResourcePath() { return "./resource/"; }
	
}
