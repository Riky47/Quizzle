package main;

import java.io.IOException;
import java.util.LinkedList;

import answers.Answer;
import answers.JSONAnswer;
import questions.JSONQuestion;
import questions.Question;

public class MainTest {

	public static void main(String[] args) throws IOException {
		
		Answer a = new Answer("Frocio", true, 0);
		
		JSONAnswer.toJson(a, "test");
		
		Answer b = new Answer(null, false, 0);
		
		b = JSONAnswer.fromJson("test");
		
		Answer c = new Answer("Etero", false, 1);
		
		b.output();
		
		LinkedList<Answer> ans = new LinkedList<Answer>();
		ans.add(b);
		ans.add(c);
		
		Question q = new Question("Cosa sei?", 10, ans);
		
		
		JSONQuestion.toJson(q, "test");
		
		Question n = new Question(null, 10, null);
		
		n = JSONQuestion.fromJson("test");
		
		n.output();
		
		

	}

}
