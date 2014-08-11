package library.classes.daos;

import java.util.ArrayList;
import java.util.List;

import static library.classes.utils.VerificationUtil.*;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.IMember;

public class MemberDAO implements IMemberDAO {

	IMemberHelper memberHelper;
	List<IMember> members;
	int nextId;
	
	public MemberDAO(IMemberHelper memberHelper) throws IllegalArgumentException {
		assertNotNull(memberHelper);
		this.memberHelper = memberHelper;
		this.members = new ArrayList<IMember>();
		nextId = 1;
	}
	
	@Override
	public IMember addMember(String firstName, String lastName,
			String ContactPhone, String emailAddress) {
		IMember member = memberHelper.makeMember(firstName, lastName, ContactPhone, emailAddress, nextId);
		members.add(member);
		nextId++;
		return member;
	}

	@Override
	public IMember getMemberByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IMember> listMembers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IMember> findMembersByLastName(String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IMember> findMembersByEmailAddress(String emailAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IMember> findMembersByNames(String firstName, String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

}
