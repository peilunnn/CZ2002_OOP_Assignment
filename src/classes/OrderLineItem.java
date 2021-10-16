package classes;

import java.io.Serializable;

/**
 * Represents a line item in an order
 * @author soh jun jie
 * @version 1.0
 * @since 2016-10-31
 */
public class OrderLineItem implements Serializable{

	private static final long serialVersionUID = -1249371886974645374L;
	
	/**
	 * Menu item the OrderLineItem represents
	 */
	private MenuItem orderItem;
	
	/**
	 * Price charged for the OrderLineItem
	 */
	private double chargedPrice;
	
	/**
	 * Create a new OrderLineItem.
	 * @param orderItem Menu item represented by the OrderLineItem
	 */
	public OrderLineItem(MenuItem orderItem){ 
		this.orderItem = orderItem;
		this.chargedPrice = orderItem.getPrice();
	}

	/**
	 * Get the menu item of this OrderLineItem
	 * @return This OrderLineItem menu item
	 */
	public MenuItem getMenuItem(){ return this.orderItem; }
	
	/**
	 * Get the price charged for this OrderLineItem
	 * @return This OrderLineItem charged price
	 */
	public double getChargedPrice(){ return this.chargedPrice; }
	
	public String toString(){ return this.getMenuItem().getMenuName(); }
	
}
