package test;

import static org.junit.Assert.*;
import library.daos.BookHelper;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BookHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings({ "static-access", "unused", "null" })
	@Test
	public void testMakeBook() {
		//Setup
		BookHelper bookHelper = null;
		String author = "Author";
		String title = "Title";
		String callNumber = "1121986";
		int id = 77;
		
		//Test
		IBook bookCreated = bookHelper.makeBook(author, title, callNumber, id);
		
		//Retrieval
		String authorReturned = bookCreated.getAuthor();
		String titleReturned = bookCreated.getTitle();
		String callNumberReturned = bookCreated.getCallNumber();
		int idReturned = bookCreated.getID();
		ILoan existingLoan = bookCreated.getLoan();
		EBookState eBookState = bookCreated.getState();
		
		//Verify
		assertTrue(author == bookCreated.getAuthor());
		assertTrue(title == bookCreated.getTitle());
		assertTrue(callNumber == bookCreated.getCallNumber());
		assertTrue(id == bookCreated.getID());
		assertTrue(bookCreated.getLoan() == null);
		assertTrue(eBookState == eBookState.AVAILABLE);
		

	}

}
