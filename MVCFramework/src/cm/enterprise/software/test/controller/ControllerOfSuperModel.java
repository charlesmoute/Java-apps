package cm.enterprise.software.test.controller;

import cm.enterprise.software.component.controller.SuperController;
import cm.enterprise.software.component.model.base.Model;
import cm.enterprise.software.component.model.event.AnyChangedEvent;
import cm.enterprise.software.component.model.listener.AnyListener;
import cm.enterprise.software.component.model.listener.Listener;
import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.test.controller.model1.ControllerOfModel1;
import cm.enterprise.software.test.controller.model2.ControllerOfModel2;
import cm.enterprise.software.test.listener.model2.Event1Listener;
import cm.enterprise.software.test.listener.model1.Event2Listener;
import cm.enterprise.software.test.listener.Event3Listener;
import cm.enterprise.software.test.model.model1.Model1;
import cm.enterprise.software.test.model.model2.Model2;
import cm.enterprise.software.test.model.SuperModelTest;
import cm.enterprise.software.test.util.MessageOfSuperModelTest;
import cm.enterprise.software.test.view.View1SuperModel;
import cm.enterprise.software.test.view.View2SuperModel;

/**
 * Exemple d'un controlleur de super modele
 * @author Charles Moute
 * @version 1.0.1, 17/07/2010
 */
public class ControllerOfSuperModel extends SuperController {

	@SuppressWarnings("unchecked")
	public ControllerOfSuperModel(SuperModelTest model)	throws ProgramException {
		super(model);
		
		//Ajout des vues controllables par le controleur.
		this.addClassOfView(View1SuperModel.class,View2SuperModel.class);
		
		//Definition des classes des sous controleurs des sous modeles.
		this.addClassOfController(ControllerOfModel1.class,ControllerOfModel2.class);

		//Instanciation des sous controleurs pour les sous modeles de notre super modele de test.
		this.installSubController(ControllerOfModel1.class,model.getSubModel(Model1.class));
		this.installSubController(ControllerOfModel2.class,model.getSubModel(Model2.class));
	}

	@Override
	protected <T extends Listener> void addListenerToModel(T listener) {
		
		/*
		 * 
		 * Vu que le sous model 2 lance des evts de type Event1 alors tout les ecouteurs
		 * de type Event1Listener doivent etre ajoutes. en realite c'est une mesure preventive
		 * il est vrai que les vues du super model n'ecoute pas Event1Listener ou en 
		 * Event2Listener, et laisse le soit au controlleur du Model1 et du Model2
		 * de s'occuper de cette gestion, mais il pourrait arriver qu'un tiers definisse
		 * une vue qui elle implemente ce genre d'ecouteur donc il faudrait pas qu'il
		 * vienne modifier le code source de notre module parce que ce dernier ne prend
		 * pas en compte tous les ecouteurs de ses sous modeles. A moins que ce soit 
		 * notre souhait. Bref libre a vous de le gerer comme bon vous semble.
		 */
		if(listener instanceof Event1Listener){
			SuperModelTest sm = (SuperModelTest)model;
			sm.addListenerToSubModel(Model2.class,Event1Listener.class,(Event1Listener)listener);
		}
		
		// Il existe un ecouteur de l'evenement 1 pour un des sous model du super model.
		if(listener instanceof Event2Listener){
			SuperModelTest sm = (SuperModelTest)model;
			sm.addListenerToSubModel(Model1.class,Event2Listener.class,(Event2Listener)listener);			
		}
		
		//L'ecouteur de l'evenement 3 est l'ecouteur definie par le super model
		if(listener instanceof Event3Listener){
			SuperModelTest sm = (SuperModelTest)model;
			sm.addEvent3Listener((Event3Listener)listener);
		}
	}

	@Override
	public boolean isControllableModel(Model model) {
		return model instanceof SuperModelTest;
	}

