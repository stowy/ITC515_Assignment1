package library.tests.integration.phase1;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import library.classes.entities.Book;
import library.classes.entities.Loan;
import library.interfaces.entities.BookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestBook_Loan_p1_1 {

	private String author = "author";
	private String title = "title";
	private String callNumber = "callNumber";
	private int bookId = 1;
			
	private Book book;
	private ILoan loan;
	IMember mockMember = createMock(IMember.class);
	int loanId = 1;
	Date borrowDate;
	Date dueDate;
	
	
	@Before
	public void setUp() throws Exception {
		book = new Book(author, title, callNumber, bookId);
		Calendar calendar = Calendar.getInstance();
		borrowDate = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, ILoan.LOAN_PERIOD);
		dueDate = calendar.getTime();
		loan = new Loan(book, mockMember, borrowDate, dueDate, loanId);
	}

	@After
	public void tearDown() throws Exception {
		book = null;
		loan = null;
		borrowDate = null;
		dueDate = null;
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
		ILoan newLoan = new Loan(book, mockMember, borrowDate, dueDate, loanId+1);
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
	public void testGetID() {
		int actual = loan.getID();
		assertEquals(bookId, actual);
	}
}
