package library.tests.integration.phase3;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import library.classes.controls.BorrowCTL;
import library.classes.daos.BookDAO;
import library.classes.daos.BookHelper;
import library.classes.daos.LoanDAO;
import library.classes.daos.LoanHelper;
import library.classes.daos.MemberDAO;
import library.classes.daos.MemberHelper;
import library.classes.entities.Member;
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


public class TestBorrowCTL_DAOs_p3_7_12 {

	private IMemberDAO memberDao;
	private IBookDAO bookDao;
	private ILoanDAO loanDao;
	private IBorrowUI mockBorrowUI;
	private IBorrowCTL borrowCTL;
	
	private IMember member;
	private IBook book;

	private String firstName = "firstName";
	private String lastName = "lastName";
	private String contactPhone = "contactPhone";
	private String email = "email";
	
	private String author = "author";
	private String title = "title";
	private String callNo = "callNo";
	
	private Field borrowCTLStateField;
	private Field borrowCTLMemberField;
	
	@Before
	public void setUp() throws Exception {
		memberDao = new MemberDAO(new MemberHelper());
		bookDao = new BookDAO(new BookHelper());
		loanDao = new LoanDAO(new LoanHelper());
		mockBorrowUI = createNiceMock(IBorrowUI.class);
		
		book = bookDao.addBook(author, title, callNo);
		member = memberDao.addMember(firstName, lastName, contactPhone, email);
		
		borrowCTLStateField = BorrowCTL.class.getDeclaredField("state");
		borrowCTLStateField.setAccessible(true);
		borrowCTLMemberField = BorrowCTL.class.getDeclaredField("member");
		borrowCTLMemberField.setAccessible(true);
	}

	@After
	public void tearDown() throws Exception {
		memberDao = null;
		bookDao = null;
		loanDao = null;
		mockBorrowUI = null;
		
		book = null;
		member = null;
	}
	
	//9.  BBUC_Op1 – inititialize - Control – DAOs
//	Pre: CTL does not exist
//	UI needs to be initialized
//	memberDAO exists loanDAO exists bookDAO exists
//	Post: CTL exists UI initialized
//	CTL.state = STARTED 
//	UI.state = STARTED 
//	Refs to DAOS stored.
	@Test
	public void testValidConstructor() {
		
		IBorrowUI testMockBorrowUI = createMock(IBorrowUI.class);
		testMockBorrowUI.initialise(EasyMock.anyObject(IBorrowCTL.class));
		expectLastCall().once();
		testMockBorrowUI.setState(State.STARTED);
		expectLastCall().once();
		replay(testMockBorrowUI);
		IBorrowCTL testBorrowCTL = new BorrowCTL(memberDao, bookDao, loanDao, testMockBorrowUI);
	
		assertNotNull(testBorrowCTL);
		assertTrue(testBorrowCTL instanceof IBorrowCTL);
		
	 	verify(testMockBorrowUI);
	 	
		//Reflect CTL state
	 	State ctlState = null;
	 	try {
			ctlState = (State)borrowCTLStateField.get(testBorrowCTL);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			fail();
		}
	 	assertEquals(State.STARTED, ctlState);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidConstructor() {
		new BorrowCTL(memberDao, bookDao, loanDao, null);
	}
	

	//12. BBUC_Op9 – cancel - Control – LoanDAO – UI
//	Sig : cancel
//	CTL.state = ANY
//	UI.state = ANY 
//	Post: CTL.state = ENDED
//	UI.state = ENDED 
//	TempLoanList cleared/deleted
	@Test
	public void testCancel() throws BorrowerNotFoundException, BookNotFoundException {

		//assert set state cancelled
		mockBorrowUI.setState(State.CANCELLED);
		expectLastCall().once();
		
		replay(mockBorrowUI);
		
		borrowCTL = new BorrowCTL(memberDao, bookDao, loanDao, mockBorrowUI);
		borrowCTL.cardScanned(member.getID());
		borrowCTL.bookScanned(book.getID());
		borrowCTL.cancel();
		
		verify(mockBorrowUI);
		List<ILoan> pendingLoans = loanDao.getPendingList(member);
		assertTrue(pendingLoans == null || pendingLoans.size() == 0);
		
		//Reflect CTL state
	 	State ctlState = null;
	 	try {
			ctlState = (State)borrowCTLStateField.get(borrowCTL);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			fail();
		}
	 	assertEquals(State.CANCELLED, ctlState);
	}

	//10. BBUC_Op2 – cardScanned - Control – MemberDAO – Member - UI
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
		 
		mockBorrowUI.setState(State.DISALLOWED);
		expectLastCall().once();
		
		replay(mockBorrowUI);
		
		//Set up member state over fine limit
		member.addFine(Member.FINE_LIMIT + 1);
		assertTrue(member.hasReachedFineLimit());
		
		borrowCTL = new BorrowCTL(memberDao, bookDao, loanDao, mockBorrowUI);
		try {
			borrowCTL.cardScanned(member.getID());
		} catch (BorrowerNotFoundException e) {
			fail();
		}
		
		//Reflect CTL state, member
	 	State ctlState = null;
	 	IMember ctlMember = null;
	 	try {
			ctlState = (State)borrowCTLStateField.get(borrowCTL);
			ctlMember = (IMember) borrowCTLMemberField.get(borrowCTL);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			fail();
		}
	 	assertEquals(State.DISALLOWED, ctlState);
	 	assertEquals(member, ctlMember);
		
		verify(mockBorrowUI);
	}
	
