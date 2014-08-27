package library.tests.unit.daos;

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
	public void testGetBookByIDFoundBook() {
		int id = 1;
		IBook mockBook = createMock(IBook.class);
		expect(bookHelper.makeBook(EasyMock.anyString(), EasyMock.anyString(), EasyMock.anyString(), EasyMock.anyInt())).andReturn(mockBook);
		expect(mockBook.getID()).andReturn(id);
		replay(bookHelper);
		replay(mockBook);
		
		bookDAO.addBook(author, title, callNo);
		IBook actual = bookDAO.getBookByID(id);
		verify(bookHelper);
		verify(mockBook);
		
		assertEquals(mockBook, actual);
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
		IBook mockBook = createMock(IBook.class);
		expect(bookHelper.makeBook(EasyMock.anyString(), EasyMock.anyString(), EasyMock.anyString(), EasyMock.anyInt())).andReturn(mockBook);
		expect(mockBook.getAuthor()).andReturn(author);
		replay(bookHelper);
		replay(mockBook);
		
		bookDAO.addBook(author, title, callNo);
		List<IBook> actual = bookDAO.findBooksByAuthor(author);
		
		verify(bookHelper);
		verify(mockBook);
		assertNotNull(actual);
		assertTrue(actual.contains(mockBook));
		assertTrue(actual.size() == 1);
	}

	@Test
	public void testFindBooksByTitle() {
		IBook mockBook = createMock(IBook.class);
		expect(bookHelper.makeBook(EasyMock.anyString(), EasyMock.anyString(), EasyMock.anyString(), EasyMock.anyInt())).andReturn(mockBook);
		expect(mockBook.getTitle()).andReturn(title);
		replay(bookHelper);
		replay(mockBook);
		
		bookDAO.addBook(author, title, callNo);
		List<IBook> actual = bookDAO.findBooksByTitle(title);
		
		verify(bookHelper);
		verify(mockBook);
		assertNotNull(actual);
		assertTrue(actual.contains(mockBook));
		assertTrue(actual.size() == 1);
	}

	@Test
	public void testFindBooksByAuthorTitle() {
		IBook mockBook = createMock(IBook.class);
		expect(bookHelper.makeBook(EasyMock.anyString(), EasyMock.anyString(), EasyMock.anyString(), EasyMock.anyInt())).andReturn(mockBook);
		expect(mockBook.getTitle()).andReturn(title);
		expect(mockBook.getAuthor()).andReturn(author);
		replay(bookHelper);
		replay(mockBook);
		
		bookDAO.addBook(author, title, callNo);
		List<IBook> actual = bookDAO.findBooksByAuthorTitle(author, title);
		
		verify(bookHelper);
		verify(mockBook);
		assertNotNull(actual);
		assertTrue(actual.contains(mockBook));
		assertTrue(actual.size() == 1);
	}

}
