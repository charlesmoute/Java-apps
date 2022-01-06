import java.io.*;
import java.rmi.*;
import java.util.*;
import java.rmi.server.*;
import javax.swing.*;



public class ServeurImpl extends UnicastRemoteObject implements Serveur {

	private static final long serialVersionUID = 5263622747288619464L;
	private final String dataFile = "datafile.dat";
	private Vector<String> internautes = new Vector<String>();   
	private JTextArea output;	
	
	private String refreshList() {
		
		String out = "\n <-------- Internautes actuellement en ligne -------->\n\n";
		int i = internautes.size()-1;
		while(i>=0){
			String identifiant,host,port;
			int pos;
			String url = (String)internautes.elementAt(i);
			pos = url.lastIndexOf('/');
			identifiant = url.substring(pos+1);
			url = url.substring(0,pos);
			pos = url.lastIndexOf('/');
			url = url.substring(pos+1);
			pos = url.indexOf(':');
			host = url.substring(0,pos);
			port = url.substring(pos+1);
			out += "    Identifiant Internaute           --> "+identifiant+"\n"+
				   "    Machine hôte Interanaute         --> "+host+"\n"+
				   "    Port de Communication Internaute --> "+port+"\n"+
				   " ----------------------- o -----------------------   \n";
			i--;
		}
		out += "\n <-------- Internautes actuellement en ligne -------->\n";
		return out;
	}
		
	class ThreadOnline extends Thread {
		public void run(){
			try{
				while(!interrupted()&&true){
					String out = refreshList();
					synchronized(output) {
						if(output!=null) output.setText(out);
						else System.out.println(out);
					}				
					try { Thread.sleep(1000); } 
					catch (InterruptedException e) {e.printStackTrace();}
			    }
			}catch(Exception err){/*Un demande d'interruption est d'arriver quand le thread dormait */}
		}
	}
	
	private int getNewPort() throws Exception{
		int port ;
		String portDefault = "1100";
		Scanner lecture = new Scanner(new FileInputStream(dataFile));
		while(lecture.hasNext()){
			portDefault = this.getPortInternaute(lecture.nextLine());
		}
		try{
		port = Integer.parseInt(portDefault)+1;
		return port ;
		}catch(NumberFormatException e){
			throw new Exception("Erreur: Aucun Port n'a ete attribue");
		}
		
	}
	
	private void appendDataInDataFile(String data) throws Exception {		
		if(isCorrectData(data))
		try{
			Vector<String> content= new Vector<String>();		
			Scanner lecture = new Scanner(new FileInputStream(dataFile));		
			while(lecture.hasNext()) content.add((String)lecture.nextLine());		
			lecture.close();
			
			PrintWriter ecrire = new PrintWriter(dataFile);		
			int i = content.size()-1;		
			while(i>=0){			
				ecrire.println((String)content.elementAt(i));			
				i--;			
			}
			ecrire.println(data);
			ecrire.close();		
			
		}catch(FileNotFoundException e) {
			throw new Exception("Le serveur n'a pas pu trouver le fichier "+dataFile);
		}
	}
		
	
	private boolean isCorrectData(String data){
		if(data==null) return false ;
		boolean correct = false ;
		String str = data ;
		int begin,end;
		
		begin = str.indexOf(':',0);
		end = str.indexOf(';',0);		
		correct= (begin!=-1 && end!=-1);		
		
		if(correct){			
			str = str.substring(end+1);			
			begin = str.indexOf(':',0);
			end = str.indexOf(';',0);
			correct = (correct && (begin!=-1 && end!=-1));
			if(correct){				
				str = str.substring(end+1);
				begin = str.indexOf(':',0);
				end = str.indexOf(';',0);
				correct = (correct && (begin!=-1 && end!=-1));
				if(correct){					
					str = str.substring(end+1);
					begin = str.indexOf(':',0);
					end = str.indexOf('.',0);
					correct = (correct && (begin!=-1 && end!=-1));			
				}
			}
		}
		return correct;
	}
	

	synchronized public void connect(String identifiant, String password) throws RemoteException {
		
		try{
			
			Scanner lecture = new Scanner(new FileInputStream(dataFile));
			boolean connectionAccepted = false;
			//String urlInternaute="rmi://" ;
			String urlInternaute="" ;
			while(lecture.hasNext()){
				String data = lecture.nextLine();
				boolean bool,b;
				bool = (this.getIdentifiantInternaute(data).compareTo(identifiant)==0);
				b =(this.getPasswordInternaute(data).compareTo(password)==0);
				if(bool&&b){
					/*urlInternaute+=this.getHostInternaute(data)+":"+this.getPortInternaute(data)+"/"+
								   this.getIdentifiantInternaute(data);*/
					urlInternaute=this.getHostInternaute(data)+":"+this.getPortInternaute(data)+"/"+
					   this.getIdentifiantInternaute(data);
					connectionAccepted=true; 
					break;
				}
			}
			lecture.close();
			if(connectionAccepted) {
				if(internautes.indexOf((String)urlInternaute)==-1)
					internautes.addElement((String)urlInternaute);
				//return internautes;
			}else throw new RemoteException("Identifiant incorrecte ou mot de passe.\n " +
										  "Enregistrez vous ou verifiez les entrees.");
		}catch(FileNotFoundException e){
			throw new RemoteException("File datafile.dat not found");			
		}		
	}

