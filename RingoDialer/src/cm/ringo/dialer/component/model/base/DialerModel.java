package cm.ringo.dialer.component.model.base;

import cm.enterprise.software.component.model.base.SuperModel;
import cm.enterprise.software.helper.Application;
import cm.ringo.dialer.component.model.event.LanguageChangedEvent;
import cm.ringo.dialer.component.model.listener.LanguageListener;

/**
 * Modele de l'application Dialer
 * @author Charles Moute
 * @version 1.0.1, 4/8/2010
 */
public class DialerModel extends SuperModel {

	/**
	 * Represente la langue courante du dialer.
	 */
	private String language;
	
	public DialerModel(){
		this.setLanguage(Application.getCurrentLanguage());
	}
	
	/**
	 * Affecte une nouvelle langue au dialer
	 * @param language Langue a affecter au dialer
	 */
	public void setLanguage(String language){
		this.language = language ;
		this.fireLanguageChanged();
	}
	
	/**
	 * Permet d'obtenir la langue courante
	 * @return La langue courante du Dialer.
	 */
	public String getLanguage(){ return this.language; }
	
	/**
	 * Ajoute un ecouteur de changement de langue a la liste des ecouteurs du Dialer
	 * @param listener Ecouteur a ajouter
	 */
	public void addLanguageListener(LanguageListener listener){
		this.addListener(LanguageListener.class, listener);
	}
	
	/**
	 * Efface l'ecouteur de la liste des ecouteurs de changement de Langue
	 * @param listener Ecouteur a supprimer
	 */
	public void removeLanguageListener(LanguageListener listener){
		this.removeListener(LanguageListener.class, listener);
	}
	
	/**
	 * Propage le changement de la langue du dialer
	 */
	protected void fireLanguageChanged(){
		LanguageListener[] listenerList;
		listenerList = (LanguageListener[])this.getListeners(LanguageListener.class);
		for(LanguageListener listener: listenerList){
			listener.languageChanged(new LanguageChangedEvent(this,this.getLanguage()));
		}
	}
	
}
