package answers;

/**
 * Classe per una singola risposta
 * 
 * @author Guizzetti Alessio
 */
public class Answer{

	private String text;
	private boolean isCorrect = false;
	
	/**
	 * Costruttore della classe, prende il testo della risposta, se è corretta e l'id della risposta.
	 * 
	 * @param text
	 * @param isCorrect
	 * @param id
	 */
	public Answer(String text, boolean isCorrect) {
		this.text = text;
		this.isCorrect = isCorrect;
	}
	
	/**
	 * Ritorna il testo della risposta.
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Imposta il testo della risposta.
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Ritorna true se la risposta è corretta, false se non lo è
	 */
	public boolean isCorrect() {
		return isCorrect;
	}
	
	@Deprecated
	public void output() {
		System.out.println();
		System.out.println("RISPOSTA: "+getText());
		System.out.println("CORRETTA: "+isCorrect());
		System.out.println();
	}

}
