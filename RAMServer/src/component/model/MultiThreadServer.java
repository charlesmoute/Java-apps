package component.model;

import helper.Util;

import java.io.*;
import java.net.*;
import java.util.*;

import component.model.base.*;

/*import java.awt.*;
import javax.swing.*;
import java.awt.event.*;*/

public class MultiThreadServer extends Server {

	private ServerSocket server = null;
	private ArrayList<MultiThreadConnection> list = null ;
	private ThreadServer myThread = null;
	
	public MultiThreadServer() throws Exception{
		try {
			this.reloadConfiguration();
			list = new ArrayList<MultiThreadConnection>();
		} catch (Exception e) {
			Util.writeError("ChannelServer.ChannelServer() :: Erreur lors du lancement du serveur en mode multi-thread");
			throw new Exception("Erreur lors du lancement du Serveur.");	
		}		
	}
	
	@Override
	public int connectionCount() {
		if(list!=null) return list.size();
		return 0;
	}

	@Override
	public boolean isConnected(int indexConnection) {
		if(list!=null && indexConnection>=0 && indexConnection<list.size() ){
			return list.get(indexConnection).isAlive();
		}
		return false;
	}

	@Override
	public void kill(int indexConnection) {
		if(list!=null && indexConnection>=0 && indexConnection<list.size() ){
			if(isConnected(indexConnection)) list.get(indexConnection).interrupt();
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
		for(int i=(list.size()-1);i>=0;i--){
			list.get(i).interrupt();
			list.remove(i);
		}
		try {
			server.close();
		} catch (IOException e) { System.out.println(" Fermeture de la socket"); }
		server = null;
		myThread.interrupt();
		myThread = null;
		this.isRunning = false ;
		System.out.println("Sortie");
	}
	
	class ThreadServer extends Thread{
		
		public void run() {
			try {
				server = new ServerSocket(port);
				while(!interrupted()){				
					MultiThreadConnection conex = new MultiThreadConnection(server.accept());
					if(threadNumber==0 || list.size()<threadNumber){
						list.add(conex);
						conex.start();	
					}else{
						if(list.size()==threadNumber){
						
							int indexConnection = -1;
							for(int i=0;i<list.size();i++){
								if(!isConnected(i)){
									indexConnection = i ;
									break;
								}
							}
							
							if(indexConnection!=-1){
								kill(indexConnection);
								list.add(conex);
								conex.start();
							}
						}
					}
				}	
			} catch (Exception e) { 
				System.out.println(" Arret du Threat Serveur");							
			}
		}
		
	}
	
	class MultiThreadConnection  extends Thread {
		
		Socket conex = null;
		
		public MultiThreadConnection(Socket socket){
			conex = socket ;
		}
		
		public void run() {		
			try {
				Message response = null;
				Message request = Util.buildMessage(conex.getInputStream());				
				if(conex.getInetAddress()!=null &&request!=null) request.setFrom(conex.getInetAddress().getHostAddress());				
				Util.writeLog(request);
				OutputStream out = conex.getOutputStream();				
				System.out.println("\nRequete client recu \n"+request);											
				if(request!=null){
					response = Util.processMessage(request);
					Util.writeLog(response);
					if(response!=null){
						Util.sendMessage(response, out);
						System.out.println("\nReponse Envoyee = "+response);
					}
				}
				synchronized (trace) {
					if(request!=null) trace+="Request   --> "+request;
					if(response!=null) trace+="\nResponse --> "+response+"\n";
				}
				out.close();
				conex.close();
			} catch (Exception e) {e.printStackTrace();}
		}		
	}
	
	/*public static void main(String[] arg){
		
		try {
			Util.setPort(80);
			Util.setThreadNumber(2);
			final Server server = new MultiThreadServer();
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
