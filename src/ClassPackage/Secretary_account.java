/**
 * 
 */
package ClassPackage;

/**
 * Reprezentuje konto sekretarki
 * @author Lena Kucharczuk
 */
public class Secretary_account 
{
	private String login;
	private String password;
	/**
	 * Inicjuje nowy obiekt podanymi wartosciami
	 * @param login
	 * @param password
	 */
	public Secretary_account(String login, String password) 
	{
		this.login = login;
		this.password = password;
	}
	
	public String getLogin() 
	{
		return login;
	}

	public String getPassword() 
	{
		return password;
	}
	/**
	 * Odpowiada za sprawdzenie, czy istnieje konto sekretarki o podanych loginie i hasle
	 * @param login
	 * @param password
	 * @return true - istnieje konto sekretarki o podanych danych
	 * @return false - nie istnieje konto sekretarki o podanych danych
	 */
	public boolean exists(String login, String password)
	{
		return login.equals(this.login) && password.equals(this.password);
	}
}