package answers;

import java.io.IOException;

import org.json.simple.JSONObject;

import parser.JSONCustomParser;

/**
 * Classe per trasformare un JSON in una risposta e vice-versa;
 * 
 * Il JSON viene salvato nel disco principale in Quizzle/Answers
 * 
 * @author Guizzetti Alessio
 */
public class JSONAnswer {
	
	static JSONCustomParser p = new JSONCustomParser("Quizzle", "Answer");
	
	/**
	 * Trasforma una risposta in JSON.
	 * 
	 * @param answer
	 * @param fileName
	 */
	public static void toJson(Answer answer, String fileName) {
		
        JSONObject answerObject = parseJSONObject(answer);
		
        try {
			p.toJson(fileName, answerObject);
		} 
		catch (IOException e) {
			System.out.print("ERRORE SAVE: ");
			e.printStackTrace();
		}
	}
	
	/**
	 * Trasforma un JSON in una risposta.
	 * 
	 * @param fileName
	 */
	public static Answer fromJson(String fileName) {
		return parseAnswerObject(p.fromJson(fileName)); //Chiama il metodo sottostante e ritorna la risposta.
	}
	
	public static JSONObject parseJSONObject(Answer answer) {
		JSONObject answerDetails = new JSONObject();
        answerDetails.put("text", answer.getText());
        answerDetails.put("isCorrect", answer.isCorrect());
        answerDetails.put("id", answer.getId());
         
        JSONObject answerObject = new JSONObject();
        answerObject.put("answer", answerDetails);
        return answerObject;
	}

	/**
	 * Trasforma un JSONObject in Answer
	 * 
	 * @param answerObject
	 */
	public static Answer parseAnswerObject(JSONObject answerObject) {
        JSONObject a = (JSONObject) answerObject.get("answer"); //Prendo l'oggetto "answer" dal file JSON.
        
        String c = ""+a.get("isCorrect"); //Prendo la variabile "isCorrect" dal JSON e la casto a String.
        
        boolean b = Boolean.parseBoolean(c); //Avendo castato a String possiamo parsare a booleano.
        
        String id = ""+a.get("id"); //Prendo la variabile "id" dal JSON e la casto a String.
        
        int i = Integer.parseInt(id); //Avendo castato a String possiamo parsare a int.
        
        return new Answer((String)a.get("text"), b, i); //Creiamo una nuova istanza di Answer e mettiamo i valori ottenuti, poi la ritorniamo.
    }
}
