package library.classes.daos;

import library.classes.entities.Book;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;

public class BookHelper implements IBookHelper {

	@Override
	public IBook makeBook(String author, String title, String callNumber, int id) throws IllegalArgumentException {
		return new Book(author, title, callNumber, id);
	}

}
