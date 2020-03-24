package app.book.ui.Purchase;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import app.book.data.purchase.Purchase;
import app.book.data.purchase.PurchaseDao;
import app.book.data.purchase.PurchaseSorters;
import app.book.ui.MainFrame;



@SuppressWarnings("serial")
public class PurchaseDialog extends JDialog {

	private static final Logger LOG = LogManager.getLogger(BookStore.class);
	private final JPanel contentPanel = new JPanel();
	private PurchaseListModel purchaseListModel;
	private JList<Purchase> purchasesList;

	/**
	 * Create the dialog.
	 */
	public PurchaseDialog() {
		createUI();
		setData();
	}
	
	private void setData() {
		LOG.debug("PurchaseDialog().setData()");
		PurchaseDao purchaseDao;
		try {
			purchaseDao = PurchaseDao.getInstance();
			ArrayList<Purchase> purchases;
			if(CustomerIDFilterDialog.customerIdFilter != null && !CustomerIDFilterDialog.customerIdFilter.equals("")) {
				long id = Long.parseLong(CustomerIDFilterDialog.customerIdFilter);
				purchases = purchaseDao.getPurchasesByCustomerID(id);
			}else {
				purchases = purchaseDao.getPurchases();
			}
			
			if(MainFrame.byTitle) {
				if(MainFrame.byPurchaseDesc) {
					Collections.sort(purchases, new PurchaseSorters.CompareTitleDescendingly());	
				}else {
					Collections.sort(purchases, new PurchaseSorters.CompareTitleAscendingly());
				}
			}
			
			if(MainFrame.byLastName) {
				if(MainFrame.byPurchaseDesc) {
					Collections.sort(purchases, new PurchaseSorters.CompareLastNameDecendingly());
				}else {
					Collections.sort(purchases, new PurchaseSorters.CompareLastNameAscendingly());
				}
			}
			
			
			for (Purchase purchase:purchases) {
				purchaseListModel.add(purchase);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void createUI() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
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
						PurchaseDialog.this.dispose();
						
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
						PurchaseDialog.this.dispose();
					}

				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		purchaseListModel = new PurchaseListModel();
		purchasesList = new JList<>(purchaseListModel);
		purchasesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(new JScrollPane(purchasesList));
	}

}
