package test;

import library.daos.BookHelper;
import library.daos.BookMapDAO;
import library.entities.Book;
import library.interfaces.entities.IBook;
import static org.junit.Assert.*;

import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.contains;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BookMapDAOTest {
	
	private BookHelper bookHelper_ = Mockito.mock(BookHelper.class);
	private int bookId_ = 1;

	@Before
	public void setUp() throws Exception {
		//Simulate the BookHelper's makrBook method.
		when(bookHelper_.makeBook(contains("Author0"), contains("BookTitle0"), contains("BookCallNumber0"), anyInt())).thenReturn(new Book("Author0", "BookTitle0", "BookCallNumber0", bookId_++));
		when(bookHelper_.makeBook(contains("Author1"), contains("BookTitle1"), contains("BookCallNumber1"), anyInt())).thenReturn(new Book("Author1", "BookTitle1", "BookCallNumber1", bookId_++));
		when(bookHelper_.makeBook(contains("Author0"), contains("BookTitle2"), contains("BookCallNumber2"), anyInt())).thenReturn(new Book("Author0", "BookTitle2", "BookCallNumber2", bookId_++));
	}

	@After
	public void tearDown() throws Exception {
	}

	//BookHelper is not null
	@Test
	public void testBookMapDAOConstructorNotNullHelper() {
		new BookMapDAO(bookHelper_);
	}
	
	//BookHelper is null
	@Test(expected=IllegalArgumentException.class)
	public void testBookMapDAOConstructorNullHelper() {
		BookHelper bookHelperTest = null;
		new BookMapDAO(bookHelperTest);
	}
	
	@Test
	public final void testAddBook() {
		//Setup new BookMapDAO
		BookMapDAO BookMapDAO= new BookMapDAO(bookHelper_);
		//Add three books
		BookMapDAO.addBook("Author0", "BookTitle0", "BookCallNumber0");
		BookMapDAO.addBook("Author1", "BookTitle1", "BookCallNumber1");
		BookMapDAO.addBook("Author0", "BookTitle2", "BookCallNumber2");
		//Verify the books exist.
		assertEquals(3, BookMapDAO.listBooks().size());
	}

	@Test
	public final void testGetBookByID() {
		//Setup new BookMapDAO
		BookMapDAO BookMapDAO= new BookMapDAO(bookHelper_);
		//Add three books
		BookMapDAO.addBook("Author0", "BookTitle0", "BookCallNumber0");
		BookMapDAO.addBook("Author1", "BookTitle1", "BookCallNumber1");
		BookMapDAO.addBook("Author0", "BookTitle2", "BookCallNumber2");
		//Get all three books by their Id
		IBook book1 = BookMapDAO.getBookByID(1);
		IBook book2 = BookMapDAO.getBookByID(2);
		IBook book3 = BookMapDAO.getBookByID(3);
		//Check that each book retrieved by their Id has correct description
		assertTrue((book1.getAuthor() == "Author0") && (book1.getTitle() == "BookTitle0") && (book1.getCallNumber() == "BookCallNumber0"));
		assertTrue((book2.getAuthor() == "Author1") && (book2.getTitle() == "BookTitle1") && (book2.getCallNumber() == "BookCallNumber1"));
		assertTrue((book3.getAuthor() == "Author0") && (book3.getTitle() == "BookTitle2") && (book3.getCallNumber() == "BookCallNumber2"));
	}

	@Test
	public final void testListBooks() {
		//Setup new BookMapDAO
		BookMapDAO BookMapDAO =  new BookMapDAO(bookHelper_);
		//Add two books
		BookMapDAO.addBook("Author0", "BookTitle0", "BookCallNumber0");
		BookMapDAO.addBook("Author1", "BookTitle1", "BookCallNumber1");
		//Check that both books are listed
		assertEquals(2, BookMapDAO.listBooks().size());
	}

	@Test
	public final void testFindBooksByAuthor() {
		//Setup new BookMapDAO
		BookMapDAO BookMapDAO= new BookMapDAO(bookHelper_);
		//Add three books
		BookMapDAO.addBook("Author0", "BookTitle0", "BookCallNumber0");
		BookMapDAO.addBook("Author1", "BookTitle1", "BookCallNumber1");
		BookMapDAO.addBook("Author0", "BookTitle2", "BookCallNumber2");
		//Check that books are retrievable by author	
		assertEquals(2, BookMapDAO.findBooksByAuthor("Author0").size());
		assertEquals(1, BookMapDAO.findBooksByAuthor("Author1").size());		
	}

	@Test
	public final void testFindBooksByTitle() {
		//Setup new BookMapDAO
		BookMapDAO BookMapDAO= new BookMapDAO(bookHelper_);
		//Add three books
		BookMapDAO.addBook("Author0", "BookTitle0", "BookCallNumber0");
		BookMapDAO.addBook("Author1", "BookTitle1", "BookCallNumber1");
		BookMapDAO.addBook("Author0", "BookTitle2", "BookCallNumber2");
		//Check that books are retrievable by title	
		assertEquals(BookMapDAO.findBooksByTitle("BookTitle0").size(), 1);
		assertEquals(BookMapDAO.findBooksByTitle("BookTitle1").size(), 1);
		assertEquals(BookMapDAO.findBooksByTitle("BookTitle2").size(), 1);
	}

	@Test
	public final void testFindBooksByAuthorTitle() {
		//Setup new BookMapDAO
		BookMapDAO BookMapDAO= new BookMapDAO(bookHelper_);
		//Add three books
		BookMapDAO.addBook("Author0", "BookTitle0", "BookCallNumber0");
		BookMapDAO.addBook("Author1", "BookTitle1", "BookCallNumber1");
		BookMapDAO.addBook("Author0", "BookTitle2", "BookCallNumber2");
		//Check that books are retrievable by author and title	
		assertEquals(BookMapDAO.findBooksByAuthorTitle("Author0", "BookTitle0").size(), 1);
		assertEquals(BookMapDAO.findBooksByAuthorTitle("Author1", "BookTitle1").size(), 1);
		assertEquals(BookMapDAO.findBooksByAuthorTitle("Author0", "BookTitle2").size(), 1);
	}

}
