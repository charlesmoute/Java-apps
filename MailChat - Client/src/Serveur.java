import java.rmi.*;
import java.util.*;

public interface Serveur extends Remote {
	
	public String getIdentifiantInternaute(String urlInternaute) throws RemoteException ;
	public String getHostInternaute(String urlInternaute) throws RemoteException ;
	public String getPortInternaute(String urlInternaute) throws RemoteException ;
	public boolean internauteIsConnected(String identifiant) throws RemoteException ;
	
	public void connect(String identifiant,String password) throws RemoteException ;
	public void register(String identifiant,String host,String password)throws RemoteException;
	public void disconnect(String identifiant)throws RemoteException ;
	public Vector<String> listInternaute() throws RemoteException;
	
	
}
