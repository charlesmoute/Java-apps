package cm.ringo.dialer.helper.connection;

import java.net.URL;
import java.text.DecimalFormat;

import cm.enterprise.software.helper.Application;
import cm.ringo.dialer.helper.connection.constants.ConnectionConstants;
import cm.ringo.dialer.helper.connection.multilingual.EntryMultilingualResource;
import cm.ringo.dialer.interfaces.Connection;

/**
 * Classe regroupant l'ensemble des fonctions utilitaires pour la manipulation
 * d'une entree.
 * @author Charles Moute
 * @version 1.0.1, 5/7/2010
 */
public class EntryHelper {

	/**
	 * Permet l'arrodissement d'ordre order du nombre number
	 * @param number Nombre a arrondi.
	 * @param order Ordre de l'arrondissement
	 * @return Le nombre number arrondi a l'ordre order
	 */
	public static double round(double number,int order) {	
		double rounded ;		
		if(order<0) return number ;
		if(order==0) Math.round(number);
		String decimalFormat ="0.";			
		for(int i=1;i<=order;i++) decimalFormat+="0";		
		DecimalFormat format  = new DecimalFormat(decimalFormat);
		rounded = Double.parseDouble(format.format(number).replace(',', '.'));
		return rounded ;
	}
	
	/**
	 * Permet la coversion des bytes en chaine de caracteres associes avec l'unite de mesure adequate.
	 * @param bytes Le nombre de bytes a convertir
	 * @return La conversion du parametre
	 */
	public static String bytesToString(long bytes){

		if(bytes<=0) return "----" ;//0 Bytes
		
		float divider = 1;
		String unit = "Bytes";
		if(bytes<1048576){
			divider = 1024;
			unit = "KB";				
		}else{
			divider = 1048576 ;
			unit = "MB";
		}
		
		float v = ((float)bytes)/divider;
		String aux = ""+round(v,2) ;
		
		return aux+" "+unit;
	}
	
	/**
	 * Permet de recuperer le message qui est associe au statut en parametre
	 * @param status Statut dont on veut obtenir le message associe
	 * @return Le message associe au statut en parametre
	 */
	public static String getMessageOfStatus(int status){
		
		if(!Application.instanceOfModuleExists(Connection.class)) return null;
		
		String message = null;
		EntryMultilingualResource rsrc;
		rsrc = Application.getModule(Connection.class).getResource(EntryMultilingualResource.class);
		switch(status){
			case ConnectionConstants.INACTIVE_STATE	: 	message = rsrc.getValueOf("disconnectedMessage"); break;
			case ConnectionConstants.DIALING_STATE	:	message = rsrc.getValueOf("dialingMessage"); break;
			case ConnectionConstants.ACTIVE_STATE	:	message = rsrc.getValueOf("connectedMessage"); break;
		}
		return message;
	}
	
	/**
	 * Permet d'obtenir l'image associee au status en parametre
	 * @param status Statut dont on veut obtenir l'image associee
	 * @return L'image associee au statut en parametre
	 */
	public static URL getPictureOfStatus(int status){

		if(!Application.instanceOfModuleExists(Connection.class)) return null;
		URL picture = null ;
		
		ConnectionConstants rsrc ;
		rsrc = Application.getModule(Connection.class).getResource(ConnectionConstants.class);
		switch(status){
			case ConnectionConstants.INACTIVE_STATE	: 	picture = rsrc.getInactivePicture(); break;
			case ConnectionConstants.DIALING_STATE	:	picture = rsrc.getDialingPicture(); break;
			case ConnectionConstants.ACTIVE_STATE	:	picture = rsrc.getActivatePicture(); break;
		}
		return picture;
	}
	
}
