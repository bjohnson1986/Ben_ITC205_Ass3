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
	private IMember member0_, member1_, member2_, member3_, member4_;
	private IBook book0_, book1_, book2_, book3_, book4_, book5_, book6_;
	
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
        mock(IMember.class);
        
        
        book0_ = mock(IBook.class);
        book1_ = mock(IBook.class);
        book2_ = mock(IBook.class);
        book3_ = mock(IBook.class);
        book4_ = mock(IBook.class);
        book5_ = mock(IBook.class);
        book6_ = mock(IBook.class);
        
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
        
        when(memberDAO_.getMemberByID(3)).thenReturn(member3_);
        when(member3_.getFirstName()).thenReturn("George");
        when(member3_.getLastName()).thenReturn("Jetson");
        when(member3_.getLoans()).thenReturn(emptyLoanList);
        when(member3_.getFineAmount()).thenReturn(0.0f);
        when(member3_.hasOverDueLoans()).thenReturn(false);
        when(member3_.hasFinesPayable()).thenReturn(false);
        when(member3_.hasReachedFineLimit()).thenReturn(true);
        when(member3_.hasReachedLoanLimit()).thenReturn(false);
        
        when(memberDAO_.getMemberByID(4)).thenReturn(member4_);
        when(member4_.getFirstName()).thenReturn("Peter");
        when(member4_.getLastName()).thenReturn("Griffin");
        when(member4_.getLoans()).thenReturn(emptyLoanList);
        when(member4_.getFineAmount()).thenReturn(0.0f);
        when(member4_.hasOverDueLoans()).thenReturn(false);
        when(member4_.hasFinesPayable()).thenReturn(false);
        when(member4_.hasReachedFineLimit()).thenReturn(false);
        when(member4_.hasReachedLoanLimit()).thenReturn(true);
        
        when(memberDAO_.getMemberByID(5)).thenReturn(null);
        
        when(bookDAO_.getBookByID(0)).thenReturn(book0_);
        when(book0_.getID()).thenReturn(0);
        when(book0_.getAuthor()).thenReturn("Author");
        when(book0_.getTitle()).thenReturn("Title");
        when(book0_.getAuthor()).thenReturn("CallNumber");
        when(book0_.getState()).thenReturn(bookState_.AVAILABLE);
        
        when(bookDAO_.getBookByID(1)).thenReturn(book1_);
        when(book1_.getID()).thenReturn(1);
        when(book1_.getAuthor()).thenReturn("Author");
        when(book1_.getTitle()).thenReturn("Title");
        when(book1_.getAuthor()).thenReturn("CallNumber");
        when(book1_.getState()).thenReturn(bookState_.AVAILABLE);
        
        when(bookDAO_.getBookByID(2)).thenReturn(book2_);
        when(book2_.getID()).thenReturn(2);
        when(book2_.getAuthor()).thenReturn("Author");
        when(book2_.getTitle()).thenReturn("Title");
        when(book2_.getAuthor()).thenReturn("CallNumber");
        when(book2_.getState()).thenReturn(bookState_.AVAILABLE);
        
        when(bookDAO_.getBookByID(3)).thenReturn(book3_);
        when(book3_.getID()).thenReturn(3);
        when(book3_.getAuthor()).thenReturn("Author");
        when(book3_.getTitle()).thenReturn("Title");
        when(book3_.getAuthor()).thenReturn("CallNumber");
        when(book3_.getState()).thenReturn(bookState_.AVAILABLE);
        
        when(bookDAO_.getBookByID(4)).thenReturn(book4_);
        when(book4_.getID()).thenReturn(4);
        when(book4_.getAuthor()).thenReturn("Author");
        when(book4_.getTitle()).thenReturn("Title");
        when(book4_.getAuthor()).thenReturn("CallNumber");
        when(book4_.getState()).thenReturn(bookState_.AVAILABLE);
        
        when(bookDAO_.getBookByID(5)).thenReturn(book5_);
        when(book5_.getID()).thenReturn(5);
        when(book5_.getAuthor()).thenReturn("Author");
        when(book5_.getTitle()).thenReturn("Title");
        when(book5_.getAuthor()).thenReturn("CallNumber");
        when(book5_.getState()).thenReturn(bookState_.AVAILABLE);
        
        when(bookDAO_.getBookByID(6)).thenReturn(book6_);
        when(book6_.getID()).thenReturn(6);
        when(book6_.getAuthor()).thenReturn("Author");
        when(book6_.getTitle()).thenReturn("Title");
        when(book6_.getAuthor()).thenReturn("CallNumber");
        when(book6_.getState()).thenReturn(bookState_.ON_LOAN);
        
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
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
		
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
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
		
		controlClass.cardSwiped(0);
		assertEquals(controlClass.getState(), EBorrowState.SCANNING_BOOKS);
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
	}

	@Test
	public final void testBookScanned() {
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
		
		controlClass.cardSwiped(0);
		assertEquals(controlClass.getState(), EBorrowState.SCANNING_BOOKS);
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
		
		controlClass.bookScanned(0);
		assertEquals(1, controlClass.getScanCount());
		assertEquals(1, controlClass.getLoanList().size());
	}
	
	@Test
	public final void sameBookScanned() {
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
		
		controlClass.cardSwiped(0);
		assertEquals(controlClass.getState(), EBorrowState.SCANNING_BOOKS);
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
		
		controlClass.bookScanned(0);
		assertEquals(1, controlClass.getScanCount());
		assertEquals(1, controlClass.getLoanList().size());
		
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
		
		assertEquals(0, controlClass.getScanCount());
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
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
		
		controlClass.cardSwiped(0);
		assertEquals(controlClass.getState(), EBorrowState.SCANNING_BOOKS);
		
		assertEquals(0, controlClass.getScanCount());
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
		
		assertEquals(0, controlClass.getScanCount());
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
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
		
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

	@Test
	public final void reachedFineLimit() {
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
	public final void reachedLoanLimit() {
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
	
	@Test(expected=RuntimeException.class)
	public final void invalidCardSwiped() {
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
	
	@Test
	public final void maxItemScanned() {
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		controlClass.cardSwiped(0);
		assertEquals(controlClass.getState(), EBorrowState.SCANNING_BOOKS);
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
		
		controlClass.bookScanned(0);
		assertEquals(1, controlClass.getScanCount());
		assertEquals(1, controlClass.getLoanList().size());
		
		controlClass.bookScanned(1);
		assertEquals(2, controlClass.getScanCount());
		assertEquals(2, controlClass.getLoanList().size());
		
		controlClass.bookScanned(2);
		assertEquals(3, controlClass.getScanCount());
		assertEquals(3, controlClass.getLoanList().size());
		
		controlClass.bookScanned(3);
		assertEquals(4, controlClass.getScanCount());
		assertEquals(4, controlClass.getLoanList().size());
		
		controlClass.bookScanned(4);
		assertEquals(5, controlClass.getScanCount());
		assertEquals(5, controlClass.getLoanList().size());
		
		assertEquals(controlClass.getState(), EBorrowState.CONFIRMING_LOANS);
	}
	
	@Test
	public final void scanUnavailableBook() {
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		controlClass.cardSwiped(0);
		assertEquals(controlClass.getState(), EBorrowState.SCANNING_BOOKS);
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
		
		controlClass.bookScanned(6);
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
		
		assertEquals(controlClass.getState(), EBorrowState.SCANNING_BOOKS);
	}
	
	@Test
	public final void notReadyToScanBook() {
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
		
		controlClass.bookScanned(0);
		assertEquals(0, controlClass.getScanCount());
		assertEquals(0, controlClass.getLoanList().size());
	}
	
	@Test(expected=RuntimeException.class)
	public final void notReadyToConfirm() {
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		controlClass.cardSwiped(0);
		assertEquals(controlClass.getState(), EBorrowState.SCANNING_BOOKS);
		
		controlClass.bookScanned(0);
		assertEquals(1, controlClass.getScanCount());
		assertEquals(1, controlClass.getLoanList().size());
		
		controlClass.loansConfirmed();
	}
	
	@Test(expected=RuntimeException.class)
	public final void noBooksScanned() {
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
		
		controlClass.initialise();
		assertEquals(controlClass.getState(), EBorrowState.INITIALIZED);
		
		controlClass.cardSwiped(0);
		assertEquals(controlClass.getState(), EBorrowState.SCANNING_BOOKS);
			
		controlClass.scansCompleted();
	}
	
	@Test(expected=RuntimeException.class)
	public final void swipeCardBeforeInitialize() {
		BorrowUC_CTL controlClass = new BorrowUC_CTL(reader_, scanner_, printer_, display_, bookDAO_, loanDAO_, memberDAO_);
		assertEquals(controlClass.getState(), EBorrowState.CREATED);
			
		controlClass.cardSwiped(0);			
	}
}
