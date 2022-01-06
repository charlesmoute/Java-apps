package component.model;

import helper.*;

import java.io.*;
import java.net.*;
import java.util.*;
/**
 * @author Charles&Marsien
 * <p>La classe HttpClient  permet de gérer une connexion cliente au serveur.</p>
 */
public class HTTPConnection {
	
	private URL url ;
	private HttpURLConnection server ;
	
	
	/**
	 * <p>Construit une instance initialisee au valeur par default</p>
	 */
	public HTTPConnection() throws Exception {
		this(null);
	}
	
	
	/**
	 * <p>Construit une instance de HttpClient avec une chaine representant l'URL de connexion.</>
	 * @param url URL de connexion.
	 * @throws Exception Si l'url est invalide.
	 */
	public HTTPConnection(String url) throws Exception{
		try {
			this.url = (url!=null)? new URL(url):null;
			this.server = null ;
		} catch (MalformedURLException e) {			
			throw new Exception("Erreur :: Url invalide");
		}
	}
	
	/**
	 * <p>Affecte une nouvelle URL de connexion</p>
	 * @param url Nouvelle URL de connexion.
	 * @throws Exception Si l'url est invalide.
	 */
	public void setURL(String url) throws Exception {
		try {			
			this.url = new URL(url);
			if(server!=null){
				server.disconnect();
				server = null ;
			}
		} catch (MalformedURLException e) {
			throw new Exception("Erreur :: Url invalide");
		}
		
	}
	
	/**
	 * @return URL de connexion.
	 */
	public URL getURFL() { return this.url; }
	
	/**
	 * <p>Construit les entetes standards.</p>
	 */
	private void buildStandardHeader() throws Exception {
		if(server!=null){ server.disconnect(); server = null; }
		try {
			server = (HttpURLConnection) this.url.openConnection();
		}catch(Exception e) { throw new Exception("Service Web Inutitisable :: Serveur Inaccessible"); }		
		server.setDoInput(true);
		server.setDoOutput(true);		
		server.setRequestProperty(HTTP.ACCEPT_CHARSET_HEADER.toLowerCase(),"iso-8859-1");
		server.setRequestProperty(HTTP.ACCEPT_HEADER.toLowerCase(), "text/html, text/plain, image/png, image/jpeg, image/gif");
		server.setRequestProperty(HTTP.ACCEPT_ENCODING_HEADER.toLowerCase(), "compress, gzip");
		server.setRequestProperty(HTTP.ACCEPT_LANGUAGE_HEADER.toLowerCase(), "fr, en");
		server.setRequestProperty(HTTP.USER_AGENT_HEADER.toLowerCase(), "RAMClient/1.0");
		//server.setRequestProperty(HTTP.FROM_HEADER.toLowerCase() , localhost+"@ramclient.cm");
		//server.setRequestProperty(HTTP.FROM_HEADER.toLowerCase() , InetAddress.getLocalHost()+"@ramclient.cm");				
	}
		
	/**
	 * <p>Effectue la methode get, du protocole HTTP, pour le fichier designe par le parametre</p>
	 * @param url Chemin du fichier que l'on souhaite obtenir
	 * @throws Exception Exception Erreur lancee lorsque l'obtention de l'url n'a pu se produire.
	 * @see #setURL(String)
	 */
	public void get(String url) throws Exception{	
		setURL(url);
		get();
	}
	
	
	/**
	 * <p>Effectue une recharge du fichier stocke dans l'url</p>
	 * @throws Exception Erreur lancee lorsque l'obtention de l'url n'a pu se produire.
	 */
	public void get() throws Exception {		
		try {			
			buildStandardHeader();
			server.setRequestMethod(HTTP.GET_METHOD);
			server.connect();
		} catch (IOException e){
			e.printStackTrace();
			throw new Exception("Error :: Ne peux obtenir "+url+"\n Probleme probablement due a la connexion au serveur.");
		}
	}
	
	/**
	 * <p>Effectue la methode head, du protocole HTTP, pour le fichier designe par le parametre</p>
	 * @param url Chemin du fichier que dont on souhaite obtenir l'entete.
	 * @throws Exception Exception Erreur lancee lorsque l'obtention de l'entete de l'url n'a pu se produire.
	 * @see #setURL(String)
	 */
	public void head(String url) throws Exception{
		setURL(url);
		head();
	}
	
