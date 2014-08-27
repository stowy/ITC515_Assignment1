package library.tests.integration.phase5;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import library.classes.controls.BorrowCTL;
import library.classes.daos.BookDAO;
import library.classes.daos.BookHelper;
import library.classes.daos.LoanDAO;
import library.classes.daos.LoanHelper;
import library.classes.daos.MemberDAO;
import library.classes.daos.MemberHelper;
import library.classes.entities.Book;
//import library.classes.exceptions.BorrowerNotFoundException;
import library.classes.uis.BorrowGuiUI;
import library.interfaces.controls.IBorrowCTL;
//import library.interfaces.controls.IBorrowCTL.State;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings(value = { "unused" })
public class TestBorrowGuiUI_BorrowCTL {

	//Set up internal reflect fields.
//	Need references to:
//	(JButton)
	private JButton btnSwipe;
	private Field uiBtnSwipeField;
	private JButton btnScan;
	private Field uiBtnScanField;
	private JButton btnContinue;
	private Field uiBtnContinueField;
	private JButton btnComplete;
	private Field uiBtnCompleteField;
	private JButton btnCancel;
	private Field uiBtnCancelField;
	
//	(JTextField)
//	borrowerIDTF
	private Field uiTFborrowerIdField;
	private JTextField tFBorrowerId;
	private Field uiTFbookIdField;
	private JTextField tFBookId;
//
//	(JLabel)
	private JLabel lblBorrowerName; 
	private Field uiLblBorrowerNameField;
	private JLabel lblBorrowerContact;
	private Field uiLblBorrowerContactField;
	private JLabel lblErrMesg;
	private Field uiLblErrMesgField;
	private JLabel lblOverdue;
	private Field uiLblOverdueField;
	private JLabel lblFineLimit;
	private Field uiLblFineLimitField;
	private JLabel lblLoanLimit;
	private Field uiLblLoanLimitField;
//	
//	(JTextArea)
	private JTextArea pendingLoanListTA; 
	private Field uiTAPendingLoansField;
	private JTextArea currentBookTA; 
	private Field uiTACurrentBookField;
	private JTextArea existingLoanListTA;
	private Field uiTACurrentLoansField;
	
	private Field uiCompletedListField;
	private List<ILoan> completedList;
	
	private Field uiControlField;
	private IBorrowCTL uiControl;
	
//	(Methods)
//	onSwipe
	private Method uiOnSwipeMethod;
//	onScan
	private Method uiOnScanMethod;
//	onContinue
	private Method uiOnContinueMethod;
//	onComplete
	private Method uiOnCompleteMethod;
//	reject
	private Method uiRejectMethod;
//	accept
	private Method uiAcceptMethod;
	
	private BorrowGuiUI borrowUI;
	
	private IMemberDAO memberDao;
	private IBookDAO bookDao;
	private ILoanDAO loanDao;
	private IBorrowCTL borrowControl;
	
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		
		borrowUI = new BorrowGuiUI();
//		(JButton)
//		btnSwipe
		uiBtnSwipeField = BorrowGuiUI.class.getDeclaredField("btnSwipe");
		uiBtnSwipeField.setAccessible(true);
//		btnScan
		uiBtnScanField = BorrowGuiUI.class.getDeclaredField("btnScan");
		uiBtnScanField.setAccessible(true);
//		btnContinue
		uiBtnContinueField = BorrowGuiUI.class.getDeclaredField("btnContinue");
		uiBtnContinueField.setAccessible(true);
//		btnComplete
		uiBtnCompleteField = BorrowGuiUI.class.getDeclaredField("btnComplete");
		uiBtnCompleteField.setAccessible(true);
//		btnCancel
		uiBtnCancelField = BorrowGuiUI.class.getDeclaredField("btnCancel");
		uiBtnCancelField.setAccessible(true);
		
		
//		(JTextField)
//		borrowerIDTF
		uiTFborrowerIdField = BorrowGuiUI.class.getDeclaredField("borrowerIDTF");
		uiTFborrowerIdField.setAccessible(true);
//		bookIDTF
		uiTFbookIdField = BorrowGuiUI.class.getDeclaredField("bookIDTF");
		uiTFbookIdField.setAccessible(true);
		
