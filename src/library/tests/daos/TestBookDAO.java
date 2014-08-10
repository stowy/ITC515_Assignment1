package library.tests.daos;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.List;

import library.classes.daos.BookDAO;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestBookDAO {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidConstructor() {
		IBookHelper helper = createMock(IBookHelper.class);
		BookDAO bookDAO = new BookDAO(helper);
		assertNotNull(bookDAO);
		assertTrue(bookDAO instanceof IBookDAO);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidConstructor() {
		new BookDAO(null);
	}
	
	@Test
	public void testAddBook() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBookByID() {
		fail("Not yet implemented");
	}

	@Test
	public void testListBooks() {
		fail("Not yet implemented");
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
