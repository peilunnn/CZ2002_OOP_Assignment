package user_lib;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Extension of the functionality of scanner class.
 * Improve error handling due to unexpected user input.
 * @author soh jun jie
 * @version 1.0
 * @since 2016-11-09
 */
public class ScannerExt {

	private static Scanner sc = new Scanner(System.in);
	
	/**
	 * Get next integer from user.
	 * @param promptMsg Error message when input entered is not integer
	 * @return Entered integer
	 */
	public static int nextInt(String promptMsg){
		
		int choice = 0;
		boolean valid = true;
		
		do{
			try{
				valid = true;
				System.out.print(promptMsg);
				choice = sc.nextInt();
			}catch(InputMismatchException e){
				valid = false;
				sc.nextLine();	// get dummy line
				System.out.println("Error, expected a integer number value");
			}
		}while(!valid);
		
		sc.nextLine();	// get dummy line
		return choice;
	}
	
	/**
	 * Get next integer from user within a specified range.
	 * @param promptMsg Error message when input entered is not integer
	 * @param lbound Lower bound
	 * @param ubound Upper bound
	 * @return Entered integer
	 */
	public static int nextInt(String promptMsg, int lbound, int ubound){
		
		int choice = 0;
		boolean valid = true;
		
		do{
			try{
				valid = true;
				System.out.print(promptMsg);
				choice = sc.nextInt();
				valid = (choice >= lbound && choice <= ubound);
				if(!valid){
					System.out.println("Values must be between " + lbound + " and " + ubound + " inclusive.");
					sc.nextLine();	// get dummy line
				}
					
			}catch(InputMismatchException e){
				valid = false;
				sc.nextLine();	// get dummy line
				System.out.println("Error, expected a integer number value");
			}
		}while(!valid);
		
		sc.nextLine();	// get dummy line
		return choice;
	}
	
	/**
	 * Get double value from user
	 * @param promptMsg Error message when entered value is not double
	 * @return Entered double
	 */
	public static double nextDouble(String promptMsg){
		
		double choice = 0;
		boolean valid = true;
		
		do{
			try{
				valid = true;
				System.out.print(promptMsg);
				choice = sc.nextDouble();
			}catch(InputMismatchException e){
				valid = false;
				sc.nextLine();	// get dummy line
				System.out.println("Error, expected a integer number value");
			}
		}while(!valid);
		
		sc.nextLine();	// get dummy line
		return choice;
		
	}
	
}