	synchronized public void disconnect(String identifiant) throws RemoteException {
	
		synchronized (internautes) {
			int i = internautes.size()-1;
			while(i>=0){
				String id = (String) internautes.elementAt(i);
				id = id.substring(id.indexOf('/')+1,id.length());
				if(id.compareTo(identifiant)==0){
					internautes.remove(i);					
					break;
				}
				i--;
			}
		}
		synchronized(output) {
			output.setText(refreshList());
		}
		
	}

	public Vector<String> listInternaute() throws RemoteException {
		return internautes;
	}
	
	public ServeurImpl(JTextArea outputServer) throws RemoteException {
		this.output = outputServer;		
		new ThreadOnline().start();
	}



	public String getIdentifiantInternaute(String urlInternaute) throws RemoteException {
		
		if(!isCorrectData(urlInternaute)) return null;
		String identifiant = null ;		
		if(urlInternaute!=null){
			int begin = urlInternaute.indexOf(':',0);
			int end = urlInternaute.indexOf(';',0);
			if(begin!=-1&&end!=-1) identifiant = urlInternaute.substring(begin+1,end);
		}
		return identifiant;
	}



	public String getHostInternaute(String urlInternaute) throws RemoteException {
       //Identifiant:valeur;Host:valeur;Port:valeur;Password:valeur.
		if(!isCorrectData(urlInternaute)) return null;
		String host = null ;
		String str =urlInternaute;
		if(urlInternaute!=null){
			int begin = str.indexOf(':',0);
			int end = str.indexOf(';',0);
			if(begin!=-1&&end!=-1){
				str = str.substring(end+1);				
				begin = str.indexOf(':',0);
				end = str.indexOf(';',0);
				if(begin!=-1&&end!=-1)host = str.substring(begin+1,end);
			}
		}
		return host;
	}



	public String getPortInternaute(String urlInternaute) throws RemoteException {
		if(!isCorrectData(urlInternaute)) return null;
		String port = null;
		String str = urlInternaute;
		if(urlInternaute!=null){
			int begin = str.indexOf(':',0);
			int end = str.indexOf(';',0);
			if(begin!=-1&&end!=-1){
				str = str.substring(end+1);				
				begin = str.indexOf(':',0);
				end = str.indexOf(';',0);				
				if(begin!=-1&&end!=-1) {
					str = str.substring(end+1);				
					begin = str.indexOf(':',0);
					end = str.indexOf(';',0);
					if(begin!=-1&&end!=-1) port = str.substring(begin+1,end);
				}
			}
		}
		return port;
	}
	
	private String getPasswordInternaute(String urlInternaute) {
		if(!isCorrectData(urlInternaute)) return null;
		String password = null;
		String str = urlInternaute;
		if(str!=null){
			int begin = str.indexOf(':',0);
			int end = str.indexOf(';',0);
			if(begin!=-1&&end!=-1){
				str = str.substring(end+1);				
				begin = str.indexOf(':',0);
				end = str.indexOf(';',0);				
				if(begin!=-1&&end!=-1) {
					str = str.substring(end+1);				
					begin = str.indexOf(':',0);
					end = str.indexOf(';',0);
					if(begin!=-1&&end!=-1) {
						str = str.substring(end+1);				
						begin = str.indexOf(':',0);
						end = str.indexOf('.',0);
						password = str.substring(begin+1,end);
					}
				}
			}
		}
		return password;
	}
	
	private boolean isAlreadyRegister(String data) throws Exception {
		
		boolean register = false ;
		Scanner lecture = new Scanner(new FileInputStream(dataFile));
		while(lecture.hasNext()){
			String ligne = lecture.nextLine();
			if(this.getIdentifiantInternaute(data).compareTo(this.getIdentifiantInternaute(ligne))==0)
				throw new Exception("Il existe deja un internaute avec ce login.");
			if(ligne.compareTo(data)==0) {register=true; break;}
		}
		return register;
	}
	
	//synchronized public void register(String identifiant, String host, String port, String password)throws RemoteException {

	public void register(String identifiant,String host,String password)throws RemoteException {		
		
		try {
			
			String data = "Identifiant:"+identifiant+";Host:"+host+";Port:"+this.getNewPort()+";Password:"+password+".";
			if(!isAlreadyRegister(data)) {
				appendDataInDataFile(data);				
			}else	throw new RemoteException("Utilisateur deja enregistre");
		} catch (Exception e) {
			throw new RemoteException("Erreur lors de l'enregistre de l'Internaute --> "+e.getMessage());
			//e.printStackTrace();
		}	
		
	}

	public boolean internauteIsConnected(String identifiant) throws RemoteException {
		if(identifiant==null) return false;
		synchronized(internautes){
			int i = internautes.size()-1;
			while(i>=0){
				String internaute = (String) internautes.elementAt(i);
				internaute = internaute.substring(internaute.indexOf('/')+1);
				if(internaute.compareTo(identifiant)==0){
					return true;
				}
				i--;
			}
		}
		return false;
	}	
	
}
