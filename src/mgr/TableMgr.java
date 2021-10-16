package mgr;

import java.util.ArrayList;
import java.util.Calendar;
import classes.Reservation;
import classes.Table;
import classes.Table.TableStatus;
import db.Restaurant;

/**
 * Table manager class allowing user to find
 * reservation table
 * availability
 * @author wang xing yue
 * @version 1.0
 * @since 2016-10-31
 */
public class TableMgr {

	private static ArrayList<Table> tables = Restaurant.tables;
	
	/**
	 * Find a reservation that fits the reservation datetime
	 * and number of people.
	 * @param reserveDateTime Datetime to reserve the table for
	 * @param numPax Number of people dining
	 * @return Reservation table to reserve
	 */
	public static Table findReservationTable(Calendar reserveDateTime, int numPax){
		
		ArrayList<Table> availableTables = checkAvailableTables(reserveDateTime, numPax);
		if(availableTables.size() == 0 || availableTables == null){
			System.out.println("No tables available for reservation on datetime " + reserveDateTime.getTime());
			return null;
		}
		
		// GET SMALLEST CAPACITY RESERVATION TABLE
		Table retTable = availableTables.get(0);
		for(Table t : availableTables)
			if(t.getCapacity() < retTable.getCapacity())
				retTable = t;

		return retTable;
	}
	
	/**
	 * Find all tables that matches satisfy reservation date time and
	 * number of people
	 * @param reserveDateTime Datetime of reservation
	 * @param numPax Number of people
	 * @return ArrayList of tables satisfying reservation criteria
	 */
	public static ArrayList<Table> checkAvailableTables(Calendar reserveDateTime, int numPax){
		
		ReservationMgr.removeExpiredReservation();
		ArrayList<Table> availableTables = new ArrayList<Table>();
		
		boolean isAMSession = (reserveDateTime.get(Calendar.HOUR_OF_DAY) < Restaurant.AMENDTIME);
		boolean available;
		Calendar now = Calendar.getInstance();	
		
		for(Table t : tables){
			
			if(now.get(Calendar.MONTH)==reserveDateTime.get(Calendar.MONTH))
				if(now.get(Calendar.DATE) == reserveDateTime.get(Calendar.DATE))
					if(isAMSession == (now.get(Calendar.HOUR_OF_DAY) < Restaurant.AMENDTIME))
						if(t.getStatus()==TableStatus.OCCUPIED)
							continue;
			
			if(t.getCapacity() < numPax) continue;
			
			available = true;
			for(Reservation r : t.getReservedBy()){
				
				if(r.getArrivalTime().get(Calendar.YEAR)!=reserveDateTime.get(Calendar.YEAR))
					continue;
				if(r.getArrivalTime().get(Calendar.MONTH)!=reserveDateTime.get(Calendar.MONTH))
					continue;
				if(r.getArrivalTime().get(Calendar.DATE)!=reserveDateTime.get(Calendar.DATE))
					continue;
				if(isAMSession == (r.getArrivalTime().get(Calendar.HOUR_OF_DAY) < Restaurant.AMENDTIME)){
					available=false;
					break;
				}
				
			}
			if(available) availableTables.add(t);

		}
		
		return availableTables;
	
	}
	
	
}
