package cm.ringo.dialer.helper;

import java.net.URL;
import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Helper;
import cm.enterprise.software.util.Resource;

/**
 * Les constantes du Dialer
 * @author Charles Moute
 * version 1.0.1, 04/08/2010
 */
public class DialerConstants extends Resource {

	public DialerConstants(){
		super();
		
		try {
			
			//Definition de quelques parametre du Dialer
			this.definesParameter("applicationName","logoDialer","defaultTheme","logoSystemTray","userProperties");
			
			//Affectation des valeurs au parametres
			this.setValueTo("applicationName", "RingoDialer");
			this.setValueTo("logoDialer","cm/ringo/dialer/resource/img/logo.png");
			this.setValueTo("defaultTheme", "cm/ringo/dialer/resource/theme/defaultTheme.zip"); //  "cm/ringo/dialer/resource/theme/defaultTheme.zip"
			this.setValueTo("logoSystemTray","cm/ringo/dialer/resource/img/logoTaskBar.png");
			this.setValueTo("userProperties","./resource/user.properties");
			
		} catch (ProgramException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Permet d'obtenir le chemin d'acces du logo du dialer
	 * @return Le chemin d'acces au fichier du logo du Dialer. 
	 */
	public URL getLogoApplication() { 		
		return Helper.getClassLoaderForResources().getResource(this.getValueOf("logoDialer")); 
	}
	
	/**
	 * Permet d'obtenir le nom de l'application
	 * @return Le nom de l'application
	 */
	public String getApplicationName(){
		return this.getValueOf("applicationName");
	}
	
	/**
	 * Permet d'obtenir l'URL du theme par defaut
	 * @return L'URL du theme par defaut.
	 */
	public URL getDefaultTheme(){
		return Helper.getClassLoaderForResources().getResource(this.getValueOf("defaultTheme"));
	}
	
	/**
	 * Permet d'obtenir le chemin d'acces du logo 64x64 du dialer
	 * @return Le chemin d'acces au fichier du logo  64x64 du Dialer. 
	 */
	public URL getLogoSystemTray() { 		
		return Helper.getClassLoaderForResources().getResource(this.getValueOf("logoSystemTray")); 
	}
	
	/**
	 * Permet d'obtenir le chemin d'accés au fichier des proprietes de l'utilisateur.
	 * @return Le chemin d'acces au fichier des proprietes de l'utilisateur.
	 */
	public String getUserPropertiesFile() {
		return this.getValueOf("userProperties");
	}
}
