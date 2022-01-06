package view;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.nio.charset.Charset;

import helper.*;
import model.DES;
import model.base.*;
import helper.component.*;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane ;
	private JLabel description;
	private Color background = Color.black;
	private Color foreground = Color.white;
	private JEditorPane currentText ;
	
	/*Barre de Menu*/
	private JMenuBar menuBar;
	private JMenu fileMenu,editionMenu,helpMenu;
	private JMenuItem newItem,loadItem,exitItem;
	private JMenuItem encodeItem,decodeItem;//,spellingItem
	private JMenuItem aboutItem,manualItem;
	
	/* Menu popup*/
	private JPopupMenu popupMenu ;
	private JMenuItem selectAllItem,clearItem,copyItem,pasteItem;
	
	/* Barre d'outils */
	private ToolBar toolBar;
	
	
	/* Contenu de la fenetre*/
	private JTextField keyField ;
	private JEditorPane input ;
	
	/* Fentre Resultat*/
	private JDialog resultFrame ;
	private JRadioButton binaryButton,asciiButton;
	private JEditorPane output;
	private JMenuItem printItem,saveItem;
	
	/* Fenetre de verification*/
	/*private JDialog spellingFrame;
	private JTextArea text ;*/
	
	private HelpFrame helpFrame;
	
	/* Mode */
	/** Represente l'action ne rien faire.
	 */
	public static final int NOTHING = -1 ;
	/**
	 * Represente l'action ou le mode chiffrement.
	 */
	public static final int CIPHER = 0 ;
	/**
	 * Represente l'action ou le mode de dechiffrement.
	 */
	public static final int DECIPHER = 1 ;
	
	int currentMode = Window.NOTHING;

	public Window(){
		this.setTitle("Data Encryption Standard (DES)");
		Dimension dimScreen =Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(175,100, (int)(dimScreen.getWidth()-300),(int) (dimScreen.getHeight()*2/3));
		this.setIconImage(YooFactory.getImageIcon("logo.png").getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		Container content = this.getContentPane();
		content.setBackground(background);
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(background);
		content.add(contentPane);
		
		this.createMenuBar();
		this.createPopupMenu();
		this.createToolBar();
		this.createContentPanel();
		this.createResultFrame();
		//this.createSpellingFrame();
		
		description = new JLabel("DES by Charles Moute, Matricule 03x202");
		description.setFont(new Font(Font.DIALOG_INPUT,Font.PLAIN,14));
		description.setForeground(foreground);
		
		JPanel footer = new JPanel();
		footer.setBackground(new Color(10,10,10));
		
		content.add(footer,BorderLayout.SOUTH);
		footer.add(description);
		footer.setPreferredSize(new Dimension(this.getWidth(),30));
		this.setBackground(background);
		currentText = input;
		
		try { helpFrame = new HelpFrame();	} catch (Exception err) {
			JOptionPane.showMessageDialog(this, err.getMessage(), "Erreur", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private JMenuItem createMenuItem(String name, Icon icone, char car, int keyCode, int modifiers,ActionListener listener){
		JMenuItem jmi = new JMenuItem(name);
		jmi.setIcon(icone);
		jmi.setMnemonic(car);
		jmi.setAccelerator(KeyStroke.getKeyStroke(keyCode, modifiers));
		jmi.addActionListener(listener);
		return jmi;
	}
	
	
	private void createMenuBar() {
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File");
		ActionListener newListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				reinitContentPanel();
				reinitResultFrame();
			}
		};
		newItem = createMenuItem("New",YooFactory.getImageIcon("16x16/new.png"), 'N', KeyEvent.VK_N,InputEvent.CTRL_MASK, newListener);
		newItem.setToolTipText("Nouveau texte à chiffrer ou dechiffrer");
		fileMenu.add(newItem);
		
		ActionListener loadListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				loadAction();
			}
		};
		loadItem = createMenuItem("Load...", YooFactory.getImageIcon("16x16/open.png"), 'O', KeyEvent.VK_O,InputEvent.CTRL_MASK, loadListener);
		loadItem.setToolTipText("Charge le contenu d'un fichier, comme texte a chiffrer ou a dechiffrer.");
		fileMenu.add(loadItem);
		
		ActionListener exitListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				System.exit(0);
			}
		};
		exitItem = createMenuItem("Exit", YooFactory.getImageIcon("16x16/exit-ico.png"), 'X', KeyEvent.VK_X,InputEvent.CTRL_MASK, exitListener);
		exitItem.setToolTipText("Quitte l'application");
		fileMenu.add(exitItem);
	
		menuBar.add(fileMenu);
		
		editionMenu = new JMenu("Edition");
		
		ActionListener encodingListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				encodingAction();
			}
		};
		encodeItem = createMenuItem("Encode", null, 'L', KeyEvent.VK_L,InputEvent.CTRL_MASK, encodingListener);
		encodeItem.setToolTipText("Lance le chiffrement du texte");
		editionMenu.add(encodeItem);
		
		ActionListener decodingListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				decodingAction();
			}
		};
		decodeItem = createMenuItem("Decode", null, 'U', KeyEvent.VK_U,InputEvent.CTRL_MASK, decodingListener);
		decodeItem.setToolTipText("Lance le dechiffrement du texte");
		editionMenu.add(decodeItem);
		
		/*ActionListener spellingListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				spellingAction();
			}
		};
		spellingItem = createMenuItem("Spelling", null, 'R', KeyEvent.VK_R,InputEvent.CTRL_MASK, spellingListener);
		spellingItem.setToolTipText("Texte qui sera réellement chiffré.");
		editionMenu.add(spellingItem);*/
		
		menuBar.add(editionMenu);
		
		helpMenu = new JMenu("Help");
		
		final JFrame frame = this ;
		ActionListener aboutListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				JOptionPane.showMessageDialog(frame, "Application Permettant l'application de l'algorithme de cryptographie DES.\n Par Charles Mouté", "About..",JOptionPane.INFORMATION_MESSAGE);
			}
		};
		aboutItem = createMenuItem("About", null, 'i', KeyEvent.VK_I,InputEvent.CTRL_MASK, aboutListener);
		aboutItem.setToolTipText("A propos de l'application");
		helpMenu.add(aboutItem);
		
		ActionListener manualListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				manualAction();
			}
		};
		manualItem = createMenuItem("Manual", null, 'M', KeyEvent.VK_M,InputEvent.CTRL_MASK, manualListener);
		manualItem.setToolTipText("Lance le dechiffrement du texte");
		helpMenu.add(manualItem);
		
		menuBar.add(helpMenu);
		
	}
	
	private void createPopupMenu() {
		popupMenu = new JPopupMenu();
		
		ActionListener selectAllListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				selectAllAction();
			}
		};
		selectAllItem = createMenuItem("Select All", null, 'A', KeyEvent.VK_A,InputEvent.CTRL_MASK, selectAllListener);
		selectAllItem.setToolTipText("Selection de tout le texte.");
		popupMenu.add(selectAllItem);
		
		ActionListener clearListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				clearAction();				
			}
		};
		clearItem = createMenuItem("Delete",YooFactory.getImageIcon("delete.png"), 'D', KeyEvent.VK_D,InputEvent.CTRL_MASK, clearListener);
		clearItem.setToolTipText("Efface tout le texte.");
		popupMenu.add(clearItem);
		
		popupMenu.addSeparator();
	
		ActionListener copyListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				copyAction();
			}
		};
		copyItem = createMenuItem("Copy", YooFactory.getImageIcon("copy.png"), 'C', KeyEvent.VK_C,InputEvent.CTRL_MASK, copyListener);
		copyItem.setToolTipText("Copie le texte selectionné.");
		popupMenu.add(copyItem);
		
		ActionListener pasteListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				pasteAction();
			}
		};
		pasteItem = createMenuItem("Paste", YooFactory.getImageIcon("paste.png"), 'V', KeyEvent.VK_V,InputEvent.CTRL_MASK, pasteListener);
		pasteItem.setToolTipText("Colle le texte contenu dans le presse papier.");
		popupMenu.add(pasteItem);
		
		contentPane.setComponentPopupMenu(popupMenu);		
	}
	
	private void addItemToPopupMenu() {
		
		popupMenu.addSeparator();
		
		ActionListener printListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				printAction();
			}
		};
		printItem = createMenuItem("Print", YooFactory.getImageIcon("print.png"), 'P', KeyEvent.VK_P,InputEvent.CTRL_MASK, printListener);
		printItem.setToolTipText("Lance l'impression du texte.");
		popupMenu.add(printItem);
		
		ActionListener saveListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				saveAction();
			}
		};
		saveItem = createMenuItem("Save As", YooFactory.getImageIcon("save.png"), 'S', KeyEvent.VK_S,InputEvent.CTRL_MASK, saveListener);
		saveItem.setToolTipText("Enregistre le texte dans un fichier.");
		popupMenu.add(saveItem);
	}
	
	private void removeItemToPopupMenu() {	
		
		if(printItem!=null) popupMenu.remove(printItem);		
		if(saveItem!=null) popupMenu.remove(saveItem);		
		if(!(popupMenu.getComponent(popupMenu.getComponentCount()-1) instanceof JMenuItem )) 
			popupMenu.remove(popupMenu.getComponentCount()-1);
	}
	
	private void createToolBar(){
		toolBar = new ToolBar(YooFactory.getImageIcon("logo.png").getImage(),"Data Encryption Standard");
		
		ActionListener cipherListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				encodingAction();
			}
		};
		toolBar.addButton("Encode", YooFactory.getImageIcon("crypte.png"), YooFactory.getImageIcon("crypte2.png"),null, cipherListener);
		toolBar.getButton(toolBar.getButtonCount()-1).setToolTipText("Chiffrer le texte");
		
		ActionListener decipherListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				decodingAction();
			}
		};
		toolBar.addButton("Decode", YooFactory.getImageIcon("decrypte.png"), YooFactory.getImageIcon("decrypte2.png"),null, decipherListener);
		toolBar.getButton(toolBar.getButtonCount()-1).setToolTipText("Déchiffrer le texte");
		
		ActionListener manualListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				manualAction();
			}
		};
		toolBar.addButton("Help", YooFactory.getImageIcon("help.png"), YooFactory.getImageIcon("help2.png"),null, manualListener);
		toolBar.getButton(toolBar.getButtonCount()-1).setToolTipText("Manuel d'aide");
		
		ActionListener exitListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				System.exit(0);
			}
		};
		toolBar.addButton("Exit", YooFactory.getImageIcon("exit.png"), YooFactory.getImageIcon("exit2.png"), null, exitListener);
		toolBar.getButton(toolBar.getButtonCount()-1).setToolTipText("Quitter l'application");
		
		YooRoundedPane roundPane = new YooRoundedPane(toolBar);
		//roundPane.setBackPaint(new Color(75, 75, 75));
		roundPane.setBackPaint(new Color(250, 250, 250));
		roundPane.setArcs(16,16);
		 
		roundPane.setBorderPaint(new Color(50,50,50));
		//roundPane.setBorderPaint(Color.white);
		roundPane.setStrokeBorder(4);
		 
		YooDivPane divPane = new YooDivPane(roundPane);
		divPane.setMarginTop(10);
		divPane.setMarginLeft(10);
		divPane.setMarginRight(10);
		divPane.setMarginBottom(3);
		divPane.setAlpha(false);		 
		 
		contentPane.add(divPane, BorderLayout.NORTH);
		divPane.setPreferredSize(new Dimension(contentPane.getWidth(), 100));
	}
	
	private void createContentPanel() {
		
		JPanel panel = new JPanel(new BorderLayout());
		
		
		JPanel pan1 = new JPanel (new FlowLayout(FlowLayout.LEFT,15,5));
		pan1.setBackground(foreground);
		JLabel keyLabel = new JLabel("DES Key :");
		keyLabel.setForeground(toolBar.getColor());
		pan1.add(keyLabel);
		keyField = new JTextField("Enter a Key");
		keyField.setFont(new Font(Font.DIALOG_INPUT,Font.PLAIN,12));
		keyField.setPreferredSize(new Dimension(this.getWidth()-150,25));
		keyField.setToolTipText("Clé à utiliser pour l'opération de chiffrement/dechiffrement");
		pan1.add(keyField);
		
		 
		YooDivPane divKeyField = new YooDivPane(pan1);//new YooDivPane(keyField);
		divKeyField.setMarginTop(10);
		divKeyField.setMarginLeft(10);
		divKeyField.setMarginRight(10);
		divKeyField.setMarginBottom(3);
		divKeyField.setAlpha(false);
		
		panel.add(divKeyField,BorderLayout.NORTH);
		
		input = new JEditorPane();		
		//input.setContentType("text/html; charset=iso-8859-1");
		input.setContentType("text/plain; charset=utf-8");
		input.setText("Enter a Text");
		input.setFont(new Font(Font.DIALOG_INPUT,Font.PLAIN,12));
		input.setToolTipText("Texte a chiffrer/déchiffrer");
		//input.setWrapStyleWord(true);
		
		removeItemToPopupMenu();
		input.setComponentPopupMenu(popupMenu);		
		JScrollPane scrollPane = new JScrollPane(input,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		
		YooDivPane divInput = new YooDivPane(scrollPane);		
		divInput.setMarginTop(10);
		divInput.setMarginLeft(93);
		divInput.setMarginRight(10);
		divInput.setMarginBottom(10);
		divInput.setAlpha(false);		
		panel.add(divInput,BorderLayout.CENTER);
		
		YooRoundedPane roundPane = new YooRoundedPane(panel);
		roundPane.setBackPaint(new Color(250, 250, 250));
		roundPane.setArcs(16,16);
		 
		roundPane.setBorderPaint(new Color(50,50,50));
		roundPane.setStrokeBorder(4);
		 
		YooDivPane divPane = new YooDivPane(roundPane);
		divPane.setMarginTop(10);
		divPane.setMarginLeft(10);
		divPane.setMarginRight(10);
		divPane.setMarginBottom(3);
		divPane.setAlpha(false);		 
	
		this.addWindowListener(new WindowAdapter(){
			
			public void windowDeiconified(WindowEvent e) {
				keyField.revalidate();
			}
		});
		
		contentPane.add(divPane, BorderLayout.CENTER);
		divPane.setPreferredSize(new Dimension(contentPane.getWidth(), 100));
		
	}
	
	private void createResultFrame() {
	
		resultFrame = new JDialog(this,true);
		
		JPanel panel = new JPanel(new BorderLayout());
		
		JToolBar bar = new JToolBar();
		bar.setFloatable(false);
		
		binaryButton = new JRadioButton("Binaire");
		binaryButton.setToolTipText("Transforme le texte Ascii en texte Binaire");
		binaryButton.setSelected(true);
		binaryButton.setContentAreaFilled(false);
		binaryButton.setMargin(new Insets(0,0,0,0));
		binaryButton.setBorderPainted(false);
		binaryButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				radioButtonAction();
			}
		});
		bar.add(binaryButton);
		bar.addSeparator();
		asciiButton = new JRadioButton("Ascii");
		asciiButton.setToolTipText("Transforme le texte Binaire en texte Ascii");
		asciiButton.setContentAreaFilled(false);
		asciiButton.setMargin(new Insets(0,0,0,0));
		asciiButton.setBorderPainted(false);
		asciiButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				radioButtonAction();
			}
		});
		bar.add(asciiButton);
		bar.addSeparator();
		ButtonGroup group = new ButtonGroup();
		group.add(binaryButton);
		group.add(asciiButton);
		
		panel.add(bar,BorderLayout.NORTH);
		
		output = new JEditorPane();
		//input.setContentType("text/html; charset=iso-8859-1");
		input.setContentType("text/plain; charset=utf-8");
		output.setFont(new Font(Font.DIALOG_INPUT,Font.PLAIN,12));
		output.setEditable(false);
		//output.setWrapStyleWord(true);		
		//addItemToPopupMenu();
		output.setComponentPopupMenu(popupMenu);
		
		JScrollPane scrollPane = new JScrollPane(output);
		panel.add(scrollPane,BorderLayout.CENTER);
		
		resultFrame.getContentPane().add(panel);
		resultFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent evt){
				removeItemToPopupMenu();
				currentText = input ;
				currentMode = Window.NOTHING;
			}
		});
		resultFrame.setResizable(false);
		resultFrame.setSize(700, 400);
		resultFrame.setLocation(this.getX()+this.getWidth()/2 - resultFrame.getWidth()/2,this.getY() + this.getHeight()/2 - resultFrame.getHeight()/2 );
		
	}
	
	/*private void createSpellingFrame(){
		spellingFrame = new JDialog(this,true);
		spellingFrame.setTitle("Texte réellement chiffré");
		
		text = new JTextArea();
		text.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,14));
		text.setEditable(false);
		text.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(text);
		
		spellingFrame.getContentPane().setLayout(new BorderLayout());
		spellingFrame.getContentPane().add(scrollPane);
		
		spellingFrame.setResizable(false);
		spellingFrame.setSize(700, 400);
		spellingFrame.setLocation(this.getX()+this.getWidth()/2 - spellingFrame.getWidth()/2,this.getY() + this.getHeight()/2 - spellingFrame.getHeight()/2 );
	}*/
	
	private void reinitContentPanel(){
		keyField.setText("Enter a Key");		
		input.setText("Enter a Text");
	}
	
	private void reinitResultFrame(){		
		output.setText("");		
	}
	
	private void loadAction() {
		try{
			
			JFileChooser explorer = new JFileChooser();
			int answer = explorer.showOpenDialog(this);
			if(answer==JFileChooser.APPROVE_OPTION){
				String name = explorer.getSelectedFile().getCanonicalPath();
				input.setText("");
				File file = new File(name);
				try{
					DataInputStream in = new DataInputStream(new FileInputStream(file));				
					String result = in.readUTF();	
					input.setText(result);
					in.close();
					System.out.println("Erreur la");
				}catch(IOException e){					
					DataInputStream in = new DataInputStream(new FileInputStream(file));				
					String result = null;
					result ="";
					byte[] buff = new byte[2048];
					int read = in.read(buff, 0, 2048);
					while(read>=0){
						String substr = new String(buff,0,read);
						result+=substr;
						read = in.read(buff, 0, 2048);
					}
					System.out.println(result);
					Charset charset = Charset.forName("UTF-8");
					result = charset.decode(charset.encode(result)).toString();
					input.setText(result);
					in.close();
					System.out.println("Erreur la2");
				}												
			}
		}catch(Exception err){
			JOptionPane.showMessageDialog(this, "Erreur lors du chargement du fichier", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void encodingAction(){ (new CipherThread(this)).start(); }
	
	private void decodingAction(){ (new DecipherThread(this)).start(); }
	
	private void manualAction() {	
		if(helpFrame!=null)helpFrame.setVisible(!helpFrame.isVisible());
	}
	
	private void selectAllAction(){		
		currentText.requestFocus();
		currentText.selectAll();
	}
	
	private void clearAction(){
		currentText.setText("");
	}
	
	private void copyAction() {
		try{
			currentText.copy();
		}catch(Exception err){
			JOptionPane.showMessageDialog(this, "Erreur lors de la copie", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void pasteAction() {
		try{
			currentText.paste();
		}catch(Exception err){
			JOptionPane.showMessageDialog(this, "Impossible de coller", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void printAction() {
		try{
			currentText.print();
		}catch(Exception err){
			JOptionPane.showMessageDialog(this, "Erreur lors du lancement de l'impression", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	
	private void saveAction() {
		try{
			
			JFileChooser explorer = new JFileChooser();
			int answer = explorer.showSaveDialog(this);
			if(answer==JFileChooser.APPROVE_OPTION){
				String name = explorer.getSelectedFile().getCanonicalPath();
				if(currentText.getText()!=null || !currentText.getText().isEmpty()){
					File file = new File(name);
					file.createNewFile();
					DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
					//out.write(currentText.getText().getBytes("ASCII"));
					out.writeUTF(currentText.getText());
					out.close();
					JOptionPane.showMessageDialog(null, "Texte sauvegardé dans le fichier "+name,"Info" , 
							JOptionPane.INFORMATION_MESSAGE);
				}else
					JOptionPane.showMessageDialog(null, "Le texte est vide","Error" , 
							JOptionPane.INFORMATION_MESSAGE);
			}
		}catch(Exception err){
			JOptionPane.showMessageDialog(this, "Erreur lors de la copie", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void radioButtonAction() {
	
		if(binaryButton.isSelected()){
			
			//JOptionPane.showMessageDialog(resultFrame, "Ascii-Binaire ");
			if(!Binary.isBinaryRepresentation(output.getText()) && !output.getText().isEmpty())
				try {
					output.setText(DESUtil.messageToDESBinaryString(output.getText()));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur", JOptionPane.WARNING_MESSAGE);
				}
		}
		
		if(asciiButton.isSelected()){
			
			//JOptionPane.showMessageDialog(resultFrame, "Binaire-Ascii");
			if(Binary.isBinaryRepresentation(output.getText()))
				try {
					output.setText(DESUtil.binaryStringToReadableMessage(output.getText()));	
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur", JOptionPane.WARNING_MESSAGE);
				}
		}
	}
	
	/*private void spellingAction(){
		if(input.getText().isEmpty())
			JOptionPane.showMessageDialog(this, "La zone de texte de l'espace de travail est vide","Erreur",JOptionPane.WARNING_MESSAGE);
		else{			
			try {
				text.setText(DESUtil.AsciiDecoding(DESUtil.AsciiEncoding(input.getText())));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur", JOptionPane.WARNING_MESSAGE);
			}
			spellingFrame.setVisible(true);
		}
	}*/
	
	private SimulationFrame simule = new SimulationFrame(this,"loading.gif");
    
    class SimuleThread extends Thread {
    	JFrame parent;
    	
    	public SimuleThread(JFrame parent){
    		this.parent = parent ;
    	}
    	
    	public void run() {    		    		
    		simule.showDialog(parent.getX()+parent.getWidth()/2 - simule.getWidth()/2, parent.getY() + parent.getHeight()/2 - simule.getHeight()/2 );
    	}    		
    }
    
    class CipherThread extends Thread {
    	
    	private JFrame parent;
    	
    	public CipherThread(JFrame parent){
    		this.parent = parent ;    	
    	}
    	
    	public void run() {
    		SimuleThread thread = new SimuleThread(parent);
    		thread.start();
    		reinitResultFrame();
    		addItemToPopupMenu();
    		resultFrame.setTitle("Résultat du Chiffrement");
    		try {
    			currentMode = Window.CIPHER;
    			binaryButton.setSelected(true);
    			if(Binary.isBinaryRepresentation(input.getText()))
    				output.setText(DES.encode(input.getText(), keyField.getText()));
    			else
    				output.setText(DES.encode(DESUtil.messageToDESBinaryString(input.getText()), keyField.getText()));
    			
    		} catch (Exception e) {
    			thread.interrupt();
    			simule.setVisible(false);
    			JOptionPane.showMessageDialog(parent, e.getMessage(), "Erreur", JOptionPane.WARNING_MESSAGE);
    		}
    		currentText = output;
    		thread.interrupt();
    		simule.setVisible(false);
    		resultFrame.setVisible(true);
    	}
    }
    
    class DecipherThread extends Thread {
    	
    	private JFrame parent;
    	
    	public DecipherThread(JFrame parent){
    		this.parent = parent ;
    	}
    	
    	public void run() {
    		SimuleThread thread = new SimuleThread(parent);
    		thread.start();
    		reinitResultFrame();
    		addItemToPopupMenu();
    		resultFrame.setTitle("Résultat du Déchiffrement");
    		try {
    			currentMode = Window.DECIPHER;
    			if(Binary.isBinaryRepresentation(input.getText()))
    				output.setText(DES.decode(input.getText(), keyField.getText()));
    			else
    				output.setText(DES.decode(DESUtil.messageToDESBinaryString(input.getText()), keyField.getText()));
    			asciiButton.setSelected(true);
    			output.setText(DESUtil.binaryStringToReadableMessage(output.getText()));
    		} catch (Exception e) {
    			thread.interrupt();
    			simule.setVisible(false);
    			JOptionPane.showMessageDialog(parent, e.getMessage(), "Erreur", JOptionPane.WARNING_MESSAGE);
    		}
    		currentText = output;
    		thread.interrupt();
			simule.setVisible(false);
    		resultFrame.setVisible(true);    		
    	}
    }
}
