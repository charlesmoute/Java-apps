package helper;

import java.io.*;
import java.util.*;

public class Util {
	
	/**
	 * <p> Taille du Buffer = 2048</p>
	 */
	public static final int SIZE_BUFFER = 2048 ;
	
	/**
	 * <p>
	 * 	Copie un fichier vers une destination, en le renomant comme suit :
	 * 	Cache+date&heure+nom du fichier originel.	 
	 * </p>
	 * @param file Fichier a copie.
	 * @param path Destination de la copie.
	 * @throws IOException 
	 */
	public static final File copyFile(File file,String path) throws IOException{
		if(file!=null && file.isFile()){
			
			File cache = new File(Configuration.CACHE_DIRECTORY);
			if(!cache.exists()) cache.mkdir();
			
			String newName = "temp"+Util.getToday();//file.getName();			
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
			return new File(newName);
		}
		return null;
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

}
