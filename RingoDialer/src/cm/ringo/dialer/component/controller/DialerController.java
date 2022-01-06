package cm.ringo.dialer.component.controller;

import cm.enterprise.software.component.controller.SuperController;
import cm.enterprise.software.component.model.base.Model;
import cm.enterprise.software.component.model.listener.Listener;
import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.ringo.dialer.component.model.base.DialerModel;
import cm.ringo.dialer.component.model.listener.LanguageListener;
import cm.ringo.dialer.component.view.DefaultView;
import cm.ringo.dialer.interfaces.Dialer;

/**
 * Controleur du dialer
 * @author Charles Moute
 * @version 1.0.1, Juillet 2010 
 */
public class DialerController extends SuperController {

	@SuppressWarnings("unchecked")
	public DialerController(DialerModel model) throws ProgramException {
		super(model);
		
		//Definition des types de vues controllables
		this.addClassOfView(DefaultView.class);
		
		//definition des classes des sous controleurs des sous modeles
		
		//Instanciation des sous controleurs des sous modeles de DialerModel
		
	}

	@Override
	protected <T extends Listener> void addListenerToModel(T listener) {
		
		if(listener instanceof LanguageListener){
			DialerModel m = (DialerModel)model;
			m.addLanguageListener((LanguageListener) listener);
		}
	}

	@Override
	public boolean isControllableModel(Model model) {
		return (model instanceof DialerModel);
	}

	@Override
	public void notifyEvent(Object param) {
		
		if(param instanceof String){
			//L'on considera que lorsque l'objet est un String on notifie le chagement de la langue
			String value = (String)param;
			Dialer dialer = Application.getModule(Dialer.class) ;
			boolean isSupported = dialer.isSupportedLanguage(value);
			
			if(dialer.getCurrentLanguage().compareTo(value)!=0 && isSupported){
				dialer.setCurrentLanguage(value);
				return;
			}
	
			DialerModel m = (DialerModel)model;
			String oldValue = m.getLanguage();			
			if(value.compareTo(oldValue)!=0 && isSupported){
				m.setLanguage(value);				
			}
		}
		
		/*
		 * Principe lorsque la vue du dialer se ferme, un ecouteur du changement de la propriete
		 * visible permettra de le savoir, et lorque la vue principal du dialer se ferme
		 * on doit appel le controleur du module connexion pour qu'il rende visible la vue des infos
		 * de parametre de connexion et lorque l'on utilisera le menu sur la barre de tache dans la vue par
		 * defaut du dialer, alors on fera tout betement la  vue d'infos si elle est visible et on
		 * reouvrira la vue par defaut du dialer, tout betement. 		 
		 */
	}

	@Override
	protected <T extends Listener> void removeListenerOfModel(T listener) {
		if(listener instanceof LanguageListener){
			DialerModel m = (DialerModel)model;
			m.removeLanguageListener((LanguageListener) listener);
		}
	}

}
