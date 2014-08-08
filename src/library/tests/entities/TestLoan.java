package library.tests.entities;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import library.classes.entities.Loan;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLoan {
	IBook book = createMock(IBook.class);
	IMember member = createMock(IMember.class);
	int loanId = 1;
	Date borrowDate;
	Date dueDate;
	
	ILoan loan;

	@Before
	public void setUp() throws Exception {
		Calendar calendar = Calendar.getInstance();
		borrowDate = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, ILoan.LOAN_PERIOD);
		dueDate = calendar.getTime();
		
		loan = new Loan(book, member, borrowDate, dueDate, loanId);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidConstructor() {
		Loan newLoan = new Loan(book, member, borrowDate, dueDate, loanId);
		assertNotNull(newLoan);
		assertTrue(newLoan instanceof Loan);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidConstructor() {
		new Loan(book, null, borrowDate, dueDate, loanId);
	}
	
	@Test(expected=RuntimeException.class)
	public void testCommitLoanNotPending() {
		loan.commit();
		loan.commit();
	}
	
	@Test(expected=RuntimeException.class)
	public void testComplete() {
		loan.complete();
	}
	
	@Test
	public void testIsOverDueNotOverDue() {
		assertFalse(loan.isOverDue());
	}
	
	@Test
	public void testCheckOverDueOverDue() {
		loan.commit();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, ILoan.LOAN_PERIOD + 1);
		Date overDueDate = calendar.getTime();
		assertTrue(loan.checkOverDue(overDueDate));
	}
	
	@Test(expected=RuntimeException.class)
	public void testCheckOverDueLoanPending() {
		loan.checkOverDue(Calendar.getInstance().getTime());
	}
	
	@Test
	public void testIsOverDueOverDue() {
		loan.commit();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, ILoan.LOAN_PERIOD + 1);
		Date overDueDate = calendar.getTime();
		assertTrue(loan.checkOverDue(overDueDate));
		assertTrue(loan.isOverDue());
	}
	
	@Test
	public void testCheckOverDueNotOverDue() {
		loan.commit();
		assertFalse(loan.checkOverDue(Calendar.getInstance().getTime()));
	}
	
	@Test
	public void testGetBorrower() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetBook() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetID() {
		fail("Not yet implemented");
	}

}
