package library.classes.entities;

import static library.classes.utils.VerificationUtil.*;

import java.util.Date;

import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.entities.LoanState;

public class Loan implements ILoan {
	IBook book;
	IMember member;
	Date borrowDate;
	Date dueDate;
	int loanId;
	LoanState state;

	public Loan(IBook book, IMember member, Date borrowDate, Date dueDate, int loanId) throws IllegalArgumentException {
		assertNotNull(book);
		assertNotNull(member);
		assertNotNull(borrowDate);
		assertNotNull(dueDate);
		assertLater(borrowDate, dueDate);
		assertNonZeroPositive(loanId);
		
		this.book = book;
		this.member = member;
		this.borrowDate = borrowDate;
		this.dueDate = dueDate;
		this.loanId = loanId;
	}
	
	@Override
	public void commit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void complete() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isOverDue() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkOverDue(Date currentDate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IMember getBorrower() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBook getBook() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
