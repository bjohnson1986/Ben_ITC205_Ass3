package test;

import java.util.Date;

import library.entities.Book;
import library.entities.Loan;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.IMember;
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

	//Attempt loan for book ON_LOAN
	@Test(expected=RuntimeException.class)
	public void testBorrowAlreadyOnLoan() {
		LoanStub existingLoan = new LoanStub();
		LoanStub newLoan = new LoanStub();
		Book book = new Book("test", "test", "test", 11);
		System.out.println("Book state " + book.getState());
		book.borrow(existingLoan);
		System.out.println("Book state " + book.getState());
		book.borrow(newLoan);
	}
	
	//Attempt loan for LOST book
	@Test(expected=RuntimeException.class)
	public void testBorrowLostBook() {	
		LoanStub loan = new LoanStub();
	    Book book = new Book("test", "test", "test", 13);
	    book.lose();
	    book.borrow(loan); 
	}	
	
	//Attempt loan for DAMAGED book
	@Test(expected=RuntimeException.class)
	public void testBorrowDamagedBook() {	
		LoanStub existingLoan = new LoanStub();
		LoanStub newLoan = new LoanStub();
	    Book book = new Book("test", "test", "test", 12);
		System.out.println("Book state " + book.getState());
	    book.borrow(existingLoan); 
	    book.returnBook(true);
		System.out.println("Book state " + book.getState());
	    book.borrow(newLoan);
	}
	
	//Attempt loan for DISPOSED book
	@Test(expected=RuntimeException.class)
	public void testBorrowDisposedBook() {	
		LoanStub loan = new LoanStub();
	    Book book = new Book("test", "test", "test", 13);
	    book.dispose();
	    book.borrow(loan); 
	}
}
