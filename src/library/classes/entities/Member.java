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
	
	private float finesOwing;
	
	private MemberState state;
	
	
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
		this.state = MemberState.BORROWING_ALLOWED;
		this.finesOwing = 0;
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
		return this.finesOwing > 0;
	}

	@Override
	public boolean hasReachedFineLimit() {
		return this.finesOwing >= FINE_LIMIT;
	}

	@Override
	public float getFineAmount() {
		return this.finesOwing;
	}

	@Override
	public void addFine(float fine) throws IllegalArgumentException {
		assertPositive(fine);
		this.finesOwing += fine;
		if (hasReachedFineLimit()) {
			this.state = MemberState.BORROWING_DISALLOWED;
		}
	}

	@Override
	public void payFine(float payment) {
		assertPositive(payment);
		assertLess(payment, this.finesOwing);
		this.finesOwing -= payment;
		if (this.state == MemberState.BORROWING_DISALLOWED && !hasReachedFineLimit() && !hasReachedLoanLimit()) {
			this.state = MemberState.BORROWING_ALLOWED;
		}
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
		return this.state;
	}

	@Override
	public String getFirstName() {
		return this.first;
	}

	@Override
	public String getLastName() {
		return this.last;
	}

	@Override
	public String getContactPhone() {
		return this.phone;
	}

	@Override
	public String getEmailAddress() {
		return this.email;
	}

	@Override
	public int getID() {
		return this.id;
	}

}
