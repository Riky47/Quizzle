package answers;

public class Answer{

	private String text;
	private boolean isCorrect = false;
	private int id = 0;
	
	public Answer(String text, boolean isCorrect, int id) {
		this.text = text;
		this.isCorrect = isCorrect;
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public boolean isCorrect() {
		return isCorrect;
	}
	
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
