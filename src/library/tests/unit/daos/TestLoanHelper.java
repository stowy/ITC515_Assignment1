package library.tests.unit.daos;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import library.classes.daos.LoanHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLoanHelper {
	
	IBook book = createMock(IBook.class);
	IMember member = createMock(IMember.class);
	int loanId = 1;
	Date borrowDate;
	Date dueDate;

	@Before
	public void setUp() throws Exception {
		Calendar calendar = Calendar.getInstance();
		borrowDate = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, ILoan.LOAN_PERIOD);
		dueDate = calendar.getTime();
	}

	@After
	public void tearDown() throws Exception {
		borrowDate = null;
		dueDate = null;
	}

	@Test
	public void test() {
		LoanHelper helper = new LoanHelper();
		ILoan loan = helper.makeLoan(book, member, borrowDate, dueDate, loanId);
		assertNotNull(loan);
		assertTrue(loan instanceof ILoan);
	}

}
