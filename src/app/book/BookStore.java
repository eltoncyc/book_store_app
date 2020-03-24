package app.book;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;

import java.util.Arrays;
import java.util.Collection;


import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

import app.book.data.AllData;
import app.book.data.book.Book;
import app.book.data.book.BookDao;
import app.book.data.customer.Customer;
import app.book.data.customer.CustomerDao;
import app.book.data.purchase.Purchase;
import app.book.data.purchase.PurchaseDao;
import app.book.db.Database;
import app.book.ui.MainFrame;

/**
 * Project: Books
 * File: BookStore.java
 */

public class BookStore {

    private static final String LOG4J_CONFIG_FILENAME = "log4j2.xml";
    public static final Instant startTime = Instant.now();
    static {
        configureLogging();
    }
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Book Constructor. Processes the commandline arguments
     * ex. -inventory -make=honda -by_count -desc -total -service
     * 
     * @throws ApplicationException
     * @throws ParseException
     */
    public BookStore(String[] args) throws ApplicationException {
        LOG.debug("Input args: " + Arrays.toString(args));
        BookOptions.process(args);
    }

    /**
     * Entry point to GIS
     * 
     * @param args
     * @throws SQLException 
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, SQLException, IOException {
        LOG.info("Starting Books");
        Instant startTime = Instant.now();
        LOG.info(startTime);

        // start the Bookstore System
        try {
            BookStore bookStore = new BookStore(args);
            bookStore.loadFileData();
            bookStore.loadCustomersToDB();
            bookStore.loadBooksToDB();
            bookStore.loadPurchasesToDB();
            bookStore.createUI();
        } catch (ApplicationException  e) {
            // e.printStackTrace();
            LOG.debug(e.getMessage());
        }

        Instant endTime = Instant.now();
        LOG.info(endTime);
        LOG.info(String.format("Duration: %d ms", Duration.between(startTime, endTime).toMillis()));
        LOG.info("Books has stopped");
    }

    /**
     * Configures log4j2 from the external configuration file specified in LOG4J_CONFIG_FILENAME.
     * If the configuration file isn't found then log4j2's DefaultConfiguration is used.
     */
    private static void configureLogging() {
        ConfigurationSource source;
        try {
            source = new ConfigurationSource(new FileInputStream(LOG4J_CONFIG_FILENAME));
            Configurator.initialize(null, source);
        } catch (IOException e) {
            System.out.println(String.format("WARNING! Can't find the log4j logging configuration file %s; using DefaultConfiguration for logging.",
                    LOG4J_CONFIG_FILENAME));
            Configurator.initialize(new DefaultConfiguration());
        }
    }
    
    private void loadFileData() throws SQLException, FileNotFoundException, IOException, ApplicationException {
    	Database.getInstance();
    	if(!Database.tableExists(CustomerDao.TABLE_NAME) || 
    			!Database.tableExists(BookDao.TABLE_NAME) || 
    			!Database.tableExists(PurchaseDao.TABLE_NAME)) {
    		AllData.loadData();
    	}
    }
    
   	@SuppressWarnings("static-access")
	private void loadCustomersToDB() throws ApplicationException{
       	LOG.debug("loadCustomersToDB()");
       	
       	
       	try {
       		CustomerDao customerDao = CustomerDao.getInstance();
       		
       		Database.getInstance();
       		if(!Database.tableExists(customerDao.TABLE_NAME)) {
       			Collection<Customer> customers = AllData.getCustomers().values();
       			customerDao.create();
       			for(Customer customer: customers) {
       				customerDao.add(customer);
       			}
       		}
       	}catch(Exception e) {
       		e.printStackTrace();
       		LOG.error(e.getMessage());
       	}
       	}
    
    @SuppressWarnings("static-access")
   	public void loadBooksToDB() throws ApplicationException{
       	LOG.debug("loadCustomersToDB()");
       	
       	
       	try {
       		BookDao bookDao = BookDao.getInstance();
       		
       		Database.getInstance();
       		if(!Database.tableExists(bookDao.TABLE_NAME)) {
       			Collection<Book> books = AllData.getBooks().values();
       			bookDao.create();
       			for(Book book:books) {
       				bookDao.add(book);
       			}
       		}
       	}catch(Exception e) {
       		e.printStackTrace();
       		LOG.error(e.getMessage());
       	}
       }
    
    @SuppressWarnings("static-access")
   	public void loadPurchasesToDB() throws ApplicationException{
       	LOG.debug("loadCustomersToDB()");
       	
       	
       	try {
       		PurchaseDao purchaseDao = PurchaseDao.getInstance();
       		
       		Database.getInstance();
       		if(!Database.tableExists(purchaseDao.TABLE_NAME)) {
       			Collection<Purchase> purchases = AllData.getPurchases().values();
       			purchaseDao.create();
       			for(Purchase purchase:purchases) {
       				purchaseDao.add(purchase);
       			}
       		}
       	}catch(Exception e) {
       		e.printStackTrace();
       		LOG.error(e.getMessage());
       	}
       }
    
    private void createUI() {
    	try {
			MainFrame frame = new MainFrame();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
