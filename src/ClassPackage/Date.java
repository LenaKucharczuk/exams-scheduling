/**
 * 
 */
package ClassPackage;
/**
 * Obiekt tej klasy reprezentuje date w systemie
 * @author Lena Kucharczuk
 */
public class Date 
{
	private int day;
	private int time;
	private int month;
	private int year;
	
	public Date(int time, int day, int month, int year)
	{
		this.day = day;
		this.time = time;
		this.month = month;
		this.year = year;
	}
	/**
	 * Odpowiada za sprawdzenie, czy termin jest wczesniejszy od podanego
	 * @param d
	 * @return true - termin jest wczesniejszy od podanego
	 * @return false - termin jest pozniejszy od podanego
	 */
	public boolean isEarlierThan(Date d)
	{
		if (year < d.year)
			return true;
		if (year > d.year)
			return false;
		// rok rowny
		if (month < d.month)
			return true;
		if (month > d.month)
			return false;
		// rok i miesiac
		if (day < d.day)
			return true;
		if (day > d.day)
			return false;
		if (time < d.time)
			return true;
		if (time > d.time)
			return false;
		return true; //w tym samym czasie
	}

	public int getDay() {
		return day;
	}

	public int getTime() {
		return time;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}
	
}