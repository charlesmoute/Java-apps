package cm.ringo.dialer.component.model.base.connection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import com.jmethod.jdialup.JDException;
import com.jmethod.jdialup.JDNotification;
import com.jmethod.jdialup.JDState;
import com.jmethod.jdialup.JDial;

import cm.enterprise.software.component.model.base.SuperModel;
import cm.enterprise.software.exception.ProgramException;
import cm.ringo.dialer.component.interfaces.connection.OperatingSystem;
import cm.ringo.dialer.component.util.connection.Horloge;
import cm.ringo.dialer.component.view.DefaultView;
import cm.ringo.dialer.helper.connection.WindowsOperatingSystem;
import cm.ringo.dialer.helper.connection.constants.ConnectionConstants;
import cm.ringo.dialer.util.WebServiceInterf;

/**
 * 
 * Modele representant une connection. Dans notre cas une connexion
 * est liee a une seul entre de connexion. Entree de connexion, representant
 * les parametres de la connextion. Le nom de cette entree de connection 
 * est par defaut Ringo. 
 * 
 * @author Charles Moute
 * @version 1.0.1, 5/7/2010
 */
public class CopyOfConnectionModel extends SuperModel {

	/**
	 * Nom d'utilisateur utilise pour etablir la connexion
	 */
	
	private String username ;
	
	/**
	 * Mot de passe utilise pour authentifier l'utilisateur
	 */
	private String password ;
	private Boolean rememberMe = false;
	
	/**
	 * Representant l'etat de la connexion.
	 */
	private int state = ConnectionConstants.INACTIVE_STATE;
	
	
	//Variables pour l'etabissement de la  gestion a proprement parler de la connexion en mode asynchrone

	/**
	 * Tache de rafraichissement des parametres de l'entree de connexion.
	 * Pour l'etat connexion active
	 */
	private ActionListener refreshTask = new ActionListener(){
		
		public void actionPerformed(ActionEvent evt){
			EntryModel m = CopyOfConnectionModel.this.getSubModel(EntryModel.class);
			String entryName = m.getEntryName() ;
			//cote interface definir un ecouteur sur le changement d'etat.
			if(!system.entryIsActive(entryName)){
				//Change l'etat de la connexion vu qu'elle n'est plus active alors qu'elle est cense l'etre
				if(horloge.isRunning()) horloge.stop();
				CopyOfConnectionModel.this.setState(ConnectionConstants.INACTIVE_STATE, 0);
			}else{
				// Rafraicit les parametres de son entree.
				m.setBytesReceived(system.getBytesReceived(entryName));
				m.setBytesTransmitted(system.getBytesTransmitted(entryName));
				m.setConnectionSpeed(system.getConnectionSpeed(entryName));
				m.setTime(horloge.getTime());
			}
		}
	};
	
	/**
	 * Permet de prendre en compte le temps ecoulee depuis la connection de l'entree.
	 * Si l'etat change est l'entree devient active alors un processus
	 * de compte et de rafraichissement est lance. Une interface peut se mettre
	 * en tache d'ecoute des modifications des parametres. 
	 */
	private Horloge horloge ;
	
	/**
	 * L'instance du systeme sur lequel on se trouve
	 */
	
	private OperatingSystem system ;
		
	/**
	 * Message du processus de traitement de la connection
	 */
	private String message ;
	
