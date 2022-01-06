package helper;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.*;
import java.net.*;
import java.awt.*;
import java.text.*;
import java.util.*;

import component.model.base.*;



/**
 * 
 * @author Charles&Marsien
 * <p>
 *   Cette classe contient toute les methodes statiques utilisees dans les modeles.
 * </p>
 * 
 */

public class Util {
	
	/**
	 * <p> Taille du Buffer = 2048</p>
	 */
	public static final int SIZE_BUFFER = 2048 ;
	
	/**
	 * <p>Codage des caracteres utilises. Valeur = ISO-8859-1</p>
	 */
	public static final String CHARSET = "ISO-8859-1";
  	
	/**
	 * <p>Port represente le port de preference de l'administrateur.</p>
	 */
	private static int port =Configuration.DEFAULT_PORT ;
	
	/**
	 * @return Le port de l'application serveur.
	 */
	public static final int getPort() {return Util.port; }
	
	/**
	 * <p>
	 * 		Iniitialise le port avec la valeur du parametre. Si ce parametre est
	 * 		inferieur a 0, la valeur 8080 est considere comme valeur du port.
	 * </p>
	 * @param port Nouveau port su serveur.
	 */
	public static final void setPort(int port){
		if(port<0) port = Configuration.DEFAULT_PORT ;
		else Util.port = port ;
	}
	
	/**
	 * <p>Nombre de connexion pouvant s'executer simultanement. Zero = nombre infini.</p>
	 */
	private static int threadNumber = Configuration.DEFAULT_THREAD_NUMBER;
	
	/**
	 * @return Le nombre de Connexion pouvant exister simultanement. 0 = nombre infini
	 */
	public static final int getThreadNumber() { return Util.threadNumber; }
	
	/**
	 * <p>
	 * 	 Affecte le nombre de thread pouvant s'executer simultanement. Si number est 
	 * 	 inferieur a zero, zero est affecte ce qui signifie qu'un nombre infini de 
	 * 	 connexion peuvent s'executer simultanement.
	 * </p>
	 * @param number Nombre de connexion.
	 */
	public static final void setThreadNumber(int number) {
		if(number<0) Util.threadNumber = Configuration.DEFAULT_THREAD_NUMBER;
		else Util.threadNumber = number ;
	}
	
	/**
	 * Definit comment le serveur gere ces connexions, en mutli-thread ou avec des canaux.
	 */	
	private static String serverType = Configuration.MULTITHREAD_SERVER ;
	
	/**
	 * @return Le type des connexions du serveur.
	 */
	public static final String getServerType() { return Util.serverType; }
	
	/**
	 * <p>Ecrit le message dans le fichier log du jour et en ajout.</p>
	 * @param message Message a ecrire dans le fichier log du jour.
	 */
	synchronized public static final void writeLog(Message message) throws Exception{
		//System.out.println("line = "+message);
		File logFolder = new File(Util.logFolder);
		if(!logFolder.exists()) logFolder.mkdirs();
		
		String name = Util.logFolder+"Log " ;
		String date = Util.today() ;
		int pos = date.lastIndexOf(' ');
		if(pos!=-1){
			pos = date.substring(0, pos).lastIndexOf(' ');
			if(pos!=-1){
				date = date.substring(0, pos);
			}
		}
		if(pos==-1) date = "";
		name+=date+".txt";
		File file = new File(name);
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				Util.writeError("Util.writeLog ::Impossible de creer le fichier log de nom "+name);
				throw new Exception("Impossible de creer le fichier "+name);
			}
		
		ArrayList<String> content = new ArrayList<String>();
		Scanner scan = new Scanner(new FileInputStream(file));
		while(scan.hasNextLine()) content.add(scan.nextLine());
		scan.close();
		
		String newContent = "";
		if(message!=null){
			String  from = null;			
			if(!message.getBeginingLine().toLowerCase().startsWith(HTTP.HTTP_VERSION.toLowerCase()))
			   from = message.getFrom();	
			if(from!=null) newContent+=" [ Request de "+from+" ] ";
			else newContent+=" [ Response Serveur ] ";
			newContent+="["+message.getBeginingLine()+"] ["+Util.today()+"] ";			
		}else newContent = "[Message Null] ["+Util.today()+"] " ;
		
