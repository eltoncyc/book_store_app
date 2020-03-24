package app.book.ui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JMenuItem mnDrop;
	private JMenuItem mnQuit;
	private JMenuItem mnBooksCount;
	private JCheckBoxMenuItem mnByAuthor;
	private JCheckBoxMenuItem mnDescend;
	private JMenuItem mnBookList;
	private JMenuItem mnCustomersCount;
	private JCheckBoxMenuItem mnJoinDate;
	private JMenuItem mnCustomerList;
	private JMenuItem mnPurchasesTotal;
	private JCheckBoxMenuItem mnLastName;
	private JCheckBoxMenuItem mnTitle;
	private JCheckBoxMenuItem mnPurchaseDescend;
	private JMenuItem mnFilterCustomer;
	private JMenuItem mnPurchasesList;
	private JMenuItem mnAbout;
	
	public static boolean byJoinedDate;
	public static boolean byAuthor;
	public static boolean byBookDesc;
	public static boolean byTitle;
	public static boolean byLastName;
	public static boolean byPurchaseDesc;
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		createUI();
		addEventHandlers();
	}
	
	private void createUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[]", "[]"));
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		mnDrop = new JMenuItem("Drop");
		mnFile.add(mnDrop);
		mnQuit = new JMenuItem("Quit");
		mnFile.add(mnQuit);

		JMenu mnBooks = new JMenu("Books");
		menuBar.add(mnBooks);
		mnBooksCount = new JMenuItem("Count");
		mnBooks.add(mnBooksCount);
		mnByAuthor = new JCheckBoxMenuItem("By Author");
		mnBooks.add(mnByAuthor);
		mnDescend = new JCheckBoxMenuItem("Descending");
		mnBooks.add(mnDescend);
		mnBookList = new JMenuItem("List");
		mnBooks.add(mnBookList);
		
		JMenu mnCustomers = new JMenu("Customers");
		menuBar.add(mnCustomers);
		mnCustomersCount = new JMenuItem("Count");
		mnCustomers.add(mnCustomersCount);
		mnJoinDate = new JCheckBoxMenuItem("By Join Date");
		mnCustomers.add(mnJoinDate);
		mnCustomerList = new JMenuItem("List");
		mnCustomers.add(mnCustomerList);
		
		JMenu mnPurchases = new JMenu("Purchases");
		menuBar.add(mnPurchases);
		mnPurchasesTotal = new JMenuItem("Total");
		mnPurchases.add(mnPurchasesTotal);
		mnLastName = new JCheckBoxMenuItem("By Last Name");
		mnPurchases.add(mnLastName);
		mnTitle = new JCheckBoxMenuItem("By Title");
		mnPurchases.add(mnTitle);
		mnPurchaseDescend = new JCheckBoxMenuItem("Descending");
		mnPurchases.add(mnPurchaseDescend);
		mnFilterCustomer = new JMenuItem("Filter by Customer ID");
		mnPurchases.add(mnFilterCustomer);
		mnPurchasesList = new JMenuItem("List");
		mnPurchases.add(mnPurchasesList);
		
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		mnAbout = new JMenuItem("About");
		mnAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
		mnHelp.add(mnAbout);
		
	}
	
	private void addEventHandlers() {
		new UIController(this);
		
		mnDrop.addActionListener(new UIController.DropTableHandler());
		mnQuit.addActionListener(new UIController.ExitMenuItemHandler());
		mnAbout.addActionListener(new UIController.AboutMenuItemHandler());
		mnCustomersCount.addActionListener(new UIController.CustomerCountHandler());
		mnCustomerList.addActionListener(new UIController.CustomerMenuItemHandeler());
		mnBooksCount.addActionListener(new UIController.BookCountHandler());
		mnBookList.addActionListener(new UIController.BookMenuItemHandeler());
		mnPurchasesTotal.addActionListener(new UIController.TotalPurchaseHandler());
		mnPurchasesList.addActionListener(new UIController.PurchaseMenuItemHandeler());
		mnFilterCustomer.addActionListener(new UIController.CustomerIdFilterHandeler());
		
		mnJoinDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				byJoinedDate = mnJoinDate.getState();
			}
			
		});
		
		mnByAuthor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				byAuthor = mnByAuthor.getState();
			}
			
		});
		
		mnDescend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				byBookDesc = mnDescend.getState();
			}
			
		});
		
		mnTitle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				byTitle = mnTitle.getState();
			}
			
		});
		
		mnLastName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				byLastName = mnLastName.getState();
			}
			
		});
		
		mnPurchaseDescend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				byPurchaseDesc = mnPurchaseDescend.getState();
			}
			
		});
		
		
		
	}

}
