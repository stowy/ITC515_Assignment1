package library.tests.suites;

import library.tests.integration.phase1.TestBook_Loan_p1_1;
import library.tests.integration.phase1.TestLoan_Book_Member_p1_3;
import library.tests.integration.phase1.TestMember_Loan_p1_2;
import library.tests.integration.phase2.TestBook_BookDAO_BookHelper_p2_4;
import library.tests.integration.phase2.TestLoan_LoanDAO_LoanHelper_p2_6;
import library.tests.integration.phase2.TestMember_MemberDAO_MemberHelper_p2_5;
import library.tests.integration.phase3.TestBorrowCTL_DAOs_p3_7_12;
import library.tests.integration.phase4.TestBorrowCTL_UI_p4_13_15;
import library.tests.integration.phase5.TestBorrowGuiUI_BorrowCTL;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestBook_Loan_p1_1.class,
	TestMember_Loan_p1_2.class,
	TestLoan_Book_Member_p1_3.class,
	TestBook_BookDAO_BookHelper_p2_4.class,
	TestMember_MemberDAO_MemberHelper_p2_5.class,
	TestLoan_LoanDAO_LoanHelper_p2_6.class,
	TestBorrowCTL_DAOs_p3_7_12.class,
	TestBorrowCTL_UI_p4_13_15.class,
	TestBorrowGuiUI_BorrowCTL.class
})


public class TestAllIntegrationTestsSuite {

}
