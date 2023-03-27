package main;

import java.util.Date;
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
	private String username;
	private LinkedList<Integer> numOfQuests = new LinkedList<Integer>();
	private LinkedList<Integer> questionTime = new LinkedList<Integer>();
	private LinkedList<Integer> questionId = new LinkedList<Integer>();
	private LinkedList<Integer> totalPoints = new LinkedList<Integer>();
	
	private Map<Integer, LinkedList<Question>> questions = new LinkedHashMap<Integer, LinkedList<Question>>();
	private Map<Integer, LinkedList<LinkedList<Answer>>> allAnswers = new LinkedHashMap<Integer, LinkedList<LinkedList<Answer>>>();
	
	/**
	 * Costruttore, prende l'id del'utente, lo username e il numero di risposte
	 */
	public UserData(int id, String name, int numberOfQuestions) {	
		userId = id;
		subjectId = 0;
		username = name;
		
		for (int i=0; i<Subjects.values().length; i++) { // Pre-Load
			numOfQuests.add(numberOfQuestions); // SHOULD CHANGE X EACH SUBJECT!
		}
		
		loadQuestions();	
		for (int i=0; i<Subjects.values().length; i++) { // Post-Load
			LinkedList<LinkedList<Answer>> answers = new LinkedList<LinkedList<Answer>>();
			
			int tot = 0;
			for(int j = 0; j<MainTest.list.getSize(); j++)
				if (MainTest.list.getElementAt(j).getSubject().ordinal() == i) {
					tot++;
					
					if (tot >= numOfQuests.get(i))
						break;
				}
			
			for(int j=0; j<tot; j++)
				answers.add(new LinkedList<Answer>());
			
			questionId.add(1);
			totalPoints.add(0);
			questionTime.add((int)(new Date().getTime()/1000));
			allAnswers.put(i, answers);
		}
	}
	
	/**
	 * Ritorna un SubjectId valido
	 */
	private int getValidSubjectId() {
		return subjectId <= 0 ? 0 : subjectId -1;
	}
	
	// Sets
	/**
	 * Imposta L'id dell'utente
	 */
	public void setUserId(int id) {
		userId = id;
	}
	/**
	 * Imposta L'id della materia
	 */
	public void setCurrentSubjectId(int id) {
		subjectId = id;
		// loadQuestions(); not needed anymore
	}
	/**
	 * Imposta L'id della domanda
	 */
	public void setCurrentQuestionId(int id) {
		int size = questions.get(getValidSubjectId()).size();
		if (id < 0) id = 1;
		else if (id > size) id = size;
		questionId.set(getValidSubjectId(), id);
	}
	/**
	 * Imposta L'username dell'utente
	 */
	public void setUsername(String name) {
		username = name;
	}
	/**
	 * Incrementa i punti
	 */
	public void incrementPoints(int points) {
		int id = getValidSubjectId();
		totalPoints.set(id, totalPoints.get(id) + points);
	}
	/**
	 * diminuisce i punti
	 */
	public void decrementPoints(int points) {
		int id = getValidSubjectId();
		totalPoints.set(id, totalPoints.get(id) - points);
	}
	/**
	 * Aggiunge una risposta data
	 */
	public void addtAnswer(int questId, Answer answer) {
		allAnswers.get(getValidSubjectId()).get(questId -1).add(answer);
	}
	/**
	 * Inizia una nuova domanda
	 */
	public void startNewQuestion() {
		questionTime.set(getValidSubjectId(), (int)(new Date().getTime()/1000));
	}
	/**
	 * Carica una domanda
	 */
	public void loadQuestions() {
		for (int i=0; i<Subjects.values().length; i++) {
			LinkedList<Question> subject = new LinkedList<Question>();
			LinkedList<Integer> whitelist = new LinkedList<Integer>();
			LinkedList<Question> subQuestions = new LinkedList<Question>();
	
			for(int j = 0; j<MainTest.list.getSize(); j++) {
				Question question = MainTest.list.getElementAt(j);
				if (question.getSubject().ordinal() == i) {
					whitelist.add(subject.size());
					subject.add(question);
				}
			}
			
			for(int j=0; j<subject.size(); j++)
			{
				int num = new Random().nextInt(0, whitelist.size());
				subQuestions.add(subject.get(whitelist.get(num)));
				whitelist.remove(num);
			}
			
			if (subject.size() < numOfQuests.get(i))
				System.out.println("[ WARN ]: Not enough questions is subject: " + Subjects.values()[i].materia + ", min: " + numOfQuests + ", current: " + subject.size());
			
			questions.put(i, subQuestions);
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
		return questionId.get(getValidSubjectId());
	}
	public final String getUsername() {
		return username;
	}
	public final int getSubjectPoints() {
		return totalPoints.get(getValidSubjectId());
	}
	public final int getTotalPoints() {
		int total = 0;
		for (int i=0; i<totalPoints.size(); i++) {
			total += totalPoints.get(i);
		}
		
		return total;
	}
	
	public final int getNextQuestionId() { // Returns 0 if out of bounds
		int id = getValidSubjectId();
		int result = questionId.get(id) +1;
		if (result > questions.get(id).size() || result < 0) {
			result = 0;
		}
		
		return result;
	}
	/**
	 * Ritorna il tempo di risposta
	 */
	public final int getResponseTime() {
		return (int)(new Date().getTime()/1000) - questionTime.get(getValidSubjectId());
	}
	/**
	 * Ritorna la domanda all'indice indicato
	 */
	public final Question getQuestionAt(int questId) {
		int size = questions.get(getValidSubjectId()).size();
		
		if (questId < 0) questId = 1;
		else if (questId > size) questId = size;
		return questions.get(getValidSubjectId()).get(questId -1);
	}
	/**
	 * Crea il recap del quiz
	 */
	public final Map<String, Map<Integer, Map<String, Map<String, Boolean>>>> getAnswersRecap() {
		Map<String, Map<Integer, Map<String, Map<String, Boolean>>>> recap = new LinkedHashMap<String, Map<Integer, Map<String, Map<String, Boolean>>>>();
		
		int questionId = 1;
		for (int i=0; i<allAnswers.size(); i++) {
			Map<Integer, Map<String, Map<String, Boolean>>> subjectQuestions = new LinkedHashMap<Integer, Map<String, Map<String, Boolean>>>();
			
			for (LinkedList<Answer> answers : allAnswers.get(i)) {
				if (answers.size() > 0) {
					Map<String, Map<String, Boolean>> questionData = new LinkedHashMap<String, Map<String, Boolean>>();			
					Map<String, Boolean> answersData = new LinkedHashMap<String, Boolean>();
					
					for (Answer answer : answers) {
						answersData.put(answer.getText(), answer.isCorrect());
					}
					
					questionData.put(this.getQuestionAt(questionId).getText(), answersData);
					subjectQuestions.put(questionId++, questionData);
				}	
			}
			
			recap.put(Subjects.values()[i].materia, subjectQuestions);
		}
		
		return recap;
	}
	/**
	 * Ritorna i punti fatti nel quiz
	 */
	public final Map<String, Integer> getPointsRecap() {
		Map<String, Integer> recap = new LinkedHashMap<String, Integer>();
		
		for (int i=0; i<totalPoints.size(); i++) {
			recap.put(Subjects.values()[i].materia, totalPoints.get(i));
		}
		
		return recap;
		
	}
}
