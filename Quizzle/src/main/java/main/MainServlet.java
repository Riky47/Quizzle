package main;

import jakarta.servlet.RequestDispatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import questions.Question;
import subjects.Subjects;

import java.io.File;
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
	private static final int questionTimeout = 33;
	private static final int questionMinTime = 3;
	private static final int totQuestion = 4;
	
	private static final String flag_username = "userFlag";		// Login page only
	private static final String flag_answerid = "answerFlag";
	private static final String flag_forcepage = "forceFlag";
	private static final String flag_subjectid = "subjectFlag";
	
	private static final String attr_reward = "reward";
	private static final String attr_answers = "answers";
	private static final String attr_balance = "balance";
	private static final String attr_question = "question";
	private static final String attr_subjects = "subjects";
	private static final String attr_maxanswers = "maxAnswers";
	
	private static final String attr_leaderboard = "leaderboard";
	private static final String attr_pointssrecap = "pointsRecap";
	private static final String attr_answersrecap = "answersRecap";
	
	private static final UsersDB usersDB = new UsersDB(totQuestion);
	
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
		Vector<Integer> answersId = new Vector<Integer>();	// Get from flag
		String quizPath = "/index.jsp";						// Default
		String forcePage = "";
		UserData userData = null;							// Data of the user
		boolean isNewQuestion = false;
		int subjectId = 0;									// Selected subject
		
		// Get Username and User Data / Prende il nome dell'utente e i suoi dati
		if (req.getParameterMap().containsKey(flag_username) && req.getParameter(flag_username) != null && !req.getParameter(flag_username).equals("")) { // Checks if username is present and gets user data
			userData = usersDB.getUser(req.getParameter(flag_username)); // Gets user data (user that called servlet)
			// NOTE: If the user is new, a new userData that references to the username is created, that will maintain a sort of "session" until it is overwritten, we'll overwrite it when starting a new test, after a recap.
		}
		
		// Get the page to forceSend / Prende la pagina da mandargli forzatamente
		if (req.getParameterMap().containsKey(flag_forcepage) && req.getParameter(flag_forcepage) != null) {
			forcePage = req.getParameter(flag_forcepage);
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
		
		if (userData != null || forcePage.equals("leaderboard")) { // Checks if the request is referenced to a user
			if (userData != null) {
				if (subjectId != 0) // If new subject selected
					userData.setCurrentSubjectId(subjectId); // Set user subject
				
				if (answersId.size() > 0) { // Checks if there is one or more answers
					int questionId = userData.getCurrentQuestionId(); // Gets current question id
					if (questionId > 0) { // Checks if there is a current question
						int responseTime = userData.getResponseTime(); // Gets the difference between the start time and the response
						if (responseTime > questionMinTime) {
							if (responseTime >= questionTimeout)
								userData.decrementPoints(questionTimeout - questionMinTime);
							else
								userData.decrementPoints(responseTime - questionMinTime);
						}
						
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
						isNewQuestion = true;
					}
				}
				
				subjectId = userData.getCurrentSubjectId(); // Get current subject
				req.setAttribute(flag_username, userData.getUsername()); // User flag is used in all the pages
			}
			switch(forcePage) {
				case "leaderboard": {	
					quizPath = "/leaderboard.jsp";
					req.setAttribute(attr_leaderboard, new JSONObject(usersDB.getLeaderboard()).toJSONString());
					break;
				}
				default: {
					if (subjectId == 0 || forcePage.equals("subjects")) { // If there is no subject selected
						Vector<String> subjects = new Vector<String>();
						for (Subjects subject : Subjects.values()) {
							subjects.add(subject.materia);
						}
						
						quizPath = "/subjects.jsp"; // Select subjects page
						req.setAttribute(attr_subjects, subjects);
					}
					else {
						int questionId = userData.getCurrentQuestionId(); // Gets next question and sets the id
						if (questionId == 0 || forcePage.equals("recap")) { // Checks if user ended the quiz
							quizPath = "/recap.jsp";
							req.setAttribute(attr_balance, userData.getTotalPoints());
							req.setAttribute(attr_answersrecap, new JSONObject(userData.getAnswersRecap()).toJSONString());
							req.setAttribute(attr_pointssrecap, new JSONObject(userData.getPointsRecap()).toJSONString());
						}
						else {					
							Question question = userData.getQuestionAt(questionId);
							Vector<String> answersList = new Vector<String>();
							
							question.getAnswers().forEach(answer -> answersList.add(answer.getText())); // Gets answers from the questions
							
							quizPath = "/quiz.jsp";	// Question Preset
							req.setAttribute(attr_answers, answersList); // Sets all possible answers
							req.setAttribute(attr_reward, question.getMaxPoints());
							req.setAttribute(attr_maxanswers, question.getNumberOfCorrectAnswer()); // Sets how many answers the client can return (multiple answers)
							req.setAttribute(attr_question, questionId + ". " + question.getText());
						}
					}
					
					if (isNewQuestion)
						userData.startNewQuestion(); // Sets the start time
				}
			}
		}
		
		RequestDispatcher view = req.getRequestDispatcher((userData == null && !forcePage.equals("leaderboard")) ? "/index.jsp" : quizPath); // If there is no user data, returns default page.
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
