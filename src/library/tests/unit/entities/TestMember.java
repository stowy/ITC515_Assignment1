package library.tests.unit.entities;

import static org.junit.Assert.*;

import static org.easymock.EasyMock.*;
import java.util.List;

import library.classes.entities.Member;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.entities.MemberState;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMember {

	String first = "first";
	String last = "last";
	String phone = "phone";
	String email = "email";
	int id = 1;
	
	Member member;
	
	@Before
	public void setUp() throws Exception {
		member = new Member(first, last, phone, email, id);
	}

	@After
	public void tearDown() throws Exception {
		member = null;
	}

	@Test
	public void testValidConstructor() {
		Member testMember = new Member(first, last, phone, email, id);
		assertNotNull(testMember);
		assertTrue(testMember instanceof IMember);
	}
	
	@Test(expected=RuntimeException.class)
	public void testInvalidConstructor() {
		new Member(first, last, null, email, id);
	}
	
	@Test
	public void testHasOverDueLoans() {
		ILoan mockLoan = createMock(ILoan.class);
		expect(mockLoan.isOverDue()).andReturn(true);
		replay(mockLoan);
		member.addLoan(mockLoan);
		assertTrue(member.hasOverDueLoans());
		verify(mockLoan);
	}
	
	@Test
	public void testHasReachedLoanLimit() {
		for (int i =0; i < Member.LOAN_LIMIT; i ++) {
			member.addLoan(createMock(ILoan.class));
		}
		assertTrue(member.getLoans().size() == Member.LOAN_LIMIT);
		assertTrue(member.hasReachedLoanLimit());
		assertTrue(member.getState() == MemberState.BORROWING_DISALLOWED);
	}
	
	@Test
	public void testHasFinesPayable() {
		member.addFine(10);
		assertTrue(member.hasFinesPayable());
	}
	
	@Test
	public void testHasReachedFineLimit() {
		member.addFine(Member.FINE_LIMIT + 1);
		assertTrue(member.hasReachedFineLimit());
		assertTrue(member.getState() == MemberState.BORROWING_DISALLOWED);
	}
	
	@Test
	public void testGetFineAmount() {
		float fineAmount = 0;
		float actual = member.getFineAmount();
		assertEquals(fineAmount, actual, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddFineNegativeAmount() {
		member.addFine(-1);
	}
	
	@Test
	public void testAddFine() {
		float fineAmount = 10;
		member.addFine(fineAmount);
		float actual = member.getFineAmount();
		assertEquals(fineAmount, actual, 0);
	}

	@Test
	public void testPayFine() {
		float fineAmount = 10;
		member.addFine(fineAmount);
		float actual = member.getFineAmount();
		assertEquals(fineAmount, actual, 0);
		member.payFine(fineAmount);
		actual = member.getFineAmount();
		assertEquals(0, actual, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPayFineTooMuch() {
		member.payFine(1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPayFineNegative() {
		member.payFine(-1);
	}
	
	@Test
	public void testAddLoan() {
		ILoan mockLoan = createMock(ILoan.class);
		member.addLoan(mockLoan);
		List<ILoan> loans = member.getLoans();
		assertNotNull(loans);
		assertTrue(loans.contains(mockLoan));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddLoanBorrowingDisallowed() {
		member.addFine(Member.FINE_LIMIT + 1);
		assertTrue(member.getState() == MemberState.BORROWING_DISALLOWED);
		ILoan mockLoan = createMock(ILoan.class);
		member.addLoan(mockLoan);
	}
	
	@Test
	public void testGetLoans() {
		List<ILoan> actual = member.getLoans();
		assertNotNull(actual);
		assertTrue(actual.size() == 0);
	}
	
	@Test
	public void testRemoveLoan() {
		ILoan mockLoan = createMock(ILoan.class);
		member.addLoan(mockLoan);
		List<ILoan> loans = member.getLoans();
		assertNotNull(loans);
		assertTrue(loans.contains(mockLoan));
		member.removeLoan(mockLoan);
		assertFalse(member.getLoans().contains(mockLoan));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveLoanNotPresent() {
		ILoan mockLoan = createMock(ILoan.class);
		member.removeLoan(mockLoan);
	}
	
	@Test
	public void testGetState() {
		MemberState actual = member.getState();
		assertEquals(MemberState.BORROWING_ALLOWED, actual);
	}
	
	@Test
	public void testGetFirstName() {
		String actual = member.getFirstName();
		assertEquals(first, actual);
	}
	
	@Test
	public void testGetLastName() {
		String actual = member.getLastName();
		assertEquals(last, actual);
	}
	
	@Test
	public void testGetContactPhone() {
		String actual = member.getContactPhone();
		assertEquals(phone, actual);
	}
	
	@Test
	public void testGetEmailAddress() {
		String actual = member.getEmailAddress();
		assertEquals(email, actual);
	}
	
	@Test
	public void testGetID() {
		int actual = member.getID();
		assertEquals(id, actual);
	}

}
