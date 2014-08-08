package library.classes.entities;

import library.classes.utils.VerificationUtil;
import library.interfaces.entities.BookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;

public class Book implements IBook {

	private String author;
	private String title;
	private String callNumber;
	private int bookId;
	private BookState state;
	private ILoan loan;
	
	public Book(String author, String title, String callNumber, int bookId) throws IllegalArgumentException {
		VerificationUtil.assertNotNullOrEmpty(author);
		VerificationUtil.assertNotNullOrEmpty(title);
		VerificationUtil.assertNotNullOrEmpty(callNumber);
		VerificationUtil.assertNonZeroPositive(bookId);
		
		this.author = author;
		this.title = title;
		this.callNumber = callNumber;
		this.bookId = bookId;
		this.state = BookState.AVAILABLE;
	}
	
	@Override
	public void borrow(ILoan loan) throws RuntimeException {
		if (this.state != BookState.AVAILABLE) {
			throw new RuntimeException("Book not avaialable");
		}
		this.state = BookState.ON_LOAN;
		this.loan = loan;
	}

	@Override
	public ILoan getLoan() {
		return this.loan;
	}

	@Override
	public void returnBook(boolean damaged) {
		if (this.state != BookState.ON_LOAN) {
			throw new RuntimeException("Book not currently on loan");
		}
		this.state = damaged ? BookState.DAMAGED : BookState.AVAILABLE;
		this.loan = null;
	}

	@Override
	public void lose() throws RuntimeException {
		if (this.state != BookState.ON_LOAN) {
			throw new RuntimeException("Book not currently on loan");
		}
		this.state = BookState.LOST;
	}

	@Override
	public void repair() throws RuntimeException {
		if (this.state != BookState.DAMAGED) {
			throw new RuntimeException("Book not damaged");
		}
		this.state = BookState.AVAILABLE;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public BookState getState() {
		return this.state;
	}

	@Override
	public String getAuthor() {
		return this.author;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getCallNumber() {
		return this.callNumber;
	}

	@Override
	public int getID() {
		return this.bookId;
	}

}
