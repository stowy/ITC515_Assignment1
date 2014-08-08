package library.classes.entities;

import static library.classes.utils.VerificationUtil.*;
import java.util.List;

import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.entities.MemberState;

public class Member implements IMember {

	private String first;
	private String last;
	private String phone;
	private String email;
	private int id;
	
	
	public Member(String first, String last, String phone, String email, int id) throws IllegalArgumentException {
		assertNotNullOrEmpty(first);
		assertNotNullOrEmpty(last);
		assertNotNullOrEmpty(phone);
		assertNotNullOrEmpty(email);
		assertNonZeroPositive(id);
		
		this.first = first;
		this.last = last;
		this.phone = phone;
		this.email = email;
		this.id = id;
	}
	
	@Override
	public boolean hasOverDueLoans() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasReachedLoanLimit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasFinesPayable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasReachedFineLimit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getFineAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addFine(float fine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void payFine(float payment) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addLoan(ILoan loan) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ILoan> getLoans() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeLoan(ILoan loan) {
		// TODO Auto-generated method stub

	}

	@Override
	public MemberState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFirstName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLastName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContactPhone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEmailAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
