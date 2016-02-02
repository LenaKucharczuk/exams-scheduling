/**
 * 
 */
package ClassPackage;

import java.util.List;

/**
 * Odpowiada za przechowywanie informacji o danej klasie- jej rozkladzie zajec i numerze
 * @author Lena Kucharczuk
 */
public class Classroom 
{
	private int number;
	private Schedule schedule;
		
	public Classroom(int number) 
	{
		this.number = number;
		schedule = new Schedule();
	}
	/**
	 * Odpowiada za sprawdzenie, czy istnieje konto administratora o podanych loginie i hasle
	 * @return number - numer sali
	 */
	public int getNumber()
	{
		return number;
	}

	public Classroom getClassroom()
	{
		return this;
	}
	/**
	 * Odpowiada za sprawdzenie, czy istnieje konto administratora o podanych loginie i hasle
	 * @return lista terminow
	 */
	public List<String> show_schedule() 
	{
		return schedule.show_schedule();
	}
	/**
	 * Odpowiada za sprawdzenie, czy sala jest wolna w podanym terminie
	 * @param date
	 * @return true - brak kolizji
	 * @return false - wystapila kolizja
	 */
	public boolean is_free(Position_in_schedule date) 
	{
		return schedule.is_free(date);
	}
	
	/**
	 * Odpowiada za dodanie do grafiku podanej daty
	 * @param date - data dodawana do grafiku sali
	 * @return true - brak kolizji
	 * @return false - wystapila kolizja
	 */
	public boolean insert_to_schedule(Position_in_schedule date) 
	{
		return schedule.insert_to_schedule(date);
	}
}