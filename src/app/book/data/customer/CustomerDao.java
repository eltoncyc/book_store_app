/**
 * 
 */
package app.book.data.customer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.book.ApplicationException;
import app.book.BookStore;
import app.book.db.Dao;
import app.book.db.Database;
import app.book.db.DbConstants;

/**
 * @author EltonChan
 *
 */
public class CustomerDao extends Dao {
	public static final String TABLE_NAME = DbConstants.CUSTOMER_TABLE_NAME;
	private static final Logger LOG = LogManager.getLogger(BookStore.class);
	private static CustomerDao instance;
	
	private CustomerDao() throws FileNotFoundException, IOException{
		super(TABLE_NAME);
	}
	
	public static CustomerDao getInstance() throws FileNotFoundException, IOException {
		if (instance == null)
			instance = new CustomerDao();

		return instance;
	}
	
	@Override
	public void create() throws SQLException {
		LOG.debug("Creating database table " + TABLE_NAME);

		// With MS SQL Server, JOINED_DATE needs to be a DATETIME type.
		String sqlString = String.format("CREATE TABLE %s(" //
				+ "%s BIGINT, " // ID
				+ "%s VARCHAR(%d), " // FIRST_NAME
				+ "%s VARCHAR(%d), " // LAST_NAME
				+ "%s VARCHAR(%d), " // STREET
				+ "%s VARCHAR(%d), " // CITY
				+ "%s VARCHAR(%d), " // POSTAL_CODE
				+ "%s VARCHAR(%d), " // PHONE
				+ "%s VARCHAR(%d), " // EMAIL_ADDRESS
				+ "%s DATETIME, " // JOINED_DATE
				+ "PRIMARY KEY (%s))", // ID
				TABLE_NAME, //
				Fields.CUSTOMER_ID.name, //
				Fields.FIRST_NAME.name, Fields.FIRST_NAME.length, //
				Fields.LAST_NAME.name, Fields.LAST_NAME.length, //
				Fields.STREET_NAME.name, Fields.STREET_NAME.length, //
				Fields.CITY.name, Fields.CITY.length, //
				Fields.POSTAL_CODE.name, Fields.POSTAL_CODE.length, //
				Fields.PHONE_NUMBER.name, Fields.PHONE_NUMBER.length, //
				Fields.EMAIL.name, Fields.EMAIL.length, //
				Fields.JOINED_DATE.name, //
				Fields.CUSTOMER_ID.name);
		super.create(sqlString);
		
	}
	
	public void add(Customer customer) throws SQLException {
		String sqlString = String.format("INSERT INTO %s values(?, ?, ?, ?, ?, ?, ?, ?, ?)", TABLE_NAME);
		boolean result = execute(sqlString, //
				customer.getId(), //
				customer.getFirstName(), //
				customer.getLastName(), //
				customer.getStreet(), //
				customer.getCity(), //
				customer.getPostalCode(), //
				customer.getPhone(), //
				customer.getEmailAddress(), //
				toTimestamp(customer.getJoinedDate()));
		LOG.debug(String.format("Adding %s was %s", customer, result ? "successful" : "unsuccessful"));
	}
	
	/**
	 * Delete the customer from the database.
	 * 
	 * @param customer
	 * @throws SQLException
	 */
	public void delete(Customer customer) throws SQLException {
		Connection connection;
		Statement statement = null;
		try {
			connection = Database.getConnection();
			statement = connection.createStatement();

			String sqlString = String.format("DELETE FROM %s WHERE %s='%s'", TABLE_NAME, Fields.CUSTOMER_ID.name, customer.getId());
			LOG.debug(sqlString);
			int rowcount = statement.executeUpdate(sqlString);
			LOG.debug(String.format("Deleted %d rows", rowcount));
		} finally {
			close(statement);
		}
	}
	
