package mgr;

import java.util.ArrayList;
import classes.Order;
import db.Restaurant;
import ui.OrderUI;
import user_lib.ScannerExt;

/**
 * Order Manager class allowing user
 * to select an order to edit
 * @author soh jun jie
 * @version 1.0
 * @since 2016-10-31
 */
public class OrderMgr {

	private static ArrayList<Order> orders = Restaurant.orders;
	private static ArrayList<Order> settledOrders = Restaurant.settledOrders;
	
	public static void moveToSettledOrder(Order order){
		settledOrders.add(order);
		orders.remove(order);
	}
	
	/**
	 * Allow user to select an order and display
	 * a UI to interact with that order
	 */
	public static void editPendingOrder(){
		
		if(orders.size() == 0){ 
			System.out.println("No orders available to edit!");
			return; 
		}
		
		int index = 0;
		Order order;
		
		System.out.println("\nSelect an order.");
		for(Order o : orders){
			System.out.println("(" + index++ + ") OrderID: " + o.getOrderID() + "    TableID: " + o.getFromReservation().getReserveTable().getTableId());
		}
    	int choice = ScannerExt.nextInt("    Enter the number of your choice: ");
		
		try {
			order = orders.get(choice);
			OrderUI.menuShowOrderOptions(order);
		}catch(IndexOutOfBoundsException e){
			System.out.println("Invalid index entered!");
		}
		
	}
	
}