	//
//		(JLabel)
//		lblBorrowerName 
		uiLblBorrowerNameField = BorrowGuiUI.class.getDeclaredField("lblBorrowerName");
		uiLblBorrowerNameField.setAccessible(true);
		lblBorrowerName = (JLabel) uiLblBorrowerNameField.get(borrowUI);
//		lblBorrowerContact
		uiLblBorrowerContactField = BorrowGuiUI.class.getDeclaredField("lblBorrowerContact");
		uiLblBorrowerContactField.setAccessible(true);
		lblBorrowerContact = (JLabel) uiLblBorrowerContactField.get(borrowUI);
//		lblErrMesg
		uiLblErrMesgField = BorrowGuiUI.class.getDeclaredField("lblErrMesg");
		uiLblErrMesgField.setAccessible(true);
		lblErrMesg = (JLabel) uiLblErrMesgField.get(borrowUI);
//		lblOverdue
		uiLblOverdueField = BorrowGuiUI.class.getDeclaredField("lblOverdue");
		uiLblOverdueField.setAccessible(true);
		lblOverdue = (JLabel) uiLblOverdueField.get(borrowUI);
//		lblFineLimit
		uiLblFineLimitField = BorrowGuiUI.class.getDeclaredField("lblFineLimit");
		uiLblFineLimitField.setAccessible(true);
		lblFineLimit = (JLabel) uiLblFineLimitField.get(borrowUI);
//		lblLoanLimit
		uiLblLoanLimitField = BorrowGuiUI.class.getDeclaredField("lblLoanLimit");
		uiLblLoanLimitField.setAccessible(true);
		lblLoanLimit = (JLabel) uiLblLoanLimitField.get(borrowUI);
	//	
//		(JTextArea)
//		pendingLoanListTA 
		uiTAPendingLoansField = BorrowGuiUI.class.getDeclaredField("pendingLoanListTA");
		uiTAPendingLoansField.setAccessible(true);
		pendingLoanListTA = (JTextArea) uiTAPendingLoansField.get(borrowUI);
//		currentBookTA 
		uiTACurrentBookField = BorrowGuiUI.class.getDeclaredField("currentBookTA");
		uiTACurrentBookField.setAccessible(true);
		currentBookTA = (JTextArea) uiTACurrentBookField.get(borrowUI);
		
//		existingLoanListTA
		uiTACurrentLoansField = BorrowGuiUI.class.getDeclaredField("existingLoanListTA");
		uiTACurrentLoansField.setAccessible(true);
		existingLoanListTA = (JTextArea) uiTACurrentLoansField.get(borrowUI);
		
		uiCompletedListField = BorrowGuiUI.class.getDeclaredField("completedList");
		uiCompletedListField.setAccessible(true);
		completedList = (List<ILoan>) uiCompletedListField.get(borrowUI);
		
		uiControlField = BorrowGuiUI.class.getDeclaredField("control");
		uiControlField.setAccessible(true);
		
//		(Methods)
//		onSwipe
		uiOnSwipeMethod = BorrowGuiUI.class.getDeclaredMethod("onSwipe");
		uiOnSwipeMethod.setAccessible(true);
//		onScan
		uiOnScanMethod = BorrowGuiUI.class.getDeclaredMethod("onScan");
		uiOnScanMethod.setAccessible(true);
//		onContinue
		uiOnContinueMethod= BorrowGuiUI.class.getDeclaredMethod("onContinue");
		uiOnContinueMethod.setAccessible(true);
//		onComplete
		uiOnCompleteMethod = BorrowGuiUI.class.getDeclaredMethod("onComplete");
		uiOnContinueMethod.setAccessible(true);
//		reject
		uiRejectMethod = BorrowGuiUI.class.getDeclaredMethod("reject");
		uiRejectMethod.setAccessible(true);
//		accept
		uiAcceptMethod= BorrowGuiUI.class.getDeclaredMethod("accept");
		uiAcceptMethod.setAccessible(true);
		
