/**
 * 
 */
package helper.component;


/**
 * @author Lekeutch
 */

import java.awt.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;

public class YooDivPane extends JComponent{

	/**
	 * serial version reconnu par la machine virtuelle
	 */
	private static final long serialVersionUID = 4602234444811484976L;
	/**
	 * Conteneur du composant mi en input, 
	 * C est lui qui s occupe le role du principal div  
	 */
	private ContainerDiv containerDiv;
	/**
	 * Composant qui doit etre present dans le containerDiv 
	 */
	private JComponent contentDiv;
	/**
	 *  les margin du composant represente psr les entiers:
	 *  ls sont au nombre de 4:
	 *  -- Left
	 *  -- Right
	 *  -- Top
	 *  -- Bottom
	 */
	private int marginLeft=0, marginBottom=0, marginRight=0, marginTop=0;
	/**
	 * les padding du containerDiv represente les dimension des labels dont ces derniers sont au nombre de 4:
	 *  -- Left
	 *  -- Right
	 *  -- Top
	 *  -- Bottom
	 */
	private JLabel paddingLeft, paddingBottom, paddingRight, paddingTop;
	
	
	
	
	
	public YooDivPane(){
		this(null);
	}
	
	/**
	 * constructeur permettant d initialiser le containerDiv complet avec un parametre qui est le contenu
	 * Il met les margin(left,bottom,righr et top) a Zero
	 * Il se met Transparent et borrdure a Zero empty
	 * Il ajoute les padding(left,bottom,right,top) dans  le <code>containerDiv</code> et met leur dimension a null
	 * 
	 * @param comp
	 * 
	 * @see #YooDivPane()
	 */
	
