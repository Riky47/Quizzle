package main;

import java.io.IOException;

import answers.Answer;
import answers.JSONAnswer;

public class MainTest {

	public static void main(String[] args) throws IOException {
		
		Answer a = new Answer("Sei frocio", true, 0);
		
		JSONAnswer.toJson(a);
		
		Answer b = new Answer(null, false, 0);
		
		b = JSONAnswer.fromJson("test");
		
		System.out.println("RISPOSTA: "+b.getText());
		System.out.println("CORRETTA: "+b.isCorrect());
		System.out.println("ID: "+b.getId());

	}

}
