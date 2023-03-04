package quizzer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import answers.Answer;
import main.MainTest;
import questions.Question;
import subjects.Subjects;

@SuppressWarnings("serial")
public class QuizzerMenu{
	
	JLabel empty = new JLabel("<html><h3>Nessuna Domanda</h3></html>");
	JLabel added = new JLabel("<html><h3>Domanda Aggiunta</h3></html>");
	JLabel changed = new JLabel("<html><h3>Domanda Modificata</h3></html>");
	JLabel cleared = new JLabel("<html><h3>Domande Eliminate</h3></html>");
	
	Question selected = Question.NULL;
	
	int currentIndex = 0;
	
	MenuPane menu = new MenuPane();
	
	AddPane addMenu = new AddPane();
	AddAnswerPane addAnswerMenu = new AddAnswerPane();
	
	DatabasePane dataMenu = new DatabasePane();
	
	ModifyPane modMenu = new ModifyPane();
	
	JFrame frame = new JFrame("Quizzer");
	
	JList<Question> list = new JList<Question>(MainTest.list);
	public static DefaultListModel<Answer> answers = new DefaultListModel<Answer>();
	JList<Answer> answersList = new JList<Answer>(answers);
	
	public QuizzerMenu() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	frame.update(frame.getGraphics());
                menu.setVisible(true);
                addMenu.setVisible(true);
                dataMenu.setVisible(true);
                frame.setSize(600, 400);
                frame.add(menu);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
	
