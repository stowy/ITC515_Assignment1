package library.tests.integration.phase2;

import static org.junit.Assert.*;

import java.util.List;

import library.classes.daos.BookDAO;
import library.classes.daos.BookHelper;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestBook_BookDAO_BookHelper_p2_4 {

	private String author = "author";
	private String title = "title";
	private String callNo = "callNo";
	
	private BookDAO bookDAO;
	private IBookHelper bookHelper;

	@Before
	public void setUp() throws Exception {
		bookHelper = new BookHelper();
		bookDAO = new BookDAO(bookHelper);
	}

	@After
	public void tearDown() throws Exception {
		bookDAO = null;
		bookHelper = null;
	}

	@Test
	public void testValidConstructor() {
		IBookHelper helper = new BookHelper();
		BookDAO testBookDAO = new BookDAO(helper);
		assertNotNull(testBookDAO);
		assertTrue(testBookDAO instanceof IBookDAO);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidConstructor() {
		new BookDAO(null);
	}
	
	@Test
	public void testAddBook() {
		IBook book = bookDAO.addBook(author, title, callNo);
		
		List<IBook> books = bookDAO.listBooks();
		assertNotNull(books);
		assertTrue(books.contains(book));
	}

	@Test
	public void testGetBookByIDFoundBook() {
			
		IBook book = bookDAO.addBook(author, title, callNo);
		IBook actual = bookDAO.getBookByID(book.getID());
		
		assertNotNull(actual);
		assertEquals(book, actual);
	}
	
	@Test
	public void testGetBookByIDBookNotFound() {
		int id = 1;
		IBook book = bookDAO.getBookByID(id);
		assertNull(book);
	}

	@Test
	public void testListBooks() {
		List<IBook> books = bookDAO.listBooks();
		assertNotNull(books);
		assertTrue(books.size() == 0);
	}

	@Test
	public void testFindBooksByAuthor() {
		
		IBook book = bookDAO.addBook(author, title, callNo);
		List<IBook> actual = bookDAO.findBooksByAuthor(author);
		
		assertNotNull(actual);
		assertTrue(actual.size() == 1);
		assertTrue(actual.contains(book));
	}

	@Test
	public void testFindBooksByTitle() {
		
		IBook book = bookDAO.addBook(author, title, callNo);
		List<IBook> actual = bookDAO.findBooksByTitle(title);
		
		assertNotNull(actual);
		assertTrue(actual.size() == 1);
		assertTrue(actual.contains(book));
	}

	@Test
	public void testFindBooksByAuthorTitle() {
		IBook book = bookDAO.addBook(author, title, callNo);
		List<IBook> actual = bookDAO.findBooksByAuthorTitle(author, title);
		
		assertNotNull(actual);
		assertTrue(actual.size() == 1);
		assertTrue(actual.contains(book));
	}


}
