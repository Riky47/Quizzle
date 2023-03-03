package questions;

import java.util.LinkedList;
import answers.Answer;

/**
 * Classe per le domande.
 * 
 * @author Guizzetti Alessio, Balducchi Riccardo
 */
public class Question{
	
	private int maxPoints = 0;
	private String text = "";
	
	private LinkedList<Answer> answers = new LinkedList<Answer>();
	
	/**
	 * Costruttore, prende il testo della domanda, i punti massimi e una lista di risposte.
	 */
	public Question(String text, int maxPoints, LinkedList<Answer> answers) {
		this.text = text;
		this.maxPoints = maxPoints;
		this.answers = answers;
	}
	
	/**
	 * Ritorna il testo della domanda.
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Imposta il testo della domanda.
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Ritorna i punti della domanda.
	 */
	public int getMaxPoints() {
		return maxPoints;
	}
	
	public LinkedList<Answer> getAnswers(){
		return answers;
	}
	
	/**
	 * Aggiunge una risposta alla domanda.
	 */
	public void addAnswer(Answer answer) {
		answers.add(answer);
	}
	
	@Deprecated
	public void output() {
		System.out.println("DOMANDA: "+getText());
		System.out.println("PUNTI: "+getMaxPoints());
		
		answers.forEach(t -> t.output());
	}
}
