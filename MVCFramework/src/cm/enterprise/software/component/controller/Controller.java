package cm.enterprise.software.component.controller;

import java.lang.reflect.Constructor;

import cm.enterprise.software.component.view.View;
import cm.enterprise.software.component.model.base.Model;
import cm.enterprise.software.component.model.listener.Listener;
import cm.enterprise.software.exception.ErroneousParameterException;
import cm.enterprise.software.exception.ProgramException;

/**
 * Controller represente l'ossature que doivent avoir les controlleurs. Un controlleur
 * ne controle qu'un modele a un moment donne, et une instance de chaqu'une des vues
 * possible pour le modele. Donc autant de modele, autant de controlleur.
 * @author Charles Moute
 * @version 1.0.1, 28/06/2010
 */

public abstract class Controller {

	/**
	 * Le modele controle
	 */
	protected Model model = null ;
			
	/**
	 * L'ensemble des vues controllees et ayant le model ci-dessus comme source de donnees. 
	 */
	private java.util.ArrayList<View> views = new java.util.ArrayList<View>();
	
	/**
	 * Represente la liste des types de vues controlees par le controleur courant.
	 */
	private java.util.ArrayList<Class<? extends View>> types = new java.util.ArrayList<Class<? extends View>>() ;
	
	public <T extends Model> Controller(T model) throws ProgramException{
		this.setModel(model);
	}
		
	/**
	 * Dit si le type de vue est controllable par le controleur
	 * @param param Type de classe
	 * @return True si le type de la classe fait partir des types de vues controllables.
	 */
	public boolean isControllableView(Class<? extends View> param){
		if(param==null) return false;
		boolean isControllable = false;
		for(Class<? extends View> type: types){
			if (type.equals(param)){
				isControllable = true ;
				break;
			}
		}
		return isControllable ;
	}
	
	/**
	 * Verifie si le model en parametre peut etre controle par le controleur courant.
	 * @param model Model a verifier
	 * @return true si le model est un model controlable par le controleur.
	 */
	public abstract boolean isControllableModel(Model  model);
	
	/**
	 * Affecte le paramtere comme le nouveau modele controle. 
	 * Si le parametre, est errone, c'est a dire n'est pas le model attendu une erreur est lancee. 
	 * Et le modele existant n'est pas modifie.
	 * @param model Nouveau modele a controler
	 * @throws cm.enterprise.software.exception.ErroneousParameterException
	 */	
	public void setModel(Model model) throws cm.enterprise.software.exception.ErroneousParameterException {
		if(!this.isControllableModel(model)) throw new cm.enterprise.software.exception.ErroneousParameterException();		
		for(View view:views)this.removeListenerOfModel(view);
		this.model = model ;	
		for(View view:views){
			model.addAnyChangeListener((cm.enterprise.software.component.model.listener.AnyListener)view);
			this.addListenerToModel(view);
		}
	}
		
	/**
	 * Retourne le model controlle.
	 * @return Le modele controle
	 */
	public Model getModel() { return this.model; }
	
	/**
	 * Dit si une instance de type la classe en parametre existe.
	 * @param type Une classe de vue
	 * @return true si une instance de la classe en parametre existe deja.
	 */
	public <T extends View> boolean viewInstanceExists(Class<T> type){
		
		if(type==null) return false;
		
		boolean exists = false ;
		for(View view: views){
			if(type.isInstance(view)){
				exists = true;
				break;
			}
		}
		return exists;
	}
	
	/**
	 * Permet d'obtenir la liste des types de vues que le controlleur peut controles 
	 * @return L'ensemble des types de vues controlables par le controleur
	 */
	@SuppressWarnings("unchecked")
	public Class[] getAvailableViewTypes(){
		Class[] result = new Class[types.size()];
		int count = 0 ;
		for(Class<? extends View> type:types) result[count++]= type ;
		return result; 
	}
	
	/**
	 * Retourne la classe de vue d'index la position dans le tableau retourne par getAvailableViews().
	 * Les index vont de 0 a la taille-1 du tableau precedement mentionne. Et la valeur de retour classe
	 * de l'appel de cette fonction peut etre utilisee dans addView(Class). Une erreur est lancee si 
	 * l'index est errone.
	 * @param index Numero de position  dans le tableau des vues disponibles.
	 * @return La classe de vue, ou tout simplement la vue se trouvant a l'index en parametre.
	 * @throws cm.enterprise.software.exception.ErroneousParameterException
	 * @see #getAvailableViewTypes()
	 * @see #installView(Class, Object...)
	 */
	public Class<? extends View> getViewType(int index) throws cm.enterprise.software.exception.ErroneousParameterException{
		if(index<0 || index>=types.size()) throw new cm.enterprise.software.exception.ErroneousParameterException();		
		return types.get(index);
	}
	
