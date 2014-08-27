package library.tests.unit.controls;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import library.classes.controls.BorrowCTL;
import library.classes.exceptions.BookNotFoundException;
import library.classes.exceptions.BorrowerNotFoundException;
import library.interfaces.controls.IBorrowCTL;
import library.interfaces.controls.IBorrowCTL.State;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
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
		mockMemberDao = createNiceMock(IMemberDAO.class);
		mockBookDao = createNiceMock(IBookDAO.class);
		mockLoanDao = createNiceMock(ILoanDAO.class);
		mockBorrowUI = createNiceMock(IBorrowUI.class);
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
//	UI.state = ANY 
//	Post: CTL.state = ENDED
//	UI.state = ENDED 
//	TempLoanList cleared/deleted
	@Test
	public void testCancel() {

		//assert clear pending list
		mockLoanDao.clearPendingLoans(EasyMock.anyObject(IMember.class));
		expectLastCall().once();
		//assert set state cancelled
		mockBorrowUI.setState(State.CANCELLED);
		expectLastCall().once();
		
		replay(mockBorrowUI);
		replay(mockLoanDao);
		
		borrowCTL = new BorrowCTL(mockMemberDao, mockBookDao, mockLoanDao, mockBorrowUI);
		borrowCTL.cancel();
		
		verify(mockBorrowUI);
		verify(mockLoanDao);
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
	public void testCardScannedMemberDisallowed() {
		IMember mockMember = createNiceMock(IMember.class);
		expect(mockMemberDao.getMemberByID(EasyMock.anyInt())).andReturn(mockMember);
		mockLoanDao.updateOverDueStatus(EasyMock.anyObject(Date.class));
		expectLastCall().once();
		expect(mockMember.hasOverDueLoans()).andReturn(true);
		mockBorrowUI.setState(State.DISALLOWED);
		expectLastCall().once();
		
		replay(mockMemberDao);
		replay(mockMember);
		replay(mockLoanDao);
		replay(mockBorrowUI);
		
		borrowCTL = new BorrowCTL(mockMemberDao, mockBookDao, mockLoanDao, mockBorrowUI);
		try {
			borrowCTL.cardScanned(1);
		} catch (BorrowerNotFoundException e) {
			fail();
		}
		
		verify(mockMemberDao);
		verify(mockLoanDao);
		verify(mockMember);
		verify(mockBorrowUI);
	}
	
	@Test
	public void testCardScannedMemberBorrowing() {
		IMember mockMember = createMock(IMember.class);
		expect(mockMemberDao.getMemberByID(EasyMock.anyInt())).andReturn(mockMember);
		mockLoanDao.updateOverDueStatus(EasyMock.anyObject(Date.class));
		expectLastCall().once();
		expect(mockMember.hasOverDueLoans()).andReturn(false);
		expect(mockMember.hasReachedFineLimit()).andReturn(false);
		expect(mockMember.hasReachedLoanLimit()).andReturn(false);
		mockBorrowUI.setState(State.BORROWING);
		expectLastCall().once();
		mockBorrowUI.scanBook();
		expectLastCall().once();
		mockLoanDao.createNewPendingList(mockMember);
		expectLastCall().once();
		
		replay(mockMemberDao);
		replay(mockMember);
		replay(mockLoanDao);
		replay(mockBorrowUI);
		
		borrowCTL = new BorrowCTL(mockMemberDao, mockBookDao, mockLoanDao, mockBorrowUI);
		try {
			borrowCTL.cardScanned(1);
		} catch (BorrowerNotFoundException e) {
			fail();
		}
		
		verify(mockMemberDao);
		verify(mockLoanDao);
		verify(mockMember);
		verify(mockBorrowUI);		
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
		int memberId = 1;
		int bookId = 1;
		//Expect		
		IMember mockMember = createMock(IMember.class);
		expect(mockMemberDao.getMemberByID(memberId)).andReturn(mockMember);
		mockLoanDao.updateOverDueStatus(EasyMock.anyObject(Date.class));
		expectLastCall().once();
		expect(mockMember.hasOverDueLoans()).andReturn(false);
		expect(mockMember.hasReachedFineLimit()).andReturn(false);
		expect(mockMember.hasReachedLoanLimit()).andReturn(false).once();
		expect(mockMember.hasReachedLoanLimit()).andReturn(true).once();
		
		//BookDAO.getbook
		IBook book = createNiceMock(IBook.class);
		expect(mockBookDao.getBookByID(bookId)).andReturn(book);
		//LoanDAO.addPendingLoan
		expect(mockLoanDao.createPendingLoan(EasyMock.anyObject(IMember.class), EasyMock.anyObject(IBook.class), EasyMock.anyObject(Date.class), EasyMock.anyObject(Date.class))).andReturn(EasyMock.anyObject(ILoan.class));
		//Member.hasReachedLoanLimit

		//UI.displaybook
		mockBorrowUI.displayBook(book);
		expectLastCall().once();
		List<ILoan> list = new ArrayList<ILoan>();
		expect(mockLoanDao.getPendingList(mockMember)).andReturn(list);
		//UI.displayPendingList
		mockBorrowUI.displayPendingList(list);
		expectLastCall().once();
		//Check state updated if at loan limit
		mockBorrowUI.setState(State.COMPLETED);
		expectLastCall().once();
		
		//Replay
		replay(mockMember);
		replay(mockMemberDao);
		replay(book);
		replay(mockBookDao);
		replay(mockLoanDao);
		replay(mockBorrowUI);
		
		//Perform Actions
		borrowCTL = new BorrowCTL(mockMemberDao, mockBookDao, mockLoanDao, mockBorrowUI);
		try {
			borrowCTL.cardScanned(memberId);
			borrowCTL.bookScanned(bookId);
		} catch (BorrowerNotFoundException e) {
			fail();
		} catch (BookNotFoundException e) {
			fail();
		}
		
		//Verify
		verify(mockMember);
		verify(mockMemberDao);
		verify(book);
		verify(mockBookDao);
		verify(mockLoanDao);
		verify(mockBorrowUI);
	}

//	Sig: scanNext
//	Pre: CTL.state = BORROWING
//	UI.state = BORROWING 
//	Post: CTL.state = BORROWING
//	UI.state = BORROWING UI.scanBook called
	@Test
	public void testScanNextContinueBorrowing() {
		
		int memberId = 1;
		//Expect		
		IMember mockMember = createNiceMock(IMember.class);
		expect(mockMemberDao.getMemberByID(memberId)).andReturn(mockMember);
		
		//assert that user at loan limit is checked (return false)
		expect(mockMember.hasReachedLoanLimit()).andReturn(false).times(2);
		//assert that scan book is called
		mockBorrowUI.scanBook();
		expectLastCall().once();
		
		//Replay
		replay(mockMember);
		replay(mockMemberDao);
		replay(mockBorrowUI);
		
		//Perform Actions
		//Set up preconditions
		borrowCTL = new BorrowCTL(mockMemberDao, mockBookDao, mockLoanDao, mockBorrowUI);
		try {
			borrowCTL.cardScanned(memberId);
		} catch (BorrowerNotFoundException e) {
			fail();
		} 
		
		//Method under test
		borrowCTL.scanNext();
		
		//Verify
		verify(mockMember);
		verify(mockMemberDao);
		verify(mockBorrowUI);
	}
	
	@Test
	public void testScanNextAtLoanLimit() {
		
		int memberId = 1;
		//Expect		
		IMember mockMember = createNiceMock(IMember.class);
		expect(mockMemberDao.getMemberByID(memberId)).andReturn(mockMember);
		
		//assert that user at loan limit is checked (return true)
		expect(mockMember.hasReachedLoanLimit()).andReturn(false).once();
		expect(mockMember.hasReachedLoanLimit()).andReturn(true).once();

		//assert set state completed is called
		mockBorrowUI.setState(State.COMPLETED);
		expectLastCall().once();
		//asert that get pending list is called
		List<ILoan> list = new ArrayList<ILoan>();
		expect(mockLoanDao.getPendingList(mockMember)).andReturn(list);
		//assert that display completed list is called
		mockBorrowUI.displayCompletedList(list);
		expectLastCall().once();
		
		//Replay
		replay(mockMember);
		replay(mockMemberDao);
		replay(mockLoanDao);
		replay(mockBorrowUI);
		
		//Perform Actions
		//Set up preconditions
		borrowCTL = new BorrowCTL(mockMemberDao, mockBookDao, mockLoanDao, mockBorrowUI);
		try {
			borrowCTL.cardScanned(memberId);
		} catch (BorrowerNotFoundException e) {
			fail();
		} 
		
		//Method under test
		borrowCTL.scanNext();
		
		//Verify
		verify(mockMember);
		verify(mockMemberDao);
		verify(mockLoanDao);
		verify(mockBorrowUI);
	}

//	Sig : scansCompleted
//	Pre: CTL.state = BORROWING
//	UI.state = BORROWING
//	tempLoanList exists 
//	Post: CTL.state = COMPLETED
//	UI.state = COMPLETED 
//	UI.displayCompletedList called
	@Test
	public void testScansCompleted() {

		int memberId = 1;
		//Expect		
		//inject mockMember dependency
		IMember mockMember = createNiceMock(IMember.class);
		expect(mockMemberDao.getMemberByID(memberId)).andReturn(mockMember);
	
		//assert set state completed is called
		mockBorrowUI.setState(State.COMPLETED);
		expectLastCall().once();
		//assert that get pending list is called
		List<ILoan> list = new ArrayList<ILoan>();
		expect(mockLoanDao.getPendingList(mockMember)).andReturn(list);
		//assert that display completed list is called
		mockBorrowUI.displayCompletedList(list);
		expectLastCall().once();
		
		//Replay
		replay(mockMember);
		replay(mockMemberDao);
		replay(mockLoanDao);
		replay(mockBorrowUI);
		
		//Perform Actions
		//Set up preconditions
		borrowCTL = new BorrowCTL(mockMemberDao, mockBookDao, mockLoanDao, mockBorrowUI);
		try {
			borrowCTL.cardScanned(memberId);
		} catch (BorrowerNotFoundException e) {
			fail();
		} 
		
		//Method under test
		borrowCTL.scansCompleted();
		
		//Verify
		verify(mockMember);
		verify(mockMemberDao);
		verify(mockLoanDao);
		verify(mockBorrowUI);
	}

//	Sig : rejectPendingList
//	CTL.state = COMPLETED
//	UI.state = COMPLETED 
//	Post: CTL.state = BORROWING
//	UI.state = BORROWING 
//	TempLoanList cleared/deleted
	@Test
	public void testRejectPendingList() {
		//Expect
		//assert clear pending list called
		mockLoanDao.clearPendingLoans(EasyMock.anyObject(IMember.class));
		expectLastCall().once();
		//assert create new pending list called
		mockLoanDao.createNewPendingList(EasyMock.anyObject(IMember.class));
		expectLastCall().once();
		//assert set state borrowing called
		mockBorrowUI.setState(State.BORROWING);
		expectLastCall().once();
		mockBorrowUI.scanBook();
		expectLastCall().once();
		
		replay(mockLoanDao);
		replay(mockBorrowUI);
		
		borrowCTL = new BorrowCTL(mockMemberDao, mockBookDao, mockLoanDao, mockBorrowUI);
		borrowCTL.rejectPendingList();
		
		verify(mockLoanDao);
		verify(mockBorrowUI);
	}

//	Sig: confirmPendingList
//	Pre: CTL.state = COMPLETED
//	UI.state = COMPLETED 
//	Post: CTL.state = CONFIRMED
//	UI.state = CONFIRMED
//	Every loan commited. 
//	TempLoanList cleared/deleted Member has ref to every 
//	Loan Book has ref to relevant Loan 
//	UI.printLoanSlip called 
	@Test
	public void testConfirmPendingList() {
		//assert UI setState CONFIRMED
		mockBorrowUI.setState(State.CONFIRMED);
		expectLastCall().once();
		//assert loan commit pending loans
		mockLoanDao.commitPendingLoans(EasyMock.anyObject(IMember.class));
		expectLastCall().once();
		//assert assert print loan slip
		mockBorrowUI.printLoanSlip();
		expectLastCall().once();
		
		replay(mockLoanDao);
		replay(mockBorrowUI);
		
		borrowCTL = new BorrowCTL(mockMemberDao, mockBookDao, mockLoanDao, mockBorrowUI);
		borrowCTL.confirmPendingList();
		
		verify(mockLoanDao);
		verify(mockBorrowUI);
		
	}

//	Sig : borrowUCended
//	CTL.state = CONFIRMED
//	UI.state = CONFIRMED Post: CTL.state = ENDED
//	UI.state = ENDED
	@Test
	public void testBorrowUCended() {
		
		//assert set state ended
		mockBorrowUI.setState(State.ENDED);
		expectLastCall().once();
		
		replay(mockBorrowUI);
		
		borrowCTL = new BorrowCTL(mockMemberDao, mockBookDao, mockLoanDao, mockBorrowUI);
		borrowCTL.borrowUCended();
		
		verify(mockBorrowUI);
	}

}
