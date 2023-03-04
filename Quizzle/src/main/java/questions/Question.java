package questions;

import java.util.LinkedList;

import answers.Answer;
import subjects.Subjects;

/**
 * Classe per le domande.
 * 
 * @author Guizzetti Alessio, Balducchi Riccardo
 */
public class Question{
	
	private int maxPoints = 0;
	private String text = "";
	private Subjects subject;

	public static Question NULL = new Question(null, 0, null, null);
	
	private LinkedList<Answer> answers = new LinkedList<Answer>();
	
	/**
	 * Costruttore, prende il testo della domanda, i punti massimi e una lista di risposte.
	 */
	public Question(String text, int maxPoints, LinkedList<Answer> answers, Subjects subject) {
		this.text = text;
		this.maxPoints = maxPoints;
		this.answers = answers;
		this.subject = subject;
	}
	
	/**
	 * Ritorna il numero di risposte corrette
	 */
	public int getNumberOfCorrectAnswer(){
		int c = 0;
		for(Answer answer : answers){
			if (answer.isCorrect()) c++;
		}
		return c;
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
	
	public Subjects getSubject() {
		return subject;
	}

	public void setSubject(Subjects subject) {
		this.subject = subject;
	}
	

	@Deprecated
	public void output() {
		System.out.println("DOMANDA: "+getText());
		System.out.println("PUNTI: "+getMaxPoints());
		System.out.println("MATERIA: "+getSubject());
		
		answers.forEach(t -> t.output());
	}
	
	public String toString() {
		return getText()+"["+getSubject()+";"+getMaxPoints()+"]";
	}
	
	
}
