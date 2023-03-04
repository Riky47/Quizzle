package main;

import jakarta.servlet.RequestDispatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import questions.Question;
import subjects.Subjects;

import java.io.IOException;
import java.util.Vector;

import org.json.simple.JSONObject;

import answers.Answer;
import main.MainTest;

/**
 * Servlet implementation class MainServlet
 */
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int totQuestion = 1;
	
	private final String flag_username = "userFlag";		// Login page only
	private final String flag_answerid = "answerFlag";
	private final String flag_subjectid = "subjectFlag";
	
	private final String attr_recap = "recap";
	private final String attr_reward = "reward";
	private final String attr_answers = "answers";
	private final String attr_balance = "balance";
	private final String attr_question = "question";
	private final String attr_subjects = "subjects";
	private final String attr_maxanswers = "maxAnswers";
	
	private static UsersDB usersDB = new UsersDB(totQuestion);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Vector<Integer> answersId = new Vector<Integer>();	// Get from flag
		String quizPath = "/index.jsp";						// Default
		UserData userData = null;							// Data of the user
		int subjectId = 0;									// Selected subject
		
		// Get Username and User Data / Prende il nome dell'utente e i suoi dati
		
		if (req.getParameterMap().containsKey(flag_username) && req.getParameter(flag_username) != null) { // Checks if username is present and gets user data
			String username = req.getParameter(flag_username); 
			userData = usersDB.getUser(username); // Gets user data (user that called servlet)
			
			// NOTE: If the user is new, a new userData that references to the username is created, that will maintain a sort of "session" until it is overwritten, we'll overwrite it when starting a new test, after a recap.
		}
		
		// Get Subject ID / Prende la Materia
		
		if (req.getParameterMap().containsKey(flag_subjectid) && req.getParameter(flag_subjectid) != null) { // Checks if a new subject is selected
			try { 
				subjectId = Integer.parseInt(req.getParameter(flag_subjectid));
		    } catch (NumberFormatException nfe) {}
		}
		
		// Get Answer/Answers / Prende la Risposta / le Risposte
		
		if (req.getParameterMap().containsKey(flag_answerid) && req.getParameter(flag_answerid) != null) { // Checks if one or more answers are sent form the client
			String value = req.getParameter(flag_answerid);
			if (value.contains(",")) { // If multiple answers
				String[] answers = value.split(",");
				for (String answer : answers) { // Add each one in the answers vector
					try {
				        answersId.add(Integer.parseInt(answer)); 
				    } catch (NumberFormatException nfe) {}
				}
			}
			else { // if single answer, add in vector
				try {
			        answersId.add(Integer.parseInt(value)); 
			    } catch (NumberFormatException nfe) {}
			}
		}
		
		if (userData != null) { // Checks if the request is referenced to a user
			if (subjectId != 0) { // If new subject selected
				userData.setCurrentSubjectId(subjectId); // Set user subject
			}
			
			if (answersId.size() > 0) { // Checks if there is one or more answers
				int questionId = userData.getCurrentQuestionId(); // Gets current question id
				if (questionId > 0) { // Checks if there is a current question
					Question question = userData.getQuestionAt(questionId);
					int earnedPoints = 0;
					
					for (int answerId : answersId) { // Repeats for each answer from the client
						Answer answer = question.getAnswers().get(answerId -1);
						userData.addtAnswer(questionId, answer);
						
						if (answer.isCorrect()) {
							earnedPoints += question.getMaxPoints() / answersId.size(); // Set points
							// TODO: add points, need a review			
						}
						else {
							earnedPoints += question.getMaxPoints() / answersId.size() / 10;
 							// TODO: add less points? need a review
						}
					};
					
					userData.incrementPoints(earnedPoints);
					questionId = userData.getNextQuestionId(); // Set next question
					userData.setCurrentQuestionId(questionId);
				}
			}
			
			subjectId = userData.getCurrentSubjectId(); // Get current subject
			req.setAttribute(flag_username, userData.getUsername()); // User flag is used in all the pages
			req.setAttribute(attr_balance, userData.getPoints());
			
			if (subjectId == 0) { // If there is no subject selected
				Vector<String> subjects = new Vector<String>();
				for (Subjects subject : Subjects.values()) {
					subjects.add(subject.materia);
				}
				
				quizPath = "subjects.jsp"; // Select subjects page
				req.setAttribute(attr_subjects, subjects);
			}
			else {
				int questionId = userData.getCurrentQuestionId(); // Gets next question and sets the id
				if (questionId == 0) { // Checks if user ended the quiz
					quizPath = "recap.jsp";
					req.setAttribute(attr_recap, new JSONObject(userData.getRecap()).toJSONString());
				}
				else {					
					Question question = userData.getQuestionAt(questionId);
					Vector<String> answersList = new Vector<String>();
					
					question.getAnswers().forEach(answer -> answersList.add(answer.getText())); // Gets answers from the questions
					
					quizPath = "quiz.jsp";	// Question Preset
					req.setAttribute(attr_answers, answersList); // Sets all possible answers
					req.setAttribute(attr_reward, question.getMaxPoints());
					req.setAttribute(attr_maxanswers, question.getNumberOfCorrectAnswer()); // Sets how many answers the client can return (multiple answers)
					req.setAttribute(attr_question, questionId + ". " + question.getText());
				}
			}
		}
		
		RequestDispatcher view = req.getRequestDispatcher((userData == null) ? "/index.jsp" : quizPath); // If there is no user data, returns default page.
		view.forward(req, res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
