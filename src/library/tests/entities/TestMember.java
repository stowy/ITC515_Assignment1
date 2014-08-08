package library.tests.entities;

import static org.junit.Assert.*;

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
		fail("Not yet implemented");
	}
	
	@Test
	public void testHasReachedLoanLimit() {
		fail("Not yet implemented");
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
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetLoans() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testRemoveLoan() {
		fail("Not yet implemented");
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
