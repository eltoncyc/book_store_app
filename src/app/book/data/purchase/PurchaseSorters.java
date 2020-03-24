/**
 * 
 */
package app.book.data.purchase;

import java.util.Comparator;



/**
 * @author EltonChan
 *
 */
public class PurchaseSorters {
	public static class CompareLastNameAscendingly implements Comparator<Purchase>{

		@Override
		public int compare(Purchase o1, Purchase o2) {
			// TODO Auto-generated method stub
			return o1.getCustomerLastName().compareTo(o2.getCustomerLastName());
		}
		
	}
	
	public static class CompareLastNameDecendingly implements Comparator<Purchase>{

		@Override
		public int compare(Purchase o1, Purchase o2) {
			// TODO Auto-generated method stub
			return o2.getCustomerLastName().compareTo(o1.getCustomerLastName());
		}
		
	}
	
	public static class CompareTitleAscendingly implements Comparator<Purchase>{

		@Override
		public int compare(Purchase o1, Purchase o2) {
			return o1.getBookTitle().compareTo(o2.getBookTitle());
		}
		
	}
	
	public static class CompareTitleDescendingly implements Comparator<Purchase>{

		@Override
		public int compare(Purchase o1, Purchase o2) {
			return o2.getBookTitle().compareTo(o1.getBookTitle());
		}
		
	}
}
