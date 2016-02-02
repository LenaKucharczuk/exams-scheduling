/**
 * 
 */
package ClassPackage;
/**
 * Reprezentuje pozycje w grafiku skladajaca sie z terminow rozpoczecia i zakonczenia 
 * @author Lena Kucharczuk
 */
public class Position_in_schedule 
{
	private Date start;
	private Date fin;
	/**
	 * Inicjuje tworzony obiekt podanymi terminami rozpoczecia i zakonczenia
	 * @param fin
	 * @param start
	 */
	public Position_in_schedule(Date start, Date fin) 
	{
		this.start = start;
		this.fin = fin;
	}

	public Date getStart() {
		return start;
	}

	public Date getFin() {
		return fin;
	}
	
	
}