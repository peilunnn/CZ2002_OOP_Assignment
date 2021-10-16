package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import classes.Food;
import classes.Food.CourseType;
import classes.Invoice;
import classes.MenuItem;
import classes.Order;
import classes.PromotionPackage;
import classes.Reservation;
import classes.Staff;
import classes.Table;

/**
 * Static restaurant database class keeping track of
 * objects in the restaurant
 * @author soh jun jie
 * @version 1.0
 * @since 2016-11-09
 */
public class Restaurant {

	public static final int		BOOKING_MTHINADVANCE		= 1;
	public static final	int		AMSTARTTIME					= 11;
	public static final	int		AMENDTIME					= 15;
	public static final	int		PMSTARTTIME					= 18;
	public static final	int		PMENDTIME					= 22;
	
	public static final Path 	DATAPATH 					= Paths.get(System.getProperty("user.dir"), "data");
	public static final String 	RESTAURANT_FILE_NAME		= "restaurant.dat";	
	
	public static ArrayList<Table> 				tables;
	public static ArrayList<Staff> 				staffs;
	public static ArrayList<MenuItem> 			foodMenu;	
	public static ArrayList<Invoice> 			invoices;	
	public static ArrayList<Order> 				orders;
	public static ArrayList<Order> 				settledOrders;
	public static ArrayList<Reservation>		reservations;
	public static ArrayList<Reservation>		settledReservations;
	
