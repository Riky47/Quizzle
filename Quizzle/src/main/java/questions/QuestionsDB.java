package questions;

import java.util.Vector;

public class QuestionsDB {
	private Vector<Question> questionsList = new Vector<Question>();
	
	private void addQuestion(Question data) {
		questionsList.add(data);
	}
	
	private boolean loadQuestions() {
		
		return true;
	}
	
	private boolean saveQuestions() {
		
		return true;
	}
}
