package cm.enterprise.software.component.controller;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import cm.enterprise.software.component.model.base.SuperModel;
import cm.enterprise.software.exception.ProgramException;

/**
 * SuperController est un controleur de super modele. 
 * Il vous permet de regouper en son sein la liste des controleurs
 * de chacun des sous modeles de votre super modele. Bien evidement le soin
 * vous est laisse de les definir, si vous ne le faite pas
 * votre controleur ne sera qu'un controleur classique.
 * Tout comme un controleur, classique, un super controleur ne controle qu'un
 * super modele a la fois avec une instance de chacune des vues possibles pour
 * le super modele. Donc autant de super modele, autant de controleur.
 * 
 * @author Charles Moute
 * @version 1.0.1, 18/07/2010
 */
public abstract class SuperController extends Controller {

	/**
	 * Liste des types de controleur autorisees
	 */
	private java.util.ArrayList<Class<? extends Controller>> classes = new java.util.ArrayList<Class<? extends Controller>>();
	
	/**
	 * Liste des controleurs associes au controleur du super controller
	 */
	private java.util.ArrayList<Controller> subcontrollers = new java.util.ArrayList<Controller>();
	
	/**
	 * Construit une instance de SuperController avec le parametre comme modele a controler
	 * @param model Model a controler
	 * @throws ProgramException
	 */
	public SuperController(SuperModel model) throws ProgramException {
		super(model);
	}
	
	public SuperModel getModel() { return (SuperModel)model; }
	
	/**
	 * Ajoute un ou plusieurs types de classe de Controller comme etant des composantes du super controlleur.
	 * @param classOfController Ensemble de types de classe d'un objet heritant de Controller.
	 */
	protected void addClassOfController(Class<? extends Controller>... classOfController){
		for(Class<? extends Controller> classe: classOfController) if(!classes.contains(classe)) classes.add(classe);
	}
	
	/**
	 * Permet d'obtenir la liste des types de classes de controleur autorise.
	 * @return La liste des types des classes des controleurs composant le super controlleur.
	 */
	@SuppressWarnings("unchecked")
	public Class[] getClassOfControllerList(){
		Class[] result = new Class[classes.size()];
		int count = 0 ;
		for(Class<? extends Controller> classOfController: classes) result[count++] = classOfController ;
		return result; 
	}
	
	/**
	 * Efface l'ensemble des classes de controleur en parametre de la liste des classes de controleur
	 * admis par le super controleur. Cette fonction effacera aussi les elements de ces types s'il existe. 
	 * @param classOfController Liste des types de controleur a supprimer.
	 */
	protected void removeClassOfController(Class<? extends Controller>... classOfController){
		
		for(Class<? extends Controller> classe:classOfController){
			if(classes.contains(classe)){
				uninstallSubController(classe);
				classes.remove(classe);
			}
		}
	}
	
	
	
	/**
	 * Dit si la classe de controleur en parametre est une classe de sous controleur du super controleur. 
	 * @param classOfController Type de classe a verifier
	 * @return true si le parametre est une classe de sous controleur du super controleur.
	 */
	public boolean isValidClassOfController(Class<? extends Controller> classOfController){
		return classes.contains(classOfController) ;
	}
	
