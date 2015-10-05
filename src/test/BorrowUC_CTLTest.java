package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import library.BorrowUC_CTL;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import stubs.CardReaderStub;
import stubs.DisplayStub;
import stubs.PrinterStub;
import stubs.ScannerStub;


public class BorrowUC_CTLTest {

	private CardReaderStub reader_;
	private DisplayStub display_;
	private PrinterStub printer_;
	private ScannerStub scanner_;
	private IBookDAO bookDAO_;
	private ILoanDAO loanDAO_;
	private IMemberDAO memberDAO_;
	private EBookState bookState_;

	private ILoan loan_;
	private IMember member0_, member1_, member2_, member3_, member4_, member5_;
	private IBook book0_, book1_, book2_, book3_, book4_, book5_;
	
	@SuppressWarnings("static-access")
	@Before
	public void setUp() throws Exception {
		
		//Hardware stubs
        display_ = new DisplayStub();
        reader_ = new CardReaderStub();
        printer_ = new PrinterStub();
        scanner_ = new ScannerStub();
        
        //Mocks
        bookDAO_ = mock(IBookDAO.class);
        loanDAO_= mock(ILoanDAO.class);
        memberDAO_ = mock(IMemberDAO.class);
        
        loan_ = mock(ILoan.class);
        
        member0_ = mock(IMember.class);
        member1_ = mock(IMember.class);
        member2_ = mock(IMember.class);
        member3_ = mock(IMember.class);
        member4_ = mock(IMember.class);
        member5_ = mock(IMember.class);
        
        
        book0_ = mock(IBook.class);
        book1_ = mock(IBook.class);
        book2_ = mock(IBook.class);
        book3_ = mock(IBook.class);
        book4_ = mock(IBook.class);
        book5_ = mock(IBook.class);
        
        List<ILoan> emptyLoanList = new ArrayList<>();
        
        //Below: Setup what the mock return when called
        when(memberDAO_.getMemberByID(0)).thenReturn(member0_);
        when(member0_.getFirstName()).thenReturn("Fred");
        when(member0_.getLastName()).thenReturn("Flintstone");
        when(member0_.getLoans()).thenReturn(emptyLoanList);
        when(member0_.getFineAmount()).thenReturn(0.0f);
        when(member0_.hasOverDueLoans()).thenReturn(false);
        when(member0_.hasFinesPayable()).thenReturn(false);
        when(member0_.hasReachedFineLimit()).thenReturn(false);
        when(member0_.hasReachedLoanLimit()).thenReturn(false);
        
        when(memberDAO_.getMemberByID(1)).thenReturn(member1_);
        when(member1_.getFirstName()).thenReturn("Barny");
        when(member1_.getLastName()).thenReturn("Rubble");
        when(member1_.getLoans()).thenReturn(emptyLoanList);
        when(member1_.getFineAmount()).thenReturn(0.0f);
        when(member1_.hasOverDueLoans()).thenReturn(true);
        when(member1_.hasFinesPayable()).thenReturn(false);
        when(member1_.hasReachedFineLimit()).thenReturn(false);
        when(member1_.hasReachedLoanLimit()).thenReturn(false);
        
        when(memberDAO_.getMemberByID(2)).thenReturn(member2_);
        when(member2_.getFirstName()).thenReturn("Wilma");
        when(member2_.getLastName()).thenReturn("Flintstone");
        when(member2_.getLoans()).thenReturn(emptyLoanList);
        when(member2_.getFineAmount()).thenReturn(0.0f);
        when(member2_.hasOverDueLoans()).thenReturn(false);
        when(member2_.hasFinesPayable()).thenReturn(true);
        when(member2_.hasReachedFineLimit()).thenReturn(false);
        when(member2_.hasReachedLoanLimit()).thenReturn(false);
        
        when(bookDAO_.getBookByID(0)).thenReturn(book0_);
        when(book0_.getID()).thenReturn(0);
        when(book0_.getAuthor()).thenReturn("Author");
        when(book0_.getTitle()).thenReturn("Title");
        when(book0_.getAuthor()).thenReturn("CallNumber");
        when(book0_.getState()).thenReturn(bookState_.AVAILABLE);
        
        when(loanDAO_.createLoan(member0_, book0_)).thenReturn(loan_);
        
	}

