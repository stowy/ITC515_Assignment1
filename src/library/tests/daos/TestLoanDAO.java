package library.tests.daos;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import library.classes.daos.LoanDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLoanDAO {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidConstructor() {
		ILoanHelper helper = createMock(ILoanHelper.class);
		ILoanDAO testLoanDAO = new LoanDAO(helper);
		assertNotNull(testLoanDAO);
		assertTrue(testLoanDAO instanceof ILoanDAO);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidConstructor() {
		new LoanDAO(null);
	}
	
	@Test
	public void testCreateNewPendingList() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreatePendingLoan() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetPendingList() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testCommitPendingLoans() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testClearPendingLoans() {
		fail("Not yet implemented");
	}	
	
	@Test
	public void testGetLoanByID() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetLoanByBook() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testListLoans() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFindLoansByBorrower() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindLoansByBookTitle() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testUpdateOverDueStatus() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindOverDueLoans() {
		fail("Not yet implemented");
	}

}
