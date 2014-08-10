package library.tests.daos;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.List;

import library.classes.daos.BookDAO;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestBookDAO {
	
	private String author = "author";
	private String title = "title";
	private String callNo = "callNo";
	
	private BookDAO bookDAO;
	private IBookHelper bookHelper;

	@Before
	public void setUp() throws Exception {
		bookHelper = createMock(IBookHelper.class);
		bookDAO = new BookDAO(bookHelper);
	}

	@After
	public void tearDown() throws Exception {
		bookDAO = null;
		bookHelper = null;
	}

	@Test
	public void testValidConstructor() {
		IBookHelper helper = createMock(IBookHelper.class);
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
		IBook mockBook = createMock(IBook.class);
		expect(bookHelper.makeBook(EasyMock.anyString(), EasyMock.anyString(), EasyMock.anyString(), EasyMock.anyInt())).andReturn(mockBook);
		replay(bookHelper);
		
		bookDAO.addBook(author, title, callNo);
		verify(bookHelper);
		
		List<IBook> books = bookDAO.listBooks();
		assertNotNull(books);
		assertTrue(books.contains(mockBook));
	}

	@Test
	public void testGetBookByID() {
		fail("Not yet implemented");
	}

	@Test
	public void testListBooks() {
		List<IBook> books = bookDAO.listBooks();
		assertNotNull(books);
		assertTrue(books.size() == 0);
	}

	@Test
	public void testFindBooksByAuthor() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindBooksByTitle() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindBooksByAuthorTitle() {
		fail("Not yet implemented");
	}

}
