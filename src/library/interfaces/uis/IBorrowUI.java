package library.interfaces.uis;

import java.util.List;

import library.interfaces.controls.IBorrowCTL;
import library.interfaces.entities.*;

public interface IBorrowUI {
	
	public void initialise(IBorrowCTL control);
	
	public void setState(IBorrowCTL.State state);
	
	public void displayBorrowerDetails(IMember borrower);
	
	public void scanBook();
	
	public void displayBook(IBook book);
	
	public void displayPendingList(List<ILoan> pendingList);
	
	public void displayCompletedList(List<ILoan> pendingList);
	
	public void printLoanSlip();

}
