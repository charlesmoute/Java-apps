package view;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;


/**
 * @author Charles
 * <p> Cette classe permet de gerer la barre d'outils de l'application</p>
 */
public class ToolBar extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	private Image image = null;
	private String title = null;
	private Font font = new Font(Font.DIALOG_INPUT,Font.BOLD,25);
	private Color color = new Color(0,102,153);
	
	public void paintComponent(Graphics g){		
		super.paintComponent(g);	
		Graphics2D pinceau = (Graphics2D)g;
		
		int size =64;
		
		if(image!=null)
			pinceau.drawImage(image,5,(this.getHeight()-size)/2,size,size,null); // y =15
		
	    if(font!=null)
	    	pinceau.setFont(font);
	    pinceau.setColor(new Color(0,102,153));
	    if(title!=null)
	    	pinceau.drawString(title,(int)((this.getHeight()-size)/2 +70),35);//58
	}
	

	/**
	 * Cree une instance de barre d'outils avec une image suivi d'un titre.
	 * @param image Image a inserer sur la toolbar.
	 * @param title Titre a inserer sur la toolbar.
	 */
	public ToolBar(Image image,String title) {
		this.image = image ;
		this.title = title ;		
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));				
	}
	
	
	/**
	 * Cree une instance de Toolbar avec un image dessus.
	 * @param image Image a inserer sur l'instance de la ToolBar
	 * @see #ToolBar(Image, String)
	 */
	public ToolBar(Image image){
		this(image,null);
	}
	
	/**
	 * Cree une instance de ToolBar avec un titre par defaut
	 * @param title Titre a inserer sur l'instance de ToolBar
	 * @see #ToolBar(Image, String)
	 */
	public ToolBar(String title){
		this(null,title);
	}
	
	/**
	 * Equivaut a ToolBar(null,null)
	 * @see #ToolBar(Image, String)
	 */
	public ToolBar(){
		this(null,null);
	}
	
	
	/**
	 * Change la police du Titre de la ToolBar
	 * @param font Nouvelle Police.
	 */
	public void setPolicy(Font font){
		this.font = font ;
		this.repaint();
	}
	
	/**
	 * Change la police du Titre de la ToolBar
	 * @param name Nom de la nouvelle police
	 * @param style Style de la nouvelle Police
	 * @param size Taille de la nouvelle Police
	 */
	public void setPolicy(String name,int style, int size){
		font = new Font(name,style,size);
		this.repaint();
	}
	
	/**
	 * Affecte la police par defaut. Elle correspond a setPolicy(Font.DIALOG_INPUT,Font.BOLD,25). 
	 * @see #setPolicy(String, int, int)
	 */
	public void setPolicy(){
		font = new Font(Font.DIALOG_INPUT,Font.BOLD,25);
		this.repaint();
	}
	
	/**
	 * @return La police du Titre associee a la ToolBar.
	 */
	public Font getPolicy(){
		return font;
	}
	
	/**
	 * Affecte le parametre comme couleur du Titre de la toolbar.
	 * @param color Nouvelle Couleur
	 */
	public void setColor(Color color){
		this.color = color ;
		this.repaint();
	}
	
	/**
	 * Considere les trois parametres comme composition de la nouvelle couleur.
	 * @param red Taux de rouge dans la nouvelle couleur
	 * @param green Taux de vert dans la nouvelle couleur
	 * @param blue Taux de bleu dans la nouvelle couleur
	 */
	public void setColor(int red, int green,int blue){
		color = new Color(red,green,blue);
		this.repaint();
	}
	
	/**
	 * Affecte la couleur par defaut. Ceci correspond a setColor(0,102,153).
	 * @see #setColor(int, int, int)
	 */
	public void  setColor(){
		color = new Color(0,102,153);
		this.repaint();
	}
	
	/**
	 * @return La couleur assignee au Titre de la ToolBar.
	 */
	public Color getColor() {return this.color; }
	
	/**
	 * Affecte le parametre comme nouvelle image de la ToolBar.
	 * @param image Nouvelle Image de la toolbar.
	 */
	public void setImage(Image image){
		this.image = image ;
		this.repaint();
	}
	
	/**
	 * Affecte le parametre comme nouveau titre du panneau.
	 * @param title Nouveau titre
	 */
	public void setTitle(String title){
		this.title = title;
		this.repaint();
	}
	
	/**
	 * @return Le titre associe a la toolbar.
	 */
	public String getTitle() { return this.title; }
	
	/**
	 * Ajoute le parametre en fin de liste des boutons.
	 * @param title Titre u bouton a afficher sous le bouton.
	 * @param icon Image du bouton lorsqu'il est actif.
	 * @param rolloverIcon Image a afficher lorsque l'on survole le bouton.
	 * @param disabledIcon Image a afficher quand le bouton est desactive.
	 * @param listener Ecouteur a associer au bouton.
	 * @return True si le bouton a effectivement ete ajoute.
	 */
	public boolean addButton(String title,ImageIcon icon,ImageIcon rolloverIcon,ImageIcon disabledIcon,ActionListener listener){
	
		JButton button = new JButton(title);
		
		if(icon!=null) button.setIcon(icon);
		button.setRolloverEnabled(rolloverIcon!=null);
		if(listener!=null) 	button.addActionListener(listener);
		if(rolloverIcon!=null) button.setRolloverIcon(rolloverIcon);
		if(disabledIcon!=null) button.setDisabledIcon(disabledIcon);
		
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setOpaque(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return addButton(button);
	}
	
	/**
	 * Ajoute le parametre en fin de liste des boutons.
	 * @param title Titre du bouton
	 * @param icon Image du bouton
	 * @param disabledIcon Image du bouton lorsqu'il est inactif.
	 * @param listener Ecouteur des clicks sur le bouton
	 * @return True si le bouton a effectivement ete ajoute.
	 * @see #addButton(String, ImageIcon, ImageIcon, ImageIcon, ActionListener)
	 */
	public boolean addButton (String title,ImageIcon icon,ImageIcon disabledIcon,ActionListener listener){
		return addButton(title,icon,null,disabledIcon,listener);
	}
	
	/**
	 * Ajoute le parametre en fin de liste des boutons.
	 * @param title Titre du bouton
	 * @param icon Image du bouton
	 * @param listener Ecouteur des clicks sur le bouton
	 * @return True si le bouton a effectivement ete ajoute.
	 * @see #addButton(String, ImageIcon, ImageIcon, ImageIcon, ActionListener)
	 */
	public boolean addButton (String title,ImageIcon icon,ActionListener listener){
		return addButton(title,icon,null,null,listener);
	}
	
	/**
	 * Ajoute le parametre en fin de liste des boutons.
	 * @param title Titre du bouton
	 * @param listener Ecouteur des clicks sur le bouton
	 * @return True si le bouton a effectivement ete ajoute.
	 * @see #addButton(String, ImageIcon, ImageIcon, ImageIcon, ActionListener)
	 */
	public boolean addButton (String title,ActionListener listener){
		return addButton(title,null,null,null,listener);
	}
	
	
	/**
	 * Ajoute le parametre en fin de liste des boutons.
	 * @param title Titre du bouton
	 * @return True si le bouton a effectivement ete ajoute.
	 * @see #addButton(String, ImageIcon, ImageIcon, ImageIcon, ActionListener)
	 */
	public boolean addButton (String title){
		return addButton(title,null,null,null,null);
	}
	
	
	/**
	 * Ajoute le parametre en fin de liste des boutons.
	 * @param button Boutons a ajouter.
	 * @return True si le bouton a effectivement ete ajoute.
	 */
	public boolean addButton(JButton button){
		if(button==null) return false;
		this.add(button);
		buttons.add(button);		
		return true;		
	}
	
	/**
	 * Retourne le bouton de numero index. Index dans l'ensemble {0,...,n-1}, donc le bouton 1 est d'index 0.
	 * @param index Numero du bouton a retourner
	 * @return Le bouton si il existe, null dans le cas contraire.
	 */
	public JButton getButton(int index){
		if(index>=0 && index<buttons.size() && buttons.size()>0) return buttons.get(index);
		return null;
	}
	
	/**
	 * @return Le nombre de boutons.
	 */
	public int getButtonCount() { return buttons.size(); }

	/**
	 * Efface le bouton d'index le parametre. NB: les index vont de 0 a (n-1), pour les boutons 1 a n.
	 * @param index L'index du bouton a effacer.
	 * @return True si le bouton a ete reellement efface.
	 */
	public boolean removeButton(int index) {
		if(index>=0 && index<buttons.size() && buttons.size()>0){
			this.remove(buttons.get(index));
			buttons.remove(index);			
			return true;
		}
		return false;
	}
	
	/**
	 * Efface tous les boutons de la toolbar. 
	 */
	public void removeButtons(){
		for(int i=(buttons.size()-1);i>=0;i--){
			this.remove(buttons.get(i));
			buttons.remove(i);	
		}
	}
	
	/**
	 * Considere le parametre comme la liste des boutons de la toolbar.
	 * @param buttons Liste des boutons de la toolbar a inserer.
	 */
	public void setButtons(ArrayList<JButton> buttons){
		this.removeButtons();
		if(buttons!=null) this.buttons = buttons ;
		for(int i=0;i<buttons.size();i++){
			
		}
		
	}
	
	/**
	 * @return La liste des boutons de la ToolBar.
	 */
	public ArrayList<JButton> getButtons(){
		return buttons;
	}
	
}	
		 
		
