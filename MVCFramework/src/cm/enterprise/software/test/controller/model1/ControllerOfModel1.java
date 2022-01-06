package cm.enterprise.software.test.controller.model1;

import cm.enterprise.software.component.controller.Controller;
import cm.enterprise.software.component.model.base.Model;
import cm.enterprise.software.component.model.listener.Listener;
import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.test.listener.model1.Event2Listener;
import cm.enterprise.software.test.model.model1.Model1;
import cm.enterprise.software.test.view.model1.View1Model1;
import cm.enterprise.software.test.view.model1.View2Model1;

/**
 * 
 * @author Charles Moute
 * @version 1.0.1, 17/07/2010
 */
public class ControllerOfModel1 extends Controller {

	@SuppressWarnings("unchecked")
	public ControllerOfModel1(Model1 model) throws ProgramException {
		super(model);
		//On definit les classes de vue controlees important afin de les instancier qd on a besoin
		this.addClassOfView(View1Model1.class);		
		this.addClassOfView(View2Model1.class);			
	}

	@Override
	protected <T extends Listener> void addListenerToModel(T view) {
		/*
		 *  Vu que l'on a definie l'ajout d'un ecouteur de l'evenement Event2.
		 *  On ajoutera ici notre vue, si elle est un ecouteur de l'evenement 2 du
		 *  modele.
		 */
		if(view instanceof Event2Listener){
			Model1 m = (Model1) model;
			m.addEvent2Listener((Event2Listener)view);
		}

	}

	@Override
	public boolean isControllableModel(Model model) {
		return model instanceof Model1;
	}

	@Override
	public void notifyEvent(Object param) {
		
		// Param peut etre de n'importe quel type selon notre besoin.
		
		/*
		 * Dans notre cas on considerera que lorsque param est un Integer ce que l'on souhaite
		 * modifie la valeur entiere du modele controle.
		 */
		if(param instanceof Integer){
			Model1 m =(Model1)model;
			Integer value = (Integer)param;
			if(m.getValue().compareTo(value)!=0) m.setAttributes(m.getAttribut(), value);
		}
		
		/*
		 * On considerera aussi que lorsque param est un String ce que l'on souhaite
		 * modifie la valeur de l'attribut du modele controle.
		 */
		if(param instanceof String){
			Model1 m =(Model1)model;
			String attrib = (String)param;
			String oldAttrib = m.getAttribut();
			boolean changeValue = (oldAttrib==null && !attrib.isEmpty()) || (oldAttrib!=null && oldAttrib.compareTo(attrib)!=0);
			if(changeValue) m.setAttributes(attrib, m.getValue());
		}
		
		/*
		 * Cette facon de faire limite un peu les actions a entreprendre pour les types String 
		 * et Integer. Nous aurions pu definir, une classe qui aurait contenu un ensemble 
		 * d'action a entreprendre avec les proprietes inherentes a l'action. Ainsi si param est 
		 * une instance de notre classe, selon les valeurs des parametres, nous auront 
		 * entreprenris differentes actions. Qui auraient pu, les actions, etre localisees dans 
		 * n'importe quelles fonctions internes a notre controlleur. Vous troiuverez un exemple 
		 * dans la fonction notifyEvent de ControllerOfSuperModel.
		 * Bref la seule limite ici est notre imagination. 
		 */

	}

	@Override
	protected <T extends Listener> void removeListenerOfModel(T view) {

		/*
		 *  Vu que l'on a definie l'ajout d'un ecouteur de l'evenement Event2.
		 *  On effacera ici notre vue, si elle est un ecouteur de l'evenement 2 du
		 *  modele.
		 */
		if(view instanceof Event2Listener){
			Model1 m = (Model1) model;
			m.removeEvent2Listener((Event2Listener)view);
		}
	}

}