	public void update(Customer customer) throws SQLException {
		String sqlString = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=?", TABLE_NAME, 
				Fields.FIRST_NAME.name, 
				Fields.LAST_NAME.name, 
				Fields.STREET_NAME.name, 
				Fields.CITY.name, 
				Fields.POSTAL_CODE.name, 
				Fields.PHONE_NUMBER.name, 
				Fields.EMAIL.name, 
				Fields.JOINED_DATE.name, 
				Fields.CUSTOMER_ID.name);
		LOG.debug("Update statment: " + sqlString);
		boolean result = execute(sqlString, customer.getFirstName(), customer.getLastName(), customer.getStreet(),
				customer.getCity(), customer.getPostalCode(), customer.getPhone(), customer.getEmailAddress(), 
				toTimestamp(customer.getJoinedDate()), customer.getId());
		LOG.debug(String.format("Updating %s was %s", customer, result ? "successful" : "unsuccessful"));
	}
	
	public Customer getCustomer(Long customerId) throws Exception {
		String sqlString = String.format("SELECT * FROM %s WHERE %s = %d", TABLE_NAME, Fields.CUSTOMER_ID.name, customerId);
		LOG.debug(sqlString);

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlString);

			int count = 0;
			while (resultSet.next()) {
				count++;
				if (count > 1) {
					throw new ApplicationException(String.format("Expected one result, got %d", count));
				}

				Timestamp timestamp = resultSet.getTimestamp(Fields.JOINED_DATE.name);
				LocalDate date = timestamp.toLocalDateTime().toLocalDate();

				Customer customer = new Customer.Builder(resultSet.getInt(Fields.CUSTOMER_ID.name), resultSet.getString(Fields.PHONE_NUMBER.name)) 
						.setFirstName(resultSet.getString(Fields.FIRST_NAME.name)) 
						.setLastName(resultSet.getString(Fields.LAST_NAME.name)) 
						.setStreet(resultSet.getString(Fields.STREET_NAME.name)) 
						.setCity(resultSet.getString(Fields.CITY.name)) 
						.setPostalCode(resultSet.getString(Fields.POSTAL_CODE.name)) 
						.setEmailAddress(resultSet.getString(Fields.EMAIL.name)) 
						.setJoinedDate(date).build();

				return customer;
			}
		} finally {
			close(statement);
		}

		return null;
	}
	
	public Map<Long, Customer> getCustomers() throws SQLException{
		Map<Long, Customer> customers = new HashMap<Long, Customer>();
		String sql = String.format("select * from %s", TABLE_NAME);
		ResultSet resultSet = super.executeSelect(sql);
		while(resultSet.next()) {
			Timestamp timestamp = resultSet.getTimestamp(Fields.JOINED_DATE.name);
			LocalDate date = timestamp.toLocalDateTime().toLocalDate();

			Customer customer = new Customer.Builder(resultSet.getInt(Fields.CUSTOMER_ID.name), resultSet.getString(Fields.PHONE_NUMBER.name)) 
					.setFirstName(resultSet.getString(Fields.FIRST_NAME.name)) 
					.setLastName(resultSet.getString(Fields.LAST_NAME.name)) 
					.setStreet(resultSet.getString(Fields.STREET_NAME.name)) 
					.setCity(resultSet.getString(Fields.CITY.name)) 
					.setPostalCode(resultSet.getString(Fields.POSTAL_CODE.name)) 
					.setEmailAddress(resultSet.getString(Fields.EMAIL.name)) 
					.setJoinedDate(date).build();
			customers.put(resultSet.getLong(Fields.CUSTOMER_ID.name), customer);
		}
		return customers;
		
	}
	
	
	public enum Fields {

		CUSTOMER_ID("customerId", "VARCHAR", 16, 1), 
		FIRST_NAME("firstName", "VARCHAR", 100, 2), 
		LAST_NAME("lastName", "VARCHAR", 100, 3), 
		STREET_NAME("streetName", "VARCHAR", 100, 4), 
		CITY("city", "VARCHAR", 100, 5), 
		POSTAL_CODE("postalCode", "VARCHAR", 100, 6), 
		PHONE_NUMBER("phoneNUMBER", "VARCHAR", 100, 7), 
		EMAIL("email", "VARCHAR", 140, 8), 
		JOINED_DATE("joinedDate", "DATE", -1, 9);

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
