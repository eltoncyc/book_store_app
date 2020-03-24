/**
 * 
 */
package app.book.data.customer;

import java.util.Comparator;

/**
 * @author EltonChan
 *
 */
public class CustomerSorters {
	public static class CompareByJoinedDate implements Comparator<Customer> {
		@Override
		public int compare(Customer customer1, Customer customer2) {
			return customer1.getJoinedDate().compareTo(customer2.getJoinedDate());
		}
	}
}
