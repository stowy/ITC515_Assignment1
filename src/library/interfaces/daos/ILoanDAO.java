package library.interfaces.daos;

import java.util.Date;
import java.util.List;

import library.classes.entities.*;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public interface ILoanDAO {
	
	public void createNewPendingList(IMember borrower);

	public ILoan createPendingLoan(IMember borrower, IBook book, Date borrowDate, Date dueDate);
	
	public List<ILoan> getPendingList(IMember borrower);
	
	public void commitPendingLoans(IMember borrower);
	
	public void clearPendingLoans(IMember borrower);	
	
	public ILoan getLoanByID(int id);
	
	public ILoan getLoanByBook(IBook book);
	
	public List<ILoan> listLoans();
	
	public List<ILoan> findLoansByBorrower(IMember borrower);

	public List<ILoan> findLoansByBookTitle(String title);
	
	public void updateOverDueStatus(Date currentDate);

	public List<ILoan> findOverDueLoans();

}

