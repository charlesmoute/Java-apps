package cm.enterprise.software.component.model.base;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import cm.enterprise.software.component.model.listener.AnyListener;
import cm.enterprise.software.component.model.listener.Listener;
import cm.enterprise.software.exception.ProgramException;

/**
 * Un super modele, heritant de SuperModel, est un modele constitue d'une ensemble de modeles.
 * Ou un modele constitutif du super modele peut etre un modele simple, ie heritant de Model, ou
 * un super model, ie heritant de SuperModel. 
 * @author Charles Moute
 * @version 1.0.1, 16/07/2010
 */
public abstract class SuperModel extends Model {

 	/**
 	 * Liste des types de classes de modeles autorises;
 	 */
	private final java.util.ArrayList<Class<? extends Model>> classes = new  java.util.ArrayList<Class<? extends Model>>();
  
	/**
	 * Listes des sous-modeles du model.
	 */
	private java.util.ArrayList<Model> submodels = new java.util.ArrayList<Model>();
	
	/**
	 * Ajoute un ou plusieurs types de classe de Model comme etant des composantes du super model.
	 * @param classOfModel Ensemble de types de classe d'un objet heritant de Model.
	 */
	protected void addClassOfModel(Class<? extends Model>... classOfModel){
		for(Class<? extends Model> classe: classOfModel) if(!classes.contains(classe)) classes.add(classe);
	}
	
	/**
	 * Permet d'obtenir la liste des types de classes de modele autorise.
	 * @return La liste des types des classes des sous modeles du super modele
	 */
	@SuppressWarnings("unchecked")
	public Class[] getClassOfModelList(){
		Class[] result = new Class[classes.size()];
		int count = 0 ;
		for(Class<? extends Model> classOfModel: classes) result[count++] = classOfModel ;
		return result; 
	}
	
	/**
	 * Efface l'ensemble des classes de modele en parametre de la liste des classes de modeles admis par le super modele.
	 * Cette fonction effacera aussi les elements de ces types s'il existe. 
	 * @param classOfModel Type de modele a supprimer
	 */
	protected void removeClassOfModel(Class<? extends Model>... classOfModel){
		
		for(Class<? extends Model> classe:classOfModel){
			if(classes.contains(classe)){
				uninstallSubModel(classe);
				classes.remove(classe);
			}
		}
	}
	
	/**
	 * Dit si la classe de modele en parametre est une classe de sous model du super model. 
	 * @param classOfModel Type de classe a verifier
	 * @return true si le parametre est une classe de sous model du super model.
	 */
	public boolean isValidClassOfModel(Class<? extends Model> classOfModel){
		return classes.contains(classOfModel) ;
	}
	
