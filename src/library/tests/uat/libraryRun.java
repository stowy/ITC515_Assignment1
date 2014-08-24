package library.tests.uat;

import library.classes.controls.BorrowCTL;
import library.classes.daos.BookDAO;
import library.classes.daos.BookHelper;
import library.classes.daos.LoanDAO;
import library.classes.daos.LoanHelper;
import library.classes.daos.MemberDAO;
import library.classes.daos.MemberHelper;
import library.classes.uis.BorrowGuiUI;
import library.interfaces.controls.IBorrowCTL.State;

public class libraryRun {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MemberDAO memberDao = new MemberDAO(new MemberHelper());
		BookDAO bookDao = new BookDAO(new BookHelper());
		LoanDAO loanDao = new LoanDAO(new LoanHelper());
		memberDao.addMember("Sam", "Stow", "000", "sfsd");
		bookDao.addBook("author", "title", "00002");
		
		BorrowGuiUI ui = new BorrowGuiUI();
		BorrowCTL ctl = new BorrowCTL(memberDao, 
				bookDao, loanDao, ui);
		ui.initialise(ctl);
		ui.setState(State.STARTED);
	}

}
