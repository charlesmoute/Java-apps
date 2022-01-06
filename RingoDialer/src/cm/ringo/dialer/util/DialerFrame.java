package cm.ringo.dialer.util;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;

/**
 * Represente la fenetre principal du dialer
 * @author Charles Mouté
 * @version 1.0.1, Août 2010
 */
public class DialerFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Contruit une instance vide  de la fenetre principal
	 */
	public DialerFrame(){
		super();
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				DialerFrame.this.firePropertyChange("visible", true, false);
			}
		});
	}
	
	/**
	 * Ajoute un ecouteur de l'etat de visibilite de la fenetre
	 * @param listener Ecouteur a ajouter.
	 */
	public void addVisibleChangeListener(PropertyChangeListener listener){
		if(listener==null) return ;
		this.addPropertyChangeListener("visible", listener);
	}
	
	/**
	 * Efface l'ecouteur en parametre de la liste des ecouteurs de l'etat de visibilite de
	 * la fenetre
	 * @param listener Ecouteur a effacer
	 */
	public void removeVisibleChangeListener(PropertyChangeListener listener){
		if(listener==null) return ;
		this.removePropertyChangeListener("visible", listener);
	}

}
