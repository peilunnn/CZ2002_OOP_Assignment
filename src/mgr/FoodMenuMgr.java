package mgr;

import java.util.ArrayList;
import java.util.Scanner;

import classes.Food;
import classes.Food.CourseType;
import classes.MenuItem;
import classes.PromotionPackage;
import db.Restaurant;

/**
 * Food Menu Manager class that manages creation, removal of menu item in a food
 * menu
 * 
 * @author user soh jun jie
 * @version 1.0
 * @since 2016-10-31
 */
public class FoodMenuMgr {

	private static ArrayList<MenuItem> foodMenu = Restaurant.foodMenu;
	private static Scanner sc = new Scanner(System.in);

	public static ArrayList<MenuItem> getRestaurantFoodMenu() {
		return foodMenu;
	}

	/**
	 * Create new food item into the restaurant food menu.
	 * 
	 * @param foodName       Name of the food
	 * @param foodDesc       Description of the food
	 * @param foodPrice      Price of the food
	 * @param foodCourseType Course type of the food
	 */
	public static void createNewFood(String foodName, String foodDesc, double foodPrice, CourseType foodCourseType) {
		Food newFood;
		newFood = new Food(foodName, foodDesc, foodPrice, foodCourseType);
		foodMenu.add((MenuItem) newFood);
		System.out.println("Food " + foodName + " was added to the food menu.");
	}

	/**
	 * Create a promotion package into the restaurant food menu
	 * 
	 * @param menuName  Name of the promotion package
	 * @param menuDesc  Description of the promotion package
	 * @param menuPrice Price of the promotion package
	 */
	public static void createNewPromotionPackage(String menuName, String menuDesc, double menuPrice) {

		PromotionPackage newPP;
		newPP = new PromotionPackage(menuName, menuDesc, menuPrice);
		String choice = "";

		boolean continueAdd = true;
		do {
			newPP.addFood(foodMenu);
			System.out.print("Continue adding menu item to promotion package? (Y/N): ");
			choice = sc.nextLine();
			continueAdd = ('Y' == Character.toUpperCase(choice.charAt(0)));
		} while (continueAdd);

		foodMenu.add((MenuItem) newPP);

	}

	/**
	 * Remove a menu item from the restaurant food menu based on a menu index
	 * 
	 * @param menuIndex Menu item to remove
	 */
	public static void removeMenuItem(int menuIndex) {

		try {
			String itemRemoved = foodMenu.get(menuIndex).getMenuName();
			foodMenu.remove(menuIndex);
			System.out.println(itemRemoved + " removed from food menu.");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Remove menu item from food menu failed! (Invalid index provided");
		}

	}

	/**
	 * Update menu item name
	 * 
	 * @param menuItem Menu item to update
	 * @param menuName New menu name
	 */
	public static void updateMenuItemName(MenuItem menuItem, String menuName) {

		try {
			String prevName = menuItem.getMenuName();
			menuItem.setMenuName(menuName);
			System.out.println("Menu name : " + prevName + " has been updated to " + menuName);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Update menu item in food menu failed! (Invalid index provided");
		}

	}

	/**
	 * Update menu item description
	 * 
	 * @param menuItem Menu item to update
	 * @param menuDesc New menu description
	 */
	public static void updateMenuItemDesc(MenuItem menuItem, String menuDesc) {

		try {
			menuItem.setDesc(menuDesc);
			String menuName = menuItem.getMenuName();
			System.out.println("Description of " + menuName + " has been updated to '" + menuDesc + "'");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Update menu item in food menu failed! (Invalid index provided");
		}

	}

	/**
	 * Update menu item price
	 * 
	 * @param menuItem  Menu item to update
	 * @param menuPrice New menu price
	 */
	public static void updateMenuItemPrice(MenuItem menuItem, double menuPrice) {

		double prevPrice;
		String menuName;

		try {
			prevPrice = menuItem.getPrice();
			menuItem.setPrice(menuPrice);

			menuName = menuItem.getMenuName();
			System.out.println("Price of " + menuName + " has been updated from " + prevPrice + " to " + menuPrice);
		}

		catch (IndexOutOfBoundsException e) {
			System.out.println("Update menu item in food menu failed! (Invalid index provided");
		}
	}

	/**
	 * Update food course type
	 * 
	 * @param menuItem       Menu item to update
	 * @param foodCourseType New food course type
	 */
	public static void updateFoodCourseType(MenuItem menuItem, CourseType foodCourseType) {

		Food food = (Food) menuItem;
		String menuName = food.getMenuName();
		CourseType prevCourseType = food.getType();

		food.setType(foodCourseType);
		System.out.println(
				"\nCourse Type of " + menuName + " has been updated from " + prevCourseType + " to " + foodCourseType);
	}

	/**
	 * Print details of food
	 * 
	 * @param food Food to print
	 */
	public static void printFood(Food food) {
		System.out.println();
		System.out.println("Food Name: " + food.getMenuName());
		System.out.println("Food Description: " + food.getDesc());
		System.out.println("Food Price: " + food.getPrice());
		System.out.println("Food course type: " + food.getType());
	}

	/**
	 * Print details of promotion package
	 * 
	 * @param pp Promotion package to print
	 */
	public static void printPromotionPackage(PromotionPackage pp) {
		System.out.println();
		System.out.println("Promotion Package Name: " + pp.getMenuName());
		System.out.println("Promotion Package Description: " + pp.getDesc());
		System.out.println("Promotion Package Price: " + pp.getPrice());

		ArrayList<Food> pp_array = pp.getFoodSet();
		System.out.println("\nThis Promotion Package contains: ");
		int index = 0;
		for (Food food : pp_array)
			System.out.println("(" + index++ + ") " + food.getMenuName());
	}

}
