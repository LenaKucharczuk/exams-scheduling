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
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * Odpowiada za interfejs pracownika
 * @author Lena Kucharczuk
 */
public class WorkerGUI 
{
	private String type;
	private String login;
	private Application app;
	private JFrame frame;
	private JPanel panel;
	/**
	 * Odpowiada za utworzenie okna z funkcjami dostepnymi dla uzytkownika
	 * @param login
	 * @param app
	 */
	public WorkerGUI(Application app, String login)
	{
		this.login = login;
		this.app = app;
		type = "worker";
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				frame = new JFrame("Terminarz Egzaminów Dyplomowych");
				panel = new JPanel();
				JButton logoutButton = new JButton("wyloguj");
				JButton showButton = new JButton("Wyœwietl grafik");
				
				panel.add(logoutButton);
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
						show_my_schedule();
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
	 * Odpowiada za dodanie terminu do grafiku
	 * @param timeStart
	 * @param timeEnd
	 * @param dayStart
	 * @param dayEnd
	 * @param monthStart
	 * @param monthEnd
	 * @param yearStart
	 * @param yearEnd
	 * @return true - brak kolizji
	 * @return false - wystapily kolizje
	 */
	public boolean insert_to_schedule(Integer timeStart, Integer timeEnd, Integer dayStart, Integer monthStart, Integer yearStart, Integer dayEnd, Integer monthEnd, Integer yearEnd) 
	{
		Date start = new Date(timeStart, dayStart, monthStart, yearStart);
		Date fin = new Date(timeEnd, dayEnd, monthEnd, yearEnd);
		Position_in_schedule date = new Position_in_schedule(start, fin);
		return app.insertToWorkerSchedule(date, login);
	}
	/**
	 * Odpowiada za usuniecie terminu z grafiku
	 * @param timeStart
	 * @param timeEnd
	 * @param dayStart
	 * @param dayEnd
	 * @param monthStart
	 * @param monthEnd
	 * @param yearStart
	 * @param yearEnd
	 */
	public void delete_from_schedule(Integer timeStart, Integer timeEnd, Integer dayStart, Integer monthStart, Integer yearStart, Integer dayEnd, Integer monthEnd, Integer yearEnd) 
	{
		Date start = new Date(timeStart, dayStart, monthStart, yearStart);
		Date fin = new Date(timeEnd, dayEnd, monthEnd, yearEnd);
		Position_in_schedule date = new Position_in_schedule(start, fin);
		app.deleteFromWorkerSchedule(date, login);
	}
	/**
	 * Odpowiada za wyswietlenie grafiku
	 */
	public void show_my_schedule() 
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame showFrame = new JFrame("Harmonogram");
				JPanel modifyPanel = new JPanel(new GridLayout(2, 9));
				
				JButton addButton = new JButton("insert date");
				JButton delButton = new JButton("delete date");
				JTextField stimeText = new JTextField(15);
				JTextField sdayText = new JTextField(15);
				JTextField smonthText = new JTextField(15);
				JTextField syearText = new JTextField(15);
				JTextField etimeText = new JTextField(15);
				JTextField edayText = new JTextField(15);
				JTextField emonthText = new JTextField(15);
				JTextField eyearText = new JTextField(15);
				
				modifyPanel.add(new JLabel("from:"));
				modifyPanel.add(stimeText);
				modifyPanel.add(new JLabel("day:"));
				modifyPanel.add(sdayText);
				modifyPanel.add(new JLabel("month:"));
				modifyPanel.add(smonthText);
				modifyPanel.add(new JLabel("year"));
				modifyPanel.add(syearText);
				modifyPanel.add(addButton);
				modifyPanel.add(new JLabel("to:"));
				modifyPanel.add(etimeText);
				modifyPanel.add(new JLabel("day:"));
				modifyPanel.add(edayText);
				modifyPanel.add(new JLabel("month:"));
				modifyPanel.add(emonthText);
				modifyPanel.add(new JLabel("year"));
				modifyPanel.add(eyearText);
				modifyPanel.add(delButton);
				showFrame.add(modifyPanel, BorderLayout.SOUTH);
				
				showFrame.add(new ShowWorkerSchedulePanel(app, login));
				/**
				 * Odpowiada za reakcje klawisza wstawiajacego termin
				 */
				addButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						Integer timeStart = Integer.parseInt(stimeText.getText());
						Integer timeEnd = Integer.parseInt(etimeText.getText());
						Integer dayStart = Integer.parseInt(sdayText.getText());
						Integer monthStart = Integer.parseInt(smonthText.getText());
						Integer yearStart = Integer.parseInt(syearText.getText());
						Integer dayEnd = Integer.parseInt(edayText.getText());
						Integer monthEnd = Integer.parseInt(emonthText.getText());
						Integer yearEnd = Integer.parseInt(eyearText.getText());
					
						if (!insert_to_schedule(timeStart, timeEnd, dayStart, monthStart,	
								yearStart, dayEnd, monthEnd, yearEnd))
						{
							JOptionPane.showMessageDialog(null, "Collision", "Error", JOptionPane.INFORMATION_MESSAGE);
						}
						else
						{
							showFrame.dispose();
							show_my_schedule();
						}
					}
				});
				/**
				 * Odpowiada za reakcje na wcisniecie klawisza usuwajacego termin
				 */
				delButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						Integer timeStart = Integer.parseInt(stimeText.getText());
						Integer timeEnd = Integer.parseInt(etimeText.getText());
						Integer dayStart = Integer.parseInt(sdayText.getText());
						Integer monthStart = Integer.parseInt(smonthText.getText());
						Integer yearStart = Integer.parseInt(syearText.getText());
						Integer dayEnd = Integer.parseInt(edayText.getText());
						Integer monthEnd = Integer.parseInt(emonthText.getText());
						Integer yearEnd = Integer.parseInt(eyearText.getText());
						
						delete_from_schedule(timeStart,timeEnd,dayStart,monthStart,yearStart,dayEnd,monthEnd,yearEnd);
						showFrame.dispose();
						show_my_schedule();
					}
				});
				
				//FRAME
				Toolkit kit = Toolkit.getDefaultToolkit();
				Dimension screenSize = kit.getScreenSize();
				int screenHeight = screenSize.height;
				int screenWidth = screenSize.width;
				showFrame.setSize(screenWidth*2/3, screenHeight*2/3);
				showFrame.setLocationByPlatform(true);
				showFrame.setVisible(true);
			}
		});
	}
	
	public void dispose()
	{
		frame.dispose();
	}
}
