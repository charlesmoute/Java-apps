package cm.enterprise.software.test.view;

import cm.enterprise.software.component.view.SuperView;
import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.test.controller.ControllerOfSuperModel;
import cm.enterprise.software.test.listener.Event3Listener;

/**
 * Classe parente des vues du super modele de test. Nous l'avons fait implementer
 * que Event3Listener, mais nous aurons pu lui faire implementer Event2Listener
 * et Event1Listener, ceci dans le but de permettre au vues d'ecouter les modfifications
 * des sous modeles. Cette methode nous vous la conseillons, lorsqu'un sous model, d'un
 * super model, lance des evenements mais n'a pas de vue pour les ecouteurs, ou dans le cas
 * ou vous souhaitez redefinir les vues de telle sorte que la super vue n'utilise aucune des
 * vues des sous modeles, dans ce cas si les sous modeles lance des evenements nous vous
 * conseillons de lister leurs evenements ici, du moins ce que vous souhaitez que la super
 * vue controle. 
 * @author Charles Moute
 * @version 1.0.1, 17/07/2010
 */
public abstract class ViewSuperModel extends SuperView implements Event3Listener {

	public ViewSuperModel(ControllerOfSuperModel controller)throws ProgramException {
		super(controller);
	}
}
