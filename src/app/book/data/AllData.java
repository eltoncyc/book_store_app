/**
 * 
 */
package app.book.data;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.book.ApplicationException;
import app.book.data.book.Book;
import app.book.data.customer.Customer;
import app.book.data.purchase.Purchase;
import app.book.io.BookReader;
import app.book.io.CustomerReader;
import app.book.io.PurchaseReader;


public class AllData {

	private static final Logger LOG = LogManager.getLogger();

	private static Map<Long, Book> books;
	private static Map<Long, Customer> customers;
	private static Map<Long, Purchase> purchases;

	private AllData() {
	}

	/**
	 * @throws ApplicationException
	 * 
	 */
	public static void loadData() throws ApplicationException {
		LOG.debug("loading the data");
		books = BookReader.read();
		customers = CustomerReader.read();
		purchases = PurchaseReader.read();
		LOG.debug("successfully loaded the data");
	}

	/**
	 * @return the customers
	 */
	public static Map<Long, Customer> getCustomers() {
		return customers;
	}

	/**
	 * @return the books
	 */
	public static Map<Long, Book> getBooks() {
		return books;
	}

	/**
	 * @return the purchases
	 */
	public static Map<Long, Purchase> getPurchases() {
		return purchases;
	}

}