	/**
	 * Cette fonction instancie un controleur de type la classe de controleur en parametre
	 * @param classOfController Classe de controleur a instancier.
	 * @param params Les parametres avec lesquels la classe doit etre instanciee.
	 * @throws ProgramException
	 */
	@SuppressWarnings("unchecked")
	protected void installSubController(Class<? extends Controller> classOfController, Object... params) throws ProgramException {
		if(isValidClassOfController(classOfController)){
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
				subcontrollers.add(subcontroller);
								
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
	}
	
	/**
	 * Permet d'obtenir la premiere instance de classe le parametre 
	 * @param classOfController Classe de l'instance a retourner
	 * @return Le sous controleur de classe le parametre, si il existe, sinon null est retourne.
	 */
	public <T extends Controller> T getSubController(Class<T> classOfController){
		T[] result = this.getSubControllerList(classOfController);
		return (result==null || result.length==0)? null:result[0];
	}
	
	/**
	 * Permet d'obtenir la liste complete des sous controleur d'un super controleur 
	 * @return La liste des sous controleurs
	 */
	public Controller[] getSubControllerList(){
		Controller[] result = new Controller[subcontrollers.size()];
		int i = 0 ;
		for(Controller subcontroller: subcontrollers) result[i++] = subcontroller;
		return result ;
	}
	
	/**
	 * Permet d'obtenir la liste des sous controleurs de type de classe classOfController
	 * @param classOfController Classe des sous controleurs a retourner
	 * @return L'ensemble des sous controleurs de classe le parametre
	 */
	@SuppressWarnings("unchecked")
	public <T extends Controller> T[] getSubControllerList(Class<T> classOfController){
		if(!this.isValidClassOfController(classOfController)) return null;
		java.util.ArrayList<Controller> sublist = new java.util.ArrayList<Controller>();
		for(Controller subcontroller:subcontrollers){
			if(classOfController.isInstance(subcontroller)) sublist.add(subcontroller);
		}		
		T[] result = (T[])Array.newInstance(classOfController,sublist.size());
		int i = 0 ;
		for(Controller subcontroller: sublist) result[i++] = (T)subcontroller; 
		return result;
	}
	
	/**
	 * Efface un sous controleur de la liste des sous controleurs du super controleur
	 * @param subcontroller Sous controleur a effacer
	 */
	protected void uninstallSubController(Controller subcontroller){
		subcontrollers.remove(subcontroller);
	}
	
	/**
	 * Efface tous les sous controleurs de la classe en parametre
	 * @param classOfController Type de classe des sous controleurs a effacer.
	 */
	protected void uninstallSubController(Class<? extends Controller> classOfController){
		if(!this.isValidClassOfController(classOfController)) return;
		for(Controller controller:subcontrollers){
			if(classOfController.isInstance(controller)) subcontrollers.remove(controller);			
		}
	}
	
	/**
	 * Efface l'ensemble des sous controleurs du super controleur
	 */
	protected void uninstallAllSubController(){
		for(int i= (subcontrollers.size()-1);i>=0;i--) subcontrollers.remove(i);
	}
	
	/**
	 * Permet d'obtenir le nombre de controleur composant le super controleur
	 * @return Le nombre de sous controleur du super controleur
	 */
	public int getSubControllerCount(){
		return subcontrollers.size() ;
	}
	
	/**
	 * Permet d'obtenir le nombre de sous controleur de classe classOfController composant le super controleur
	 * @param classOfController Classe des sous controleurs a compter
	 * @return Le nombre de sous controleur de classe le parametre
	 */
	public int getSubControllerCount(Class<? extends Controller> classOfController){
		if(!this.isValidClassOfController(classOfController)) return 0;
		int count = 0 ;
		for(Controller subcontroller:subcontrollers) if(classOfController.isInstance(subcontroller)) count++;
		return count ;
	}
	
	/**
	 * Invoque methodeName de la premiere instance de type classOfController, en lui passant en parmetre params. 
	 * @param classOfController Classe de l'instance possedant la methode a invoquer
	 * @param methodName Nom de la methode a invoquer.
	 * @param params Parametres a passer a la methode invoquee.
	 * @return La valeur de retour de la fonction evoquee, si cette derniere en possede sinon null est renvoye.
	 * @throws ProgramException
	 */
	public <T extends Controller> Object invokeMethodOfSubController(Class<T> classOfController,String methodName,Object... params) throws ProgramException{
		 return this.invokeMethodOfSubController(classOfController, 1, methodName, params);
	}
	
	/**
	 * Permet d'obtenir la methode  de nom fonction et de parametres params definie pour le sous controleur
	 * de numero d'instance instanceNumber et de classe classOfController. Le numero d'instance va de
	 * 1 a getSubControllerCount(ClassOfController). Attention l'utilisation de cette methode, necessite que
	 * la foncction a evoquer, n'est que des types objets en parametres, pas de type basique.
	 * En exemple a la place de int, elle, la fonction considerera que c'est Integer.
	 * @param classOfController Classe ou se trouve la methode 
	 * @param instanceNumber Numero de l'instance du sous controleur.
	 * @param methodName Nom de la methode a invoquer
	 * @param params Parametres de la methode.
	 * @return Si la fonction a une valeur de retour, elle est renvoyee, sinon null est retourne.
	 * @throws ProgramException 
	 * @see #getSubControllerCount(Class)
	 */
	@SuppressWarnings("unchecked")
	public <T extends Controller> Object invokeMethodOfSubController(Class<T> classOfController,int instanceNumber,String methodName,Object... params) throws ProgramException{
		if(!this.isValidClassOfController(classOfController)) return null;
		if(instanceNumber<1 || instanceNumber>this.getSubControllerCount(classOfController)) return null;
		
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
			Method method = classOfController.getMethod(methodName, parameterTypes);
			Controller subcontroller = this.getSubControllerList(classOfController)[instanceNumber-1];
			return method.invoke(subcontroller, params);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new ProgramException(e.getMessage());
		}
	} 
}
