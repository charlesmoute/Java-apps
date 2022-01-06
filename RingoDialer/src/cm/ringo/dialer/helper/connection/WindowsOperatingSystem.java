package cm.ringo.dialer.helper.connection;

import com.jmethod.jdialup.JDEntryProperties;
import com.jmethod.jdialup.JDException;
import com.jmethod.jdialup.JDNotification;
import com.jmethod.jdialup.JDial;

import cm.ringo.dialer.component.interfaces.connection.OperatingSystem;
import cm.ringo.dialer.component.model.base.connection.EntryModel;

public class WindowsOperatingSystem extends JDial implements OperatingSystem {

	public WindowsOperatingSystem(JDNotification dialNotifier) throws JDException  {
			super(dialNotifier);
	}

	@Override
	public int addEntry(EntryModel entry) {
		if(entry==null) return 1 ;
		JDEntryProperties properties = new JDEntryProperties();
		String[] pppoeDevices = this.getPPPDeviceNames();
		if(pppoeDevices==null) return 1 ;
		properties.setDeviceName(pppoeDevices[0]);
		properties.setDeviceType(JDEntryProperties.PPPoE_TYPE);
		properties.setRemoteDefaultGateway(true);
		return this.createEntry(entry.getEntryName(), properties,null,null);
	}

	public void dial(String entryName){
		this.dialAsyncProcess(entryName);
	}
	
	@Override
	public void dial(String entryName,String username,String password) {
		 this.dialAsyncProcess(entryName, username, password);
	}

	@Override
	public long getBytesReceived(String entryName) {
		return this.getConnectionBytesReceived(entryName);
	}

	@Override
	public long getBytesTransmitted(String entryName) {
		return this.getConnectionBytesTransmitted(entryName);
	}

	@Override
	public long getDialingTimeout() {
		return this.getDialTimeOut();
	}

	@Override
	public EntryModel getEntryModel(String entry) {
		/*if(!this.entryExists(entry)) return null;
		JDEntryProperties properties = this.getEntryProperties(entry);
		int idleTimeout = properties.getIdleTimeout();
		return new EntryModel(entry,idleTimeout);*/
		return null;
	}

	@Override
	public int getHangUpTimeout() {
		return this.getHangUpTimeOut();
	}

	@Override
	public String getIPAddress(String entryName) {
		return this.getClientPppIp(entryName);
	}

	@Override
	public int getIdleTimeout(String entryName) {
		if(this.entryExists(entryName)){
			JDEntryProperties properties = this.getEntryProperties(entryName);
			if(properties!=null) return properties.getIdleTimeout();
		}
		return -1;
	}

	@Override
	public String[] getPPPDeviceNames() {
		com.jmethod.jdialup.JDial jdial;
		String[] pppoeDevices = null;
		try {
			jdial = new com.jmethod.jdialup.JDial(null);
			pppoeDevices = jdial.getDeviceNames( JDEntryProperties.PPPoE_TYPE );
		} catch (JDException e) { return null; }		
		return pppoeDevices;
	}

	@Override
	public boolean renameEntry(EntryModel entry, String newName) {
		if(entry==null) return false;		
		return this.renameEntry(entry.getEntryName(), newName);
	}
	
	@Override
	public boolean removeEntry(String entryName) {
		return this.deleteEntry(entryName);
	}

	@Override
	public void setHangUpTimeout(int time) {
		this.setHangUpTimeOut(time);
	}

	@Override
	public int setProperties(EntryModel entry, int idleTimeout) {
		return this.setProperties(entry, idleTimeout, null, null);
	}

	@Override
	public int setProperties(EntryModel entry, String userName, String password) {
		return this.setProperties(entry, 0, userName, password);
	}

	@Override
	public int setProperties(EntryModel entry, int idleTimeout, String userName, String password) {
		if(entry!=null){
			if(!this.entryExists(entry.getEntryName())) return 1 ;	
			
			JDEntryProperties properties = this.getEntryProperties(entry.getEntryName());
			properties.setIdleTimeout(idleTimeout);				
			int ans = this.setEntryProperties(entry.getEntryName(), properties, userName, password);				
			if(ans!=0) return 1 ;				
			//entry.setIdleTimeout(idleTimeout);
			return 0 ;
		}
		return 1 ;
	}	
	
	public void renewNetworkInterfaces(){
		try {
			Runtime.getRuntime().exec("ipconfig /renew");
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
}
