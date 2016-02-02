/**
 * 
 */
package ClassPackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.util.*;

public class GUI 
{
	Application app;
	private JFrame frame;
	private JPanel panel;
	private String login;
	private String password;
	private static final int DEFAULT_WIDTH = 1000;
	private static final int DEFAULT_HEIGHT = 700;
		
	public GUI(Application app)
	{
		this.app = app;
	}
	
	/**
	 * Odpowiada za wyswietlenie okna logowania
	 */
	public void log_in()
	{
		// wyswietl okno logowania
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				frame = new JFrame("Terminarz Egzaminów Dyplomowych - Logowanie");				
				panel = new JPanel();
				JPanel panelInfo = new JPanel();
				JButton loginButton = new JButton("log in");
				Container contentPane = frame.getContentPane();
				
				panelInfo.add(new JLabel("In order to log in to the system type your login and password"));
				frame.setLayout(new BorderLayout(0, 34));
				frame.add(panelInfo, BorderLayout.NORTH);
				
				// pola na login i haslo
				JTextField loginText = new JTextField(15);
				JPasswordField passwordText = new JPasswordField(15);
				panel.add(new JLabel("Login: "));
				panel.add(loginText);
				panel.add(new JLabel("Password: "));
				panel.add(passwordText);
				
				panel.add(loginButton);
				frame.add(panel);
				
				/**
				 * Reakcja na wcisniecie klawisza logowania
				 */			
				loginButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						login = loginText.getText();
						password = new String(passwordText.getPassword());
						if (app.log_in(login, password) == false)
						{
							JOptionPane.showMessageDialog(null, "Incorrect data", "Error", JOptionPane.INFORMATION_MESSAGE);
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
				frame.setSize(screenWidth*2/3, screenHeight/3);
				frame.setLocationByPlatform(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
	
	public void dispose()
	{
		frame.dispose(); // wywo³anie metody dispose dla pola sk³adowego
	}
}