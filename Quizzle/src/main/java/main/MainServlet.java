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
	private final String key_username = "userFlag";		// Login page only
	private final String key_answerid = "answerFlag";
	private final String key_subjectId = "subjectFlag";
	private static final int totQuestion = 10;
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
					// Save answer of the question or remove question from userData
				}
			}
			
			subjectId = userData.getCurrentSubjectId(); // Get current subject
			req.setAttribute(key_username, userData.getUsername()); // User flag is used in all the pages
			
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
				// TODO: Generate new random question and load html page.
				
				LinkedList<Answer> answerList = new LinkedList<Answer>();	// 
				
				answerList.add(new Answer("Ciao", false, 1));		// Load answers from the generated question
				answerList.add(new Answer("alura mat", true, 2));
				
				Question question = new Question("Saluto migliore", 100, answerList, Subjects.ITALIAN); // Crea la domanda di esempio
				
				Vector<String> answerVector = new Vector<String>();
				answerList.forEach((answer) -> answerVector.add(answer.getText()));
				
				quizPath = "quiz.jsp";	// Question Preset
				req.setAttribute("answers", answerVector); // Sets all possible answers
				req.setAttribute("maxAnswers", question.getNumberOfCorrectAnswer()); // Sets how many answers the client can return (multiple answers)
				req.setAttribute("question", question.getText());
				
				// TODO: Set quiz as already used form that user, so it doesnt repeat.
				// Example userData.addAnswer(answerId, questionId)
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
