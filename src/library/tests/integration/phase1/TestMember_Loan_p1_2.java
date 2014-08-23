package library.tests.integration.phase1;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import library.classes.entities.Loan;
import library.classes.entities.Member;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.entities.MemberState;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMember_Loan_p1_2 {

	String first = "first";
	String last = "last";
	String phone = "phone";
	String email = "email";
	int id = 1;
	
	IBook mockBook = createMock(IBook.class);
	ILoan loan;
	int loanId = 1;
	Date borrowDate;
	Date dueDate;
	
	IMember member;
	
	@Before
	public void setUp() throws Exception {
		member = new Member(first, last, phone, email, id);
		Calendar calendar = Calendar.getInstance();
		borrowDate = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, ILoan.LOAN_PERIOD);
		dueDate = calendar.getTime();
		
		loan = new Loan(mockBook, member, borrowDate, dueDate, loanId);
	}

	@After
	public void tearDown() throws Exception {
		member = null;
		loan = null;
		borrowDate = null;
		dueDate = null;
	}
	
	@Test
	public void testHasOverDueLoans() {
		loan.commit();
		member.addLoan(loan);
		assertFalse(member.hasOverDueLoans());
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dueDate);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		loan.checkOverDue(calendar.getTime());
		
		assertTrue(loan.isOverDue());
		assertTrue(member.hasOverDueLoans());
	}
	
	@Test
	public void testHasReachedLoanLimit() {
		int newLoanId = loanId + 1;
		for (int i =0; i < Member.LOAN_LIMIT; i ++) {
			ILoan newLoan = new Loan(mockBook, member, borrowDate, dueDate, newLoanId);
			member.addLoan(newLoan);
			newLoanId++;
		}
		assertTrue(member.getLoans().size() == Member.LOAN_LIMIT);
		assertTrue(member.hasReachedLoanLimit());
		assertTrue(member.getState() == MemberState.BORROWING_DISALLOWED);
	}
	
	@Test
	public void testAddLoan() {
		member.addLoan(loan);
		List<ILoan> loans = member.getLoans();
		assertNotNull(loans);
		assertTrue(loans.contains(loan));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddLoanBorrowingDisallowed() {
		member.addFine(Member.FINE_LIMIT + 1);
		assertTrue(member.getState() == MemberState.BORROWING_DISALLOWED);
		member.addLoan(loan);
	}

	@Test
	public void testRemoveLoan() {
		member.addLoan(loan);
		List<ILoan> loans = member.getLoans();
		assertNotNull(loans);
		assertTrue(loans.contains(loan));
		member.removeLoan(loan);
		assertFalse(member.getLoans().contains(loan));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveLoanNotPresent() {
		member.removeLoan(loan);
	}
	


}
