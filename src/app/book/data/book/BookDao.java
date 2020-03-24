/**
 * 
 */
package app.book.data.book;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.book.BookStore;
import app.book.db.Dao;
import app.book.db.DbConstants;

/**
 * @author EltonChan
 *
 */
public class BookDao extends Dao{
	public static final String TABLE_NAME = DbConstants.BOOK_TABLE_NAME;
	private static final Logger LOG = LogManager.getLogger(BookStore.class);
	private static BookDao instance;
	
	private BookDao() throws FileNotFoundException, IOException{
		super(TABLE_NAME);
	}
	
	public static BookDao getInstance() throws FileNotFoundException, IOException {
		if (instance == null)
			instance = new BookDao();

		return instance;
	}
	
	@Override
	public void create() throws SQLException {
		String sql = String.format(
				"create table %s("
				+ "%s BIGINT, " 
				+ "%s VARCHAR(100), " 
				+ "%s VARCHAR(100), " 
				+ "%s INT, " 
				+ "%s VARCHAR(100), " 
				+ "%s DECIMAL(3,2), " 
				+ "%s INT, " 
				+ "%s VARCHAR(200), " 
				+ "primary key (%s) )", 
		tableName, 
		Fields.BOOK_ID.getName(), 
		Fields.ISBN.getName(), 
		Fields.AUTHOR.getName(), 
		Fields.PUBLISH_YEAR.getName(), 
		Fields.TITLE.getName(), 
		Fields.RATING.getName(), 
		Fields.RATING_COUNT.getName(), 
		Fields.IMAGE_URL.getName(), 
		Fields.BOOK_ID.getName()
				);
		LOG.debug(sql);
		super.create(sql);
		
	}
	
	public void add(Book book) throws SQLException {
		String sqlString = String.format("INSERT INTO %s values(?, ?, ?, ?, ?, ?, ?, ?)", TABLE_NAME);
		boolean result = execute(sqlString, //
				book.getId(), 
				book.getIsbn(), 
				book.getAuthors(),
				book.getYear(),
				book.getTitle(),
				book.getRating(),
				book.getRatingsCount(),
				book.getImageUrl());
		LOG.debug(String.format("Adding %s was %s", book, result ? "successful" : "unsuccessful"));
	}
	
	
	public Map<Long, Book> getBooks() throws SQLException{
		Map<Long, Book> books = new HashMap<Long, Book>();
		String sql = String.format("select * from %s", TABLE_NAME);
		ResultSet resultSet = super.executeSelect(sql);
		while(resultSet.next()) {

			Book book = new Book.Builder(resultSet.getLong(Fields.BOOK_ID.name)) //
					.setIsbn(resultSet.getString(Fields.ISBN.getName()))
					.setAuthors(resultSet.getString(Fields.AUTHOR.getName()))
					.setYear(resultSet.getInt(Fields.PUBLISH_YEAR.getName()))
					.setTitle(resultSet.getString(Fields.TITLE.getName()))
					.setRating(resultSet.getFloat(Fields.RATING.getName()))
					.setRatingsCount(resultSet.getInt(Fields.RATING_COUNT.getName()))
					.setImageUrl(resultSet.getString(Fields.IMAGE_URL.getName()))
					.build();
			books.put(resultSet.getLong(Fields.BOOK_ID.name), book);
		}
		return books;
		
	}
	
	public enum Fields {

		BOOK_ID("bookId", "VARCHAR", 10, 1), 
		ISBN("isbn", "VARCHAR", 100, 2), 
		AUTHOR("author", "VARCHAR", 100, 3), 
		PUBLISH_YEAR("publishYear", "INT", 30, 4), 
		TITLE("title", "VARCHAR", 100, 5), 
		RATING("rating", "NUMERIC", 1, 6), 
		RATING_COUNT("ratingCount", "INT", 0, 7), 
		IMAGE_URL("imageURL", "VARCHAR", 200, 8);

		private final String name;
		private final String type;
		private final int length;
		private final int column;

		Fields(String name, String type, int length, int column) {
			this.name = name;
			this.type = type;
			this.length = length;
			this.column = column;
		}

		public String getType() {
			return type;
		}

		public String getName() {
			return name;
		}

		public int getLength() {
			return length;
		}

		public int getColumn() {
			return column;
		}
	}
}
