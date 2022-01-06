package cm.enterprise.software.helper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.UIManager;


import cm.enterprise.software.exception.OperationFailureException;


/**
 * Helper est la classe regroupant l'ensemble des fonctions utilitaires.
 * 
 * @author Charles Mouté
 * @version 1.0.1, 24/01/2010
 */

public class Helper {
		
	/**
	 * Retourne un chargeur de resource interne
	 * @return Un chargeur de resource interne.
	 */
	public static final ClassLoader getClassLoaderForResources() {
		ClassLoader loader = (ClassLoader) UIManager.get("ClassLoader");
		if (loader == null)	loader = Thread.currentThread().getContextClassLoader();		
		return loader;
	}
	
		
	/**
	 * Ecrit les proprietes en parametres dans le fichier dont le chemin d'acces est dans file, et associe le commentaire
	 * comment a ce fichier
	 * @param properties Proprietes a stocker dans le fichier en parametre.
	 * @param file Chemin d'acces a un fichier de configuration.
	 * @param comment Commentaire a inscrire dans le fichier.
	 * @throws OperationFailureException
	 */
	public static void writeProperties(Properties properties,String file,String comment) throws OperationFailureException {
	
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			properties.store(out,comment);
		} catch (Exception e) {
			throw new OperationFailureException(e.getMessage());
		}
	}
	
	/**
	 * Lit les proprietes contenues dans le fichier de configuration de nom en parametre.
	 * @param file Chemin d'acces a un fichier de configuration interne
	 * @throws OperationFailureException
	 */
	public static Properties readInternalProperties(String file) throws OperationFailureException {
		ClassLoader loader = Helper.getClassLoaderForResources();
		java.util.Properties properties = new java.util.Properties();
		try {
			properties.load(loader.getResourceAsStream(file));
			return properties;
		} catch (IOException e) {
			throw new OperationFailureException(e.getMessage());
		}
	}
	
	/**
	 * Retourne les proprietes stockes dans le fichier dont le nom est passe en parametre
	 * @param file Chemin du fichier des proprietes a lire.
	 * @return Les proprietes stockees dans le fichier en parametre.
	 * @throws OperationFailureException
	 */
	public static Properties readProperties(String file) throws OperationFailureException {
		Properties properties ;
		try{
			properties = new Properties();
			properties.load(new FileInputStream(file));
			return properties;
		} catch (IOException e) {
			throw new OperationFailureException(e.getMessage());
		}
	}
			
	/**
	 * Ajoute ou modifie une propriete dans le fichier dont le chemin est en parametre dans file.
	 * @param key Cle de la propriete
	 * @param value Valeur de la propriete
	 * @param file Chemin d'acces au fichier de configuration.
	 * @param comment Commentaire a introduire dans le fichier
	 * @throws OperationFailureException
	 */
	public static void setProperties(String key, String value,String file,String comment) throws OperationFailureException{
		Properties properties = Helper.readProperties(file);
		properties.put(key, value);
		Helper.writeProperties(properties, file,comment);
	}
	
	/**
	 * Lit la propriete keyProperties dans le fichier interne file.
	 * @param keyProperties Nom de la propriete dont on veut obtenir la valeur.
	 * @param file Chemin d'acces d'un fichier de configuration interne.
	 * @return La valeur de keyProperties contenues dans le fichier dont le chemin d'acces est stocke dans file
	 * @throws OperationFailureException
	 */
	public static String getInternalPropertiesOfFile(String keyProperties,String file) throws OperationFailureException {
		Properties properties = Helper.readInternalProperties(file);
		return properties.getProperty(keyProperties);
	}
	
	/**
	 * Lit la propriete keyProperties dans le fichier file.
	 * @param keyProperties Nom de la propriete dont on veut obtenir la valeur.
	 * @param file Chemin d'acces d'un fichier de configuration.
	 * @return La valeur de keyProperties contenues dans le fichier dont le chemin d'acces est stocke dans file
	 * @throws OperationFailureException
	 */
	public static String getPropertiesOfFile(String keyProperties,String file) throws OperationFailureException {
		Properties properties = Helper.readProperties(file);
		return properties.getProperty(keyProperties);
	}
	
	
}