	/**
	 * Save restaurant current state
	 */
	public static void saveRestaurant(){
		
		if(!Files.exists(DATAPATH)){
			System.out.println("Data folder not found!");
			File dir = new File(DATAPATH.toString());
			if(dir.mkdir()){
				System.out.println("Directory " + DATAPATH + " created...");
			}
		}
		
		Object[] restaurantMember 	= {tables,
										staffs, 
										foodMenu, 
										invoices, 
										orders, 
										settledOrders, 
										reservations, 
										settledReservations};
		
		Path 				saveFileName 	= Paths.get(DATAPATH.toString(), RESTAURANT_FILE_NAME);
		FileOutputStream   	fos 			= null;
		ObjectOutputStream 	oos 			= null;
		
		try {
			fos = new FileOutputStream(saveFileName.toString());
			oos = new ObjectOutputStream(fos);
			oos.writeObject(restaurantMember);
			oos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * Load restaurant previous save state.
	 */
	@SuppressWarnings("unchecked")
	public static void loadRestaurant(){
		
		Object[] restaurantMember 	= null;
		Path saveData 				= Paths.get(DATAPATH.toString(), RESTAURANT_FILE_NAME);
		FileInputStream fis 		= null;
		ObjectInputStream ois 		= null;
		
		try {
			fis = new FileInputStream(saveData.toString());
			ois = new ObjectInputStream(fis);
			restaurantMember = (Object[]) ois.readObject();			
			if(restaurantMember != null){
				tables = (ArrayList<Table>) restaurantMember[0];
				staffs = (ArrayList<Staff>) restaurantMember[1];
				foodMenu = (ArrayList<MenuItem>) restaurantMember[2];
				invoices = (ArrayList<Invoice>) restaurantMember[3];
				orders = (ArrayList<Order>) restaurantMember[4];
				settledOrders = (ArrayList<Order>) restaurantMember[5];
				reservations = (ArrayList<Reservation>) restaurantMember[6];
				settledReservations = (ArrayList<Reservation>) restaurantMember[7];
			}
			ois.close();
		} catch (IOException ex) {
			System.out.println(RESTAURANT_FILE_NAME + " not found or does not exists. Default settings will be loaded.");
			initRestaurant();
		} catch (ClassCastException|ClassNotFoundException ex) {
			System.out.println("Data file " + RESTAURANT_FILE_NAME + " is corrupted. Default settings will be loaded instead.");
			initRestaurant();
		}
		
	}
	
	/**
	 * Initialise restaurant static members
	 */
	public static void initRestaurant(){
		initTables();
		initStaff();
		initFoodMenu();
		initInvoices();
		initOrders();
		initSettledOrders();
		initReservations();
		initSettledReservations();
	}
	
	/**
	 * Initialise restaurant tables
	 */
	public static void initTables(){
		ArrayList<Table> tables = new ArrayList<Table>();
		int i;
		for(i=0; i<5; i++)						// 5 x 10 seats
			tables.add(new Table(i, 10));	
		for(i=5; i<10; i++)						// 5 x 8 seats
			tables.add(new Table(i, 8));
		for(i=10; i<20; i++)					// 10 x 4 seats
			tables.add(new Table(i, 4));
		for(i=20; i<30; i++)					// 10 x 2 seats
			tables.add(new Table(i, 2));
		Restaurant.tables = tables;
	}
	
	/**
	 * Initialise restaurant staff
	 */
	public static void initStaff(){
		ArrayList<Staff> staffs = new ArrayList<Staff>();
		staffs.add(new Staff("John", 1, 'M', "Chef"));
		staffs.add(new Staff("May", 2, 'F', "Cashier"));
		staffs.add(new Staff("Nitro", 3, 'M', "Waiter"));
		staffs.add(new Staff("Miki", 4, 'F', "Cashier"));
		Restaurant.staffs = staffs;
	}
	
	/**
	 * Initialise restaurant food menu
	 */
	public static void initFoodMenu(){
		
		ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
		
		Food food1 = new Food("Burger", "Juicy American burger", 3.50, CourseType.MAIN_COURSE);
		Food food2 = new Food("Nuggets", "Nuggets - halal", 1.00, CourseType.MAIN_COURSE);
		Food food3 = new Food("Oreo McFlurry", "Ice cream dessert filled with crushed oreos", 3.85, CourseType.DESSERT);
		Food food4 = new Food("Apple Ice Cream", "Apple flavoured ice cream dessert", 8.00, CourseType.DESSERT);
		Food food5 = new Food("Ice Lemon Tea", "Homemade Ice Lemon Tea", 1.50, CourseType.DRINKS);
		Food food6 = new Food("Plain Water", "On the house", 0.00, CourseType.DRINKS);
		Food food7 = new Food("Healthy Fruit Juice", "Mixed fruit juice", 2.00, CourseType.DRINKS);
		
		PromotionPackage promo1 = new PromotionPackage("McNugget Meal", "6pc nuggets in 1", 3.00);
		promo1.addFood(food2);
		promo1.addFood(food2);
		promo1.addFood(food2);
		promo1.addFood(food2);
		promo1.addFood(food2);
		promo1.addFood(food2);

		PromotionPackage promo2 = new PromotionPackage("Burger set meal", "Burger set at cheaper price", 5.00);
		promo2.addFood(food1);
		promo2.addFood(food2);
		promo2.addFood(food7);

		PromotionPackage promo3 = new PromotionPackage("Healthy set meal", "Very healthy meal", 2.00);
		promo3.addFood(food7);
		promo3.addFood(food6);
		promo3.addFood(food4);

		menuItems.add((MenuItem) food1);
		menuItems.add((MenuItem) food2);
		menuItems.add((MenuItem) food3);
		menuItems.add((MenuItem) food4);
		menuItems.add((MenuItem) food5);
		menuItems.add((MenuItem) food6);
		menuItems.add((MenuItem) food7);
		menuItems.add((MenuItem) promo1);
		menuItems.add((MenuItem) promo2);
		menuItems.add((MenuItem) promo3);
		
		Restaurant.foodMenu = menuItems;
		
	}
	
	/**
	 * Initialise restaurant invoices
	 */
	public static void initInvoices(){
        Restaurant.invoices = new ArrayList<Invoice>();
	}

	/**
	 * Initialise restaurant orders
	 */
	public static void initOrders(){
		Restaurant.orders = new ArrayList<Order>();
	}

	/**
	 * Initialise restaurant settled orders
	 */
	public static void initSettledOrders(){
        Restaurant.settledOrders = new ArrayList<Order>();
	}

	/**
	 * Initialise restaurant reservations
	 */
	public static void initReservations(){
        Restaurant.reservations = new ArrayList<Reservation>();
	}
	
	/**
	 * Initialise restaurant settled reservations
	 */
	public static void initSettledReservations(){
        Restaurant.settledReservations = new ArrayList<Reservation>();
	}

}
