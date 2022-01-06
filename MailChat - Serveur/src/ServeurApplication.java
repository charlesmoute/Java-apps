import java.rmi.*;

import javax.naming.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServeurApplication  extends JFrame{

	private static final long serialVersionUID = -8010317839306554717L;
	
	private JTextArea outputServer = null ;
	
	private JMenuBar barMenu ;
	private JMenu fichier,edition;
	private JMenuItem exit,help;	  
	private void disconnectAction() {System.exit(0); }
	
	private void helpAction() {
		JOptionPane.showMessageDialog(null,"Mail Chat est une appli réalisé dans le cadre d'un tp de Mr J.M Nlong\n" +
										  "		                      Par                                       \n" +
										  "              Mouté Charles --> 03x202                    ","Information",
										  JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void buildMenu() {
	
		barMenu = new JMenuBar();
		this.setJMenuBar(barMenu);
		fichier = new JMenu("File");
		exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				disconnectAction();
			}
		});
		fichier.add(exit);
		barMenu.add(fichier);
		
		edition = new JMenu("Edition");
		help = new JMenuItem("Help");
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				helpAction();
			}
		});
		edition.add(help);
		barMenu.add(edition);
	}
			
	
	public ServeurApplication() {		
		this.setTitle("Serveur MailChat");
		this.setBounds(250,250,425,450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);		
		buildMenu();
		Container contentPane = this.getContentPane();
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		outputServer = new JTextArea("",25,50);
		outputServer.setWrapStyleWord(true);
		outputServer.setEditable(false);
		outputServer.setForeground(Color.BLACK);
		outputServer.setFont(new Font(Font.DIALOG_INPUT,Font.ITALIC,12));	
		panel.add(outputServer);
		contentPane.setLayout(new BorderLayout());
		contentPane.add(new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
							JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		try {
			
			System.out.println("<----------- Création du Serveur de messagerie      ----------->");
			ServeurImpl serveur = new ServeurImpl(outputServer); 
			
			System.out.println("<----------- Enregistrement au service d'annuaire   ----------->");
			Context myContext = new InitialContext();
			myContext.rebind("rmi:appliServer",serveur);			
			
			System.out.println("<----------- Attente des invocations des clients... ----------->");			
			
		} catch (RemoteException e) {
			System.out.println("<----------- Erreur Rebind Détectée ----------->");
			e.printStackTrace();
		} catch (NamingException e) {
			System.out.println("<----------- Erreur InitialContext Détectée ----------->");
			e.printStackTrace();
		} 
		
	}
	
	public static void main(String[] args) {		
		new ServeurApplication().setVisible(true);
	}

}
