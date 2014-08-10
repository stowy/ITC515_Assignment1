package library.classes.daos;

import java.util.Date;

import library.interfaces.daos.ILoanHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class LoanHelper implements ILoanHelper {

	@Override
	public ILoan makeLoan(IBook book, IMember borrower, Date borrowDate,
			Date dueDate, int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
