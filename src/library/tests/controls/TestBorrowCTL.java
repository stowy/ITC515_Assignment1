package library.tests.controls;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import library.classes.controls.BorrowCTL;
import library.interfaces.controls.IBorrowCTL;
import library.interfaces.controls.IBorrowCTL.State;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.uis.IBorrowUI;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestBorrowCTL {
	
	private IMemberDAO mockMemberDao;
	private IBookDAO mockBookDao;
	private ILoanDAO mockLoanDao;
	private IBorrowUI mockBorrowUI;
	private IBorrowCTL borrowCTL;

	@Before
	public void setUp() throws Exception {
		mockMemberDao = createMock(IMemberDAO.class);
		mockBookDao = createMock(IBookDAO.class);
		mockLoanDao = createMock(ILoanDAO.class);
		mockBorrowUI = createMock(IBorrowUI.class);
		borrowCTL = new BorrowCTL(mockMemberDao, mockBookDao, mockLoanDao, mockBorrowUI);
	}

	@After
	public void tearDown() throws Exception {
		mockMemberDao = null;
		mockBookDao = null;
		mockLoanDao = null;
		mockBorrowUI = null;
	}
	
//	Pre: CTL does not exist
//	UI needs to be initialized
//	memberDAO exists loanDAO exists bookDAO exists
//	Post: CTL exists UI initialized
//	CTL.state = STARTED UI.state = STARTED 
//	Refs to DAOS stored.
	@Test
	public void testValidConstructor() {
		IBorrowUI testMockBorrowUI = createMock(IBorrowUI.class);
		testMockBorrowUI.initialise(EasyMock.anyObject(IBorrowCTL.class));
		expectLastCall().once();
		testMockBorrowUI.setState(State.STARTED);
		expectLastCall().once();
		replay(testMockBorrowUI);
		IBorrowCTL testBorrowCTL = new BorrowCTL(mockMemberDao, mockBookDao, mockLoanDao, testMockBorrowUI);
	
		assertNotNull(testBorrowCTL);
		assertTrue(testBorrowCTL instanceof IBorrowCTL);
		
	 	verify(testMockBorrowUI);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidConstructor() {
		new BorrowCTL(mockMemberDao, mockBookDao, mockLoanDao, null);
	}
	
//	Sig : cancel
//	CTL.state = ANY
//	UI.state = ANY Post: CTL.state = ENDED
//	UI.state = ENDED TempLoanList cleared/deleted
	@Test
	public void testCancel() {
		fail("Not yet implemented");
	}

//	Sig: Borrower = cardScanned(borrowerID)
//			Throws borrowerNotFoundException
//			Pre: CTL.state = STARTED UI.state = STARTED
//			Post: CTL has ref to Member UI.displayBorrowerDetails called
//			overdue, atLoanLimit, overFineLimit set
//			UI.displayBorrowerDetails called.
//			Rules: if (overdue || atLoanLimit ||overFineLimit)
//			CTL.state = DISALLOWED UI.state = DISALLOWED
//			Else:
//			CTL.state = BORROWING
//			UI.state = BORROWING tempLoanList created. UI.scanBook called
	@Test
	public void testCardScanned() {
		fail("Not yet implemented");
	}

//	Sig : bookScanned(bookID)
//	Throws BookNotFoundException
//	Pre: CTL.state = BORROWING UI.state = BORROWING
//	Post: book added to temporary loan list atLoanLimit updated
//	UI.displayBook called UI.displayPendingList called UI has ref to Book
//	UI has ref to tempLoanList
//	Rules: if atLoanLimit:
//	CTL.state = COMPLETED
//	UI.state = COMPLETED
	@Test
	public void testBookScanned() {
		fail("Not yet implemented");
	}

//	Sig: scanNext
//	Pre: CTL.state = BORROWING
//	UI.state = BORROWING Post: CTL.state = BORROWING
//	UI.state = BORROWING UI.scanBook called
	@Test
	public void testScanNext() {
		fail("Not yet implemented");
	}

//	Sig : scansCompleted
//	Pre: CTL.state = BORROWING
//	UI.state = BORROWING
//	tempLoanList exists Post: CTL.state = COMPLETED
//	UI.state = COMPLETED UI.displayCompletedList called
	@Test
	public void testScansCompleted() {
		fail("Not yet implemented");
	}

//	Sig : rejectPendingList
//	CTL.state = COMPLETED
//	UI.state = COMPLETED Post: CTL.state = BORROWING
//	UI.state = BORROWING TempLoanList cleared/deleted
	@Test
	public void testRejectPendingList() {
		fail("Not yet implemented");
	}

//	Sig: confirmPendingList
//	Pre: CTL.state = COMPLETED
//	UI.state = COMPLETED Post: CTL.state = CONFIRMED
//	UI.state = CONFIRMED
//	Every loan commited. TempLoanList cleared/deleted Member has ref to every Loan Book has ref to relevant Loan UI.printLoanSlip called 
	@Test
	public void testConfirmPendingList() {
		fail("Not yet implemented");
	}

//	Sig : borrowUCended
//	CTL.state = CONFIRMED
//	UI.state = CONFIRMED Post: CTL.state = ENDED
//	UI.state = ENDED
	@Test
	public void testBorrowUCended() {
		fail("Not yet implemented");
	}

}
