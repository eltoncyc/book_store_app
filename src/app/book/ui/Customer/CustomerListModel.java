package app.book.ui.Customer;

import java.util.ArrayList;


import javax.swing.AbstractListModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.book.BookStore;
import app.book.data.customer.Customer;

@SuppressWarnings("serial")
public class CustomerListModel extends AbstractListModel<Customer>{
	private static final Logger LOG = LogManager.getLogger(BookStore.class);

	private ArrayList<Customer> customersList;

	
	public CustomerListModel() {
		customersList = new ArrayList<>();

	}

	@Override
	public int getSize() {
		return customersList == null ? 0 : customersList.size();
	}

	@Override
	public Customer getElementAt(int index) {
		return customersList.get(index);
	}

	public void setCustomerItems(ArrayList<Customer> customersList) {
		this.customersList = customersList;

	}

	public ArrayList<Customer> getCustomers() {
		return customersList;
	}

	public void update(int index, Customer customer) {
		LOG.debug("CustomerListModel update item " + customer.toString());
		customersList.set(index, customer);

		fireContentsChanged(this, index, index);
	}
	
	public void add(Customer customer) {
		LOG.debug("CustomerListModel add customer " + customer.toString());
		add(-1, customer);
	}

	public void add(int index, Customer customer) {
		if (index == -1) {
			customersList.add(customer);
			index = getSize() - 1;
		} else {
			customersList.add(index, customer);
		}

		fireContentsChanged(this, index, index);
	}


}