	@Override
	public void notifyEvent(Object param) {
		
		/*
		 * Param doit etre du type de classe MessageOfSuperModelTest
		 * Classe indiquant l'action a entreprendre selon le parametre
		 */
		
		if(param instanceof MessageOfSuperModelTest){
			
			MessageOfSuperModelTest p = (MessageOfSuperModelTest)param;
			
			/*
			 * Les deux conditions des cas ou la notification est destinee
			 * soit au Model1 soit au Model2, ne sont la que pour vous
			 * montrer comment on aurait pu gerer si on avait
			 * considerer que les super vues du super model
			 * se charger elle même de valider les informations des sous modeles.
			 * Nous nous avons considerez le cas ou on laisse les controlleurs des
			 * sous modeles faire se travail, apres tout c'est le but de la modularite.
			 * Donc les deux grandes conditions ne seront jamais verifiez dans notre cas
			 * mais si une tiers developper une autre super vue qui se charge de valider
			 * les valeurs de ses sous modeles, ce controlleur restera toujours d'actualite.
			 * Mais si j'amais cela s'avere ardu avec ce controlleur, alors il pourra
			 * lui meme ecrire un autre controleur avec ses propres principes
			 * et il l'utilisera comme bon lui semble. Avec les vues qui l'interesse. 
			 */
			
			if(p.getNotification()==MessageOfSuperModelTest.MODEL1_NOTIFICATION){
				if(p.getMessageContent()instanceof String){
					String attribut = (String) p.getMessageContent();
					SuperModelTest m = (SuperModelTest)model;
					m.setValueToModel1(attribut, m.getValueOfModel1());
					
					/*
					 * On aurait pu utiliser la methode ci dessous si on
					 * avait pas definit les methodes de nom setValueToModel1 
					 * et getValueOfModel1 dans SuperModelTest.
					 */
					
					/*try {
					 	Integer value = (Integer) m.invokeMethodOfSubModel(Model1.class, "getValue");
						m.invokeMethodOfSubModel(Model1.class,"setAttributes",attribut, value);
					} catch (ProgramException e) {
						e.printStackTrace();
					}*/
					return;
				}
				
				if(p.getMessageContent()instanceof Integer){
					Integer value = (Integer) p.getMessageContent();
					SuperModelTest m = (SuperModelTest)model;
					m.setValueToModel1(m.getAttributeOfModel1(),value);
					
					/*
					 * On aurait pu utiliser la methode ci dessous si on
					 * avait pas definit les methodes de nom setValueToModel1 
					 * et getValueOfModel1 dans SuperModelTest.
					 */
					
					/*try {
					 	String attribut = (String) m.invokeMethodOfSubModel(Model1.class, "getAttribut");
						m.invokeMethodOfSubModel(Model1.class,"setAttributes",attribut, value);
					} catch (ProgramException e) {
						e.printStackTrace();
					}*/
					
					return;
				}
				
			}
			
			if(p.getNotification()==MessageOfSuperModelTest.MODEL2_NOTIFICATION){
				
				if(p.getMessageContent()instanceof String){
					String value = (String) p.getMessageContent();
					SuperModelTest m = (SuperModelTest)model;
					m.setValueToModel2(value);
					
					/*
					 * On aurait pu utiliser la methode ci dessous si on
					 * avait pas definit la methode de nom setValueToModel2 
					 * dans SuperModelTest.
					 */
					
					/*try {
						m.invokeMethodOfSubModel(Model2.class,"setValue",value);
					} catch (ProgramException e) {
						e.printStackTrace();
					}*/
					return;
				}
			}
			
			if(p.getNotification()==MessageOfSuperModelTest.SUPERMODEL_NOTIFICATION){
				
				if(p.getMessageContent()instanceof String){
					
					SuperModelTest m = (SuperModelTest)model;
					
					String value = (String) p.getMessageContent();
					String oldValue = m.getValueOfAtttrribute();
					
					boolean notify = (oldValue==null && value!=null && !value.isEmpty()) || (oldValue!=null && oldValue.compareTo(value)!=0);
					if(notify){
						m.setValueToAttribute(value);
						String infos = " Attribut du super model a changer sa valeur. La nouvelle valeur est = "+value;
						//Propager le changement suivant a tous les ecouteurs de n'importe quel changemet
						//m.fireAnyChange(infos);
						//L'instruction ci-dessus est equivalente a l'instruction ci-dessous, un peu plus longue.
						try {
							m.fireChangeToListener(AnyListener.class, "anyChanged",new AnyChangedEvent(m,infos));
						} catch (ProgramException e) {
							e.printStackTrace();
						}
					}
					return;
				}
			}
		}

	}

	@Override
	protected <T extends Listener> void removeListenerOfModel(T view) {
		//Un seul type d'ecouteur est admis par le model
		if(view instanceof Event3Listener){
			SuperModelTest m = (SuperModelTest)model;
			m.removeEvent3Listener((Event3Listener)view);
		}
	}

}
