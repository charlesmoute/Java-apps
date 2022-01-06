package cm.enterprise.software.interfaces;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import cm.enterprise.software.exception.ProgramException;


/**
 * Ce concept permet de definir un module comme une combinaison de modules
 * @author Charles Moute
 * @version 1.0.1, 26/07/2010
 */
public abstract class SuperModule extends Module {

	/**
	 * Liste des sous modules associes du super module
	 */
	private java.util.ArrayList<Module> submodules = new java.util.ArrayList<Module>();
	
	
	public void setCurrentLanguage(String language) throws ProgramException{
		super.setCurrentLanguage(language);
		for(Module submodule:submodules) submodule.setCurrentLanguage(language);
	}

	/**
	 * Initialise les sous modules du super module
	 */
	public void initSubModule() {
		for(Module submodule:submodules) submodule.initModule();
	}
	
	/**
	 * Permet de verifier qu'il existe au moins une instance de la classe en parametre
	 * @param classOfModule Classe a verifier
	 */
	public boolean instanceModuleExists(Class<? extends Module> classOfModule){
		if(classOfModule==null) return false;
		boolean exists = false;
		for(Module submodule:submodules) { 
			if(classOfModule.isInstance(submodule)){
				exists = true ;
				break;
			}
		}
		return exists;
	}
	/**
	 * Permet de definir et d'ajouter un module au super module.
	 * @param classOfModule Classe du super module a definit et a ajouter
	 * @param params Parametres a passer au constructeur de la classe classOfModule
	 * @throws ProgramException
	 */
	@SuppressWarnings("unchecked")
	protected void installSubModule(Class<? extends Module> classOfModule, Object... params) throws ProgramException {
		if(classOfModule==null) return ;
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
			
			Constructor<? extends Module> constructor = classOfModule.getConstructor(typesOfClass);				
			Module submodule = constructor.newInstance(params);
			submodules.add(submodule);
							
		} catch (NoSuchMethodException e) {
			String value = "";
			int count = 0 ;
			for(Object param:params){
				value+= param.getClass().getSimpleName();
				if(count<(params.length-1)) value+=", ";
				count++;
			}
			throw new ProgramException("Le constructeur public "+classOfModule.getName()+"("+value+") n'existe pas.");			
		}catch(Exception err){
			err.printStackTrace();
			throw new ProgramException(err.getMessage()); 
		}
	}
	
	/**
	 * Permet d'obtenir la premiere instance de classe le parametre 
	 * @param classOfModule Classe de l'instance a retourner
	 * @return Le sous module de classe le parametre, si il existe, sinon null est retourne.
	 */
	public <T extends Module> T getSubModule(Class<T> classOfModule){
		T[] result = this.getSubModuleList(classOfModule);
		return (result==null || result.length==0)? null:result[0];
	}
	
	/**
	 * Permet d'obtenir la liste complete des sous modules d'un super module 
	 * @return La liste des sous modules
	 */
	public Module[] getSubModuleList(){
		Module[] result = new Module[submodules.size()];
		int i = 0 ;
		for(Module submodule: submodules) result[i++] = submodule;
		return result ;
	}
	
	/**
	 * Permet d'obtenir la liste des sous modules de type de classe classOfModule
	 * @param classOfModule Classe des sous modules a retourner
	 * @return L'ensemble des sous modules de classe le parametre
	 */
	@SuppressWarnings("unchecked")
	public <T extends Module> T[] getSubModuleList(Class<T> classOfModule){
		if(classOfModule==null) return null;
		java.util.ArrayList<Module> sublist = new java.util.ArrayList<Module>();
		for(Module submodule:submodules){
			if(classOfModule.isInstance(submodule)) sublist.add(submodule);
		}		
		T[] result = (T[])Array.newInstance(classOfModule,sublist.size());
		int i = 0 ;
		for(Module submodule: sublist) result[i++] = (T)submodule; 
		return result;
	}
	
	/**
	 * Efface un sous module de la liste des sous modules du super module
	 * @param submodule Sous module a effacer
	 */
	protected void uninstallSubModule(Module submodule){
		submodules.remove(submodule);
	}
	
	/**
	 * Efface tous les sous modules de la classe en parametre
	 * @param classOfModule Type de classe des sous module a effacer.
	 */
	protected void uninstallSubModule(Class<? extends Module> classOfModule){
		if(classOfModule==null) return;
		for(Module module:submodules){
			if(classOfModule.isInstance(module)) submodules.remove(module);			
		}
	}
	
	/**
	 * Efface l'ensemble des sous modules du super module
	 */
	protected void uninstallAllSubModule(){
		for(int i= (submodules.size()-1);i>=0;i--) submodules.remove(i);
	}
	
	/**
	 * Permet d'obtenir le nombre de module composant le super module
	 * @return Le nombre de sous module du super module
	 */
	public int getSubModuleCount(){
		return submodules.size() ;
	}
	
	/**
	 * Permet d'obtenir le nombre de sous module de classe classOfModule composant le super module
	 * @param classOfModule Classe des sous modules a compter
	 * @return Le nombre de sous module de classe le parametre
	 */
	public int getSubModuleCount(Class<? extends Module> classOfModule){
		if(classOfModule==null) return 0;
		int count = 0 ;
		for(Module submodule:submodules) if(classOfModule.isInstance(submodule)) count++;
		return count ;
	}
	
	/**
	 * Invoque methodeName de la premiere instance de type classOfModule, en lui passant en parmetre params. 
	 * @param classOfModule Classe de l'instance possedant la methode a invoquer
	 * @param methodName Nom de la methode a invoquer.
	 * @param params Parametres a passer a la methode invoquee.
	 * @return La valeur de retour de la fonction evoquee, si cette derniere en possede sinon null est renvoye.
	 * @throws ProgramException
	 */
	public <T extends Module> Object invokeMethodOfSubModule(Class<T> classOfModule,String methodName,Object... params) throws ProgramException{
		 return this.invokeMethodOfSubModule(classOfModule, 1, methodName, params);
	}
	
	/**
	 * Permet d'obtenir la methode  de nom fonction et de parametres params definie pour le sous module
	 * de numero d'instance instanceNumber et de classe classOfModule. Le numero d'instance va de
	 * 1 a getSubModuleCount(ClassOfModule). Attention l'utilisation de cette methode, necessite que
	 * la foncction a evoquer, n'est que des types objets en parametres, pas de type basique.
	 * En exemple a la place de int, elle, la fonction considerera que c'est Integer.
	 * @param classOfModule Classe ou se trouve la methode 
	 * @param instanceNumber Numero de l'instance du sous module.
	 * @param methodName Nom de la methode a invoquer
	 * @param params Parametres de la methode.
	 * @return Si la fonction a une valeur de retour, elle est renvoyee, sinon null est retourne.
	 * @throws ProgramException 
	 * @see #getSubModuleCount(Class)
	 */
	@SuppressWarnings("unchecked")
	public <T extends Module> Object invokeMethodOfSubModule(Class<T> classOfModule,int instanceNumber,String methodName,Object... params) throws ProgramException{
		if(instanceNumber<1 || instanceNumber>this.getSubModuleCount(classOfModule)) return null;
		
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
			Method method = classOfModule.getMethod(methodName, parameterTypes);
			Module submodule = this.getSubModuleList(classOfModule)[instanceNumber-1];
			return method.invoke(submodule, params);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new ProgramException(e.getMessage());
		}
	} 


}
