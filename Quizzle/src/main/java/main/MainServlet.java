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
		
		if (req.getParameterMap().containsKey(key_username) && req.getParameter(key_username) != null) {
			String username = req.getParameter(key_username);
			userData = usersDB.getUser(username);
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
			if (answersId.size() > 0) {
				// Save answer of the question
			}
		
			// Get new random question and load html page.
			Vector<String> answers = new Vector<String>();
			answers.add("ciao?");
			answers.add("alura mat");
			
			quizPath = "quiz.jsp";	// Question Preset
			req.setAttribute(key_username, userData.getUsername()); // Sets userid flag
			req.setAttribute("answers", answers);
			req.setAttribute("maxAnswers", 1);
			
			// Set quiz as already used form that userId, so it doesnt repeat.
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
