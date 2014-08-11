package library.tests.daos;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.List;

import library.classes.daos.MemberDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.IMember;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMemberDAO {

	private String firstName = "firstName";
	private String lastName = "lastName";
	private String contactPhone = "contactPhone";
	private String email = "email";
	
	private IMemberHelper mockHelper;
	private IMemberDAO memberDao;
	
	@Before
	public void setUp() throws Exception {
		mockHelper = createMock(IMemberHelper.class);
		memberDao = new MemberDAO(mockHelper);
	}

	@After
	public void tearDown() throws Exception {
		mockHelper = null;
		memberDao = null;
	}

	@Test
	public void testValidConstructor() {
		IMemberHelper helper = createMock(IMemberHelper.class);
		MemberDAO memberDao = new MemberDAO(helper);
		assertNotNull(memberDao);
		assertTrue(memberDao instanceof IMemberDAO);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidConstructor() {
		new MemberDAO(null);
	}
	
	@Test
	public void testAddMember() {
		IMember mockMember = createMock(IMember.class);
 		expect(mockHelper.makeMember(EasyMock.anyString(), EasyMock.anyString(), EasyMock.anyString(), EasyMock.anyString(), EasyMock.anyInt())).andReturn(mockMember);
 		replay(mockHelper);
		IMember member = memberDao.addMember(firstName, lastName, contactPhone, email);
		verify(mockHelper);
		assertEquals(mockMember, member);
	}
	
	@Test
	public void testGetMemberByID() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testListMembers() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFindMembersByLastName() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindMembersByEmailAddress() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFindMembersByNames() {
		fail("Not yet implemented");
	}

}
