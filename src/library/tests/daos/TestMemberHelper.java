package library.tests.daos;

import static org.junit.Assert.*;
import library.classes.daos.MemberHelper;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMemberHelper {

	String first = "first";
	String last = "last";
	String phone = "phone";
	String email = "email";
	int id = 1;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		MemberHelper helper = new MemberHelper();
		IMember member = helper.makeMember(first, last, phone, email, id);
		assertNotNull(member);
		assertTrue(member instanceof IMember);
	}

}
