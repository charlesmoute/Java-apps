package cm.ringo.dialer.util;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * Represente les elements de menu du Dialer
 *
 * @author Charles Moute
 * @version 1.0.1, 4/8/2010
 */
public class DialerOption extends JMenuItem {

	private static final long serialVersionUID = 1L;
	
	/**
	 * @param title Libelle de l'element de menu
	 * @param mnemonic Lettre de raccourci
	 * @param listener Ecouteur de l'option
	 * @see #DialerOption(String, char, String, ImageIcon, ActionListener)
	 */
	public DialerOption(String title, char mnemonic,ActionListener listener){
		this(title,mnemonic,null,null,listener);
	}
	
	/**
	 * @param title Libelle de l'element de menu
	 * @param tooltip Info bulle de l'option
	 * @param listener Ecouteur des actions sur l'option
	 * @see #DialerOption(String, char, String, ImageIcon, ActionListener)
	 */
	public DialerOption(String title,String tooltip, ActionListener listener){
		this(title,listener);
		this.setToolTipText(tooltip);
	}
	
	/**
	 * @param title Libelle de l'option
	 * @param listener Ecouteur des evenements sur l'option
	 * @see #DialerOption(String, char, String, ImageIcon, ActionListener)
	 */
	public DialerOption(String title, ActionListener listener){
		super(title);
		this.addActionListener(listener);
	}
	
	/**
	 * Cree une instance de menu de nom title, avec pour raccourci la touche control+mnemonic,
	 * l'info bulle tooltipText, l'icone icon et l'ecouteur d'evenement listener.
	 * 
	 * @param title Libelle de l'element de menu.
	 * @param mnemonic Lettre de  raccourci
	 * @param tooltipText Info bulle
	 * @param icon Image associee a l'element de menu
	 * @param listener Ecouteur de click sur l'element de menu
	 */
	public DialerOption(String title, char mnemonic,String tooltipText,ImageIcon icon, ActionListener listener){
		this(title,tooltipText,listener);
		this.setIcon(icon);
		this.setAccelerator(KeyStroke.getKeyStroke("control "+mnemonic));
	}
}
