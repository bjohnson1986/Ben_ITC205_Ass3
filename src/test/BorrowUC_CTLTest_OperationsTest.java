package test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import library.BorrowUC_CTL;
import library.daos.BookHelper;
import library.daos.BookMapDAO;
import library.daos.LoanHelper;
import library.daos.LoanMapDAO;
import library.daos.MemberHelper;
import library.daos.MemberMapDAO;
import library.entities.Member;
import library.hardware.CardReader;
import library.hardware.Display;
import library.hardware.Printer;
import library.hardware.Scanner;
import library.interfaces.EBorrowState;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BorrowUC_CTLTest_OperationsTest {

	private CardReader reader_;
	private Display display_;
	private Printer printer_;
	private Scanner scanner_;
	private IBookDAO bookDAO_;
	private ILoanDAO loanDAO_;
	private IMemberDAO memberDAO_;
	private IMember member0_, member1_, member2_, member3_, member4_;
	private IBook book0_, book1_, book2_, book3_, book4_, book5_;
	private Calendar cal_;
	
	@Before
	public void setUp() throws Exception {
		reader_ = new CardReader();
		display_ = new Display();
		printer_ = new Printer();
		scanner_ = new Scanner();
        bookDAO_ = new BookMapDAO(new BookHelper());
        loanDAO_ = new LoanMapDAO(new LoanHelper());
        memberDAO_ = new MemberMapDAO(new MemberHelper());
        cal_ = Calendar.getInstance();
        
        book0_ = bookDAO_.addBook("author0", "title0", "0");
        book1_ = bookDAO_.addBook("author1", "title0", "1");
        book2_ = bookDAO_.addBook("author2", "title0", "2");
        book3_ = bookDAO_.addBook("author3", "title0", "3");
        book4_ = bookDAO_.addBook("author4", "title0", "4");
        book5_ = bookDAO_.addBook("author5", "title5", "5");

        member0_ = memberDAO_.addMember("Fred", "Flintstone", "0412 345 679", "fred@gmail.com");
        
        member1_ = memberDAO_.addMember("Barny", "Rubble", "0412 345 678", "barny@gmail.com");
        member1_.addFine(5.0f);
        
        member2_ = memberDAO_.addMember("Wilma", "Flintstone", "0412 345 677", "wilma@gmail.com");
        ILoan loan = loanDAO_.createLoan(member2_, book0_);
        loanDAO_.commitLoan(loan);
        Date currentDate = new Date();
		cal_.setTime(currentDate);
		cal_.add(Calendar.DATE, ILoan.LOAN_PERIOD +1);
		Date overDueDate = cal_.getTime();
		loanDAO_.updateOverDueStatus(overDueDate);
        
        member3_ = memberDAO_.addMember("George", "Jetson", "0412 345 676", "george@gmail.com");
        member3_.addFine(10.0f);
        
        member4_ = memberDAO_.addMember("Peter", "Griffin", "0412 345 675", "peter@gmail.com");
        ILoan loan0 = loanDAO_.createLoan(member4_, book1_);
        loanDAO_.commitLoan(loan0);
        ILoan loan1 = loanDAO_.createLoan(member4_, book2_);
        loanDAO_.commitLoan(loan1);
        ILoan loan2 = loanDAO_.createLoan(member4_, book3_);
        loanDAO_.commitLoan(loan2);
        ILoan loan3 = loanDAO_.createLoan(member4_, book4_);
        loanDAO_.commitLoan(loan3);
        ILoan loan4 = loanDAO_.createLoan(member4_, book5_);
        loanDAO_.commitLoan(loan4);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testInitialise() {
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);	
	}
	
	@Test
	public final void nonRestrictedMemberCardSwipe() {
				
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
		
		controlClass.cardSwiped(1);
		assertEquals(controlClass.getState(), EBorrowState.SCANNING_BOOKS);
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
	}
	
	@Test
	public final void finesPayableMemberCardSwipe() {
				
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
		
		controlClass.cardSwiped(2);
		assertEquals(controlClass.getState(), EBorrowState.BORROWING_RESTRICTED);
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
	}
	
	@Test
	public final void overDueLoansMemberCardSwipe() {
				
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
		
		controlClass.cardSwiped(3);
		assertEquals(controlClass.getState(), EBorrowState.BORROWING_RESTRICTED);
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
	}
	
	@Test
	public final void fineLimitMemberCardSwipe() {
				
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
		
		controlClass.cardSwiped(4);
		assertEquals(controlClass.getState(), EBorrowState.BORROWING_RESTRICTED);
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
	}
	
	@Test
	public final void loanLimitMemberCardSwipe() {
				
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
		
		controlClass.cardSwiped(5);
		assertEquals(controlClass.getState(), EBorrowState.BORROWING_RESTRICTED);
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
	}

}
