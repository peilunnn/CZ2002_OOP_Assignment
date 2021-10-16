package ui;

import java.util.ArrayList;
import java.util.Scanner;

import classes.Food;
import classes.MenuItem;
import classes.PromotionPackage;
import classes.Food.CourseType;
import mgr.FoodMenuMgr;
import user_lib.ScannerExt;

/**
 * Represents the UI displayed to the user when changing the food menu.
 * @author Sean
 * @version 1.0
 * @since 2016-10-28
 */
public class FoodMenuUI {
	
	private static Scanner sc = new Scanner(System.in);
	
	
	/**
	 * Display options user can perform on restaurant food menu.
	 * User can Add new food, new promotion package or remove 
	 * menu item from menu
	 */	
	public static void menuShowFoodMenuOptions(){
		
		int choice;
		
        do {
        	System.out.println("\nSelect a choice: ");
            System.out.println("(1) Add new food to food menu");
            System.out.println("(2) Add new promotion package to food menu");
            System.out.println("(3) Update menu item in food menu");
            System.out.println("(4) Remove a menu item from food menu");
            System.out.println("(5) View menu item");
            System.out.println("(6) Back");
        	choice = ScannerExt.nextInt("\n    Enter the number of your choice: ", 1, 6);
        	
        	switch (choice) {
	            case 1: 
	            		createNewFoodUI();
	                    break;
	            case 2:
	            		createNewPromotionPackageUI();
	                    break;
	            case 3:
	            		updateMenuItemUI();
	                	break;
	            case 4:
	            		removeMenuItemUI();
	            		break;
	            case 5:
	            		viewMenuItemUI();
	            		break;
	            case 6:
        	}
        } while (choice < 6);
	
	}

	/**
	 * Show a UI that prompts user for menu item to be
	 * viewed.
	 */
	private static void viewMenuItemUI(){
		
		int choice;
		ArrayList<MenuItem> foodMenu = FoodMenuMgr.getRestaurantFoodMenu();
		
		int index = 0;
		System.out.println("\nList of Menu Items:");
		for(MenuItem menuItem : foodMenu)
			System.out.println("(" + index++ + ") " + menuItem.getMenuName());
		choice = ScannerExt.nextInt("\n    Enter the number of your choice: ", 0, index-1);
		
		MenuItem viewMenuItem = foodMenu.get(choice);
		if (viewMenuItem instanceof Food)
			FoodMenuMgr.printFood((Food) viewMenuItem);
		else if (viewMenuItem instanceof PromotionPackage)
			FoodMenuMgr.printPromotionPackage((PromotionPackage) viewMenuItem);
		else
			System.out.println("Invalid number!");
	}
	/**
	 * Show a UI that prompts user for menu item to be
	 * removed.
	 */
	private static void removeMenuItemUI(){
		
		int choice;
		ArrayList<MenuItem> foodMenu = FoodMenuMgr.getRestaurantFoodMenu();

		int index = 0;
		System.out.println("\nSelect the menu item to remove from the food menu:");
		for(MenuItem menuItem : foodMenu)
			System.out.println("(" + index++ + ") " + menuItem.getMenuName());
    	choice = ScannerExt.nextInt("    Enter the number of your choice: ");
		

		FoodMenuMgr.removeMenuItem(choice);

	}
	
	/**
	 * Show a UI that prompts user for details of
	 * the promotion package to be created.
	 */
	private static void createNewPromotionPackageUI(){
		
		String menuName;
		String menuDesc;
		double menuPrice;
		
		System.out.print("Enter menu name of promotion package: "); menuName = sc.nextLine();
		System.out.print("Enter menu description of promotion package: "); menuDesc = sc.nextLine();
		menuPrice = ScannerExt.nextDouble("Enter price of promotion package: ");
		
		FoodMenuMgr.createNewPromotionPackage(menuName, menuDesc, menuPrice);
		
	}
	
	/**
	 * Show a UI that prompts user for details of
	 * the food to be created.
	 */
	private static void createNewFoodUI(){
		
		CourseType foodCourseType = null;

		System.out.print("Enter menu name of food: "); String foodName = sc.nextLine();
		System.out.print("Enter menu description of food: "); String foodDesc = sc.nextLine();
		double foodPrice = ScannerExt.nextDouble("Enter price of food: ");
		
		foodCourseType = selectFoodCourseTypeUI("\nSelect course type of your food.");    	
		FoodMenuMgr.createNewFood(foodName, foodDesc, foodPrice, foodCourseType);

	}
	
	/**
	 * Show a UI that prompts user for menu item to be 
	 * updated.
	 */
	private static void updateMenuItemUI(){
		
		int choice;
		MenuItem menuItemToUpdate;
		ArrayList<MenuItem> foodMenu = FoodMenuMgr.getRestaurantFoodMenu();
		
		if(foodMenu.size() <= 0){
			System.out.println("There are no menu items in food menu.");
			return;
		}
		
		int index = 0;
		System.out.println("\nSelect the menu item to update in the food menu:");
		for(MenuItem menuItem : foodMenu)
			System.out.println("(" + index++ + ") " + menuItem.getMenuName());
		
		choice = ScannerExt.nextInt("\n    Enter the number of your choice: ", 0, index-1);
		menuItemToUpdate = foodMenu.get(choice);
		
		if (menuItemToUpdate instanceof Food)
			updateFood(menuItemToUpdate);
		else if (menuItemToUpdate instanceof PromotionPackage)
			updatePromotionPackage(menuItemToUpdate);
		else
			System.out.println("Invalid number!");
	}
	