	/**
	 * <p>Effectue une nouvelle requete de l'entete du fichier stocke dans l'url</p>
	 * @throws Exception Erreur lancee lorsque l'obtention de l'entete de l'url n'a pu se produire.
	 */
	public void head() throws Exception {
		try {
			buildStandardHeader();
			server.setRequestMethod(HTTP.HEAD_METHOD);
			server.connect();
		} catch (IOException e){
			e.printStackTrace();
			throw new Exception("Error :: Ne peux obtenir l'entete de "+url+"\n Probleme probablement due a la connexion au serveur.");
		}
	}
	
	/**
	 * <p>Effectue la methode head, du protocole HTTP, pour le fichier designe par le parametre</p>
	 * @param url Chemin du fichier que dont on souhaite obtenir l'entete.
	 * @throws Exception Exception Erreur lancee lorsque l'obtention de l'entete de l'url n'a pu se produire.
	 * @see #setURL(String)
	 */
	public void delete(String url) throws Exception{
		setURL(url);
		delete();
	}
	
	/**
	 * <p>Demande unesuppresion du fichier stocke dans l'url</p>
	 * @throws Exception Erreur lancee lorsque la suppresion n'a pu se produire,pour une raison ou une autre.
	 */
	public void delete() throws Exception {
		try {			
			buildStandardHeader();
			server.setRequestMethod(HTTP.DELETE_METHOD);
			server.connect();
		} catch (IOException e){
			e.printStackTrace();
			throw new Exception("Erreur :: Ne peux supprimer "+url+"\n Probleme probablement due a la connexion au serveur.");
		}
	}
	
	/**
	 * <p>Cree un fichier sur le serveur avec pour contenu message et pour chemin absolue l'url.</p>
	 * @param url Chemin absolue du fichier a creer sur le serveur.
	 * @param message Contenu du fichier.
	 * @throws Exception Erreur lancee, en particulier lorsqu'on est incapable d'ecrire dans le flux de sortie.
	 */
	public void put (String url,String message)throws Exception{
		setURL(url);
		put(message);
	}
	
	/**
	 * <p>Creer un fichier texte sur le serveur; avec pour contenu message et pour chemin absolu l'url courante.</p>
	 * @param message contenu du fichier.
	 * @throws Exception Erreur lancee, en particulier lorsqu'on est incapable d'ecrire dans le flux de sortie.
	 */
	public void put (String message)throws Exception{		
		if(message!=null && message.compareTo("")!=0){
			buildStandardHeader();
			String line = message+HTTP.CRLF;			
			server.setRequestMethod(HTTP.PUT_METHOD);			
			server.setRequestProperty(HTTP.CONTENT_TYPE_HEADER.toLowerCase(), "text/plain");
			server.setRequestProperty(HTTP.CONTENT_LENGTH_HEADER.toLowerCase(),""+line.length());
			server.connect();			
			try
		    {
			  PrintWriter out;
			  out = new PrintWriter(new OutputStreamWriter(server.getOutputStream()));
			  out.write(line);
			  out.flush();
			  out.close();
		    }catch(Exception e){
		      throw new Exception("N'est pas en mesure d'ecrire dans le flux de sortie.Verifiez votre connexion");
		    }
		}else throw new Exception("Le message est vide.Veuillez saisir un non vide");
	}
	
	/**
	 * <p>Creer le fichier file sur le serveur avec comme chemin absolue url.</p>
	 * @param url Chemin du fichier a creer sur le serveur.
	 * @param file fichier a creer sur le serveur.
	 * @throws Exception Erreur genere au cours de l'upload.
	 */
	public void put(String url,File file) throws Exception {
		setURL(url);
		put(file);
	}
	
