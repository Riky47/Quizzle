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
		
		loadDataBase();
		/*
		 * QuizzerMenu avvia il processo per la Java Application, mostrando anche l'interfaccia grafica.
		 * 
		 * QuizzerMenu non è una classe completa ed è da riordinare, per favore non modificate NULLA.
		*/
		new QuizzerMenu(); 
	}
	
	
	/**
	 * Il metodo saveDataBase() salva tutte le domande in file JSON
	 * nella cartella della propria materia.
	 */
	@SuppressWarnings("unchecked")
	public static void saveDataBase() {
		LinkedList<Question> a = new LinkedList<Question>();
		LinkedList<Integer> indexes = new LinkedList<Integer>();
		
		for(int i = 0; i<Subjects.values().length; i++) {
			indexes.add(0);
		}
		
		for(int i = 0; i<list.getSize(); i++) {
			a.add(list.getElementAt(i));
		}
		
		a.forEach(t -> {
			int id = t.getSubject().ordinal();
			int index = indexes.get(id);
			indexes.set(id, index +1);
			
			JSONQuestion.toJson(t, "QuestionN"+index);
		});
	}
	
	
	/**
	 * Il metodo loadDataBase() inizializza il database, caricando tutti i file JSON
	 * in una LinkedList.
	 */
	public static void loadDataBase() {
		File db = new File(path+sp+"Question"); //Cartella dove leggere e scrivere i dipendenti
		String[] ls=db.list();
		
		if(ls != null) {
			list.clear();
			
			for(int i = 0; i<ls.length; i++) {
				File f = new File(path+sp+"Question"+sp+ls[i]);
				String[] ls1=f.list();
				for(int j = 0; j<ls1.length; j++) {
					list.addElement(JSONQuestion.fromJson(ls[i]+sp+"QuestionN"+j));
				}
			}
		}
	}
	
	
	/**
	 * Il metodo root() trova la cartella principale dell'hardisk su cui ci troviamo.
	 * Esempio: C: , Z: , ecc...
	 * 
	 * @return Nome della cartella principale
	 */
	public static String root(){
		File file = new File(".").getAbsoluteFile();
		File root = file.getParentFile();
		while (root.getParentFile() != null) {
		    root = root.getParentFile();
		}
		return root.getPath();
	}

}
