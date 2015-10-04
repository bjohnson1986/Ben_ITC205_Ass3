package test;

import static org.junit.Assert.*;
import library.BorrowUC_CTL;
import library.interfaces.EBorrowState;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
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

	private ILoan loan_;
	private IMember member0_, member1_, member2_, member3_, member4_, member5_;
	private IBook book0_, book1_, book2_, book3_, book4_, book5_;
	
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
        
        //Below: Setup what the mock return when called
        
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public final void testBorrowUC_CTL() {
		fail("Not yet implemented");
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
		fail("Not yet implemented");
	}

	@Test
	public final void testBookScanned() {
		fail("Not yet implemented");
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
		fail("Not yet implemented");
	}

	@Test
	public final void testLoansConfirmed() {
		fail("Not yet implemented");
	}

	@Test
	public final void testLoansRejected() {
		fail("Not yet implemented");
	}

}
