package test;


import java.util.List;

import library.daos.BookHelper;
import library.daos.BookDAO;
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
	private BookDAO bookDao_ = Mockito.mock(BookDAO.class);
	private List<IBook> runningBookList_;
	private int bookId_;
	
	public BookDAOTest(){
		bookId_ = 1;
	}

	@Before
	public void setUp() throws Exception {	
		BookHelper bookHelper = Mockito.mock(BookHelper.class);
		when(bookHelper.makeBook(anyString(), anyString(), anyString(), anyInt())).thenReturn(new BookStub("Author", "Title", "CallId", ++bookId_));
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

	@SuppressWarnings("unused")
	@Test
	public final void testGetBookByID() {
		bookId_ = 1;
		BookDAO bookDao =  new BookDAO(bookHelper_);
		bookDao.addBook("Author0", "BookTitle0", "BookCallNumber0");
		bookDao.addBook("Author1", "BookTitle1", "BookCallNumber1");
		bookDao.addBook("Author0", "BookTitle2", "BookCallNumber2");
		IBook book = bookDao.getBookByID(1);
	}

	@Test
	public final void testListBooks() {		
		bookId_ = 1;
		BookDAO bookDao =  new BookDAO(bookHelper_);
		
		IBook book0 = bookDao.addBook("Author0", "BookTitle0", "BookCallNumber0");
		runningBookList_.add(book0);
		IBook book1 = bookDao.addBook("Author1", "BookTitle1", "BookCallNumber1");
		runningBookList_.add(book1);
		IBook book2 = bookDao.addBook("Author0", "BookTitle2", "BookCallNumber2");
		runningBookList_.add(book2);
		List <IBook> bookList = bookDao_.listBooks();
		assertTrue(bookList == runningBookList_);
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
