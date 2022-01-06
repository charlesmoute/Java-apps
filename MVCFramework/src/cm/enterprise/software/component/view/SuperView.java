package cm.enterprise.software.component.view;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import cm.enterprise.software.component.controller.SuperController;
import cm.enterprise.software.exception.ProgramException;

/**
 * Une super vue est une vue composee de plusieurs autres vues, qui peuvent etre des
 * vues simples, ou des supers vues.
 * @author Charles Moute
 * @version 1.0.1, 16/07/2010
 */
public abstract class SuperView extends View {

	/**
	 * Represente la liste des types de vues composantes de la super vue.
	 */
	private java.util.ArrayList<Class<? extends View>> classes = new java.util.ArrayList<Class<? extends View>>();
	
	/**
	 * La liste des vues composant la super vue.
	 */
	private java.util.ArrayList<View> subviews = new java.util.ArrayList<View>();
	
	/**
	 * Cree une instance d'une super vue avec un super controlleur. 
	 * @param controller Super controlleur de la super vue/
	 * @throws ProgramException
	 */
	public SuperView(SuperController controller) throws ProgramException {
		super(controller);
	}
		
	public SuperController getController() { return (SuperController)controller; }
	
	/**
	 * Ajoute une ou plusieurs classes de vues comme composantes de la super vue.
	 * @param classOfView Classes de vue a ajouter
	 */
	protected void addClassOfView(Class<? extends View>... classOfView){
		for(Class<? extends View> classe:classOfView) if(!classes.contains(classe)) classes.add(classe);
	}
	
	/**
	 * Permet de savoir si la classe de vue en parametre est une composante valide 
	 * @param classOfView Classe de vue a verifier
	 * @return true si le parametre est une composante valide de la super vue.
	 */
	public boolean isValidClassOfView(Class<? extends View> classOfView){
		return this.classes.contains(classOfView);
	}
	
	/**
	 * Permet d'obtenir la liste des types de classes de vue admis comme composante de la super vue.
	 * @return Un tableau des vues composantes de la super vue.
	 */
	@SuppressWarnings("unchecked")
	public Class[] getClassOfViewList(){
		Class[] result = new Class[classes.size()];
		int count = 0 ;
		for(Class<?extends View> type:classes ) result[count++] = type;
		return result; 
	}
	
	/**
	 * Efface la classe de vue en parametre de la liste des classes de vues admises comme composantes
	 * de la super vue.
	 * @param classOfView Classe de vue a effacer
	 */
	protected void removeClassOfView(Class<? extends View > classOfView){
		classes.remove(classOfView);
	}
	
