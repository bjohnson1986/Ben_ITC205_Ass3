package test;


import java.util.List;

import library.daos.BookHelper;
import library.daos.BookDAO;
import library.entities.Book;
import library.interfaces.entities.IBook;
import static org.junit.Assert.*;

import org.mockito.Mockito;

import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BookDAOTest {
	
	private BookHelper bookHelper_ = Mockito.mock(BookHelper.class);
	private BookDAO bookDao_= new BookDAO(bookHelper_);
	private IBook book_;
	private List<IBook> runningBookList_;
	private int bookId_;
	

	@Before
	public void setUp() throws Exception {	
		bookId_ = 1;
		BookDAO bookDao_ =  new BookDAO(bookHelper_);
		bookDao_.addBook("Author0", "BookTitle0", "BookCallNumber0");
		bookDao_.addBook("Author1", "BookTitle1", "BookCallNumber1");
		bookDao_.addBook("Author0", "BookTitle2", "BookCallNumber2");
		when(bookHelper_.makeBook(anyString(), anyString(), anyString(), anyInt())).thenReturn(new Book("Author", "Title", "CallId", ++bookId_));
		Book book_ = new Book("Author", "Title", "CallId", 1);
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
		BookDAO bookDao =  new BookDAO(bookHelper_);
		bookDao.addBook("Author0", "BookTitle0", "BookCallNumber0");
		bookDao.addBook("Author1", "BookTitle1", "BookCallNumber1");
		bookDao.addBook("Author0", "BookTitle2", "BookCallNumber2");
		assertEquals(bookDao.listBooks().size(), 3);
	}

	@Test
	public final void testGetBookByID() {
		bookId_ = 1;
		bookDao_.addBook("Author0", "BookTitle0", "BookCallNumber0");
		IBook book_ = bookDao_.getBookByID(1);
		String author = book_.getAuthor();
		System.out.println(author);
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
		fail("Not yet implemented");
	}

	@Test
	public final void testFindBooksByTitle() {
		fail("Not yet implemented");
	}

	@Test
	public final void testFindBooksByAuthorTitle() {
		fail("Not yet implemented");
	}

}
