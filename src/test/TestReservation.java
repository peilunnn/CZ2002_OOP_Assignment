package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import classes.*;
import db.Restaurant;
import mgr.ReservationMgr;

/**
 * Test the reservation class
 * 
 * @author soh jun jie
 * @version 1.0
 * @since 2016-11-3
 */
// @RunWith(PowerMockRunner.class)
// @PrepareForTest(Calendar.class)
public class TestReservation {

	private static ArrayList<Table> testTables;
	private static ArrayList<Reservation> testReservations;
	private static ArrayList<Order> testOrders;
	private static Staff testStaff;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		testTables = Restaurant.tables;
		testReservations = Restaurant.reservations;
		testOrders = Restaurant.orders;
		testStaff = Restaurant.staffs.get(0);

	}

	@Before
	public void setUp() throws Exception {

		Restaurant.settledReservations.clear();
		Restaurant.reservations.clear();
		Restaurant.orders.clear();
		Restaurant.invoices.clear();

		for (Table t : Restaurant.tables)
			t.getReservedBy().clear();
	}

	/**
	 * Ensure that reservation cannot be made when full reservation
	 */
	@Test
	public void testMakeReservationWhenFull() {

		Reservation newReservation;
		Calendar reserveCal = Calendar.getInstance();
		reserveCal.add(Calendar.DATE, 1);
		reserveCal.set(Calendar.HOUR_OF_DAY, Restaurant.AMSTARTTIME);
		reserveCal.set(Calendar.MINUTE, 0);
		reserveCal.set(Calendar.SECOND, 0);
		reserveCal.set(Calendar.MILLISECOND, 0);

		int index = 0;
		for (Table t : testTables) {
			newReservation = new Reservation("cust" + index, index, t.getCapacity(), reserveCal, t);
			testReservations.add(newReservation);
			index++;
		}
		assertEquals(testTables.size(), testReservations.size()); // confirm full reservation
		int fullReservationSize = testReservations.size();

		ReservationMgr.makeReservation("MakeReservationWhenFullCustomer", 98765432, 1, reserveCal);
		assertEquals(fullReservationSize, testReservations.size()); // ensure reservation size still same
	}

	// TODO: Mock calendar.getInstance() to get specific date
	@Test
	public void testMakeWalkInReservationWhenFull() {

		Reservation newReservation;
		Calendar now = Calendar.getInstance();

		int index = 0;
		for (Table t : testTables) {
			newReservation = new Reservation("cust" + index, index, t.getCapacity(), now, t);
			testReservations.add(newReservation);
			index++;
		}
		assertEquals(testTables.size(), testReservations.size()); // confirm full reservation
		int fullReservationSize = testReservations.size();
		int prevOrdersSize = testOrders.size();

		ReservationMgr.makeWalkInReservation(testStaff, 1);
		assertEquals(fullReservationSize, testReservations.size()); // ensure reservation size still same
		assertEquals(prevOrdersSize, testOrders.size()); // ensure order size is still same after failed reservation

	}

	// @Test
	// public void testMakeWalkInReservationSuccess(){
	//
	// Calendar now = Calendar.getInstance();
	// now.set(Calendar.HOUR_OF_DAY, Restaurant.AMStartTime);
	//
	// when(Calendar.getInstance()).thenReturn(now);
	//
	// ReservationMgr.makeWalkInReservation(testStaff, 1);
	// assertEquals(1, testOrders.size());
	//
	// }

	/**
	 * Ensure making reservation is successful
	 */
	@Test
	public void testMakeReservationSuccess() {

		Calendar reserveCal = Calendar.getInstance();
		reserveCal.add(Calendar.DATE, 1);
		reserveCal.set(Calendar.HOUR_OF_DAY, Restaurant.AMSTARTTIME);
		reserveCal.set(Calendar.MINUTE, 0);
		reserveCal.set(Calendar.SECOND, 0);
		reserveCal.set(Calendar.MILLISECOND, 0);

		ReservationMgr.makeReservation("MakeReservationCustomer", 98765432, 1, reserveCal);
		assertEquals(1, testReservations.size()); // ensure reservation size still same

	}

	/**
	 * Test reservation made 30min before now and not yet accepted is removed.
	 */
	@Test
	public void testRemoveExpiredReservation() {

		Calendar reserveCal;
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, -30);
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		int day = now.get(Calendar.DATE);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int minute = now.get(Calendar.MINUTE);

		assertEquals(0, Restaurant.reservations.size());

		Reservation newReservation;
		int index = 0;
		for (Table t : testTables) {
			reserveCal = new GregorianCalendar(year, month, day, hour, minute);
			newReservation = new Reservation("cust" + index, index, t.getCapacity(), reserveCal, t);
			testReservations.add(newReservation);
			index++;
		}

		ReservationMgr.removeExpiredReservation();
		assertEquals(0, testReservations.size());

	}

}
