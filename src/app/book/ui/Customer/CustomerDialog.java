package app.book.ui.Customer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JDialog;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.book.BookStore;
import app.book.data.customer.Customer;
import app.book.data.customer.CustomerDao;
import app.book.data.customer.CustomerSorters;
import app.book.ui.MainFrame;
import app.book.ui.UIController;


@SuppressWarnings("serial")
public class CustomerDialog extends JDialog {
	
	private static final Logger LOG = LogManager.getLogger(BookStore.class);
	private final JPanel contentPanel = new JPanel();
	private CustomerListModel customerListModel;
	private JList<Customer> customersList;

	/**
	 * Create the dialog.
	 */
	public CustomerDialog() {
		
		createUI();
		addEventHandlers();
		setData();
		
	}
	
	private void createUI() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
//		contentPanel.setLayout(new MigLayout("", "[]", "[]"));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						CustomerDialog.this.dispose();
						
					}
					
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						CustomerDialog.this.dispose();
					}

				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		customerListModel = new CustomerListModel();
		customersList = new JList<>(customerListModel);
		customersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(new JScrollPane(customersList));
		
	}
	
	private void setData() {
		LOG.debug("CustomerDialog().setData()");
		CustomerDao customerDao;
		try {
			customerDao = CustomerDao.getInstance();
			Collection<Customer> customersValue = customerDao.getCustomers().values();
			ArrayList<Customer> customersList = new ArrayList<Customer>(customersValue);
			if(MainFrame.byJoinedDate) {
				Collections.sort(customersList, new CustomerSorters.CompareByJoinedDate());
			}
			for (Customer customer : customersList) {

				customerListModel.add(customer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addEventHandlers() {

		customersList.getSelectionModel().addListSelectionListener(new UIController.CustomersSelectionHandler(customersList));
	}

}
