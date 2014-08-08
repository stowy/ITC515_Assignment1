package library.tests.entities;

import static org.junit.Assert.*;
import library.classes.entities.Book;
import library.interfaces.entities.BookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;

import static org.easymock.EasyMock.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestBook {

	private String author = "author";
	private String title = "title";
	private String callNumber = "callNumber";
	private int bookId = 1;
			
	private Book book;
	
	@Before
	public void setUp() throws Exception {
		book = new Book(author, title, callNumber, bookId);
	}

	@After
	public void tearDown() throws Exception {
		book = null;
	}

	@Test
	public void testValidConstructor() {
		Book book = new Book(author, title, callNumber, bookId);
		assertNotNull(book);
		assertTrue(book instanceof IBook);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidConstructor() {
		Book book = new Book(author, title, null, bookId);
	}
	
	@Test
	public void testBorrowBookAvailable() {
		ILoan mockLoan = createMock(ILoan.class);
		book.borrow(mockLoan);
		BookState actualState = book.getState();
		assertEquals(BookState.ON_LOAN, actualState);
		ILoan actualLoan = book.getLoan();
		assertEquals(mockLoan, actualLoan);
	}
	
	@Test(expected=RuntimeException.class)
	public void testBorrowBookNotAvailable() {
		ILoan mockLoan = createMock(ILoan.class);
		book.borrow(mockLoan);
		ILoan newMockLoan = createMock(ILoan.class);
		book.borrow(newMockLoan);
	}
	
	@Test
	public void testGetLoanOnLoan() {
		ILoan mockLoan = createMock(ILoan.class);
		book.borrow(mockLoan);
		ILoan actualLoan = book.getLoan();
		assertEquals(mockLoan, actualLoan);
	}
	
	@Test
	public void testGetLoanNotOnLoan() {
		ILoan actual = book.getLoan();
		assertEquals(null, actual);
	}
	
	@Test(expected=RuntimeException.class)
	public void testReturnBookNotOnLoan() {
		book.returnBook(false);
	}
	
	@Test
	public void testReturnBookDamaged() {
		ILoan loan = createMock(ILoan.class);
		book.borrow(loan);
		book.returnBook(true);
		BookState actual = book.getState();
		assertEquals(BookState.DAMAGED, actual);
	}
	
	@Test
	public void testReturnBookNotDamaged() {
		ILoan loan = createMock(ILoan.class);
		book.borrow(loan);
		book.returnBook(false);
		BookState actualState = book.getState();
		assertEquals(BookState.AVAILABLE, actualState);
		ILoan actualLoan = book.getLoan();
		assertEquals(null, actualLoan);
	}
	
	@Test(expected=RuntimeException.class)
	public void testLoseNotOnLoan() {
		book.lose();
	}
	
	@Test
	public void testLoseOnLoan() {
		ILoan loan = createMock(ILoan.class);
		book.borrow(loan);
		book.lose();
		BookState actual = book.getState();
		assertEquals(BookState.LOST, actual);
	}
	
	@Test
	public void testRepairBookDamaged() {
		ILoan mockLoan = createMock(ILoan.class);
 		book.borrow(mockLoan);
 		book.returnBook(true);
 		BookState actualState = book.getState();
 		assertEquals(BookState.DAMAGED, actualState);
 		
 		book.repair();
 		actualState = book.getState();
 		assertEquals(BookState.AVAILABLE, actualState);
	}
	
	@Test(expected=RuntimeException.class)
	public void testRepairBookNotDamaged() {
		book.repair();
	}
	
	@Test
	public void testDispose() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetState() {
		BookState actual = book.getState();
		assertEquals(BookState.AVAILABLE, actual);
	}
	
	@Test
	public void testGetAuthor() {
		String actual = book.getAuthor();
		assertEquals(author, actual);
	}
	
	@Test
	public void testGetTitle() {
		String actual = book.getTitle();
		assertEquals(title, actual);
	}
	
	@Test
	public void testGetCallNumber() {
		String actual = book.getCallNumber();
		assertEquals(callNumber, actual);
	}
	
	@Test
	public void testGetID() {
		int actual = book.getID();
		assertEquals(bookId, actual);
	}

}
