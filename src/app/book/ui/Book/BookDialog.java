package app.book.ui.Book;

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
import app.book.data.book.Book;
import app.book.data.book.BookDao;
import app.book.data.book.BookSorters;
import app.book.ui.MainFrame;


@SuppressWarnings("serial")
public class BookDialog extends JDialog {

	private static final Logger LOG = LogManager.getLogger(BookStore.class);
	private final JPanel contentPanel = new JPanel();
	private BookListModel bookListModel;
	private JList<Book> booksList;


	/**
	 * Create the dialog.
	 */
	public BookDialog() {
		createUI();
		setData();
	}
	
	
	private void setData() {
		LOG.debug("BookDialog().setData()");
		BookDao bookDao;
		try {
			bookDao = BookDao.getInstance();
			Collection<Book> booksListValue = bookDao.getBooks().values();

			ArrayList<Book> booksList = new ArrayList<Book>(booksListValue);
			if(MainFrame.byAuthor && MainFrame.byBookDesc) {
				Collections.sort(booksList, new BookSorters.CompareByAuthorDescendingly());
			}
			if(MainFrame.byAuthor && !MainFrame.byBookDesc) {
				Collections.sort(booksList, new BookSorters.CompareByAuthorAscendingly());
			}
			
			
			for (Book book: booksList) {
				bookListModel.add(book);
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
						BookDialog.this.dispose();
						
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
						BookDialog.this.dispose();
					}

				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		bookListModel = new BookListModel();
		booksList = new JList<>(bookListModel);
		booksList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(new JScrollPane(booksList));
		
	}

}
