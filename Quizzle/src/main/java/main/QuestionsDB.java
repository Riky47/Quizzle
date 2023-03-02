package main;

import java.util.Vector;

public class QuestionsDB {
	private Vector<QuestionData> questionsList = new Vector<QuestionData>();
	
	private void addQuestion(QuestionData data) {
		questionsList.add(data);
	}
	
	private boolean loadQuestions() {
		
		return true;
	}
	
	private boolean saveQuestions() {
		
		return true;
	}
}
