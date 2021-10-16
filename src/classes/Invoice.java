package classes;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Represents an invoice generated from an order.
 * @author michelle
 * @version 1.0
 * @since 2016-10-24
 */
public class Invoice implements Serializable{

	private static final long serialVersionUID = 103202593696268715L;
	
	/**
	 * Order for which this invoice was generated for
	 */
	private Order order;
	
	/**
	 * Auto-generated invoice number of this invoice
	 */
	private int invoiceNumber;
	
	/**
	 * Gst charged for the order
	 */
	private double gst;
	
	/**
	 * Price charge for the order, excluding gst
	 */
	private double price;
	
	/**
	 * Total price charged for the order
	 */
	private double totalPrice;
	
	/**
	 * Date the invoice was generated
	 */
	private Calendar dateGenerated;
	
	/**
	 * GST percentage to be applied on price charged on order
	 */
	public static final double GSTPERCENTAGE = 0.07;
	
	/**
	 * Create a new invoice which will be tagged to an order.
	 * @param order Order the invoice is generated for
	 */
	public Invoice(Order order){
		this.order = order;
		this.invoiceNumber = Calendar.getInstance().hashCode();
		this.price = order.calculateTotalOrderPrice();
		this.gst = Math.round(GSTPERCENTAGE * this.price * 100.0)/100.0;
		this.totalPrice = this.price + this.gst;
		this.dateGenerated = Calendar.getInstance();
	}
	
	/**
	 * Get the date invoice was generated
	 * @return This invoice date
	 */
	public Calendar getDateGenerated() { return dateGenerated; }
	
	/**
	 * Get the total price chargeable for the invoice
	 * @return This invoice price
	 */
	public double getTotalPrice() { return totalPrice; }
	
	/**
	 * Get the order of this invoice
	 * @return This invoice order
	 */
	public Order getOrder() { return order; }
	
	/**
	 * Print this invoice
	 */
	public void printInvoice(){
		System.out.println("Date & Time: " + this.order.getDateTime());
		System.out.println("Invoice Number: " + this.invoiceNumber);
		System.out.println("Items ordered: " + this.order);
		System.out.println("Subtotal: " + price);
		System.out.println("GST: " + gst);
		System.out.println("Total: " + totalPrice);
	}

}
