package main;

import java.util.LinkedList;

import questions.Question;

public class UserData {
	private int userId;
	private int subjectId;
	private int questionId;
	private String username;
	
	public static LinkedList<Question> answeredQuestion;
	
	// Constructor
	public UserData(int id, String name, int numberOfQuestions) {
		this.setUserId(id);
		this.setUsername(name);
		this.setCurrentSubjectId(0);
		this.setCurrentQuestionId(0);
		this.answeredQuestion = new LinkedList<Question>();
		for(int i=0; i<numberOfQuestions; i++)
		{
			// Load Questions
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
		questionId = id;
	}
	public void setUsername(String name) {
		username = name;
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
	
	public final int getNextQuestionId() {
		return questionId + 1; 
	}
	
}
