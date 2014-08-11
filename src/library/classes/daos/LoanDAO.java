package library.classes.daos;

import java.util.Date;
import java.util.List;

import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class LoanDAO implements ILoanDAO {

	public LoanDAO(ILoanHelper loanHelper) throws IllegalArgumentException {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void createNewPendingList(IMember borrower) {
		// TODO Auto-generated method stub

	}

	@Override
	public ILoan createPendingLoan(IMember borrower, IBook book,
			Date borrowDate, Date dueDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ILoan> getPendingList(IMember borrower) {
		// TODO Auto-generated method stub
		return null;
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
