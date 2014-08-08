package library.tests.entities;

import static org.junit.Assert.*;
import library.classes.entities.Book;
import library.interfaces.entities.BookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;

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
	public void testBorrow() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetLoan() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testReturnBook() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testLose() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testRepair() {
		fail("Not yet implemented");
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
	public void testgetID() {
		int actual = book.getID();
		assertEquals(bookId, actual);
	}

}
