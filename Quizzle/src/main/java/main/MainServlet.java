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
	
	private static final String flag_username = "userFlag";		// Login page only / solo per la pagina del login
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
		Vector<Integer> answersId = new Vector<Integer>();	// Get from flag / Prende dal Flag
		String quizPath = "/index.jsp";						// Default / Predefinito
		String forcePage = "";
		UserData userData = null;							// Data of the user / Informazioni dell'utente
		boolean isNewQuestion = false;
		int subjectId = 0;									// Selected subject / Materia selezionata
		
		// Get Username and User Data / Prende il nome dell'utente e i suoi dati
		if (req.getParameterMap().containsKey(flag_username) && req.getParameter(flag_username) != null && !req.getParameter(flag_username).equals("")) { // Checks if username is present and gets user data / Controlla se è presente un username, se lo è prende i dati dell'utente
			userData = usersDB.getUser(req.getParameter(flag_username)); // Gets user data (user that called servlet) / Prende le informazioni dell'utente
			// NOTE: If the user is new, a new userData that references to the username is created, that will maintain a sort of "session" until it is overwritten, we'll overwrite it when starting a new test, after a recap.
			// NOTA: Se l'utente è nuovo, viene creato un nuovo userData che fa riferimento allo username, che manterrà una sorta di "sessione" finché non verrà sovrascritto, lo sovrascriveremo all'avvio di un nuovo test, dopo un riepilogo.
		}
		
		// Get the page to forceSend / Prende la pagina da mandargli forzatamente
		if (req.getParameterMap().containsKey(flag_forcepage) && req.getParameter(flag_forcepage) != null) {
			forcePage = req.getParameter(flag_forcepage);
		}
		
		// Get Subject ID / Prende la Materia
		if (req.getParameterMap().containsKey(flag_subjectid) && req.getParameter(flag_subjectid) != null) { // Checks if a new subject is selected / Controlla se è stata selezionata una materia
			try { 
				subjectId = Integer.parseInt(req.getParameter(flag_subjectid));
		    } catch (NumberFormatException nfe) {}
		}
		
		// Get Answer/Answers / Prende la Risposta / le Risposte
		if (req.getParameterMap().containsKey(flag_answerid) && req.getParameter(flag_answerid) != null) { // Checks if one or more answers are sent form the client / Controlla se una o più risposte sono mandate dal client
			String value = req.getParameter(flag_answerid);
			if (value.contains(",")) { // If multiple answers / Se ci sono più risposte
				String[] answers = value.split(",");
				for (String answer : answers) { // Add each one in the answers vector / Aggiunge le risposte in un vettore
					try {
				        answersId.add(Integer.parseInt(answer)); 
				    } catch (NumberFormatException nfe) {}
				}
			}
			else { // if single answer, add in vector / se c'è solo una risposta
				try {
			        answersId.add(Integer.parseInt(value)); 
			    } catch (NumberFormatException nfe) {}
			}
		}
		
		if (userData != null || forcePage.equals("leaderboard")) { // Checks if the request is referenced to a user / Contolla se c'è un utente
			if (userData != null) {
				if (subjectId != 0) // If new subject selected / Se c'è una materia selezionata
					userData.setCurrentSubjectId(subjectId); // Set user subject / Inserisce la materia selezionata nelle informazioni dell'utente
				
				if (answersId.size() > 0) { // Checks if there is one or more answers / Controlla se l'utente ha risposto
					int questionId = userData.getCurrentQuestionId(); // Gets current question id / prende l'id della domanda
					if (questionId > 0) { // Checks if there is a current question / controlla se c'è una domanda
						int responseTime = userData.getResponseTime(); // Gets the difference between the start time and the response / Prende il tempo di risposta
						if (responseTime > questionMinTime) {
							if (responseTime >= questionTimeout)
								userData.decrementPoints(questionTimeout - questionMinTime);
							else
								userData.decrementPoints(responseTime - questionMinTime);
						}
						// System.out.print(responseTime);
						Question question = userData.getQuestionAt(questionId);
						int earnedPoints = 0;
						
						for (int answerId : answersId) { // Repeats for each answer from the client / Ripete per ogni domanda
							Answer answer = question.getAnswers().get(answerId -1);
							userData.addtAnswer(questionId, answer);
							
							if (answer.isCorrect()) {
								earnedPoints += question.getMaxPoints() / answersId.size(); // Set points / Aggiunge i punti
								// TODO: add points, need a review			
							}
							else {
								earnedPoints += question.getMaxPoints() / answersId.size() / 10;
	 							// TODO: add less points? need a review
							}
						};
						
						userData.incrementPoints(earnedPoints);
						questionId = userData.getNextQuestionId(); // Set next question / Mette la prossima domanda
						userData.setCurrentQuestionId(questionId);
						isNewQuestion = true;
					}
				}
				
				subjectId = userData.getCurrentSubjectId(); // Get current subject / Prende la materia selezionata
				req.setAttribute(flag_username, userData.getUsername()); // User flag is used in all the pages
			}
			switch(forcePage) {
				case "leaderboard": {	
					quizPath = "/leaderboard.jsp";
					req.setAttribute(attr_leaderboard, new JSONObject(usersDB.getLeaderboard()).toJSONString());
					break;
				}
				case "index": {	// Log out
					quizPath = "/index.jsp";
					break;
				}
				default: {
					if (subjectId == 0 || forcePage.equals("subjects")) { // If there is no subject selected / se non ci sono materie selezionate
						Vector<String> subjects = new Vector<String>();
						for (Subjects subject : Subjects.values()) {
							subjects.add(subject.materia);
						}
						
						quizPath = "/subjects.jsp"; // Select subjects page / seleziona la pagina delle materie
						req.setAttribute(attr_subjects, subjects);
					}
					else {
						int questionId = userData.getCurrentQuestionId(); // Gets next question and sets the id / prende la prossima domanda e mette l'id
						if (questionId == 0 || forcePage.equals("recap")) { // Checks if user ended the quiz / controlla se l'utente ha finito il quiz
							quizPath = "/recap.jsp";
							req.setAttribute(attr_balance, userData.getTotalPoints());
							req.setAttribute(attr_answersrecap, new JSONObject(userData.getAnswersRecap()).toJSONString());	// inizia il recap
							req.setAttribute(attr_pointssrecap, new JSONObject(userData.getPointsRecap()).toJSONString());
						}
						else {					
							Question question = userData.getQuestionAt(questionId);
							Vector<String> answersList = new Vector<String>();
							
							question.getAnswers().forEach(answer -> answersList.add(answer.getText())); // Gets answers from the questions / prende le risposte dalle domande
							
							quizPath = "/quiz.jsp";	// Question Preset / preset delle domande
							req.setAttribute(attr_answers, answersList); // Sets all possible answers / Mette tutte le possibili risposte
							req.setAttribute(attr_reward, question.getMaxPoints());
							req.setAttribute(attr_maxanswers, question.getNumberOfCorrectAnswer()); // Sets how many answers the client can return (multiple answers) / Mette quante risposte può dare l'utente
							req.setAttribute(attr_question, questionId + ". " + question.getText());
						}
					}
					
					if (isNewQuestion)
						userData.startNewQuestion(); // Sets the start time / mette il tempo iniziale
				}
			}
		}
		
		RequestDispatcher view = req.getRequestDispatcher((userData == null && !forcePage.equals("leaderboard")) ? "/index.jsp" : quizPath); // If there is no user data, returns default page. / se non c'è user data, ritorna alla pagina di default
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
