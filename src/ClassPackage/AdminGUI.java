/**
 * 
 */
package ClassPackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Odpowiada za sprawdzenie, czy istnieje konto administratora o podanym loginie i hasle
 * @author Lena Kucharczuk
 */
public class AdminGUI 
{	
	private String type;
	String chosen_type;
	private Application app;
	private JFrame frame;
	private JPanel panel;
	
	/**
	 * Tworzy okno z funkcjami dostepnymi dla admina
	 * @param app - obiekt aplikacji, ktora stworzyla obiekt tej klasy
	 */
	public AdminGUI(Application app)
	{
		this.app = app;
		type = "admin";
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				frame = new JFrame("Terminarz Egzaminów Dyplomowych - Administracja");
				panel = new JPanel();
				JButton logoutButton = new JButton("log out");
				JButton addButton = new JButton("Create account");
				JButton deleteButton = new JButton("Delete account");
				JButton showButton = new JButton("Show list of accounts");
				
				panel.add(logoutButton);
				panel.add(addButton);
				panel.add(deleteButton);
				panel.add(showButton);
				frame.add(panel);
				
				/**
				 * Obsluga wcisniecia przez admina przycisku "wyloguj"
				 */
				logoutButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						app.log_out(type);
					}
				});
				/**
				 * Obsluga wcisniecia przez admina przycisku "Wyswietl liste kont"
				 */
				showButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						show_accounts();
					}
				});
				/**
				 * Obsluga wcisniecia przez admina przycisku "Dodaj konto"
				 */
				addButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						add_account();
					}
				});
				/**
				 * Obsluga wcisniecia przez admina przycisku "Skasuj konto"
				 */
				deleteButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						delete_account();
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
	 * Wywolywana, gdy admin chce dodac nowe konto, tworzy okno umozliwiajace mu wpisanie danych nowego uzytkownika i okreslenie jego typu
	 */
	public void add_account() 
	{
		// nowe okienko
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame frameDel = new JFrame("Dodawanie konta");				
				JPanel panelDel = new JPanel();
				JPanel panelInfo = new JPanel();
				JButton delButton = new JButton("submit");
				//Container contentPane = frameDel.getContentPane();
				
				panelInfo.add(new JLabel("Type data of a new user:"));
				frameDel.setLayout(new BorderLayout(0, 34));
				frameDel.add(panelInfo, BorderLayout.NORTH);
				
				// typ uzytkownika
				JComboBox<String> account_types = new JComboBox<String>();
				account_types.addItem("Worker");
				account_types.addItem("Secretary");
							
				chosen_type = "Pracownik"; // domyœlnie
				
				// pole na dane
				JTextField loginText = new JTextField(15);
				JTextField passwordText = new JTextField(15);
				panelDel.add(new JLabel("Login: "));
				panelDel.add(loginText);
				panelDel.add(new JLabel("Password: "));
				panelDel.add(passwordText);
				panelDel.add(new JLabel("Type: "));
				panelDel.add(account_types);
				
				panelDel.add(delButton);
				frameDel.add(panelDel);
				
				/**
				 * Obsluga klawisza potwierdzajacego dodanie nowego uzytkownika do systemu
				 */
				delButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						String login = loginText.getText();
						String password = passwordText.getText();
					
						if (app.add_account(login, password, chosen_type))
						{
							loginText.setText("");
							passwordText.setText("");					
						}
						else
						{
							JOptionPane.showMessageDialog(null, "User with such login already exists", "Error", JOptionPane.INFORMATION_MESSAGE);
							loginText.setText("");
							passwordText.setText("");
						}
					}
				});
				
				account_types.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						chosen_type = account_types.getSelectedItem().toString();
					}
				});
						
				//FRAME
				Toolkit kit = Toolkit.getDefaultToolkit();
				Dimension screenSize = kit.getScreenSize();
				int screenHeight = screenSize.height;
				int screenWidth = screenSize.width;
				frameDel.setSize(screenWidth*2/3, screenHeight/3);
				frameDel.setLocationByPlatform(true);
				frameDel.setVisible(true);
			}
		});
	}
	/**
	 * Metoda wywolywana, gdy admin chce skasowac istniejace konto
	 */
	public void delete_account() 
	{
		// nowe okienko
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame frameDel = new JFrame("Kasowanie konta");				
				JPanel panelDel = new JPanel();
				JPanel panelInfo = new JPanel();
				JButton delButton = new JButton("submit");
				
				panelInfo.add(new JLabel("Type data of a user who will be deleted:"));
				frameDel.setLayout(new BorderLayout(0, 34));
				frameDel.add(panelInfo, BorderLayout.NORTH);
				
				// pole na login
				JTextField loginText = new JTextField(15);
				JTextField passwordText = new JTextField(15);
				panelDel.add(new JLabel("Login: "));
				panelDel.add(loginText);
				panelDel.add(new JLabel("Password: "));
				panelDel.add(passwordText);
				
				panelDel.add(delButton);
				frameDel.add(panelDel);
				
				// pobranie loginu i hasla
				delButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						String login = loginText.getText();
						String password = passwordText.getText();
						if (app.delete_account(login, password))
						{
							frameDel.dispose();
						}
						else
						{
							JOptionPane.showMessageDialog(null, "There is no user with given data", "Error", JOptionPane.INFORMATION_MESSAGE);
							loginText.setText("");
							passwordText.setText("");
						}
					}
				});
				
				//FRAME
				Toolkit kit = Toolkit.getDefaultToolkit();
				Dimension screenSize = kit.getScreenSize();
				int screenHeight = screenSize.height;
				int screenWidth = screenSize.width;
				frameDel.setSize(screenWidth*2/3, screenHeight/3);
				frameDel.setLocationByPlatform(true);
				frameDel.setVisible(true);
			}
		});
	}
	/**
	 * Odpowiada za wyswietlenie okna z lista kont uzytkownikow
	 */
	public void show_accounts()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame frameDel = new JFrame("Accounts List");
				frameDel.add(new ShowPanel(app));
								
				//FRAME
				frameDel.pack();				
				frameDel.setBounds(400, 50, 500, 600);
				frameDel.setVisible(true);
			}
		});
	}
	
	public void dispose()
	{
		frame.dispose();
	}	
}

