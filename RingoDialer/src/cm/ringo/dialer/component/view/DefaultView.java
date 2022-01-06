package cm.ringo.dialer.component.view;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.l2fprod.gui.plaf.skin.Skin;
import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;

import cm.enterprise.software.component.model.event.AnyChangedEvent;
import cm.enterprise.software.exception.OperationFailureException;
import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.enterprise.software.helper.ExceptionResource;
import cm.enterprise.software.helper.Helper;
import cm.enterprise.software.interfaces.Module;
import cm.ringo.dialer.component.controller.DialerController;
import cm.ringo.dialer.component.model.base.connection.ConnectionModel;
import cm.ringo.dialer.component.model.event.LanguageChangedEvent;
import cm.ringo.dialer.helper.DialerConstants;
import cm.ringo.dialer.helper.DialerMultilingualResource;
import cm.ringo.dialer.helper.connection.constants.ConnectionConstants;
import cm.ringo.dialer.interfaces.Connection;
import cm.ringo.dialer.interfaces.Dialer;
import cm.ringo.dialer.startup.ModuleConnexion;
import cm.ringo.dialer.util.DialerFrame;
import cm.ringo.dialer.util.DialerOption;
import cm.ringo.dialer.util.TaskBarOption;

public class DefaultView extends DialerView {

	/**
	 * La fenetre principal du dialer
	 */
	private static DialerFrame frame ;
	
	/**
	 * Les menu de la barre de menu du dialer.
	 */
	private JMenu file,edition, languages, ringo, help ;
	
	/**
	 * Les options du dialer 
	 */
	private DialerOption createNewAccount, connexionStatus, space, exit, fr, en, preference, updates, manual, about; 
	
	//, online
	
	/**
	 * Le module de gestion de la connexion
	 */
	private Connection conex = ModuleConnexion.getModule();
	
	/**
	 * La barre de tache
	 */
	private TrayIcon tray ;
	
	/**
	 * Le menu de la barre des taches.
	 */
	private PopupMenu menu ;
	
	/**
	 * Les options de la baree des taches
	 */
	private TaskBarOption visibilityOption, connectionOption, preferenceOption, exitOption;

	/**
	 * Font du menu de la barre des taches
	 */
	private Font font = new Font("Arial", Font.PLAIN, 12);
	
	/**
	 * La fenetre d'informations du dialer.
	 */
	//private JFrame dialerNews = null ;
	