	/**
	 * Ecouteur des notifications de connections sur windows. Pour l'etat dialing
	 */
	private int currentState; 
	private volatile Long sessionStart;
	private JDNotification connectionNotification = new JDNotification (){
				
		void logJDNotification(String entry, int state, int error){
			java.util.Date time = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
			try{
				
				if(state == 28){
					sessionStart = time.getTime(); // connected			
				}
				
				String message ;				
				if(state == 29 && sessionStart != null){
					
					Float durationInMinutes = (time.getTime() - sessionStart) / 60000F;
					message = String.format("%1$tD %1$tT, %2$s : [state %3$d, %4$s ], userInitiated[%6$d] --> [DurationInMinutes: %5$.0f]", time, entry, state, JDState.getStateString(state), durationInMinutes, -error);
					sessionStart = null;
				}else{
					message = error!=0?
							String.format("%1$tD %1$tT, %2$s : [state %3$d, %4$s ] --> [error %5$d, %6$s ]", time, entry, state, JDState.getStateString(state), error, JDial.getErrorMessage( error ))
							: String.format("%1$tD %1$tT, %2$s : [state %3$d, %4$s ]...", time, entry, state, JDState.getStateString(state), error, JDial.getErrorMessage( error ));
				
				}
				
				File file = new File("log.txt");
				FileOutputStream fileInput = new FileOutputStream(file, (file.length() < 1000000));
				PrintWriter out = new PrintWriter(fileInput);
				out.println(message);
				out.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Override
		public void notify(String entry, int state, int error) {
			
			CopyOfConnectionModel.this.currentState = state;
			/*
			 * Dans le pire des cas, i.e le message n'a pas le temps d'etre lu
			 * on va creer un thread qui aura une queue de message
			 * et les lira au fur a mesure, le pire des cas et  que on est pas 
			 * le temps de voire les messages defiles.
			 */
			
			logJDNotification(entry, state, error);		
			if(error > 0){
				String errorMessage = JDial.getErrorMessage(error);
				String errorMessageMultiLine = "";
				for(String str: errorMessage.split("\\.")){
					errorMessageMultiLine += "\n"+str.trim()+ ".";
				}
				DefaultView.showInformationBox("Error "+error+": "+errorMessageMultiLine);
			}
			
			//Ici tentative de connexion
			if(state==JDState.DIAL_TIMEOUT)	CopyOfConnectionModel.this.setMessage("Dial Time out") ;
			else CopyOfConnectionModel.this.setMessage(JDState.getStateString(state));
			
			final int status = state ;
			final int code = error;
			
			
			
			SwingUtilities.invokeLater(new Runnable(){
				
				//Ici tentative de connexion terminee, erreur ou connected
				
				public void run(){
					if(status==JDState.Ras_Connected || code!=0){	
						if(status==JDState.Ras_Connected){
							//Ici affecte l'etat de l'entre a active et lance la tache de rafraichissement
							CopyOfConnectionModel.this.setState(ConnectionConstants.ACTIVE_STATE, 0);
							horloge.start();
						}else{
							//ici affecte l'etat de l'entre a disconnect
							CopyOfConnectionModel.this.setState(ConnectionConstants.INACTIVE_STATE, 0);
						}
						CopyOfConnectionModel.this.setMessage("");
					}
				}
			});
			 
		}
		
	};
			
	/**
	 * Cree une instance vide de modele de connexion
	 * @throws ProgramException 
	 */
	public CopyOfConnectionModel() throws ProgramException{
		this(null,null);
	}
	
	/**
	 * Cree une instance de modele de connexion avec le nom utilisateur et mot de passe en parametre.
	 * @param utilisateur Nom utilisateur
	 * @param password Mot de passe pour l'authentification de l'utilisateur.
	 * @throws ProgramException 
	 */

	@SuppressWarnings("unchecked")
	public CopyOfConnectionModel(String utilisateur,String password) throws ProgramException{
		
		//definition de la classe du sous modele de connexion
		this.addClassOfModel(EntryModel.class);
		
		//Initialisation des parametres du modele 
		this.username = utilisateur;
		this.password = password ;
		this.message = "";
		
		try {
			//Initialisation par instanciation du sous modele du modele
			this.installSubModel(EntryModel.class, "Ringo");
		} catch (ProgramException e) {
			e.printStackTrace();
		}
		
		if(this.isWindowsOS()){
			try {
				system = new WindowsOperatingSystem(connectionNotification);
			} catch (JDException e) {
				throw new ProgramException(e.getMessage());
			}
		}
		
		//Ici initialisation de system pour un autre systeme
		
		if(!system.entryExists(this.getSubModel(EntryModel.class).getEntryName())){
			system.addEntry(this.getSubModel(EntryModel.class));
		}
		
		/*
		 * Horloge avec la tache de rafraichissement des parametres de connexion en parametre.
		 */
		horloge = new Horloge();
		horloge.addTask(refreshTask);
	}
	
	/**
	 * Permet de savoir si le systeme sur lequel on se trouve est windows
	 * @return true si le systeme sur lequel on se trouve est windows, false dans le cas contraire.
	 */
	private boolean isWindowsOS(){
		return (System.getProperty("os.name").indexOf("Windows")!=-1) ;
	}
	
	/**
	 * Affecte le parametre commme nom de l'utilisateur a utiliser pour l'authentification
	 * @param username Nom utilisateur
	 */
	public void setUsername(String username){
		String oldValue = this.username;
		this.username = username ;
		this.firePropertyChange("username", oldValue, username);
	}
	
	/**
	 * Permet d'obtenir le nom d'utilisateur associe a la connexion.
	 * @return Le nom de l'utilisateur de la connexion
	 */
	public String getUsername(){
		return this.username;
	}

	/**
	 * Affecte le parametre comme mot de passe de l'utilisateur de la connexion.
	 * @param password Mot de passe de l'utilisateur de la connexion.
	 */
	public void setPassword(String password){
		String oldValue = this.password ;
		this.password = password ;
		this.firePropertyChange("password", oldValue, password);
	}
	
	/**
	 * Permet d'obtenir le mot de passe permettant d'authentifier l'utilisateur de la connexion. 
	 * @return Le modt de passe de l'authentification de l'utilisateur de la connexion.
	 */
	public String getPassword(){
		return this.password ;
	}
	
	/**
	 * Remember my connexion params 
	 */	
	public void setRememberMe(boolean rememberMe) {
		boolean oldRememberMe = this.rememberMe;
		this.rememberMe = rememberMe;
		this.firePropertyChange("rememberMe", oldRememberMe, rememberMe);
		
	}	
	public boolean getRememberMe() {		
		return this.rememberMe;
		
	}	
	/**
	 * Affecte un message du processus de traitement de l'appel en cours.
	 * @param message Message du processus de traitement de l'appel en cours.
	 */
	private void setMessage(String message){
		String oldValue = this.message ;
		this.message = message ;
		this.firePropertyChange("message", oldValue, this.message);
	}
	
	/**
	 * Affecte un nouvel Etat a la connection 
	 * @param state Etat de la connection
	 */
	public void setState(int state, int error){
		if(state!=ConnectionConstants.INACTIVE_STATE && state!=ConnectionConstants.DIALING_STATE && state!=ConnectionConstants.ACTIVE_STATE)
			return;
		int oldValue = this.state;
		this.state = state ;
		this.firePropertyChange("state", oldValue, state);
		//Affectation de l'etat a l'entree
		this.getSubModel(EntryModel.class).setStatus(this.state);
		//
		if(state == ConnectionConstants.INACTIVE_STATE  && this.currentState != JDState.Ras_Disconnected){
			
			String entryName = this.getSubModel(EntryModel.class).getEntryName();
			connectionNotification.notify(entryName, JDState.Ras_Disconnected, error);
			system.renewNetworkInterfaces();
		}
	}
	
	/**
	 * Permet d'obtenir l'etat de la connexion
	 * @return L'etat de la connexion
	 */
	public int getState(){
		return this.state;
	}
	
	/**
	 * Permet de savoir si l'entree de connexion est active ou pas.
	 * @return true si la connexion est active et false dans le cas contraire.
	 */
	public boolean isActive(){
		return system.entryIsActive(this.getSubModel(EntryModel.class).getEntryName());
	}
	
	/**
	 * Permet de savoir si l'entree de connexion est en cours d'appel.
	 * @return true si l'entree de connexion est en cours d'appel et false dans le cas contraire.
	 */
	public boolean isDialing(){
		return system.entryIsDialing(this.getSubModel(EntryModel.class).getEntryName());
	}
	
	/**
	 * Selon que l'appel est actif ou pas, la fonction lance ou interrompe l'appel
	 */
	String accessType;
	Long surfDurationInMinutes;
	public void dial(){
		String entryName = this.getSubModel(EntryModel.class).getEntryName();
		if(system.entryIsDialing(entryName) || system.entryIsActive(entryName)) return;
		this.setState(ConnectionConstants.DIALING_STATE, -1);
		//Eventuellement a effacer.
		this.setMessage("Wait...");
		//Eventuellement attendre avt de lancer l'appel.
		Map<String, String> params = WebServiceInterf.getUserConnectionParams(username, password);
		if(params.containsKey("errorText")){
			
			this.setState(ConnectionConstants.INACTIVE_STATE, -1);
			DefaultView.showInformationBox(params.get("errorText"));
		}else{
			
			try{
				accessType = params.get(accessType);
				DefaultView.showInformationBox(params.get("errorText"));
				surfDurationInMinutes = new Long(params.get("surfDurationInMinutes")); // la duréé du surf
			}catch(Exception e){}
			system.dial(entryName, username, password);		
		}
	}
	
	/**
	 * Interrompe la connexion de l'entree de connexion du modele courant.
	 */
	public void hangup(){
		String entryName = this.getSubModel(EntryModel.class).getEntryName();
		if(system.entryIsDialing(entryName) || system.entryIsActive(entryName)){
			//this.s
			system.hangUp(entryName);
			// connectionNotification.notify(entryName, JDState.Ras_Disconnected, 0);
			this.setMessage("");//Eventuellement a effacer
			this.setState(ConnectionConstants.INACTIVE_STATE, -1);
			if(horloge.isRunning()) horloge.stop();
		}
	}
			
	/**
	 * Permet d'ajouter une ecouteur au processus d'appel.
	 * @param listener Ecouteur a ajouter.
	 */
	public void addDialProcessListener(PropertyChangeListener listener){
		if(listener==null) return;
		this.addPropertyChangeListener("message", listener);
	}
	
	/**
	 * Efface l'ecouteur du processus d'appel en parametre.
	 * @param listener Ecouteur du processus d'appel.
	 */
	public void removeDialProcessListener(PropertyChangeListener listener){
		if(listener==null) return ;
		this.removePropertyChangeListener("message",listener);
	}
	
	/**
	 * Ajoute un ecouteur des changements de nom utilisateur.
	 * @param listener Eccouteur de changement de nom d'utilisateur.
	 */
	public void addUsernameChangeListener(PropertyChangeListener listener){
		if(listener==null) return;
		this.addPropertyChangeListener("username", listener);
	}
	
	/**
	 * Efface un ecouteur de changement de nom d'utilisateur
	 * @param listener Ecouteur a supprimer
	 */
	public void removeUsernameChangeListener(PropertyChangeListener listener){
		if(listener==null) return ;
		this.removePropertyChangeListener("username",listener);
	}
	
	/**
	 * Ajoute un ecouteur au changement de mot de passe.
	 * @param listener Ecouteur a ajouter
	 */
	public void addPasswordChangeListener(PropertyChangeListener listener){
		if(listener==null) return;
		this.addPropertyChangeListener("password", listener);
	}
	
	/**
	 * Efface l'ecouteur de changement de mot de passe en parametre.
	 * @param listener Ecouteur a supprimer.
	 */
	public void removePasswordChangeListener(PropertyChangeListener listener){
		if(listener==null) return ;
		this.removePropertyChangeListener("password",listener);
	}
	
	/** remember me*/
	public void addRememberMeChangeListener(PropertyChangeListener listener){
		if(listener==null) return;
		this.addPropertyChangeListener("rememberMe", listener);
	}
	
	public void removeRememberMeChangeListener(PropertyChangeListener listener){
		if(listener==null) return ;
		this.removePropertyChangeListener("rememberMe",listener);
	}	
	
	
	/**
	 * Ajoute un ecouteur au changement d'etat de la connection.
	 * @param listener Ecouteur a ajouter
	 */
	public void addStateChangeListener(PropertyChangeListener listener){
		if(listener==null) return;
		this.addPropertyChangeListener("state", listener);
	}
	
	/**
	 * Efface l'ecouteur de changement d'etat en parametre.
	 * @param listener Ecouteur a supprimer.
	 */
	public void removeStateChangeListener(PropertyChangeListener listener){
		if(listener==null) return ;
		this.removePropertyChangeListener("state",listener);
	}

	private static List<CopyOfConnectionModel> connections = new ArrayList<CopyOfConnectionModel>();
	{
		connections.add(this);
	}	
	
	public static void closeAll(){
		for(CopyOfConnectionModel conn : connections){
			String entryName = conn.getSubModel(EntryModel.class).getEntryName();
			conn.system.hangUp(entryName);
			conn.system.removeEntry(entryName);
		}
	}
}