	/**
	 * Update menu item name, desc or price from restaurant food menu.
	 * @param menuItem Menu item to be updated
	 */
	private static void updateFood(MenuItem menuItem){

		int choice;

		try {
			
			do {
				System.out.println("\nWhat would you like to update?");
				System.out.println("(1) Food Name");
				System.out.println("(2) Food Description");
				System.out.println("(3) Food Price");
				System.out.println("(4) Food Course Type");
				System.out.println("(5) Back"); 
				choice = ScannerExt.nextInt("\n    Enter the number of your choice: ", 1, 5);
			
				switch (choice) {
					case 1:
							updateMenuItemName(menuItem);
							break;
					case 2:
							updateMenuItemDesc(menuItem);
							break;
					case 3: 
							updateMenuItemPrice(menuItem);
							break;
					case 4:
							FoodMenuMgr.updateFoodCourseType(menuItem, 
									selectFoodCourseTypeUI("Select food course type."));
							break;
					case 5:
				}
			
			} while (choice != 5);
		
		}catch(IndexOutOfBoundsException e){
			System.out.println("Update menu item in food menu failed! (Invalid index provided");
		}

	}
	
	/**
	 * Update promotion package name, desc or price from restaurant food menu.
	 * @param menuItem Menu item to be updated
	 */
	private static void updatePromotionPackage(MenuItem menuItem){
		int choice;

		do {
			System.out.println("\nWhat would you like to update?");
			System.out.println("(1) Promotion Package Name");
			System.out.println("(2) Promotion Package Description");
			System.out.println("(3) Promotion Package Price");
			System.out.println("(4) Promotion Package Food");
			System.out.println("(5) Back"); 
			choice = ScannerExt.nextInt("\n    Enter the number of your choice: ", 1, 5);
		
			switch (choice) {
				case 1:
						updateMenuItemName(menuItem);
						break;
				case 2:
						updateMenuItemDesc(menuItem);
						break;
				case 3: 
						updateMenuItemPrice(menuItem);
						break;
				case 4:
						updatePromotionPackageFoodUI(menuItem);
						break;
				case 5:
			}
		
		} while (choice != 5);

	}
	
	/**
	 * Edit food contained in promotion package
	 * @param menuItem Promotion Package menu item to be edited
	 */
	public static void updatePromotionPackageFoodUI(MenuItem menuItem) {
		
		int choice;		
		PromotionPackage pp = (PromotionPackage) menuItem;
		
		do{
		
			System.out.println("\nWould you like to add or delete food from Promotion Package?");
			System.out.println("(1) Add food");
			System.out.println("(2) Remove food");
			System.out.println("(3) Back"); 
			choice = ScannerExt.nextInt("\n    Enter the number of your choice: ", 1, 3);
	
			switch (choice){
				case 1:
						System.out.println();
						pp.addFood(FoodMenuMgr.getRestaurantFoodMenu());
						break;
				case 2:
						System.out.println();
						pp.removeFood();
						break;
				case 3:
			}
		} while (choice !=  3);

	}
	
	/**
	 * Prompt user for menu item name to be updated
	 * @param menuItem Menu Item to be updated
	 */
	public static void updateMenuItemName(MenuItem menuItem){
		
		System.out.print("Enter Updated Menu Name : "); String menuName = sc.nextLine();
		FoodMenuMgr.updateMenuItemName(menuItem, menuName);
		
	}
	
	/**
	 * Prompt user for menu item description to be updated
	 * @param menuItem Menu Item to be updated
	 */
	public static void updateMenuItemDesc(MenuItem menuItem){

		System.out.print("Enter Updated Menu Description : "); String menuDesc = sc.nextLine();
		FoodMenuMgr.updateMenuItemDesc(menuItem, menuDesc);
		
	}
	
	/**
	 * Prompt user for menu item price to be updated
	 * @param menuItem Menu Item to be updated
	 */
	public static void updateMenuItemPrice(MenuItem menuItem){

		double menuPrice = ScannerExt.nextDouble("\nEnter Updated Menu Price : ");
		FoodMenuMgr.updateMenuItemPrice(menuItem, menuPrice);
		
	}
	
	/**
	 * Prompt user for a food course type
	 * @param promptMsg Message to ask user before prompting a selection
	 */
	public static CourseType selectFoodCourseTypeUI(String promptMsg){
		
		CourseType foodCourseType;
		
		System.out.println(promptMsg);
		int index = 0;
		for (CourseType courseType : CourseType.values())
			System.out.println("(" + index++ + ")" + " " + courseType);
		int choice = ScannerExt.nextInt("    Enter the number of your choice: ", 0, CourseType.values().length-1);
    	foodCourseType = Food.CourseType.values()[choice];
		
    	return foodCourseType;
    	
	}
	
}

