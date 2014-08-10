package library.classes.daos;

import library.classes.entities.Member;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.IMember;

public class MemberHelper implements IMemberHelper {

	@Override
	public IMember makeMember(String firstName, String lastName,
			String contactPhone, String emailAddress, int id) throws IllegalArgumentException {
		return new Member(firstName, lastName, contactPhone, emailAddress, id);
	}

}
