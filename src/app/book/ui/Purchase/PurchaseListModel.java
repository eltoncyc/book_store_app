/**
 * 
 */
package app.book.ui.Purchase;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.book.BookStore;
import app.book.data.purchase.Purchase;

/**
 * @author EltonChan
 *
 */
@SuppressWarnings("serial")
public class PurchaseListModel extends AbstractListModel<Purchase>{

	private static final Logger LOG = LogManager.getLogger(BookStore.class);

	private ArrayList<Purchase> purchasesList;

	
	public PurchaseListModel() {
		purchasesList = new ArrayList<>();

	}

	@Override
	public int getSize() {
		return purchasesList == null ? 0 : purchasesList.size();
	}

	@Override
	public Purchase getElementAt(int index) {
		return purchasesList.get(index);
	}

	public void setPurchaseItems(ArrayList<Purchase> purchasesList) {
		this.purchasesList = purchasesList;

	}

	public ArrayList<Purchase> getPurchases() {
		return purchasesList;
	}

	public void update(int index, Purchase purchase) {
		LOG.debug("PurchaseListModel update purchase" + purchase.toString());
		purchasesList.set(index, purchase);

		fireContentsChanged(this, index, index);
	}
	
	public void add(Purchase purchase) {
		LOG.debug("PurchaseListModel add book " + purchase.toString());
		add(-1, purchase);
	}

	public void add(int index, Purchase purchase) {
		if (index == -1) {
			purchasesList.add(purchase);
			index = getSize() - 1;
		} else {
			purchasesList.add(index, purchase);
		}

		fireContentsChanged(this, index, index);
	}
	

}
