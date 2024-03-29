package questions;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.security.auth.Subject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import answers.Answer;
import answers.JSONAnswer;
import parser.JSONCustomParser;
import subjects.Subjects;

public class JSONQuestion {
	
static JSONCustomParser p = new JSONCustomParser("Quizzle", "Question");

	static String sp = File.separator;
	
	/**
	 * Trasforma una domanda in JSON.
	 * 
	 * @param question
	 * @param fileName
	 */
	@SuppressWarnings("unchecked")
	public static void toJson(Question question, String fileName) {
		
		JSONObject questionDetails = new JSONObject();
        questionDetails.put("text", question.getText());
        questionDetails.put("maxPoints", question.getMaxPoints());
        questionDetails.put("subject", question.getSubject().name());
        
        JSONArray answers = new JSONArray();
        question.getAnswers().forEach(t -> answers.add(JSONAnswer.parseJSONObject(t)));
        
        questionDetails.put("answers", answers);
         
        JSONObject questionObject = new JSONObject();
        questionObject.put("question", questionDetails);
        
        try {
			p.toJson(question.getSubject().materia+sp+fileName, questionObject);
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
	@SuppressWarnings("unchecked")
	public static Question fromJson(String fileName) {
		JSONObject question = (JSONObject) p.fromJson(fileName).get("question"); //Prendo l'oggetto "question" dal file JSON.
        
        String maxPoints = ""+question.get("maxPoints"); //Prendo la variabile "maxPoints" dal JSON e la casto a String.
        
        int p = Integer.parseInt(maxPoints); //Avendo castato a String possiamo parsare a int.
        
        String m = (String) question.get("subject");
        
        Object obj = question.get("answers");
        JSONArray array = (JSONArray) obj;
        
        LinkedList<Answer> ans = new LinkedList<Answer>();
        array.forEach(q -> ans.add(JSONAnswer.parseAnswerObject((JSONObject)q)));
        
        return new Question((String)question.get("text"), p, ans, Subjects.valueOf(m)); //Creiamo una nuova istanza di Question e mettiamo i valori ottenuti, poi la ritorniamo.
	}

}
