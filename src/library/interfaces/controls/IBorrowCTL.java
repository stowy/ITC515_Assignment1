package library.interfaces.controls;

public interface IBorrowCTL {
	
	public static enum State { STARTED, BORROWING, DISALLOWED, COMPLETED, CONFIRMED, CANCELLED, ENDED };
	
	public void cancel();
	
	public void cardScanned(int memberID);
	
	public void bookScanned(int bookID);
	
	public void scanNext();
	
	public void scansCompleted();
	
	public void rejectPendingList();
	
	public void confirmPendingList();
	
	public void borrowUCended();
	

}
