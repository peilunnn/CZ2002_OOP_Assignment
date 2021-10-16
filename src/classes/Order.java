package classes;

import java.util.Date;

import classes.Table.TableStatus;
import db.Restaurant;
import user_lib.ScannerExt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Represents an order made by a staff
 * @author soh jun jie
 * @version 1.0
 * @since 2016-10-31
 */
public class Order implements Serializable{
	
	private static final long serialVersionUID = -9135686500512288865L;
	
	/**
	 * Auto-generated ID of the Order
	 */
	private int							orderID;
	
	/**
	 * Contains individual items ordered in an order
	 */
	private ArrayList<OrderLineItem> 	orderLineItems;
	
	/**
	 * Staff that created the order
	 */
	private Staff 						createdByStaff;
	
	/**
	 * Reservation in which order was created from
	 */
	private Reservation					fromReservation;
	
	/**
	 * Datetime the order was created
	 */
	private Date 						dateTime;
	
	/**
	 * Invoice of the order.
	 */
	private Invoice 					invoice;

	/**
	 * Creates a new order. Invoice of an order will be null for a
	 * newly created order as invoice is yet to be generated.
	 * @param createdByStaff Staff creating the order
	 * @param fromReservation Reservation the order is made for
	 */
	public Order(Staff createdByStaff, Reservation fromReservation){
		this.orderID			= Calendar.getInstance().hashCode();
		this.orderLineItems		= new ArrayList<OrderLineItem>();
		this.createdByStaff 	= createdByStaff;
		this.fromReservation 	= fromReservation;
		this.dateTime 			= Calendar.getInstance().getTime();
		this.invoice			= null;
	}
	
	/**
	 * Get the order ID of this order
	 * @return This order id
	 */
	public int getOrderID(){ return this.orderID; }
	
	/**
	 * Get arraylist of items currently ordered in this order
	 * @return list of items currently ordered
	 */
	public ArrayList<OrderLineItem> getOrderLineItems(){return this.orderLineItems;}
	
	/**
	 * Get the datetime the order was created
	 * @return Datetime the order was created
	 */
	public Date getDateTime(){return this.dateTime;}
	
	/**
	 * Get the staff that created this order
	 * @return Staff that created the order
	 */
	public Staff getStaffCreated(){return this.createdByStaff;}
	
	/**
	 * Get the invoice of this order.
	 * @return This order's invoice
	 */
	public Invoice getInvoice(){ return this.invoice; }

	/**
	 * Get the reservation this order was created from
	 * @return This order reservation
	 */
	public Reservation getFromReservation(){
		return this.fromReservation; 
	}
	
	/**
	 * Add an item to the order
	 * @param orderItem Item to be added to the order
	 */
	public void addOrderItem(OrderLineItem orderItem){
		if(this.invoice != null) return;	//lock order for editing when invoice already generated
		orderLineItems.add(orderItem);
	}
	
	/**
	 * Method to add item to the order. User will be asked to
	 * select an item from a printed list of menu items
	 */
	public void addOrderItem(){
		
		if(this.invoice != null) return;	//lock order for editing when invoice already generated
		
		int choice;
		int index = 0;
		OrderLineItem orderItem;
		ArrayList<MenuItem> foodMenu = Restaurant.foodMenu;
		
		System.out.println("\nSelect the food item to add to the order:");
		for(MenuItem menuItem : foodMenu)
			System.out.println("(" + index++ + ") " + menuItem.getMenuName());
    	choice = ScannerExt.nextInt("    Enter the number of your choice: ");
		
		try {
			String orderItemAdded = foodMenu.get(choice).getMenuName();
			orderItem = new OrderLineItem(foodMenu.get(choice));
			this.orderLineItems.add(orderItem);
			System.out.println(orderItemAdded + " added to order."); 
		}catch(IndexOutOfBoundsException e){
			System.out.println("Add order item failed! (Invalid index provided)");
		}

	}
	
	/**
	 * Remove an item from the order. User will be asked to select 
	 * an item to be removed from a printed list of ordered items.
	 */
	public void removeOrderItem(){
		
		if(this.invoice != null) return;	//lock order for editing when invoice already generated
		
		int choice, index;
		
		System.out.println("\nWhat item would you like to remove from the order?");
		
		index = 0;
		for (OrderLineItem orderItem : orderLineItems)
			System.out.println(index++ + ": " + orderItem.getMenuItem().getMenuName());
    	choice = ScannerExt.nextInt("    Enter the number of your choice: ");
		
		try {
			String orderItemRemoved = orderLineItems.get(choice).toString();
			this.orderLineItems.remove(choice);
			System.out.println(orderItemRemoved + " removed from order."); 
		}catch(IndexOutOfBoundsException e){
			System.out.println("Order item removal failed! (Invalid index provided");
		}
	}
	
	
	/**
	 * Generate an invoice for the order
	 */
	public void generateInvoice(){
		
		if(this.invoice != null) return;	//lock order for editing when invoice already generated
		this.invoice = new Invoice(this);
		this.fromReservation.getReserveTable().setStatus(TableStatus.VACATED);

	}
	
	/**
	 * Calculate the current total order price
	 * @return Order current total price
	 */
	public double calculateTotalOrderPrice(){
		double retPrice = 0;
		for(OrderLineItem o : this.orderLineItems)
			retPrice += o.getMenuItem().getPrice();
		return retPrice;
	}
	
	/**
	 * prints order items in the order's orderlineitems
	 */
	public String toString(){
		String printOrderString = "";
		for(OrderLineItem o : orderLineItems){
			printOrderString += o.getMenuItem().getMenuName() + "    " + o.getChargedPrice() + "\n";
		}
		return printOrderString;
	}
	
}
