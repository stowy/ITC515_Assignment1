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
		IMember foundMember = null;
		for (IMember member : members) {
			if (member.getID() == id) {
				foundMember = member;
			}
		}
		return foundMember;
	}

	@Override
	public List<IMember> listMembers() {
		return members;
	}

	@Override
	public List<IMember> findMembersByLastName(String lastName) {
		List<IMember> results = new ArrayList<IMember>();
		for (IMember member : members) {
			if (member.getLastName().equals(lastName)) {
				results.add(member);
			}
		}
		return results;
	}

	@Override
	public List<IMember> findMembersByEmailAddress(String emailAddress) {
		List<IMember> results = new ArrayList<IMember>();
		for (IMember member : members) {
			if (member.getEmailAddress().equals(emailAddress)) {
				results.add(member);
			}
		}
		return results;
	}

	@Override
	public List<IMember> findMembersByNames(String firstName, String lastName) {
		List<IMember> results = new ArrayList<IMember>();
		for (IMember member : members) {
			if (member.getLastName().equals(lastName) && member.getFirstName().equals(firstName)) {
				results.add(member);
			}
		}
		return results;
	}

}
