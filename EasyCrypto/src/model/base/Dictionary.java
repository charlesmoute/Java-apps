package model.base;

import java.util.*;
import java.util.Map.Entry;
/**
 * @author Charles
 * <p> Represente le dictionnaire utilise dans LZW</p>
 */
public class Dictionary {

	/**
	 * Le dictionnaire
	 */
	private TreeMap<String,String> dico = null;
	
	
	/**
	 * Cree une instance de dictionnaire avec comme valeur initial de count le parmetre
	 */
	public Dictionary(){
		dico = new TreeMap<String,String>();		
	}
	
	/**
	 * Ajoute le mot si et seulement si le mot est non vide.
	 * @param word Mot a ajouter	
	 * @return true si le mot a ete ajoute.	 
	 */
	public boolean addWord(Word word){		
		return this.addWord(word.getCode(),word.getValue());		
	}
	
	/**
	 * <p>
	 * Ajoute un nouveau mot de code le premier parametre et de valeur de deuxieme parametre.
	 * NB: si le code existe deja la value est remplacee par celle en parametre. Si au contraire
	 * la value est deja dans le dictionnaire le mot n'est pas ajoute dans le dictionnaire.
	 * De meme si le code est vide ou que la valeur du mot est vide le mot n'est pas ajoute dans le dictionnaire.
	 * </p>
	 * @param code  Code associe au mot a ajouter
	 * @param value Valeur du mot a ajouter dans le dictionnaire
	 * @return true si le mot a ete ajoute, false dans le cas contraire. 
	 */
	public boolean addWord(String code, String value){
		if(code==null|| code.length()==0 || value==null || value.length()==0) return false;
		if(!dico.isEmpty()){
			String aux = dico.firstKey();
			if(aux.length()!=code.length()) return false;
		}
		if(dico.containsValue(value)) return false;
		dico.put(code, value);
		return true;
	}
	
	/**
	 * Efface tous les mots du dictionnaire.
	 */
	public void clear() { dico.clear();	}
	
	/**
	 * Efface le mot de code le parametre
	 * @param code Code du mot a effacer
	 * @throws Exception Erreur lance lorsque le code ne s'execute pas correctement.
	 */
	public void remove(String code) throws Exception {	dico.remove(code);	}
	
	/**
	 * Efface le parametre si il est dans le dictionnaire
	 * @param word Mot a supprimer
	 */
	public void remove(Word word){
		if(this.containsWord(word)) dico.remove(word.getCode());		
	}
	
	/**
	 * Verifie si le dictionnaire contient le code passe en parametre. 
	 * @param key Code a verifier
	 * @return true si le code est dans le dictionnaire.
	 */
	public boolean containsCode(String key){ return dico.containsKey(key);	}
	
	/**
	 * Verifie si le dictionnaire contient la valeur du mot passe en parametre. 
	 * @param value Valeur a verifier
	 * @return true si la valeur est dans le dictionnaire.
	 */
	public boolean containsValue(String value){ return dico.containsValue(value); }
	
	/**
	 * Verifie si le mot est dans le dictionnaire
	 * @param word Mot a verifier
	 * @return true si le mot est dans le dictionnaire
	 */
	public boolean containsWord(Word word){
		if(word==null) return false;		
		for(Map.Entry<String, String> entry:dico.entrySet()){
			if(entry.getValue().compareTo(word.getValue())==0 && entry.getKey().compareTo(word.getCode())==0) 
				return true;
		}
		return  false; 
	}
	
	/**
	 * Donne la valeur du mot associe au code passe en parametre.	
	 * @param code Code d'un mot
	 * @return Valeur du code si il existe.
	 */
	public String getValueOf(String code){
		if(code==null) return code;
		return dico.get(code);
	}
	
	/**
	 * Return le code associe a la valeur du mot passee en parametre.
	 * @param value Valeur d'un mot du dictionnaire
	 * @return Le code associe au parametre, si le parametre est dans le dictionnaire.
	 */
	public String getCodeOf(String value) {
		if(!dico.containsValue(value)) return null;
		for(Map.Entry<String, String> entry:dico.entrySet()){
			if(entry.getValue().compareTo(value)==0) return entry.getKey();
		}
		return null;
	}
	
	/**
	 * @return La taille, en nombre de mots, du dictionaire. 
	 */
	public int size() { return dico.size(); }
	
	/**
	 * @return true si le dictionnaire ne contient auncun element
	 */
	public boolean isEmpty() { return dico.isEmpty(); }
	
	/**
	 * @return Le premiere code du dictionnaire.
	 */
	public String firstKey() { return dico.firstKey(); }
	
	/**
	 * @return Le premier mot du dictionnaire
	 */
	public Map.Entry<String, String> firstEntry() { return dico.firstEntry(); }
	
	/**
	 * @return Le dernier mot du dictionnaire
	 */
	public String lastKey() { return dico.lastKey(); }
	
	/**
	 * @return Le dernier mot du dictionnaore
	 */
	public Map.Entry<String, String> lastEntry() { return dico.lastEntry(); }
	
	/**
	 * @return L'ensemble des mots du dictionnaire
	 */
	public Set<Entry<String,String>> wordSet() {  return dico.entrySet();	}
	
	public String toString() {
		String value = "[";
		long i = 0 ;
		for(Map.Entry<String, String> entry:dico.entrySet()){
			value += entry.getKey()+" ==> "+entry.getValue();
			if(i!=(dico.size()-1)) value+=" ," ;
			i++;
		}
		value+="]";
		return value;
	}
	
	public Dictionary clone() {
		Dictionary d = new Dictionary();
		for(Map.Entry<String, String> entry:dico.entrySet())
			d.addWord(entry.getKey(),entry.getValue());		
		return d;
	}
}
