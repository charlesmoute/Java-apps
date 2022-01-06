package cm.enterprise.software.test.controller.model2;

import cm.enterprise.software.component.controller.Controller;
import cm.enterprise.software.component.model.base.Model;
import cm.enterprise.software.component.model.listener.Listener;
import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.test.listener.model2.Event1Listener;
import cm.enterprise.software.test.model.model2.Model2;
import cm.enterprise.software.test.view.model2.View1Model2;
import cm.enterprise.software.test.view.model2.View2Model2;

/**
 * Exemple d'un controlleur pour le model2
 * @author Charles Moute
 * @version 1.0.1, 17/07/2010
 */
public class ControllerOfModel2 extends Controller {

	@SuppressWarnings("unchecked")
	public ControllerOfModel2(Model2 model) throws ProgramException {
		super(model);
		//Ajoute des classes de vues controlables par ce controleur
		this.addClassOfView(View1Model2.class);
		this.addClassOfView(View2Model2.class);		
	}

	@Override
	protected <T extends Listener> void addListenerToModel(T view) {
		
		/*
		 * Si la vue est un ecouteur de l'evenement 1 on l'ajoute 
		 * a l'ecoute des evenement 1 genere par le model controle.
		 */
		if( view instanceof Event1Listener){
			Model2 m = (Model2)model ;
			m.addEvent1Listener((Event1Listener)view);			
		}

	}

	@Override
	public boolean isControllableModel(Model model) {
		return model instanceof Model2 ;
	}

	@Override
	public void notifyEvent(Object param) {

		/*
		 * On considere que si param est une instance de String ce que la notification
		 * doit porter sur la modification de la valeur du modele.
		 */		
		if(param instanceof String){
			Model2 m = (Model2)model;
			String value = (String) param;
			String oldValue = m.getValue();
			boolean notify = (oldValue==null && !value.isEmpty()) || (oldValue!=null && oldValue.compareTo(value)!=0);
			if(notify) m.setValue(value);
		}
		
		/**
		 * Nous aurions pu definir un classe definissant l'ensemble des actions a entreprendre
		 * avec les proprietes associees. Ainsi si param est une instance de cette classe nous
		 * pouvons lancer differentes actions qui pourront etre repertoriees dans differentes 
		 * fonctions internes du controleur. Vous troiuverez un exemple dans la fonction
		 * notifyEvent de ControllerOfSuperModel.
		 */
	}

	@Override
	protected <T extends Listener> void removeListenerOfModel(T view) {
		
		//Event1Listener est le seul ecouteur que nous avons definis dans Model2
		if( view instanceof Event1Listener){
			Model2 m = (Model2)model ;
			m.removeEvent1Listener((Event1Listener)view);			
		}
	}

}
