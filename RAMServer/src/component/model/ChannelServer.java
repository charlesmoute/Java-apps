package component.model;

import helper.*;

import java.io.*;
import java.net.*;
//import java.awt.*;
import java.util.*;
//import javax.swing.*;
//import java.awt.event.*;
import java.nio.channels.*;



import component.model.base.*;

/**
 * <p>Gere les connexions du serveur avec les cannaux.</p>
 * @author Charles&Marsien
 * @see component.model.base.Server
 */

public class ChannelServer extends Server {

	
	public static final String CHARSET = "ISO-8859-1";
	//public static final int SIZE_BUFFER = 2048;
	
	private ServerSocketChannel server = null;
	private ArrayList<SelectionKey> list = null;
	private ThreadServer myThread = null ;
	
	//private Charset charset = null ;
	//private CharsetDecoder decoder = null ;
	//private CharsetEncoder encoder = null ;
	//private ByteBuffer buffer = null ;

	public ChannelServer() throws Exception {
		try {
			this.reloadConfiguration();
			list = new ArrayList<SelectionKey>();
		} catch (Exception e) {
			Util.writeError("ChannelServer.ChannelServer() :: Erreur lors du lancement du serveur en mode canaux"); 
			throw new Exception("Erreur lors du lancement du Serveur en mode canaux."); 
		}
	}
	
	@Override
	public int connectionCount() { 
		if(list!=null) return list.size();
		return 0;
	}

	@Override
	public boolean isConnected(int indexConnection) {
		if(list!=null && indexConnection>=0 && indexConnection<list.size()){
			SelectionKey key = list.get(indexConnection);
			return key.channel().isOpen();
		}
		return false;
	}

	@Override
	public void kill(int indexConnection) {
		if(list!=null && indexConnection>=0 && indexConnection<list.size()){
			SelectionKey key = list.get(indexConnection);
			SocketChannel client = (SocketChannel) key.channel();
			key.cancel();			
			try {client.close();} catch (IOException e) {
				System.out.println("Client Ferme");
			}
			list.remove(indexConnection);
		}
	}

	@Override
	public void start() {
		myThread = new ThreadServer(); 
		myThread.start();
		this.isRunning = true ;
		System.out.println("Lancement du Serveur");
	}

	@Override
	public void stop() {
		try {
			for(int i= (list.size()-1);i>=0;i--){
				SelectionKey key = list.get(i); 
				SocketChannel client = (SocketChannel) key.channel();
				key.cancel();
				client.close();
				list.remove(i);
			}
			server.close();			
		} catch (IOException e) { 
			System.out.println(" Fermeture de la socket"); 
		}finally{
			server = null;		
			myThread.interrupt();
			myThread = null;
			isRunning = false ;			
			list = new ArrayList<SelectionKey>();
			System.out.println("Sortie");
		}
	}
	
	class ThreadServer extends Thread {
		
		public void run() {
			try{
								
				server = ServerSocketChannel.open();
				ServerSocket socketServer = server.socket();
				socketServer.bind(new InetSocketAddress(port));
				server.configureBlocking(false);
				Selector selector = Selector.open();
				server.register(selector, SelectionKey.OP_ACCEPT);
				
				while(!interrupted()){
					selector.select();
					Set<SelectionKey> keys = selector.selectedKeys();
					for (Iterator<SelectionKey> itr = keys.iterator(); itr.hasNext();) {
						SelectionKey key = (SelectionKey)itr.next();
						itr.remove();
						if (key.isAcceptable()) {
							SocketChannel client = server.accept();
							if(threadNumber==0 || (list.size()< threadNumber && list.size()>=0)){
								client.configureBlocking(false);
								SelectionKey clientKey = client.register(selector, SelectionKey.OP_READ);
								synchronized(list) {list.add(clientKey);}
							}
						}else if (key.isReadable()) {
							SocketChannel clientChannel = (SocketChannel)key.channel();
							Socket conex = clientChannel.socket();
							Message response = null;
							Message request = Util.buildMessage(clientChannel);
							if(conex!=null && conex.getInetAddress()!=null &&request!=null) request.setFrom(conex.getInetAddress().getHostAddress());
							Util.writeLog(request);
							System.out.println("Requete recue : "+request);
							if(request!=null){
								response = Util.processMessage(request);
								Util.writeLog(response);
								if(response!=null){
									Util.sendMessage(response, clientChannel);
									System.out.println("\nReponse Envoyee = "+response);
								}
							}
							synchronized (trace) {
								if(request!=null) trace+="Request   --> "+request;
								if(response!=null) trace+="\nResponse --> "+response+"\n";
							}
							key.cancel();
							clientChannel.close();
							synchronized(list) { list.remove(key); }
						}
					}
				}
			}catch(Exception e){				
				System.out.println(" Arret du Threat Serveur");
			}
			
		}
		
	}
	
	/*public static void main(String[] arg){
		
		try {
			Util.setPort(80);
			Util.setThreadNumber(2);
			final Server server = new ChannelServer();
			JFrame frame = new JFrame("Serveur Test");
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,15,20));
			frame.getContentPane().add(panel);
			final JButton b1 = new JButton("Start");
			final JButton b2 = new JButton("Stop");
			
			b1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					b1.setEnabled(false);
					b2.setEnabled(true);
					server.start();
				}
			});
			panel.add(b1);			
			
			b2.setEnabled(false);
			b2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					b1.setEnabled(true);
					b2.setEnabled(false);
					server.stop();
					
				}
			});
			panel.add(b2);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
			Server.listLogFiles(null).setVisible(true);
		} catch (Exception e) {	e.printStackTrace(); }
		
	}*/


}
