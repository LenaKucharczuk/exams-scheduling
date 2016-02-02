/**
 * 
 */
package ClassPackage;

import java.util.*;

/**
 * Przechowuje wszystkie dane systemu (konta, egzaminy, klasy) i odpowiada za logike jego dzialania.
 * @author Lena Kucharczuk
 */
public class Application 
{
	public GUI gUI;
	public AdminGUI adminGUI;
	public SecretaryGUI secretaryGUI;	
	public WorkerGUI workerGUI;	
	
	private Admin_account admin_account;
	private List<Worker_account> worker_accounts;
	private List<Secretary_account> secretary_accounts;
	private List<Classroom> classrooms;	
	private List<Exam> exams;

	public Application()
	{
		gUI = new GUI(this);
		gUI.log_in();	
		
	}
	
	/**
	 * Odpowiada za utworzenie konta administratora i dodaje do systemu sale
	 * @param login
	 * @param password
	 */
	public void admin_create(String login, String password)
	{
		admin_account = new Admin_account(login, password);
		secretary_accounts = new LinkedList<>();
		worker_accounts = new LinkedList<>();
		exams = new LinkedList<>();
		classrooms= new LinkedList<>();
		
		for (int i = 1; i < 100; ++i)
		{
			Classroom cr = new Classroom(i);
			classrooms.add(cr);
		}	
	}
	/**
	 * Zwraca lancuch znakow z informacja o egzaminie o podanym id
	 * @param part - informacja, o ktora jest zapytanie
	 * @param index - id egzaminu
	 */
	public String show_exam(String part, Integer index)
	{
		if(index > exams.size())
			return null;
		
		switch (part)
		{
		case "type":
			return exams.get(index).getType();
			
		case "id":
			return exams.get(index).getExam_id().toString(); 
			
		case "topic":
			return exams.get(index).getTopic();
			
		case "authors":
			if (exams.get(index).isSingle())
				return exams.get(index).getAuthor_1();
			else
				return exams.get(index).getAuthor_1()+", "+exams.get(index).getAuthor_2();
			
		case "description":
			if (exams.get(index).getDescription() != null)
				return exams.get(index).getDescription();
			else
				return "";
			
		case "mark_1":
			if (exams.get(index).getMark_1()!=0.0)
				return exams.get(index).getMark_1().toString();
			else
				return "";
			
		case "mark_2":
			if (exams.get(index).getMark_2()!=0.0)
				return exams.get(index).getMark_2().toString();
			else
				return "";
			
		case "promotor":
			return exams.get(index).getPromoter();
			
		case "leader":
			if (exams.get(index).getLeader() != null)
				return exams.get(index).getLeader();
			else
				return "brak";
			
		case "reviewer":
			if(exams.get(index).getReviewer() != null)
				return exams.get(index).getReviewer();
			else
				return "brak";
			
		case "comission_member": 
			if(exams.get(index).getComission_member()!=null)
				return exams.get(index).getComission_member();
			else
				return "brak";
			
		case "class":
			if (exams.get(index).getClassroom()!=0)
				return exams.get(index).getClassroom().toString();
			else
				return "";
		case "date":
			if (exams.get(index).getPosition_in_schedule() != null)
				return exams.get(index).getPosition_in_schedule().getStart().getTime()+
						":00 - "+exams.get(index).getPosition_in_schedule().getFin().getTime()+":00, "+
						exams.get(index).getPosition_in_schedule().getStart().getDay()+
						"."+exams.get(index).getPosition_in_schedule().getStart().getMonth()+
						"."+exams.get(index).getPosition_in_schedule().getStart().getYear();
			else
				return "";
		}
		return null;
	}	
	/**
	 * Pomocnicza dla wyswietlania kont
	 * @param type - typ uzytkownika
	 * @param index
	 * @return login
	 */
	public String show_login(String type, int index)
	{
		switch (type)
		{
		case "secretary":
			return secretary_accounts.get(index).getLogin();
		case "worker":
			return worker_accounts.get(index).getLogin();
		}
		return null;
	}
	/**
	 * Pomocniczo przy wyswietlaniu kont
	 * @param type
	 * @param index
	 * @return password
	 */
	public String show_password(String type, int index)
	{
		switch (type)
		{
		case "secretary":
			return secretary_accounts.get(index).getPassword();
		case "worker":
			return worker_accounts.get(index).getPassword();
		}
		return null;
	}
	/**
	 * Odpowiada za zniszczenie okna uzytkownika, ktory sie wylogowal i wywolanie okna logowania
	 * @param type
	 */
	public void log_out(String type)
	{
		switch(type)
		{
		case "admin":
			adminGUI.dispose();
			break;
		case "secretary":
			secretaryGUI.dispose();
			break;
		case "worker":
			workerGUI.dispose();
			break;
		}		
		gUI = new GUI(this);
		gUI.log_in();
	}
	/**
	 * Odpowiada za logowanie, rozpoznaje typ uzytkownika i wyswietla odpowiedni interfejs
	 * @param login
	 * @param password
	 * @return true - istnieje konto o podanych danych
	 * @return false - nie istnieje konto o podanych danych
	 */
	public boolean log_in(String login, String password)
	{
		if (admin_account.exists(login, password))
		{
			gUI.dispose();
			adminGUI = new AdminGUI(this);
			return true;
		}
		
		for (Secretary_account s: secretary_accounts)
			 if(s.exists(login, password))
			 {
				 gUI.dispose();
				 secretaryGUI = new SecretaryGUI(this);
				 return true;
			 }
		
		for (Worker_account w: worker_accounts)
			if(w.exists(login, password))
			 {
				 gUI.dispose();
				 workerGUI = new WorkerGUI(this, login);
				 return true;
			 }
		return false;
	}
	