	public class MenuPane extends JPanel{
		public MenuPane(){
			setBorder(new EmptyBorder(10, 10, 10, 10));
	        setLayout(new GridBagLayout());

	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.gridwidth = GridBagConstraints.REMAINDER;
	        gbc.anchor = GridBagConstraints.NORTH;

	        add(new JLabel("<html><h1><strong><i>Quizzer</i></strong></h1><hr></html>"), gbc);
	        add(cleared, gbc);
	        cleared.setVisible(false);

	        gbc.anchor = GridBagConstraints.CENTER;
	        gbc.fill = GridBagConstraints.HORIZONTAL;
	        

	        JPanel buttons = new JPanel(new GridBagLayout());
	        JButton add = new JButton("Aggiungi Domanda");
	        JButton database = new JButton("Domande");
	        JButton clear = new JButton("Elimina Domande");
	        JButton exit = new JButton("Esci");
	        
	        database.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		frame.remove(menu);
	        		frame.add(dataMenu);
	        		empty.setVisible(MainTest.list.isEmpty());
	        		cleared.setVisible(false);
	        		dataMenu.updateUI();
	            }
	        });
	        
	        add.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		frame.remove(menu);
	        		frame.add(addMenu);
	        		cleared.setVisible(false);
	        		addMenu.updateUI();
	            }
	        });
	        
	        clear.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		MainTest.list.clear();
	        		MainTest.saveDataBase();
	        		menu.updateUI();
	        		list.updateUI();
	        		cleared.setVisible(true);
	            }
	        });
	        
	        exit.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		cleared.setVisible(false);
	                System.exit(0);
	            }
	        });
	        
	        buttons.add(add, gbc);
	        buttons.add(database, gbc);
	        buttons.add(clear, gbc);
	        buttons.add(exit, gbc);

	        gbc.weighty = 1;
	        add(buttons, gbc);
		}
	}
	
	public class AddPane extends JPanel{
		public AddPane(){
			setBorder(new EmptyBorder(10, 10, 10, 10));
	        setLayout(new GridBagLayout());

	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.gridwidth = GridBagConstraints.REMAINDER;
	        gbc.anchor = GridBagConstraints.NORTH;

	        add(new JLabel("<html><h1><strong><i>Nuova Domanda</i></strong></h1><hr></html>"), gbc);
	        add(added, gbc);
	        added.setVisible(false);

	        gbc.anchor = GridBagConstraints.CENTER;
	        gbc.fill = GridBagConstraints.HORIZONTAL;

	        JPanel buttons = new JPanel(new GridBagLayout());
	        JTextField textField = new JTextField();
	        textField.setToolTipText("Testo");
	        JTextField pointsField = new JTextField();
	        pointsField.setToolTipText("Punti");
	        JComboBox<Subjects> combo = new JComboBox<Subjects>(Subjects.values());
	        
	        Subjects s = Subjects.MATH;
	        
	        MenuActionListener m = new MenuActionListener(s) {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					this.subj = (Subjects) combo.getSelectedItem();
					
				}
	        	
	        };
	        
	        combo.addActionListener(m);
	        
	        answersList = new JList(answers);
	        
	        JButton add = new JButton("Aggiungi");
	        JButton addAnswer = new JButton("Aggiungi Risposta");
	        JButton exit = new JButton("Indietro");
	        
	        add.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		
					added.setVisible(false);
					added.setVisible(true);
					LinkedList<Answer> a = new LinkedList<Answer>();
					
					for(int i = 0; i<answers.getSize(); i++) {
						a.add(answers.getElementAt(i));
					}
					
	        		Question dp = new Question(textField.getText(), Integer.parseInt(pointsField.getText()), a, m.getSubject());
	        		
	        		textField.setText(" ");
	        		pointsField.setText(" ");
	        		answers.clear();
	        		
	        		
	        		MainTest.list.addElement(dp);
	        		MainTest.saveDataBase();
	        		list.updateUI();
	        		answersList.updateUI();
	            }
	        });
	        
	        addAnswer.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		added.setVisible(false);
	        		frame.remove(addMenu);
	        		frame.add(addAnswerMenu);
	        		addAnswerMenu.updateUI();
	            }
	        });
	        
	        exit.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		added.setVisible(false);
	        		frame.remove(addMenu);
	        		frame.add(menu);
	        		menu.updateUI();
	            }
	        });
	        
	        JScrollPane scrollPane = new JScrollPane();
	        scrollPane.setViewportView(answersList);
	        answersList.setLayoutOrientation(JList.VERTICAL);
	        
	        buttons.add(textField, gbc);
	        buttons.add(pointsField, gbc);
	        buttons.add(combo, gbc);
	        buttons.add(scrollPane, gbc);
	        
	        buttons.add(addAnswer, gbc);
	        buttons.add(add, gbc);
	        buttons.add(exit, gbc);

	        gbc.weighty = 1;
	        add(buttons, gbc);
		}
	}
	
	public class AddAnswerPane extends JPanel{
		public AddAnswerPane(){
			setBorder(new EmptyBorder(10, 10, 10, 10));
	        setLayout(new GridBagLayout());

	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.gridwidth = GridBagConstraints.REMAINDER;
	        gbc.anchor = GridBagConstraints.NORTH;

	        add(new JLabel("<html><h1><strong><i>Nuova Risposta</i></strong></h1><hr></html>"), gbc);
	        add(added, gbc);
	        added.setVisible(false);

	        gbc.anchor = GridBagConstraints.CENTER;
	        gbc.fill = GridBagConstraints.HORIZONTAL;

	        JPanel buttons = new JPanel(new GridBagLayout());
	        JTextField textField = new JTextField();
	        textField.setToolTipText("Testo");
	        JCheckBox correctField = new JCheckBox();
	        correctField.setText("Corretta?");
	        correctField.setToolTipText("Is Correct");
	        
	        JButton add = new JButton("Aggiungi");
	        JButton exit = new JButton("Indietro");
	        
	        add.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		
					added.setVisible(false);
					added.setVisible(true);
	        		Answer dp = new Answer(textField.getText(), correctField.isSelected());
	        		
	        		textField.setText(" ");
	        		correctField.setSelected(false);
	        		
	        		answers.addElement(dp);
	        		MainTest.saveDataBase();
	        		answersList.updateUI();
	            }
	        });
	        
	        exit.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		added.setVisible(false);
	        		frame.remove(addAnswerMenu);
	        		frame.add(addMenu);
	        		addMenu.updateUI();
	            }
	        });
	        
	        buttons.add(textField, gbc);
	        buttons.add(correctField, gbc);
	        
	        buttons.add(add, gbc);
	        buttons.add(exit, gbc);

	        gbc.weighty = 1;
	        add(buttons, gbc);
		}
	}
	
	public class DatabasePane extends JPanel{
		public DatabasePane(){
			setBorder(new EmptyBorder(10, 10, 10, 10));
	        setLayout(new GridBagLayout());

	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.gridwidth = GridBagConstraints.REMAINDER;
	        gbc.anchor = GridBagConstraints.NORTH;

	        add(new JLabel("<html><h1><strong><i>Domande</i></strong></h1><hr></html>"), gbc);
	        add(empty, gbc);

	        gbc.anchor = GridBagConstraints.CENTER;
	        gbc.fill = GridBagConstraints.HORIZONTAL;

	        JPanel buttons = new JPanel(new GridBagLayout());
	        
	        list = new JList<Question>(MainTest.list);
	        
	        JTextField idField = new JTextField();
	        idField.setToolTipText("Numero dell'Elemento");
	        JButton modify = new JButton("Modifica");
	        JButton exit = new JButton("Exit");
	        
	        exit.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		frame.remove(dataMenu);
	        		frame.add(menu);
	        		menu.updateUI();
	            }
	        });
	        
	        modify.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		if(idField.getText().isBlank()) {
	        			return;
	        		}
	        		
	        		try {
	        			if(MainTest.list.elementAt(Integer.parseInt(idField.getText())).equals(Question.NULL)) {
		        			return;
		        		}
	        			else {
	        				selected = MainTest.list.elementAt(Integer.parseInt(idField.getText()));
	        			}
	        		}
	        		catch(ArrayIndexOutOfBoundsException ai){
	        			idField.setText("");
	        			return;
	        		}
	        		
	        		
	        		selected = MainTest.list.elementAt(Integer.parseInt(idField.getText()));
	        		
	        		frame.remove(dataMenu);
	        		frame.add(modMenu);
	        		modMenu.updateUI();
	            }
	        });
	        
	        JScrollPane scrollPane = new JScrollPane();
	        scrollPane.setViewportView(list);
	        list.addMouseMotionListener(new MouseMotionAdapter() {
	            @Override
	            public void mouseMoved(MouseEvent e) {
	                JList l = (JList)e.getSource();
	                ListModel m = l.getModel();
	                int index = l.locationToIndex(e.getPoint());
	                if( index>-1 ) {
	                    l.setToolTipText("Index: " + String.valueOf(index));
	                }
	            }
	        });
	        list.setLayoutOrientation(JList.VERTICAL);
	        buttons.add(scrollPane, gbc);
	        buttons.add(idField, gbc);
	        buttons.add(modify, gbc);
	        buttons.add(exit, gbc);

	        gbc.weighty = 1;
	        add(buttons, gbc);
		}
	}
	
	public class ModifyPane extends JPanel{
		public ModifyPane(){
			setBorder(new EmptyBorder(10, 10, 10, 10));
	        setLayout(new GridBagLayout());

	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.gridwidth = GridBagConstraints.REMAINDER;
	        gbc.anchor = GridBagConstraints.NORTH;

	        add(new JLabel("<html><h1><strong><i>Modifica Domanda</i></strong></h1><hr></html>"), gbc);
	        add(changed, gbc);
	        changed.setVisible(false);

	        gbc.anchor = GridBagConstraints.CENTER;
	        gbc.fill = GridBagConstraints.HORIZONTAL;
	        
	        JPanel buttons = new JPanel(new GridBagLayout());
	        
	        JTextField textField = new JTextField(selected.getText());
	        textField.setText(selected.getText());
	        textField.setToolTipText("Testo");
	        
	        JTextField pointsField = new JTextField(selected.getMaxPoints());
	        pointsField.setText(""+selected.getMaxPoints());
	        pointsField.setToolTipText("Max Points");
	        
	        
	        JButton add = new JButton("Modifica");
	        JButton exit = new JButton("Menu Principale");
	        
	        add.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	    		   /* 
	    		    Date data = null;
					try {
						data = formatter.parse(dateField.getText());
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					changed.setVisible(false);
					changed.setVisible(true);
	        		Dipendente dp = new Dipendente(nameField.getText(), surnameField.getText(), data);
	        		
	        		nameField.setText(" ");
	        		surnameField.setText(" ");
	        		dateField.setText(" ");
	        		
	        		Main.list.set(currentIndex, dp);
	        		try {
						Main.saveDataBase();
					} 
	        		catch (IOException e1) {
						e1.printStackTrace();
					}
	        		list.updateUI();
	        		*/
	            }
	        });
	        
	        exit.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		changed.setVisible(false);
	        		frame.remove(modMenu);
	        		frame.add(menu);
	        		menu.updateUI();
	            }
	        });
	        
	        buttons.add(textField, gbc);
	        buttons.add(pointsField, gbc);
	        
	        buttons.add(add, gbc);
	        buttons.add(exit, gbc);

	        gbc.weighty = 5;
	        add(buttons, gbc);
		}
	}
	
	class MenuActionListener implements ActionListener {
	    protected Subjects subj;

	    public MenuActionListener(Subjects subj) {
	        this.subj = subj;
	    }
	    
	    public Subjects getSubject() {
	    	return this.subj;
	    }
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
	    }
	};
}
