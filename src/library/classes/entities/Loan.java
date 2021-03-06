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
		this.state = LoanState.PENDING;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Book: " + book.getTitle() + ",\nDue: " + dueDate.toString() + ", \nLoanId: " + loanId + ",\nState: " + state.toString();
	}
	
	@Override
	public void commit() {
		if (this.state != LoanState.PENDING) {
			throw new RuntimeException("Loan not pending");
		}
		this.state = LoanState.CURRENT;
	}

	@Override
	public void complete() throws RuntimeException {
		if (this.state != LoanState.CURRENT && this.state != LoanState.OVERDUE) {
			throw new RuntimeException("Loan not current or overdue");
		}
		this.state = LoanState.COMPLETE;
	}

	@Override
	public boolean isOverDue() {
		return this.state == LoanState.OVERDUE;
	}

	@Override
	public boolean checkOverDue(Date currentDate) throws RuntimeException {
		if (this.state != LoanState.CURRENT && this.state != LoanState.OVERDUE) {
			throw new RuntimeException("Loan not current or overdue");
		}
		if (currentDate.after(dueDate)) {
			this.state = LoanState.OVERDUE;
		}
		return this.state == LoanState.OVERDUE;
	}

	@Override
	public IMember getBorrower() {
		return member;
	}

	@Override
	public IBook getBook() {
		return book;
	}

	@Override
	public int getID() {
		return book.getID();
	}
	

}
