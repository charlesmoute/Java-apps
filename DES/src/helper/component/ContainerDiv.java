package helper.component;

import java.awt.*;
import javax.swing.*;
import java.net.*;


public class ContainerDiv  extends JPanel{
  
	/**
	 * 
	 */
	

	private static final long serialVersionUID = 1804245520093119645L;
	private Image backImage=null;
//	private Image backImageCut=null;
	private int imgX=0, imgY=0, larg, haut;
	private double ratio;
	
	
	public ContainerDiv(){
		super(new BorderLayout());
		this.setOpaque(true);
	}	
	
	public void setBackImage(URL  imageURL){
		backImage = (new ImageIcon(imageURL)).getImage();
		larg = backImage.getWidth(null);
		haut = backImage.getHeight(null);
		ratio = larg/haut;
//		backImageCut = backImage.getScaledInstance(larg, (int)(larg/ratio),Image.SCALE_DEFAULT);
		repaint();
	}
	
	public void scaleBackImage(int width, int height){
	//	backImageCut=backImage.getScaledInstance(width,height,Image.SCALE_AREA_AVERAGING);
		repaint();
	}
	
	public void setWidthImage(int larg){
		this.scaleBackImage(larg, backImage.getHeight(null));
		repaint();
	}
	
	public void setHeightImage(int haut){
		this.scaleBackImage(backImage.getWidth(null),haut);
		repaint();
	}
	
	
	public void setImgPos(int x,int y){
		imgX = x;
		imgY = y;
		repaint();
	}
	
	public void setImgX(int x){ setImgPos(x,imgY) ;}
	public void setImgY(int y){ setImgPos(imgX,y) ;}	
	
	
	public void paintComponent(Graphics cr){
		super.paintComponent(cr);
		if(backImage!=null){
			cr.drawImage(backImage,imgX,imgY,larg,(int)(larg/ratio),null);
		}
	}
}
