package cm.enterprise.software.component.model.event;

import cm.enterprise.software.component.model.base.Model;

/**
 * Represente l'evenement qui se produit lorsqu'une modification quelconque d'un modele a lieu.
 * @author Charles Moute
 * @version 1.0.1, 2/07/2010
 */
public class AnyChangedEvent extends Event {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Represente l'information ayant trait a l'evenement.
	 */
	private Object infos = null;
	
	/**
	 * Construit une instance de AnyChangeEvent
	 * @param source Source de donnees emettrices
	 * @param infos Informations ayant trait a l'evenement
	 */
	public AnyChangedEvent(Model source,Object infos) {
		super(source);
		this.infos = infos ;
	}
	
	/**
	 * Retourne un objet representant les informations ayant trait au changement
	 * @return L'information ayant trait a la modification qui a engendre l'evenement.
	 */
	public Object getInformationOnChange(){ return this.infos; }

}
