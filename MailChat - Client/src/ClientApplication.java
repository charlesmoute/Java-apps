
import java.io.*;
import java.net.*;
import java.net.UnknownHostException;
import java.awt.*;
import java.rmi.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.naming.*;

import java.awt.event.*;

public class ClientApplication {

	//private static int portDefault =9632 ;
	
	private DatagramSocket client ; // représentent le client qui attend les demandes
	//de connexions des autres clients et met à jour les informations nécessaires 
	//au multi chat
	//ou private Socket client ;//dans le cas de TCP
	
	private Vector<ConnectionService> connexions = new Vector<ConnectionService>(); // vector de DatagramSocket pour les différentes connexions dans UDP
	
	
	
	/* ou Dans TCP 
	 * 
	 * private Vector entrees = new Vector(); //vecteur de BufferedReader pour les différentes entrées dans TCP
	 * private Vector sorties = new Vector(); //vecteur de PrintWriter pour les différentes sorties en cours dans RCP
	 * private Vector enConnexions = new Vactor(); // vecteur de boolean pour les connexions en cours
	 * private Vectro client // pour les clients   
	 * */

	private String nameClient = null;
	private String motdepasse = null;	
	private String hostNameServer = "127.0.0.1"; //variable à demandé.
	private final  String nameServer = "appliServer";
	private Serveur server ;
	private Vector<String> internautes = new Vector<String>(); //internautes des internautes
	private final static int  taille = 1024;
	private static byte  buffer[] = new byte[taille];
	
	
	/*
	 * pour Interface application
	 * 
	 * */	
	
