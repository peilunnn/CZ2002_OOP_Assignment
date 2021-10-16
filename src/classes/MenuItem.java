package classes;

import java.io.Serializable;


/**
 * Represents a menu item as appear on the food menu.
 * Any menu item on a food menu has a menu name, 
 * description and price.
 * @author stella
 * @version 1.0
 * @since 2016-10-24
 */
public abstract class MenuItem implements Serializable{
	
	private static final long serialVersionUID = 1991173970229115074L;
	
	/**
	 * Menu name of the menu item.
	 */
	private String menuName;
	
	/**
	 * Description of the menu item.
	 */
	private String desc;
	
	/**
	 * Price of the menu item.
	 */
	private double price;
	
	/**
	 * Creates a new menu item with a menu name, description and price
	 * @param menuName Menu name of the menu item
	 * @param desc Description of the menu item
	 * @param price Price of the menu item
	 */
	public MenuItem(String menuName, String desc, double price){
		this.menuName = menuName;
		this.desc = desc;
		this.price = price;
	}
	
	/**
	 * Changes the menu name of the menu item
	 * @param menuName Desired menu name
	 */
	public void setMenuName(String menuName){ this.menuName = menuName; }
	
	/**
	 * Updates the description of the food menu
	 * @param desc Description of the food menu
	 */
	public void setDesc(String desc){ this.desc = desc; }
	
	/**
	 * Changes the price of the menu item
	 * @param price New menu item pricing
	 */
	public void setPrice(double price){ this.price = price; }
	
	/**
	 * Get the menu name of this menu item
	 * @return This menu name
	 */
	public String getMenuName(){ return this.menuName; }
	
	/**
	 * Get the menu item description
	 * @return This menu item description
	 */
	public String getDesc(){ return this.desc; }
	
	/**
	 * Get the price of the menu item
	 * @return This menu item price
	 */
	public double getPrice(){ return this.price; }

}