	/**
	 * Permet d'obtenir la premiere instance de la vue de type le parametre.
	 * Si la classe de vue n'est pas prise en compte par le model
	 * ou si la classe n'a pas ete instanciee, la fonction retourne null.
	 * @param classOfView Classe de la vue de l'instance a retourne
	 * @return L'instance controlllee de type le parametre
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getViewOfClass(Class<T> classOfView){
		if(!this.isControllableView(classOfView)) return null;
		for(View view:views){
			if(classOfView.isInstance(view)) return (T) view;
		}
		return null;
	}
	
	/**
	 * Permet d'obtenir toutes les vues controlees.
	 * @return Liste des vues controlees.
	 */
	public View[] getAllViews(){
		View[] result = new View[views.size()] ;
		int count = 0 ;
		for(View view : views) result[count++]=view;
		return result;
	}
	/**
	 * Retourne le contenu d'une vue de classe le parametre.
	 * @param classe Type de la vue dont le contenu sera retourne
	 * @return Le contenu de la premiere instance de la classe en parametre.
	 */
	public Object getContentOf(Class<? extends View> classe){
		
		if(classe == null) return null;
		
	    Object content = null;
		int count = views.size()-1;
	    for(int i=count;i>=0;i--){
	    	View view = views.get(i);
	    	if(classe.isInstance(view)){
	    		content = view.getContent();
	    		break;
	    	}
	    }
		return content;
	}
	
	/**
	 * Ajoute une liste de nouvelle classe de vue a controler.
	 * Ceci permettra, desormais au controleur de generer les vues passes en parametre.
	 * @param classeOfView Liste des classes de vue a ajouter au control.
	 * @see #installView(Class,Object...)
	 */
	protected void addClassOfView(Class<? extends View>... classeOfView){
		for(Class<? extends View> type:classeOfView){
			if(type!=null && !types.contains(type)) types.add(type);
		}
	}
	
	/**
	 * Efface une liste des classes de vues controlees.
	 * @param classeOfView Liste des classes de vues a supprimer
	 */
	protected void removeClassOfView(Class<? extends View>... classeOfView){
		for(Class<? extends View> type:classeOfView){
			if(types.contains(type)){
				this.uninstallView(type);
				types.add(type);
			}
		}
	}
			
	/**
	 * Cette fonction a pour vocation de construire la view adequate selon le type de classe type et les parametres params.
	 * Si le type de vue passe en parametre est inadequate, une erreur sera lancee. Cette fonction ne cree qu'une
	 * instance par type de vue. 
	 * @param type Type de la vue a ajouter.
	 * @param params Parametre a utiliser pour la construction de la vue.
	 * @throws cm.enterprise.software.exception.ProgramException
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> void installView(Class<T> type,Object... params)throws cm.enterprise.software.exception.ProgramException {
		if(!this.isControllableView(type))	throw new ErroneousParameterException();
		if(!this.viewInstanceExists(type)){
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
					
					Constructor<? extends View> constructor = type.getConstructor(typesOfClass);
					View view = constructor.newInstance(params);
					views.add(view);
					model.addAnyChangeListener((cm.enterprise.software.component.model.listener.AnyListener)view);
					addListenerToModel(view);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ProgramException(e.getMessage());			
			}		
		}
	}
			
	/**
	 * Rend inacessible, en desinstallant ou supprimant, la ou les vues de type de classe le parametre.
	 * @param type Type de classe des vues a effacer
	 * @throws cm.enterprise.software.exception.ErroneousParameterException
	 */
	public <T extends View> void uninstallView(Class<T> type){
		if(this.isControllableView(type)){
			int count = views.size()-1;
			for(int i = count; i>=0;i--){
				View view = views.get(i);
				if(type.isInstance(view)){
					if(view.isVisible()) view.setVisible(false);
					views.remove(view);
					this.removeListenerOfModel(view);
				}
			}			
		}
	}
	
	/**
	 * Rend inacessible, en desinstallant ou supprimant, toutes les vues controlees. 
	 */
	public void uninstallViews(){
		int count = views.size() - 1;
		for(int i=count; i>=0;i--){
			View view = views.get(i);			
			this.removeListenerOfModel(view);
			if(view.isVisible()) view.setVisible(false);
			views.remove(i);
		}
	}	
	
	/**
	 * Affiche toutes les vues controlees.
	 */
	public void showViews() { 
		for(View view: views){
			view.setVisible(true);
		}
	}
	