	private JList  listInternautes = new JList(); 	
	private RegisterFrame register = new RegisterFrame();
	private ConnectionFrame connector = new ConnectionFrame();
	private FrontFrame frontFrame = new FrontFrame(); 
	private final Color COULEUR = new Color(216,228,241);
	private String [] list ; 

	
	private void  initListInternautes() {
		 
		int i = internautes.size()-1;
		list = new String[internautes.size()];
		try {
			while(i>=0){		
				String locuteur;
				locuteur = getIdentifiantInternaute((String)internautes.elementAt(i));
				if(locuteur.compareTo(nameClient)!=0 && server.internauteIsConnected(locuteur)) list[i]= locuteur;				
				i--;
			}
		} catch (RemoteException e) {
			System.out.println("Erreur lors de l'initialisation de la nouvelle liste");
		}
		listInternautes.setModel(new AbstractListModel(){		
			private static final long serialVersionUID = 1L;
			
			public int getSize() { return list.length; }
            public Object getElementAt(int i) { return list[i]; }
		});		
	}
	
	
	private void buildServer() {
		try {
			Context context = new InitialContext();
			server = (Serveur)context.lookup("rmi://"+hostNameServer+"/"+nameServer);//port par défaut 1099
		} catch (NamingException e) {
			JOptionPane.showMessageDialog(null,"Erreur Connection au Serveur","Erreur",
										JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			return;
		}
	}	
	
	
	private boolean isCorrectURL(String url) {
		
		if(url==null) return false;
		boolean correct = false ;
		int begin,end ;
		String str = url;		
		begin = str.indexOf(':');
		end = str.indexOf('/');
		if( (end-begin)>1 && begin>0) {
		 correct = str.substring(end+1).length()!=0 ;
		}		
		return correct ;
	}
	
	private String getIdentifiantInternaute(String url) {
		
		if(!isCorrectURL(url)) return null;
		String identifiant=null;
		int pos = url.indexOf('/');
		identifiant = url.substring(pos+1);
		return identifiant;
	}
	
	private String getHostNameInternaute(String url) {
		
		if(!isCorrectURL(url)) return null;						
		return url.substring(0,url.indexOf(':'));		
	}
	
	private String getPortInternaute(String url) {
		
		if(!isCorrectURL(url)) return null;						
		String str  = url.substring(url.indexOf(':')+1); 
		return str.substring(0,str.indexOf('/'));		
	}
	
	private boolean isCorrectPassword(String password){
		  if(password==null) return false;
		  if(password.indexOf('/')!=-1) return false;
		  if(password.indexOf(':')!=-1) return false;
		  if(password.indexOf(';')!=-1) return false;
		  if(password.indexOf('.')!=-1) return false;
		  return true;
	}
		
	private boolean isCorrectIdentifiant(String identifiant){
			if(identifiant==null) return false;
			if(identifiant.indexOf('/')!=-1) return false;
			if(identifiant.indexOf(':')!=-1) return false;
			if(identifiant.indexOf(';')!=-1) return false;
			if(identifiant.indexOf(' ')!=-1) return false ;
			if(identifiant.indexOf('.')!=-1) return false ;
			return true;
	}
	
	
	private boolean isCorrectHostName(String host){
		if(host==null) return false;
		if(host.indexOf('/')!=-1) return false;
		if(host.indexOf(':')!=-1) return false;
		if(host.indexOf(';')!=-1) return false;
		if(host.indexOf(' ')!=-1) return false ;
		
		int pos ;
		String str = host,ch;
		
		pos = str.indexOf('.');
		if(pos!=-1){
			ch = str.substring(0,pos);			
			if(ch.length()<=3 && ch.length()>=1){
				try{
					Integer.parseInt(ch);
					str = str.substring(pos+1);					
					pos = str.indexOf('.');					
					if(pos!=-1){
						ch = str.substring(0,pos);						
						if(ch.length()<=3 && ch.length()>=1){								
							try{
								Integer.parseInt(ch);
								str = str.substring(pos+1);								
								pos = str.indexOf('.');								
								if(pos!=-1){
									ch = str.substring(0,pos);									
									if(ch.length()<=3 && ch.length()>=1){
										try{
											Integer.parseInt(ch);
											str = str.substring(pos+1);											
											if(str.length()<=3 && str.length()>=1){												
												try{
													Integer.parseInt(str);													
													return true;
												}catch(NumberFormatException e){
													return false ;
												}
												
											}else return false ;
											
										}catch(NumberFormatException e){
											return false;
										}
										
									}else return false;
								}else return false;
							}catch(NumberFormatException e){
								return false;
							}
							
						}else return false;
						
					}else return false;
				}catch (NumberFormatException e){
					return false ;
				}
				
			}else return false ;
		}else {
			try{
				Integer.parseInt(host);													
				return false;
			}catch(NumberFormatException e){
				return true ;
			}
		}
	}	
	
	//service de rafraichissement de la internautese des connectés toutes les 1secondes.
	
	class  RefreshService extends Thread {
		
		public void run() {
			try{
				while(!interrupted()&&true){
					synchronized(internautes){ 
						try {
							internautes = server.listInternaute();
							initListInternautes();						
							Thread.sleep(1000);						 
						} catch (RemoteException e) {
							System.out.println("Erreur rafraichissement des présents sur le réseau");
						} catch (InterruptedException e) {
							System.out.println("Erreur endormissement du Thread RefreshService");
						}
					}
				}
			}catch(Exception err){/*pour arréter une Exception de typeInterruptedException */}
		}
		
	}
	
	//service de tchat pour un client 
	class ChatService extends Thread {
				
		public ChatService() {
			
			try {
				int port;
				synchronized(internautes){
					port = Integer.parseInt(getPortInternaute((String)internautes.elementAt(searchPositionInternaute(nameClient))));
				}
				client = new DatagramSocket(port);		 
				System.out.println("--- Service en ecoute ---");
				
			} catch (SocketException e) {
				System.out.println("--- Service Interrompue ---");
				client = null ;
				System.out.println("--- Service Termine ---");
			} 
		}
				
		public void run() {			
			try {
				while(!interrupted()&&true){
					DatagramPacket paquet = new DatagramPacket(buffer,buffer.length);				
					client.receive(paquet);
					String messageReceived = new String(paquet.getData(),0,paquet.getLength());
					int pos = messageReceived.indexOf(':');
					if(pos!=-1){
						String locuteur = messageReceived.substring(0,pos);
						String message = messageReceived.substring(pos+1);						
						boolean isConnected = server.internauteIsConnected(locuteur);
						ConnectionService con = searchConnection(locuteur);
						
						if(isConnected){							
							if(con==null){
								con = new ConnectionService(locuteur);
								synchronized(connexions) {connexions.add((ConnectionService)con);}
								con.start();
							}
							con.putOutputMessage(locuteur,message);							
						}else{							
							if(con!=null){
								con.putOutputMessage(locuteur,"Votre interlocuteur n'est plus en ligne");
							}							
						}
						
						/*pos = searchPositionInternaute(locuteur);
						if(pos==-1) {
							ConnectionService con = searchConnection(locuteur);
							if(con!=null) connexions.remove(connexions.indexOf((ConnectionService)con));
							System.out.println(locuteur+"n'est plus sur le reseau");
						}
						else{
							ConnectionService con = searchConnection(locuteur);							
							if(con!=null)con.putOutputMessage(locuteur,message);								
						}*/						
					}else System.out.println(messageReceived+" --> Message Errone doit etre de la forme locuteur:message");					
				}
			} catch (IOException e) {
				System.out.println("Erreur lors de la réception du message");
			}catch(Exception err){/*pour arréter une Exception de typeInterruptedException */}
		}
	}
	
	private ConnectionService searchConnection(String locuteur){
		if(locuteur==null) return null;
		ConnectionService conex = null ;		
		int i = connexions.size()-1;
		while(i>=0){
			ConnectionService  con = (ConnectionService)connexions.elementAt(i);
			if( (con.getLocuteur()).compareTo(locuteur)==0){
			    conex= con ;
				break;
			}		
			i--;
		}
		return conex ;
	}
	
	
	class ConnectionService extends Thread {
		private String locuteur = null;
		private Output output = null ;
		private DatagramSocket connexion = null;		
		private InetAddress serveur ;
		private int port;
		public String getLocuteur() { return this.locuteur; }
		
		public void putOutputMessage(String locuteur,String message){
			if(!output.isVisible()) output.setVisible(true);
			synchronized(output) {
				output.putMessage(locuteur,message);			
			}
		}
		
		
		public void run() {
			try{
				while(!interrupted()&&true){
					try {
						
						DatagramPacket paquetReceive = new DatagramPacket(new byte[taille],taille);
						connexion.receive(paquetReceive);					
						String messageReceive = new String(paquetReceive.getData(),0,paquetReceive.getLength());
						String message =null;					
						int pos = messageReceive.indexOf(':');
						if(pos!=-1&&messageReceive.substring(0,pos).compareTo(locuteur)==0)
						     message = messageReceive.substring(pos+1);					
						synchronized(output){
							if(message==null)
								output.putMessage(locuteur,"Message Incohérent");
							else output.putMessage(locuteur,message);
						}
						
					} catch (IOException e) {
						System.out.println("Echec de Réception de message ");;
					}
				}
			}catch(Exception err){/*pour arréter une Exception de typeInterruptedException */}
			
		}
		
		
		class Output extends JFrame {			
			
			private static final long serialVersionUID = -1786734557870150534L;
			private JTextArea out = new JTextArea("Hello "+nameClient+"!!\n",10,40);
			private JTextField message = new JTextField("",30);
			private JButton sendButton = new JButton("Send");
			
			private void putMessage(String locuteur,String message){
				out.append("<"+locuteur+"> "+message+"\n");
			}
			
			private void sendButtonAction(){
				try{
					
					
					if(server.internauteIsConnected(locuteur)){
						String sms ;
						sms = nameClient+":"+message.getText();
						/*Erreur ici*/
						DatagramPacket paquetSent = new DatagramPacket(sms.getBytes(),sms.length(),serveur,port);
						connexion.send(paquetSent);
						putMessage(nameClient,message.getText());
					}
					else putMessage(nameClient,"Votre Locuteur n'est plus en ligne");					
					
					message.setText("");
					
				}catch(Exception e){
					putMessage("Systeme",e.getMessage());
					return ;
				}
			}
			
			public void sendMesage(String message){
				this.message.setText(message);
				this.sendButtonAction();
			}
			
			public Output(String title){			
				this.setTitle(title);
				this.setBounds(250,250,450,350);
				this.setResizable(false);
				out.setForeground(Color.BLACK);
				out.setFont(new Font(Font.DIALOG_INPUT,Font.ITALIC,12));			
				out.setWrapStyleWord(true);
				out.setEditable(false);
				out.setPreferredSize(new Dimension(450,300));
				JPanel panel = new JPanel(new FlowLayout());
				panel.add(out);
				JScrollPane pan = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				this.getContentPane().add(pan);
				
				JPanel p = new JPanel(new FlowLayout());
				message.setPreferredSize(new Dimension(255,25));
				message.addKeyListener(new KeyAdapter(){
					public void keyPressed(KeyEvent e){
						if(e.getKeyCode()==KeyEvent.VK_ENTER) sendButtonAction () ;						
					}
				});
				p.add(message);
				sendButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
					   sendButtonAction () ;	
					}
				});
				sendButton.setPreferredSize(new Dimension(100,24));
				p.add(sendButton);
				this.getContentPane().add(p,BorderLayout.NORTH);
				this.addWindowListener(new WindowAdapter(){
					public void windowClosing(WindowEvent e ){
						try {
							if(!server.internauteIsConnected(locuteur)){
								ConnectionService con = (ConnectionService)searchConnection(locuteur);
								if(con!=null){
								  int pos = connexions.indexOf((ConnectionService) con);
								  if(pos!=-1) connexions.remove(pos);
								}
							}
						} catch (RemoteException e1) {
							System.out.println("Erreur lors de la vérification de la connectivité");
							e1.printStackTrace();
						}
					}
				});				
			}
		}
		
		public ConnectionService(String locuteur){			
			try {
				
				this.locuteur = locuteur;
				int pos = searchPositionInternaute(locuteur);
				String hostName = "127.0.0.1";				
				if(pos!=-1) hostName = getHostNameInternaute((String)internautes.elementAt(pos));
				serveur = InetAddress.getByName(hostName);
				port = Integer.parseInt(getPortInternaute( (String) internautes.elementAt(pos) ));
				output = new Output("MailChat- "+nameClient+"&"+locuteur);
				connexion = new DatagramSocket();
				 
			} catch (SocketException e) {
				System.out.println("Erreur lors de l'établissement de la connexion avec "+locuteur);
			} catch (UnknownHostException e) {
				System.out.println("Nom d'hote inconnu ");
			}catch(NumberFormatException e){
				System.out.println("Port incohérent ");
			}
			
		}		
		
	}
	
	 private int searchPositionInternaute(String identifiant) {
		 synchronized(internautes){
			
			 int i = internautes.size()-1;
			 while(i>=0){
				String locuteur = getIdentifiantInternaute((String)internautes.elementAt(i));
				if( (locuteur!=null) && (locuteur.compareTo(identifiant)==0)){
					return i;
				}
				i--;
			}
			return i;
			 
		 }
	}
	
	
	
	 class RegisterFrame extends JFrame {
		
		private static final long serialVersionUID = 5871789719946729609L;

		private JTextField identifiantText = new JTextField(),
						   hostNameText = new JTextField("localhost"),
						   passwordText = new JTextField();
		
		private JLabel identifiantLabel = new JLabel("Identifiant :"),
					   hostNameLabel =  new JLabel("Adresse IP :") ,
					   passwordLabel = new JLabel("Mot de Passe :");
		
		private JPanel i = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5)),
					   h = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5)),
					   p = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
		
		private JPanel pan = new JPanel(new BorderLayout()),
					   panel = new JPanel(new BorderLayout()); 
		
		public JButton registerButton= new JButton("S'enregistrer");		
		
		private void registerButtonAction() {
			nameClient = identifiantText.getText();
			String host = hostNameText.getText();
			motdepasse = passwordText.getText();
			System.out.println(nameClient+";"+host+";"+motdepasse);
			if(!isCorrectHostName(host)||!isCorrectIdentifiant(nameClient)||!isCorrectPassword(motdepasse))
				JOptionPane.showMessageDialog(this,"L'un des paramétres entrés n'est pas valide");
			else
			try {
				
				server.register(nameClient,host,motdepasse);				
				frontFrame.setSelectedTo(0,false);
				this.setVisible(false);
				this.emptyField();
				connector.putValueToField();
                connector.disableRegisterButton();
				frontFrame.setSelectedTo(1,true);				
				connector.setVisible(true);
				
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(this,"Une Erreur est survenue lors de l'enregistrement\n"
													+e.getMessage());
				e.printStackTrace();
				return;
			}
			
		}
		
				
		public void emptyField() {
			identifiantText.setText("") ;
			hostNameText.setText("localhost");
			passwordText.setText("");			
		}
		public void reinit() {
			emptyField();
			nameClient = null;
			motdepasse = null;
		}
		
		public void putValueToField(){
			identifiantText.setText(nameClient) ;
			hostNameText.setText("localhost");
			passwordText.setText(motdepasse);
		}
		
		public RegisterFrame() {
			super("Client MailChat - Register Frame");
			this.setLocation(200,150);
			hostNameLabel.setHorizontalAlignment(JLabel.LEFT);
			hostNameLabel.setPreferredSize(new Dimension(100,25));
			h.add(hostNameLabel,BorderLayout.WEST);
			
			hostNameText.setHorizontalAlignment(JTextField.LEFT);
			hostNameText.setPreferredSize(new Dimension(265,25));		
			hostNameText.setToolTipText("L'adresse IP de votre machine ou son nom sur le réseau");
			h.add(hostNameText);
			
			pan.add(h,BorderLayout.NORTH);
			pan.setBackground(COULEUR);
			
			identifiantLabel.setHorizontalAlignment(JLabel.LEFT);
			identifiantLabel.setPreferredSize(new Dimension(100,25));			
			i.add(identifiantLabel,BorderLayout.WEST);
			
			identifiantText.setHorizontalAlignment(JLabel.LEFT);
			identifiantText.setPreferredSize(new Dimension(265,25));
			identifiantText.setToolTipText("Votre future nameClient sur le réseau");
			identifiantText.setText(nameClient);
			i.add(identifiantText);
			
			pan.add(i,BorderLayout.SOUTH);
			this.getContentPane().add(pan,BorderLayout.NORTH);
			this.getContentPane().setBackground(COULEUR);
			
			passwordLabel.setHorizontalAlignment(JLabel.LEFT);
			passwordLabel.setPreferredSize(new Dimension(100,25));
			
			p.setBackground(COULEUR);
			p.add(passwordLabel,BorderLayout.WEST);
			
			passwordText.setHorizontalAlignment(JLabel.LEFT);
			passwordText.setPreferredSize(new Dimension(265,25));
			passwordText.setToolTipText("Le mot de passe qui vous permettra d'avoir accés au Réseaux");
			passwordText.setText(motdepasse);
			p.add(passwordText);
			
			panel.add(p,BorderLayout.NORTH);
			panel.setBackground(COULEUR);
			
			registerButton.setPreferredSize(new Dimension(100,24));
			registerButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					registerButtonAction();
				}
			});
			panel.add(registerButton,BorderLayout.SOUTH);
			this.getContentPane().add(panel,BorderLayout.SOUTH);
			this.addWindowListener(new WindowAdapter(){
				
				public void  windowClosing(WindowEvent e){
					frontFrame.unSelected ();
				}
			});
			
			this.pack();
		}
	}
	
	
	class ConnectionFrame extends JFrame {
	
		private static final long serialVersionUID = -8712973881025768654L;

		private JTextField identifiantText = new JTextField(nameClient),
		   					passwordText = new  JPasswordField(motdepasse);//JTextField(motdepasse);

		private JLabel identifiantLabel = new JLabel("Identifiant :"),
			   		   passwordLabel = new JLabel("Mot de Passe :");
		
		private JPanel i = new JPanel(new FlowLayout(FlowLayout.LEFT,5,10)),
			   p = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5)),
			   panel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,5));
		
		private JPanel pan = new JPanel(new BorderLayout()); 
		
		private JButton connectButton= new JButton("connect"),
					   registerButton = new JButton("S'enregistrer");
				
		public void emptyField() {
			identifiantText.setText("") ;			
			passwordText.setText("");			
		}
		public void reinit() {
			emptyField();
			nameClient = null;
			motdepasse = null;
		}
		
		public void putValueToField(){
			identifiantText.setText(nameClient) ;			
			passwordText.setText(motdepasse);
		}
		
		public void disableRegisterButton() { registerButton.setEnabled(false);}
		
		private void connectButtonAction() {
			nameClient = identifiantText.getText();			
			motdepasse = passwordText.getText();
			if(!isCorrectIdentifiant(nameClient)||!isCorrectPassword(motdepasse))
				JOptionPane.showMessageDialog(this,"L'un des paramétres entrés n'est pas valide");
			else
			try {				
				server.connect(nameClient,motdepasse);
				new RefreshService().start();				
				frontFrame.setSelectedTo(1,false);				
				this.setVisible(false);
				this.emptyField();
				frontFrame.setVisible(false);
				new ApplicationFrame().setVisible(true);
				new ChatService().start();
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(this,"Une Erreur est survenue lors de la connection\n"
													+e.getMessage());
				e.printStackTrace();
				return;
			}
			
		}
		
		private void registerButtonAction(){
			frontFrame.setSelectedTo(1,false);
			this.setVisible(false);
			this.emptyField();
			register.putValueToField();
			frontFrame.setSelectedTo(0,true);			
			register.setVisible(true);		
		}
		
		
				
		public ConnectionFrame() {
			super("Client MailChat - Connection Frame");						
			this.setLocation(200,150);
			this.addWindowListener(new WindowAdapter(){				
				public void  windowClosing(WindowEvent e){
					frontFrame.unSelected ();
				}
			});
			
			identifiantLabel.setHorizontalAlignment(JLabel.LEFT);
			identifiantLabel.setPreferredSize(new Dimension(100,25));
			i.add(identifiantLabel,BorderLayout.WEST);
			
			identifiantText.setHorizontalAlignment(JLabel.LEFT);
			identifiantText.setPreferredSize(new Dimension(265,25));
			identifiantText.setToolTipText("Votre Login de connexion");
			identifiantText.setText(nameClient);
			i.add(identifiantText);
			
			pan.setBackground(COULEUR);
			pan.add(i,BorderLayout.NORTH);
			
			passwordLabel.setHorizontalAlignment(JLabel.LEFT);
			passwordLabel.setPreferredSize(new Dimension(100,25));
			
			p.setBackground(COULEUR);
			p.add(passwordLabel,BorderLayout.WEST);
			
			passwordText.setHorizontalAlignment(JLabel.LEFT);
			passwordText.setPreferredSize(new Dimension(265,25));
			passwordText.setToolTipText("Le mot de passe qui vous donne accés au réseau");
			passwordText.setText(motdepasse);
			p.add(passwordText);
			
			pan.add(p,BorderLayout.SOUTH);
			this.getContentPane().add(pan,BorderLayout.NORTH);
			
			connectButton.setPreferredSize(new Dimension(100,24));
			connectButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					connectButtonAction();
				}
			});			
			
			panel.setBackground(COULEUR);
			panel.add(connectButton);
			
			registerButton.setPreferredSize(new Dimension(110,24));
			registerButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					registerButtonAction();
				}
			});
			panel.add(registerButton);
			
			this.getContentPane().add(panel,BorderLayout.SOUTH);
			this.pack();
		}
	}
	
	
	class FrontFrame extends JFrame implements ActionListener {

		
		private static final long serialVersionUID = -6288846393278024364L;
		
		private JRadioButton[] radioButton = new JRadioButton [2];		
		private boolean unSelected = false ;		
		public FrontFrame(){
			this.setTitle("Client Mail Chat - Front Frame");			
			this.setBounds(50,50,300,75);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setResizable(false);
			
			Container contentPane = this.getContentPane();
			contentPane.setBackground(COULEUR);
			contentPane.setLayout(new FlowLayout());
						
			radioButton[0] = new JRadioButton("S'enregistrer");
			radioButton[0].addActionListener(this);
			radioButton[0].setSelected(false);		
			
			radioButton[1] = new JRadioButton("Se connecter");
			radioButton[1].addActionListener(this);
			radioButton[1].setSelected(false);
			
			int i;
			for(i=0;i<radioButton.length;i++) contentPane.add(radioButton[i]);						
		}
		
		public void actionPerformed(ActionEvent e) {		
		
			if(!unSelected) {
			   if(radioButton[0].isSelected()){
				   radioButton[1].setSelected(false);
				   connector.setVisible(false);
				   register.reinit();
				   register.setVisible(true);			 
			   }else if (radioButton[1].isSelected()){
				   radioButton[0].setSelected(false);
				   register.setVisible(false);
				   connector.reinit();
				   connector.setVisible(true);			   
			   }
			}
			
		}		
		
		public void setSelectedTo(int i,boolean bool) {
			if(i==0 || i==1) radioButton[i].setSelected(bool);
		}
		public void unSelected() {
			int i ;
			unSelected = true ;
			synchronized(radioButton) {
				
				for(i=0;i<radioButton.length;i++) 
					radioButton[i].setSelected(false);			
			}
			unSelected = false ;
		}
		
      }
	
	
	class ApplicationFrame extends JFrame {
	
		private static final long serialVersionUID = -6187693620904266519L;
		private JMenuBar barMenu ;
		private JMenu fichier,edition;
		private JMenuItem exit,help;
		
		private void disconnectAction() {
		           
			try {
				
				synchronized(server) {server.disconnect(nameClient);}				
				int i = connexions.size()-1;
				while(i>=0){
					( (ConnectionService)connexions.elementAt(i)).interrupt();
					connexions.remove(i);
					i--;
				}
				connexions = null;
				synchronized(internautes){
					i = internautes.size()-1;
					while(i>=0){
						internautes.remove(i);
						i--;
					}
					internautes = null;
				}
			} catch (RemoteException e) {
				System.out.println("Echec déconnexion");
				e.printStackTrace();
				return ;
			}
		}
		
		private void helpAction() {
			JOptionPane.showMessageDialog(null,"Mail Chat est une appli réalisé dans le cadre d'un tp de Mr J.M Nlong\n" +
											  "		                      Par                                       \n" +
											  "       Mouté Charles <--> 03x202           ","Information",
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
					System.exit(0);
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
		
		
		public ApplicationFrame() {
			
			this.getContentPane().setLayout(new BorderLayout());
			this.setTitle(nameClient+" MailChat");
			this.getContentPane().add(listInternautes);
			this.getContentPane().setBackground(COULEUR);
			this.setBounds(50,50,300,600);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			buildMenu();
			this.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					disconnectAction();
				}
			});			
		}
	
	}
	
	
	//public Test(String nameClient,String hostNameServer)
	public ClientApplication() {
		
		do{
			
		    hostNameServer =(String) JOptionPane.showInputDialog(null,"Donnez l'adresse IP du serveur Ou \n"+   
				"Son nom dans le réseau.","Client MailChat", JOptionPane.QUESTION_MESSAGE);
		}while (!isCorrectHostName(hostNameServer));
		//hostNameServer +="1100";
		buildServer();		
		listInternautes.addListSelectionListener(new ListSelectionListener(){

			public void valueChanged(ListSelectionEvent e) {
				
				String locuteur = (String)listInternautes.getSelectedValue();
				if(locuteur!=null){
					ConnectionService con = searchConnection(locuteur);							 
					if(con==null){
						con = new ConnectionService(locuteur);
						synchronized(connexions) {connexions.add((ConnectionService)con);}
						con.start();
					}
					con.output.setVisible(true);
				}
				
			}
			
		});		
		frontFrame.setVisible(true);		
	}
	
	public static void main(String[] args) {
		new ClientApplication();		

	}

}
