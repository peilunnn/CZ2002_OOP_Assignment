package test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import db.Restaurant;


/**
 * Test runner class to run test suite and print
 * test case statistics
 * @author soh jun jie
 * @version 1.0
 * @since 2016-11-3
 */
public class TestRunner {

	public static void main(String[] args) {
		
		setupTestEnvironment();
		
		Result result = JUnitCore.runClasses(TestJunit.class);
		
		double runCount = result.getRunCount();
		double failCount = result.getFailureCount();
		double breakRate = failCount / runCount;
		
		System.out.println();
		System.out.println("Total tests run: " + result.getRunCount());
		System.out.println("Total tests failed: " + result.getFailureCount());
		System.out.println("% of code failed: " + 100 * breakRate + "%");
		System.out.println("Total time ran (nano): " + result.getRunTime());
		
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		
	}

	/**
	 * Set up restaurat database environment
	 */
	public static void setupTestEnvironment(){
		Restaurant.initTables();
		Restaurant.initReservations();
		Restaurant.initSettledReservations();
		Restaurant.initStaff();
		Restaurant.initOrders();
		Restaurant.initSettledOrders();
		Restaurant.initInvoices();
	}
	
}
