package component.model.base;

import helper.*;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;


/**
 * @author Charles&Marsien
 * <p>
 * 	Server represente un serveur qu'il soit un serveur gerant ses connexions par
 *  multi-threading ou par des canaux.
 * </p>
 */
public abstract class Server {

	/**
	 * Port du serveur.
	 */
	protected int port = Util.getPort();
		
	/**
	 * <p>
	 * 	 Le nombre de connexion que le serveur peut gerer simultanement.
	 *   La valeur zero represente une infinite de connexion.
	 * </p>
	 */
	protected int threadNumber = Util.getThreadNumber();
	
	/**
	 * <p>
	 * 	 Represente les traces d'execution du serveur. Les requetes qu'il recoit
	 *   et les reponses qu'il envoie.
	 * </p>
	 */
	protected String trace = "";
	
	/**
	 * @return Le numero de port sur lequel le serveur tourne.
	 */
	public int getPort() { return this.port; }
	
	/**
	 * @return Le nombre de connexion pouvant coexister.
	 */
	public int getThreafNumber() {return this.threadNumber; }
	
	/**
	 * @return La trace des activites du serveur.
	 */
	public String getTrace() {return this.trace; }
	
	/**
	 * <p>Dit si le serveur est en cours d'execution ou pas</p>
	 */
	protected boolean isRunning = false ;
	
	/**
	 * @return True si le serveur est en cours d'execution et non dans le cas contraire.
	 */
	public boolean isRunning() { return this.isRunning; }
	
	/**
	 * <p>Recharge les configurations depuis le fichier config</p>
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void reloadConfiguration() throws FileNotFoundException, IOException {
		Util.initializeConfiguration();
		this.port = Util.getPort();
		this.threadNumber = Util.getThreadNumber();		
	}
	
	/**
	 * Arrete le serveur.
	 */
	public abstract void stop() ;
	
	/**
	 * Lance le serveur.
	 */
	public abstract void start() ;
	
	/**
	 * <p>Detruit une connexion</p>
	 * @param indexConnection Index de la connexion a detruire.
	 */
	public abstract void kill(int indexConnection);
	
	/**
	 * <p>Retourne le nombre de connexion actuel du serveur.</p>
	 * @return Nombre de connexion courante.
	 */
	public abstract int connectionCount() ;
	
	/**
	 * @param indexConnection Index d'une connexion existante.
	 * @return True si cette connection est encore active, False dans le cas contraire.
	 */
	public abstract boolean isConnected(int indexConnection) ;
	
	/**
	 * <p>Nettoie le cache</p>
	 */
	public static void cleanCache() {
		File f = new File(Configuration.CACHE_DIRECTORY);
		if(f.exists()&& f.isDirectory())Util.delete(f);
		f.mkdir();
	}
	
	/**
	 * <p>Nettoie le dossier de log</p>
	 */
	public static void cleanLog() {
		File f = new File(Configuration.LOG_DIRECTORY);
		if(f.exists()&& f.isDirectory())Util.delete(f);
		f.mkdir();
	}
	
	/**
	 * @return Sous Forme de JDialog le contenu du dossier des fichiers de log
	 */
	public static JDialog listLogFiles(JFrame parent) {
		
		String name = Util.logFolder+"Log " ;
		String date = Util.today() ;
		int pos = date.lastIndexOf(' ');
		if(pos!=-1){
			pos = date.substring(0, pos).lastIndexOf(' ');
			if(pos!=-1){
				date = date.substring(0, pos);
			}
		}
		if(pos==-1) date = "";
		name+=date+".txt";
		File file = new File(name);
		if(!file.exists()) try {file.createNewFile();} catch (IOException e) {}
		
		File directory = new File(Util.logFolder);
		String [] list = directory.list(); 
		final JTree tree = new JTree(list);
		tree.setRootVisible(false);	
		tree.setFont(new Font(Font.DIALOG_INPUT,Font.PLAIN,14));
		JDialog frame = new JDialog(parent);
		frame.setTitle("Log Files");		
		frame.add(new JScrollPane(tree,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),BorderLayout.WEST);
		final JTextArea text = new JTextArea();
		text.setFont(new Font(Font.DIALOG_INPUT,Font.PLAIN,14));
		text.setEditable(false);
		text.setBorder(BorderFactory.createEtchedBorder());
		JScrollPane panel = new JScrollPane(text) ;
		panel.setBorder(BorderFactory.createEtchedBorder());
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(250, 100,800, 450);
		final JPopupMenu menu = new JPopupMenu();
		JMenuItem item = new JMenuItem("Refresh",UResource.createIcon("reset-ico.png","resource"));
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				if(tree.getSelectionPath()!=null){
					text.setText("");
					String name = tree.getSelectionPath().getLastPathComponent().toString();
					try {
						Scanner reader = new Scanner(new FileInputStream(new File(Util.logFolder+name)));
						while(reader.hasNextLine()) text.append(reader.nextLine()+"\n");
						reader.close();
					} catch (FileNotFoundException e) {	text.setText("Fichier non trouve"); e.printStackTrace(); }					
				}
			}
		});
		menu.add(item);
		text.setComponentPopupMenu(menu);
		tree.addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent evt){
				if(tree.getSelectionPath()!=null){
					text.setText("");
					String name = tree.getSelectionPath().getLastPathComponent().toString();
					try {
						Scanner reader = new Scanner(new FileInputStream(new File(Util.logFolder+name)));
						while(reader.hasNextLine()) text.append(reader.nextLine()+"\n");
						reader.close();
					} catch (FileNotFoundException e) {	text.setText("Fichier non trouve"); e.printStackTrace(); }					
				}
			}
		});
		frame.setResizable(false);
		return frame;
	}	
}
