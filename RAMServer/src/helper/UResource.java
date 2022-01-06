package helper;

import java.awt.*;
import java.net.*;
import javax.swing.*;
import java.awt.TrayIcon.*;


/**
 * 
 * @author Charles&Masien
 * <p> 
 * Cette classe contient l'ensemble des methodes statiques necessaires au fonctionnement de
 * l'application 
 * </p>
 */

public class UResource {


	public static Font FONT = new Font("DialogInput", 0, 14);
	
	public static Color COLOR = new Color(76,128,235);
	
	/** 
	 * @param packageName Nom du paquetage dont on désire avoir l'url
	 * @return URL du paquetage de nom packageName.
	 */
	
	public static URL urlPackage(String packageName){
      URL url = UResource.class.getResource("UResource.class");
      try{
       String path = url.getFile();       
       int index = path.lastIndexOf("helper/UResource.class");
       String pathPackage = path.substring(0,index)+packageName+"/";
       return new URL(url.getProtocol(), url.getHost(), url.getPort(), pathPackage);     
      }catch(MalformedURLException e){} 
      return null;        
    }
	
	/**
	 * <p>Construit l'image de chemin pathIcon et de paquetage namePackage</p>
	 * @param pathIcon
	 * @param namePackage
	 * @return L'image de l'icone. 
	 */
	public static ImageIcon createIcon(String pathIcon, String namePackage){

	   try{  
	    URL url = urlPackage(namePackage);
	    String path= url.getFile()+"UImages/"+pathIcon;    
	    //System.out.println("--> Chemin de pathIcon: "+path);
	    return new ImageIcon(new URL(url.getProtocol(), url.getHost(), url.getPort(), path));	  
	   }catch(MalformedURLException e){
	       System.out.println("URL mal forme");
	   } 
	   return null;
	}
	
	
	public static void createSystemTray(Image image,String title,PopupMenu popup){
		SystemTray tray;
		TrayIcon trayIcon ;
		if(SystemTray.isSupported()){
			tray = SystemTray.getSystemTray();			
			trayIcon = new TrayIcon(image,title,popup);			
			try { tray.add(trayIcon); } catch (AWTException e) { e.printStackTrace(); }
			trayIcon.setImageAutoSize(true);
		}
	}
	
	public static void setMessage(String title,String description, MessageType type){		
		if(SystemTray.isSupported()){
			TrayIcon ti ;			
			if (SystemTray.getSystemTray().getTrayIcons()!=null){
				ti = SystemTray.getSystemTray().getTrayIcons()[0];
				ti.displayMessage(title,description,type);
			}	
		}		
	}
	
}
