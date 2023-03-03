package questions;

import java.io.IOException;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import answers.Answer;
import answers.JSONAnswer;
import parser.JSONCustomParser;

public class JSONQuestion {
	
static JSONCustomParser p = new JSONCustomParser("Quizzle", "Question");
	
	/**
	 * Trasforma una domanda in JSON.
	 * 
	 * @param question
	 * @param fileName
	 */
	public static void toJson(Question question, String fileName) {
		
		JSONObject questionDetails = new JSONObject();
        questionDetails.put("text", question.getText());
        questionDetails.put("maxPoints", question.getMaxPoints());
        
        JSONArray answers = new JSONArray();
        question.getAnswers().forEach(t -> answers.add(JSONAnswer.parseJSONObject(t)));
        
        questionDetails.put("answers", answers);
         
        JSONObject questionObject = new JSONObject();
        questionObject.put("question", questionDetails);
        
        try {
			p.toJson(fileName, questionObject);
		} 
		catch (IOException e) {
			System.out.print("ERRORE SAVE: ");
			e.printStackTrace();
		}
	}
	
	/**
	 * Trasforma un JSON in una domanda.
	 * 
	 * @param fileName
	 */
	public static Question fromJson(String fileName) {
		return parseQuestionObject(p.fromJson(fileName)); //Chiama il metodo sottostante e ritorna la risposta.
	}

	/**
	 * Trasforma un JSONObject in Answer
	 * 
	 * @param answerObject
	 */
	private static Question parseQuestionObject(JSONObject answerObject) {
        JSONObject a = (JSONObject) answerObject.get("question"); //Prendo l'oggetto "question" dal file JSON.
        
        String maxPoints = ""+a.get("maxPoints"); //Prendo la variabile "maxPoints" dal JSON e la casto a String.
        
        int p = Integer.parseInt(maxPoints); //Avendo castato a String possiamo parsare a int.
        
        Object obj = a.get("answers");
        JSONArray ar = (JSONArray) obj;
        
        LinkedList<Answer> ans = new LinkedList<Answer>();
        ar.forEach(t -> ans.add(JSONAnswer.parseAnswerObject((JSONObject)t)));
        
        return new Question((String)a.get("text"), p, ans); //Creiamo una nuova istanza di Question e mettiamo i valori ottenuti, poi la ritorniamo.
    }

}
