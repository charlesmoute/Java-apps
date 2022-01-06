package component.view;

import java.text.MessageFormat;
import java.util.*;
import component.model.*;

import helper.*;

import java.io.*;
import java.net.*;
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.awt.print.PrinterException;

import javax.swing.event.*;

public class HTTPSession extends JPanel{

	private static int count = 0 ;
	private int number ;
	
	private static final long serialVersionUID = 1L;
	
	public static final String HTML =	"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"+
										"<html xmlns=\"http://www.w3.org/1999/xhtml\">"+
										"<head>"+
										"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />"+
										"<title>RAMClient</title>"+
										"</head>"+
										"<body style=\" font-family:'Courier New', Courier, monospace; font-size:12px;\">"+
										"<div>"+
										"	<center><div style=\"margin-top:35px; padding-bottom:15px;border-bottom:black dotted medium;\"><b>RAMClient 1.0</b></div></center>"+
										"	<div style=\"margin:25px 0; padding:0 50px;\">"+
										"		<p> Bienvenu dans RAMClient. Tapez l'adresse, dont vous souhaitez avoir le contenu, dans le champ ad&eacute;quat.</p>"+
										"	</div>"+
										"	<center><div style=\"margin-bottom:35px; padding-top:15px;border-top:black dotted medium;\"><i>CopyRight All Rights reserverd 2010 Charles&Marsien</i></div></center>"+
										"</div>"+
										"</body>"+
										"</html>";
	
	private JTabbedPane container = null ;
	private JToolBar toolbar ;
	private JEditorPane editor ; //editeur ou les pages web s'affiche.
	private JComboBox addressBar; // barre d'addresse
	private Vector<String> historic = null;
	
	private JLabel statusBar;
	private String method ;
	private JRadioButton getButton,headButton;
	
	private JButton firstButton,previousButton,nextButton,lastButton,refreshButton;
	
	//private JPanel simulation ;
	
	private int current = -1 ;
		
	public HTTPSession() {
		this(null);		
	}	
	
