/**
 * 
 */
package ClassPackage;
/**
 * Odpowiada za sprawdzenie, czy istnieje konto administratora o podanych loginie i hasle
 * @author Lena Kucharczuk
 */
public class Exam 
{
	private String topic;
	private Double mark_1;
	private Double mark_2;
	private String description;
	private String type;
	private String author_1;
	private String author_2;
	private Integer exam_id;
	private Position_in_schedule date;
	
	private Integer classroom;
	
	//KOMISJA - zamienic na obiekty
	private String leader;
	private String promoter;
	private String comission_member;
	private String reviewer;
	
	
	private boolean single;
	
	/**
	 * Odpowiada za poinformowanie, czy praca ma dwoch czy jednego autora 
	 * @return true - praca ma jednego autora
	 * @return false - jest dwoje autorow
	 */
	public boolean isSingle()
	{
		return single;
	}
	/**
	 * Odpowiada za wstawienie oceny do egzaminu
	 * @param type
	 * @param mark
	 */
	public void add_review_mark(String type, Double mark) 
	{
		switch(type)
		{
		case "Promotor":
			mark_1=mark;
			break;
		case "Recenzent":
			mark_2 = mark;
			break;
		}		
	}
	
	public void setLeader(String leader) {
		this.leader = leader;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public void set_commission_member(String newMemberLogin) 
	{
		comission_member = newMemberLogin;
	}

	public void set_date(Position_in_schedule date) 
	{
		this.date = date;
	}

	public void show_comission_schedules() 
	{
		
	}

	public String getTopic() 
	{
		return topic;
	}

	public Double getMark_1() 
	{
		return mark_1;
	}

	public Double getMark_2() 
	{
		return mark_2;
	}

	public String getDescription() 
	{
		return description;
	}

	public String getType() 
	{
		return type;
	}

	public String getAuthor_1() 
	{
		return author_1;
	}

	public String getAuthor_2() 
	{
		return author_2;
	}

	public Integer getExam_id() 
	{
		return exam_id;
	}

	public Position_in_schedule getPosition_in_schedule() 
	{
		return date;
	}

	public Integer getClassroom() 
	{
		return classroom;
	}

	public String getLeader() 
	{
		return leader;
	}

	public String getPromoter() 
	{
		return promoter;
	}

	public String getComission_member() 
	{
		return comission_member;
	}

	public String getReviewer() 
	{
		return reviewer;
	}
	/**
	 * Inicjacja pol obiektu
	 */
	public Exam(String topic, String type, String author_1, String author_2, String promoter, int exam_id, boolean signle) 
	{
		this.single = single;
		this.topic = topic;
		this.type = type;
		this.author_1 = author_1;
		this.author_2 = author_2;
		this.promoter = promoter;
		this.exam_id = exam_id;
		mark_1 = mark_2 = 0.0;
		description = null;
		reviewer = null;
		comission_member = null;
		leader = null;
		classroom =0;
		
	}

	public void setClassroom(Integer classroom) 
	{
		this.classroom = classroom;
	}
	
	
}