package library.tests.daos;

import static org.junit.Assert.*;
import library.classes.daos.BookHelper;
import library.interfaces.entities.IBook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestBookHelper {

	private String author = "author";
	private String title = "title";
	private String callNumber = "callNumber";
	private int bookId = 1;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		BookHelper helper = new BookHelper();
		IBook book = helper.makeBook(author, title, callNumber, bookId);
		assertNotNull(book);
		assertTrue(book instanceof IBook);
	}

}