	/**
	 * Le browser
	 */
	//private Browser browser ;
	
	
	public DefaultView(DialerController controller) throws ProgramException {
		super(controller);
					
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		
		DialerConstants cst = Application.getModule(Dialer.class).getResource(DialerConstants.class);
		try {
			Skin theSkinToUse;
			theSkinToUse = SkinLookAndFeel.loadThemePack(cst.getDefaultTheme());
			SkinLookAndFeel.setSkin(theSkinToUse);
	        UIManager.setLookAndFeel(new SkinLookAndFeel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			frame = new DialerFrame();
			frame.setResizable(false);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();		
			frame.setBounds((int)dim.getWidth()/2, (int)dim.getHeight()/5, 250, 425);
			frame.setIconImage(ImageIO.read(cst.getLogoApplication()));
			frame.setFont(new Font("Tahoma",1,12));
			frame.addVisibleChangeListener( new PropertyChangeListener(){
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					//boolean value = (Boolean)evt.getNewValue();
					startDialer();
				}
			});
			
			
			/*frame.addWindowListener(new WindowAdapter(){
				
				@Override
				public void windowOpened(WindowEvent e) {
					if(dialerNews==null){
						ActionListener listener = new ActionListener(){
							public void actionPerformed(ActionEvent evt){
								createBrowser();
							}
						};
						
						Horloge timer = new Horloge(1000,listener);//monter l'initialisation a 3secondes
						timer.setRepeats(false);
						timer.start();
					}
				}
				
			});*/

			createContent();
			createMenuBar();
			createTaskBar();
			SwingUtilities.updateComponentTreeUI(frame);
			
			/*
			 * Ajout d'un ecouteur de changement d'etat de la connection
			 */
			conex.addStateChangeListener(new PropertyChangeListener(){
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					Integer state = (Integer)evt.getNewValue();
					if(state==ConnectionConstants.DIALING_STATE){
						tray.displayMessage("RingoDialer", "Wait...", TrayIcon.MessageType.INFO);
						return;
					}
					
					if(state==ConnectionConstants.INACTIVE_STATE){
						tray.displayMessage("RingoDialer", "Disconnected", TrayIcon.MessageType.INFO);
						return;
					}
					
					if(state==ConnectionConstants.ACTIVE_STATE){
						tray.displayMessage("RingoDialer", "Connected", TrayIcon.MessageType.INFO);
						//DefaultView.this.startBrowser();
						return;
					}
					
				}
				
			});
			
			final String file = Application.getModule(Dialer.class).getResource(DialerConstants.class).getUserPropertiesFile();
			//Initialisation du nom utilisateur au login précédement stocké.
						
			String login = Helper.getPropertiesOfFile("login", file);
			conex.setUsername(login);
			
			//Ajout d'un ecouteur de changement de nom utilisateur au module de connexion.
			//Cet ecouteur aura pour but, de stocker a chaque changement le nouveau nom d'utilisateur saisi
			
			conex.addUsernameChangeListener( new PropertyChangeListener(){
				public void propertyChange(PropertyChangeEvent evt) {
					String username = (String) evt.getNewValue();
					String comment = "Created by Ringo S.A." ;
					try {
						Helper.setProperties("login", username, file, comment);
					} catch (OperationFailureException e) {
						//e.printStackTrace();
					}
				}
			});
			
			
			//
			// inititilalisation 
			String encryptedPwd = Helper.getPropertiesOfFile("xywz", file);
			final byte[] encryptionKey = getKey();
			
			if(encryptedPwd != null && encryptedPwd.trim().isEmpty() == false){				
				String password = unshadow(encryptedPwd, encryptionKey);
				conex.setPassword(password);
			}
			
			
			//Ajout d'un ecouteur de changement de nom utilisateur au module de connexion.
			//Cet ecouteur aura pour but, de stocker a chaque changement le nouveau nom d'utilisateur saisi
			
			conex.addPasswordChangeListener( new PropertyChangeListener(){
				public void propertyChange(PropertyChangeEvent evt) {
					
					String password = (String) evt.getNewValue();
					String comment = "Created by Ringo S.A." ;
					try {
						if(password != null) {
							Helper.setProperties("xywz", conex.getRememberMe()? shadow(password, encryptionKey) : "", file, comment);
						}
					} catch (Exception e) {
						//e.printStackTrace();
					}
				}
			});			
			
			//
			String rememberMe = Helper.getPropertiesOfFile("rememberMe", file);
			
			if(rememberMe != null){
				conex.setRememberMe(rememberMe.equals("true"));
			}
			
			
			//Ajout d'un ecouteur de changement de nom utilisateur au module de connexion.
			//Cet ecouteur aura pour but, de stocker a chaque changement le nouveau nom d'utilisateur saisi
			
			conex.addRememberMeChangeListener( new PropertyChangeListener(){
				public void propertyChange(PropertyChangeEvent evt) {
										
					String rememberMe = ((Boolean) evt.getNewValue()).toString();
					String comment = "Created by Ringo S.A." ;
					try {
						if(rememberMe != null) {
							Helper.setProperties("rememberMe", rememberMe, file, comment);
						}
					} catch (Exception e) {
						//e.printStackTrace();
					}
				}
			});				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	private static String bytesToDigits(byte[] bytes){
		StringBuilder builder = new StringBuilder();
		for(Byte b : bytes){ builder.append(b).append(".");	}
		return builder.toString();
	}
	
	private static byte[] digitsToBytes(String digits){		
		
		String[] bytesString =  digits != null? digits.split("\\.") : new String[]{};
		byte[] bytes = new byte[bytesString.length];
		
		for(int i=0; i < bytes.length; i++){			
			bytes[i] = new Byte(bytesString[i]);		}
		
		return bytes;
		
	}
	
	public static List<byte[]> generateKeys() throws Exception {
		
		List<byte[]> result = new ArrayList<byte[]>();
		
		for(int i = 0; i < 10; i++){
	       KeyGenerator kgen = KeyGenerator.getInstance("AES");
	       kgen.init(128);
	       SecretKey skey = kgen.generateKey();	
	       result.add(skey.getEncoded()); 
		}		
		return result;
	}
	
	private static byte[] getKey(){
		
		String[] keyString = " 17, 4, 80, 83, -101, 81, 93, 11, -127, -126, -7, -64, 108, 15, 60, -118 ".split(",");
		byte[] keyBytes = new byte[keyString.length];
		for(int i=0; i < keyBytes.length; i++){ keyBytes[i] = new Byte(keyString[i].trim()); }
		return keyBytes;
	}
	
	private static String shadow(String input, byte[] key) {

		try{
			
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");		
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			return bytesToDigits(cipher.doFinal(input.getBytes()));
			
		}catch(Exception e){
			return "";
		}
	}
	
	private static String unshadow(String input, byte[] key) {		
		try{
			
	       SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		   Cipher cipher = Cipher.getInstance("AES");
	       cipher.init(Cipher.DECRYPT_MODE, skeySpec);	       
	       byte[] original = cipher.doFinal(digitsToBytes(input));
	       return new String(original);
	       
		}catch(Exception e){
			return "";
		}
	}
	
/*	public static void main(String[] args) throws Exception {

		List<byte[]> keys = generateKeys();
		for(byte[] k : keys) System.out.println(Arrays.toString(k));
	}*/
	
	
	
	private void createMenuBar(){
		
		DialerMultilingualResource resource = Application.getModule(Dialer.class).getResource(DialerMultilingualResource.class);
		
		JMenuBar menuBar = new JMenuBar();
		
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		menuBar.setBackground(new Color(214, 214, 214));
		
		//Menu File
		file = new JMenu(resource.getValueOf("fileMenu"));
		file.setMnemonic('f');
		ActionListener exitListener = new ActionListener(){ 
			@Override
			public void actionPerformed(ActionEvent e) {
				exitDialer();
			}
		};
				
		exit = new DialerOption(resource.getValueOf("exitItem"),'Q', exitListener);
		file.add(exit);
		menuBar.add(file);
		
		//Menu Edition
		edition = new JMenu(resource.getValueOf("editionMenu"));
		edition.setMnemonic('E');
		edition.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				JOptionPane.showMessageDialog(frame, "test");
			}
		});
						
		//Menu Languages
		languages = new JMenu(resource.getValueOf("languageMenu"));
		fr = new DialerOption(resource.getValueOf("frenchItem"),'F', new ActionListener(){ public void actionPerformed(ActionEvent evt){ changeLanguage(Locale.FRENCH.getLanguage()); } });
		
		en = new DialerOption(resource.getValueOf("englishItem"),'E', new ActionListener(){ public void actionPerformed(ActionEvent evt){ changeLanguage(Locale.US.getLanguage()); } });
		languages.add(fr);
		languages.add(en);
		edition.add(languages);
		
		//Menu Preference.
		preference = new DialerOption(resource.getValueOf("preferenceItem"),'P', new ActionListener(){public void actionPerformed(ActionEvent evt){
			JOptionPane.showMessageDialog(frame, preference.getText(),"",JOptionPane.INFORMATION_MESSAGE);
		}});
		preference.setEnabled(false);
		
		edition.add(preference);
		//menuBar.add(edition);

		//Menu Ringo
		ringo = new JMenu(resource.getValueOf("ringoMenu"));
		ringo.setMnemonic('r');
		
		//Espace client
		space = new DialerOption(resource.getValueOf("clientSpace"),'C',new ActionListener(){public void actionPerformed(ActionEvent evt){
			openBrowser("http://www.ringo.cm/client/home.html");
		}});
		ringo.add(space);

		//Site Web de ringo
		connexionStatus = new DialerOption(resource.getValueOf("connectionStatus"),'I',new ActionListener(){public void actionPerformed(ActionEvent evt){
			if(conex.isActive()) conex.showInfosView();
		}});		
		ringo.add(connexionStatus);
		
		createNewAccount = new DialerOption(resource.getValueOf("createNewAccount"),'N',new ActionListener(){public void actionPerformed(ActionEvent evt){
			openBrowser("http://ringo.cm/create-account/");
		}});
		ringo.add(createNewAccount);		
		
		
		menuBar.add(ringo);	
		
				
		//Menu Aide
		boolean isFrench = Application.getCurrentLanguage().compareTo(Locale.FRENCH.getLanguage())==0 ;
		help = new JMenu(resource.getValueOf("helpMenu"));
		help.setMnemonic(isFrench? 'a':'h');
		
		updates = new DialerOption(resource.getValueOf("dialerUpdates"),'U', new ActionListener(){public void actionPerformed(ActionEvent evt){
			openBrowser("http://www.ringo.cm/dialer/update.html");
		}});
		help.add(updates);		
		
		manual = new DialerOption(resource.getValueOf("manualItem"),'D', new ActionListener(){public void actionPerformed(ActionEvent evt){
			openBrowser("http://www.ringo.cm/dialer/manual.html");
		}});		
		help.add(manual);
		
		/*online = new DialerOption(resource.getValueOf("onlineItem"),'R', new ActionListener(){public void actionPerformed(ActionEvent evt){
			JOptionPane.showMessageDialog(frame, online.getText(),"",JOptionPane.INFORMATION_MESSAGE);
		}});
		help.add(online);*/
		
		about = new DialerOption(resource.getValueOf("aboutItem"),'A', new ActionListener(){public void actionPerformed(ActionEvent evt){
			openBrowser("http://www.ringo.cm/dialer/about.html");
		}});
		help.add(about);
		menuBar.add(help);
				
		frame.setJMenuBar(menuBar);
	}
	
	private void createTaskBar(){
		
		if(SystemTray.isSupported()){
			DialerConstants cst = Application.getModule(Dialer.class).getResource(DialerConstants.class);
			Image icon = (new ImageIcon(cst.getLogoSystemTray())).getImage();
			this.createPopupOfTaskBar();
			tray = new TrayIcon(icon,"RingoDialer",menu);
			tray.setImageAutoSize(true);
					
			tray.addActionListener(new ActionListener(){
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(conex.isActive()){
						frame.setVisible(false);
						if(conex.infosViewIsVisible()) conex.hideInfosView(); 
						else conex.showInfosView();						
					}else frame.setVisible(!frame.isVisible());
				}
				
			});

			try{
				SystemTray.getSystemTray().add(tray);
			}catch(AWTException err){}
		}
		
	}
	
	private void createPopupOfTaskBar(){
		
		menu =  new PopupMenu();
		
		DialerMultilingualResource rsrc = Application.getModule(Dialer.class).getResource(DialerMultilingualResource.class);
		
		ActionListener listener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				startDialer();
			}};		
		visibilityOption = new TaskBarOption(rsrc.getValueOf("visibilityOption"),listener);
		
		listener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				if(tray!=null){
					tray.displayMessage("Connecion", "Lancement ou arret de la connection", TrayIcon.MessageType.INFO);
				}
				conex.dialHangup();
			}
		};		
		connectionOption = new TaskBarOption(rsrc.getValueOf("connectionOption"),listener);
		
		listener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				if(tray!=null){
					tray.displayMessage("Preference Dialer", "Afficher ou fermer la fenetre", TrayIcon.MessageType.INFO);
				}
			}
		};
		preferenceOption = new TaskBarOption(rsrc.getValueOf("preferenceItem")+" Dialer",listener);
		preferenceOption.setEnabled(false);
		
		listener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				exitDialer();
			}
		};
		exitOption = new TaskBarOption(rsrc.getValueOf("exitItem")+" Dialer",listener);
	
		menu.setFont(font);
		menu.add(visibilityOption);
		menu.addSeparator();
		menu.add(connectionOption);
		menu.add(preferenceOption);
		menu.addSeparator();
		menu.add(exitOption);
	}
	
	private void createContent(){
		
		//Creation contenu de l'interface principal
		conex.installDefaultView();
		frame.getContentPane().add(conex.getContentOfDefaultView());

		//Creation vue secondaire dialer
		conex.installInfosView(); //on la rendra visible plus tard
	}
	
	/*private void createBrowser(){
		browser = BrowserFactory.createBrowser(BrowserType.Mozilla);
		dialerNews = new JFrame("RingoDialer News");
		dialerNews.getContentPane().add(browser.getComponent());
		int x = (int) ( frame.getLocation().getX()-605);
		int y = (int) ( frame.getLocation().getY());
		dialerNews.setBounds(x, y, 600, 500);
		dialerNews.setResizable(false);
	}*/

	private void reinitMainFrame() {
		if(frame.getContentPane().getComponentCount()>0) frame.getContentPane().removeAll();
		frame.getContentPane().add(conex.getContentOfDefaultView());
		frame.getContentPane().repaint();
	}
	
	
	/**
	 * Permet le lancement du Browser sur l'addresse en parametre
	 * @param link Url a utiliser pour le lancement du Browser.
	 */
	public void openBrowser(String link){
		
		String message = Application.getResource(ExceptionResource.class).getValueOf("notSupportedException");
		
		if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE) ){
			try {
				Desktop.getDesktop().browse(new URI(link));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(frame, message,"",JOptionPane.WARNING_MESSAGE);
				e.printStackTrace();
				//startBrowser(); 
			}
		}else JOptionPane.showMessageDialog(frame, message,"",JOptionPane.WARNING_MESSAGE); //startBrowser();
		
		
	}
	
	/**
	 * Lance le browser, uniquement si la connexion est active
	 */
	/*public void startBrowser(){
		//if(conex.isActive()){
			browser.navigate("http://www.ringo.cm/");
			//browser.waitReady();
			dialerNews.setVisible(true);
		//}
	}*/
		
	/**
	 * Affiche ou cache la fenetre principale
	 */
	public void startDialer(){
		frame.setVisible(!frame.isVisible());
		if(conex.isActive()){
			if(frame.isVisible()) conex.hideInfosView(); 
			else conex.showInfosView();
			//startBrowser()
		}
	}
	
	/**
	 * Permet d'interrompre le programme.
	 */
	public void exitDialer(){
		if(conex.isActive()){
			conex.hangup();
		}
		ConnectionModel.closeAll();
		System.exit(0);
	}

	/**
	 * Permet de changer la langue de la vue.
	 * @param language Langue de la vue a changer
	 */
	public void changeLanguage(String language){
		DialerController c = (DialerController) controller;
		c.notifyEvent(language);
	}
	
	@Override
	public void disable() {
		//Le menu a disable
		file.setEnabled(false);
		exit.setEnabled(false);
		edition.setEnabled(false);
		languages.setEnabled(false);
		fr.setEnabled(false);
		en.setEnabled(false);
		//preference.setEnabled(false);
		ringo.setEnabled(false);
		connexionStatus.setEnabled(false);
		space.setEnabled(false);
		help.setEnabled(false);
		manual.setEnabled(false);
		//online.setEnabled(false);
		about.setEnabled(false);
	}

	@Override
	public void enable() {
		file.setEnabled(true);
		exit.setEnabled(true);
		edition.setEnabled(true);
		languages.setEnabled(true);
		fr.setEnabled(true);
		en.setEnabled(true);
		//preference.setEnabled(true);
		ringo.setEnabled(true);
		connexionStatus.setEnabled(true);
		space.setEnabled(true);
		help.setEnabled(true);
		manual.setEnabled(true);
		//online.setEnabled(true);
		about.setEnabled(true);
	}

	@Override
	public Object getContent() {
		return frame;
	}

	@Override
	public void hide() {
		frame.setVisible(false);
	}

	@Override
	public void show() {
		frame.setVisible(true);
	}
	
	@Override
	public void languageChanged(LanguageChangedEvent evt) {

		//MenuBar
		Module module = Application.getModule(Dialer.class);
		DialerMultilingualResource resource = module.getResource(DialerMultilingualResource.class);
		file.setText(resource.getValueOf("fileMenu"));
		exit.setText(resource.getValueOf("exitItem"));
		edition.setText(resource.getValueOf("editionMenu"));
		languages.setText(resource.getValueOf("languageMenu"));
		fr.setText(resource.getValueOf("frenchItem"));
		en.setText(resource.getValueOf("englishItem"));
		preference.setText(resource.getValueOf("preferenceItem"));
		
		ringo.setText(resource.getValueOf("ringoMenu"));
		connexionStatus.setText(resource.getValueOf("connectionStatus"));
		space.setText(resource.getValueOf("clientSpace"));
		
		help.setText(resource.getValueOf("helpMenu"));
		boolean isFrench = Locale.FRENCH.getLanguage().compareTo(evt.getNewLanguage())== 0;
		help.setMnemonic(isFrench? 'a':'h');
		manual.setText(resource.getValueOf("manualItem"));
		//online.setText(resource.getValueOf("onlineItem"));
		about.setText(resource.getValueOf("aboutItem"));
		
		frame.getJMenuBar().revalidate();
	
		//TaskBar
		visibilityOption.setLabel(resource.getValueOf("visibilityOption"));
		connectionOption.setLabel(resource.getValueOf("connectionOption"));
		preferenceOption.setLabel(resource.getValueOf("preferenceItem")+" Dialer");
		exitOption.setLabel(resource.getValueOf("exitItem")+ " Dialer");
		
		
		//Initialisation de la fenetre principale
		reinitMainFrame();
		
		//Content main frame
		/*if(frame.getContentPane().getComponentCount()>0) frame.getContentPane().removeAll();
		frame.getContentPane().add(conex.getContentOfDefaultView());
		frame.getContentPane().repaint();*/
		
	}

	@Override
	public void anyChanged(AnyChangedEvent event) {
	}
	
	
	//
	public static void showInformationBox(String information){
		JOptionPane.showMessageDialog(frame, information);
	}
}
