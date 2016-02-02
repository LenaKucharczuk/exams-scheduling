/**
 * 
 */
package ClassPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * Reprezentuje obiekt odpowiadajacy za interfejs sekretarki
 * @author Lena Kucharczuk
 */
public class SecretaryGUI 
{
	private String type;
	private String mark_type;
	private String member_type;
	private Double mark;
	private Application app;
	private JFrame frame;
	private JPanel panel;
	private String chosen_type;
	/**
	 * Odpowiada za wyswietlenie okna z funkcjami dostepnymi dla sekretarki
	 * @param app
	 */
	public SecretaryGUI(Application app)
	{
		this.app = app;
		type = "secretary";
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				frame = new JFrame("Terminarz Egzaminow Dyplomowych");
				panel = new JPanel();
				JButton logoutButton = new JButton("wyloguj");
				JButton addButton = new JButton("Dodaj egzamin");
				JButton classButton = new JButton("Wyswietl harmonogram sal");
				JButton showButton = new JButton("Wyswietl liste egzaminow");
				
				panel.add(logoutButton);
				panel.add(addButton);
				panel.add(classButton);
				panel.add(showButton);
				frame.add(panel);
				
				logoutButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						app.log_out(type);
					}
				});
				
				showButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						show_exams_list();
					}
				});
				
				addButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						add_exam();
					}
				});
				
				classButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						show_classroom_schedule();
					}
				});
				
				//FRAME
				Toolkit kit = Toolkit.getDefaultToolkit();
				Dimension screenSize = kit.getScreenSize();
				int screenHeight = screenSize.height;
				int screenWidth = screenSize.width;
				frame.setSize(screenWidth*2/3, screenHeight*2/3);
				frame.setLocationByPlatform(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
	/**
	 * Odpowiada za wyswietlenie okna do pobrania danych na temat dodawanego egzaminu
	 */
	public void add_exam() 
	{
		// nowe okienko
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame frameAdd = new JFrame("Dodawanie egzaminu");				
				JPanel panelAdd = new JPanel();
				JPanel panelInfo = new JPanel();
				JButton addButton = new JButton("dodaj egzamin");
				//Container contentPane = frameDel.getContentPane();
						
				panelInfo.add(new JLabel("Podaj dane egzaminu, ktory ma zostac dodany do systemu:"));
				frameAdd.setLayout(new BorderLayout(0, 34));
				frameAdd.add(panelInfo, BorderLayout.NORTH);
											
				// pole na dane
				JTextField examTopic = new JTextField(15);
				JTextField author_1 = new JTextField(15);
				JTextField author_2 = new JTextField(15);
				JTextField promoter = new JTextField(15);
				JComboBox<String> exam_types = new JComboBox<String>();
				exam_types.addItem("In�ynierski");
				exam_types.addItem("Magisterski");
				chosen_type = "In�ynierski"; //default
				
				panelAdd.add(new JLabel("Temat egzaminu: "));
				panelAdd.add(examTopic);
				panelAdd.add(new JLabel("Typ egzaminu: "));
				panelAdd.add(exam_types);				
				panelAdd.add(new JLabel("Autor 1: "));
				panelAdd.add(author_1);
				panelAdd.add(new JLabel("Autor 2: "));
				panelAdd.add(author_2);
				panelAdd.add(new JLabel("Promotor: "));
				panelAdd.add(promoter);
						
				panelAdd.add(addButton);
				frameAdd.add(panelAdd);
					
				/**
				 * Obsluga wcisniecia klawisza "dodaj egzamin"
				 */
				addButton.addActionListener(new ActionListener()
				{		
					public void actionPerformed(ActionEvent event)
					{
						String topic = examTopic.getText();
						String author1 = author_1.getText();
						String author2 = author_2.getText();
						String prom = promoter.getText();
						
						int result = app.add_exam(topic, chosen_type, author1, author2, prom);
						
						if (result == 0)
						{		
							frameAdd.dispose();		
						}		
						else
							if (result == 2)
							{
								JOptionPane.showMessageDialog(null, "Nie istnieje taki pracownik", "Error", JOptionPane.INFORMATION_MESSAGE);
								promoter.setText("");
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Podaj poprawne dane", "Error", JOptionPane.INFORMATION_MESSAGE);
							}
					}
				});								
						
				exam_types.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						chosen_type = exam_types.getSelectedItem().toString();
					}
				});
								
				//FRAME
				Toolkit kit = Toolkit.getDefaultToolkit();
				Dimension screenSize = kit.getScreenSize();
				int screenHeight = screenSize.height;
				int screenWidth = screenSize.width;
				frameAdd.setSize(screenWidth*2/3, screenHeight/3);
				frameAdd.setLocationByPlatform(true);
				frameAdd.setVisible(true);
			}
		});	
	}
	/**
	 * Odpowiada za dodanie czlonka komisji
	 * @param login
	 * @param member_type
	 * @param examID
	 * @return true - istnieje konto o podanych danych
	 * @return false - nie istnieje konto o podanych danych
	 */
	public boolean set_comission_member(String login, String member_type, Integer examID) 
	{
		switch(member_type)
		{
		case "Przewodniczacy":
			return app.set_comission_member(login, "leader", examID);
		case "Czlonek":
			return app.set_comission_member(login, "member", examID);
		case "Recenzent":
			return app.set_comission_member(login, "reviewer", examID);
		}
		return false;
	}

	public void delete_from_classroom_schedule(int nr_classroom, Position_in_schedule date) 
	{
		
	}

	public void dispose()
	{
		frame.dispose();
	}
	/**
	 * Odpowiada za wyswietlenie danych dotyczacych wybranego egzaminu i wprowadzenia dodatkowych danych - ocen, czlonkow komisji, sali, daty
	 * @param exam_ID
	 */
	public void show_exam(Integer exam_ID)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame frameShowExam = new JFrame("Egzamin");
				
				JPanel panelSchedules = new JPanel();
				JPanel panelClass = new JPanel();
				panelSchedules.setLayout(new GridLayout(5, 1));
				JPanel panelAdd = new JPanel(new GridLayout(3, 4));
				JPanel panelSetDate = new JPanel(new GridLayout(3, 1));
				JPanel panelTime = new JPanel();
				JPanel panelDate = new JPanel();
				
				JButton addMemberButton = new JButton("dodaj cz�onka");
				JButton addMarkButton = new JButton("dodaj ocen�");
				JButton promotorButton = new JButton("grafik Promotora");
				JButton leaderButton = new JButton("grafik Przewodnicz�cego");
				JButton reviewerButton = new JButton("grafik Recenzenta");
				JButton memberButton = new JButton("grafik Cz�onka");
				JButton classButton = new JButton("grafik sali");				
				JButton setClassButton = new JButton("przypisz sal�");
				
				// typ czlonka komisji
				JComboBox<String> member_types = new JComboBox<String>();
				member_types.addItem("Przewodniczacy");
				member_types.addItem("Recenzent");
				member_types.addItem("Czlonek");
				
				member_type= "Przewodniczacy"; //default
				
				JTextField loginText = new JTextField(15);
				JTextField setClassText = new JTextField(4);
				JLabel memberLogin = new JLabel("Nazwa cz�onka: ");
				JLabel markLabel = new JLabel("Ocena: ");
				
				JTextField classText = new JTextField(4);
				JLabel classLabel = new JLabel("Sala: ");
				
				// czyja recenzja
				JComboBox<String> mark_types = new JComboBox<String>();
				mark_types.addItem("Promotor");
				mark_types.addItem("Recenzent");
				
				mark_type = "Promotor";
				
				// ocena
				JComboBox<String> marks = new JComboBox<String>();
				marks.addItem("5");
				marks.addItem("4.5");
				marks.addItem("4");
				marks.addItem("3.5");
				marks.addItem("3");
				
				mark = 5.0; //default
								
				panelAdd.add(memberLogin);	//etykieta
				panelAdd.add(loginText);	// text
				panelAdd.add(member_types);	//wybor
				panelAdd.add(addMemberButton); // guzik zatwierdzajacy
				panelAdd.add(markLabel);	// etykieta
				panelAdd.add(marks);		// text
				panelAdd.add(mark_types);	
				panelAdd.add(addMarkButton);
				panelAdd.add(new JLabel("Sala:"));				
				panelAdd.add(setClassText);
				panelAdd.add(setClassButton);
								
				
				panelSchedules.add(promotorButton);
				panelSchedules.add(leaderButton);
				panelSchedules.add(reviewerButton);
				panelSchedules.add(memberButton);
				panelClass.add(classLabel);
				panelClass.add(classText);
				panelClass.add(classButton);
				panelSchedules.add(panelClass);
				
				JTextField start = new JTextField(2);
				JTextField end = new JTextField(2);
				panelTime.add(new JLabel("from:"));
				panelTime.add(start);
				panelTime.add(new JLabel("to"));
				panelTime.add(end);
				
				JTextField dayText = new JTextField(2);
				JTextField monthText = new JTextField(2);
				JTextField yearText = new JTextField(4);
				JButton setDateButton = new JButton("zapisz");
				panelDate.add(new JLabel("day:"));
				panelDate.add(dayText);
				panelDate.add(new JLabel("month:"));
				panelDate.add(monthText);
				panelDate.add(new JLabel("year:"));
				panelDate.add(yearText);
				panelDate.add(setDateButton);
				
				panelSetDate.add(new JLabel("Wyznacz termin egzaminu"));
				panelSetDate.add(panelTime);
				panelSetDate.add(panelDate);
				
				frameShowExam.add(panelSchedules,BorderLayout.EAST);
				frameShowExam.add(panelSetDate,BorderLayout.SOUTH);
				frameShowExam.add(panelAdd,BorderLayout.NORTH);				
				frameShowExam.add(new ShowExamPanel(app, exam_ID), BorderLayout.CENTER);
				
				
				setClassButton.addActionListener(new ActionListener()
				{		
					public void actionPerformed(ActionEvent event)
					{
						Integer classroom = Integer.parseInt(setClassText.getText());
						if (app.setClassForExam(exam_ID, classroom))
						{
							frameShowExam.dispose();
							show_exam(exam_ID);
						}
						else
							JOptionPane.showMessageDialog(null, "Nie ma takiej sali", "B��d", JOptionPane.INFORMATION_MESSAGE);
					}
				});			
				addMemberButton.addActionListener(new ActionListener()
				{		
					public void actionPerformed(ActionEvent event)
					{
						String login = loginText.getText();
						if(!login.isEmpty())
						{
							if (!set_comission_member(login, member_type, exam_ID))
								JOptionPane.showMessageDialog(null, "Nie istnieje pracownik o podanym loginie", "B��d", JOptionPane.INFORMATION_MESSAGE);
							else
							{
								frameShowExam.dispose();
								show_exam(exam_ID);
							}
						}	
					}
				});					
				addMarkButton.addActionListener(new ActionListener()
				{		
					public void actionPerformed(ActionEvent event)
					{
						app.set_mark(mark_type, mark, exam_ID);
						frameShowExam.dispose();
						show_exam(exam_ID);
					}
				});	
				promotorButton.addActionListener(new ActionListener()
				{		
					public void actionPerformed(ActionEvent event)
					{
						String member = app.show_exam("promotor", exam_ID);
						JFrame frameShowSchedule = new JFrame("Grafik promotora");
						frameShowSchedule.add(new ShowWorkerSchedulePanel(app, member));
						frameShowSchedule.pack();				
						frameShowSchedule.setBounds(400, 50, 500, 600);
						frameShowSchedule.setVisible(true);
					}
				});	
				leaderButton.addActionListener(new ActionListener()
				{		
					public void actionPerformed(ActionEvent event)
					{
						String member = app.show_exam("leader", exam_ID);
						JFrame frameShowSchedule = new JFrame("Grafik przewodniczacego");
						frameShowSchedule.add(new ShowWorkerSchedulePanel(app, member));
						frameShowSchedule.pack();				
						frameShowSchedule.setBounds(400, 50, 500, 600);
						frameShowSchedule.setVisible(true);
					}
				});	
				reviewerButton.addActionListener(new ActionListener()
				{		
					public void actionPerformed(ActionEvent event)
					{
						String member = app.show_exam("reviewer", exam_ID);
						JFrame frameShowSchedule = new JFrame("Grafik recenzenta");
						frameShowSchedule.add(new ShowWorkerSchedulePanel(app, member));
						frameShowSchedule.pack();				
						frameShowSchedule.setBounds(400, 50, 500, 600);
						frameShowSchedule.setVisible(true);
					}
				});	
				memberButton.addActionListener(new ActionListener()
				{		
					public void actionPerformed(ActionEvent event)
					{
						String member = app.show_exam("comission_member", exam_ID);
						JFrame frameShowSchedule = new JFrame("Grafik czlonka");
						frameShowSchedule.add(new ShowWorkerSchedulePanel(app, member));
						frameShowSchedule.pack();				
						frameShowSchedule.setBounds(400, 50, 500, 600);
						frameShowSchedule.setVisible(true);
					}
				});	
				classButton.addActionListener(new ActionListener()
				{		
					public void actionPerformed(ActionEvent event)
					{
						Integer classNr = Integer.parseInt(classText.getText());
						JFrame frameShowSchedule = new JFrame("Grafik sali");
						frameShowSchedule.add(new ShowClassSchedulePanel(app, classNr));
						frameShowSchedule.pack();				
						frameShowSchedule.setBounds(400, 50, 500, 600);
						frameShowSchedule.setVisible(true);
					}
				});	
				setDateButton.addActionListener(new ActionListener()
				{		
					public void actionPerformed(ActionEvent event)
					{
						Integer timeStart = Integer.parseInt(start.getText());
						Integer timeEnd = Integer.parseInt(end.getText());
						Integer day = Integer.parseInt(dayText.getText());
						Integer month = Integer.parseInt(monthText.getText());
						Integer year = Integer.parseInt(yearText.getText());
						
						Date start = new Date(timeStart, day, month, year);
						Date fin = new Date(timeEnd, day, month, year);
						Position_in_schedule date = new Position_in_schedule(start, fin);
						
						if (app.set_exam_date(exam_ID, date))
						{
							frameShowExam.dispose();
							show_exam(exam_ID);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Wystapily kolizje w grafikach", "Error", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				});	
				
				member_types.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						member_type = member_types.getSelectedItem().toString();
					}
				});
				
				mark_types.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						mark_type = mark_types.getSelectedItem().toString();
					}
				});
				
				marks.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						mark = Double.parseDouble(marks.getSelectedItem().toString());
					}
				});
								
				//FRAME
				frameShowExam.pack();			
				frameShowExam.setBounds(200, 50, 800, 600);
				frameShowExam.setVisible(true);
			}
		});
	}
	/**
	 * Odpowiada za wyswietlenie listy egzaminow figurujacych w systemie
	 */
	public void show_exams_list() 
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame frameShowExams = new JFrame("Lista egzaminow");
				JPanel panelShowExams = new JPanel();
				JButton showExamButton = new JButton("Wyswietl");
				
				JTextField examID = new JTextField(15);
				panelShowExams.add(new JLabel("ID egzaminu: "));
				panelShowExams.add(examID);
				panelShowExams.add(showExamButton);
				frameShowExams.add(panelShowExams,BorderLayout.NORTH);				
				frameShowExams.add(new ShowExamsPanel(app), BorderLayout.CENTER);
				
				showExamButton.addActionListener(new ActionListener()
				{		
					public void actionPerformed(ActionEvent event)
					{
						if (!examID.getText().isEmpty())
						{	
							Integer exam_ID = Integer.parseInt(examID.getText());
							
							if (exam_ID < app.getNumberofExams()) // sprawdzic czy dzizala
							{
								show_exam(exam_ID);	
							}		
							else
							{
								JOptionPane.showMessageDialog(null, "Nie istnieje egzamin o takim id", "Error", JOptionPane.INFORMATION_MESSAGE);
								examID.setText("");
							}
						}
					}
				});	
								
				//FRAME
				frameShowExams.pack();				
				frameShowExams.setBounds(200, 50, 800, 600);
				frameShowExams.setVisible(true);
			}
		});
	}
	/**
	 * Odpowiada za wyswietlenie harmonogramu wybranej sali
	 */
	public void show_classroom_schedule() 
	{
		int class_id=0;
		//okienko- pobieram id, wyswietlam harmonogram
		JFrame frameShow = new JFrame("Harmonogram sal");	
		JPanel panelNr = new JPanel(new GridLayout(1, 10));
		JPanel panelDate = new JPanel(new GridLayout(2, 10));
		JPanel panelSet = new JPanel();
		JTextField textNr = new JTextField(4);
		JButton buttonAdd = new JButton("Insert date");
		JButton buttonDel = new JButton("Free date");
		
		JPanel panelSchedule = new JPanel();
		JTextField textNrSchedule = new JTextField(4);
		JButton buttonShow = new JButton("Schedule");
		panelSchedule.add(new JLabel("Show schedule of class:"));
		panelSchedule.add(textNrSchedule);
		panelSchedule.add(buttonShow);
		
		panelNr.add(new JLabel(" class nr:"));
		panelNr.add(textNr);
		
		JTextField fromText = new JTextField(2);
		JTextField toText = new JTextField(2);
		JTextField dayText = new JTextField(2);
		JTextField monthText = new JTextField(2);
		JTextField yearText = new JTextField(4);
		
		panelDate.add(new JLabel("from"));
		panelDate.add(fromText);
		panelDate.add(new JLabel("to"));
		panelDate.add(toText);
		panelDate.add(new JLabel("day"));
		panelDate.add(dayText);
		panelDate.add(new JLabel("month"));
		panelDate.add(monthText);
		panelDate.add(new JLabel("year"));
		panelDate.add(yearText);
		panelDate.add(buttonAdd);
		panelDate.add(buttonDel);
		
		panelSet.add(panelNr);
		panelSet.add(panelDate);
		
		frameShow.add(panelSet, BorderLayout.NORTH);
		frameShow.add(panelSchedule);
		//frameShow.add(new ShowClassSchedulePanel(app),BorderLayout.CENTER);
				
		
		buttonAdd.addActionListener(new ActionListener()
		{		
			public void actionPerformed(ActionEvent event)
			{
				Integer clasNr = Integer.parseInt(textNr.getText());
				Integer timeStart = Integer.parseInt(fromText.getText());
				Integer timeEnd = Integer.parseInt(toText.getText());
				Integer day = Integer.parseInt(dayText.getText());
				Integer month = Integer.parseInt(monthText.getText());
				Integer year = Integer.parseInt(yearText.getText());
				
				Date start = new Date(timeStart, day, month, year);
				Date fin = new Date(timeEnd, day, month, year);
				Position_in_schedule date = new Position_in_schedule(start, fin);
				
				if (app.insertToClassSchedule(date, clasNr))
				{
					textNr.setText("");
					fromText.setText("");
					toText.setText("");
					dayText.setText("");
					monthText.setText("");
					yearText.setText("");
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Wystapily kolizje w grafikach", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});	
		
		buttonDel.addActionListener(new ActionListener()
		{		
			public void actionPerformed(ActionEvent event)
			{
				
			}
		});
		
		buttonShow.addActionListener(new ActionListener()
		{		
			public void actionPerformed(ActionEvent event)
			{
				Integer classNr = Integer.parseInt(textNrSchedule.getText());
				if (app.classExists(classNr))
				{
					JFrame frameShowSchedule = new JFrame("Grafik sali");
					frameShowSchedule.add(new ShowClassSchedulePanel(app, classNr));
					frameShowSchedule.pack();				
					frameShowSchedule.setBounds(400, 50, 500, 600);
					frameShowSchedule.setVisible(true);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Nie ma takiej sali", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		//FRAME
		frameShow.pack();
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		frameShow.setSize(screenWidth*2/3, screenHeight/3);
		frameShow.setLocationByPlatform(true);
		frameShow.setVisible(true);
	}
}
/**
 * Panel wyswietlajacy liste egzaminow w systemie
 */
class ShowExamsPanel extends JComponent
{
	Application app;
	public static final int MESSAGE_X = 30;
	public static final int MESSAGE_Y = 30;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 200;
	
	public ShowExamsPanel(Application app) 
	{
		this.app = app;
	}

	public void paintComponent(Graphics g)
	{
		final int x = 200;
		int y = 20;
		//g.drawString("SEKRETARKI", MESSAGE_X, MESSAGE_Y-10);
		g.drawString("ID:", MESSAGE_X, MESSAGE_Y+y);
		g.drawString("Temat:", MESSAGE_X+50, MESSAGE_Y+y);
		g.drawString("Typ:", MESSAGE_X+400, MESSAGE_Y+y);
		g.drawString("Promotor:", MESSAGE_X+500, MESSAGE_Y+y);
		g.drawString("Autorzy:", MESSAGE_X+600, MESSAGE_Y+y);
		
		for (int i =0; i < app.getNumberofExams(); ++i)
		{
			y +=20;
			g.drawString(app.show_exam("id", i), MESSAGE_X, MESSAGE_Y+y);
			g.drawString(app.show_exam("topic", i), MESSAGE_X+50, MESSAGE_Y+y);
			g.drawString(app.show_exam("type", i), MESSAGE_X+400, MESSAGE_Y+y);
			g.drawString(app.show_exam("promotor", i), MESSAGE_X+500, MESSAGE_Y+y);
			g.drawString(app.show_exam("authors", i), MESSAGE_X+600, MESSAGE_Y+y);
		}
	}
	
	public Dimension getPreferredSize() 
	{ 
		return new Dimension(DEFAULT_WIDTH,	DEFAULT_HEIGHT);
	}
}
/**
 * Panel wyswietlajacy harmonogram wybranej sali
 */
class ShowClassSchedulePanel extends JComponent
{
	Application app;
	Integer class_id;
	public static final int MESSAGE_X = 30;
	public static final int MESSAGE_Y = 30;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 200;
	
	public ShowClassSchedulePanel(Application app, int class_id) 
	{
		this.class_id = class_id;
		this.app = app;
	}
	
	public void paintComponent(Graphics g)
	{
		List<String> schedule = app.show_classroom_schedule(class_id);
		final int x = 120;
		int y = 20;
		
		g.drawString("Grafik sali "+class_id.toString(), MESSAGE_X, MESSAGE_Y-10);
		
		for (String s : schedule)
		{				
			g.drawString(s, MESSAGE_X+x, MESSAGE_Y+y);
			y += 20;
		}
	}
	
	public Dimension getPreferredSize() 
	{ 
		return new Dimension(DEFAULT_WIDTH,	DEFAULT_HEIGHT);
	}
}
/**
 * Panel odpowiedzialny za wyswietlanie grafiku pracownika
 */
class ShowWorkerSchedulePanel extends JComponent
{
	Application app;
	String login;
	public static final int MESSAGE_X = 30;
	public static final int MESSAGE_Y = 30;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 200;
	
	public ShowWorkerSchedulePanel(Application app, String login) 
	{
		this.login = login;
		this.app = app;
	}
	
	public void paintComponent(Graphics g)
	{
		List<String> schedule = app.showWorkerSchedule(login);
		final int x = 120;
		int y = 20;
		
		g.drawString("Grafik "+login, MESSAGE_X, MESSAGE_Y-10);
		
		for (String s : schedule)
		{				
			g.drawString(s, MESSAGE_X+x, MESSAGE_Y+y);
			y += 20;
		}
	}
	
	public Dimension getPreferredSize() 
	{ 
		return new Dimension(DEFAULT_WIDTH,	DEFAULT_HEIGHT);
	}
}
/**
 * Panel odpowiedzialny za wyswietlenie szczegolowych informacji o egzaminie
 */
class ShowExamPanel extends JComponent
{
	Application app;
	Integer examID;
	public static final int MESSAGE_X = 30;
	public static final int MESSAGE_Y = 30;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 200;
	
	public ShowExamPanel(Application app, Integer examID) 
	{
		this.app = app;
		this.examID = examID;
	}

	public void paintComponent(Graphics g)
	{
		final int x = 120;
		int y = 20;
		g.drawString("EGZAMIN", MESSAGE_X, MESSAGE_Y-10);		
		g.drawString("ID:", MESSAGE_X, MESSAGE_Y+y);
		g.drawString(app.show_exam("id", examID), MESSAGE_X+x, MESSAGE_Y+y);
		y += 20;
		g.drawString("Temat:", MESSAGE_X, MESSAGE_Y+y);
		g.drawString(app.show_exam("topic", examID), MESSAGE_X+x, MESSAGE_Y+y);
		y += 20;
		g.drawString("Typ:", MESSAGE_X, MESSAGE_Y+y);
		g.drawString(app.show_exam("type", examID), MESSAGE_X+x, MESSAGE_Y+y);
		y += 20;
		g.drawString("Promotor:", MESSAGE_X, MESSAGE_Y+y);
		g.drawString(app.show_exam("promotor", examID), MESSAGE_X+x, MESSAGE_Y+y);
		y += 20;
		g.drawString("Autorzy:", MESSAGE_X, MESSAGE_Y+y);		
		g.drawString(app.show_exam("authors", examID), MESSAGE_X+x, MESSAGE_Y+y);
		y += 20;
		g.drawString("Opis:", MESSAGE_X, MESSAGE_Y+y);		
		g.drawString(app.show_exam("description", examID), MESSAGE_X+x, MESSAGE_Y+y);		
		y += 20;
		g.drawString("Ocena promotora:", MESSAGE_X, MESSAGE_Y+y);		
		g.drawString(app.show_exam("mark_1", examID), MESSAGE_X+x, MESSAGE_Y+y);
		y += 20;
		g.drawString("Ocena recenzenta:", MESSAGE_X, MESSAGE_Y+y);		
		g.drawString(app.show_exam("mark_2", examID), MESSAGE_X+x, MESSAGE_Y+y);
		
		y += 40;
		g.drawString("KOMISJA", MESSAGE_X, MESSAGE_Y+y);
		y += 20;
		g.drawString("Przewodniczacy:", MESSAGE_X, MESSAGE_Y+y);
		g.drawString(app.show_exam("leader", examID), MESSAGE_X+x, MESSAGE_Y+y);
		y += 20;
		g.drawString("Recenzent:", MESSAGE_X, MESSAGE_Y+y);		
		g.drawString(app.show_exam("reviewer", examID), MESSAGE_X+x, MESSAGE_Y+y);
		y += 20;
		g.drawString("Czlonek komisji:", MESSAGE_X, MESSAGE_Y+y);
		g.drawString(app.show_exam("comission_member", examID), MESSAGE_X+x, MESSAGE_Y+y);	
		y += 40;
		g.drawString("Sala:", MESSAGE_X, MESSAGE_Y+y);		
		g.drawString(app.show_exam("class", examID), MESSAGE_X+x, MESSAGE_Y+y);
		y += 20;
		g.drawString("Data:", MESSAGE_X, MESSAGE_Y+y);		
		g.drawString(app.show_exam("date", examID), MESSAGE_X+x, MESSAGE_Y+y);
	}
	
	public Dimension getPreferredSize() 
	{ 
		return new Dimension(DEFAULT_WIDTH,	DEFAULT_HEIGHT);
	}
}