		memberDao = new MemberDAO(new MemberHelper());
		bookDao = new BookDAO(new BookHelper());
		loanDao = new LoanDAO(new LoanHelper());	
		
	}

	@After
	public void tearDown() throws Exception {
		borrowUI = null;
//		(JButton)
//		btnSwipe
		uiBtnSwipeField = null;
		btnSwipe = null;
//		btnScan
		uiBtnScanField = null;
		btnScan = null;
//		btnContinue
		uiBtnContinueField = null;
		btnContinue = null;
//		btnComplete
		uiBtnCompleteField = null;
		btnComplete = null;
//		btnCancel
		uiBtnCancelField = null;
		btnCancel = null;
		
//		(JTextField)
//		borrowerIDTF
		uiTFborrowerIdField = null;
//		bookIDTF
		uiTFbookIdField = null;
	//
//		(JLabel)
//		lblBorrowerName 
		uiLblBorrowerNameField = null;
		lblBorrowerName = null;
//		lblBorrowerContact
		uiLblBorrowerContactField = null;
		lblBorrowerContact = null;
//		lblErrMesg
		uiLblErrMesgField = null;
		lblErrMesg = null;
//		lblOverdue
		uiLblOverdueField = null;
		lblOverdue = null;
//		lblFineLimit
		uiLblFineLimitField = null;
		lblFineLimit = null;
//		lblLoanLimit
		uiLblLoanLimitField = null;
		lblLoanLimit = null;
	//	
//		(JTextArea)
//		pendingLoanListTA 
		uiTAPendingLoansField = null;
		pendingLoanListTA = null;
//		currentBookTA 
		uiTACurrentBookField = null;
		currentBookTA = null;
		
//		existingLoanListTA
		uiTACurrentLoansField = null;
		existingLoanListTA = null;
		
		uiCompletedListField = null;
		completedList = null;
		
		uiControlField = null;
		uiControl = null;
		
//		(Methods)
//		onSwipe
		uiOnSwipeMethod = null;
//		onScan
		uiOnScanMethod = null;
//		onContinue
		uiOnContinueMethod= null;
//		onComplete
		uiOnCompleteMethod = null;
//		reject
		uiRejectMethod = null;
//		accept
		uiAcceptMethod= null;
	}

	
	
