package test;

import library.entities.Book;
import library.interfaces.entities.EBookState;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BookTest {

	private EBookState eBookState_;
	
	@Before
	public void setUp() throws Exception {
	
	}

	@After
	public void tearDown() throws Exception {
	}

	//Book's author is null
	@Test(expected=IllegalArgumentException.class)
	public void testBookConstructorNullAuthor() {
		@SuppressWarnings("unused")
		Book book = new Book(null, "test", "123", 5);
	}
	
	//Book's author is an empty string
	@Test(expected=IllegalArgumentException.class)
	public void testBookConstructorEmptyAuthor() {
		@SuppressWarnings("unused")
		Book book = new Book("", "test", "123", 6);
	}
	
	//Book's title is null
	@Test(expected=IllegalArgumentException.class)
	public void testBookConstructorNullTitle() {
		@SuppressWarnings("unused")
		Book book = new Book("test", null, "123", 7);
	}
	
	//Book's title is an empty string
	@Test(expected=IllegalArgumentException.class)
	public void testBookConstructorEmptyTitle() {
		@SuppressWarnings("unused")
		Book book = new Book("test", null, "123", 8);
	}
	
	//Book's call number is null
	@Test(expected=IllegalArgumentException.class)
	public void testBookConstructorNullCallId() {
		@SuppressWarnings("unused")
		Book book = new Book("test", "test", null, 9);
	}
	
	//Book's call number is an empty string
	@Test(expected=IllegalArgumentException.class)
	public void testBookConstructorEmptyCallId() {
		@SuppressWarnings("unused")
		Book book = new Book("test", "test", "", 10);
	}
	
	//Book's id is negative
	@Test(expected=IllegalArgumentException.class)
	public void testBookConstructorNegativeBookId() {
		@SuppressWarnings("unused")
		Book book = new Book("test", "test", "test", -1);
	}
	
	//Book's id is 0
	@Test(expected=IllegalArgumentException.class)
	public void testBookConstructorZeroBookId() {
		@SuppressWarnings("unused")
		Book book = new Book("test", "test", "test", 0);
	}
	
	//Attempt loan for AVAILABLE book
	@SuppressWarnings("static-access")
	@Test
	public void testBorrowAvailable() {
		LoanStub loan = new LoanStub();
		Book book = new Book("test", "test", "test", 100);
		assertTrue(book.getState() == eBookState_.AVAILABLE);//Verify book's status is available
		book.borrow(loan);//Borrow the book
		assertTrue(book.getState() == eBookState_.ON_LOAN);//Verify book's status is on loan
	}

	//Attempt loan for book ON_LOAN
	@Test(expected=RuntimeException.class)
	public void testBorrowAlreadyOnLoan() {
		LoanStub existingLoan = new LoanStub();
		LoanStub newLoan = new LoanStub();
		Book book = new Book("test", "test", "test", 11);
		book.borrow(existingLoan);//Borrow the book
		book.borrow(newLoan);//Attempt new loan for same book
	}
	
	//Attempt loan for LOST book
	@Test(expected=RuntimeException.class)
	public void testBorrowLostBook() {	
		LoanStub loan = new LoanStub();
	    Book book = new Book("test", "test", "test", 13);
	    book.lose();//Book is lost
	    book.borrow(loan);//Attempt new loan for same book
	}	
	
	//Attempt loan for DAMAGED book
	@Test(expected=RuntimeException.class)
	public void testBorrowDamagedBook() {	
		LoanStub oldLoan = new LoanStub();
		LoanStub newLoan = new LoanStub();
	    Book book = new Book("test", "test", "test", 14);
	    book.borrow(oldLoan);//Borrow the book
	    book.returnBook(true);//Return damaged
	    book.borrow(newLoan);//Attempt new loan
	}
	
	//Attempt loan for DISPOSED book
	@Test(expected=RuntimeException.class)
	public void testBorrowDisposedBook() {	
		LoanStub loan = new LoanStub();
	    Book book = new Book("test", "test", "test", 15);
	    book.dispose();
	    book.borrow(loan); 
	}
	
	//Check if book returns the associated loan
	@Test
	public void testGetLoanWhenBookOnLoan() {	
		LoanStub loan = new LoanStub();
	    Book book = new Book("test", "test", "test", 16);
	    book.borrow(loan); 
	    assertEquals("", loan, book.getLoan());//Verify loan association
	}
	
	//Check if book returns a null loan when it has never been borrowed
	@Test
	public void testGetNullLoanForNeverBorrowedBook() {	
	    Book book = new Book("test", "test", "test", 17);
	    assertEquals("", null, book.getLoan());//Verify no loan associated
	}
	
	//Check if book returns a null loan when it has been borrowed and returned without damage
	@Test
	public void testGetNullLoanBorrowedReturned() {	
		LoanStub loan = new LoanStub();
		Book book = new Book("test", "test", "test", 18);
	    book.borrow(loan);//Borrow the book
	    book.returnBook(false);//Return without damage
	    assertEquals("", null, book.getLoan());//Verify no loan associated
	}
	
	//Check if book returns a null loan when it has been borrowed and returned damaged
	@Test
	public void testGetNullLoanBorrowedReturnedDamaged() {	
		LoanStub loan = new LoanStub();
		Book book = new Book("test", "test", "test", 19);
	    book.borrow(loan);//Borrow the book
	    book.returnBook(true);//Return with damage
	    assertEquals("", null, book.getLoan());//Verify no loan associated
	}
	
	//Attempt return ON_LOAN book without damage
	@SuppressWarnings("static-access")
	@Test
	public void testReturnOnLoanBookWithoutDamage() {	
		LoanStub loan = new LoanStub();
	    Book book = new Book("test", "test", "test", 101);
	    book.borrow(loan);
		assertTrue(book.getState() == eBookState_.ON_LOAN);//Verify book's status is on loan	    
	    book.returnBook(false);
		assertTrue(book.getState() == eBookState_.AVAILABLE);//Verify book's status is available
	}
	
	//Attempt return ON_LOAN book with damage
	@SuppressWarnings("static-access")
	@Test
	public void testReturnOnLoanBookDamaged() {	
		LoanStub loan = new LoanStub();
	    Book book = new Book("test", "test", "test", 102);
	    book.borrow(loan);
		assertTrue(book.getState() == eBookState_.ON_LOAN);//Verify book's status is on loan	    
	    book.returnBook(true);
		assertTrue(book.getState() == eBookState_.DAMAGED);//Verify book's status is damaged
	}	
	
	//Attempt return AVAILABLE book with damage
	@Test(expected=RuntimeException.class)
	public void testReturnAvailableBookDamaged() {	
	    Book book = new Book("test", "test", "test", 20);
	    book.returnBook(true);
	}

	//Attempt return AVAILABLE book without damage
	@Test(expected=RuntimeException.class)
	public void testReturnAvailableBookUndamaged() {	
	    Book book = new Book("test", "test", "test", 21);
	    book.returnBook(false);
	}
	
	//Attempt return LOST book with damage
	@Test(expected=RuntimeException.class)
	public void testReturnLostBookDamaged() {	
	    Book book = new Book("test", "test", "test", 22);
	    book.lose();
	    book.returnBook(true);
	}

	//Attempt return LOST book without damage
	@Test(expected=RuntimeException.class)
	public void testReturnLostBookUndamaged() {	
	    Book book = new Book("test", "test", "test", 23);
	    book.lose();
	    book.returnBook(false);
	}
	
	//Attempt return DAMAGED book with damage
	@Test(expected=RuntimeException.class)
	public void testReturnDamagedBookWithDamaged() {	
		LoanStub loan = new LoanStub();
		Book book = new Book("test", "test", "test", 24);
	    book.borrow(loan);//Borrow Book
	    book.returnBook(true); //Return with damage
	    book.returnBook(true); //Attempt return DAMAGED Book
	}

	//Attempt return DAMAGED book without damage
	@Test(expected=RuntimeException.class)
	public void testReturnDamagedBookWithoutDamaged() {	
		LoanStub loan = new LoanStub();
		Book book = new Book("test", "test", "test", 25);
	    book.borrow(loan);//Borrow Book
	    book.returnBook(false); //Return without damage
	    book.returnBook(true); //Attempt return DAMAGED Book
	}
	
	//lose book which is on loan
	@SuppressWarnings("static-access")
	@Test
	public void testLoseBook() {	
		LoanStub loan = new LoanStub();
		Book book = new Book("test", "test", "test", 26);
		assertTrue(book.getState() == eBookState_.AVAILABLE);//Verify book's status is available
	    book.borrow(loan);//Borrow Book
		assertTrue(book.getState() == eBookState_.ON_LOAN);//Verify book's status is on loan
	    book.lose();//Lose Book
		assertTrue(book.getState() == eBookState_.LOST);//Verify book's status is lost
	}
	
	//Attempt lose book which is AVAILABLE
	@Test(expected=RuntimeException.class)
	public void testLoseBookAvailable() {	
		Book book = new Book("test", "test", "test", 27);
		book.lose();//Lose Book
	}
	
	//Attempt lose book which is already LOST
	@SuppressWarnings("static-access")
	@Test(expected=RuntimeException.class)
	public void testLoseBookLost() {
		LoanStub loan = new LoanStub();
		Book book = new Book("test", "test", "test", 28);
		assertTrue(book.getState() == eBookState_.AVAILABLE);//Verify book's status is available
	    book.borrow(loan);//Borrow Book
		assertTrue(book.getState() == eBookState_.ON_LOAN);//Verify book's status is on loan
		book.lose();//Lose Book
		assertTrue(book.getState() == eBookState_.LOST);//Verify book's status is lost
		book.lose();//Attempt Lose Book when already lost
	}
	
	//Attempt lose book which is damaged
	@SuppressWarnings("static-access")
	@Test(expected=RuntimeException.class)
	public void testLoseBookDamaged() {
		LoanStub loan = new LoanStub();
		Book book = new Book("test", "test", "test", 29);
		assertTrue(book.getState() == eBookState_.AVAILABLE);//Verify book's status is available
	    book.borrow(loan);//Borrow Book
		assertTrue(book.getState() == eBookState_.ON_LOAN);//Verify book's status is on loan
		book.returnBook(true);//Return Book with damage
		assertTrue(book.getState() == eBookState_.DAMAGED);//Verify book's status is damaged
		book.lose();//Attempt Lose Book when damaged
	}
	
	//Attempt lose book which is disposed
	@SuppressWarnings("static-access")
	@Test(expected=RuntimeException.class)
	public void testLoseBookDisposed() {
		LoanStub loan = new LoanStub();
		Book book = new Book("test", "test", "test", 30);
		assertTrue(book.getState() == eBookState_.AVAILABLE);//Verify book's status is available
	    book.borrow(loan);//Borrow Book
		assertTrue(book.getState() == eBookState_.ON_LOAN);//Verify book's status is on loan
		book.returnBook(true);//Return Book with damage
		assertTrue(book.getState() == eBookState_.DAMAGED);//Verify book's status is damaged
		book.dispose();//Dispose Book
		book.lose();//Attempt Lose Book when disposed
	}
	
	//Repair damaged book
	@SuppressWarnings("static-access")
	public void testRepairDamaged() {
		LoanStub loan = new LoanStub();
		Book book = new Book("test", "test", "test", 31);
		assertTrue(book.getState() == eBookState_.AVAILABLE);//Verify book's status is available
	    book.borrow(loan);//Borrow Book
		assertTrue(book.getState() == eBookState_.ON_LOAN);//Verify book's status is on loan
		book.returnBook(true);//Return Book with damage
		assertTrue(book.getState() == eBookState_.DAMAGED);//Verify book's status is damaged
		book.repair();//Repair Book
		assertTrue(book.getState() == eBookState_.AVAILABLE);//Verify book's status is available
	}
	
	//Attempt repair available book
	@Test(expected=RuntimeException.class)
	public void testRepairAvailable() {
		Book book = new Book("test", "test", "test", 32);
		book.repair();//Attempt Repair Book
	}
	
	//Attempt repair on loan book
	@SuppressWarnings("static-access")
	@Test(expected=RuntimeException.class)
	public void testRepairOnLoan() {
		LoanStub loan = new LoanStub();
		Book book = new Book("test", "test", "test", 33);
		assertTrue(book.getState() == eBookState_.AVAILABLE);//Verify book's status is available
	    book.borrow(loan);//Borrow Book
		assertTrue(book.getState() == eBookState_.ON_LOAN);//Verify book's status is on loan
		book.repair();//Attempt Repair Book
	}
	
	//Attempt repair lost book
	@SuppressWarnings("static-access")
	@Test(expected=RuntimeException.class)
	public void testRepairLost() {
		LoanStub loan = new LoanStub();
		Book book = new Book("test", "test", "test", 34);
		assertTrue(book.getState() == eBookState_.AVAILABLE);//Verify book's status is available
	    book.borrow(loan);//Borrow Book
		assertTrue(book.getState() == eBookState_.ON_LOAN);//Verify book's status is on loan
		book.lose();//Lose Book
		assertTrue(book.getState() == eBookState_.LOST);//Verify book's status is lost
		book.repair();//Attempt Repair Book
	}
	
	//Attempt repair disposed book
	@SuppressWarnings("static-access")
	@Test(expected=RuntimeException.class)
	public void testRepairDisposed() {
		Book book = new Book("test", "test", "test", 35);
		assertTrue(book.getState() == eBookState_.AVAILABLE);//Verify book's status is available
		book.dispose();//Dispose Book
		assertTrue(book.getState() == eBookState_.DISPOSED);//Verify book's status is disposed
		book.repair();//Attempt Repair Book
	}
	//Attempt dispose available book
	@SuppressWarnings("static-access")
	@Test
	public void testDisposeAvailable() {
		Book book = new Book("test", "test", "test", 36);
		assertTrue(book.getState() == eBookState_.AVAILABLE);//Verify book's status is available
		book.dispose();//Dispose Book
		assertTrue(book.getState() == eBookState_.DISPOSED);//Verify book's status is disposed
	}
	
	//Attempt dispose damaged book
	@SuppressWarnings("static-access")
	@Test
	public void testDisposeDamaged() {
		LoanStub loan = new LoanStub();
		Book book = new Book("test", "test", "test", 37);
		assertTrue(book.getState() == eBookState_.AVAILABLE);//Verify book's status is available
	    book.borrow(loan);//Borrow Book
		assertTrue(book.getState() == eBookState_.ON_LOAN);//Verify book's status is on loan
		book.returnBook(true);
		assertTrue(book.getState() == eBookState_.DAMAGED);//Verify book's status is damaged		
		book.dispose();//Dispose Book
		assertTrue(book.getState() == eBookState_.DISPOSED);//Verify book's status is disposed
	}
	
	//Attempt dispose lost book
	@SuppressWarnings("static-access")
	@Test
	public void testDisposeLost() {
		LoanStub loan = new LoanStub();
		Book book = new Book("test", "test", "test", 38);
		assertTrue(book.getState() == eBookState_.AVAILABLE);//Verify book's status is available
	    book.borrow(loan);//Borrow Book
		assertTrue(book.getState() == eBookState_.ON_LOAN);//Verify book's status is on loan
		book.lose();
		assertTrue(book.getState() == eBookState_.LOST);//Verify book's status is lost		
		book.dispose();//Dispose Book
		assertTrue(book.getState() == eBookState_.DISPOSED);//Verify book's status is disposed
	}
	
	//Attempt dispose on loan book
	@SuppressWarnings("static-access")
	@Test(expected=RuntimeException.class)
	public void testDisposeOnLoan() {
		LoanStub loan = new LoanStub();
		Book book = new Book("test", "test", "test", 38);
		assertTrue(book.getState() == eBookState_.AVAILABLE);//Verify book's status is available
	    book.borrow(loan);//Borrow Book
		assertTrue(book.getState() == eBookState_.ON_LOAN);//Verify book's status is on loan
		book.dispose();//Dispose Book
	}
	
	//Attempt dispose, disposed book
	@SuppressWarnings("static-access")
	@Test(expected=RuntimeException.class)
	public void testDisposeDisposed() {
		LoanStub loan = new LoanStub();
		Book book = new Book("test", "test", "test", 38);
		assertTrue(book.getState() == eBookState_.AVAILABLE);//Verify book's status is available
	    book.borrow(loan);//Borrow Book
		assertTrue(book.getState() == eBookState_.ON_LOAN);//Verify book's status is on loan
		book.lose();
		assertTrue(book.getState() == eBookState_.LOST);//Verify book's status is lost		
		book.dispose();//Dispose Book
		assertTrue(book.getState() == eBookState_.DISPOSED);//Verify book's status is disposed
		book.dispose();//Attempt to dispose again
	}
	
	//Check if book returns the book state 'available'
	@SuppressWarnings("static-access")
	@Test
	public void testGetStateAvailable() {	
	    Book book = new Book("test", "test", "test", 39);
	    assertEquals(eBookState_.AVAILABLE, book.getState());//Verify book is on loan
	}
	
	//Check if book returns the book state 'on_loan'
	@SuppressWarnings("static-access")
	@Test
	public void testGetStateOnLoan() {	
		LoanStub loan = new LoanStub();
	    Book book = new Book("test", "test", "test", 40);
	    book.borrow(loan);//Borrow Book
	    assertEquals(eBookState_.ON_LOAN, book.getState());//Verify book is on loan
	}
	
	//Check if book returns the book state 'lost'
	@SuppressWarnings("static-access")
	@Test
	public void testGetStateLost() {	
		LoanStub loan = new LoanStub();
	    Book book = new Book("test", "test", "test", 41);
	    book.borrow(loan);//Borrow Book
	    book.lose();//Lose Book
	    assertEquals(eBookState_.LOST, book.getState());//Verify book is lost
	}
	
	//Check if book returns the book state 'damaged'
	@SuppressWarnings("static-access")
	@Test
	public void testGetStateDamaged() {	
		LoanStub loan = new LoanStub();
	    Book book = new Book("test", "test", "test", 42);
	    book.borrow(loan);//Borrow Book
	    book.returnBook(true);//Return Book damaged
	    assertEquals(eBookState_.DAMAGED, book.getState());//Verify book is damaged
	}
	
	//Check if book returns the book state 'disposed'
	@SuppressWarnings("static-access")
	@Test
	public void testGetStateDisposed() {	
		LoanStub loan = new LoanStub();
	    Book book = new Book("test", "test", "test", 43);
	    book.borrow(loan);//Borrow Book
	    book.returnBook(true);//Return Book damaged
	    book.dispose();//Dispose Book
	    assertEquals(eBookState_.DISPOSED, book.getState());//Verify book is disposed
	}
	
	//Check if getAuthor returns the book's author
	@Test
	public void testGetAuthor() {	
	    Book book = new Book("test1", "test2", "test3", 44);
	    assertEquals("test1", book.getAuthor());
	}
	
	//Check if getTitle returns the book's title
	@Test
	public void testGetTitle() {	
	    Book book = new Book("test1", "test2", "test3", 45);
	    assertEquals("test2", book.getTitle());
	}
	
	//Check if getCallNumber returns the book's call number
	@Test
	public void testGetCallNumber() {	
	    Book book = new Book("test1", "test2", "test3", 46);
	    assertEquals("test3", book.getCallNumber());
	}
	
	//Check if getId returns the book's id
	@Test
	public void testGetBookId() {	
	    Book book = new Book("test1", "test2", "test3", 47);
	    assertEquals(47, book.getID());
	}
}
