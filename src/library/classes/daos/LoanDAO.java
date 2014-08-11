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
	
	public LoanDAO(ILoanHelper loanHelper) throws IllegalArgumentException {
		assertNotNull(loanHelper);
		this.loanHelper = loanHelper;
		this.pendingLoansByMember = new HashMap<IMember, List<ILoan>>();
	}
	
	@Override
	public void createNewPendingList(IMember borrower) {
		List<ILoan> pendingLoans = new ArrayList<ILoan>();
		pendingLoansByMember.put(borrower, pendingLoans);
	}

	@Override
	public ILoan createPendingLoan(IMember borrower, IBook book,
			Date borrowDate, Date dueDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ILoan> getPendingList(IMember borrower) throws RuntimeException {
		List<ILoan> pendingList = pendingLoansByMember.get(borrower);
		if (pendingList == null) {
			throw new RuntimeException("No pending loan list for borrower");
		}
		return pendingList;
	}

	@Override
	public void commitPendingLoans(IMember borrower) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearPendingLoans(IMember borrower) {
		// TODO Auto-generated method stub

	}

	@Override
	public ILoan getLoanByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILoan getLoanByBook(IBook book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ILoan> listLoans() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ILoan> findLoansByBorrower(IMember borrower) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ILoan> findLoansByBookTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateOverDueStatus(Date currentDate) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ILoan> findOverDueLoans() {
		// TODO Auto-generated method stub
		return null;
	}

}
