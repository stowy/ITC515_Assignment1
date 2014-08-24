package library.tests.integration.phase4;

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

public class TestBorrowCTL_UI_p4_13_15 {
		
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
		

//13. BBUC_Op4 – scanNext – Control – UI
//		Sig: scanNext
//		Pre: CTL.state = BORROWING
//		UI.state = BORROWING 
//		Post: CTL.state = BORROWING
//		UI.state = BORROWING UI.scanBook called
		@Test
		public void testScanNextContinueBorrowing() {
			
			
			mockBorrowUI.setState(State.BORROWING);
			expectLastCall().once();
			//assert that scan book is called
			mockBorrowUI.scanBook();
			expectLastCall().once();
			
			//Replay
			replay(mockBorrowUI);
			
			//Perform Actions
			//Set up preconditions
			borrowCTL = new BorrowCTL(memberDao, bookDao, loanDao, mockBorrowUI);
			try {
				borrowCTL.cardScanned(member.getID());
			} catch (BorrowerNotFoundException e) {
				fail();
			} 
			
			//Method under test
			borrowCTL.scanNext();
			
			
			//Verify
			verify(mockBorrowUI);
			
			//Reflect CTL state
		 	State ctlState = null;
		 	try {
				ctlState = (State)borrowCTLStateField.get(borrowCTL);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				fail();
			}
		 	assertEquals(State.BORROWING, ctlState);
		}
		
		@SuppressWarnings("unchecked")
		@Test
		public void testScanNextAtLoanLimit() {
			
			//Expect		
			//assert set state completed is called
			mockBorrowUI.setState(State.COMPLETED);
			expectLastCall().once();
			//assert that display completed list is called
			mockBorrowUI.displayCompletedList(EasyMock.anyObject(List.class));
			expectLastCall().once();
			
			//Replay
			replay(mockBorrowUI);
			
			//Perform Actions
			//Set up preconditions
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
			
			//Method under test
			borrowCTL.scanNext();
			
			//Verify
			verify(mockBorrowUI);
			
			//Reflect CTL state
		 	State ctlState = null;
		 	try {
				ctlState = (State)borrowCTLStateField.get(borrowCTL);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				fail();
			}
		 	assertEquals(State.COMPLETED, ctlState);
		}

		//14. BBUC_Op5 – scansCompleted – Control – UI 
//		Sig : scansCompleted
@SuppressWarnings("unchecked")
		//		Pre: CTL.state = BORROWING
//		UI.state = BORROWING
//		tempLoanList exists 
//		Post: CTL.state = COMPLETED
//		UI.state = COMPLETED 
//		UI.displayCompletedList called
		@Test
		public void testScansCompleted() {

			//assert set state completed is called
			mockBorrowUI.setState(State.COMPLETED);
			expectLastCall().once();
			//assert that display completed list is called
			mockBorrowUI.displayCompletedList(EasyMock.anyObject(List.class));
			expectLastCall().once();
			
			//Replay
			replay(mockBorrowUI);
			
			//Perform Actions
			//Set up preconditions
			borrowCTL = new BorrowCTL(memberDao, bookDao, loanDao, mockBorrowUI);
			try {
				borrowCTL.cardScanned(member.getID());
			} catch (BorrowerNotFoundException e) {
				fail();
			} 
			try {
				borrowCTL.bookScanned(book.getID());
			} catch (BookNotFoundException e) {
				fail();
			}
			
			//Method under test
			borrowCTL.scansCompleted();

			//Reflect CTL state
		 	State ctlState = null;
		 	try {
				ctlState = (State)borrowCTLStateField.get(borrowCTL);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				fail();
			}
		 	assertEquals(State.COMPLETED, ctlState);
		 	
			//Verify
			verify(mockBorrowUI);
		}

//15. BBUC_Op8 – borrowUCended – Control - UI
//		Sig : borrowUCended
//		CTL.state = CONFIRMED
//		UI.state = CONFIRMED Post: CTL.state = ENDED
//		UI.state = ENDED
		@Test
		public void testBorrowUCended() {
			
			//assert set state ended
			mockBorrowUI.setState(State.ENDED);
			expectLastCall().once();
			
			replay(mockBorrowUI);
			
			borrowCTL = new BorrowCTL(memberDao, bookDao, loanDao, mockBorrowUI);
			borrowCTL.borrowUCended();
			
			verify(mockBorrowUI);
			
			//Reflect CTL state
		 	State ctlState = null;
		 	try {
				ctlState = (State)borrowCTLStateField.get(borrowCTL);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				fail();
			}
		 	assertEquals(State.ENDED, ctlState);
		}
	
}