	/**
	 * Cette fonction instancie un model de type la classe de modele en parametre
	 * @param classOfModel Classe de modele a instancier.
	 * @param params Les parametres avec lesquels la classe doit etre instanciee.
	 * @throws ProgramException
	 */
	@SuppressWarnings("unchecked")
	protected void installSubModel(Class<? extends Model> classOfModel, Object... params) throws ProgramException {
		if(isValidClassOfModel(classOfModel)){
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
				
				Constructor<? extends Model> constructor = classOfModel.getConstructor(typesOfClass);				
				Model submodel = constructor.newInstance(params);
				submodels.add(submodel);
				
				AnyListener[] sublisteners = this.getListeners(AnyListener.class);
				for(AnyListener listener:sublisteners) submodel.addAnyChangeListener(listener);
				
			} catch (NoSuchMethodException e) {
				String value = "";
				int count = 0 ;
				for(Object param:params){
					value+= param.getClass().getSimpleName();
					if(count<(params.length-1)) value+=", ";
					count++;
				}
				throw new ProgramException("Le constructeur public "+classOfModel.getName()+"("+value+") n'existe pas.");			
			}catch(Exception err){
				err.printStackTrace();
				throw new ProgramException(err.getMessage()); 
			}
		}
	}
	
	/**
	 * Permet d'obtenir la premiere instance de classe le parametre 
	 * @param classOfModel Classe de l'instance a retourner
	 * @return Le sous modele de classe le parametre, si il existe, sinon null est retourne.
	 */
	public <T extends Model> T getSubModel(Class<T> classOfModel){
		T[] result = this.getSubModelList(classOfModel);
		return (result==null || result.length==0)? null:result[0];
	}
	
	/**
	 * Permet d'obtenir la liste complete des sous modeles d'un super modele 
	 * @return La liste des sous modeles
	 */
	public Model[] getSubModelList(){
		Model[] result = new Model[submodels.size()];
		int i = 0 ;
		for(Model submodel: submodels) result[i++] = submodel;
		return result ;
	}
	
	/**
	 * Permet d'obtenir la liste des sous models de type de classe classOfModel
	 * @param classOfModel Classe des sous modeles a retourner
	 * @return L'ensemble des sous modeles de classe le parametre
	 */
	@SuppressWarnings("unchecked")
	public <T extends Model> T[] getSubModelList(Class<T> classOfModel){
		if(!this.isValidClassOfModel(classOfModel)) return null;
		java.util.ArrayList<Model> sublist = new java.util.ArrayList<Model>();
		for(Model submodel:submodels){
			if(classOfModel.isInstance(submodel)) sublist.add(submodel);
		}		
		T[] result = (T[])Array.newInstance(classOfModel,sublist.size());
		int i = 0 ;
		for(Model submodel: sublist) result[i++] = (T)submodel; 
		return result;
	}
	
	/**
	 * Efface un sous model de la liste des sous modeles du super modele
	 * @param submodel Sous modele a effacer
	 */
	protected void uninstallSubModel(Model submodel){
		submodels.remove(submodel);
	}
	
	/**
	 * Efface tous les sous modeles de la classe en parametre
	 * @param classOfModel Type de classe des sous modeles a effacer.
	 */
	protected void uninstallSubModel(Class<? extends Model> classOfModel){
		if(!this.isValidClassOfModel(classOfModel)) return;
		for(Model model:submodels){
			if(classOfModel.isInstance(model)) submodels.remove(model);			
		}
	}
	
	/**
	 * Efface l'ensemble des sous modeles du super modele
	 */
	protected void uninstallAllSubModel(){
		for(int i= (submodels.size()-1);i>=0;i--) submodels.remove(i);
	}
	
		
	/**
	 * Ajoute un ecouteur listener de type le deuxieme parametre a tous les sous modeles de type de classe le premier parametre.
	 * @param classOfModel Classe des sous models a qui l'ecouteur doit etre associe.
	 * @param type Type de l'ecouteur a ajouter
	 * @param listener Ecouteur a ajouter
	 */
	public <T extends Listener> void addListenerToSubModel(Class<? extends Model> classOfModel,Class<T> type,T listener){
		if(!this.isValidClassOfModel(classOfModel))return;		
		for(Model model:submodels){
			if(classOfModel.isInstance(model)) model.addListener(type,listener);
		}
	}
	
	/**
	 * Efface l'ecouteur listener instance de la classe T des sous modeles de type d classe le premier parametre.
	 * @param classOfModel Classe des sous models dont l'ecouteur doit etre supprime.
	 * @param type Type de l'ecouteur a effacer 
	 * @param listener Ecouteur a effacer
	 */
	public <T extends Listener> void removeListenerOfSubModel(Class<? extends Model> classOfModel, Class<T> type, T listener){
		if(!this.isValidClassOfModel(classOfModel))return;
		for(Model model:submodels){
			if(classOfModel.isInstance(model)) model.removeListener(type,listener);
		}
	}
	
	
	/**
	 * cette fonction propage un changement au travers des ecouteurs de type 
	 * de classe le premier parametre et de la fonction de nom le deuxieme 
	 * parametre avec comme parametre(s) le dernier parametre.
	 * @param classOfListener Classe des ecouteurs qui serviront a la propagation
	 * @param function Nom de la fonction  ecoutrice appelee
	 * @param params Parametre(s) de la fonction ecoutrice.
	 * @throws ProgramException 
	 */
	@SuppressWarnings("unchecked")
	public <T extends Listener> void fireChangeToListener(Class<T> classOfListener, String function,Object...params) throws ProgramException{
		if(classOfListener!=null && function!=null){
			T[] listenerList ;
			listenerList = (T[])this.getListeners(classOfListener);
			Class[] parameterTypes = null ;
			if(params!=null){
				parameterTypes = new Class [params.length];
				int index = 0 ;
				for(Object param:params){
					parameterTypes[index] = param.getClass();
					index++;
				}
			}
			Method method = null;
			try {
				method = classOfListener.getMethod(function, parameterTypes);
			} catch (Exception e) {
				//e.printStackTrace();
				throw new ProgramException(e.getMessage());
			}
			
			for(T listener:listenerList){
				try { method.invoke(listener, params); } catch (Exception e) {
					throw new ProgramException(e.getMessage());
				} 
			}
			
			for(Model submodel:submodels){
				T[] subListenerList = submodel.getListeners(classOfListener);
				for(T listener:subListenerList){
					try { method.invoke(listener, params); } catch (Exception e) {
						throw new ProgramException(e.getMessage());
					}
				}
			}
			
		}
	}

	/**
	 * Ajoute un ecouteur sur le changement de n'importe quelles informations du super model.
	 * Cet ecouteur est ajoute tant au super model qu'a ses sous modeles.
	 * @param listener Ecouteur a ajouter
	 */
	public void addAnyChangeListener(AnyListener listener){
		this.addListener(AnyListener.class,listener);
		for(Model model:submodels) model.addAnyChangeListener(listener);
	}
	
	/**
	 * Efface l'ecouteur de n'importe quel informations en parametre. L'efface tant du super model
	 * que de ses sous modeles.
	 * @param listener Ecouteur a effacer
	 */
	public void removeAnyChangeListener(AnyListener listener){
		this.removeListener(AnyListener.class, listener);
		for(Model model:submodels) model.removeAnyChangeListener(listener);
	}
	
	/**
	 * Propage n'importe quel changement a travers l'objet infos au modele et a tous ses sous modeles. 
	 * @param infos Objet vehiculant n'importe quelle information.
	 */
	public void fireAnyChange(Object infos){
		AnyListener[] listenerList;
		listenerList = (AnyListener[])this.getListeners(AnyListener.class);
		for(AnyListener listener: listenerList){
			listener.anyChanged(new cm.enterprise.software.component.model.event.AnyChangedEvent(this,infos));
		}		
		for(Model model:submodels)	model.fireAnyChange(infos);
	}
	
	/**
	 * Permet d'obtenir le nombre de modele composant le super modele
	 * @return Le nombre de sous modele du super modele
	 */
	public int getSubModelCount(){
		return submodels.size() ;
	}
	
	/**
	 * Permet d'obtenir le nombre de sous modele de classe classOfModel composant le super modele
	 * @param classOfModel Classe des sous modeles a compter
	 * @return Le nombre de sous modele de classe le parametre
	 */
	public int getSubModelCount(Class<? extends Model> classOfModel){
		if(!this.isValidClassOfModel(classOfModel)) return 0;
		int count = 0 ;
		for(Model m:submodels) if(classOfModel.isInstance(m)) count++;
		return count ;
	}
	
	/**
	 * Invoque methodeName de la premiere instance de type classOfModel, en lui passant en parmetre params. 
	 * @param classOfModel Classe de l'instance possedant la methode a invoquer
	 * @param methodName Nom de la methode a invoquer.
	 * @param params Parametres a passer a la methode invoquee.
	 * @return La valeur de retour de la fonction evoquee, si cette derniere en possede sinon null est renvoye.
	 * @throws ProgramException
	 */
	public <T extends Model> Object invokeMethodOfSubModel(Class<T> classOfModel,String methodName,Object... params) throws ProgramException{
		 return this.invokeMethodOfSubModel(classOfModel, 1, methodName, params);
	}
	
	/**
	 * Permet d'obtenir la methode  de nom fonction et de parametres params definie pour le sous modele
	 * de numero d'instance instanceNumber et de classe classOfModel. Le numero d'instance va de
	 * 1 a getSubModelCount(ClassOfModel). Attention l'utilisation de cette methode, necessite que
	 * la foncction a evoquer, n'est que des types objets en parametres, pas de type basique.
	 * En exemple a la place de int, elle, la fonction considerera que c'est Integer.
	 * @param classOfModel Classe ou se trouve la methode 
	 * @param instanceNumber Numero de l'instance du sous modele.
	 * @param methodName Nom de la methode a invoquer
	 * @param params Parametres de la methode.
	 * @return Si la fonction a une valeur de retour, elle est renvoyee, sinon null est retourne.
	 * @throws ProgramException 
	 * @see #getSubModelCount(Class)
	 */
	@SuppressWarnings("unchecked")
	public <T extends Model> Object invokeMethodOfSubModel(Class<T> classOfModel,int instanceNumber,String methodName,Object... params) throws ProgramException{
		if(!this.isValidClassOfModel(classOfModel)) return null;
		if(instanceNumber<1 || instanceNumber>this.getSubModelCount(classOfModel)) return null;
		
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
			Method method = classOfModel.getMethod(methodName, parameterTypes);
			Model submodel = this.getSubModelList(classOfModel)[instanceNumber-1];
			return method.invoke(submodel, params);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new ProgramException(e.getMessage());
		}
	}
}
