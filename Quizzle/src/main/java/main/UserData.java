package main;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import answers.Answer;
import questions.Question;
import subjects.Subjects;

public class UserData {
	private int userId;
	private int subjectId;
	private int questionId;
	private int totalPoints;
	private int numOfQuests;
	private String username;
	
	private LinkedList<Question> questions = new LinkedList<Question>();
	private LinkedList<LinkedList<Answer>> allAnswers = new LinkedList<LinkedList<Answer>>();
	
	// Constructor
	public UserData(int id, String name, int numberOfQuestions) {
		userId = id;
		subjectId = 0;
		questionId = 1;
		totalPoints = 0;
		username = name;
		numOfQuests = numberOfQuestions;
		
		loadQuestions();
		for(int i=0; i<numOfQuests; i++) {
			allAnswers.add(new LinkedList<Answer>());
		}
	}
	
	// Sets
	public void setUserId(int id) {
		userId = id;
	}
	public void setCurrentSubjectId(int id) {
		subjectId = id;
		loadQuestions();
	}
	public void setCurrentQuestionId(int id) {
		if (id < 0) id = 1;
		else if (id > questions.size()) id = questions.size();
		questionId = id;
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
	
	public void addtAnswer(int questId, Answer answer) {
		allAnswers.get(questId -1).add(answer);
	}
	
	public void loadQuestions() {
		LinkedList<Question> subject = new LinkedList<Question>();
		LinkedList<Integer> blacklist = new LinkedList<Integer>();
		
		for(int j = 0; j<MainTest.list.getSize(); j++) {
			Question question = MainTest.list.getElementAt(j);
			if (question.getSubject().ordinal() == (subjectId <= 0 ? 0 : subjectId -1)) {
				subject.add(question);
			}
		}
		
		if (subject.size() > 0) {
			questions.clear();
			for(int i=0; i<numOfQuests; i++)
			{
				Random rand = new Random();
				int num = -1;
				System.out.println(subject.size());
				while (num == -1 || blacklist.contains(num)) {
					num = rand.nextInt(0, subject.size());
				}
				
				blacklist.add(num);
				questions.add(subject.get(num));
			}
		}
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
	
	public final Question getQuestionAt(int questId) {
		if (questId < 0) questId = 1;
		else if (questId > questions.size()) questId = questions.size();
		return questions.get(questId -1);
	}
	
	public final Map<Integer, Map<String, Map<String, Boolean>>> getRecap() {
		Map<Integer, Map<String, Map<String, Boolean>>> recap = new LinkedHashMap<Integer, Map<String, Map<String, Boolean>>>();
		
		int questionId = 1;		
		for (LinkedList<Answer> answers : allAnswers) {
			Map<String, Map<String, Boolean>> questionData = new LinkedHashMap<String, Map<String, Boolean>>();			
			Map<String, Boolean> answersData = new LinkedHashMap<String, Boolean>();
			
			for (Answer answer : answers) {
				answersData.put(answer.getText(), answer.isCorrect());
			}
			
			questionData.put(this.getQuestionAt(questionId).getText(), answersData);
			recap.put(questionId++, questionData);
		}
		
		return recap;
	}
}
