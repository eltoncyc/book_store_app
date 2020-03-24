/**
 * 
 */
package app.book.ui;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.book.BookStore;
import app.book.data.book.Book;
import app.book.data.book.BookDao;
import app.book.data.customer.Customer;
import app.book.data.customer.CustomerDao;
import app.book.data.purchase.Purchase;
import app.book.data.purchase.PurchaseDao;
import app.book.ui.Book.BookDialog;
import app.book.ui.Customer.CustomerDialog;
import app.book.ui.Customer.CustomerItemDialog;
import app.book.ui.Customer.CustomerListModel;
import app.book.ui.Purchase.CustomerIDFilterDialog;
import app.book.ui.Purchase.PurchaseDialog;

/**
 * @author EltonChan
 *
 */
public class UIController {
	private static final Logger LOG = LogManager.getLogger();

	private static JFrame mainFrame;

	@SuppressWarnings("static-access")
	public UIController(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	protected static class DropTableHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int input = JOptionPane.showConfirmDialog(mainFrame, "Do you want to drop all the data tables?");
			if(input == 0) {
				try {
					CustomerDao.getInstance().drop();
					BookDao.getInstance().drop();
					PurchaseDao.getInstance().drop();
					JOptionPane.showMessageDialog(mainFrame, "All tables have been removed from the database.");
					LOG.info("Removed all three data tables from the database.");
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
		
	}

	protected static class ExitMenuItemHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Instant endTime = Instant.now();
			LOG.info("End: " + endTime);
			LOG.info(String.format("Duration: %d ms", Duration.between(BookStore.startTime, endTime).toMillis()));
			System.exit(0);
		}
	}

	protected static class AboutMenuItemHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LOG.debug("About menu item pressed.");
			JOptionPane.showMessageDialog(mainFrame, "Second Assignment\nElton Chan A00999717");
		}

	}
	
	protected static class BookMenuItemHandeler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				BookDialog dialog = new BookDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);
			} catch (Exception exception) {
				exception.printStackTrace();
				LOG.error(exception.getMessage());
			}
		}

	}
	
	
	protected static class BookCountHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int count = 0;
			
			try {
				Collection<Book> books = BookDao.getInstance().getBooks().values();
				count = books.size();
			} catch (SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally {
				JOptionPane.showMessageDialog(mainFrame, "The total number of books is "+count);
			}
			
		}
		
	}
	
	
	
	
	protected static class CustomerMenuItemHandeler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				CustomerDialog dialog = new CustomerDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);
			} catch (Exception exception) {
				exception.printStackTrace();
				LOG.error(exception.getMessage());
			}
		}

	}
	
	protected static class CustomerCountHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int count = 0;
			
			try {
				Collection<Customer> customers = CustomerDao.getInstance().getCustomers().values();
				count = customers.size();
			} catch (SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally {
				JOptionPane.showMessageDialog(mainFrame, "The total number of customers is "+count);
			}
			
		}
		
	}
	
	public static class CustomersSelectionHandler implements ListSelectionListener {
		private JList<Customer> customersList;
		private CustomerListModel customerListModel;

		public CustomersSelectionHandler(JList<Customer> customersList) {
			this.customersList = customersList;
			customerListModel = (CustomerListModel) customersList.getModel();
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting()) {
				return;
			}

			Customer customer = customersList.getSelectedValue();
			if (customer != null) {
				LOG.debug("Customer selected: " + customersList.getSelectedIndex());
				updateCustomer(customer, customersList.getSelectedIndex());
			}
		}

		private void updateCustomer(Customer customer, int index) {
			try {
				CustomerDao customerDao = CustomerDao.getInstance();
				CustomerItemDialog dialog = new CustomerItemDialog(customer);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);

				customer = dialog.getCustomer();
				customerDao.update(customer);
				customerListModel.update(index, customer);
				LOG.debug("Customer " + customer.getId() + " updated");
			} catch (Exception exception) {
				exception.printStackTrace();
				LOG.error(exception.getMessage());
			}
			customersList.clearSelection();
		}
	}
	
	
	
	protected static class TotalPurchaseHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LOG.debug("Total purchases pressed.");
			float total = 0;
			try {
				PurchaseDao purchaseDao = PurchaseDao.getInstance();
				ArrayList<Purchase> purchaseList;
				if(CustomerIDFilterDialog.customerIdFilter != null && !CustomerIDFilterDialog.customerIdFilter.equals("")) {
					long id = Long.parseLong(CustomerIDFilterDialog.customerIdFilter);
					purchaseList = purchaseDao.getPurchasesByCustomerID(id);
				}else {
					purchaseList = purchaseDao.getPurchases();
				}

				for (Purchase purchase : purchaseList) {
					total += purchase.getPrice();
				}
			} catch (Exception exception) {
				exception.printStackTrace();
				LOG.error(exception.getMessage());
			}
			JOptionPane.showMessageDialog(mainFrame, "The total dollar amount of the purchase is " + String.valueOf(total));
		}
	}

	protected static class CustomerIdFilterHandeler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				CustomerIDFilterDialog dialog = new CustomerIDFilterDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);
			} catch (Exception exception) {
				exception.printStackTrace();
				LOG.error(exception.getMessage());
			}
		}

	}
	
	protected static class PurchaseMenuItemHandeler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				PurchaseDialog dialog = new PurchaseDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);
			} catch (Exception exception) {
				exception.printStackTrace();
				LOG.error(exception.getMessage());
			}
		}

	}
	
	


}
