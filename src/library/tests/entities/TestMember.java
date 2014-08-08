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
		fail("Not yet implemented");
	}
	
	@Test
	public void testHasReachedFineLimit() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetFineAmount() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAddFine() {
		fail("Not yet implemented");
	}

	@Test
	public void testPayFine() {
		fail("Not yet implemented");
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
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetFirstName() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetLastName() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetContactPhone() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetEmailAddress() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetID() {
		fail("Not yet implemented");
	}

}
