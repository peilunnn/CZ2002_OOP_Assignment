package classes;

import java.io.Serializable;
import java.util.ArrayList;
import user_lib.ScannerExt;

/**
 * Represents a promotion package offered by the restaurant.
 * A promotion package comprises of multiple food item which
 * are charged for cheaper price.
 * @author stella
 * @version 1.0
 * @since 2016-24-10
 */
public class PromotionPackage extends MenuItem implements Serializable{

	private static final long serialVersionUID = -5702646010331366934L;
	
	/**
	 * List of food item included in the promotion package
	 */
	private ArrayList<Food> foodSet;
	
	/**
	 * Creates a new promotion package without any food item in it
	 * @param menuName Menu name of the promotion package
	 * @param desc Description of the promotion package
	 * @param price Price of the promotion package
	 */
	public PromotionPackage(String menuName, String desc, double price) {
		super(menuName, desc, price);
		this.foodSet = new ArrayList<Food>();
	}
	
	/**
	 * Get the list of food included in the promotion package
	 * @return Arraylist of food item included in promotion package
	 */
	public ArrayList<Food> getFoodSet(){ return foodSet; }	
	
	/**
	 * Add a food item to the promotion package
	 * @param food food item to be added to promotion package
	 */
	public void addFood(Food food){ foodSet.add(food); }
	
	/**
	 * Allows user to select a food item from a printed
	 * list of choice to be added to the promotion package.
	 * Only a food instance can added to the promotion package.
	 * @param foodMenu Arraylist of menu item for user to select a food item from
	 */
	public void addFood(ArrayList<MenuItem> foodMenu){

		int index = 0;
		
		System.out.println("What food would you like to add to the Promotion Package?");
		for (MenuItem menuItem : foodMenu){
			if(menuItem instanceof Food)
				System.out.println("(" + index + ") " + menuItem.getMenuName());
			index++;
		}
    	int choice = ScannerExt.nextInt("    Enter the number of your choice: ");

		try {
			if(foodMenu.get(choice) instanceof Food){
				Food foodToAdd = (Food) foodMenu.get(choice);
				this.foodSet.add(foodToAdd);
				System.out.println(foodToAdd.getMenuName() + " successfully added to promotion package.");
			}else{
				System.out.println("selected item is not a food");
			}
		}catch(IndexOutOfBoundsException e){
			System.out.println("Add food item failed! (Invalid index provided");
		}

	}

	/**
	 * Remove a food from the promotion package
	 * @param food Food item to be removed from the promotion package
	 */
	public void removeFood(Food food) { foodSet.remove(food); }
	
	/**
	 * Allows user to select a food from a printed list of choice
	 * to remove from the promotion package.
	 */
	public void removeFood() {

		System.out.println("What food would you like to remove from the Promotion Package?");
		int i=0;
		for (Food fd : foodSet)
			System.out.println(i++ + ": " + fd.getMenuName());
    	int index = ScannerExt.nextInt("    Enter the number of your choice: ");
		
		try {
			String foodNameRemoved = foodSet.get(index).getMenuName();
			foodSet.remove(index);
			System.out.println(foodNameRemoved + " sucessfully removed from promotion package."); 
		}catch(IndexOutOfBoundsException e){
			System.out.println("Food item removal failed! (Invalid index provided");
		}
		
	}

}
