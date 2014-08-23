package library.tests.integration.phase1;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import library.classes.entities.Book;
import library.classes.entities.Loan;
import library.classes.entities.Member;
import library.interfaces.entities.BookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.entities.MemberState;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLoan_Book_Member_p1_3 {

	private String author = "author";
	private String title = "title";
	private String callNumber = "callNumber";
	private int bookId = 1;
			
	private Book book;
	
	String first = "first";
	String last = "last";
	String phone = "phone";
	String email = "email";
	int id = 1;
	
	ILoan loan;
	int loanId = 1;
	Date borrowDate;
	Date dueDate;
	
	IMember member;
	
	@Before
	public void setUp() throws Exception {
		member = new Member(first, last, phone, email, id);
		Calendar calendar = Calendar.getInstance();
		borrowDate = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, ILoan.LOAN_PERIOD);
		dueDate = calendar.getTime();
		book = new Book(author, title, callNumber, bookId);
		loan = new Loan(book, member, borrowDate, dueDate, loanId);
	}

	@After
	public void tearDown() throws Exception {
		member = null;
		loan = null;
		borrowDate = null;
		dueDate = null;
		book = null;
	}
	
	@Test
	public void testHasOverDueLoans() {
		loan.commit();
		member.addLoan(loan);
		assertFalse(member.hasOverDueLoans());
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dueDate);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		loan.checkOverDue(calendar.getTime());
		
		assertTrue(loan.isOverDue());
		assertTrue(member.hasOverDueLoans());
	}
	
	@Test
	public void testHasReachedLoanLimit() {
		int newLoanId = loanId + 1;
		for (int i =0; i < Member.LOAN_LIMIT; i ++) {
			ILoan newLoan = new Loan(book, member, borrowDate, dueDate, newLoanId);
			member.addLoan(newLoan);
			newLoanId++;
		}
		assertTrue(member.getLoans().size() == Member.LOAN_LIMIT);
		assertTrue(member.hasReachedLoanLimit());
		assertTrue(member.getState() == MemberState.BORROWING_DISALLOWED);
	}
	
	@Test
	public void testAddLoan() {
		member.addLoan(loan);
		List<ILoan> loans = member.getLoans();
		assertNotNull(loans);
		assertTrue(loans.contains(loan));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddLoanBorrowingDisallowed() {
		member.addFine(Member.FINE_LIMIT + 1);
		assertTrue(member.getState() == MemberState.BORROWING_DISALLOWED);
		member.addLoan(loan);
	}

	@Test
	public void testRemoveLoan() {
		member.addLoan(loan);
		List<ILoan> loans = member.getLoans();
		assertNotNull(loans);
		assertTrue(loans.contains(loan));
		member.removeLoan(loan);
		assertFalse(member.getLoans().contains(loan));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveLoanNotPresent() {
		member.removeLoan(loan);
	}
	
	@Test
	public void testBorrowBookAvailable() {
		book.borrow(loan);
		BookState actualState = book.getState();
		assertEquals(BookState.ON_LOAN, actualState);
		ILoan actualLoan = book.getLoan();
		assertEquals(loan, actualLoan);
	}
	
	@Test(expected=RuntimeException.class)
	public void testBorrowBookNotAvailable() {
		book.borrow(loan);
		ILoan newLoan = new Loan(book, member, borrowDate, dueDate, loanId+1);
		book.borrow(newLoan);
	}
	
	@Test
	public void testGetLoanOnLoan() {
		book.borrow(loan);
		ILoan actualLoan = book.getLoan();
		assertEquals(loan, actualLoan);
	}
	
	@Test
	public void testReturnBookDamaged() {
		book.borrow(loan);
		book.returnBook(true);
		BookState actual = book.getState();
		assertEquals(BookState.DAMAGED, actual);
	}
	
	@Test
	public void testReturnBookNotDamaged() {
		book.borrow(loan);
		book.returnBook(false);
		BookState actualState = book.getState();
		assertEquals(BookState.AVAILABLE, actualState);
		ILoan actualLoan = book.getLoan();
		assertEquals(null, actualLoan);
	}
	
	@Test
	public void testLoseOnLoan() {
		book.borrow(loan);
		book.lose();
		BookState actual = book.getState();
		assertEquals(BookState.LOST, actual);
	}
	
	@Test
	public void testRepairBookDamaged() {
 		book.borrow(loan);
 		book.returnBook(true);
 		BookState actualState = book.getState();
 		assertEquals(BookState.DAMAGED, actualState);
 		
 		book.repair();
 		actualState = book.getState();
 		assertEquals(BookState.AVAILABLE, actualState);
	}
	
	@Test(expected=RuntimeException.class)
	public void testDisposeOnLoan() {
		book.borrow(loan);
		assertEquals(BookState.ON_LOAN, book.getState());
		book.dispose();
	}
	
	@Test
	public void testGetBook() {
		book.borrow(loan);
		IBook actual = loan.getBook();
		assertEquals(book, actual);
	}
	
	@Test
	public void testLoanGetID() {
		int actual = loan.getID();
		assertEquals(bookId, actual);
	}
	
	@Test
	public void testGetBorrower() {
		IMember actual = loan.getBorrower();
		assertEquals(member, actual);
	}
	
	@Test
	public void testLoanGetBook() {
		IBook actual = loan.getBook();
		assertEquals(book, actual);
	}

}
