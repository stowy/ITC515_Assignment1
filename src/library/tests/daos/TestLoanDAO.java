package library.tests.daos;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import library.classes.daos.LoanDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLoanDAO {

	private ILoanHelper mockLoanHelper;
	private ILoanDAO loanDAO;
	
	ILoan mockLoan;
	IBook mockBook;
	IMember mockMember;
	Date borrowDate;
	Date dueDate;
	
	@Before
	public void setUp() throws Exception {
		mockLoanHelper = createMock(ILoanHelper.class);
		loanDAO = new LoanDAO(mockLoanHelper);
		
		Calendar calendar = Calendar.getInstance();
		borrowDate = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, ILoan.LOAN_PERIOD);
		dueDate = calendar.getTime();
		
		mockLoan = createMock(ILoan.class);
		mockBook = createMock(IBook.class);
		mockMember = createMock(IMember.class);
	}

	@After
	public void tearDown() throws Exception {
		mockLoanHelper = null;
		loanDAO = null;
		
		borrowDate = null;
		dueDate = null;
		mockBook = null;
		mockMember = null;
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
		loanDAO.createNewPendingList(mockMember);
		List<ILoan> loans = loanDAO.getPendingList(mockMember);
		assertNotNull(loans);
		assertTrue(loans.size() == 0);
	}

	@Test
	public void testCreatePendingLoan() {
		expect(mockLoanHelper.makeLoan(EasyMock.anyObject(IBook.class), EasyMock.anyObject(IMember.class), EasyMock.anyObject(Date.class), EasyMock.anyObject(Date.class), EasyMock.anyInt())).andReturn(mockLoan);
		replay(mockLoanHelper);
		loanDAO.createNewPendingList(mockMember);
		loanDAO.createPendingLoan(mockMember, mockBook, borrowDate, dueDate);
		verify(mockLoanHelper);
		List<ILoan> actual = loanDAO.getPendingList(mockMember);
		assertNotNull(actual);
		assertTrue(actual.contains(mockLoan));
	}
	
	@Test(expected=RuntimeException.class)
	public void testCreatePendingLoanException() {
		loanDAO.createPendingLoan(mockMember, mockBook, borrowDate, dueDate);
	}
	
	@Test
	public void testGetPendingList() {
		loanDAO.createNewPendingList(mockMember);
		List<ILoan> loans = loanDAO.getPendingList(mockMember);
		assertNotNull(loans);
		assertTrue(loans.size() == 0);
	}
	
	@Test(expected=RuntimeException.class)
	public void testGetPendingListException() {
		loanDAO.getPendingList(mockMember);
	}
	
	@Test
	public void testCommitPendingLoans() {
		//Set up expectations
		int id = 1;
		expect(mockLoanHelper.makeLoan(EasyMock.anyObject(IBook.class), EasyMock.anyObject(IMember.class), EasyMock.anyObject(Date.class), EasyMock.anyObject(Date.class), EasyMock.anyInt())).andReturn(mockLoan);
		expect(mockLoan.getBorrower()).andReturn(mockMember);
		expect(mockLoan.getBook()).andReturn(mockBook);
		mockMember.addLoan(mockLoan);
		expectLastCall().once();
		mockBook.borrow(mockLoan);
		expectLastCall().once();
		mockLoan.commit();
		expectLastCall().once();
		expect(mockLoan.getID()).andReturn(id);
		
		//Replay mocks
		replay(mockLoanHelper);
		replay(mockMember);
		replay(mockBook);
		replay(mockLoan);
		
		//Perform actions
		loanDAO.createNewPendingList(mockMember);
		loanDAO.createPendingLoan(mockMember, mockBook, borrowDate, dueDate);
		loanDAO.commitPendingLoans(mockMember);
		
		//Verify mocks
		verify(mockLoanHelper);
		verify(mockMember);
		verify(mockBook);
		verify(mockBook);
		
		//Check loans state
		List<ILoan> committedLoans = loanDAO.listLoans();
		assertTrue(committedLoans.contains(mockLoan));
	}
	
	@Test
	public void testClearPendingLoans() {
		expect(mockLoanHelper.makeLoan(EasyMock.anyObject(IBook.class), EasyMock.anyObject(IMember.class), EasyMock.anyObject(Date.class), EasyMock.anyObject(Date.class), EasyMock.anyInt())).andReturn(mockLoan);
		
		//Replay mocks
		replay(mockLoanHelper);
				
		//Perform actions
		loanDAO.createNewPendingList(mockMember);
		loanDAO.createPendingLoan(mockMember, mockBook, borrowDate, dueDate);
		
		//Verify mocks
		verify(mockLoanHelper);
		
		List<ILoan> actual = loanDAO.getPendingList(mockMember);
		assertEquals(1, actual.size());
		assertTrue(actual.contains(mockLoan));
		
		loanDAO.clearPendingLoans(mockMember);
		actual = loanDAO.getPendingList(mockMember);
		assertEquals(0, actual.size());
		assertFalse(actual.contains(mockLoan));
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
