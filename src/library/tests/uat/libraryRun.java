package library.tests.uat;

import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
import java.util.List;

import library.classes.controls.BorrowCTL;
import library.classes.daos.BookDAO;
import library.classes.daos.BookHelper;
import library.classes.daos.LoanDAO;
import library.classes.daos.LoanHelper;
import library.classes.daos.MemberDAO;
import library.classes.daos.MemberHelper;
//import library.classes.entities.Loan;
//import library.classes.entities.Member;
//import library.classes.exceptions.BookNotFoundException;
//import library.classes.exceptions.BorrowerNotFoundException;
import library.classes.uis.BorrowGuiUI;
import library.interfaces.entities.IBook;
//import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class libraryRun {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		MemberDAO memberDao = new MemberDAO(new MemberHelper());
		BookDAO bookDao = new BookDAO(new BookHelper());
		LoanDAO loanDao = new LoanDAO(new LoanHelper());
		
		IMember member = memberDao.addMember("Sam", "Stow", "03 5444 4444", "sam@mail.com");
		
		//User at fine limit
//		member.addFine(Member.FINE_LIMIT + 1);
		List<IBook> books = new ArrayList<IBook>();
		
		books.add(bookDao.addBook("Tim Smith", "Wonderland", "0001"));
		books.add(bookDao.addBook("Tim Smith", "Wonderland 2", "0002"));
		books.add(bookDao.addBook("Tim Smith", "Wonderland 3", "0003"));
		books.add(bookDao.addBook("Sian Whiley", "Love is Forever", "0004"));
		books.add(bookDao.addBook("Sian Whiley", "Love is Forever Again", "0005"));
		
		//User has reached loan limit
//		Calendar calendar = Calendar.getInstance();
//		Date borrowDate = calendar.getTime();
//		calendar.add(Calendar.DAY_OF_YEAR, ILoan.LOAN_PERIOD);
//		Date dueDate = calendar.getTime();
//		loanDao.createNewPendingList(member);
//		for (IBook book : books) {
//			loanDao.createPendingLoan(member, book, borrowDate, dueDate);
//		}
		//User has existing loans
//		for (int i = 0; i <= 2; i++) {
//			IBook book = books.get(i);
//			loanDao.createPendingLoan(member, book, borrowDate, dueDate);
//		}
//		loanDao.commitPendingLoans(member);
		
		//User has overdue loans
//		calendar.add(Calendar.DAY_OF_YEAR, Loan.LOAN_PERIOD + 1);
//		Date overDueDate = calendar.getTime();
//		loanDao.createNewPendingList(member);
//		loanDao.createPendingLoan(member, books.get(0), borrowDate, dueDate);
//		loanDao.commitPendingLoans(member);
//		loanDao.updateOverDueStatus(overDueDate);
		
		BorrowGuiUI ui = new BorrowGuiUI();
		BorrowCTL ctl = new BorrowCTL(memberDao, 
				bookDao, loanDao, ui);
	}

}
