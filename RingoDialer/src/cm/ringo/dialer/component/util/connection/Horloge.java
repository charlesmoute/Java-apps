package cm.ringo.dialer.component.util.connection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * Represente une horloge avec un ensemble de taches a executer, apres un temps et 
 * ce de maniere repetitive ou unique.
 * 
 * @author Charles Moute
 * @version 1.0.1, 5/7/2010
 */
public class Horloge  implements ActionListener {

	/**
	 * timer est la personnification virtuelle du temps s'ecoulant.
	 */
	private Timer timer ;
	
	/** 
	 * Champ representant l'ecoulement du temps
	 */
	private int hour, min, second ;
	
	/**
	 * Cree une instance vide de l'horloge.
	 * L'horloge ainsi creee, fait juste le decompte du temps qui s'effrite.
	 */
	public Horloge(){
		this(1000,null);
	}
	
	/**
	 * Cree une instance d'Horloge evoluant avec le delai en parametre
	 * @param delay Temps de l'unite de temps en milliseconde
	 */
	public Horloge(int delay){
		this(delay,null);
	}
	
	/**
	 * Horloge ayant comme unite de temps la seconde, soit un delai de 1000, et répetant
	 * la tache en parametre toutes les secondes
	 * @param task Tache a executer toutes les secondes.
	 */
	public Horloge(ActionListener task){
		this(1000,task);
	}
	
	/**
	 * Construit une instance de l'horloge avec une tache initiale en parametre.
	 * 
	 * @param delay Delai d'execution de la tache en milliseconde
	 * @param task Tache a executer toutes les delay milliseconde
	 */
	public Horloge(int delay,ActionListener task){
		timer = new Timer(delay,task);
		timer.addActionListener(this);
	}
	
	/**
	 * Reinitialise les parametres du temps
	 */
	private void initTimerParam(){
		hour = 0 ;
		min = 0 ;
		second = 0 ;
	}
	/**
	 * Affecte un nouveau delai d'execution d'une tache.
	 * @param delay Delai d'excution de la tache
	 */
	public void setDelay(int delay){
		timer.setDelay(delay);
	}
	
	/**
	 * Lance l'horloge
	 */
	public void start(){
		initTimerParam();
		timer.start();
	}
	
	/**
	 * Arrete l'horloge
	 */
	public void stop(){
		timer.stop();
	}
	
	/**
	 * Relance l'horloge.
	 */
	public void restart(){
		initTimerParam();
		timer.restart();
	}
	
	/**
	 * Permet de savoir si l'horloge est en cour d'execution
	 * @return true si l'horloge est en cour d'execution
	 */
	public boolean isRunning(){
		return timer.isRunning();
	}
	
	/**
	 * Permet de savoir si les taches sont repetees, apres le delay, ou si elles sont executees une seule fois.
	 * @param flag true si vous souhaitez que les taches soit repetees.
	 */
	public void setRepeats(boolean flag){
		timer.setRepeats(flag);
	}
	
	/**
	 * Permet de savoir si l'horloge repete les taches qui lui sont associees
	 * @return true si l'horloge repete les taches  qui lui sont associees.
	 */
	public boolean isRepeats(){
		return timer.isRepeats();
	}
	
	/**
	 * Associe une nouvelle tache a l'horloge.
	 * @param task Tache a ajoute au taches de l'horloge.
	 */
	public void addTask(ActionListener task){
		if(task==null) return ;
		timer.addActionListener(task);
	}
	
	/**
	 * Efface la tache en parametre de la liste des taches de l'horloge.
	 * @param task Tache a effacer de la liste des taches 
	 */
	public void removeTask(ActionListener task){
		if(task==null) return ;
		timer.removeActionListener(task);
	}
	
	/**
	 * Permet d'obtenir le temps de l'horloge.
	 * @return le temps de l'horloge selon votre delai.
	 */
	public String getTime(){
		String time = "";
		time = (hour<=9)? "0"+hour:""+hour;
		time+=":";
		time+= (min<=9)? "0"+min:""+min;
		time+=":";
		time+= (second<=9)? "0"+second:""+second;
		return time;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(second<59) second++;
		else{
			second = 0 ;
			if(min<59) min++;
			else{
				min = 0 ;
				hour++;
			}
		}		
	}
}
