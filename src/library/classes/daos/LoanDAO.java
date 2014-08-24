package library.classes.daos;


import static library.classes.utils.VerificationUtil.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class LoanDAO implements ILoanDAO {

	ILoanHelper loanHelper;
	Map<IMember, List<ILoan>> pendingLoansByMember;
	Map<Integer, ILoan> currentLoans;
	int nextId;
	
	public LoanDAO(ILoanHelper loanHelper) throws IllegalArgumentException {
		assertNotNull(loanHelper);
		this.loanHelper = loanHelper;
		this.pendingLoansByMember = new HashMap<IMember, List<ILoan>>();
		this.currentLoans = new HashMap<Integer, ILoan>();
		nextId = 1;
	}
	
	@Override
	public void createNewPendingList(IMember borrower) throws IllegalArgumentException{
		assertNotNull(borrower);
		List<ILoan> pendingLoans = new ArrayList<ILoan>();
		pendingLoansByMember.put(borrower, pendingLoans);
	}

	@Override
	public ILoan createPendingLoan(IMember borrower, IBook book,
			Date borrowDate, Date dueDate) throws IllegalArgumentException, RuntimeException {
		List<ILoan> pendingLoans = getPendingList(borrower);
		ILoan loan = loanHelper.makeLoan(book, borrower, borrowDate, dueDate, nextId);
		pendingLoans.add(loan);
		borrower.addLoan(loan);
		nextId++;
		return loan;
	}

	@Override
	public List<ILoan> getPendingList(IMember borrower) throws RuntimeException, IllegalArgumentException {
		assertNotNull(borrower);
		List<ILoan> pendingList = pendingLoansByMember.get(borrower);
		if (pendingList == null) {
			throw new RuntimeException("No pending loan list for borrower");
		}
		return pendingList;
	}

	@Override
	public void commitPendingLoans(IMember borrower) throws IllegalArgumentException, RuntimeException {
		List<ILoan> pendingLoans = getPendingList(borrower);
		for (ILoan loan : pendingLoans) {
//			adds the loan to the borrower’s list of current loans
			//loan already associated with borrower
//			associates the loan with the book
			loan.getBook().borrow(loan);
			loan.commit();
//			inserts the loan into a map of committed loans with a key of the loan id
			currentLoans.put(loan.getID(), loan);
		}
//		removes the pending loan list associated with the borrower from the pending list map
		pendingLoansByMember.remove(borrower);
	}

	@Override
	public void clearPendingLoans(IMember borrower) throws IllegalArgumentException, RuntimeException {
		List<ILoan> pendingLoans = getPendingList(borrower);
		//Remove all pending loans from borrower list
		for (ILoan pendingLoan : pendingLoans) {
			borrower.removeLoan(pendingLoan);
		}
		pendingLoans.clear();
	}

	@Override
	public ILoan getLoanByID(int id) {
		return currentLoans.get(id);
	}

	@Override
	public ILoan getLoanByBook(IBook book) {
		ILoan foundLoan = null;
		for (ILoan loan : currentLoans.values()) {
			if (loan.getBook().equals(book)) {
				foundLoan = loan;
			}
		}
		return foundLoan;
	}

	@Override
	public List<ILoan> listLoans() {
		return new ArrayList<ILoan>(currentLoans.values());
	}

	@Override
	public List<ILoan> findLoansByBorrower(IMember borrower) {
		List<ILoan> foundLoans = new ArrayList<ILoan>();
		for (ILoan loan : currentLoans.values()) {
			if (loan.getBorrower().equals(borrower)) {
				foundLoans.add(loan);
			}
		}
		return foundLoans;
	}

	@Override
	public List<ILoan> findLoansByBookTitle(String title) {
		List<ILoan> foundLoans = new ArrayList<ILoan>();
		for (ILoan loan : currentLoans.values()) {
			IBook book = loan.getBook();
			if (book.getTitle().equals(title)) {
				foundLoans.add(loan);
			}
		}
		return foundLoans;
	}

	@Override
	public void updateOverDueStatus(Date currentDate) {
		for (ILoan loan : currentLoans.values()) {
			loan.checkOverDue(currentDate);
		}
	}

	@Override
	public List<ILoan> findOverDueLoans() {
		List<ILoan> overdueLoans = new ArrayList<ILoan>();
		for (ILoan loan : currentLoans.values()) {
			if (loan.isOverDue()) {
				overdueLoans.add(loan);
			}
		}
		return overdueLoans;
	}

}