	@After
	public void tearDown() throws Exception {
	}
	

	@Test
	public final void testConstructor()
	{
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
	}
	
	@Test
	public final void testInitialise() {
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		assertEquals(reader_.getEnabled(), true);
		assertEquals(scanner_.getEnabled(), false);
		assertEquals(display_.getId(), "Borrow UI");		
	}

	@Test
	public final void testCardSwiped() {
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		controlClass.cardSwiped(0);
		assertEquals(controlClass.getState(), EBorrowState.SCANNING_BOOKS);
	}

	@Test
	public final void testBookScanned() {
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		controlClass.cardSwiped(0);
		assertEquals(controlClass.getState(), EBorrowState.SCANNING_BOOKS);
		
		assertEquals(0, controlClass.getLoanList().size());
		
		controlClass.bookScanned(0);
		assertEquals(1, controlClass.getScanCount());
		assertEquals(1, controlClass.getLoanList().size());
	}
	
	public final void sameBookScanned() {
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		controlClass.cardSwiped(0);
		assertEquals(controlClass.getState(), EBorrowState.SCANNING_BOOKS);
		
		assertEquals(0, controlClass.getLoanList().size());
		
		controlClass.bookScanned(0);
		controlClass.bookScanned(0);
		assertEquals(1, controlClass.getScanCount());
		assertEquals(1, controlClass.getLoanList().size());
	}

	@Test
	public final void testCancelled() {
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		controlClass.cancelled();
		assertEquals(controlClass.getState(), EBorrowState.CANCELLED);
	}

	@Test
	public final void testScansCompleted() {
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		controlClass.cardSwiped(0);
		assertEquals(controlClass.getState(), EBorrowState.SCANNING_BOOKS);
		
		assertEquals(0, controlClass.getLoanList().size());
		
		controlClass.bookScanned(0);
		assertEquals(1, controlClass.getScanCount());
		assertEquals(1, controlClass.getLoanList().size());
		
		controlClass.scansCompleted();
		assertEquals(controlClass.getState(), EBorrowState.CONFIRMING_LOANS);	
	}

	@Test
	public final void testLoansConfirmed() {
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		controlClass.cardSwiped(0);
		assertEquals(controlClass.getState(), EBorrowState.SCANNING_BOOKS);
		
		assertEquals(0, controlClass.getLoanList().size());
		
		controlClass.bookScanned(0);
		assertEquals(1, controlClass.getScanCount());
		assertEquals(1, controlClass.getLoanList().size());
		
		controlClass.scansCompleted();
		assertEquals(controlClass.getState(), EBorrowState.CONFIRMING_LOANS);
		
		controlClass.loansConfirmed();
		assertEquals(controlClass.getState(), EBorrowState.COMPLETED);
		assertEquals(false, reader_.getEnabled());
		assertEquals(false, scanner_.getEnabled());
	}

	@Test
	public final void testLoansRejected() {
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		controlClass.cardSwiped(0);
		assertEquals(controlClass.getState(), EBorrowState.SCANNING_BOOKS);
		
		assertEquals(0, controlClass.getLoanList().size());
		
		controlClass.bookScanned(0);
		assertEquals(1, controlClass.getScanCount());
		assertEquals(1, controlClass.getLoanList().size());
		
		controlClass.scansCompleted();
		assertEquals(controlClass.getState(), EBorrowState.CONFIRMING_LOANS);
		
		controlClass.loansRejected();
		assertEquals(controlClass.getState(), EBorrowState.SCANNING_BOOKS);
		assertEquals(false, reader_.getEnabled());
		assertEquals(true, scanner_.getEnabled());
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
	}
	
	@Test
	public final void overDueLoans() {
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		controlClass.cardSwiped(1);
		assertEquals(controlClass.getState(), EBorrowState.BORROWING_RESTRICTED);
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
	}
	
	@Test
	public final void finesPayable() {
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		controlClass.cardSwiped(2);
		assertEquals(controlClass.getState(), EBorrowState.BORROWING_RESTRICTED);
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
	}

}