/**
 * Panel wyswietlajacy liste kont w systemie
 */
class ShowPanel extends JComponent
{
	Application app;
	public static final int MESSAGE_X = 30;
	public static final int MESSAGE_Y = 30;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 200;
	
	public ShowPanel(Application app) 
	{
		this.app = app;
	}

	public void paintComponent(Graphics g)
	{
		final int x = 200;
		int y = 20;
		g.drawString("SCRETARIES", MESSAGE_X, MESSAGE_Y-10);
		g.drawString("Login:", MESSAGE_X, MESSAGE_Y+y);
		g.drawString("Password:", MESSAGE_X+x, MESSAGE_Y+y);
		//sekretarki
		for (int i =0; i < app.getNumberofSecretaries(); ++i)
		{
			y +=20;
			g.drawString(app.show_login("secretary", i), MESSAGE_X, MESSAGE_Y+y);
			g.drawString(app.show_password("secretary", i), MESSAGE_X+x, MESSAGE_Y+y);			
		}
		y +=40;
		g.drawString("WORKERS", MESSAGE_X, MESSAGE_Y+y-10);
		y +=20;
		g.drawString("Login:", MESSAGE_X, MESSAGE_Y+y);
		g.drawString("Password:", MESSAGE_X+x, MESSAGE_Y+y);
		//pracownicy
		for (int i =0; i < app.getNumberofWorkers(); ++i)
		{
			y +=20;
			g.drawString(app.show_login("worker", i), MESSAGE_X, MESSAGE_Y+y);
			g.drawString(app.show_password("worker", i), MESSAGE_X+x, MESSAGE_Y+y);			
		}
	}
	
	public Dimension getPreferredSize() 
	{ 
		return new Dimension(DEFAULT_WIDTH,	DEFAULT_HEIGHT);
	}
}