	/**
	 * Montre les vues dont le type de classe est le parametre.
	 * @param type Type de classe.
	 */
	public <T extends View> void showViews(Class<T> type){
		if(this.isControllableView(type)){
			int count = views.size()-1;
			for(int i=count;i>=0;i--){
				View view = views.get(i);
				if(type.isInstance(view)){
					view.setVisible(true);
				}
			}
		}
	}
	
	/**
	 * Montre uniquement la premiere instance, trouvee, de la vue de type le parametre passe.
	 * @param type Type de classe vue.
	 */
	public <T extends View> void showView(Class<T> type){
		if(this.isControllableView(type)){
			int count = views.size()-1;
			for(int i=count;i>=0;i--){
				View view = views.get(i);
				if(type.isInstance(view)){
					view.setVisible(true);
					break;
				}
			}
		}
	}
	
	/**
	 * Cache toutes les vues controlees.
	 */
	public void hideViews(){ 
		for(View view: views){
			view.setVisible(false);
		}
	}
	
	/**
	 * Cache les vues de type de classe le parametre.
	 * @param type Type de classe de vue.
	 */
	public <T extends View> void hideViews(Class<T> type){
		if(this.isControllableView(type)){
			int count = views.size()-1;
			for(int i=count;i>=0;i--){
				View view = views.get(i);
				if(type.isInstance(view)){
					view.setVisible(false);
				}
			}
		}
	}
	
	/**
	 * Cache la premiere vue de type de classe le parametre.
	 * @param type Type de classe de vue.
	 */
	public <T extends View> void hideView(Class<T> type){
		if(this.isControllableView(type)){
			int count = views.size()-1;
			for(int i=count;i>=0;i--){
				View view = views.get(i);
				if(type.isInstance(view)){
					view.setVisible(false);
					break;
				}
			}
		}
	}
	
	/**
	 * Permet les interactions avec toutes les vues. 		
	 */
	public void enableViews(){ 
		for(View view: views){
			view.setEnable(true);
		}
	}
	
	/**
	 * Permet les interactions avec les vues de classe type. 
	 * @param type Classe de vue
	 */
	public <T extends View> void enableViews(Class<T> type) {
		if(this.isControllableView(type)){
			int count = views.size()-1;
			for(int i=count;i>=0;i--){
				View view = views.get(i);
				if(type.isInstance(view)){
					view.setEnable(true);
				}
			}
		}
	}
	
	/**
	 * Permet l'interaction la premiere vue de classe type.
	 * @param type Classe de vue.
	 */
	public <T extends View> void enableView(Class<T> type){
		if(this.isControllableView(type)){
			int count = views.size()-1;
			for(int i=count;i>=0;i--){
				View view = views.get(i);
				if(type.isInstance(view)){
					view.setEnable(true);
					break;
				}
			}
		}
	}
	
	/**
	 * Annule les interactions avec toutes les vues. 		
	 */
	public void disableViews(){ 
		for(View view: views){
			view.setEnable(false);
		}
	}
	
	/**
	 * Annule les interactions avec les vues de classe type. 
	 * @param type Classe de vue
	 */
	public <T extends View> void disableViews(Class<T> type) {
		if(this.isControllableView(type)){
			int count = views.size()-1;
			for(int i=count;i>=0;i--){
				View view = views.get(i);
				if(type.isInstance(view)){
					view.setEnable(false);
				}
			}
		}
	}
	
	/**
	 * Annule l'interaction la premiere vue de classe type.
	 * @param type Classe de vue.
	 */
	public <T extends View> void disableView(Class<T> type){
		if(this.isControllableView(type)){
			int count = views.size()-1;
			for(int i=count;i>=0;i--){
				View view = views.get(i);
				if(type.isInstance(view)){
					view.setEnable(false);
					break;
				}
			}
		}
	}
	
	/**
	 * Cette fonction servira a ajouter chaque vue cree comme ecouteur du modele.
	 * Cette fonction ne devrait par etre appelee directement.
	 * @param listener Ecouteur a ajouter. 
	 */
	protected abstract <T extends Listener> void addListenerToModel(T listener);
	
	/**
	 * Efface une vue de la liste des ecouteurs du modele controle.
	 * Cette fonction ne devrait par etre appelee directement.
	 * @param listener Ecouteur a effacer de la liste des ecouteurs du modele controle.
	 */
	protected abstract <T extends Listener> void removeListenerOfModel(T listener);
	
	/**
	 * Notifie un evenement aux vues, avec param comme parametres de cet evenement.  
	 * @param param Parametres de l'evenement a notifier.
	 */
	public abstract void notifyEvent(Object param);
}
