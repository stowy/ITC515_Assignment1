package library.tests.integration.phase2;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import library.classes.daos.BookHelper;
import library.classes.daos.LoanDAO;
import library.classes.daos.LoanHelper;
import library.classes.daos.MemberHelper;
import library.interfaces.daos.IBookHelper;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLoan_LoanDAO_LoanHelper_p2_6 {

	private ILoanHelper loanHelper;
	private ILoanDAO loanDAO;
	
	private String author = "author";
	private String title = "title";
	private String callNumber = "callNumber";
	
	String first = "first";
	String last = "last";
	String phone = "phone";
	String email = "email";
	
	int bookId = 1;
	int memberId = 1;

	IBook book;
	IMember member;
	
	IBookHelper bookHelper = new BookHelper();
	IMemberHelper memberHelper = new MemberHelper();
	
	Date borrowDate;
	Date dueDate;
	
	@Before
	public void setUp() throws Exception {
		loanHelper = new LoanHelper();
		loanDAO = new LoanDAO(loanHelper);
		
		Calendar calendar = Calendar.getInstance();
		borrowDate = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, ILoan.LOAN_PERIOD);
		dueDate = calendar.getTime();
		
		book = bookHelper.makeBook(author, title, callNumber, bookId);
		member = memberHelper.makeMember(first, last, phone, email, memberId);
		
	}

	@After
	public void tearDown() throws Exception {
		loanHelper = null;
		loanDAO = null;
		
		borrowDate = null;
		dueDate = null;
		book = null;
		member = null;
	}

	@Test
	public void testValidConstructor() {
		ILoanHelper helper = new LoanHelper();
		ILoanDAO testLoanDAO = new LoanDAO(helper);
		assertNotNull(testLoanDAO);
		assertTrue(testLoanDAO instanceof ILoanDAO);
	}
	
	@Test
	public void testCreateNewPendingList() {
		loanDAO.createNewPendingList(member);
		List<ILoan> loans = loanDAO.getPendingList(member);
		assertNotNull(loans);
		assertTrue(loans.size() == 0);
	}

	@Test
	public void testCreatePendingLoan() {
		loanDAO.createNewPendingList(member);
		ILoan loan = loanDAO.createPendingLoan(member, book, borrowDate, dueDate);
		List<ILoan> actual = loanDAO.getPendingList(member);
		assertNotNull(actual);
		assertTrue(actual.contains(loan));
	}
	
	@Test(expected=RuntimeException.class)
	public void testCreatePendingLoanException() {
		loanDAO.createPendingLoan(member, book, borrowDate, dueDate);
	}
	
	@Test(expected=RuntimeException.class)
	public void testCreatePendingLoanForBookTwice() {
		loanDAO.createNewPendingList(member);
		loanDAO.createPendingLoan(member, book, borrowDate, dueDate);
		loanDAO.createPendingLoan(member, book, borrowDate, dueDate);
	}
	
	@Test
	public void testGetPendingList() {
		loanDAO.createNewPendingList(member);
		List<ILoan> loans = loanDAO.getPendingList(member);
		assertNotNull(loans);
		assertTrue(loans.size() == 0);
	}
	
	@Test(expected=RuntimeException.class)
	public void testGetPendingListException() {
		loanDAO.getPendingList(member);
	}
	
	@Test
	public void testCommitPendingLoans() {
		//Perform actions
		loanDAO.createNewPendingList(member);
		ILoan loan = loanDAO.createPendingLoan(member, book, borrowDate, dueDate);
		loanDAO.commitPendingLoans(member);
		
		//Check loans state
		List<ILoan> committedLoans = loanDAO.listLoans();
		assertTrue(committedLoans.contains(loan));
		
		//Maybe use reflection of loan state here
//		assertEquals(LoanState.CURRENT, loan.getState?);
		List<ILoan> memberLoans = member.getLoans();
		assertTrue(memberLoans.contains(loan));
	}
	
	@Test
	public void testClearPendingLoans() {
		//Perform actions
		loanDAO.createNewPendingList(member);
		ILoan loan = loanDAO.createPendingLoan(member, book, borrowDate, dueDate);
		
		List<ILoan> actual = loanDAO.getPendingList(member);
		assertEquals(1, actual.size());
		assertTrue(actual.contains(loan));
		
		loanDAO.clearPendingLoans(member);
		actual = loanDAO.getPendingList(member);
		assertEquals(0, actual.size());
		assertFalse(actual.contains(loan));
	}	
	
	@Test
	public void testGetLoanByID() {
		
		//Perform actions
		loanDAO.createNewPendingList(member);
		ILoan loan = loanDAO.createPendingLoan(member, book, borrowDate, dueDate);
		loanDAO.commitPendingLoans(member);

		ILoan actual = loanDAO.getLoanByID(loan.getID());
		assertEquals(loan, actual);
	}
	
	@Test
	public void testGetLoanByBook() {

		//Perform actions
		loanDAO.createNewPendingList(member);
		ILoan loan = loanDAO.createPendingLoan(member, book, borrowDate, dueDate);
		loanDAO.commitPendingLoans(member);
		
		ILoan actual = loanDAO.getLoanByBook(book);
		assertEquals(loan, actual);
	}
	
	@Test
	public void testListLoans() {
		List<ILoan> actual = loanDAO.listLoans();
		assertNotNull(actual);
		assertEquals(0, actual.size());
		
		//Perform actions
		loanDAO.createNewPendingList(member);
		ILoan loan = loanDAO.createPendingLoan(member, book, borrowDate, dueDate);
		loanDAO.commitPendingLoans(member);

		actual = loanDAO.listLoans();
		assertTrue(actual.contains(loan));
		assertEquals(1, actual.size());
	}
	
	@Test
	public void testFindLoansByBorrower() {

		//Perform actions
		loanDAO.createNewPendingList(member);
		ILoan loan = loanDAO.createPendingLoan(member, book, borrowDate, dueDate);
		loanDAO.commitPendingLoans(member);

		List<ILoan> actual = loanDAO.findLoansByBorrower(member);
		assertNotNull(actual);
		assertEquals(1, actual.size());
		assertTrue(actual.contains(loan));
	}

	@Test
	public void testFindLoansByBookTitle() {

		//Perform actions
		loanDAO.createNewPendingList(member);
		ILoan loan = loanDAO.createPendingLoan(member, book, borrowDate, dueDate);
		loanDAO.commitPendingLoans(member);
			
		List<ILoan> actual = loanDAO.findLoansByBookTitle(title);
		
		assertNotNull(actual);
		assertEquals(1, actual.size());
		assertTrue(actual.contains(loan));
	}
	
	@Test
	public void testUpdateOverDueStatus() {

		//Perform actions
		loanDAO.createNewPendingList(member);
		ILoan loan = loanDAO.createPendingLoan(member, book, borrowDate, dueDate);
		loanDAO.commitPendingLoans(member);
		
		Calendar calender = Calendar.getInstance();
		calender.setTime(dueDate);
		calender.add(Calendar.DAY_OF_YEAR, 1);
		
		loanDAO.updateOverDueStatus(calender.getTime());
		assertTrue(loan.isOverDue());
		assertTrue(member.hasOverDueLoans());
		
		List<ILoan> loans = loanDAO.findOverDueLoans();
		assertTrue(loans.contains(loan));
	}

	@Test
	public void testFindOverDueLoans() {
		
		//Perform actions
		loanDAO.createNewPendingList(member);
		ILoan loan = loanDAO.createPendingLoan(member, book, borrowDate, dueDate);
		loanDAO.commitPendingLoans(member);
		
		Calendar calender = Calendar.getInstance();
		calender.setTime(dueDate);
		calender.add(Calendar.DAY_OF_YEAR, 1);
		loan.checkOverDue(calender.getTime());
		assertTrue(loan.isOverDue());
		
		List<ILoan> actual = loanDAO.findOverDueLoans();

		assertNotNull(actual);
		assertEquals(1, actual.size());
		assertTrue(actual.contains(loan));
	}

}
