package classes;

import java.io.Serializable;
import java.util.Calendar;

import classes.Table.TableStatus;

/**
 * Represents a reservation made by a customer
 * @author wang xing yue
 * @version 1.0
 * @since 2016-10-25
 */
public class Reservation implements Serializable{
	
	private static final long serialVersionUID = 3558136592677236481L;
	
	/**
	 * Unique reservation id identifying a unique reservation instance
	 */
	private int reservationID;
	
	/**
	 * Number of people for the reservation
	 */
	private int numPax;
	
	/**
	 * Name of the customer making the reservation
	 */
	private String customerName;
	
	/**
	 * Contact number of the customer
	 */
	private int customerContact;
	
	/**
	 * Indicate whether reservation has been accepted
	 */
	private boolean accepted;
	
	/**
	 * Arrival time of the customer.
	 */
	private Calendar arrivalTime;
	
	/**
	 * Table reserved for the reservation
	 */
	private Table reserveTable;
	
	/**
	 * create a new reservation having a customer name, customer contact number, number of expected people,
	 * expected arrival time, and reserved table
	 * @param customerName		Name of customer who made the reservation
	 * @param customerContact	Contact number of customer
	 * @param numPax			Number of expected people for this reservation
	 * @param arrivalTime		Time customer is expected to arrive
	 * @param reserveTable		Table reserved for this reservation
	 */
	public Reservation(String customerName, int customerContact, int numPax, Calendar arrivalTime, Table reserveTable){
		this.customerName = customerName;
		this.customerContact = customerContact;
		this.numPax = numPax;
		this.accepted = false;
		this.reservationID = Calendar.getInstance().hashCode();
		this.arrivalTime = arrivalTime;
		this.reserveTable = reserveTable;
		reserveTable.addTableReservation(this);
	}
	
	/**
	 * Set accepted attribute from false to true
	 * Change reserved table's tableStatus attribute to OCCUPIED
	 * Remove this reservation from table's array of reservations
	 */
	public void setAccepted(){
		this.accepted = true;
		this.reserveTable.setStatus(TableStatus.OCCUPIED);
		this.reserveTable.removeTableReservation(this);
	}

	/**
	 * get the accepted status of this reservation
	 * @return this reservation's accepted status
	 */
	public boolean getAcceptedStatus(){ return this.accepted; }
	
	/**
	 * get the name of the customer who made this reservation
	 * @return this reservation's customer's name
	 */
	public String getCustomerName(){ return this.customerName; }
	
	/**
	 * get the contact number of the customer who made this reservation
	 * @return this reservation's customer contact number
	 */
	public int getCustomerContact(){ return this.customerContact; }
	
	/**
	 * get the number of expected people for this reservation
	 * @return this reservation's number of expected people
	 */
	public int getNumPax(){ return this.numPax; }
	
	/**
	 * get the unique ID of this reservation
	 * @return this reservation's unique ID
	 */
	public int getReservationID(){ return this.reservationID; }
	
	/**
	 * get the expected arrival time of this reservation
	 * @return this reservation's expected arrival time
	 */
	public Calendar getArrivalTime(){ return this.arrivalTime; }
	
	/**
	 * get the table reserved for this reservation
	 * @return this reservation's reserved table
	 */
	public Table getReserveTable(){ return this.reserveTable; }
	
	/**
	 * set a new customer name for this reservation
	 * @param newName New customer name for this reservation
	 */
	public void setCustomerName(String newName){ this.customerName = newName; }
	
	/**
	 * set a new customer contact number for this reservation
	 * @param newContact New customer contact number for this reservation
	 */
	public void setCustomerContact(int newContact) { this.customerContact = newContact; }

	public String toString(){
		return "Customer Name:" + this.customerName + "    Contact: " + this.customerContact + 
				"    Arrival time: " + this.arrivalTime.getTime() + "	 Reservation ID: "+ this.reservationID +
				"	 Table ID: "+ this.reserveTable.getTableId();
	}
	
}