	/**
	 * Instancie une vue de la classe en parametre. Si et seulement ce type de vue
	 * est une vue valide de la super vue.
	 * @param classOfView Classe de la vue a instancier
	 * @param params de la vue a instancier
	 * @throws ProgramException 
	 */
	@SuppressWarnings("unchecked")
	protected void installSubView(Class<? extends View> classOfView,Object... params) throws ProgramException{
		
		if(!this.isValidClassOfView(classOfView)) return ;
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
			
			Constructor<? extends View> constructor;
			constructor = classOfView.getConstructor(typesOfClass);
			View view = constructor.newInstance(params);
			subviews.add(view);
			
		} catch (Exception e) {
			throw new ProgramException(e.getMessage());
		} 			
		
	}

	/**
	 * Permet d'obtenir la premiere instance de vue de type de classe le parametre
	 * @param classOfView Classe de la sous vue a retourner
	 * @return Premiere Instance de classe le parametre, si elle existe, sinon null. 
	 */
	public <T extends View> T getSubView(Class<T> classOfView){
		T[] result = this.getSubViewList(classOfView);
		return (result==null || result.length==0)? null:result[0]; 
	}
	
	/**
	 * Permet d'obtenir la liste complete des vues composant la super vue 
	 * @return La liste des vues composantes de la super vue.
	 */
	public View[] getSubViewList(){
		View[] result = new View[subviews.size()];
		int i = 0 ;
		for(View view: subviews) result[i++] = view;
		return result ;
	}
	
	/**
	 * Permet d'obtenir la liste des vues composantes de type de classe classOfView
	 * @param classOfView Classe des vues composantes a retourner
	 * @return L'ensemble des sous vues de la classe le parametre
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T[] getSubViewList(Class<T> classOfView){
		
		if(!this.isValidClassOfView(classOfView)) return null;
		java.util.ArrayList<View> sublist = new java.util.ArrayList<View>();
		
		for(View view:subviews){
			if(classOfView.isInstance(view)) sublist.add(view);
		}		
		
		T[] result = (T[]) Array.newInstance(classOfView, sublist.size());
		int i = 0 ;
		for(View view: sublist) result[i++] = (T)view; 
		return result;
	}
	
	/**
	 * Permet d'obtenir le nombre de sous vues instanciees.
	 * @return Le nombre de vues composant la super vue.
	 */
	public int getSubViewCount() { return subviews.size(); }
	
	/**
	 * Permet d'obtenir le nombre de sous vues d'instance la classe en parametre.
	 * @param classOfView Classe des instances de vues a compter
	 * @return Le nombre de sous vue instanciee de classe le parametre
	 */
	public int getSubViewCount(Class<? extends View> classOfView){
		int count = 0 ;
		if(!this.isValidClassOfView(classOfView)) return count;
		for(View view:subviews) if(classOfView.isInstance(view)) count++;
		return count;
	}
	
	/**
	 * Efface un vue composante de la liste des vues composant la super vue.
	 * @param view Vue composante a effacer
	 */
	protected void uninstallSubView(View view){
		subviews.remove(view);
	}
	
	/**
	 * Efface toutes les vues composantes de type de classe la classe en parametre
	 * @param classOfView Type de classe des vues composantes a effacer.
	 */
	protected void uninstallSubView(Class<? extends View> classOfView){
		if(!this.isValidClassOfView(classOfView)) return;
		for(View view:subviews){
			if(classOfView.isInstance(view)) subviews.remove(view);			
		}
	}
	
	/**
	 * Efface l'ensemble des vues composant la super vue.
	 */
	protected void uninstallAllSubModel(){
		for(int i= (subviews.size()-1);i>=0;i--) subviews.remove(i);
	}
	
	/**
	 * Invoque methodName de la premiere instance de type classOfView avec les parametres params.
	 * <strong>NB:</strong> La methode a invoquer ne doit avoir que des objets en parametre et non des types basiques.
	 * Donc si votre methode avec un parametre int remplace le int par Integer, ainsi de suite.  
	 * @param classOfView Classe de l'instance possedant la methode a invoquer
	 * @param methodName Nom de la methode a invoquer
	 * @param params Parametre a passer a la methode invoquer.
	 * @return La valeur de retour de la methode invoquer, ou null dans le cas ou cette methode n'a pas de valeur de retour.
	 * @throws ProgramException
	 */
	public <T extends View> Object invokeMethodOfSubView(Class<T> classOfView,String methodName,Object... params) throws ProgramException{
		return this.invokeMethodOfSubView(classOfView,1, methodName, params);
	}
	
	/**
	 * Invoque la methode de nom methodName de la classe classOfView avec les parametres params pour 
	 * l'instance de numero instanceNumber et de type de classe classOfView.<br><br> 
	 * <strong> instanceNumber doit etre entre 1 et getSubViewCount(classOfView), les bornes comprises</strong><br>
	 * <strong>NB:</strong> La methode a invoquer ne doit avoir que des objets en parametre et non des types basiques.
	 * Donc si votre methode avec un parametre int remplace le int par Integer, ainsi de suite.   
	 * @param classOfView Classe de l'instance possedant la methode a invoquer
	 * @param instanceNumber Numero d'instance de la vue
	 * @param methodName Nom de la methode a invoquer
	 * @param params Parametres de la methode a invoquer.
	 * @return La valeur de retour de la methode invoquer, ou null si cette derniere n'a pas de valeur de retour
	 * @throws ProgramException
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> Object invokeMethodOfSubView(Class<T> classOfView,int instanceNumber,String methodName,Object... params) throws ProgramException{
		
		if(!this.isValidClassOfView(classOfView)) return null;
		if(instanceNumber<1  || instanceNumber>this.getSubViewCount(classOfView)) return null;
		
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
			Method method = classOfView.getMethod(methodName, parameterTypes);
			View subview = this.getSubViewList(classOfView)[instanceNumber-1];
			return method.invoke(subview, params);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new ProgramException(e.getMessage());
		}
	}
}
