package main;

import jakarta.servlet.RequestDispatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import questions.Question;
import subjects.Subjects;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Vector;

import answers.Answer;

/**
 * Servlet implementation class MainServlet
 */
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*
	 * 	 Data & Settings
	 */
	private static final int totQuestion = 10;
	private final String key_username = "userFlag";		// Login page only
	private final String key_answerid = "answerFlag";
	private final String key_subjectId = "subjectFlag";
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
		
		if (req.getParameterMap().containsKey(key_username) && req.getParameter(key_username) != null) { // Checks if username is present and gets user data
			String username = req.getParameter(key_username); 
			userData = usersDB.getUser(username); // Gets user data (user that called servlet)
			
			// NOTE: If the user is new, a new userData that references to the username is created, that will maintain a sort of "session" until it is overwritten, we'll overwrite it when starting a new test, after a recap.
		}
		
		// Get Subject ID / Prende la Materia
		
		if (req.getParameterMap().containsKey(key_subjectId) && req.getParameter(key_subjectId) != null) { // Checks if a new subject is selected
			try { 
				subjectId = Integer.parseInt(req.getParameter(key_subjectId));
		    } catch (NumberFormatException nfe) {}
		}
		
		// Get Answer/Answers / Prende la Risposta / le Risposte
		
		if (req.getParameterMap().containsKey(key_answerid) && req.getParameter(key_answerid) != null) { // Checks if one or more answers are sent form the client
			String value = req.getParameter(key_answerid);
			if (value.contains(",")) { // If multiple answers
				String[] answers = value.split(",");
				for (String answer : answers) { // Add each one in the answers vector
					try {
				        answersId.add(Integer.parseInt(value)); 
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
						Answer answer = question.getAnswers().get(answerId);
						userData.setCorrectAnswer(questionId, answer);
						
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
				}
			}
			
			subjectId = userData.getCurrentSubjectId(); // Get current subject
			req.setAttribute(key_username, userData.getUsername()); // User flag is used in all the pages
			System.out.println(userData.getPoints());
			
			if (subjectId == 0) { // If there is no subject selected
				Vector<String> subjects = new Vector<String>();
				subjects.add("Matematica"); // Load all subjects from json
				subjects.add("Italiano");
				subjects.add("Meccanica");
				subjects.add("Storia");
				
				quizPath = "subjects.jsp"; // Select subjects page
				req.setAttribute("subjects", subjects);
			}
			else {
				int questionId = userData.getNextQuestionId(); // Gets next question and sets the id
				if (questionId == 0) { // Checks if user ended the quiz
					// TODO: user ended the quiz
					
				}
				else {	
					userData.setCurrentQuestionId(questionId);
					
					Question question = userData.getQuestionAt(questionId);
					Vector<String> answersList = new Vector<String>();
					
					question.getAnswers().forEach(answer -> answersList.add(answer.getText())); // Gets answers from the questions
					
					quizPath = "quiz.jsp";	// Question Preset
					req.setAttribute("answers", answersList); // Sets all possible answers
					req.setAttribute("maxAnswers", question.getNumberOfCorrectAnswer()); // Sets how many answers the client can return (multiple answers)
					req.setAttribute("question", question.getText());
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
