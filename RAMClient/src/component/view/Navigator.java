package component.view;

import helper.*;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Navigator  extends JFrame{	
	
	private static final long serialVersionUID = 1L;

	private JTabbedPane onglets = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
	
	private JMenuBar menubar ;
	private JMenu fileMenu,editionMenu,helpMenu;
	private JMenuItem newOnglet,printItem,exitItem;	
	private JMenuItem cleanCacheItem,cleanHistoricItem,uploadItem,deleteItem;
	private JMenuItem manuelItem,aboutItem;
	private JPopupMenu popupMenu;
	private JMenuItem newItem,closeItem,closeAllItem,closeOtherItem;
	private HelpFrame helpFrame ;
	
	public Navigator(){
		buildMenu();		
		buildPopupMenu();
		Image image = UResource.createIcon("logo.png", "resource").getImage();
		this.setIconImage(image);
		this.setTitle("RAMClient1.0 by Charles&Marsien.");
		this.setBounds(50,50,800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(onglets,BorderLayout.CENTER);
		addHTTPSession();
		try { helpFrame = new HelpFrame();	} catch (Exception e1) {
			JOptionPane.showMessageDialog(this, "Le manuel d'aide n'a pas été détecté.\nIl ne sera donc pas disponible.", "RAMClient",
							JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void buildMenu(){
		menubar = new JMenuBar();
		
		fileMenu = new JMenu("Fichier");
			
		newOnglet = new JMenuItem("Nouvel Onglet",UResource.createIcon("new-ico.png", "resource"));
		newOnglet.setToolTipText("Creer un nouvel Onglet");
		newOnglet.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				addHTTPSession();
			}
		});
		fileMenu.add(newOnglet);
		
		printItem = new JMenuItem("Imprimer",UResource.createIcon("print-ico.png", "resource"));
		printItem.setToolTipText("Lance l'impression de la page de l'onglet courant");
		printItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				print();
			}
		});
		fileMenu.add(printItem);
		
		exitItem = new JMenuItem("Exit",UResource.createIcon("exit-ico.png", "resource"));
		exitItem.setToolTipText("Ferme l'application");
		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				exit();
			}
		});
		fileMenu.add(exitItem);		
		menubar.add(fileMenu);
		
		editionMenu = new JMenu("Edition");
		cleanCacheItem = new JMenuItem("Nettoyer cache",UResource.createIcon("trash-ico.png", "resource"));
		cleanCacheItem.setToolTipText("Nettoie le cache");
		cleanCacheItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				cleanCache();
			}
		});
		editionMenu.add(cleanCacheItem);
		
		cleanHistoricItem = new JMenuItem("Nettoyer Historique(s)",UResource.createIcon("trash-ico.png", "resource"));
		cleanHistoricItem.setToolTipText("Nettoie l'ensemble des historiques du navigateur");
		cleanHistoricItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				cleanHistoric();
			}
		});
		editionMenu.add(cleanHistoricItem);
				
		uploadItem = new JMenuItem("Upload",UResource.createIcon("upload-ico.png", "resource"));
		uploadItem.setToolTipText("Charge un fichier sur le serveur.");
		uploadItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				upload();
			}
		});
		editionMenu.add(uploadItem);
		
		deleteItem = new JMenuItem("Delete",UResource.createIcon("trash-ico.png", "resource"));
		deleteItem.setToolTipText("Efface un fichier sur le serveur.");
		deleteItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				delete();
			}
		});
		editionMenu.add(deleteItem);
		menubar.add(editionMenu);		
		
		helpMenu = new JMenu("Aide");
		manuelItem = new JMenuItem("Manuel",UResource.createIcon("manuel-ico.png", "resource"));
		manuelItem.setToolTipText("Manuel d'aide de RAMClient");
		manuelItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				manuelAction();
			}
		});
		helpMenu.add(manuelItem);
		
		aboutItem = new JMenuItem("A propos",UResource.createIcon("about-ico.png", "resource"));
		aboutItem.setToolTipText("A propos de RAMClient");
		aboutItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				aboutAction();
			}
		});
		helpMenu.add(aboutItem);		
		menubar.add(helpMenu);		
		this.setJMenuBar(menubar);
	}
	
	private void buildPopupMenu() {
		popupMenu = new JPopupMenu();
		
		newItem = new JMenuItem("Nouvel Onglet",UResource.createIcon("new-ico.png", "resource"));
		newItem.setToolTipText("Ouvre un nouvel Onglet");
		newItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				addHTTPSession() ;
			}
		});
		popupMenu.add(newItem);
		
		closeItem = new JMenuItem("Fermer");
		closeItem.setToolTipText("Ferme l'onglet courant");
		closeItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				closeHTTPSession() ;
			}
		});
		popupMenu.add(closeItem);
		
		closeOtherItem = new JMenuItem("Fermer tout sauf... ",UResource.createIcon("cancel0.png", "resource"));
		closeOtherItem.setToolTipText("Les autres onglets sont tous fermés");
		closeOtherItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				closeOtherHTTPSession();
			}
		});
		popupMenu.add(closeOtherItem);
		
		closeAllItem = new JMenuItem("Fermer tous les Onglets",UResource.createIcon("exit-ico.png", "resource"));
		closeAllItem.setToolTipText("Les onglets sont tous fermés");
		closeAllItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				closeAll();
			}
		});
		popupMenu.add(closeAllItem);
		onglets.setComponentPopupMenu(popupMenu);
	}
	
	
	/**
	 * <p>Cree un nouvel onglet</p>
	 */
	public void addHTTPSession() {
		HTTPSession onglet = new HTTPSession();
		//onglets.addTab("Nouvel Onglet",UResource.createIcon("refresh.gif", "resource"),onglet);
		onglets.addTab("Nouvel Onglet",onglet);
		onglet.setContainer(onglets);
	} 
	
	/**
	 * <p>Ferme l'onglet courant</p>
	 */
	public void closeHTTPSession() { 
		if(onglets.getTabCount()>0){
			onglets.remove(onglets.getSelectedIndex());
		}
	} 
	
	
	/**
	 * <p>Ferme Tous les onglets a l'exception de l'onglet courant</p>
	 */
	public void closeOtherHTTPSession() {
		if(onglets.getTabCount()>0){
			int index = onglets.getSelectedIndex();
			for(int i=(onglets.getTabCount()-1);i>=0;i--){
				if(i==index) continue;
				onglets.remove(i);
			}
		}
	}
	
	/**
	 * <p>Ferme tous les onglets</p>
	 */
	public void closeAll() {
		if(onglets.getTabCount()>0) onglets.removeAll();
	} 
	
	/**
	 * <p>Efface le cache</p>
	 */
	public void cleanCache() {
		int answer = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment nettoyer le cache", "RAMClient", JOptionPane.YES_NO_OPTION);
		if(answer==JOptionPane.YES_OPTION){
			File f = new File(Configuration.CACHE_DIRECTORY);
			if(f.exists()&& f.isDirectory())Util.delete(f);
			f.mkdir();
			JOptionPane.showMessageDialog(this, "Cache Nettoyé", "RAMClient", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	/**
	 * <p>Efface tous les historiques des onglets</p>
	 */
	public void cleanHistoric() {
		if(onglets.getTabCount()>0){
			int answer = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment effacer tous les historiques", "RAMClient", JOptionPane.YES_NO_OPTION);
			if(answer==JOptionPane.YES_OPTION){
				for(int i=0;i<onglets.getTabCount();i++){
					if(onglets.getComponent(i) instanceof HTTPSession )((HTTPSession)onglets.getComponent(i)).cleanHistoric();
				}
			  JOptionPane.showMessageDialog(this, "Cache Nettoyé", "RAMClient", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	
	/**
	 * <p>Lance la fenetre du telechargement</p>
	 */
	public void upload() {
		new PutFrame().setVisible(true);		
		//new PutFrame(null).setVisible(true);
	}
	
	public void print() {
		//int pos ;
		//if((pos=onglets.getSelectedIndex())>-1){
		if(onglets.getSelectedIndex()>-1){
			Thread thread = new Thread(){
				public void run(){
					HTTPSession onglet = (HTTPSession)onglets.getComponent(onglets.getSelectedIndex());
					onglet.printAction();
				}
			};
			//thread.setDaemon(true);
			thread.start();
		}
	}
	
	public void delete() {
		new DeleteFrame().setVisible(true);
	}
		
	public void manuelAction() {
		if(helpFrame!=null)
			helpFrame.setVisible(!helpFrame.isVisible());
	}
	
	public void aboutAction() {
		JOptionPane.showMessageDialog(this, "RAMClient1.0 est un navigateur web\n\n réalisé par Charles & Marsien.", "RAMClient",
										JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void exit(){ System.exit(0);	}
	
	/*public static void main(String[] arg){
		new Navigator().setVisible(true);
	}*/
}
