package main;

import java.util.Iterator;
import java.util.Vector;

public class QuestionData {
	private int maxPoints = 0;
	private String question = "";
	
	private Vector<String> correctAnswers = new Vector<String>();
	private Vector<String> wrongAnswers = new Vector<String>();
	
	// Sets
	public void setMaxPoints(int maxp) {
		maxPoints = 0;
	}
	
	public void setQuestion(String quest) {
		question = quest;
	}
	
	// Gets
	public int getMaxPoints() {
		return maxPoints;
	}
	
	public String getQuestion() {
		return question;
	}
	
	// Other
	public int addCorrentAnswer(String answer) {
		correctAnswers.add(answer);
		return correctAnswers.size() -1;
	}
	
	public int addWrongAnswer(String answer) {
		wrongAnswers.add(answer);
		return wrongAnswers.size() -1;
	}
	
	public void removeCorrectAnswer(String answer) {
		Iterator<String> it = correctAnswers.iterator();
		int index = 0;
		
		while (it.hasNext()) {
			if (it.next() == answer) {
				correctAnswers.remove(index);
			}
			
			index++;
		}
	}
	
	public void removeCorrectAnswer(int index) {
		if (index < correctAnswers.size() && index >= 0) {
			correctAnswers.remove(index);
		}
	}
	
	public void removeWrongAnswer(String answer) {
		Iterator<String> it = wrongAnswers.iterator();
		int index = 0;
		
		while (it.hasNext()) {
			if (it.next() == answer) {
				wrongAnswers.remove(index);
			}
			
			index++;
		}
	}
	
	public void removeWrongAnswer(int index) {
		if (index < wrongAnswers.size() && index >= 0) {
			wrongAnswers.remove(index);
		}
	}
	
	public QuestionData(String quest, int maxp) {
		question = quest;
		maxPoints = maxp;
	}
}
