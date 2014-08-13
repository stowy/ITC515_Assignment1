package library.classes.uis;

import java.util.List;

import library.interfaces.controls.IBorrowCTL;
import library.interfaces.controls.IBorrowCTL.State;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.uis.IBorrowUI;

public class BorrowUI implements IBorrowUI {

	public BorrowUI() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initialise(IBorrowCTL control) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setState(State state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayBorrowerDetails(IMember borrower) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scanBook() {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayBook(IBook book) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayPendingList(List<ILoan> pendingList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayCompletedList(List<ILoan> pendingList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void printLoanSlip() {
		// TODO Auto-generated method stub

	}

}
