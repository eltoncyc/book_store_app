/**
 * 
 */
package app.book.data.purchase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.book.BookStore;
import app.book.data.book.BookDao;
import app.book.data.customer.CustomerDao;
import app.book.db.Dao;
import app.book.db.DbConstants;

/**
 * @author EltonChan
 *
 */
public class PurchaseDao extends Dao{
	public static final String TABLE_NAME = DbConstants.PURCHASE_TABLE_NAME;
	private static final Logger LOG = LogManager.getLogger(BookStore.class);
	private static PurchaseDao instance;
	
	private PurchaseDao() throws FileNotFoundException, IOException{
		super(TABLE_NAME);
	}
	
	public static PurchaseDao getInstance() throws FileNotFoundException, IOException {
		if (instance == null)
			instance = new PurchaseDao();

		return instance;
	}
	
	@Override
	public void create() throws SQLException {
		String sql = String.format(
				"create table %s(" 
				+ "%s BIGINT, " 
				+ "%s BIGINT, " 
				+ "%s BIGINT, " 
				+ "%s DECIMAL(10,2), " 
				+ "primary key (%s) )", 
		tableName, 
		Fields.PURCHASE_ID.getName(),
		Fields.CUSTOMER_ID.getName(),
		Fields.BOOK_ID.getName(),
		Fields.PRICE.getName(),
		Fields.PURCHASE_ID.getName()
				);
		LOG.debug(sql);
		super.create(sql);
		
	}
	
	public void add(Purchase purchase) throws SQLException {
		String sqlString = String.format("INSERT INTO %s values(?, ?, ?, ?)", TABLE_NAME);
		boolean result = execute(sqlString, //
				purchase.getId(), //
				purchase.getCustomerId(),
				purchase.getBookId(),
				purchase.getPrice());
		LOG.debug(String.format("Adding %s was %s", purchase, result ? "successful" : "unsuccessful"));
	}
	
	public ArrayList<Purchase> getPurchases() throws SQLException{
		ArrayList<Purchase>purchases = new ArrayList<>();
//		String sql1 = String.format("select * from %s", TABLE_NAME);
		
		String sql = String.format("select p.*, c.lastName, b.title from %s p\r\n" + 
				"left join %s c on c.customerId = p.customerId\r\n" + 
				"left join %s b on p.bookId = b.bookId", TABLE_NAME, CustomerDao.TABLE_NAME, BookDao.TABLE_NAME);
		
		
		ResultSet resultSet = super.executeSelect(sql);
		while(resultSet.next()) {
			

			purchases.add(new Purchase.Builder(resultSet.getLong(Fields.PURCHASE_ID.getName()),
					resultSet.getLong(Fields.CUSTOMER_ID.getName()),
					resultSet.getLong(Fields.BOOK_ID.getName()),
					resultSet.getFloat(Fields.PRICE.getName()))
					.setCustomerLastName(resultSet.getString("lastName"))
					.setBookTitle(resultSet.getString("title"))
					.build());
		}
		close(resultSet.getStatement());
		return purchases;
		
	}
	
	public ArrayList<Purchase> getPurchasesByCustomerID(Long customerId) throws SQLException{
		ArrayList<Purchase>purchases = new ArrayList<>();
//		String sql = String.format("select * from %s where %s = %d", TABLE_NAME, Fields.CUSTOMER_ID.name,customerId);
		
		String sql = String.format("select p.*, c.lastName, b.title from %s p\r\n" + 
				"left join %s c on c.customerId = p.customerId\r\n" + 
				"left join %s b on p.bookId = b.bookId where p.%s = %d", 
				TABLE_NAME, CustomerDao.TABLE_NAME, BookDao.TABLE_NAME,
				Fields.CUSTOMER_ID.name, customerId);
		
		
		ResultSet resultSet = super.executeSelect(sql);
		while(resultSet.next()) {
			
			purchases.add(new Purchase.Builder(resultSet.getLong(Fields.PURCHASE_ID.getName()),
					resultSet.getLong(Fields.CUSTOMER_ID.getName()),
					resultSet.getLong(Fields.BOOK_ID.getName()),
					resultSet.getFloat(Fields.PRICE.getName()))
					.setCustomerLastName(resultSet.getString("lastName"))
					.setBookTitle(resultSet.getString("title"))
					.build());
		}
		close(resultSet.getStatement());
		return purchases;
		
	}
	
	public enum Fields {

		PURCHASE_ID("purchaseId", "BIGINT", 10, 1), //
		CUSTOMER_ID("customerId", "VARCHAR", 20, 2), //
		BOOK_ID("bookId", "VARCHAR", 20, 3), //
		PRICE("price", "INT", 30, 4); //

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
