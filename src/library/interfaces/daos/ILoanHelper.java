package library.interfaces.daos;

import java.util.Date;

import library.classes.entities.*;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public interface ILoanHelper {
	
	public ILoan makeLoan(IBook book, IMember borrower, Date borrowDate, Date dueDate, int id);

}
