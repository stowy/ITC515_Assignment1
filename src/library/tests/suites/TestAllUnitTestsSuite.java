package library.tests.suites;

import library.tests.unit.controls.TestBorrowCTL;
import library.tests.unit.daos.TestBookDAO;
import library.tests.unit.daos.TestBookHelper;
import library.tests.unit.daos.TestLoanDAO;
import library.tests.unit.daos.TestLoanHelper;
import library.tests.unit.daos.TestMemberDAO;
import library.tests.unit.daos.TestMemberHelper;
import library.tests.unit.entities.TestBook;
import library.tests.unit.entities.TestLoan;
import library.tests.unit.entities.TestMember;
import library.tests.unit.uis.TestBorrowGuiUI;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestBook.class,
	TestMember.class,
	TestLoan.class,
	TestBookHelper.class,
	TestBookDAO.class,
	TestMemberHelper.class,
	TestMemberDAO.class,
	TestLoanHelper.class,
	TestLoanDAO.class,
	TestBorrowCTL.class,
	TestBorrowGuiUI.class
})


public class TestAllUnitTestsSuite {

}
