/**
 * 
 */
package ClassPackage;

import java.util.List;
/**
 * Reprezentuje konto pracownika
 * @author Lena Kucharczuk
 */
public class Worker_account 
{
	private String login;
	private String password;
	private Schedule schedule;
	
	
	public Worker_account(String login, String password) 
	{
		this.login = login;
		this.password = password;
		schedule = new Schedule();
	}

	public boolean exists(String login, String password)
	{
		return login.equals(this.login) && password.equals(this.password);
	}
	
	public List<String> show_schedule() 
	{
		return schedule.show_schedule();
	}

	public boolean is_free(Position_in_schedule date) 
	{
		return schedule.is_free(date);
	}

	public void insert_to_schedule(Position_in_schedule date) 
	{
		schedule.insert_to_schedule(date);
	}

	public String getLogin() 
	{
		return login;
	}

	public String getPassword() 
	{
		return password;
	}

	public void deleteFromSchedule(Position_in_schedule date) 
	{
		schedule.delete_from_schedule(date);		
	}
}