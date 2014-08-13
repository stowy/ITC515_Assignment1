package library.classes.controls;

import static library.classes.utils.VerificationUtil.*;

import java.util.Calendar;
import java.util.Date;

import org.easymock.EasyMock;

import library.classes.exceptions.BookNotFoundException;
import library.classes.exceptions.BorrowerNotFoundException;
import library.interfaces.controls.IBorrowCTL;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.IMember;
import library.interfaces.uis.IBorrowUI;

public class BorrowCTL implements IBorrowCTL {
	private IMemberDAO memberDao;
	private IBookDAO bookDao;
	private ILoanDAO loanDao;
	private IBorrowUI borrowUI;
	private State state;
	private IMember member;
	
	
//	Pre: CTL does not exist
//	UI needs to be initialized
//	memberDAO exists loanDAO exists bookDAO exists
//	Post: CTL exists UI initialized
//	CTL.state = STARTED UI.state = STARTED 
//	Refs to DAOS stored.
	public BorrowCTL(IMemberDAO memberDao, IBookDAO bookDao, ILoanDAO loanDao, IBorrowUI borrowUI) throws IllegalArgumentException {
		assertNotNull(memberDao);
		assertNotNull(bookDao);
		assertNotNull(loanDao);
		assertNotNull(borrowUI);
		
		this.memberDao = memberDao;
		this.bookDao = bookDao;
		this.loanDao = loanDao;
		this.borrowUI = borrowUI;
		this.state = State.STARTED;
		
		borrowUI.initialise(this);
		borrowUI.setState(State.STARTED);
	}
	
//	Sig : cancel
//	CTL.state = ANY
//	UI.state = ANY Post: CTL.state = ENDED
//	UI.state = ENDED TempLoanList cleared/deleted
	@Override
	public void cancel() {
		// TODO Auto-generated method stub

	}

//	Sig: Borrower = cardScanned(borrowerID)
//			Throws borrowerNotFoundException
//			Pre: CTL.state = STARTED UI.state = STARTED
//			Post: CTL has ref to Member UI.displayBorrowerDetails called
//			overdue, atLoanLimit, overFineLimit set
//			UI.displayBorrowerDetails called.
//			Rules: if (overdue || atLoanLimit ||overFineLimit)
//			CTL.state = DISALLOWED UI.state = DISALLOWED
//			Else:
//			CTL.state = BORROWING
//			UI.state = BORROWING tempLoanList created. UI.scanBook called
	@Override
	public IMember cardScanned(int memberID) throws BorrowerNotFoundException {
		member = memberDao.getMemberByID(memberID);
		if (member == null) {
			throw new BorrowerNotFoundException();
		}
		loanDao.updateOverDueStatus(Calendar.getInstance().getTime());
		borrowUI.displayBorrowerDetails(member);
		if (member.hasOverDueLoans() || member.hasReachedFineLimit() || member.hasReachedLoanLimit()) {
			this.state = State.DISALLOWED;
			borrowUI.setState(State.DISALLOWED);
			//TODO: End UI session
		} else {
			this.state = State.BORROWING;
			borrowUI.setState(State.BORROWING);
			loanDao.createNewPendingList(member);
			borrowUI.scanBook();
		}
		
		return member;
	}

//	Sig : bookScanned(bookID)
//	Throws BookNotFoundException
//	Pre: CTL.state = BORROWING UI.state = BORROWING
//	Post: book added to temporary loan list atLoanLimit updated
//	UI.displayBook called UI.displayPendingList called UI has ref to Book
//	UI has ref to tempLoanList
//	Rules: if atLoanLimit:
//	CTL.state = COMPLETED
//	UI.state = COMPLETED
	@Override
	public void bookScanned(int bookID) throws BookNotFoundException {
		// TODO Auto-generated method stub

	}

//	Sig: scanNext
//	Pre: CTL.state = BORROWING
//	UI.state = BORROWING Post: CTL.state = BORROWING
//	UI.state = BORROWING UI.scanBook called
	@Override
	public void scanNext() {
		// TODO Auto-generated method stub

	}

//	Sig : scansCompleted
//	Pre: CTL.state = BORROWING
//	UI.state = BORROWING
//	tempLoanList exists Post: CTL.state = COMPLETED
//	UI.state = COMPLETED UI.displayCompletedList called
	@Override
	public void scansCompleted() {
		// TODO Auto-generated method stub

	}

//	Sig : rejectPendingList
//	CTL.state = COMPLETED
//	UI.state = COMPLETED Post: CTL.state = BORROWING
//	UI.state = BORROWING TempLoanList cleared/deleted
	@Override
	public void rejectPendingList() {
		// TODO Auto-generated method stub

	}

//	Sig: confirmPendingList
//	Pre: CTL.state = COMPLETED
//	UI.state = COMPLETED Post: CTL.state = CONFIRMED
//	UI.state = CONFIRMED
//	Every loan commited. TempLoanList cleared/deleted Member has ref to every Loan Book has ref to relevant Loan UI.printLoanSlip called 
	@Override
	public void confirmPendingList() {
		// TODO Auto-generated method stub

	}

//	Sig : borrowUCended
//	CTL.state = CONFIRMED
//	UI.state = CONFIRMED Post: CTL.state = ENDED
//	UI.state = ENDED
	@Override
	public void borrowUCended() {
		// TODO Auto-generated method stub

	}

}
