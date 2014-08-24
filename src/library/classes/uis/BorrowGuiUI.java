package library.classes.uis;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import library.classes.exceptions.BookNotFoundException;
import library.classes.exceptions.BorrowerNotFoundException;
import library.interfaces.controls.IBorrowCTL;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.uis.IBorrowUI;

import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Font;

public class BorrowGuiUI extends JFrame implements IBorrowUI {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnSwipe, btnScan, btnContinue, btnComplete, btnCancel;
	private JTextField borrowerIDTF, bookIDTF;
	private JLabel lblBookId;
	private JTextArea pendingLoanListTA, currentBookTA, existingLoanListTA;
	private JScrollPane pendingLoanListSCL, currentBookSCL, existingLoanListSCL;
	private JLabel lblBorrowerName, lblBorrowerContact;
	private JLabel lblOverdue, lblFineLimit, lblLoanLimit;
	private JLabel lblErrMesg;
	private List<ILoan> completedList;
	
	private IBorrowCTL control;

	public BorrowGuiUI() {
		setResizable(false);
		setTitle("Borrow Book");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 759);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "CardScanner", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 211, 112);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblBorrowerId = new JLabel("Borrower ID:");
		lblBorrowerId.setBounds(10, 23, 75, 14);
		panel.add(lblBorrowerId);
		
		borrowerIDTF = new JTextField();
		borrowerIDTF.setBounds(20, 42, 163, 20);
		panel.add(borrowerIDTF);
		borrowerIDTF.setColumns(10);
		
		btnSwipe = new JButton("Swipe");
		btnSwipe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onSwipe();
			}
		});
		btnSwipe.setBounds(56, 73, 89, 23);
		panel.add(btnSwipe);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "BookScanner", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(233, 11, 211, 112);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		lblBookId = new JLabel("Book ID:");
		lblBookId.setBounds(10, 23, 46, 14);
		panel_1.add(lblBookId);
		
		bookIDTF = new JTextField();
		bookIDTF.setBounds(20, 45, 159, 20);
		panel_1.add(bookIDTF);
		bookIDTF.setColumns(10);
		
		btnScan = new JButton("Scan");
		btnScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onScan();
			}
		});
		btnScan.setBounds(58, 76, 89, 23);
		panel_1.add(btnScan);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Current Loan", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 431, 434, 242);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Pending Loan List", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(10, 107, 414, 126);
		panel_2.add(panel_4);
		panel_4.setLayout(null);
		
		pendingLoanListSCL = new JScrollPane();
		pendingLoanListSCL.setBounds(10, 18, 394, 96);
		panel_4.add(pendingLoanListSCL);
		
		pendingLoanListTA = new JTextArea();
		pendingLoanListTA.setFont(new Font("Tahoma", Font.PLAIN, 11));
		pendingLoanListSCL.setViewportView(pendingLoanListTA);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Current Book", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBounds(10, 18, 414, 89);
		panel_2.add(panel_5);
		panel_5.setLayout(null);
		
		currentBookSCL = new JScrollPane();
		currentBookSCL.setBounds(10, 18, 394, 60);
		panel_5.add(currentBookSCL);
		
		currentBookTA = new JTextArea();
		currentBookTA.setFont(new Font("Tahoma", Font.PLAIN, 11));
		currentBookTA.setEditable(false);
		currentBookSCL.setViewportView(currentBookTA);
		
		btnComplete = new JButton("Complete");
		btnComplete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onComplete();
			}
		});
		btnComplete.setBounds(355, 134, 89, 23);
		contentPane.add(btnComplete);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onCancel();
			}
		});
		btnCancel.setBounds(10, 134, 89, 23);
		contentPane.add(btnCancel);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new TitledBorder(null, "Borrower Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(10, 168, 434, 252);
		contentPane.add(panel_3);
		
		JLabel label = new JLabel("Name: ");
		label.setBounds(10, 21, 46, 14);
		panel_3.add(label);
		
		lblBorrowerName = new JLabel("Fred Nurke");
		lblBorrowerName.setForeground(Color.BLUE);
		lblBorrowerName.setBackground(Color.LIGHT_GRAY);
		lblBorrowerName.setBounds(54, 21, 153, 14);
		panel_3.add(lblBorrowerName);
		
		JLabel label_2 = new JLabel("Contact:");
		label_2.setBounds(217, 21, 56, 14);
		panel_3.add(label_2);
		
		lblBorrowerContact = new JLabel("02 63384931");
		lblBorrowerContact.setForeground(Color.BLUE);
		lblBorrowerContact.setBounds(268, 21, 146, 14);
		panel_3.add(lblBorrowerContact);
		
		JPanel panel_6 = new JPanel();
		panel_6.setLayout(null);
		panel_6.setBorder(new TitledBorder(null, "Existing Loans", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.setBounds(10, 112, 414, 129);
		panel_3.add(panel_6);
		
		existingLoanListSCL = new JScrollPane();
		existingLoanListSCL.setBounds(10, 22, 394, 96);
		panel_6.add(existingLoanListSCL);
		
		existingLoanListTA = new JTextArea();
		existingLoanListTA.setFont(new Font("Tahoma", Font.PLAIN, 11));
		existingLoanListTA.setEditable(false);
		existingLoanListTA.setBackground(new Color(248, 248, 248));
		existingLoanListSCL.setViewportView(existingLoanListTA);
		
		JPanel panel_7 = new JPanel();
		panel_7.setLayout(null);
		panel_7.setBorder(new TitledBorder(null, "Borrowing Restrictions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_7.setBounds(10, 46, 414, 66);
		panel_3.add(panel_7);
		
		lblOverdue = new JLabel("Overdue Loan restriction");
		lblOverdue.setForeground(Color.RED);
		lblOverdue.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOverdue.setBounds(10, 18, 354, 14);
		panel_7.add(lblOverdue);
		
		lblFineLimit = new JLabel("Fine Limit reached restriction");
		lblFineLimit.setForeground(Color.RED);
		lblFineLimit.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFineLimit.setBounds(10, 32, 354, 14);
		panel_7.add(lblFineLimit);
		
		lblLoanLimit = new JLabel("Loan Limit Reached Restriction");
		lblLoanLimit.setForeground(Color.RED);
		lblLoanLimit.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLoanLimit.setBounds(10, 46, 354, 14);
		panel_7.add(lblLoanLimit);
		
		lblErrMesg = new JLabel("New label");
		lblErrMesg.setForeground(Color.RED);
		lblErrMesg.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblErrMesg.setBounds(10, 688, 434, 29);
		contentPane.add(lblErrMesg);
		
		btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onContinue();
			}
		});
		btnContinue.setBounds(233, 134, 89, 23);
		contentPane.add(btnContinue);
	}

	@Override
	public void initialise(IBorrowCTL control) {
		//System.err.printf("BorrowUI : initialise\n");
		this.setVisible(true);
		this.control = control;
		
		btnSwipe.setEnabled(false);
		btnScan.setEnabled(false);
		btnContinue.setEnabled(false);
		btnComplete.setEnabled(false);
		btnCancel.setEnabled(false);
		borrowerIDTF.setEditable(false);
		bookIDTF.setEditable(false);

		lblBorrowerName.setText(""); 
		lblBorrowerContact.setText("");
		lblErrMesg.setText("");
		lblOverdue.setText("");
		lblFineLimit.setText("");
		lblLoanLimit.setText("");
		
		pendingLoanListTA.setEditable(false); 
		currentBookTA.setEditable(false); 
		existingLoanListTA.setEditable(false);

	}

	@Override
	public void setState(IBorrowCTL.State state) {		
		//System.err.printf("BorrowUI : setState : %s\n", state);

		switch(state) {
		
		case CREATED:
			break;
			
		case CANCELLED: 
			break;
		
		case STARTED:
			borrowerIDTF.setEditable(true);
			btnSwipe.setEnabled(true);
			btnCancel.setEnabled(true);			
			break;
		
		case DISALLOWED:
			btnSwipe.setEnabled(false);			
			break;
			
		case BORROWING:
			borrowerIDTF.setEditable(false);
			btnSwipe.setEnabled(false);
			currentBookTA.setText("");
			pendingLoanListTA.setText("");
			break;

		case COMPLETED:
			bookIDTF.setText("");
			bookIDTF.setEditable(false);
			btnContinue.setEnabled(false);
			btnComplete.setEnabled(false);
			break;

		case CONFIRMED:
			btnCancel.setEnabled(false);
			break;

		case ENDED:
			this.setVisible(false);
			this.dispose();
			break;			
		}		
	}

	
	@Override
	public void displayBorrowerDetails(IMember borrower) {
		//System.err.printf("BorrowUI : displayBorrowerDetails\n");
		String name = borrower.getFirstName() + " " + borrower.getLastName();
		String contact = borrower.getContactPhone();
		lblBorrowerName.setText(name);
		lblBorrowerContact.setText(contact);
		if (borrower.hasOverDueLoans()) {
			lblOverdue.setText("Borrower has overdue loans");
		}
		if (borrower.hasReachedFineLimit()){
			float amountOwing = borrower.getFineAmount();
			lblFineLimit.setText(String.format("Borrower has reached fine limit. Amount owing: $%.2f", amountOwing ));
		}
		if (borrower.hasReachedLoanLimit()){
			lblLoanLimit.setText("Borrower has reached maximum number of borrowed items");
		}
		StringBuilder bld = new StringBuilder();
		for (ILoan ln : borrower.getLoans()) {
			bld.append(ln).append("\n\n");
		}
		existingLoanListTA.setText(bld.toString());
		existingLoanListTA.setCaretPosition(0);
	}

	@Override
	public void scanBook() {
		//System.err.printf("BorrowUI : scanBook\n");
		bookIDTF.setEditable(true);
		bookIDTF.requestFocusInWindow();
		btnScan.setEnabled(true);
		btnContinue.setEnabled(false);
		btnComplete.setEnabled(false);
		
	}

	@Override
	public void displayBook(IBook book) {
		//System.err.printf("BorrowUI : displayBook\n");
		currentBookTA.setText(((library.classes.entities.Book)book).toString());
		currentBookTA.setCaretPosition(0);
		
	}

	@Override
	public void displayPendingList(List<ILoan> pendingList) {
		//System.err.printf("BorrowUI : displayPendingList\n");
		StringBuilder bld = new StringBuilder();
		for (ILoan ln : pendingList) {
			bld.append(ln).append("\n\n");
		}
		pendingLoanListTA.setText(bld.toString());
		pendingLoanListTA.setCaretPosition(0);
		
	}

	@Override
	public void displayCompletedList(List<ILoan> completedList) {
		//System.err.printf("BorrowUI : displayCompletedList\n");
		this.completedList = completedList;
		ConfirmCompleteDialog dialog = new ConfirmCompleteDialog(this, completedList);
		dialog.setVisible(true);		
	}

	@Override
	public void printLoanSlip() {
		//System.err.printf("BorrowUI : printLoanSlip\n");
		LoanSlipDialog dialog = new LoanSlipDialog(this, completedList);
		dialog.setVisible(true);
		control.borrowUCended();
		
	}
	
	private void onCancel() {
		//System.err.printf("BorrowUI : onCancel\n");
		control.cancel();
	}
	
	private void onSwipe() {
		//System.err.printf("BorrowUI : onSwipe\n");
		int borrowerID = 0;
		
		lblErrMesg.setText("");
		String borrowerIDstr  = borrowerIDTF.getText();
		if (borrowerIDstr.isEmpty()) {
			lblErrMesg.setText("Borrower ID cannot be empty");
		}
		else {
			try {
				borrowerID = Integer.valueOf(borrowerIDstr);
				if (borrowerID <= 0) throw new NumberFormatException();
			
				try {
					control.cardScanned(borrowerID);
				} catch (BorrowerNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			catch (NumberFormatException e) {
				lblErrMesg.setText(String.format("Borrower ID must be positive integer",e));
				borrowerIDTF.setText("");
			}
			catch (RuntimeException e) {
				lblErrMesg.setText(String.format("Error: %s",e.getMessage()));	
				//lblErrMesg.setText(String.format("Borrower ID %d: not found",borrowerID));				
				borrowerIDTF.setText("");
			}
		}
	}
	
	private void onScan() {
		//System.err.printf("BorrowUI : onScan\n");
		int bookID = 0;
		
		lblErrMesg.setText("");
		String bookIDstr  = bookIDTF.getText();
		if (bookIDstr.isEmpty()) {
			lblErrMesg.setText("Book ID cannot be empty");
		}
		else {
			try {
				bookID = Integer.valueOf(bookIDstr);
				if (bookID <= 0) throw new NumberFormatException();
			
				try {
					control.bookScanned(bookID);
				} catch (BookNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				btnScan.setEnabled(false);
				btnContinue.setEnabled(true);
				btnComplete.setEnabled(true);
				bookIDTF.setEditable(false);				
			}
			catch (NumberFormatException e) {
				lblErrMesg.setText(String.format("Book ID must be positive integer",e));
				borrowerIDTF.setText("");
			}
			catch (RuntimeException e) {
				lblErrMesg.setText(String.format("Error: %s",e.getMessage()));	
				//lblErrMesg.setText(String.format("Book ID %d: not found",borrowerID));				
				bookIDTF.setText("");
			}
		}
	}
	
	private void onContinue() {
		//System.err.printf("BorrowUI : onContinue\n");
		bookIDTF.setText("");
		control.scanNext();
	}
	
	private void onComplete() {
		//System.err.printf("BorrowUI : onComplete\n");
		control.scansCompleted();
	}
	
	protected void reject() {
		//System.err.printf("BorrowUI : reject\n");
		completedList = null;
		control.rejectPendingList();
	}
	
	protected void accept() {
		//System.err.printf("BorrowUI : accept\n");
		control.confirmPendingList();
	}

}