	public int getNumberofExams()
	{
		return exams.size();
	}
	
	public int getNumberofSecretaries()
	{
		return secretary_accounts.size();
	}
	
	public int getNumberofWorkers()
	{
		return worker_accounts.size();
	}
	/**
	 * Odpowiada za rozpoznanie, czy nie wystepuja kolizje w grafikach z podanym terminiem i za wczytanie terminu do grafikow
	 * @param exam_id - id egzaminu, ktoremu przypisywana jest data
	 * @param date
	 * @return true - brak kolizji
	 * @return false - wystapily kolizje
	 */
	public boolean set_exam_date(int exam_id, Position_in_schedule date) 
	{
		int all =0;
		String leader = exams.get(exam_id).getLeader();
		String promotor = exams.get(exam_id).getPromoter();
		String reviewer = exams.get(exam_id).getReviewer();
		String member = exams.get(exam_id).getComission_member();
		Integer classroom = exams.get(exam_id).getClassroom();
		Worker_account l = null, p = null, r = null, m = null;
		Classroom c = null;
		// sprawdz kolizje
		
		for (Classroom cr: classrooms)
		{
			if (classroom.equals(cr.getNumber()));
			{
				++all;
				c = cr;
				if (!c.is_free(date))
					return false;
				else break;
			}
		}
		if (all != 1)
			return false;
		
		for (Worker_account w: worker_accounts)
		{
			if (leader.equals(w.getLogin()))
			{
				++all;
				l = w;
				if (!l.is_free(date))
					return false;
				if (all == 5)
					break;
			}
			if (promotor.equals(w.getLogin()))
			{
				++all;
				p = w;
				if (!p.is_free(date))
					return false;
				if (all == 5)
					break;
			}
			if (reviewer.equals(w.getLogin()))
			{
				++all;
				r = w;
				if (!r.is_free(date))
					return false;
				if (all == 5)
					break;
			}
			if (member.equals(w.getLogin()))
			{
				++all;
				m = w;
				if (!m.is_free(date))
					return false;
				if (all == 5)
					break;
			}
		}
		if (all == 5) // wszystko znalezione
		{
			c.insert_to_schedule(date);
			l.insert_to_schedule(date);
			p.insert_to_schedule(date);
			r.insert_to_schedule(date);
			m.insert_to_schedule(date);
			exams.get(exam_id).set_date(date);
		}	
		
		return true;
	}
	/**
	 * Odpowiada za zwrocenie listy terminow pracownika
	 * @param login
	 * @return lista terminow uzytkownika
	 */
	public List<String> showWorkerSchedule(String login) 
	{
		List<String> dates = new LinkedList<>();
		for (Worker_account w: worker_accounts)
			if(login.equals(w.getLogin()))
			 {
				dates = w.show_schedule();
			 }
		return dates;
	}
	/**
	 * Odpowiada za zwrocenie listy terminow sali
	 * @param class_id
	 * @return lista terminow sali
	 */
	public List<String> show_classroom_schedule(int class_id) 
	{
		List<String> dates = new LinkedList<>();
		for (Classroom c: classrooms)
		{
			if( class_id == c.getNumber())
			{
				dates = c.show_schedule();
			}
		}
		return dates;
	}
	/**
	 * Odpowiada za dodanie konta do systemu
	 * @param login
	 * @param password
	 * @param type
	 * @return true - nie istnieje konto o podanych danych
	 * @return false - istnieje juz konto o podanych danych
	 */
	public boolean add_account(String login, String password, String type)
	{
		for (Secretary_account s: secretary_accounts)
			 if(login.equals(s.getLogin()))
			 {
				 return false;
			 }
		
		for (Worker_account w: worker_accounts)
			if(login.equals(w.getLogin()))
			 {
				return false;
			 }
		
		switch(type)
		{
		case "Sekretarka":
			secretary_accounts.add(new Secretary_account(login, password));
			break;
		case "Pracownik":
			worker_accounts.add(new Worker_account(login, password));
			break;
		}
		return true;
	}
	/**
	 * Odpowiada za usuniecie konta z systemu
	 * @param login
	 * @param password
	 * @return true - istnieje konto o podanych danych
	 * @return false - nie istnieje konto o podanych danych
	 */
	public boolean delete_account(String login, String password)
	{
		for (Secretary_account s: secretary_accounts)
			 if(s.exists(login, password))
			 {
				 secretary_accounts.remove(s);
				 return true;
			 }
		
		for (Worker_account w: worker_accounts)
			if(w.exists(login, password))
			 {
				worker_accounts.remove(w);
				return true;
			 }
		return false;
	}
	/**
	 * Odpowiada za wczytanie do systemu nowego egzaminu
	 * @param topic
	 * @param type
	 * @param author_1
	 * @param author_2
	 * @param promoter
	 * @return 0 - wstawianie powiodlo sie
	 * @return 1 - brak wymaganych danych
	 * @return 2 - bledny login promotora
	 */
	public int add_exam(String topic, String type, String author_1, String author_2, String promoter)
	{		
		boolean single = false;
		if (author_2==null)
		{
			author_2= "";
			single = true;
		}
		if (topic == null || author_1 == null || promoter == null)
			return 1;
		for (Worker_account w: worker_accounts)
			if(promoter.equals(w.getLogin()))
			 {
				exams.add(new Exam(topic, type, author_1, author_2, promoter, exams.size(), single));
				return 0;
			 }
		return 2;
	}
	/**
	 * Odpowiada za wczytanie czlonka komisji
	 * @param login
	 * @param type
	 * @param examID
	 * @return true - czlonek przypisany
	 * @return false - nie istnieje konto o podanych danych
	 */
	public boolean set_comission_member(String login, String type, Integer examID)
	{
		for (Worker_account w: worker_accounts)
		{
			if(login.equals(w.getLogin()))
			 {
				switch(type)
				{
				case "leader":
					exams.get(examID).setLeader(login);
					return true;
				case "member":
					exams.get(examID).set_commission_member(login);
					return true;
				case "reviewer":
					exams.get(examID).setReviewer(login);
					return true;
				}
			 }
		}
		return false;
	}
	/**
	 * Odpowiada za wstawienie oceny (recenzji)
	 * @param type
	 * @param mark
	 * @param examID
	 */
	public void set_mark(String type, Double mark, Integer examID)
	{
		exams.get(examID).add_review_mark(type, mark);
	}
	/**
	 * Odpowiada za przypisanie klasy egzaminowi
	 * @param exam_ID
	 * @param classroom
	 * @return true - istnieje klasa o tym numerze
	 * @return false - nie istnieje klasa o tym numerze
	 */
	public boolean setClassForExam(Integer exam_ID, Integer classroom) 
	{
		for (Classroom c: classrooms)
		{
			if (classroom.equals(c.getNumber()))
			{
				exams.get(exam_ID).setClassroom(classroom);
				return true;
			}
		}
		return false;
	}
	/**
	 * Odpowiada za wstawienie terminu do grafiku pracownika
	 * @param login
	 * @param date
	 * @return true - istnieje konto pracownika o podanych danych
	 * @return false - nie istnieje konto pracownika o podanych danych
	 */
	public boolean insertToWorkerSchedule(Position_in_schedule date, String login)
	{
		for (Worker_account w: worker_accounts)
		{
			if (login.equals(w.getLogin()))
			{
				if(w.is_free(date))
				{
					w.insert_to_schedule(date);
					return true;
				}
				else
					return false;
			}
		}		
		return false;
	}
	/**
	 * Odpowiada za wstawienie terminu do grafiku sali
	 * @param date
	 * @param classNr
	 * @return true - wstawianie powiodlo sie
	 * @return false - kolizja lub nie istnieje taka sala
	 */
	public boolean insertToClassSchedule(Position_in_schedule date, int classNr)
	{
		for (Classroom c: classrooms)
		{
			if (classNr == c.getNumber())
			{				
				return c.insert_to_schedule(date);
			}
		}
		return false;
	}
	/**
	 * Odpowiada za usuniecie pozycji z grafiku pracownika
	 * @param login
	 * @param date
	 */
	public void deleteFromWorkerSchedule(Position_in_schedule date, String login) 
	{
		for (Worker_account w: worker_accounts)
		{
			if (login.equals(w.getLogin()))
			{
				w.deleteFromSchedule(date);
				break;
			}
		}
	}
	/**
	 * Odpowiada za sprawdzenie, czy istnieje sala o podanym numerze
	 * @param classNr
	 * @return true - istnieje sala o podanych danych
	 * @return false - nie istnieje sala o podanych danych
	 */
	public boolean classExists(int classNr)
	{
		for (Classroom c: classrooms)
		{
			if (classNr == c.getNumber())
			{
				return true;
			}
		}
		return false;
	}
}