//	Pre-conditions: not yet assigned control, not yet visible
//	Post-conditions: has reference to control, is visible
	@Test
	public void testInitialise() throws IllegalArgumentException, IllegalAccessException {
		assertFalse(borrowUI.isVisible());
		assertNull(uiControl);
		
		borrowControl = new BorrowCTL(memberDao, bookDao, loanDao, borrowUI);
		
		uiControl = (IBorrowCTL) uiControlField.get(borrowUI);
		btnSwipe = (JButton) uiBtnSwipeField.get(borrowUI);
		btnScan = (JButton) uiBtnScanField.get(borrowUI);
		btnContinue = (JButton) uiBtnContinueField.get(borrowUI);
		btnComplete = (JButton) uiBtnCompleteField.get(borrowUI);
		btnCancel = (JButton) uiBtnCancelField.get(borrowUI);
		
		tFBookId = (JTextField) uiTFbookIdField.get(borrowUI);
		tFBorrowerId = (JTextField) uiTFborrowerIdField.get(borrowUI);
		
		assertNotNull(uiControl);
		assertEquals(borrowControl, uiControl);
		
		assertTrue(btnSwipe.isEnabled());
		assertTrue(btnCancel.isEnabled());
		assertFalse(btnContinue.isEnabled());
		assertFalse(btnScan.isEnabled());
		assertFalse(btnComplete.isEnabled());
		
		assertFalse(tFBookId.isEditable());
		assertTrue(tFBorrowerId.isEditable());
	}

	@Test
	public void testDisplayBorrowerDetails() throws IllegalArgumentException, IllegalAccessException {
		borrowControl = new BorrowCTL(memberDao, bookDao, loanDao, borrowUI);
		
		tFBorrowerId = (JTextField) uiTFborrowerIdField.get(borrowUI);
		
		tFBorrowerId.setText("1");

		String firstName = "Sam";
		String lastName = "Stow";
		String phone = "000";
		String email = "sam@mail.com";
		
		IMember member = memberDao.addMember(firstName, lastName, phone, email);
		
		try {
			uiOnSwipeMethod.invoke(borrowUI);
		} catch (InvocationTargetException e) {
			fail();
		}

		btnSwipe = (JButton) uiBtnSwipeField.get(borrowUI);
		btnScan = (JButton) uiBtnScanField.get(borrowUI);
		btnContinue = (JButton) uiBtnContinueField.get(borrowUI);
		btnComplete = (JButton) uiBtnCompleteField.get(borrowUI);
		btnCancel = (JButton) uiBtnCancelField.get(borrowUI);
		
		tFBookId = (JTextField) uiTFbookIdField.get(borrowUI);
		
		lblBorrowerName = (JLabel) uiLblBorrowerNameField.get(borrowUI);
		lblBorrowerContact = (JLabel) uiLblBorrowerContactField.get(borrowUI);
		
		assertTrue(lblBorrowerName.getText().contains(firstName));
		assertTrue(lblBorrowerName.getText().contains(lastName));
		assertTrue(lblBorrowerContact.getText().contains(phone));
		
		assertFalse(btnSwipe.isEnabled());
		assertTrue(btnCancel.isEnabled());
		assertFalse(btnContinue.isEnabled());
		assertTrue(btnScan.isEnabled());
		assertFalse(btnComplete.isEnabled());
		
		assertTrue(tFBookId.isEditable());
		assertFalse(tFBorrowerId.isEditable());
	}

	@Test
	public void testScanBook() throws IllegalArgumentException, IllegalAccessException {
		borrowControl = new BorrowCTL(memberDao, bookDao, loanDao, borrowUI);
		tFBorrowerId = (JTextField) uiTFborrowerIdField.get(borrowUI);
		
		tFBorrowerId.setText("1");

		String firstName = "Sam";
		String lastName = "Stow";
		String phone = "000";
		String email = "sam@mail.com";
		
		IMember member = memberDao.addMember(firstName, lastName, phone, email);
		
		try {
			uiOnSwipeMethod.invoke(borrowUI);
		} catch (InvocationTargetException e) {
			fail();
		}
		List<IBook> books = new ArrayList<IBook>();
		
		books.add(bookDao.addBook("Tim Smith", "Wonderland", "0001"));
		books.add(bookDao.addBook("Tim Smith", "Wonderland 2", "0002"));
		books.add(bookDao.addBook("Tim Smith", "Wonderland 3", "0003"));
		books.add(bookDao.addBook("Sian Whiley", "Love is Forever", "0004"));
		books.add(bookDao.addBook("Sian Whiley", "Love is Forever Again", "0005"));
		
		tFBookId = (JTextField) uiTFbookIdField.get(borrowUI);
		tFBookId.setText("1");
		try {
			uiOnScanMethod.invoke(borrowUI);
		} catch (InvocationTargetException e) {
			fail();
		}
		IBook actualBook = bookDao.getBookByID(1);
		
		currentBookTA = (JTextArea) uiTACurrentBookField.get(borrowUI);
		assertTrue(currentBookTA.getText().contains(actualBook.toString()));
		
		btnSwipe = (JButton) uiBtnSwipeField.get(borrowUI);
		btnScan = (JButton) uiBtnScanField.get(borrowUI);
		btnContinue = (JButton) uiBtnContinueField.get(borrowUI);
		btnComplete = (JButton) uiBtnCompleteField.get(borrowUI);
		btnCancel = (JButton) uiBtnCancelField.get(borrowUI);
		
		tFBorrowerId = (JTextField) uiTFborrowerIdField.get(borrowUI);
		tFBookId = (JTextField) uiTFbookIdField.get(borrowUI);
		
		assertFalse(btnSwipe.isEnabled());
		assertTrue(btnCancel.isEnabled());
		assertTrue(btnContinue.isEnabled());
		assertFalse(btnScan.isEnabled());
		assertTrue(btnComplete.isEnabled());
		
		assertFalse(tFBookId.isEditable());
		assertFalse(tFBorrowerId.isEditable());

	}

	@Test
	public void testDisplayBook() throws IllegalArgumentException, IllegalAccessException {
		
		//Easy mock cannot mock toString method. Using real book for test
		
//		Book mockBook = createMock(Book.class);
//		expect(mockBook.toString()).andReturn(bookDescription);
//		replay(mockBook);
		
		Book book = new Book("author", "title", "callNumber", 1);
		
		borrowUI.displayBook(book);
		
		currentBookTA = (JTextArea) uiTACurrentBookField.get(borrowUI);
		
//		verify(mockBook);
		
		assertTrue(currentBookTA.getText().contains(book.toString()));

	}



}
