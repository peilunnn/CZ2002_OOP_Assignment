package ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import classes.Reservation;
import classes.Staff;
import classes.Table;
import mgr.ReservationMgr;
import mgr.TableMgr;
import user_lib.ScannerExt;

public class ReservationUI {

	private static Scanner sc = new Scanner(System.in);

	public static void menuShowReservationOptions(Staff staff) {

		int choice;

		do {
			System.out.println("\nSelect a choice: ");
			System.out.println("(1) Show table availability");
			System.out.println("(2) Walk in dining");
			System.out.println("(3) Make reservation");
			System.out.println("(4) Accept reservation");
			System.out.println("(5) Remove reservation");
			System.out.println("(6) Show reservations");
			System.out.println("(7) Back");
			System.out.println();
			choice = ScannerExt.nextInt("    Enter the number of your choice: ", 1, 7);

			switch (choice) {
				case 1:
					showTableAvailability();
					break;
				case 2:
					makeWalkInReservationUI(staff);
					break;
				case 3:
					makeReservationUI();
					break;
				case 4:
					acceptReservationUI(staff);
					break;
				case 5:
					removeReservationUI();
					break;
				case 6:
					showReservationsUI();
					break;
				case 7:
			}

		} while (choice < 7);

	}

	/**
	 * Show user list of reservations for the next 30 days
	 */
	private static void showReservationsUI() {

		ArrayList<Reservation> validReservations = ReservationMgr.getUnexpiredReservations();

		if (validReservations.size() <= 0) {
			System.out.println("No reservations found for the next 30 days.");
			return;
		}

		System.out.println("Showing reservations for the next 30 days: ");
		int index = 0;
		for (Reservation r : validReservations) {
			System.out.println("(" + (index++) + ") " + r);
		}

	}

	/**
	 * Prompt user for the reservation to be removed
	 */
	private static void removeReservationUI() {
		ArrayList<Reservation> removeReservations = ReservationMgr.getUnexpiredReservations();
		if (removeReservations.size() <= 0) {
			System.out.println("No reservations needed to be removed.");
			return;
		}

		int index = 0;
		for (Reservation r : removeReservations) {
			System.out.println("(" + (index++) + ") " + r);
		}
		int choice = ScannerExt.nextInt("Select which reservation to remove:");

		try {
			Reservation reservation = removeReservations.get(choice);
			ReservationMgr.moveToSettledReservation(reservation);
			System.out.println("Reservation removed.");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Fail to remove reservation! (Invalid index provided)");
		}

	}

	/**
	 * Prompt user for the reservation to be accepted. Accepting a reservation means
	 * creating an order from it
	 * 
	 * @param staff Staff creating the order from the reservation
	 */
	private static void acceptReservationUI(Staff staff) {

		ArrayList<Reservation> acceptReservations = ReservationMgr.getUnexpiredReservations();
		if (acceptReservations.size() <= 0) {
			System.out.println("No reservations to accept for current dining session.");
			return;
		}

		System.out.println("Select which reservation to accept:");
		int index = 0;
		for (Reservation ar : acceptReservations)
			System.out.println("(" + (index++) + ") " + ar);
		int choice = ScannerExt.nextInt("Select which reservation to accept:");

		try {
			Reservation reservation = acceptReservations.get(choice);
			ReservationMgr.acceptReservation(staff, reservation);
			System.out.println("Reservation accepted.");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Fail to accept reservation! (Invalid index provided)");
		}

	}

	/**
	 * Make a walk-in reservation by creating a reservation and then instantly
	 * create an order from it.
	 * 
	 * @param staff Staff creating the order from the reservation
	 */
	private static void makeWalkInReservationUI(Staff staff) {
		int numPax = ScannerExt.nextInt("Enter number of people: ");
		ReservationMgr.makeWalkInReservation(staff, numPax);
	}

	/**
	 * Prompts user for reservation details and make a reservation.
	 */
	private static void makeReservationUI() {

		System.out.print("Enter customer name: ");
		String customerName = sc.nextLine();
		int customerContact = ScannerExt.nextInt("Enter customer contact number: ");
		int numPax = ScannerExt.nextInt("Enter number of people: ");
		while (numPax > 10) {
			System.out.println("Only 10 or less pax for one reservation");
			numPax = ScannerExt.nextInt("Enter number of people: ");
		}

		Calendar arrivalTime = ReservationMgr.getValidReservationDateTime();
		ReservationMgr.makeReservation(customerName, customerContact, numPax, arrivalTime);

	}

	/**
	 * Prompt user for a reservation datetime and #pax and display available tables
	 * for reservation.
	 */
	private static void showTableAvailability() {

		int numPax = ScannerExt.nextInt("Enter number of people: ");
		Calendar reserveDateTime = ReservationMgr.getValidReservationDateTime();

		ArrayList<Table> availableTables = TableMgr.checkAvailableTables(reserveDateTime, numPax);
		if (availableTables == null || availableTables.size() <= 0) {
			System.out.println("No tables available for reservation on datetime " + reserveDateTime.getTime());
			return;
		}

		System.out.println("The following tables are available for Pax no " + numPax + " and datetime "
				+ reserveDateTime.getTime());
		System.out.println("Table number			Capacity");
		for (Table t : availableTables)
			System.out.println(t.getTableId() + "				" + t.getCapacity());

	}

}
