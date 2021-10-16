
package mgr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import classes.Order;
import classes.Reservation;
import classes.Staff;
import classes.Table;
import db.Restaurant;

/**
 * Reservation Manager class for user to create, accept, and
 * cancel reservations.
 * @author soh jun jie
 * @version 1.0
 * @since 2016-10-31
 */
public class ReservationMgr {

	private static Scanner sc = new Scanner (System.in);
	
	private static ArrayList<Reservation> reservations = Restaurant.reservations;
	private static ArrayList<Reservation> settledReservations = Restaurant.settledReservations;
	
	/**
	 * Find all reservations that are not yet expired
	 * @return Reservations not yet expired
	 */
	public static ArrayList<Reservation> getUnexpiredReservations(){
		removeExpiredReservation();
		return reservations;
	}
	
	/**
	 * Find all reservations to be accepted with arrival time in
	 * the current dining session
	 * @return Reservation with arrival time in current session
	 */
	public static ArrayList<Reservation> getNowSessionAcceptReservation(){
		removeExpiredReservation();

		Calendar now = Calendar.getInstance();
		ArrayList<Reservation> acceptReservations = new ArrayList<Reservation>();

		int sessionCode = getDateTimeSession(now);
		if(sessionCode == 0)
			return acceptReservations;
		
		boolean AMSession = (sessionCode == 1);
		
		for(Reservation r : reservations)
			if(now.get(Calendar.MONTH)==r.getArrivalTime().get(Calendar.MONTH))
				if(now.get(Calendar.DATE) == r.getArrivalTime().get(Calendar.DATE))
					if(AMSession == (getDateTimeSession(r.getArrivalTime()) == 1))
						acceptReservations.add(r);
		
		return acceptReservations;
	}
	
	/**
	 * Allow user to make a reservation
	 */
	public static void makeReservation(String customerName, int customerContact, int numPax, Calendar arrivalTime) {
		
		Reservation newReservation;

		Table reserveTable = TableMgr.findReservationTable(arrivalTime, numPax);
		
		if(reserveTable != null){
			newReservation = new Reservation(customerName, customerContact, numPax, arrivalTime, reserveTable);
			reservations.add(newReservation);
			System.out.println("Reservation Confirmation");
			System.out.println("Customer name: " + newReservation.getCustomerName());
			System.out.println("Contact number: " + newReservation.getCustomerContact());
			System.out.println("#PAX: " + newReservation.getNumPax());
			System.out.println("Reservation datetime: " + newReservation.getArrivalTime().getTime());
			System.out.println("Reservation Table: " + newReservation.getReserveTable().getTableId());
		}else{
			System.out.println("Reservation table could not be found for datetime " +
								arrivalTime.getTime() + " for " + numPax + " person(s).");
		}
	
	}
	
	/**
	 * Allow walk in orders. Function creates a walk in reservation
	 * and create an order from the reservation immediately.
	 * @param staff Staff creating the order from the reservation
	 * @param numPax Number of people dining
	 */
	public static void makeWalkInReservation(Staff staff, int numPax){
		
		String customerName = "Unknown";
		int customerContact = 0;
		
		Calendar arrivalTime = Calendar.getInstance();
		if(!checkValidWalkInDate(arrivalTime))
			return;
		
		Table reserveTable = TableMgr.findReservationTable(arrivalTime, numPax);
		
		if(reserveTable != null){
			Reservation dineInReservation = new Reservation(customerName, customerContact, numPax, arrivalTime, reserveTable);			
			acceptReservation(staff, dineInReservation);
			System.out.println("Walk-in order created.	Table ID: "+reserveTable.getTableId());
		}

	}
	
	/**
	 * Accept a reservation. This method creates an order from
	 * the reservation and remove the reservation.
	 * @param staff Staff to create the order
	 * @param reservation Reservation to accept
	 */
	public static void acceptReservation(Staff staff, Reservation reservation){
		reservation.setAccepted();
		Order newOrder = new Order(staff, reservation);
		Restaurant.orders.add(newOrder);
		moveToSettledReservation(reservation);
	}
	
	/**
	 * Check valid reservation datetime. Datetime must not be in the past
	 * @param date Reservation date time
	 * @return True-False value indicating valid or invalid reservation date time
	 */
	public static boolean checkValidReservationDate(Calendar date){
    	
		Calendar now = Calendar.getInstance();
		if(date.before(now)){
    		System.out.println("Reservation datetime cannot be in the past!");
    		return false;
		}
		return checkValidWalkInDate(date);
		
	}
	
