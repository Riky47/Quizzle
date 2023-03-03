package main;

import java.util.LinkedList;

import answers.Answer;
import questions.Question;
import subjects.Subjects;

public class UserData {
	private int userId;
	private int subjectId;
	private int questionId;
	private int totalPoints;
	private String username;
	
	private LinkedList<Question> questions = new LinkedList<Question>();
	private LinkedList<LinkedList<Answer>> correctAnswers = new LinkedList<LinkedList<Answer>>();
	
	// Constructor
	public UserData(int id, String name, int numberOfQuestions) {
		totalPoints = 0;
		this.setUserId(id);
		this.setUsername(name);
		this.setCurrentSubjectId(0);
		this.setCurrentQuestionId(0);
		
		for(int i=0; i<numberOfQuestions; i++)
		{
			// Load Questions from global list
			LinkedList<Answer> answersList = new LinkedList<Answer>();
			answersList.add(new Answer("No", true));
			answersList.add(new Answer("Si", false));
			
			Question question = new Question("Domanda", 100, answersList, Subjects.ITALIAN);
			questions.add(question);
			
			// Load correctAnswers
			correctAnswers.add(new LinkedList<Answer>());
		}
	}
	
	// Sets
	public void setUserId(int id) {
		userId = id;
	}
	public void setCurrentSubjectId(int id) {
		subjectId = id;
	}
	public void setCurrentQuestionId(int id) {
		if (id < 0) id = 0;
		else if (id > questions.size()) id = questions.size();
	}
	public void setUsername(String name) {
		username = name;
	}
	public void incrementPoints(int points) {
		totalPoints += points;
	}
	public void decrementPoints(int points) {
		totalPoints -= points;
	}
	
	public void setCorrectAnswer(int questId, Answer answer) {
		correctAnswers.get(questId -1).add(answer);
	}
	
	// Gets
	public final int getUserId() {
		return userId;
	}
	public final int getCurrentSubjectId() {
		return subjectId;
	}
	public final int getCurrentQuestionId() {
		return questionId;
	}
	public final String getUsername() {
		return username;
	}
	public final int getPoints() {
		return totalPoints;
	}
	
	public final int getNextQuestionId() { // Returns 0 if out of bounds
		int result = questionId +1;
		if (result > questions.size() || result < 0) {
			result = 0;
		}
		
		return result;
	}
	public final Question getQuestionAt(int index) {
		return questions.get(index);
	}
}