		PrintWriter out = new PrintWriter(new FileOutputStream(file));
		//<=>
		if(content.size()>0){
			for(String val: content) out.println(val);
			//out.println(content);
			out.flush();
		}
		out.println(newContent);		
		out.flush();
		out.close();
		//System.out.println(Util.today());
	}
	
	
	/**
	 * <p>Ecrit le message dans le fichier erreur du jour et en ajout.</p>
	 * @param message Message a ecrire dans le fichier erreur du jour.
	 */
	synchronized public static final void writeError(String message){
		//System.out.println("line = "+message);
		
		File errorFolder = new File(Configuration.ERROR_DIRECTORY);
		if(!errorFolder.exists()) errorFolder.mkdirs();
		
		String name = Configuration.ERROR_DIRECTORY+"Error " ;
		String date = Util.today() ;
		int pos = date.lastIndexOf(' ');
		if(pos!=-1){
			pos = date.substring(0, pos).lastIndexOf(' ');
			if(pos!=-1){
				date = date.substring(0, pos);
			}
		}
		if(pos==-1) date = "";
		name+=date+".txt";
		File file = new File(name);
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {}
		
		ArrayList<String> content = new ArrayList<String>();
		Scanner scan;
		try {
			scan = new Scanner(new FileInputStream(file));
			while(scan.hasNextLine()) content.add(scan.nextLine());
			scan.close();
		} catch (FileNotFoundException e) { e.printStackTrace(); }
		
		
		String newContent = "";
		if(message!=null){			
			newContent+="["+message+"] ["+Util.today()+"] ";			
		}
		
		PrintWriter out;
		try {
			out = new PrintWriter(new FileOutputStream(file));
			if(content.size()>0){
				for(String val: content) out.println(val);
				out.flush();
			}
			out.println(newContent);		
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {	e.printStackTrace(); }		
	}
	
	/**
	 * <p>
	 * 	  Affecte le type de serveur les valeurs possibles sont :
	 * 	  <ul>
	 * 		<li>ConfigurationConstant.MULTITHREAD_SERVER</li>
	 * 		<li>ConfigurationConstant.CHANNEL_SERVER</li>
	 * 	  </ul>
	 * </p> 
	 * @param type Type des connexions du serveur.
	 */
	public static final void setServerType(String type){
		if(type.compareTo(Configuration.MULTITHREAD_SERVER)!=0 &&
				type.compareTo(Configuration.CHANNEL_SERVER)!=0)
			Util.serverType = Configuration.MULTITHREAD_SERVER ;
		else
			Util.serverType = type ;
	}	
	/**
	 * <p>Represente le chemin d'acces au repertoire racine du serveur</p>
	 */
	private static String webFolder = Configuration.WEB_DIRECTORY ;
	
	/**
	 * @return Le chemin d'acces au repertoire racine du serveur.
	 */
	public static final String getWebFolder(){ return Util.webFolder; }
	
	/**
	 * <p>
	 * 	 Definit un repertoire racine du serveur a partir du repertoire courant
	 *   de l'application.
	 * </p>
	 * @param folder
	 */
	public static void setWebFolder(String folder){
		if(folder==null || folder.length()==0 || folder.compareTo(" ")==0)
			Util.webFolder = Configuration.WEB_DIRECTORY ;
		else Util.webFolder =folder;			
	}
	
	/**
	 * <p>Reponse a une question de type oui ou non.</p>
	 */
	private static boolean answer = true ;
	
	/**
	 * <p>
	 * 	 Affecte une valeur comme reponse a la question voulez-vous renomer les fichiers stockes
	 *   dans le cache lors de l'application des methodes PUT et DELETE ?
	 * 	 Les valeurs possibles sont true pour oui et false pour non.
	 * </p>
	 * @param answer Booleen representant une reponse.
	 */
	public static  final void setAnswer(boolean answer){
		Util.answer = answer;
	}
	
	/**
	 * <p>
	 * 	 Retourne la valuer de la reponse a la question Voulez-vous renomer les fichiers
	 * 	 stockes dans le cache lors de l'application des methodes PUT et DELETE ?   
	 * </p>
	 * @return True si la reponse est oui et false dans le cas contraire.
	 */
	public static final boolean getAnswer() { return Util.answer; }
	
	
	/**
	 * <p>
	 * 	Repertoire des fichiers de log sur le serveur.
	 * </p>
	 */
	public static String logFolder = Configuration.LOG_DIRECTORY;
	
	/**
	 * <p>Couleur d'arriere-plan du serveur.(Cote Graphisme du Serveur)</p>
	 */
	public static Color backgroundColor = Configuration.BACKGROUND ;
	
	
	/**
	 * <p>Couleur du texte du serveur. (Cote Graphisme du Serveur)</p>
	 */
	public static Color foregroundColor = Configuration.FOREGROUND ;
	
	/**
	 * <p> Couleur des caracteres. (Cote Graphisme du Serveur)</p>
	 */
	public static Color caretColor = Configuration.CARETCOLOR ;
	
	/**
	 * <p> Reinitialise les configurations du serveur</p>
	 */
	public static final void resetConfiguration() {
		Util.answer = true ;
		Util.port = Configuration.DEFAULT_PORT ;
		Util.threadNumber = Configuration.DEFAULT_THREAD_NUMBER;
		Util.serverType = Configuration.MULTITHREAD_SERVER ;
		Util.webFolder = Configuration.WEB_DIRECTORY ;
		Util.logFolder = Configuration.LOG_DIRECTORY ;
		Util.backgroundColor = Configuration.BACKGROUND ;
		Util.foregroundColor = Configuration.FOREGROUND ;
		Util.caretColor = Configuration.CARETCOLOR ;		
	}
	
	/**
	 * <p>Sauvegarde les configurations.</p>
	 * @throws IOException 
	 */
	public static void saveConfiguration() throws IOException {
		Properties config = new Properties();
		config.put("port",""+Util.getPort());
		config.put("threadNumber", ""+Util.getThreadNumber());
		config.put("serverType", Util.getServerType());
		
		File webroot = new File(Util.getWebFolder());
		if(!webroot.exists()) webroot.mkdirs();
		config.put("webDirectory", Util.getWebFolder());
		
		File logFolder = new File(Util.getWebFolder());
		if(!logFolder.exists()) logFolder.mkdirs();
		config.put("logDirectory",Util.logFolder);
		
		if(Util.answer) config.put("answer", Configuration.YES);
		else config.put("answer", Configuration.NO);
		
		File f = new File(Configuration.NAME_CONFIGURATION_FILE); 
		if(!f.exists()) f.createNewFile();
		FileOutputStream file = new FileOutputStream(f);		
		config.store(file,"Reglages du logiciel RAMServer par Charles&Marsien");		
		file.close();
	}
	
	/**
	 * <p>Initialise le fichier de configuration.</p>
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static final void initializeConfiguration() throws FileNotFoundException, IOException {
		if(!new File(Configuration.NAME_CONFIGURATION_FILE).exists())
			resetConfiguration();
		else{
			Properties config = new Properties();
			config.load(new FileInputStream(Configuration.NAME_CONFIGURATION_FILE));
			Util.port = Integer.parseInt(config.getProperty("port"));
			Util.threadNumber = Integer.parseInt(config.getProperty("threadNumber"));
			Util.serverType = config.getProperty("serverType");
			Util.setWebFolder(config.getProperty("webDirectory"));
			Util.logFolder = config.getProperty("logDirectory");
			if(config.getProperty("answer").compareToIgnoreCase(Configuration.YES)==0)
				Util.answer = true ;
			else Util.answer = false ;
		}
	}
	
	/**
	 * <p>Dit si la methode est une methode permise.</p>
	 * @param method Methode que l'on doit verifie.
	 * @return True si la methode est autorisee par le serveur.
	 */
	public static final boolean isAllowedMethod(String method) {
		boolean isCorrect = false ;
		ArrayList<String> list = new ArrayList<String>();		
		list.add(HTTP.GET_METHOD);
		list.add(HTTP.HEAD_METHOD);
		list.add(HTTP.PUT_METHOD);
		list.add(HTTP.DELETE_METHOD);
		for(String methode:list) if(methode.compareToIgnoreCase(method)==0){isCorrect = true; break;}
		return isCorrect ;
	}
	
	/**
	 * @return La liste des mine disponibles.
	 */
	public static final Map<String,String>getListdMineType(){
		Map<String,String> map = new TreeMap<String,String>();
		StringTokenizer mine = new StringTokenizer(
				"htm		text/html "+
				"html		text/html "+
				"txt		text/plain "+
				"asc		text/plain "+
				"gif		image/gif "+
				"jpg		image/jpeg "+
				"jpeg		image/jpeg "+
				"png		image/png "+
				"mp3		audio/mpeg "+
				"m3u		audio/mpeg-url " +
				"pdf		application/pdf "+
				"doc		application/msword "+
				"ogg		application/x-ogg "+
				"zip		application/octet-stream "+
				"exe		application/octet-stream "+
				"class		application/octet-stream " );
			while ( mine.hasMoreTokens())
				map.put( mine.nextToken(), mine.nextToken());
		return map;
	}
		
	/**
	 * <p>
	 * 	Cree un flux de donnees de type raw, tel les images, a partir d'un fichier 
	 * 	passe en parametre.
	 * </p>
	 * @param file Fichier dont on veut obtenir un flux de donnees.
	 * @return Flux permettant de gerer des donnees raw. 
	 * @throws FileNotFoundException si le fichier n'est pas trouve.
	 */
	public static final InputStream createInputStream(File file) throws FileNotFoundException {
		FileInputStream inputStream= null;
		if(file!=null && file.isFile())	inputStream = new FileInputStream(file);		
		return inputStream;
	}
	
	/**
	 * <p>
	 * 	Cree un flux de donnees de type caratere a partir de la donnee passe en parametre.
	 * </p>
	 * @param data Donnee dont on veut obtenir un flux de donnees.
	 * @return Flux permettant de gerer des donnees de type caractere.
	 */
	public static final InputStream createInputStream(String data) {
		ByteArrayInputStream inputStream = null;
		if(data!=null) inputStream = new ByteArrayInputStream( data.getBytes());
		return inputStream;
	}
	
	
	
	/**
	 * <p>
	 * 	 Construit une page Web avec comme corps le deuxieme parametre et comme titre de document
	 * 	 code.	
	 * </p>
	 * @param code Titre de document.
	 * @param body Corps du document HTML.
	 */
	public static final String buildHTMLPage(String code,String body){
		return " <html> "+
			   "	<head> "+
			   "		<title>HTTP "+code+"</title> "+		
			   "	</head> "+
			   "    <body style=\"font-size=10px;\"> "+
			   "		<center> "+
			   "		 <div style=\"margin-top:35px;border-bottom:dotted 2px black;\"> <h2><b>"+code+"</b></h2> </div>" +
			   "		</center> "+
			   "		<p>"+body+"</p> "+
			   "	</body> "+
			   " </html> ";
	}
	
	/**
	 * @return Le jour avec l'heure de l'instant.
	 */
	public static final String today() {		
		SimpleDateFormat date = new java.text.SimpleDateFormat( "E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		date.setTimeZone(TimeZone.getTimeZone("GMT"));
		return  date.format(new Date());
	}
	
	/**
	 * <p>
	 * 	 Transforme la date et l'heure courante comme suit :
	 * 	 si on a comme date le 22/01/2010 13:54:49 on obtient
	 * 	 22012010135449
	 * </p>
	 * @return Un code de la date et heure courante.
	 */
	public static final String getToday() {		
		
		Calendar calendar = Calendar.getInstance();		
		String date =""+calendar.get(Calendar.DAY_OF_MONTH);
		
		if(calendar.get(Calendar.MONTH)<9) date+="0"+(calendar.get(Calendar.MONTH)+1);
		else date+=(calendar.get(Calendar.MONTH)+1);
		
		date+=calendar.get(Calendar.YEAR);
		
		if(calendar.get(Calendar.HOUR_OF_DAY)<9)date+="0"+calendar.get(Calendar.HOUR_OF_DAY);
		else date+=calendar.get(Calendar.HOUR_OF_DAY);
		
		if(calendar.get(Calendar.MINUTE)<9)date+="0"+calendar.get(Calendar.MINUTE);
		else date+=calendar.get(Calendar.MINUTE);
		
		if(calendar.get(Calendar.SECOND)<9)date+="0"+calendar.get(Calendar.SECOND);
		else date+=calendar.get(Calendar.SECOND);
		return  date;
	}
	
	/**
	 * <p> Retransforme la chaine obtenu avec getToday</p>
	 * @param date Date a retransforme.
	 * @return Date coherente.
	 * @see #getToday()
	 */
	public static final String transformDate(String date) {
		String val = null;
		if(date!=null && date.length()==14){
			val= date.substring(0,2)+"/"+date.substring(2,4)+"/"+date.substring(4,8);
			val+= " "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12) ;
		}
		return val ;
	}
	
	/**
	 * <p> Construit une requete a partir du flux d'entree.</p>
	 * @param inputStream  Flux d'entree.
	 * @return Une Objet contenant les informations relatives a la requete.
	 */
	public static final Message buildMessage(InputStream inputStream){
		Message request = null;
		InputStream in = inputStream ;		
		if(in!=null){
			
			Scanner scan = new Scanner(in);
			String line =null;
			while(scan.hasNext() && (line=scan.nextLine()).compareTo(HTTP.CRLF)==0);
			if(line!=null){
				request = new Message();
				request.setBeginingLine(line);				
				Map<String,String> header = new TreeMap<String,String>();
				boolean bool = scan.hasNextLine()&& (line=scan.nextLine()).compareTo(HTTP.CRLF)!=0 && line!=null && line.length()>0;
				while(bool){					
					int pos = line.indexOf(':');
					if(pos==-1) return null;
					String key = line.substring(0,pos).trim().toLowerCase();
					String value = line.substring(pos+1).trim().toLowerCase();
					header.put(key, value);
					bool = scan.hasNextLine()&& (line=scan.nextLine()).compareTo(HTTP.CRLF)!=0 && line!=null && line.length()>0;					
				}
				if(header.size()>0){
					request.setHeader(header);
					request.setMINEType(header.get(HTTP.CONTENT_TYPE_HEADER.toLowerCase()));					
				}
				
				//if(request.getMINEType()==null) request.setMINEType(MIMEConstant.MIMETYPE_TXTPLAIN_VALUE);
				
				if(request.getBeginingLine().toLowerCase().startsWith(HTTP.PUT_METHOD.toLowerCase())){
					
					try {
						long size = 0 ;
						String type="";
						if(header!=null){
							String length = header.get(HTTP.CONTENT_LENGTH_HEADER.toLowerCase());
							//System.out.println("content-length = "+length);
							if(length!=null) size = Long.parseLong(length);
							line = header.get(HTTP.CONTENT_TYPE_HEADER.toLowerCase());
							//System.out.println("type "+line);
							if(line!=null){
								int pos = line.indexOf('/');
								if(pos!=-1){
									type = line.substring(0,pos);
									//System.out.println(" type = "+type);
								}
							}
						}
						
						//System.out.println("Taille du fichier a lire "+size);
						
						if(type.compareToIgnoreCase("text")==0){
							line = "";
							String data = "";
							while(scan.hasNextLine()){
								line=scan.nextLine();
								size-= line.length();
								if(line.endsWith(HTTP.CRLF)) break;
								data+=line;
								if(size<=2) break;
							}
							if(line.endsWith(HTTP.CRLF)) data+=line.substring(0,line.indexOf(HTTP.CRLF));
							else data=line;							
							if(data.compareToIgnoreCase("")!=0)
								request.setData(Util.createInputStream(data));
						}else{							
							File file = new File(Configuration.CACHE_DIRECTORY+"temp"+Util.getToday());
							file.createNewFile();
							
							DataInputStream data = new DataInputStream(in);
							DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
							byte [] buff = new byte[Util.SIZE_BUFFER];
							int read = data.read(buff,0,Util.SIZE_BUFFER);
							while(read>=0 && size>0){
								//System.out.println("Size = "+size+" read = "+read);
								size-=read;
								out.write(buff, 0, read);
								if(size>0) read = data.read(buff, 0, Util.SIZE_BUFFER);
							}
							out.flush();
							out.close();
							//System.out.println("creation file");
							request.setData(Util.createInputStream(file));						
							//if(request.getData()!=null) request.getData().close();
							//System.out.println("Size = "+size);
						}						
					} catch (IOException e) {
						Util.writeError("Util.buildMessage :: Erreur lors du traitement d'une requete par le Serveur en mode MultiThread");
						e.printStackTrace();
						return null;
					}													
				}
			}			
		}
		return request;
	}
	
	
	/**
	 * <p> Construit une requete a partir du flux d'entree de type canal.</p>
	 * @param inputStream  Flux d'entree represente par un canal.
	 * @return Une Objet contenant les informations relatives a la requete.
	 */
	public static final Message buildMessage(SocketChannel inputStream){
		Message request = null;
		SocketChannel in = inputStream ;		
		if(in!=null){
			
			Scanner scan = new Scanner(in);
			String line =null;
			while(scan.hasNext() && (line=scan.nextLine()).compareTo(HTTP.CRLF)==0);
			if(line!=null){
				request = new Message();
				request.setBeginingLine(line);				
				Map<String,String> header = new TreeMap<String,String>();
				boolean bool = scan.hasNextLine()&& (line=scan.nextLine()).compareTo(HTTP.CRLF)!=0 && line!=null && line.length()>0;
				while(bool){					
					int pos = line.indexOf(':');
					if(pos==-1) return null;
					String key = line.substring(0,pos).trim().toLowerCase();
					String value = line.substring(pos+1).trim().toLowerCase();
					header.put(key, value);
					bool = scan.hasNextLine()&& (line=scan.nextLine()).compareTo(HTTP.CRLF)!=0 && line!=null && line.length()>0;					
				}
				if(header.size()>0){
					request.setHeader(header);
					request.setMINEType(header.get(HTTP.CONTENT_TYPE_HEADER.toLowerCase()));					
				}
				
				//if(request.getMINEType()==null) request.setMINEType(MIMEConstant.MIMETYPE_TXTPLAIN_VALUE);
				
				if(request.getBeginingLine().toLowerCase().startsWith(HTTP.PUT_METHOD.toLowerCase())){
					
					try {
						long size = 0 ;
						String type="";
						if(header!=null){
							String length = header.get(HTTP.CONTENT_LENGTH_HEADER.toLowerCase());
							//System.out.println("content-length = "+length);
							if(length!=null) size = Long.parseLong(length);
							line = header.get(HTTP.CONTENT_TYPE_HEADER.toLowerCase());
							//System.out.println("type "+line);
							if(line!=null){
								int pos = line.indexOf('/');
								if(pos!=-1){
									type = line.substring(0,pos);
									//System.out.println(" type = "+type);
								}
							}
						}
						
						//System.out.println("Taille du fichier a lire "+size);
						
						if(type.compareToIgnoreCase("text")==0){
							line = "";
							String data = "";
							while(scan.hasNextLine()){
								line=scan.nextLine();
								size-= line.length();
								if(line.endsWith(HTTP.CRLF)) break;
								data+=line;
								if(size<=2) break;
							}
							if(line.endsWith(HTTP.CRLF)) data+=line.substring(0,line.indexOf(HTTP.CRLF));
							else data=line;							
							if(data.compareToIgnoreCase("")!=0)
								request.setData(Util.createInputStream(data));
						}else{							
							File file = new File(Configuration.CACHE_DIRECTORY+"temp"+Util.getToday());
							file.createNewFile();
							
							DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
							
							ByteBuffer buffer = ByteBuffer.allocate(Util.SIZE_BUFFER);
							int read = in.read(buffer);
							while(read>=0 && size>0){
								//System.out.println("Size = "+size+" read = "+read);
								size-=read;
								buffer.flip();
								out.write(buffer.array());
								buffer.clear();
								if(size>0) read = in.read(buffer);
							}							
							out.flush();
							out.close();
							//System.out.println("creation file");
							request.setData(Util.createInputStream(file));
							//System.out.println("Size = "+size);
						}						
					} catch (IOException e) {
						Util.writeError("Util.buildMessage :: Erreur lors du traitement d'une requete par le serveur en mode canaux");
						e.printStackTrace();
						return null;
					}								
				}
			}			
		}
		return request;
	}
	
	/**
	 * <p>Traite une requete</p>
	 * @param request Requete a traiter.
	 * @return Un objet representant la reponse de la requete.
	 */
	public static final Message processMessage(Message request){
		Message response ;
		Message badRequest = new Message(Util.buildBeginingLine(HTTP.BAD_REQUEST_STATUS),MIMEType.HTML_VALUE,
				 Util.buildHTMLPage(HTTP.BAD_REQUEST_STATUS,HTTP.BAD_REQUEST_STATUS.substring(4))); 
	
		if(request!=null){
			
			response = new Message();
			Scanner scan = new Scanner(request.getBeginingLine());
						
			if(scan.hasNext()){				
				String method = scan.next();
				
				if(Util.isAllowedMethod(method)){
					if(scan.hasNext()){						
						
						String link = scan.next();
						link = link.substring(1);
						
						URI url=null;
						try {
							url = new URI(link);
						} catch (URISyntaxException e) {
							return badRequest;
						}						
						
						if(method.compareToIgnoreCase(HTTP.GET_METHOD)==0)
							response = processGETRequest(request,url);
						else if(method.compareToIgnoreCase(HTTP.HEAD_METHOD)==0)
							response = processHEADRequest(request,url);
						else if(method.compareToIgnoreCase(HTTP.PUT_METHOD)==0)
							response = processPUTRequest(request,url);
						else if(method.compareToIgnoreCase(HTTP.DELETE_METHOD)==0)
							response = processDELETERequest(request,url);			
						
					}else response =badRequest;
				}else response = new Message(Util.buildBeginingLine(HTTP.UNAUTHORIZED_STATUS),MIMEType.HTML_VALUE,
						 Util.buildHTMLPage(HTTP.UNAUTHORIZED_STATUS,HTTP.UNAUTHORIZED_STATUS.substring(4)));
				
			}else response = badRequest ;
			
		}else response = badRequest ; 
		
		return response;
	}
	
	
	private static final Message processGETRequest(Message request,URI url) {
		Message badRequest = new Message(Util.buildBeginingLine(HTTP.BAD_REQUEST_STATUS),MIMEType.HTML_VALUE,
				 Util.buildHTMLPage(HTTP.BAD_REQUEST_STATUS,HTTP.BAD_REQUEST_STATUS.substring(4)));		
		if(request==null || url==null) return badRequest;				
		
		String link = Util.webFolder+url.getPath();		
		File file = new File(link);
		if(!file.exists()) return new Message(Util.buildBeginingLine(HTTP.NOT_FOUND_STATUS),MIMEType.HTML_VALUE,
				 Util.buildHTMLPage(HTTP.NOT_FOUND_STATUS,HTTP.NOT_FOUND_STATUS.substring(4)));
		
		if(file.isDirectory()){			
			if(!Util.containsFile(link, "index.htm") && !Util.containsFile(link, "index.html") ){				
				return new Message(Util.buildBeginingLine(HTTP.SUCCESS_STATUS),MIMEType.HTML_VALUE,
									Util.buildListingHTMLPage(link));
			}else{
				if(!link.endsWith("/")) link+="/";
				if(Util.containsFile(link, "index.htm")) file = new File(link+"index.htm");
				else file = new File(link+"index.html");
			}
		}
		
		String mime =null;
		Map<String,String> list = getListdMineType();
		try {
			int index = file.getCanonicalPath().lastIndexOf('.');
			if(index>=0) mime = list.get(file.getCanonicalPath().substring(index+1).toLowerCase());
		} catch (IOException e) {
			return new Message(Util.buildBeginingLine(HTTP.INTERNAL_ERROR_STATUS),MIMEType.HTML_VALUE,
					 Util.buildHTMLPage(HTTP.INTERNAL_ERROR_STATUS,HTTP.INTERNAL_ERROR_STATUS.substring(4)));
		}
		//if(mime==null) mime = "application/octet-stream" ;
		Message response = new Message();
		try {
			response.setData(Util.createInputStream(file));
		} catch (FileNotFoundException e) {
			return new Message(Util.buildBeginingLine(HTTP.INTERNAL_ERROR_STATUS),MIMEType.HTML_VALUE,
					 Util.buildHTMLPage(HTTP.INTERNAL_ERROR_STATUS,HTTP.INTERNAL_ERROR_STATUS.substring(4)));
		}
		response.setBeginingLine(Util.buildBeginingLine(HTTP.SUCCESS_STATUS));
		response.setMINEType(mime);
		response.addHeader(HTTP.ALLOW_HEADER,HTTP.GET_METHOD+", "+HTTP.PUT_METHOD+", "+
				HTTP.HEAD_METHOD+", "+HTTP.DELETE_METHOD);
		response.addHeader("Date", Util.today());
		response.addHeader(HTTP.SERVER_HEADER, Configuration.SERVER_VERSION);
		if(response.getMINEType()!=null) response.addHeader(HTTP.CONTENT_TYPE_HEADER, mime);
		response.addHeader(HTTP.CONTENT_LENGTH_HEADER, ""+file.length());		
		
		return response ;
	}
	
	
	private static final Message processPUTRequest(Message request,URI url) {
		Message badRequest = new Message(Util.buildBeginingLine(HTTP.BAD_REQUEST_STATUS),MIMEType.HTML_VALUE,
				 Util.buildHTMLPage(HTTP.BAD_REQUEST_STATUS,HTTP.BAD_REQUEST_STATUS.substring(4)));
		if(request==null || url==null) return badRequest;		
		
		String link = Util.webFolder+url.getPath();
		boolean exists = new File(link).isFile();
		try {
			
			//Sauvegarde de l'ancienne version.
			if(exists){
				File file = new File(link);
				String path = Configuration.CACHE_DIRECTORY;
				 if(Util.answer){
					 int pos = file.getPath().indexOf(Util.webFolder);
					 String parent = "" ;
					 if(pos!=-1){
						 parent = file.getPath().substring(pos+Util.webFolder.length());
					 }
					 path += "Cache "+Util.getToday()+"/"+parent;
					 File directory = new File(path);
					 if(!directory.exists())directory.mkdirs();
					 path+="/";
				 }
				 Util.copyFile(file,path);
			}
			
			//Creation du  Nouveau fichier.
			DataOutputStream out = new DataOutputStream(new FileOutputStream(link));
			if(request.getData()!=null){				
				InputStream in = request.getData();
				byte[] buff = new byte[Util.SIZE_BUFFER];
				while (true)
				{
					int read = in.read( buff, 0, Util.SIZE_BUFFER );
					if (read <= 0)
						break;
					out.write( buff, 0, read );
				}					
				out.flush();				
			}			
			out.close();
			
			/*if(exists && request.getData()==null)				
				return new Message(Util.buildBeginingLine(HTTP.NOT_CONTENT_STATUS),MIMEType.HTML_VALUE,null);*/
			
			if(exists)				
				return new Message(Util.buildBeginingLine(HTTP.SUCCESS_STATUS),MIMEType.HTML_VALUE,
						Util.buildHTMLPage(HTTP.SUCCESS_STATUS,HTTP.SUCCESS_STATUS.substring(4)));
			
			return new Message(Util.buildBeginingLine(HTTP.CREATED_STATUS),MIMEType.HTML_VALUE,
					Util.buildHTMLPage(HTTP.CREATED_STATUS,HTTP.CREATED_STATUS.substring(4)));
			
		} catch (IOException e) {
			 return new Message(Util.buildBeginingLine(HTTP.INTERNAL_ERROR_STATUS),MIMEType.HTML_VALUE,
					 Util.buildHTMLPage(HTTP.INTERNAL_ERROR_STATUS,HTTP.INTERNAL_ERROR_STATUS.substring(4)));
		}		
	}	
	
	private static final Message processDELETERequest(Message request,URI url) {
		Message badRequest = new Message(Util.buildBeginingLine(HTTP.BAD_REQUEST_STATUS),MIMEType.HTML_VALUE,
				 Util.buildHTMLPage(HTTP.BAD_REQUEST_STATUS,HTTP.BAD_REQUEST_STATUS.substring(4)));
		if(request==null || url==null) return badRequest;
		String link = Util.webFolder+url.getPath();
		File file = new File(link);		
		if(file.exists()){
			try {
				String path = Configuration.CACHE_DIRECTORY;
				 if(Util.answer){
					 int pos = file.getPath().indexOf(Util.webFolder);
					 String parent = "" ;
					 if(pos!=-1){
						 parent = file.getPath().substring(pos+Util.webFolder.length());
					 }
					 path += "Cache "+Util.getToday()+"/"+parent;
					 File directory = new File(path);
					 if(!directory.exists())directory.mkdirs();
					 path+="/";
				 }
				 Util.move(file,path);
				 File webroot = new File(Util.webFolder);
				 if(!webroot.exists()) webroot.mkdirs();
			} catch (IOException e) {
				return new Message(Util.buildBeginingLine(HTTP.NOT_FOUND_STATUS),MIMEType.HTML_VALUE,
						Util.buildHTMLPage(HTTP.NOT_FOUND_STATUS,HTTP.NOT_FOUND_STATUS.substring(4)));
			} 
			return new Message(Util.buildBeginingLine(HTTP.ACCEPTED_STATUS),MIMEType.HTML_VALUE,
					Util.buildHTMLPage(HTTP.ACCEPTED_STATUS,file+" a ete supprime."));
		}
		
		return new Message(Util.buildBeginingLine(HTTP.NOT_FOUND_STATUS),MIMEType.HTML_VALUE,
				Util.buildHTMLPage(HTTP.NOT_FOUND_STATUS,HTTP.NOT_FOUND_STATUS.substring(4)));
	}
	
	private static final Message processHEADRequest(Message request,URI url){
		Message badRequest = new Message(Util.buildBeginingLine(HTTP.BAD_REQUEST_STATUS),MIMEType.HTML_VALUE,
				 Util.buildHTMLPage(HTTP.BAD_REQUEST_STATUS,HTTP.BAD_REQUEST_STATUS.substring(4)));
		if(request==null || url==null) return badRequest;		
		
		String link = Util.webFolder+url.getPath();
		String mime = null;
		long size = 0 ;
		File file = new File(link);
		if(file.exists()){
			if(file.isFile()){
				size = file.length() ;
				Map<String,String> list = getListdMineType();
				try {
					int index = file.getCanonicalPath().lastIndexOf('.');
					if(index>=0) mime = list.get(file.getCanonicalPath().substring(index+1).toLowerCase());
				} catch (IOException e) {
					return new Message(Util.buildBeginingLine(HTTP.INTERNAL_ERROR_STATUS),MIMEType.HTML_VALUE,
							 Util.buildHTMLPage(HTTP.INTERNAL_ERROR_STATUS,HTTP.INTERNAL_ERROR_STATUS.substring(4)));
				}
			}else {
				size = Util.buildListingHTMLPage(link).length();
				mime = MIMEType.HTML_VALUE ;
			}
		}
		Message response = new Message();
		response.setBeginingLine(Util.buildBeginingLine(HTTP.SUCCESS_STATUS));
		response.setMINEType(mime);
		response.addHeader("Date", Util.today());
		response.addHeader(HTTP.SERVER_HEADER, Configuration.SERVER_VERSION);
		if(response.getMINEType()!=null) response.addHeader(HTTP.CONTENT_TYPE_HEADER, mime);
		response.addHeader(HTTP.CONTENT_LENGTH_HEADER, ""+size);		
				
		return response;
	}
	
	/**
	 * <p>
	 * 	Copie un fichier vers une destination, en le renomant comme suit :
	 * 	Cache+date&heure+nom du fichier originel.	 
	 * </p>
	 * @param file Fichier a copie.
	 * @param path Destination de la copie.
	 * @throws IOException 
	 */
	public static final void copyFile(File file,String path) throws IOException{
		if(file!=null && file.isFile()){
			
			String newName = file.getName();			
			String separator="";
			if(path!=null &&!path.endsWith("/")) separator+="/";			
			if(path!=null && !path.isEmpty())newName=path+separator+newName ;
			else newName = Configuration.CACHE_DIRECTORY+newName ;
						
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			DataOutputStream out = new DataOutputStream(new FileOutputStream(newName));
			
			byte[] buff = new byte[Util.SIZE_BUFFER];
			while (true)
			{
				int read = in.read( buff, 0, Util.SIZE_BUFFER );
				if (read <= 0)
					break;
				out.write( buff, 0, read );
			}					
			out.flush();
			out.close();
			in.close();
		}
	}
	
	/**
	 * <p>
	 * 	 Copie un dossier folder dans un emplacement path. Le fichier est renome 
	 *   comme suit cache+datedecopie+nom originel du dossier.
	 * </p>
	 * @param folder Dossier a copier.
	 * @param path Chemin ou copier le dossier.
	 * @throws IOException 
	 */
	public static final void copyFolder(File folder,String path) throws IOException{
		if(folder!=null && folder.isDirectory()){
			
			String newName = folder.getName();			
			String separator="";
			if(path!=null &&!path.endsWith("/")) separator+="/";			
			if(path!=null && !path.isEmpty()) newName=path+separator+newName ;
			else newName = Configuration.CACHE_DIRECTORY+newName ;
			
			File directory = new File(newName);
			directory.mkdirs();
			if(directory.exists()){
				for(File file: folder.listFiles()){
					if(file.isDirectory()) copyFolder(file,newName);
					else Util.copyFile(file, newName);						
				}
									
			}			
		}
	}
	
	/**
	 * <p> Efface un dossier</p>
	 * @param folder Dossier a effacer.
	 */
	public static final void delete(File folder) {		
		if(folder!=null && folder.isDirectory()){			
			for(File  file: folder.listFiles()) {				
				if(file.isDirectory()) delete(file);
				else file.delete();
			}			
			folder.delete();
		}
	}
	
	/**
	 * <p>Deplace le fichier passe en parametre</p>
	 * @param file Dossier ou fichier a deplacer.
	 * @param path Chemin ou deplacer le fichier ou le dossier.
	 * @throws IOException Erreur lors du deplacement. 
	 */
	public static final void move(File file,String path) throws IOException{
		if(file!=null && file.exists()){
			
			String pathFile = path;			
			if(path==null || path.length()==0) pathFile = Configuration.CACHE_DIRECTORY ;
			
			if(file.isFile()){				
				Util.copyFile(file,pathFile);
				file.delete();
			}else{
				Util.copyFolder(file,pathFile);
				Util.delete(file);
			}
		}
	}
	
	/**
	 * <p>Construit la premiere ligne d'un message compte tenu de la methode.</p>
	 * @param status Code de statut de la ligne statut.
	 * @return Une chaine de caractere representant la ligne statut.
	 */
	public static final String buildBeginingLine(String status) {
		return HTTP.HTTP_VERSION+" "+status;
	}
	
	/**
	 * <p>Construit une chaine de caractere representant les entetes.</p>
	 * @param header Entete a donner sous forme de chaine de caractere.
	 * @return Chaine de caractere representant le parametre.
	 */
	public static final String buildHeader(Map<String,String> header){
		String result = "";
		if(header!=null){
			for(String key:header.keySet()){
				result+=key+": "+header.get(key)+HTTP.CRLF;
			}
		}
		return result+HTTP.CRLF;
	}
		
	/**
	 * <p>Construit un Flux de donnees a partir d'un flux d'octet</p>
	 * @param flux Flux d'octet.
	 * @return Un flux de donnees.
	 */
	/*public static final InputStream buildData(byte[] flux) {		
		return new ByteArrayInputStream(flux) ;
	}*/
	
	/**
	 * <p> Dit si le fichier file a pour chemin path</p>
	 * @param path Chemin du repertoire contenant file.
	 * @param file Nom du fichier dont on doit verifier l'existence.
	 * @return True si le fichier set dans le dossier designe par path.
	 */
	public static final boolean containsFile(String path,String file){		
		if(new File(path+File.separator+file).isFile()) return true;
		return false;
	}
	
	
	
	/**
	 * <p>
	 * 	Construit une page HTML representant le contenu d'un dossier.
	 *  Exemple d'utilisation : si on a http://127.0.0.1/example/ alors on aura
	 *  path = example et url=http://127.0.0.1/
	 * </p>	
	 * @param path Chemin d'acces au repertoire.
	 * @return chaine representant la page web.
	 */
	public static final String buildListingHTMLPage(String path) {
		String listing = null ;
		String[] list = new File(path).list();
		if(list!=null){
			listing="<ul>";
			for(String file:list){
				String info = " &nbsp;<font size=3>( Folder )</font>",separator="/";
				File f = new File(path+File.separator+file) ;
				if(f.isFile()){					
					long len = f.length();
					info = " &nbsp;<font size=3>( ";
					if ( len < 1024 )
						info += f.length() + " bytes";
					else if ( len < 1024 * 1024 )
						info += f.length()/1024 + "." + (f.length()%1024/10%100) + " KB";
					else
						info += f.length()/(1024*1024) + "." + f.length()%(1024*1024)/10%100 + " MB";

					info += " )</font>";
					//String size = String.format("\t%10d octets\n",f.length());
					//info = "\n( File size = "+size+" )";				
					separator= "";
				}
				String fichier = file+info;
				listing+="<li><a href=\""+file+separator+"\">"+fichier+"</a></li>";
			}
			listing+="</ul>";
		}else listing = "Dossier Vide";
		return Util.buildHTMLPage("Directory Listing",listing);
	}
			
	/**
	 * <p>Envoie un message au travers d'un flux de sortie.</p>
	 * @param message Message a envoyer.
	 * @param out Flux de sortie. 
	 */
	public static final void sendMessage(Message message,OutputStream out){
		if(message!=null && out!=null) {
			PrintWriter printer = new PrintWriter( out );
			printer.print(message.getBeginingLine()+" ");
			printer.print(Util.buildHeader(message.getHeaderList()));			
			printer.flush();
			try {
				if(message.getData()!=null){
					InputStream data = message.getData() ; 
					byte[] buff = new byte[Util.SIZE_BUFFER];
					while (true)
					{
						int read = data.read( buff, 0, Util.SIZE_BUFFER );
						if (read <= 0)
							break;
						out.write( buff, 0, read );
					}
				}
				out.flush();				
				out.close();
				if ( message.getData() != null )
					message.getData().close();
			} catch (IOException e) {
				Util.writeError("Util.sendMessage :: Erreur lors de l'envoie d'un message avec le serveur en mode multithread");
				e.printStackTrace();
			}
			
		}
	}	
	
	/**
	 * <p>Envoie un message au travers d'un flux de sortie de type canal.</p>
	 * @param message Message a envoyer.
	 * @param out Flux de sortie. 
	 */
	public static final void sendMessage(Message message,SocketChannel out){
		if(message!=null && out!=null) {
			
			try {
				
				Charset charset = Charset.forName(Util.CHARSET);
				CharsetEncoder encoder = charset.newEncoder();
				
				out.write(encoder.encode(CharBuffer.wrap(message.getBeginingLine()+" ")));
				out.write(encoder.encode(CharBuffer.wrap(Util.buildHeader(message.getHeaderList()))));
				
				if(message.getData()!=null){
					InputStream data = message.getData() ; 
					byte[] buff = new byte[Util.SIZE_BUFFER];
					while (true)
					{
						int read = data.read( buff, 0, Util.SIZE_BUFFER );
						if (read <= 0)	break;
						out.write( ByteBuffer.wrap(buff,0, read));
					}
				}				
				out.close();
				if ( message.getData() != null )
					message.getData().close();
			} catch (IOException e) {Util.writeError("Util.sendMessage :: Erreur lors de l'envoie d'un message avec le serveur en mode canaux "); e.printStackTrace();}
			
		}
	}	
}
