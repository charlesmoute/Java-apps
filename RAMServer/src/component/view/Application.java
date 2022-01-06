package component.view;

import helper.*;
import java.awt.*;
import java.text.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.print.*;
import component.model.*;



import component.model.base.*;

public class Application extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static final String MESSAGE_STOP_STATUS = "Serveur en mode arrêt.";
	private static final String MESSAGE_START_MULTITHREAD_STATUS = "Serveur demarré en mode MultiThread.";
	private static final String MESSAGE_START_CHANNEL_STATUS = "Serveur demarré en mode Canaux.";
	
	private JTextArea output = null;
	private JScrollBar scrollBar =null;
	
	private JMenuBar menubar = null;
	private JToolBar toolbar = null;

	private JMenu fileMenu,editionMenu,helpMenu;
	private PopupMenu popup = null;
	
	private JMenuItem printItem,startItem,stopItem,exitItem;
	private JMenuItem cleanLogItem,cleanCacheItem,preferenceItem,viewLogItem;
	private JMenuItem manuelItem,aboutItem;
	
	private JButton startButton,stopButton,printButton,manuelButton,viewButton,exitButton;
	
	private Server server;
	private JLabel statusbar ;
	
	private RefreshThread myThread ;
	private JDialog logFrame = null;
	private boolean isOpened = false ;
	private boolean refresh = false,refresh2 = false ;
	
	private HelpFrame helpFrame ;
	
	
	public Application() {
			
		statusbar = new JLabel(Application.MESSAGE_STOP_STATUS);
		this.getContentPane().add(statusbar,BorderLayout.SOUTH);
		buildMenuBar();
		buildToolBar();
		buildPopup();
		
		output = new JTextArea("");
		output.setBackground(Configuration.BACKGROUND);
		output.setForeground(Configuration.FOREGROUND);
		output.setCaretColor(Configuration.CARETCOLOR);
		output.setFont(UResource.FONT);
		output.setText(Configuration.NAME_SERVER+" en attente...");	
		output.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(output);
		//scrollBar.setValue(scrollBar.getMaximum());
		scrollBar = scrollPane.getVerticalScrollBar() ; 
		scrollBar.addAdjustmentListener(new AdjustmentListener(){
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				if(refresh){
					refresh = false;
					refresh2 = true ;
					scrollBar.setValue(scrollBar.getMaximum());					
				}
				if(refresh2){
					refresh2 = false ;
					scrollBar.setValue(scrollBar.getMaximum());
				}				
			}			
		});
		this.getContentPane().add(scrollPane);
		
		this.setTitle("RAMServer1.0 by Charles&Marsien.");
		this.setResizable(false);
		this.setBounds(150, 50, 600, 700);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent evt){				
				setVisible(false);
				UResource.setMessage("Info", "L'applicationa a été réduite mais pas arrêtée.",TrayIcon.MessageType.INFO);
			}
		});
		
		 try { helpFrame = new HelpFrame();	} catch (Exception e1) {
			 Util.writeError("Application.Application() : Erreur lors de l'ouverture de l'aide.");
			 UResource.setMessage("Info", "La Fenêtre du manuel ne pourra être ouverte.",TrayIcon.MessageType.WARNING);
		}
	}
	
	private void buildMenuBar() {
		menubar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		
		startItem = new JMenuItem("Start Server",UResource.createIcon("play-ico.png","resource"));
		startItem.setToolTipText("Demarre/Redemarre le serveur");
		startItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				startAction();
			}
		});		
		fileMenu.add(startItem);
		
		stopItem = new JMenuItem("Stop Server",UResource.createIcon("stop-ico.png","resource"));
		stopItem.setToolTipText("Arrete le Serveur");
		stopItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				stopAction();
			}
		});
		stopItem.setEnabled(false);
		fileMenu.add(stopItem);
		
		printItem = new JMenuItem("Print Trace",UResource.createIcon("print-ico.png","resource"));
		printItem.setToolTipText("Lance l'impression les traces d'execution");
		printItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				printAction();
			}
		});		
		fileMenu.add(printItem);
		
		fileMenu.addSeparator();
		
		exitItem = new JMenuItem("Exit",UResource.createIcon("exit-ico.png","resource"));
		exitItem.setToolTipText("Quitte l'application");
		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				exitAction();
			}
		});		
		fileMenu.add(exitItem);		
		menubar.add(fileMenu);
		
		editionMenu = new JMenu("Edition");
		
		cleanLogItem = new JMenuItem("Clean Log Folder",UResource.createIcon("trash-ico.png","resource"));
		cleanLogItem.setToolTipText("Nettoie le dossier des fichiers Log");
		cleanLogItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				cleanLogAction();
			}
		});
		editionMenu.add(cleanLogItem);
		editionMenu.addSeparator();		
		cleanCacheItem = new JMenuItem("Clean Cache Folder",UResource.createIcon("trash-ico.png","resource"));
		cleanCacheItem.setToolTipText("Nettoie le cache du serveur");
		cleanCacheItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				cleanCacheAction();
			}
		});
		editionMenu.add(cleanCacheItem);
		editionMenu.addSeparator();
		
		viewLogItem = new JMenuItem("Log Files Listing",UResource.createIcon("view-ico.png","resource"));
		viewLogItem.setToolTipText("Permet de visualiser les fichiers Log");
		viewLogItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				viewLogAction();
			}
		});	
		editionMenu.add(viewLogItem);
		editionMenu.addSeparator();
		
		preferenceItem = new JMenuItem("Preference",UResource.createIcon("config-ico.png","resource"));
		preferenceItem.setToolTipText("Configuration du Serveur");
		preferenceItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				preferenceAction();
			}
		});	
		editionMenu.add(preferenceItem);
		menubar.add(editionMenu);
		
		helpMenu = new JMenu("Help");
		manuelItem = new JMenuItem("Manuel",UResource.createIcon("manuel-ico.png","resource"));
		manuelItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				manuelAction();
			}
		});
		helpMenu.add(manuelItem);
		
		aboutItem = new JMenuItem("About",UResource.createIcon("about-ico.png","resource"));
		aboutItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				aboutAction();
			}
		});
		helpMenu.add(aboutItem);
		
		menubar.add(helpMenu);
		
		this.setJMenuBar(menubar);
	}
	
	private void buildPopup(){
		Image image = UResource.createIcon("logo.png", "resource").getImage();
		this.setIconImage(image);
		
		popup = new PopupMenu();
		
		MenuItem open = new MenuItem("Open");
		open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setVisible(true);
			}
		});
		popup.add(open);
		
		MenuItem hide = new MenuItem("Hide");
		hide.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setVisible(false);
			}
		});
		popup.add(hide);
		
		MenuItem exit = new MenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				exitAction();
			}
		});
		popup.add(exit);
		UResource.createSystemTray(image,"RamServer", popup);
	}
	
	private void buildToolBar() {
		toolbar = new JToolBar(JToolBar.HORIZONTAL);
		toolbar.setFloatable(false);
		this.getContentPane().add(toolbar,BorderLayout.NORTH);
		
		startButton = new JButton();
		startButton.setFont(UResource.FONT);
		startButton.setIcon(UResource.createIcon("play.png","resource"));
		startButton.setRolloverEnabled(true);
		startButton.setRolloverIcon(UResource.createIcon("play2.png","resource"));
		startButton.setDisabledIcon(UResource.createIcon("play3.png","resource"));
		startButton.setContentAreaFilled(false);
		startButton.setMargin(new Insets(0,0,0,0));
		startButton.setBorderPainted(false);
		startButton.setToolTipText("Lance ou redemarre le serveur");
		startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				 startAction();
			}
		});
		toolbar.add(startButton);
		toolbar.addSeparator();
		stopButton = new JButton();
		stopButton.setFont(UResource.FONT);
		stopButton.setIcon(UResource.createIcon("stop.png","resource"));
		stopButton.setRolloverEnabled(true);
		stopButton.setRolloverIcon(UResource.createIcon("stop2.png","resource"));		
		stopButton.setDisabledIcon(UResource.createIcon("stop3.png","resource"));
		stopButton.setContentAreaFilled(false);
		stopButton.setMargin(new Insets(0,0,0,0));
		stopButton.setBorderPainted(false);
		stopButton.setToolTipText("Arrete le serveur");
		stopButton.setEnabled(false);
		stopButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				stopAction();
			}
		});
		toolbar.add(stopButton);
		
		toolbar.addSeparator();
		printButton = new JButton();
		printButton.setFont(UResource.FONT);
		printButton.setIcon(UResource.createIcon("print.png","resource"));
		printButton.setRolloverEnabled(true);
		printButton.setRolloverIcon(UResource.createIcon("print3.png","resource"));		
		printButton.setDisabledIcon(UResource.createIcon("print.png","resource"));
		printButton.setContentAreaFilled(false);
		printButton.setMargin(new Insets(0,0,0,0));
		printButton.setBorderPainted(false);
		printButton.setToolTipText("Imprime les traces d'execution");
		printButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				printAction();
			}
		});
		toolbar.add(printButton);
		
		toolbar.addSeparator();
		manuelButton = new JButton();
		manuelButton.setFont(UResource.FONT);
		manuelButton.setIcon(UResource.createIcon("help.png","resource"));
		manuelButton.setRolloverEnabled(true);
		manuelButton.setRolloverIcon(UResource.createIcon("help2.png","resource"));		
		manuelButton.setDisabledIcon(UResource.createIcon("help.png","resource"));
		manuelButton.setContentAreaFilled(false);
		manuelButton.setMargin(new Insets(0,0,0,0));
		manuelButton.setBorderPainted(false);
		manuelButton.setToolTipText("Manuel d'utilisation");
		manuelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				manuelAction();
			}
		});
		toolbar.add(manuelButton);
		
		toolbar.addSeparator();
		viewButton = new JButton();
		viewButton.setFont(UResource.FONT);
		viewButton.setIcon(UResource.createIcon("view.png","resource"));
		viewButton.setRolloverEnabled(true);
		viewButton.setRolloverIcon(UResource.createIcon("view2.png","resource"));		
		viewButton.setDisabledIcon(UResource.createIcon("view.png","resource"));
		viewButton.setContentAreaFilled(false);
		viewButton.setMargin(new Insets(0,0,0,0));
		viewButton.setBorderPainted(false);
		viewButton.setToolTipText("Listing des fichiers Log");
		viewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				viewLogAction();
			}
		});
		toolbar.add(viewButton);
		
		toolbar.addSeparator();
		exitButton = new JButton();
		exitButton.setFont(UResource.FONT);
		exitButton.setIcon(UResource.createIcon("exit.png","resource"));
		exitButton.setRolloverEnabled(true);
		exitButton.setRolloverIcon(UResource.createIcon("exit2.png","resource"));		
		exitButton.setDisabledIcon(UResource.createIcon("exit.png","resource"));
		exitButton.setContentAreaFilled(false);
		exitButton.setMargin(new Insets(0,0,0,0));
		exitButton.setBorderPainted(false);
		exitButton.setToolTipText("Ferme definitivement l'apllication");
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				exitAction();
			}
		});
		toolbar.add(exitButton);
		
	}
	
	private void printAction() {
	  if(output!=null && output.getText().compareToIgnoreCase("")!=0){
		  try {
			this.output.print(new MessageFormat("RAMServer 1.0"), new MessageFormat("Implementation du protocole HTTP."));
		} catch (PrinterException e) {
			Util.writeError("Application.printAction :: Erreur lorss du lancement de l'impression");
			JOptionPane.showMessageDialog(this, "Un probléme a été détecté lors du lancement de l'impression.\n" +
					"Vérifiez que votre imprimante est bien connecté", "RAMServer Information", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	  }
		  
	}
	
	private void startAction() {
		try { 
			Util.initializeConfiguration();
			if(Util.getServerType().compareToIgnoreCase(Configuration.MULTITHREAD_SERVER)==0){
				server = new MultiThreadServer();
				this.statusbar.setText(Application.MESSAGE_START_MULTITHREAD_STATUS);				
			}else{ 
				server = new ChannelServer();
				this.statusbar.setText(Application.MESSAGE_START_CHANNEL_STATUS);
			}
			server.start();
			if(server.isRunning()){
				output.setText("");
				UResource.setMessage("Info", "Serveur Lance", TrayIcon.MessageType.INFO);				
				myThread = new RefreshThread();
				myThread.start();
				stopItem.setEnabled(true);
				stopButton.setEnabled(true);
				startItem.setEnabled(false);
				startButton.setEnabled(false);
			}else{
				UResource.setMessage("Erreur", "Le seveur n'a pas été lancé.", TrayIcon.MessageType.ERROR);
				this.statusbar.setText(Application.MESSAGE_STOP_STATUS);
			}
		}catch (Exception e) {
			Util.writeError("Application.startAction() :: Erreur lors du Lancement du Serveur");
			JOptionPane.showMessageDialog(this, "Erreur lors du lancement du Serveur", "Erreur",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
				
	}
	
	private void stopAction() {
		server.stop();
		if(!server.isRunning()) {			
			myThread.interrupt();
			UResource.setMessage("Info", "Serveur Arrêté", TrayIcon.MessageType.INFO);
			
			stopItem.setEnabled(false);
			stopButton.setEnabled(false);
			startItem.setEnabled(true);
			startButton.setEnabled(true);
			this.statusbar.setText(Application.MESSAGE_STOP_STATUS);
			server = null;
			myThread = null;
			output.append("\nRAMServer a été interrompu.");
			scrollBar.setValue(scrollBar.getMaximum());			
		}else UResource.setMessage("Erreur", "Le Serveur n'a pas été arrêté", TrayIcon.MessageType.ERROR);
	}
	
	private void exitAction(){
		if(server!=null && server.isRunning()) stopAction();
		System.exit(0);
	}
	
	private void cleanLogAction(){
		int answer = JOptionPane.showConfirmDialog(this, "Voulez-vous Vraiment supprimer les fichiers de Log?", "RAMServer Question", JOptionPane.YES_NO_OPTION);
		if(answer== JOptionPane.YES_OPTION) Server.cleanLog();
	}
	
	private void cleanCacheAction(){
		int answer = JOptionPane.showConfirmDialog(this, "Voulez-vous Vraiment nettoyer le cache?", "RAMServer Question", JOptionPane.YES_NO_OPTION);
		if(answer== JOptionPane.YES_OPTION) Server.cleanCache();
	}
	
	private void preferenceAction() {
		new Preference(this).setVisible(true);
	}
	
	private void manuelAction(){
		if(helpFrame!=null)
			helpFrame.setVisible(!helpFrame.isVisible());		
	}
	
	private void aboutAction() {
		JOptionPane.showMessageDialog(this,"RAMServer est un serveur prennant en compte\n" +
				"les requêtes GET,PUT,DELETE et HEAD.\nIl est réalisé par Charles&MArsien", "A propos",JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void viewLogAction(){
		if(!isOpened){
			logFrame = Server.listLogFiles(this);
			logFrame.setModal(false);
			isOpened = true;
			logFrame.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent evt){
					isOpened = false ;
				}				
			});
			logFrame.setVisible(true);
		}
	}
		
	
	class RefreshThread extends Thread {
	
		public void run() {
			while(!interrupted()){
				if(server!=null){
					String message = "RAMServer lancé et en attente de clients..." ;
					
					if(output.getText().isEmpty()) output.setText(message);
					
					if(output.getText().length()<server.getTrace().length()){
						output.setText("");
						output.append(server.getTrace());
						refresh = true ;
					}
				}
				try {Thread.sleep(500); } catch (InterruptedException e) {	/*output.append("\n Serveur arrêté.");*/}
			}
		}
	}	
}