	@Test
	public void testCardScannedMemberBorrowing() {
		
		mockBorrowUI.setState(State.BORROWING);
		expectLastCall().once();
		mockBorrowUI.scanBook();
		expectLastCall().once();
		
		replay(mockBorrowUI);
		
		borrowCTL = new BorrowCTL(memberDao, bookDao, loanDao, mockBorrowUI);
		try {
			borrowCTL.cardScanned(1);
		} catch (BorrowerNotFoundException e) {
			fail();
		}
		//Reflect CTL state, member
	 	State ctlState = null;
	 	IMember ctlMember = null;
	 	try {
			ctlState = (State)borrowCTLStateField.get(borrowCTL);
			ctlMember = (IMember) borrowCTLMemberField.get(borrowCTL);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			fail();
		}
	 	assertEquals(State.BORROWING, ctlState);
	 	assertEquals(member, ctlMember);
		
		verify(mockBorrowUI);		
	}

	//11. BBUC_Op3 – bookScanned - Control – BookDAO – Book – Member
@SuppressWarnings("unchecked")
	//	Sig : bookScanned(bookID)
//	Throws BookNotFoundException
//	Pre: CTL.state = BORROWING UI.state = BORROWING
//	Post: book added to temporary loan list atLoanLimit updated
//	UI.displayBook called 
//  UI.displayPendingList called UI has ref to Book
//	UI has ref to tempLoanList
//	Rules: if atLoanLimit:
//	CTL.state = COMPLETED
//	UI.state = COMPLETED
	@Test
	public void testBookScanned() {
		
		//UI.displaybook
		mockBorrowUI.displayBook(EasyMock.anyObject(IBook.class));
		expectLastCall().times(Member.LOAN_LIMIT);
		//UI.displayPendingList
		mockBorrowUI.displayPendingList(EasyMock.anyObject(List.class));
		expectLastCall().once();
		//Check state updated if at loan limit
		mockBorrowUI.setState(State.COMPLETED);
		expectLastCall().once();
		
		replay(mockBorrowUI);
		
		//SetUp books for loan limit
		List<IBook> books = new ArrayList<IBook>();
		for (int i = 0; i <= Member.LOAN_LIMIT; i++) {
			IBook newBook = bookDao.addBook(author, title, callNo+i);
			books.add(newBook);
		}
		
		//Perform Actions
		borrowCTL = new BorrowCTL(memberDao, bookDao, loanDao, mockBorrowUI);
		try {
			borrowCTL.cardScanned(member.getID());
			for (IBook currentBook : books) {
				borrowCTL.bookScanned(currentBook.getID());
			}
		} catch (BorrowerNotFoundException e) {
			fail();
		} catch (BookNotFoundException e) {
			fail();
		}
		//Member.hasReachedLoanLimit
		assertTrue(member.hasReachedLoanLimit());
		List<ILoan> pendingLoans = loanDao.getPendingList(member);
		assertEquals(Member.LOAN_LIMIT, pendingLoans.size());
		
		//Reflect CTL state, member
	 	State ctlState = null;
	 	IMember ctlMember = null;
	 	try {
			ctlState = (State)borrowCTLStateField.get(borrowCTL);
			ctlMember = (IMember) borrowCTLMemberField.get(borrowCTL);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			fail();
		}
	 	assertEquals(State.COMPLETED, ctlState);
	 	assertEquals(member, ctlMember);
		
		//Verify
		verify(mockBorrowUI);
	}