	/**
	 * <p>Cree le fichier </p>
	 * @param file
	 * @throws Exception
	 */
	public void put (File file) throws Exception{
		
		if(file!=null && file.isFile()){			
			buildStandardHeader();
			server.setRequestMethod(HTTP.PUT_METHOD);			
			String mimeType = "";
			mimeType = URLConnection.guessContentTypeFromName(file.getName()) ;			
			String type = "application/octet-stream";
			
			if(mimeType!=null){									
				int pos = mimeType.indexOf('/');
				if(pos!=-1) type = mimeType.substring(0, pos);
				server.setRequestProperty(HTTP.CONTENT_TYPE_HEADER.toLowerCase(), mimeType);
			}else server.setRequestProperty(HTTP.CONTENT_TYPE_HEADER.toLowerCase(),type);
			
			server.setRequestProperty(HTTP.CONTENT_LENGTH_HEADER.toLowerCase(),""+file.length());
			server.connect();
			
			try{
								
				File newFile = Util.copyFile(file, Configuration.CACHE_DIRECTORY);
				if(type.compareToIgnoreCase("text")==0){
					PrintWriter out;
					out = new PrintWriter(new OutputStreamWriter(server.getOutputStream()));
					Scanner in = new Scanner(new FileInputStream(newFile));
					while(in.hasNextLine()) out.write(in.nextLine());
					out.write(HTTP.CRLF);					
					out.flush();
					in.close();
					out.close();					
				}else{
					DataOutputStream out = new DataOutputStream(server.getOutputStream());
					DataInputStream in = new DataInputStream(new FileInputStream(newFile));
					byte[] buff = new byte[2048];
					int read = in.read(buff, 0, 2048);
					while(read>=0){						
						out.write(buff,0 ,read);
						read = in.read(buff, 0, 2048);
					}
					in.close();
					out.flush();		
					out.close();					
				}
			}catch(Exception e){ throw new Exception("N'est pas en mesure d'ecrire dans le flux de sortie."+"\n Probleme probablement due a la connexion au serveur."); }		   
		}else throw new Exception("Le fichier "+file.getAbsolutePath()+" n'est pas un fichier. Veuillez en donner un de valide");
	}
	
	/**
	 * <p>Dit si la connexion est encore disponible.</p> 
	 */
	public boolean isConnect() { return (server!=null); }
	
	public String getResponseMessage() {
		if(server!=null)
			try { return server.getResponseMessage(); } catch (IOException e) {				
				e.printStackTrace();
				return e.getMessage();
			}
		return null;
	};
	
	/**
	 * <p>Interrompe la connexion au serveur</p>
	 */
	public void disconnect() { 
		if(server!=null){
			server.disconnect();
			server = null;
		}
	}
	
	/**
	 * @return Le type du contenu du fichier.
	 */
	
	public String getContentType() {
		if(server!=null) return server.getContentType();		
		return null;
	}
	
	/**
	 * @return Le contenu du fichier obtenue de la requete.
	 */
	public InputStream getContent() throws Exception{
		if(server!=null)
			try {
				return server.getInputStream();
			} catch (IOException e) {
				throw new Exception("Erreur lors de la lecture du contenu");
			}
			
		return null;
	}
	
	public File getResponseOfHeadRequest() {		
		if(server!=null && server.getRequestMethod().compareToIgnoreCase(HTTP.HEAD_METHOD)==0){
			Map<String,List<String>> header = server.getHeaderFields();
			File file = null;
			if(header!=null){
				Vector<String>	value = new Vector<String>();
				for(String key: header.keySet()){					
					String entete ="";					
					if(key!=null && !key.isEmpty()) entete=" "+key+":";					
					List<String> values = header.get(key);
					if(values!=null){
						for(int i=0;i<values.size();i++){
							entete+=values.get(i);
							if(i!=(values.size()-1)) entete+=",";									
						}									
					}
					//value+=entete+"\n";
					value.add(new String(entete));
					//System.out.println(entete);
				}
				File cache = new File(Configuration.CACHE_DIRECTORY);
				if(!cache.exists()) cache.mkdir();
				String nameFile = Configuration.CACHE_DIRECTORY+"temp"+Util.getToday();
				file = new File(nameFile);
				try {
					file.createNewFile();
					PrintWriter out = new PrintWriter(new FileOutputStream(file));
					if(value!=null && value.size()>0) for(String val:value) out.println(val);
					out.close();
				} catch (IOException e) {e.printStackTrace();}
			}
			return file;
		}				
		return null;		
	}	
}