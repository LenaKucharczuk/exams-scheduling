package ClassPackage;

import java.awt.SystemColor;

/**
 * Obiekt <code>Admin_account</code> reprezentuje konto administratora.
 * @author Lena Kucharczuk
 */
public class Admin_account 
{
	private String login;
	private String password;
	
	
	/**
	 * Odpowiada za sprawdzenie, czy istnieje konto administratora o podanych loginie i hasle
	 * @param login
	 * @param password
	 * @return true - istnieje konto administratora o podanych danych
	 * @return false - nie istnieje konto administratora o podanych danych
	 */
	public boolean exists(String login, String password)
	{	
		return login.equals(this.login) && password.equals(this.password);
	}
	
	/**
	 * Inicjuje obiekt danym loginem i haslem
	 * @param login
	 * @param password
	 */
	public Admin_account(String login, String password)
	{
		this.login = login;
		this.password = password;
	}
}