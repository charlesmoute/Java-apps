/**
 * 
 */
package helper.component;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class YooButton extends JButton implements MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -181097536661317105L;




	public YooButton(String name){
		super(name);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		this.setHorizontalTextPosition(SwingConstants.CENTER);
		this.setVerticalTextPosition(SwingConstants.BOTTOM);
		this.setOpaque(false);			
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(Color.black,Color.black),BorderFactory.createEmptyBorder(3,3,3,3)));
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.addMouseListener(this);
		this.getFont().deriveFont(10);
		
	}
	
	public void mouseEntered(MouseEvent me){
		
		this.setBorderPainted(true);
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(new Color(160,160,160),new Color(250,250,250)),BorderFactory.createEmptyBorder(3,3,3,3)));
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
    public void mouseExited(MouseEvent me){
    	this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(Color.black,Color.black),BorderFactory.createEmptyBorder(3,3,3,3)));
    	this.setBorderPainted(false);
    	this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
    public void mouseClicked(MouseEvent me){
    	
		
	} 		
    public void mousePressed(MouseEvent me){
    	this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		
	}
    public void mouseReleased(MouseEvent me){
    	if(this.contains(me.getX(),me.getY()))
    		this.setCursor(new Cursor(Cursor.HAND_CURSOR));	
    	else this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

}

