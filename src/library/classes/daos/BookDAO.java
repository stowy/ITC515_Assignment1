package library.classes.daos;

import static library.classes.utils.VerificationUtil.*;

import java.util.ArrayList;
import java.util.List;

import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;

public class BookDAO implements IBookDAO {
	
	private IBookHelper bookHelper;
	private int nextID = 1;
	private List<IBook> books;
	
	public BookDAO(IBookHelper bookHelper) throws IllegalArgumentException {
		assertNotNull(bookHelper);
		this.bookHelper = bookHelper;
		this.books = new ArrayList<IBook>();
	}
	
	
	@Override
	public IBook addBook(String author, String title, String callNo) throws IllegalArgumentException {
		IBook book = bookHelper.makeBook(author, title, callNo, nextID);
		books.add(book);
		nextID++;
		return book;
	}

	@Override
	public IBook getBookByID(int id) {
		IBook foundBook = null;
		for (IBook book : this.books) {
			if (book.getID() == id) {
				foundBook = book;
			}
		}
		return foundBook;
	}

	@Override
	public List<IBook> listBooks() {
		return this.books;
	}

	@Override
	public List<IBook> findBooksByAuthor(String author) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IBook> findBooksByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IBook> findBooksByAuthorTitle(String author, String title) {
		// TODO Auto-generated method stub
		return null;
	}

}
