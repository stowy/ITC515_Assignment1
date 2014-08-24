package library.interfaces.controls;

import library.classes.exceptions.BookNotFoundException;
import library.classes.exceptions.BorrowerNotFoundException;

public interface IBorrowCTL {
	
	public static enum State { CREATED, STARTED, BORROWING, DISALLOWED, COMPLETED, CONFIRMED, CANCELLED, ENDED };
	
	public void cancel();
	
	public void cardScanned(int memberID) throws BorrowerNotFoundException;
	
	public void bookScanned(int bookID) throws BookNotFoundException;
	
	public void scanNext();
	
	public void scansCompleted();
	
	public void rejectPendingList();
	
	public void confirmPendingList();
	
	public void borrowUCended();
	

}
