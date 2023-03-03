package main;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Vector;

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
	private static UsersDB usersDB = new UsersDB();
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
		
		if (req.getParameterMap().containsKey(key_username) && req.getParameter(key_username) != null) {
			String username = req.getParameter(key_username);
			userData = usersDB.getUser(username);
		}
		
		if (req.getParameterMap().containsKey(key_subjectId) && req.getParameter(key_subjectId) != null) {
			try {
				subjectId = Integer.parseInt(req.getParameter(key_subjectId)); 
		    } catch (NumberFormatException nfe) {}
		}
		
		if (req.getParameterMap().containsKey(key_answerid) && req.getParameter(key_answerid) != null) {
			String value = req.getParameter(key_answerid);
			if (value.contains(",")) {
				String[] answers = value.split(",");
				for (String answer : answers) {
					try {
				        answersId.add(Integer.parseInt(value)); 
				    } catch (NumberFormatException nfe) {}
				}
			}
			else {
				try {
			        answersId.add(Integer.parseInt(value)); 
			    } catch (NumberFormatException nfe) {}
			}
		}
			
		if (userData != null) {
			if (subjectId != 0) { // If new subject selected
				userData.setCurrentSubjectId(subjectId); // Set user subject
			}
			
			if (answersId.size() > 0) {
				int questionId = userData.getCurrentQuestionId();
				// Save answer of the question
			}
		
			subjectId = userData.getCurrentSubjectId(); // Get current subject
			
			// Get new random question and load html page.
			userData.setCurrentQuestionId(0);
			Vector<String> answers = new Vector<String>();
			answers.add("ciao?");
			answers.add("alura mat");
			
			if (subjectId == 0) {
				Vector<String> subjects = new Vector<String>();
				subjects.add("Matematica");
				subjects.add("Italiano");
				
				quizPath = "subjects.jsp"; // Select subjects page
				req.setAttribute("subjects", subjects);
			}
			else {
				quizPath = "quiz.jsp";	// Question Preset
				req.setAttribute(key_username, userData.getUsername()); // Sets userid flag
				req.setAttribute("answers", answers);
				req.setAttribute("maxAnswers", 1);
				
				// Set quiz as already used form that userId, so it doesnt repeat.
			}
		}
		
		RequestDispatcher view = req.getRequestDispatcher((userData == null) ? "/index.jsp" : quizPath);
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
