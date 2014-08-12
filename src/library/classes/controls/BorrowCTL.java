package library.classes.controls;

import library.interfaces.controls.IBorrowCTL;

public class BorrowCTL implements IBorrowCTL {

	//Updates state to Cancelled
	//Ends session, calls BorrowUCended
	@Override
	public void cancel() {
		// TODO Auto-generated method stub

	}

	//State = STARTED
	//Gets Member using MemberID from MemberDAO
	//Updates state based on member state
	//Updates state to IBorrowUI
	//Displays borrower details using IBorrowUI
	@Override
	public void cardScanned(int memberID) {
		// TODO Auto-generated method stub

	}

	//State = BORROWING
	//Checks if loan limit reached (LoanDAO)
	//Retrieves book using book ID (BookDAO)
	//Shows book details on IBorrowUI 
	//Creates pending loan list if necessary (LoanDAO)
	//Creates pending loan and adds to pending list (LoanDAO)
	//Shows pending loan list (IBorrowUI)
	@Override
	public void bookScanned(int bookID) {
		// TODO Auto-generated method stub

	}

	//State = BORROWING
	//Gets next book ID 
	//Calls bookScanned
	@Override
	public void scanNext() {
		// TODO Auto-generated method stub

	}

	//Updates state to completed
	//Shows completed pending list on IBorrowUI to reject or commit (need prompt)
	@Override
	public void scansCompleted() {
		// TODO Auto-generated method stub

	}

	//State = BORROWING
	//Clears pending list (LoanDAO)
	//Cancels or restarts scanning (need prompt)
	@Override
	public void rejectPendingList() {
		// TODO Auto-generated method stub

	}

	//Updates state to Confirmed
	//Commit pending list using loanDAO 
	//Prints borrowing slip using IBorrowUI 
	@Override
	public void confirmPendingList() {
		// TODO Auto-generated method stub

	}

	//Updates state to Ended
	//Show end, terminate (IBorrowUI)
	@Override
	public void borrowUCended() {
		// TODO Auto-generated method stub

	}

}
