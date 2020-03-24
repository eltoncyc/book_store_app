package app.book.ui.Purchase;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import app.book.data.purchase.Purchase;
import app.book.data.purchase.PurchaseDao;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CustomerIDFilterDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField customerIdField;
	public static String customerIdFilter;
	

	/**
	 * Create the dialog.
	 */
	public CustomerIDFilterDialog() {
		createUI();
		
	}
	
	

	public void createUI() {
		setBounds(100, 100, 450, 178);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][][grow][]", "[][][]"));
		{
			JLabel lblNewLabel = new JLabel("Customer ID");
			contentPanel.add(lblNewLabel, "cell 1 1,alignx trailing");
		}
		{
			customerIdField = new JTextField();
			contentPanel.add(customerIdField, "cell 2 1,growx");
			customerIdField.setColumns(10);
		}
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
						customerIdFilter = customerIdField.getText();
						if(!customerIdFilter.equals("") && customerIdFilter != null) {
							long id = Long.parseLong(customerIdFilter);
							try {
								ArrayList<Purchase> purchases = PurchaseDao.getInstance().getPurchasesByCustomerID(id);
								if(purchases.size() > 0) {
									CustomerIDFilterDialog.this.dispose();
								}else {
									customerIdFilter = null;
									JOptionPane.showMessageDialog(CustomerIDFilterDialog.this, "No records found.");
								}
							} catch (SQLException | IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}else {
							CustomerIDFilterDialog.this.dispose();
						}
						
						
						
					}
					
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						CustomerIDFilterDialog.this.dispose();
						
					}
					
				});
				buttonPane.add(cancelButton);
			}
		}
	}
	


	

}
