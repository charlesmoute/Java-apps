package cm.enterprise.software.component.view;

import cm.enterprise.software.component.controller.Controller;

/**
 * Reprensente l'ossature des differentes vues de l'application.
 * @author Charles Moute
 * @version 1.0.1, 28/06/2010
 */
public abstract class View implements cm.enterprise.software.component.model.listener.Listener,cm.enterprise.software.component.model.listener.AnyListener {
	
	/**
	 * Represente le controleur en charge du model sur lequel la vue est basee.
	 */
	protected Controller controller = null;
	
	/**
	 * Represente l'etat de visibilite d'une vue.
	 */
	private boolean isVisible ;
	
	/**
	 * Dit si l'on peut interagir avec la vue.
	 */
	private boolean isEnable;
	
	/**
	 * Retourne le controlleur de la vue.
	 * @return Le controleur de la vue
	 */
	public Controller getController() { return this.controller; }
		
	/**
	 * Retourne la vue
	 * @return Un objet representant la vue
	 */
	public abstract  Object getContent() ;
	
	/**
	 * Affiche la vue.
	 */
	protected abstract void show() ;
	
	/**
	 * Cache la vue.
	 */
	protected abstract void hide();
	
	/**
	 * Permet les interactions avec la vue.
	 */
	protected abstract void enable() ;
	
	/**
	 * Annule les interactions avec la vue
	 */
	protected abstract void disable();
	
	/**
	 * Dit si la vue est visible ou pas.
	 * @return true si la vue est visible et false dans le cas contraire.
	 */
	public boolean isVisible(){ return this.isVisible; }
	
	/**
	 * Affecte un etat de visibilte a la vue.
	 * @param flag true si la vue est visible et false dans le cas contraire.
	 */
	public void setVisible(boolean flag) { 
		this.isVisible = flag ;
		if(isVisible) show();
		else hide();
	}
	
	/**
	 * Dit si les interactions avec la vue sont permise.
	 * @return true si les interactions avec la vue sont permises.
	 */
	public boolean isEnable(){ return this.isEnable; }
	
	/**
	 * Permet ou ne permet pas les interactions avec la vue.
	 * @param flag true si les interactions avec la vue sont permises.
	 */
	public void setEnable(boolean flag) { 
		this.isEnable = flag;
		if(isEnable) enable();
		else disable();
	}
	
	/**
	 * Construit une instance de View
	 * @param controller Controleur 
	 */
	public View(Controller controller){
		super();		
		this.controller = controller;
		this.isVisible = true;
		this.isEnable = true;
	}
}