	public YooDivPane(JComponent comp){
		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
	    this.setOpaque(false);	
		paddingLeft = new JLabel("");//paddingLeft.setOpaque(true); paddingLeft.setBackground(Color.GRAY);
		paddingBottom = new JLabel("");//paddingBottom.setOpaque(true); paddingBottom.setBackground(Color.GREEN);
		paddingRight = new JLabel("");//paddingRight.setOpaque(true); paddingRight.setBackground(Color.PINK);
		paddingTop = new JLabel("");//paddingTop.setOpaque(true); paddingTop.setBackground(Color.ORANGE);
		
		if(comp==null){contentDiv = new JPanel(); contentDiv.setBackground(Color.WHITE);}
		else contentDiv = comp;
		
		containerDiv = new ContainerDiv();		
		containerDiv.setLayout(new BorderLayout());
		containerDiv.add(paddingLeft, BorderLayout.WEST);
		containerDiv.add(paddingBottom, BorderLayout.SOUTH);
		containerDiv.add(paddingRight, BorderLayout.EAST);
		containerDiv.add(paddingTop, BorderLayout.NORTH);
		containerDiv.add(contentDiv,BorderLayout.CENTER);
		this.add(containerDiv);
		containerDiv.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));		
	}
	
	
	
	/**
	 * mise de valeur dans le margin left
	 * @param larg
	 */
	
	public void setMarginLeft(int larg){
		marginLeft =larg;
		this.setBorder(BorderFactory.createEmptyBorder(marginTop, marginLeft, marginBottom, marginRight));
	}
	
	/**
	 * mise de valeur dans le margin bottom
	 * @param bas
	 */
	
	public void setMarginBottom(int bas){
		marginBottom =bas;
		this.setBorder(BorderFactory.createEmptyBorder(marginTop, marginLeft, marginBottom, marginRight));		
	}	

	/**
	 * mise de valeur dans le margin right
	 * @param larg
	 */	
	
	public void setMarginRight(int larg){
		marginRight =larg;
		this.setBorder(BorderFactory.createEmptyBorder(marginTop, marginLeft, marginBottom, marginRight));
	}	

	
	/**
	 * mise de valeur dans le margin topt
	 * @param haut
	 */
	
	public void setMarginTop(int haut){
		marginTop =haut;
		this.setBorder(BorderFactory.createEmptyBorder(marginTop, marginLeft, marginBottom, marginRight));
	}	
	
	/**
	 * mise de valeur a tous les  margin 
	 * @param taille
	 */
	
	public void setMargin(int taille){
		marginLeft=marginRight= marginBottom= marginTop=taille;
		this.setBorder(BorderFactory.createEmptyBorder(marginTop, marginLeft, marginBottom, marginRight));
	}
	
	/**
	 * obtention du Margin left
	 * @return marginLeft
	 */
	public int getMarginLeft(){ return marginLeft;}
	
	/**
	 * obtention du Margin bottom
	 * @return marginBottom
	 */	
	public int getMarginBottom(){ return marginBottom;}
	
	/**
	 * obtention du Margin right
	 * @return marginRight
	 */	
	public int getMarginRight(){ return marginRight;}
	
	/**
	 * obtention du Margin top
	 * @return marginTop
	 */	
	public int getMarginTop(){ return marginTop;}
	
	/**
	 * 
	 * @param longueur
	 */
	
	public void setPaddingLeft(int longueur){
		paddingLeft.setPreferredSize(new Dimension(longueur,paddingLeft.getHeight()));
	}
	
	
	/**
	 * 
	 * @param longueur
	 */
	public void setPaddingRight(int longueur){
		paddingRight.setPreferredSize(new Dimension(longueur,paddingRight.getHeight()));
	}
	
	/**
	 * 
	 * @param haut
	 */
	public void setPaddingTop(int haut){
		paddingTop.setPreferredSize(new Dimension(paddingLeft.getWidth(),haut));
	} 

	/**
	 * 
	 * @param haut
	 */
	public void setPaddingBottom(int haut){
		paddingBottom.setPreferredSize(new Dimension(paddingBottom.getWidth(),haut));
		
	} 	
	
	
	
	/**
	 * 
	 * @param taille
	 */
	public void setPadding(int taille){
		setPaddingLeft(taille);
		setPaddingBottom(taille);
		setPaddingRight(taille);
		setPaddingTop(taille);
	}
	
	
	
	
	public int getPaddingLeft(){ return paddingLeft.getWidth();}
	
	
	public int getPaddingRight(){ return paddingRight.getWidth();}
	
	
	public int getPaddingBottom(){ return paddingBottom.getHeight();}
	
	
	public int getPaddingTop(){ return paddingTop.getHeight();}
	
	
	
	public ContainerDiv getContainerDiv(){ return this.containerDiv;}
	
	
	public JComponent getContentDiv(){ return contentDiv;}
	
	
	
	public void setcontentDiv(JComponent comp){
		containerDiv.remove(contentDiv);
		contentDiv = comp;
		containerDiv.add(contentDiv, BorderLayout.CENTER);
		containerDiv.repaint();
	}
	/**
	 * 
	 * @param border
	 */
	public void setBorderDiv(Border border){
		containerDiv.setBorder(border);
	}
	
	/**
	 * 
	 * @param color
	 */
	public void setBackDiv(Color color){
		containerDiv.setBackground(color);
	}
	
	/**
	 * 
	 * @param imgURL
	 */
	public void setBackDiv(URL imgURL){
		containerDiv.setBackImage(imgURL);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void setBackPosition(int x, int y){
		containerDiv.setImgPos(x,y);
	}
	
	
	/**
	 * 
	 * @param x
	 */
    public void setBackPositionX(int x){
    	containerDiv.setImgX(x);
    }

    /**
     * 
     * @param y
     */
    public void setBackPositionY(int y){
    	containerDiv.setImgY(y);
    }    
    
    /**
     * 
     * @param larg
     * @param haut
     */
    public void scaleBack(int larg, int haut){
    	containerDiv.scaleBackImage(larg,haut);
    }
    
	/*public void setOpaque(boolean bool){
		super.setOpaque(false);
	}*/
    	
    public void setAlpha(boolean alpha){
    	containerDiv.setOpaque(alpha);
    }
}

