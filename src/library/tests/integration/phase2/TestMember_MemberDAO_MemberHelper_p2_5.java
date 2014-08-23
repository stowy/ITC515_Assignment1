package library.tests.integration.phase2;

import static org.junit.Assert.*;

import java.util.List;

import library.classes.daos.MemberDAO;
import library.classes.daos.MemberHelper;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMember_MemberDAO_MemberHelper_p2_5 {

	private String firstName = "firstName";
	private String lastName = "lastName";
	private String contactPhone = "contactPhone";
	private String email = "email";
	
	private IMemberHelper memberHelper;
	private IMemberDAO memberDao;
	
	@Before
	public void setUp() throws Exception {
		memberHelper = new MemberHelper();
		memberDao = new MemberDAO(memberHelper);
	}

	@After
	public void tearDown() throws Exception {
		memberHelper = null;
		memberDao = null;
	}

	@Test
	public void testAddMember() {
		IMember member = memberDao.addMember(firstName, lastName, contactPhone, email);

		assertNotNull(member);
		assertEquals(firstName, member.getFirstName());
	}
	
	@Test
	public void testGetMemberByID() {
		int id = 1;
		IMember member = memberDao.addMember(firstName, lastName, contactPhone, email);
 		IMember actual = memberDao.getMemberByID(id);
 		assertEquals(member, actual);
	}
	
	@Test
	public void testFindMembersByLastName() {
		IMember member = memberDao.addMember(firstName, lastName, contactPhone, email);
 		List<IMember> actual = memberDao.findMembersByLastName(lastName);
 		assertNotNull(actual);
 		assertTrue(actual instanceof List);
 		assertTrue(actual.contains(member));
	}

	@Test
	public void testFindMembersByEmailAddress() {
		IMember member = memberDao.addMember(firstName, lastName, contactPhone, email);
 		List<IMember> actual = memberDao.findMembersByEmailAddress(email);
 		assertNotNull(actual);
 		assertTrue(actual instanceof List);
 		assertTrue(actual.contains(member));
	}
	
	@Test
	public void testFindMembersByNames() {
		IMember member = memberDao.addMember(firstName, lastName, contactPhone, email);
 		List<IMember> actual = memberDao.findMembersByNames(firstName, lastName);
 		assertNotNull(actual);
 		assertTrue(actual instanceof List);
 		assertTrue(actual.contains(member));
	}
	
}
