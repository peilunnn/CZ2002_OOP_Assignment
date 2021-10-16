package classes;

import java.io.Serializable;

/**
 * Represents a staff in the restaurant.
 * Every staff has an id, name, gender and job title.
 * @author Sean
 * @version 1.0
 * @since 2016-10-31
 */
public class Staff implements Serializable{
	
	private static final long serialVersionUID = -3686894603777756471L;
	
	/**
	 * Unique staff id identifying a unique staff instance
	 */
	private int empId;
	
	/**
	 * Name of staff
	 */
	private String name;
	
	/**
	 * Gender of staff. m for male, f for female
	 */
	private char gender;
	
	/**
	 * Job title of staff
	 */
	private String jobTitle;
	
	/**
	 * returns empId, Name and jobTitle in a string
	 */
	public String toString(){
		return "EmpId: " + this.empId + "     Name: " + this.name + "    title: " + this.jobTitle;
	}
	
	/**
	 * create a new Staff having a name, empId, gender and jobTitle
	 * @param name			name of the staff
	 * @param empId			empId of the staff
	 * @param gender		gender of the staff
	 * @param jobTitle		jobTitle of the staff
	 */
	public Staff(String name, int empId, char gender, String jobTitle) {
		this.empId=empId;
		this.name=name;
		this.gender=gender;
		this.jobTitle=jobTitle;
	}
	
	/**
	 * get the id of the staff
	 * @return this staff's empId
	 */
	public int getEmpId(){ return empId; }
	
	/**
	 * get the name of the staff
	 * @return this staff's name
	 */
	public String getName(){ return name; }
	
	/**
	 * get the job title of the staff
	 * @return this staff's jobTitle
	 */
	public String getJobTitle(){ return jobTitle; }
	
	/**
	 * get the gender of the staff
	 * @return this staff's gender
	 */
	public char getGender() {
		return this.gender;
	}
	
	/**
	 * Set a new name for this staff
	 * @param name New name for this staff
	 */
	public void setName(String name){ this.name = name; }
	
	/**
	 * Set a new empId for this staff
	 * @param empId New empId for this staff
	 */
	public void setEmpId(int empId){ this.empId = empId; }
	
	/**
	 * Set a new gender for this staff
	 * @param gender New gender for this staff
	 */
	public void setGender(char gender){ this.gender = gender; }
	
	/**
	 * Set a new job title for this staff
	 * @param jobTitle New jobTitle for this staff
	 */
	public void setJobTitle(String jobTitle){ this.jobTitle = jobTitle; }

}