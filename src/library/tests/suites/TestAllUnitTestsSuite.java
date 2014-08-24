package library.tests.suites;

import library.tests.controls.TestBorrowCTL;
import library.tests.daos.TestBookDAO;
import library.tests.daos.TestBookHelper;
import library.tests.daos.TestLoanDAO;
import library.tests.daos.TestLoanHelper;
import library.tests.daos.TestMemberDAO;
import library.tests.daos.TestMemberHelper;
import library.tests.entities.TestBook;
import library.tests.entities.TestLoan;
import library.tests.entities.TestMember;

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
	TestBorrowCTL.class
})


public class TestAllUnitTestsSuite {

}