	//8.  BBUC_Op7 – rejectPendingList - Control – LoanDAO – UI
//	Sig : rejectPendingList
//	CTL.state = COMPLETED
//	UI.state = COMPLETED 
//	Post: CTL.state = BORROWING
//	UI.state = BORROWING 
//	TempLoanList cleared/deleted
	@Test
	public void testRejectPendingList() throws BorrowerNotFoundException, BookNotFoundException {
		//Expect
		//assert set state borrowing called
		mockBorrowUI.setState(State.BORROWING);
		expectLastCall().once();
		mockBorrowUI.scanBook();
		expectLastCall().once();
		
		replay(mockBorrowUI);
		
		borrowCTL = new BorrowCTL(memberDao, bookDao, loanDao, mockBorrowUI);
		borrowCTL.cardScanned(member.getID());
		borrowCTL.bookScanned(book.getID());
		borrowCTL.rejectPendingList();
		
		verify(mockBorrowUI);
		//TODO: verify CTL state using reflection
		// Verify list cleared
		List<ILoan> pendingLoans = loanDao.getPendingList(member);
		assertTrue(pendingLoans == null || pendingLoans.size() == 0);
		
		//Reflect CTL state, member
	 	State ctlState = null;
	 	IMember ctlMember = null;
	 	try {
			ctlState = (State)borrowCTLStateField.get(borrowCTL);
			ctlMember = (IMember) borrowCTLMemberField.get(borrowCTL);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			fail();
		}
	 	assertEquals(State.BORROWING, ctlState);
	 	assertEquals(member, ctlMember);
	}

	//7.  BBUC_Op6 – confirmPendingList - Control – LoanDAO – Book – Member – UI
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
	public void testConfirmPendingList() throws BorrowerNotFoundException, BookNotFoundException {
		//assert UI setState CONFIRMED
		mockBorrowUI.setState(State.CONFIRMED);
		expectLastCall().once();
		//assert assert print loan slip
		mockBorrowUI.printLoanSlip();
		expectLastCall().once();
		
		replay(mockBorrowUI);
		
		borrowCTL = new BorrowCTL(memberDao, bookDao, loanDao, mockBorrowUI);
		borrowCTL.cardScanned(member.getID());
		borrowCTL.bookScanned(book.getID());
		List<ILoan> pendingLoans = loanDao.getPendingList(member);
		ILoan pendingLoan = pendingLoans.get(0);
		assertNotNull(pendingLoan);
		
		borrowCTL.confirmPendingList();
		
		verify(mockBorrowUI);
		
		List<ILoan> newPendingLoans = null;
		boolean exceptionCaught = false;
		try {
			newPendingLoans = loanDao.getPendingList(member);
		} catch (RuntimeException e) {
			exceptionCaught = true;
		}
		assertTrue(exceptionCaught);
		assertNull(newPendingLoans);
		
		List<ILoan> currentLoans = loanDao.listLoans();
		assertTrue(currentLoans.contains(pendingLoan));
		
		//Reflect CTL state, member
	 	State ctlState = null;
	 	IMember ctlMember = null;
	 	try {
			ctlState = (State)borrowCTLStateField.get(borrowCTL);
			ctlMember = (IMember) borrowCTLMemberField.get(borrowCTL);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			fail();
		}
	 	assertEquals(State.CONFIRMED, ctlState);
	 	assertEquals(member, ctlMember);
	}


}
