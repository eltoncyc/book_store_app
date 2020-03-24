/**
 * 
 */
package app.book.data.book;

import java.util.Comparator;



/**
 * @author EltonChan
 *
 */
public class BookSorters {
	
	public static class CompareByAuthorAscendingly implements Comparator<Book> {
		@Override
		public int compare(Book book1, Book book2) {
			return book1.getAuthors().compareTo(book2.getAuthors());
		}
	}
	
	public static class CompareByAuthorDescendingly implements Comparator<Book> {
		@Override
		public int compare(Book book1, Book book2) {
			return book2.getAuthors().compareTo(book1.getAuthors());
		}
	}
}
