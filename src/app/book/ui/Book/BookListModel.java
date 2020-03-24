package app.book.ui.Book;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.book.BookStore;
import app.book.data.book.Book;


@SuppressWarnings("serial")
public class BookListModel extends AbstractListModel<Book>{

	private static final Logger LOG = LogManager.getLogger(BookStore.class);

	private ArrayList<Book> booksList;

	
	public BookListModel() {
		booksList = new ArrayList<>();

	}

	@Override
	public int getSize() {
		return booksList == null ? 0 : booksList.size();
	}

	@Override
	public Book getElementAt(int index) {
		return booksList.get(index);
	}

	public void setBookItems(ArrayList<Book> booksList) {
		this.booksList = booksList;

	}

	public ArrayList<Book> getBooks() {
		return booksList;
	}

	public void update(int index, Book book) {
		LOG.debug("BookListModel update book" + book.toString());
		booksList.set(index, book);

		fireContentsChanged(this, index, index);
	}
	
	public void add(Book book) {
		LOG.debug("BookListModel add book " + book.toString());
		add(-1, book);
	}

	public void add(int index, Book book) {
		if (index == -1) {
			booksList.add(book);
			index = getSize() - 1;
		} else {
			booksList.add(index, book);
		}

		fireContentsChanged(this, index, index);
	}

}
