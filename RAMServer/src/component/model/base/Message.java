package component.model.base;

import java.io.*;
import java.util.*;

import helper.*;

/**
 * 
 * @author Charles&Marsien
 *  <p>
 *  	Cette classe represente dans le cas du :
 *  	<ul> 
 *  		<li>Serveur : une reponse de ce dernier au client.</li>
 *  		<li>Client : une requete de ce dernier au serveur.</li>
 *  	</ul> 
 *  	Elle implemente les methodes utiles pour :
 *  	 <ul>
 *  		<li>Construire un message de facon progressive</li>
 *      	<li>Construire un message a partir d' une url specifie</li>
 *          <li>Obtenir les informations d'une requete ou d'une reponse</li>
 *       </ul>
 *  </p>
 */
public class Message {
	
	/**
	 * line est la premiere ligne d'un message. Elle est soit : 
	 * 	<ul>
	 * 		<li>
	 * 			Une ligne de requete lorsque le message vient du client.<br>
	 * 			Exemple : GET /Web/exemple/index.html HTTP/1.0
	 * 		</li>
	 * 		<li>
	 * 			Une ligne de statut lorsque le message vient du serveur.<br>
	 * 			Exemple : HTTP/1.0 200 OK
	 * 		</li>
	 *  </ul>
	 */
	private String line ;
	
	/**
	 * minetype est le type MINE du contenu. Exemple : "text/html" 
	 */
	private String mimeType ;
	
	/**
	 * data represente le corps du message. Il peut etre null.
	 */
	private InputStream data ;
	
	/**
	 * header represente l'ensemble des entetes du message.
	 */
	private Map<String,String> header ;
	
	/**
	 * <p>Dit de qui vient le message</p>
	 */
	private String from =null ;
	
	/**
	 *  <p>
	 *  	Constructeur par defaut. Les valeurs par de faut sont :
	 *  	<ul>
	 *  		<li>line = null</li>
	 *  		<li>data=mine=null</li>
	 *  		<li>header = null</li>
	 *  	</ul>
	 *  </p>
	 */	
	public Message(){
		this.line =null;
		this.mimeType = null ;
		this.header = null ;
		this.data = null;	
	}

	/**
	 * <p>Construit un message.</p>
	 * @param line Ligne de debut du message.
	 * @param mimeType Type du contenu du message.
	 * @param data Contenu du message.
	 */
	public Message( String line, String mimeType, InputStream data ){
		this.line = line;
		this.mimeType = mimeType;
		this.header = new TreeMap<String,String>() ;
		addHeader("Date", Util.today());
		addHeader(HTTP.SERVER_HEADER, Configuration.SERVER_VERSION);		
		if(mimeType!=null) addHeader(HTTP.CONTENT_TYPE_HEADER, mimeType);		
		if(data!=null)
			try {
				addHeader(HTTP.CONTENT_LENGTH_HEADER, ""+(data.available()+1000));
			} catch (IOException e) {e.printStackTrace();}
		this.data = data;
	}

	/**
	 * <p>
	 * 	Construit un message en produisant Flux de caractere a partir d'un texte en entree.
	 * </p>
	 * @param line Ligne de debut du message.
	 * @param mimeType Type du contenu du message.
	 * @param data Contenu du message.
	 */	
	public Message( String line, String mimeType, String data ){
		this.line = line;
		this.mimeType = mimeType;		
		this.header = new TreeMap<String,String>() ;
		addHeader("Date", Util.today());
		addHeader(HTTP.SERVER_HEADER, Configuration.SERVER_VERSION);
		if(mimeType!=null) addHeader(HTTP.CONTENT_TYPE_HEADER, mimeType);
		if(data!=null)addHeader(HTTP.CONTENT_LENGTH_HEADER, ""+data.length());
		this.data = Util.createInputStream(data);
	}
	
	/**
	 * <p>
	 * 	Construit un message en produisant flux raw a partir d'un fichier, a l'instar 
	 *  des fichiers images.
	 * </p>
	 * @param line Ligne de debut du message.
	 * @param mimeType Type du contenu du message.
	 * @param data Contenu du message.
	 * @throws FileNotFoundException Si le fichier n'est pas trouve.
	 */	
	public Message( String line, String mimeType, File data ) throws FileNotFoundException{
		this.line = line;
		this.mimeType = mimeType;
		this.header = new TreeMap<String,String>() ;
		addHeader("Date", Util.today());
		addHeader(HTTP.SERVER_HEADER, Configuration.SERVER_VERSION);
		if(mimeType!=null) addHeader(HTTP.CONTENT_TYPE_HEADER, mimeType);
		if(data!=null)addHeader(HTTP.CONTENT_LENGTH_HEADER, ""+data.length());
		this.data = Util.createInputStream(data);
	}

	/**
	 * <p>Affecte le parametre commme la premiere ligne du message.</p>
	 * @param line Ligne de requete ou de statut.
	 */
	public void setBeginingLine(String line) {this.line = line ;}
	
	/**
	 * @return La ligne de debut du message.
	 */
	public String getBeginingLine() { return this.line; }
	
	/**
	 * <p>Affecte le type du contenu du message a mineType.</p>
	 * @param mineType Type du contenu.
	 */
	public void setMINEType(String mineType) { this.mimeType = mineType; }
	
	/**
	 * @return Le type du contenu.
	 */
	public String getMINEType() { return this.mimeType; }
	
	
	/**
	 * <p>Ajoute une entete.</p>
	 * @param name Entete a ajouter ou a modifier.
	 * @param value Valeur de l'entete.
	 */
	public void addHeader( String name, String value )	{
		if(header==null) this.header = new TreeMap<String,String>() ;
		header.put(name, value);
	}
	
	/**
	 * <p>Efface une entete.</p>
	 * @param name Nom de l'entete a effacer. 
	 */
	public void removeHeader(String name) {	header.remove(name); }
	
	/**
	 * <p>Retourne la valeur d'une entete donnee.</p>
	 * @param name Nom entete.
	 * @return Valeur de l'entete de nom name.
	 */
	public String getHeader(String name) {return header.get(name); }
	
	/**
	 * <p>Affecte le parametre commme ensemble des entetes</p>
	 * @param headerList Liste d'entete.
	 * @see #getHeaderList()
	 */
	public void setHeader(Map<String,String> headerList) {
		this.header = headerList ;
	}
	/**
	 * @return L'ensemble des entetes et leurs valeurs asssocies.
	 * @see #setHeader(Map)
	 */
	public Map<String,String> getHeaderList(){return this.header;}
	
		
	/**
	 * <p>Affecte le parametre comme le contenu du message.</p>
	 * @param data Contenu du message.
	 * @see #getData()
	 */
	public void setData(InputStream data){ this.data = data; }
	
	/**
	 * @return Le contenu du message.
	 * @see #setData(InputStream)
	 */
	public InputStream getData() { return this.data; }
	
	/**
	 * <p>Affecte le parametre comme adresse de l'expediteur du message</p>
	 * @param from Expediteur du message.
	 */
	public void setFrom(String from) { this.from = from ; }
	
	/**
	 * @return L'expediteur du message.
	 */
	public String getFrom() { return this.from; }
	
	public String toString() {
		String str = "";
		if(line!=null) str+=line+"\n";
		if(from!=null) str+=" From: "+from+"\n" ;
		if(header!=null){
			for(String key:header.keySet())
				str+=" Header: '"+key+"' = '"+header.get(key)+"'\n";			
		}
		return str;
	}
}
