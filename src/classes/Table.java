package classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a table in the restaurant.
 * Every table has an ID, a capacity, a status, and an ArrayList of reservations.
 * @author Sean
 * @version 1.0
 * @since 2016-10-31
 */
public class Table implements Serializable{

	private static final long serialVersionUID = -696513068399675213L;
	
	/**
	 * enum class for table status
	 * @author soh jun jie
	 * @version 1.0
	 * @since 2016-11-3
	 */
	public enum TableStatus {VACATED, OCCUPIED};
	
	/**
	 * Unique table id identifying a unique table instance
	 */	
	private int table_id;
	
	/**
	 * Capacity of the table. Can be 2, 4, 8 or 10
	 */
	private int capacity;
	
	/**
	 * Status of the table. Uses TableStatus enum: VACATED, OCCUPIED
	 */	
	private TableStatus status;
	
	/**
	 * ArrayList of all reservations of the table
	 */
	private ArrayList<Reservation> reservedBy;
	
	/**
	 * Create a new table having an id, capacity and an empty 
	 * reservedBy ArrayList. Default table status is vacated
	 * @param table_id 		table id of the table
	 * @param capacity 		capacity of the table
	 */	
	public Table (int table_id, int capacity) {
		this.table_id	= table_id;
		this.capacity	= capacity;
		this.reservedBy	= new ArrayList<Reservation>();
		this.status		= TableStatus.VACATED;
	}
	
	/**
	 * get the table_id of the table
	 * @return this table's table_id
	 */	
	public int getTableId(){ return table_id; }
	
	/**
	 * get the capacity of the table
	 * @return this table's capacity
	 */	
	public int getCapacity(){ return capacity; }
	
	/**
	 * get the status of the table
	 * @return this table's status (VACATED or OCCUPIED)
	 */	
	public TableStatus getStatus(){ return status; }
	
	/**
	 * get the ArrayList of reservations
	 * @return this table's reservations
	 */	
	public ArrayList<Reservation> getReservedBy(){ return this.reservedBy; }
	
	/**
	 * change the status of the table
	 * @param status New status for this table (VACATED or OCCUPIED)
	 */
	public void setStatus(TableStatus status){ this.status=status; }
	
	/**
	 * add a reservation to the table's reservation list
	 * @param reservation New reservation for the table
	 */
	public void addTableReservation(Reservation reservation){ this.reservedBy.add(reservation); }
	
	/**
	 * Use index of reservation in the ArrayList to remove a 
	 * reservation from the reservation list
	 * @param index Index of the reservation to be removed
	 */
	public void removeTableReservation(int index){ this.reservedBy.remove(index); }
	
	/**
	 * Use reservation to remove a reservation from the reservation list
	 * @param reservation Reservation to be removed
	 */
	public void removeTableReservation(Reservation reservation){ this.reservedBy.remove(reservation); }
	
}
