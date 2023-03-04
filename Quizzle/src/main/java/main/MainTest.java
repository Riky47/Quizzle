package main;

import java.io.IOException;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import answers.Answer;
import answers.JSONAnswer;
import questions.JSONQuestion;
import questions.Question;
import quizzer.QuizzerMenu;
import subjects.Subjects;

import javax.swing.DefaultListModel;

public class MainTest {
	
	static String sp = File.separator;
	static String path = root()+sp+"Quizzle";
	public static DefaultListModel<Question> list = new DefaultListModel<Question>();

	public static void main(String[] args) throws IOException {
		
		new QuizzerMenu();
		
		Answer a = new Answer("Frocio", true);
		
		JSONAnswer.toJson(a, "test");
		
		Answer b = new Answer(null, false);
		
		b = JSONAnswer.fromJson("test");
		
		Answer c = new Answer("Etero", false);
		
		b.output();
		
		LinkedList<Answer> ans = new LinkedList<Answer>();
		ans.add(b);
		ans.add(c);
		
		Question q = new Question("Cosa sei?", 10, ans, Subjects.MATH);
		
		
		JSONQuestion.toJson(q, "test");
		
		Question n = Question.NULL;
		
		n = JSONQuestion.fromJson("test");
		
		n.output();

	}
	
	@SuppressWarnings("unchecked")
	public static void saveDataBase() {
		LinkedList<Question> a = new LinkedList<Question>();
		
		for(int i = 0; i<list.getSize(); i++) {
			a.add(list.getElementAt(i));
		}
		
		a.forEach(t -> JSONQuestion.toJson(t, "QuestionN"+a.indexOf(t)));
	}
	
	public static void loadDataBase() {
		File db = new File(path+sp+"Question"); //File dove leggere e scrivere i dipendenti
		String[] ls=db.list();
		
		for(int i = 0; i<ls.length; i++) {
			list.addElement(JSONQuestion.fromJson(path+sp+"Question"+sp+"QuestionN"+i));
		}
	}
	
	public static String root(){
		File file = new File(".").getAbsoluteFile();
		File root = file.getParentFile();
		while (root.getParentFile() != null) {
		    root = root.getParentFile();
		}
		return root.getPath();
	}

}