	public HTTPSession(JTabbedPane container){
		HTTPSession.count++;
		number = HTTPSession.count;
		
		this.container = container ;
		this.method = HTTP.GET_METHOD ;
		this.setLayout(new BorderLayout());
		toolbar = new JToolBar() ; 
		toolbar.setFloatable(false);
		
		firstButton = new JButton();
		firstButton.setFont(Configuration.FONT);
		firstButton.setIcon(UResource.createIcon("first0.png","resource"));
		firstButton.setRolloverEnabled(true);
		firstButton.setRolloverIcon(UResource.createIcon("first1.png","resource"));
		firstButton.setDisabledIcon(UResource.createIcon("first2.png","resource"));
		firstButton.setContentAreaFilled(false);
		firstButton.setMargin(new Insets(0,0,0,0));
		firstButton.setBorderPainted(false);
		firstButton.setToolTipText("Premiére Page.");
		firstButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				firstButtonAction();
			}
		});
		toolbar.add(firstButton);
		toolbar.addSeparator();
		
		previousButton = new JButton();
		previousButton.setFont(Configuration.FONT);
		previousButton.setIcon(UResource.createIcon("previous0.png","resource"));
		previousButton.setRolloverEnabled(true);
		previousButton.setRolloverIcon(UResource.createIcon("previous1.png","resource"));
		previousButton.setDisabledIcon(UResource.createIcon("previous2.png","resource"));
		previousButton.setContentAreaFilled(false);
		previousButton.setMargin(new Insets(0,0,0,0));
		previousButton.setBorderPainted(false);
		previousButton.setToolTipText("Page précédente.");
		previousButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				previousButtonAction();
			}
		});
		toolbar.add(previousButton);
		toolbar.addSeparator();

		nextButton = new JButton();
		nextButton.setFont(Configuration.FONT);
		nextButton.setIcon(UResource.createIcon("next0.png","resource"));
		nextButton.setRolloverEnabled(true);
		nextButton.setRolloverIcon(UResource.createIcon("next1.png","resource"));
		nextButton.setDisabledIcon(UResource.createIcon("next2.png","resource"));
		nextButton.setContentAreaFilled(false);
		nextButton.setMargin(new Insets(0,0,0,0));
		nextButton.setBorderPainted(false);
		nextButton.setToolTipText("Page Suivante.");
		nextButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				nextButtonAction();
			}
		});
		toolbar.add(nextButton);
		toolbar.addSeparator();
		
		lastButton = new JButton();
		lastButton.setFont(Configuration.FONT);
		lastButton.setIcon(UResource.createIcon("last0.png","resource"));
		lastButton.setRolloverEnabled(true);
		lastButton.setRolloverIcon(UResource.createIcon("last1.png","resource"));
		lastButton.setDisabledIcon(UResource.createIcon("last2.png","resource"));
		lastButton.setContentAreaFilled(false);
		lastButton.setMargin(new Insets(0,0,0,0));
		lastButton.setBorderPainted(false);
		lastButton.setToolTipText("Derniére Page.");
		lastButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				lastButtonAction();
			}
		});
		toolbar.add(lastButton);
		toolbar.addSeparator();
				
		refreshButton = new JButton();
		refreshButton.setFont(Configuration.FONT);
		refreshButton.setIcon(UResource.createIcon("reset0.png","resource"));
		refreshButton.setRolloverEnabled(true);
		refreshButton.setRolloverIcon(UResource.createIcon("reset1.png","resource"));
		refreshButton.setDisabledIcon(UResource.createIcon("reset2.png","resource"));
		refreshButton.setContentAreaFilled(false);
		refreshButton.setMargin(new Insets(0,0,0,0));
		refreshButton.setBorderPainted(false);
		refreshButton.setToolTipText("Réinitialise la page.");
		refreshButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				refreshEditor();
			}
		});
		toolbar.add(refreshButton);
		toolbar.addSeparator();
		
		getButton = new JRadioButton("GET");
		getButton.setSelected(true);
		getButton.setContentAreaFilled(false);
		getButton.setMargin(new Insets(0,0,0,0));
		getButton.setBorderPainted(false);
		getButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
					changeProcessThread();
			}
		});
		toolbar.add(getButton);
		toolbar.addSeparator();
		headButton = new JRadioButton("HEAD");
		headButton.setSelected(true);
		headButton.setContentAreaFilled(false);
		headButton.setMargin(new Insets(0,0,0,0));
		headButton.setBorderPainted(false);
		headButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
					changeProcessThread();
			}
		});
		toolbar.add(headButton);
		toolbar.addSeparator();
		ButtonGroup group = new ButtonGroup();
		group.add(getButton);
		group.add(headButton);
		
		historic = new Vector<String>();
		addressBar = new JComboBox(historic);
		addressBar.setEditable(true);
		addressBar.setFont(Configuration.FONT);
		addressBar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				addressBarAction();
			}
		});
		addressBar.setToolTipText("Barre d'adresse");
		toolbar.add(addressBar);
		
		this.add(toolbar,BorderLayout.NORTH);
		
		/*JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT,15,25));

		final ImageIcon icone = UResource.createIcon("refresh2.gif", "resource"); 
		simulation = new JPanel(){
			
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics cr){
				super.paintComponent(cr);
				cr.drawImage(icone.getImage(), 0, 0, icone.getIconWidth(), icone.getIconHeight(), this);
			}
		};		
		panel.add(simulation);*/
		
		statusBar = new JLabel("RAMClient 1.0");		
		statusBar.setFont(Configuration.FONT);	
		//panel.add(statusBar);
		
			
		this.add(statusBar,BorderLayout.SOUTH);
		//this.add(panel,BorderLayout.SOUTH);
		
		editor = new JEditorPane();
		editor.setEditable(false);
		editor.addHyperlinkListener(new HyperlinkListener(){
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				hyperlinkAction(e);
			}			
		});
		this.addPopupMenuToEditor();		
		this.setDefaultPage();
		this.add(new JScrollPane(editor),BorderLayout.CENTER);
		this.configureNavigationButtons();
		
	}
	
	
	/**
	 * <p>Affecte la page par defaut a la session.</p>
	 */
	public void setDefaultPage(){	
		
		File directory = new File(Configuration.RESOURCE_DIRECTORY);
		if(!directory.exists()) directory.mkdir();
		File file = new File(Configuration.RESOURCE_DIRECTORY+"index.html");
		if(!file.exists()){
			try { 
				file.createNewFile(); 
				PrintWriter out = new PrintWriter(new FileOutputStream(file));
				out.write(HTTPSession.HTML);
				out.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "La page par defaut n'a pu être atteint.", "RAMClient",
						JOptionPane.WARNING_MESSAGE);		
			}			
		}
		
		if(file.exists()){			
			try {
				editor.setContentType("text/html");
				editor.setPage("file:///"+file.getCanonicalPath());				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "La page par defaut n'a pu être atteint.", "RAMClient",
					JOptionPane.WARNING_MESSAGE);
			}
		}		
		
	}
	
	public void changeProcessThread() {
		if(headButton.isSelected()) method = HTTP.HEAD_METHOD ;
		else method = HTTP.GET_METHOD ;
	}
	
	public void cleanHistoric(){
		if(historic!=null && historic.size()>0){
			for(int i= (historic.size()-1);i>=0;i--) historic.remove(i);
			this.setDefaultPage();
			this.configureNavigationButtons();
			statusBar.setText("  RAMClient 1.0");
			
		}
	}
	
	/*
	 * Ne pas oublier de redéfinir hyperlinkUpdate(HyperlinkEvent e) pour editor
	 **/
	
	private void addressBarAction() {
		if(addressBar.getSelectedIndex()==-1){
			
			if(current!=-1){
				historic.insertElementAt((String)addressBar.getSelectedItem(), current);
				for(int i= (historic.size()-1);i>current;i--) historic.remove(i);
			}else{
				historic.add((String)addressBar.getSelectedItem());
				current = historic.size()-1;
			}			
			
		}else current = addressBar.getSelectedIndex();
		statusBar.setText((String)addressBar.getSelectedItem());
		configureNavigationButtons();
		refreshEditor();
	}
	
	private void configureNavigationButtons() {
		if(historic==null || historic.size()==0){
			this.firstButton.setEnabled(false);
			this.nextButton.setEnabled(false);
			this.previousButton.setEnabled(false);
			this.lastButton.setEnabled(false);
			this.refreshButton.setEnabled(false);
		}else if(historic.size()==1){
			this.firstButton.setEnabled(false);
			this.nextButton.setEnabled(false);
			this.previousButton.setEnabled(false);
			this.lastButton.setEnabled(false);
			this.refreshButton.setEnabled(true);
		}else{
			if(current==0){
				this.firstButton.setEnabled(false);
				this.nextButton.setEnabled(true);
				this.previousButton.setEnabled(false);
				this.lastButton.setEnabled(true);
				this.refreshButton.setEnabled(true);
			}else if(current==(historic.size()-1)){
				this.firstButton.setEnabled(true);
				this.nextButton.setEnabled(false);
				this.previousButton.setEnabled(true);
				this.lastButton.setEnabled(false);
				this.refreshButton.setEnabled(true);
			}else{
				this.firstButton.setEnabled(true);
				this.nextButton.setEnabled(true);
				this.previousButton.setEnabled(true);
				this.lastButton.setEnabled(true);
				this.refreshButton.setEnabled(true);
			}
		}
	}
	
	private void addPopupMenuToEditor(){
		JPopupMenu popup = new JPopupMenu();
		JMenuItem otherNavigator = new JMenuItem("Améliorer la Visibilité",UResource.createIcon("browser-ico.png", "resource"));
		otherNavigator.setToolTipText("Ouvrir la page courante dans un autre Navigateur");
		otherNavigator.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				OpenCurrentPageInOtherNavigator();
			}
		});
		popup.add(otherNavigator);
		
		JMenuItem cleanHistoric = new JMenuItem("Effacer l'historique",UResource.createIcon("trash-ico.png", "resource"));
		cleanHistoric.setToolTipText("Efface l'historique de cette session");
		cleanHistoric.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				cleanHistoric();
			}
		});
		popup.add(cleanHistoric);
	
		JMenuItem print = new JMenuItem("Imprimer",UResource.createIcon("print-ico.png", "resource"));
		print.setToolTipText("Imprime la page courante.");
		print.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				printAction();
			}
		});
		popup.add(print);
		
		JMenuItem saveAs = new JMenuItem("Sauvegarder Sous",UResource.createIcon("download.png", "resource"));
		saveAs.setToolTipText("Enregistrer le fichier courant.");
		saveAs.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				saveAction();
			}
		});
		popup.add(saveAs);
		
		//printAction
		editor.setComponentPopupMenu(popup);
	}
	
	private void getPage(String adresse){
		try{
			
			if(!historic.contains(adresse))	addressBar.addItem(adresse);
			if( ( (String)addressBar.getSelectedItem()).compareToIgnoreCase(adresse)!=0){
				addressBar.setSelectedItem(adresse);
			}			
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, "Echec de connexion au serveur", "RAMClient", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void hyperlinkAction(HyperlinkEvent e) {
		HyperlinkEvent.EventType type = e.getEventType();
	      if (type == HyperlinkEvent.EventType.ACTIVATED){
	    	  if(e.getURL()!=null){
	    		  statusBar.setText("Accés à "+e.getURL().toExternalForm()+"...");
	    		  getPage(e.getURL().toExternalForm());
	    	  }
	      }
	      if(type==HyperlinkEvent.EventType.ENTERED){
	    	  if(e.getURL()!=null)  statusBar.setText(e.getURL().toExternalForm());
	      }
	      if(type==HyperlinkEvent.EventType.EXITED)  {
	    	  if(e.getURL()!=null)  statusBar.setText(e.getURL().toExternalForm());
	      }
	      
	}
	
	public void setContainer(JTabbedPane container){
		this.container = container ;
	}
	
	
	/**
	 * Rafraichit la session courante.
	 */
	public void refreshEditor(){
		
		if(container!=null){
			int index = container.indexOfComponent(this);
			if(index!=-1){
				String title = (String)addressBar.getSelectedItem();
				if(title==null) title = "Onglet "+number;
				container.setTitleAt(index, title);
			}
		}
		
		final HTTPSession session = this;
				
		
		if(this.method.compareToIgnoreCase(HTTP.HEAD_METHOD)==0){
			Thread thread = new Thread(){
				public void run(){
					try {
						HTTPConnection conex = new HTTPConnection();
						conex.head((String)addressBar.getSelectedItem());
						File file = conex.getResponseOfHeadRequest();
						conex.disconnect();	
						editor.setContentType("text/plain");
						String page ="file:///"+file.getCanonicalPath();
						editor.setFont(Configuration.FONT);						
						setDefaultPage();
						editor.setPage(page);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(session, "Echec de connexion au serveur", "RAMClient", JOptionPane.ERROR_MESSAGE);
					}
				}
			};
			thread.start();
			/*try {
				HTTPConnection conex = new HTTPConnection();
				conex.head((String)addressBar.getSelectedItem());
				File file = conex.getResponseOfHeadRequest();
				conex.disconnect();	
				editor.setContentType("text/plain");
				String page ="file:///"+file.getCanonicalPath();
				editor.setFont(Configuration.FONT);
				
				//editor.getPage().toExternalForm()!=null && editor.getPage().toExternalForm().comparet=To(page)==0
				setDefaultPage();
				//if(editor.getPage()==null  ) this.setDefaultPage();				
				editor.setPage(page);//.replaceAll(" ", "%20")
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Echec de connexion au serveur", "RAMClient", JOptionPane.ERROR_MESSAGE);
			}*/
			
		}else{
			Thread thread = new Thread() {
				public void run(){
					try {
						editor.setContentType("text/html");
						String page = ((String)addressBar.getSelectedItem());
						System.out.println("Avt lancement");
						System.out.println("Avt chargement");
						setDefaultPage();
						System.out.println("Avt deuxieme chargement");
						editor.setPage(page.replaceAll(" ", "%20"));
						System.out.println("End chargement");						
					} catch (IOException e) {
						JOptionPane.showMessageDialog(session, "Echec de connexion au serveur", "RAMClient", JOptionPane.ERROR_MESSAGE);
					}
				}
			};
			thread.start();
			/*try {
				editor.setContentType("text/html");
				String page = ((String)addressBar.getSelectedItem());
				
				//editor.getPage().toExternalForm()!=null && editor.getPage().toExternalForm().comparet=To(page)==0
				setDefaultPage();
				//if(editor.getPage()==null)	this.setDefaultPage();				
				editor.setPage(page.replaceAll(" ", "%20"));				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Echec de connexion au serveur", "RAMClient", JOptionPane.ERROR_MESSAGE);
			}*/
		}
	}
		
	/**
	 * @return La methode de traitement de la requete.
	 */
	public String getMethod() { return this.method; }
	
	private void firstButtonAction(){ 
		this.current = 0 ;		
		addressBar.setSelectedIndex(this.current);
	}
	
	private void previousButtonAction(){		
		if(this.current>0){
			this.current--;
			addressBar.setSelectedIndex(this.current);
		}
	}
	
	
	private void nextButtonAction() {
		if(this.current<(historic.size()-1)){
			this.current++;
			addressBar.setSelectedIndex(this.current);
		}
	} 
	private void lastButtonAction() {
		this.current = (historic.size()-1);
		addressBar.setSelectedIndex(this.current);
	}
	
	/**
	 * <p>Lance l'impresson de la page.</>
	 */
	public void printAction() {
		if(editor!=null){
			try {
					this.editor.print(new MessageFormat("RAMClient"), new MessageFormat("Implementation du protocole HTTP."));
			} catch (PrinterException e) {
					JOptionPane.showMessageDialog(null, "Un probléme a été détecté lors du lancement de l'impression.\n" +
							"Vérifiez que votre imprimante est bien connecté", "RAMClient", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
			}
		}
	}
	
	/**
	 * <p>Lance l' ouverture de la page dans un autre navigateur.</>
	 */
	public void OpenCurrentPageInOtherNavigator(){
		if(current!=-1){
			if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE) ) {
				try {
					Desktop.getDesktop().browse( new URI(( (String)addressBar.getSelectedItem()).replaceAll(" ","%20")));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Un probléme est survenu lors du lancement de votre navigatuer." +
							"\nVerifiez qu'il est correctement installé","RAMClient" , JOptionPane.ERROR_MESSAGE);
				}
			}else
				JOptionPane.showMessageDialog(null, "Votre Systéme ne supporte pas ce type d'opération.","RAMClient" , 
							JOptionPane.WARNING_MESSAGE);
		}else
			JOptionPane.showMessageDialog(this, "Cette page ne peut être ouvert sur un autre navigateur","RAMClient" , 
					JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	/**
	 * <p>Sauvegarde le contenu de la page .</>
	 */
	public void saveAction(){
		if(current!=-1){
			try {
				JFileChooser explorer = new JFileChooser();
				int answer = explorer.showSaveDialog(this);
				if(answer==JFileChooser.APPROVE_OPTION){
					HTTPConnection conex = new HTTPConnection();
					conex.get(((String)addressBar.getSelectedItem()).replaceAll(" ", "%20"));
					
					String name = explorer.getSelectedFile().getCanonicalPath();
					if(conex.getContent()!=null){
						String type = conex.getContentType();
						File file = new File(name);
						file.createNewFile();		
						if(type!=null && type.startsWith("text")){
							PrintWriter out = new PrintWriter(new FileOutputStream(file));
							Scanner in = new Scanner(conex.getContent());
							while(in.hasNextLine()) out.println(in.nextLine());							
							out.flush();
							in.close();
							out.close();
						}else{
							DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
							DataInputStream in = new DataInputStream(conex.getContent());
							byte[] buff = new byte[2048];
							int read = in.read(buff, 0, 2048);
							while(read>=0){						
								out.write(buff,0 ,read);
								read = in.read(buff, 0, 2048);
							}
							in.close();
							out.flush();		
							out.close();							
						}						
						JOptionPane.showMessageDialog(null, "Le fichier "+name+" a bien été sauvegardé","RAMClient" , 
								JOptionPane.INFORMATION_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null, "L'application ne peut sauvegarder "+name,"RAMClient" , 
								JOptionPane.INFORMATION_MESSAGE);
					}					
					conex.disconnect();										
				}
				
			} catch (Exception e) {;
				JOptionPane.showMessageDialog(null, "Erreur lors de la Sauvegarde du fichier.","RAMClient" , 
						JOptionPane.ERROR_MESSAGE);
			}
		}else JOptionPane.showMessageDialog(this, "Ce fichier ne peut être sauvegardé.","RAMClient" , 
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/*public static void main(String[] arg){
		JFrame frame = new JFrame("Test");
		frame.setBounds(50,50,800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new HTTPSession());
		frame.setVisible(true);	
		
	}*/
	
	
}
