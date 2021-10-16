package test;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import classes.*;
import db.Restaurant;
import mgr.ReservationMgr;
import ui.OrderUI;

/**
 * Test order class and methods
 * @author soh jun jie
 * @version 1.0
 * @since 2016-11-3
 */
public class TestOrder {

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
		
		for(Table t : Restaurant.tables)
			t.getReservedBy().clear();
		
	}

	/**
	 * Ensure table status changes to VACATED after invoice
	 * printed
	 */
	@Test
	public void testTableReleaseAfterInvoicePrinted() {
		
		Calendar now = Calendar.getInstance();
		
		Table reserveTable = testTables.get(0);
		Reservation newReservation = new Reservation("TestCustomer", 98765432, reserveTable.getCapacity(), now, reserveTable);
		testReservations.add(newReservation);
		
		// Reservation for the table must be 1 due to being reserved
		assertEquals(1, reserveTable.getReservedBy().size());
		
		// Table status must switch to occupied once accepted, order must be created
		ReservationMgr.acceptReservation(testStaff, newReservation);
		assertEquals(Table.TableStatus.OCCUPIED, reserveTable.getStatus());
		assertEquals(1, testOrders.size());
		
		Order order = testOrders.get(0);
		Class<OrderUI> c = OrderUI.class;

		@SuppressWarnings("rawtypes")
		Class[] cArg = new Class[1];
        cArg[0] = Order.class;
		
		Method method = null;
		
		try {
			method = c.getDeclaredMethod("printInvoice", cArg);
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
		
		method.setAccessible(true);
		
		// Table reserved by must be removed, status must be vacated
		try {
			method.invoke(null, order);
			assertEquals(0, reserveTable.getReservedBy().size());
			assertEquals(Table.TableStatus.VACATED, reserveTable.getStatus());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}

}