	/**
	 * Find the session code of the datetime given
	 * @param date Datetime to find the session code
	 * @return session code. 0 represents invalid session, 1 for AM Session, 2 for PM Session
	 */
	public static int getDateTimeSession(Calendar date){
		
	    Calendar AMStartCal = (Calendar) date.clone();
	    AMStartCal.set(Calendar.HOUR_OF_DAY, Restaurant.AMSTARTTIME);
	    AMStartCal.set(Calendar.MINUTE, 0);
	    AMStartCal.set(Calendar.SECOND, 0);
	    AMStartCal.set(Calendar.MILLISECOND, 0);
	    
	    Calendar AMEndCal = (Calendar) date.clone();
	    AMEndCal.set(Calendar.HOUR_OF_DAY, Restaurant.AMENDTIME);
	    AMEndCal.set(Calendar.MINUTE, 0);
	    AMEndCal.set(Calendar.SECOND, 0);
	    AMEndCal.set(Calendar.MILLISECOND, 0);
	    
	    Calendar PMStartCal = (Calendar) date.clone();
	    PMStartCal.set(Calendar.HOUR_OF_DAY, Restaurant.PMSTARTTIME);
	    PMStartCal.set(Calendar.MINUTE, 0);
	    PMStartCal.set(Calendar.SECOND, 0);
	    PMStartCal.set(Calendar.MILLISECOND, 0);
	    
	    Calendar PMEndCal = (Calendar) date.clone();
	    PMEndCal.set(Calendar.HOUR_OF_DAY, Restaurant.PMENDTIME);
	    PMEndCal.set(Calendar.MINUTE, 0);
	    PMEndCal.set(Calendar.SECOND, 0);
	    PMEndCal.set(Calendar.MILLISECOND, 0);
	    
    	if((date.before(AMStartCal) || date.after(AMEndCal)) && (date.before(PMStartCal) || date.after(PMEndCal)))
    		return 0;
    	if(!(date.before(AMStartCal) || date.after(AMEndCal)))	// AM SESSION
    		return 1;
    	if(!(date.before(PMStartCal) || date.after(PMEndCal)))		//PM SESSION
    		return 2;
    	
    	return 0;
	}
	
	/**
	 * Check if a reservation datetime is valid for walk-in
	 * on that day according to restaurant operation hours
	 * @param date Datetime to check
	 * @return True-False value indicating valid or invalid walk in datetime
	 */
	public static boolean checkValidWalkInDate(Calendar date){
		
		boolean validDate = false;
		
	    Calendar maxBookingDate = Calendar.getInstance();
	    maxBookingDate.add(Calendar.DAY_OF_MONTH, 30);
	    
    	if(date.after(maxBookingDate))
    		System.out.println("Reservation is for 1 month in advance only!");
    	else if (getDateTimeSession(date) == 0)
    		System.out.println("Reservation must be within operation hours");
    	else
    		validDate = true;
	    
		return validDate;
	}
	
	/**
	 * Remove reservation that are more than 30min 
	 * past arrival time
	 */
	public static void removeExpiredReservation(){
		
		Calendar expiredDateTime = Calendar.getInstance();
		expiredDateTime.add(Calendar.MINUTE, -30);
		Reservation r;
		
		for (int i = reservations.size()-1; i >= 0; i--){
			r = reservations.get(i);
		    if(r.getArrivalTime().before(expiredDateTime))
				moveToSettledReservation(r);
		 }

	}
	
	/**
	 * Move pending reservation to settled
	 * @param reservation Reservation to move to settled
	 */
	public static void moveToSettledReservation(Reservation reservation){

		Table tempTable;
		tempTable = reservation.getReserveTable();		
		tempTable.removeTableReservation(reservation);		
		settledReservations.add(reservation);
		reservations.remove(reservation);
	}
	
	/**
	 * Get a valid reservation date time from the user
	 * @return Valid reservation date time
	 */
	public static Calendar getValidReservationDateTime(){

		String date = "";
	    Date parsedDate = null;
	    SimpleDateFormat dateFormat = null;
		boolean validDate = false;		
		Calendar arrivalTime = Calendar.getInstance();
		
		do{
		    System.out.print("Enter reservation datetime (dd/MM/yyyy HH:mm): ");	date  = sc.nextLine();
		    dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		    try {
		    	parsedDate = dateFormat.parse(date);
		    } catch (ParseException e) {
		        System.out.println("Entered reservation date is not in the correct format!");
		        continue;
		    }

		    arrivalTime.setTime(parsedDate);
		    validDate = checkValidReservationDate(arrivalTime);

		} while(!validDate);
				
		return arrivalTime;
	}
	

}
