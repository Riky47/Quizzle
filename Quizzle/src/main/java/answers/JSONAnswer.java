package answers;

import java.io.IOException;

import org.json.simple.JSONObject;

import parser.JSONCustomParser;

public class JSONAnswer {
	
	static JSONCustomParser p = new JSONCustomParser("Quizzle");
	
	public static void toJson(Answer a) {
		JSONObject answerDetails = new JSONObject();
        answerDetails.put("text", a.getText());
        answerDetails.put("isCorrect", a.isCorrect());
        answerDetails.put("id", a.getId());
         
        JSONObject answerObject = new JSONObject();
        answerObject.put("answer", answerDetails);
        
        try {
			p.toJson("test", answerObject);
		} 
		catch (IOException e) {
			System.out.print("ERRORE SAVE: ");
			e.printStackTrace();
		}
	}
	
	public static Answer fromJson(String file) {
		return parseAnswerObject(p.fromJson(file));
	}

	private static Answer parseAnswerObject(JSONObject answerObject) {
        //Get employee object within list
        JSONObject a = (JSONObject) answerObject.get("answer");
        
        String c = ""+a.get("isCorrect");
        
        boolean b = Boolean.parseBoolean(c);
        
        String id = ""+a.get("id");
        
        int i = Integer.parseInt(id);
        
        return new Answer((String)a.get("text"), b, i);
    }
}
