package library.interfaces.controls;

import library.interfaces.entities.IMember;

public interface IBorrowCTL {
	
	public static enum State { STARTED, BORROWING, DISALLOWED, COMPLETED, CONFIRMED, CANCELLED, ENDED };
	
	public void cancel();
	
	public IMember cardScanned(int memberID);
	
	public void bookScanned(int bookID);
	
	public void scanNext();
	
	public void scansCompleted();
	
	public void rejectPendingList();
	
	public void confirmPendingList();
	
	public void borrowUCended();
	

}
