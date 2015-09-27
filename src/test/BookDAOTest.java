package test;




import library.daos.BookHelper;
import library.daos.BookDAO;
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

public class BookDAOTest {
	
	private BookHelper bookHelper_ = Mockito.mock(BookHelper.class);
	@SuppressWarnings("unused")
	private BookDAO bookDao_= new BookDAO(bookHelper_);
	private int bookId_ = 1;
	

	@Before
	public void setUp() throws Exception {	
		when(bookHelper_.makeBook(contains("Author0"), contains("BookTitle0"), contains("BookCallNumber0"), anyInt())).thenReturn(new Book("Author0", "BookTitle0", "BookCallNumber0", bookId_++));
		when(bookHelper_.makeBook(contains("Author1"), contains("BookTitle1"), contains("BookCallNumber1"), anyInt())).thenReturn(new Book("Author1", "BookTitle1", "BookCallNumber1", bookId_++));
		when(bookHelper_.makeBook(contains("Author0"), contains("BookTitle2"), contains("BookCallNumber2"), anyInt())).thenReturn(new Book("Author0", "BookTitle2", "BookCallNumber2", bookId_++));
	}

	@After
	public void tearDown() throws Exception {
	}

	//BookHelper is not null
	@Test
	public void testBookDAOConstructorNotNullHelper() {
		new BookDAO(bookHelper_);
	}
	
	//BookHelper is null
	@Test(expected=IllegalArgumentException.class)
	public void testBookDAOConstructorNullHelper() {
		BookHelper bookHelperTest = null;
		new BookDAO(bookHelperTest);
	}
	
	@Test
	public final void testAddBook() {
		BookDAO bookDao_= new BookDAO(bookHelper_);
		bookDao_.addBook("Author0", "BookTitle0", "BookCallNumber0");
		bookDao_.addBook("Author1", "BookTitle1", "BookCallNumber1");
		bookDao_.addBook("Author0", "BookTitle2", "BookCallNumber2");
		assertEquals(3, bookDao_.listBooks().size());
	}

	@Test
	public final void testGetBookByID() {
		System.out.println(bookId_);
		System.out.println(bookId_);
		BookDAO bookDao_= new BookDAO(bookHelper_);
		bookDao_.addBook("Author0", "BookTitle0", "BookCallNumber0");
		bookDao_.addBook("Author1", "BookTitle1", "BookCallNumber1");
		bookDao_.addBook("Author0", "BookTitle2", "BookCallNumber2");
		IBook book1 = bookDao_.getBookByID(1);
		IBook book2 = bookDao_.getBookByID(2);
		IBook book3 = bookDao_.getBookByID(3);
		System.out.println(book1.getAuthor());
		assertTrue((book1.getAuthor() == "Author0") && (book1.getTitle() == "BookTitle0") && (book1.getCallNumber() == "BookCallNumber0"));
		assertTrue((book2.getAuthor() == "Author1") && (book2.getTitle() == "BookTitle1") && (book2.getCallNumber() == "BookCallNumber1"));
		assertTrue((book3.getAuthor() == "Author0") && (book3.getTitle() == "BookTitle2") && (book3.getCallNumber() == "BookCallNumber2"));
	}

	@Test
	public final void testListBooks() {		
		bookId_ = 1;
		BookDAO bookDao =  new BookDAO(bookHelper_);
		bookDao.addBook("Author0", "BookTitle0", "BookCallNumber0");
		bookDao.addBook("Author1", "BookTitle1", "BookCallNumber1");
		assertEquals(bookDao.listBooks().size(), 2);
	}

	@Test
	public final void testFindBooksByAuthor() {
		bookId_ = 20;
		BookDAO bookDao_= new BookDAO(bookHelper_);
		bookDao_.addBook("Author0", "BookTitle0", "BookCallNumber0");
		bookDao_.addBook("Author1", "BookTitle1", "BookCallNumber1");
		bookDao_.addBook("Author0", "BookTitle2", "BookCallNumber2");
		assertEquals(bookDao_.findBooksByAuthor("Author0").size(), 2);
		assertEquals(bookDao_.findBooksByAuthor("Author1").size(), 1);		
	}

	@Test
	public final void testFindBooksByTitle() {
		bookId_ = 30;
		BookDAO bookDao_= new BookDAO(bookHelper_);
		bookDao_.addBook("Author0", "BookTitle0", "BookCallNumber0");
		bookDao_.addBook("Author1", "BookTitle1", "BookCallNumber1");
		bookDao_.addBook("Author0", "BookTitle2", "BookCallNumber2");
		assertEquals(bookDao_.findBooksByTitle("BookTitle0").size(), 1);
		assertEquals(bookDao_.findBooksByTitle("BookTitle1").size(), 1);
		assertEquals(bookDao_.findBooksByTitle("BookTitle2").size(), 1);
	}

	@Test
	public final void testFindBooksByAuthorTitle() {
		bookId_ = 40;
		BookDAO bookDao_= new BookDAO(bookHelper_);
		bookDao_.addBook("Author0", "BookTitle0", "BookCallNumber0");
		bookDao_.addBook("Author1", "BookTitle1", "BookCallNumber1");
		bookDao_.addBook("Author0", "BookTitle2", "BookCallNumber2");
		assertEquals(bookDao_.findBooksByAuthorTitle("Author0", "BookTitle0").size(), 1);
		assertEquals(bookDao_.findBooksByAuthorTitle("Author1", "BookTitle1").size(), 1);
		assertEquals(bookDao_.findBooksByAuthorTitle("Author0", "BookTitle2").size(), 1);
	}

}
