/**
 * 
 */
package ClassPackage;

import java.util.*;
/**
 * Reprezentuje grafik- liste pozycji w grafiku
 * @author Lena Kucharczuk
 */
public class Schedule 
{
	private List<Position_in_schedule> positions;
	
	public Schedule() 
	{
		positions = new LinkedList<>();
	}
	/**
	 * Odpowiada za wstawienie terminu do grafiku
	 * @param date
	 * @return true - brak kolizji
	 * @return false - wystapila kolizja
	 */
	public boolean insert_to_schedule(Position_in_schedule date) 
	{
		if (is_free(date))
		{
			positions.add(date);
			return true;
		}
		else
			return false;
	}
	/**
	 * Odpowiada za usuniecie terminu z grafiku
	 * @param date
	 * @return true - powiodlo sie usuwanie
	 * @return false - nie powiodlo sie usuwanie
	 */
	public boolean delete_from_schedule(Position_in_schedule date) 
	{
		Date start = date.getStart();
		Date fin = date.getFin();
		
		for (Position_in_schedule p: positions)
		{
			if (start.isEarlierThan(p.getStart()))
			{
				if ((p.getStart()).isEarlierThan(fin))
				{
					// usuwam cz. wspolna
					
				}
			}
			else
			{
				if (start.isEarlierThan(p.getFin()))
				{
					//usuwam cz. ws.
				}
			}
		}
		return false;
	}
	/**
	 * Odpowiada za zwrocenie listy terminow, pomocnicza przy wyswietlaniu
	 * @return lista terminow
	 */
	public List<String> show_schedule() 
	{
		List<String> dates = new LinkedList<>();
		
		for (Position_in_schedule p : positions)
		{
			String date = new String(
					p.getStart().getTime()+":00, "+	
					p.getStart().getDay()+"."+p.getStart().getMonth()+"."+p.getStart().getYear()+" - "+
					p.getFin().getTime()+":00, "+
					p.getFin().getDay()+"."+p.getFin().getMonth()+"."+p.getFin().getYear()
					);
			dates.add(date);
		}
		
		return dates;
	}
	/**
	 * Odpowiada za sprawdzenie, czy podany termin nie koliduje z innymi
	 * @param date
	 * @return true - termin jest wolny
	 * @return false - termin jest zajety
	 */
	public boolean is_free(Position_in_schedule date) 
	{
		Date start = date.getStart();
		Date fin = date.getFin();
		
		for (Position_in_schedule p: positions)
		{
			if (p.equals(date))
				return false;
			if (start.isEarlierThan(p.getStart()))
			{
				if ((p.getStart()).isEarlierThan(fin))
					return false;
			}
			else
			{
				if (start.isEarlierThan(p.getFin()))
					return false;
			}
		}		
		return true;
	}
}