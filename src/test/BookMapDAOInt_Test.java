package test;

import static org.junit.Assert.*;

import java.util.List;

import library.daos.BookHelper;
import library.daos.BookMapDAO;
import library.entities.Book;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BookMapDAOInt_Test {
	
	private IBook book_;
	private BookMapDAO bookDao_;
	private BookHelper bookHelper_;
	private String author_;
	private String title_;
	private String callNumber_;
	private int id_;
	private List<IBook> bookList_;

	@Before
	public void setUp() throws Exception {
		bookHelper_ = new BookHelper();
		bookDao_ = new BookMapDAO(bookHelper_);
		author_ = "author";
		title_ = "title";
		callNumber_ = "callNumber";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBookMapDAO() {
		bookDao_ = new BookMapDAO(bookHelper_);
		assertTrue(bookDao_ instanceof BookMapDAO);
	}

	@Test
	public void testAddBook() {
		book_ = bookDao_.addBook(author_, title_, callNumber_);
		assertTrue(book_ instanceof Book);				
	}

	@Test
	public void testGetBookByID() {
		bookHelper_ = new BookHelper();
		bookDao_ = new BookMapDAO(bookHelper_);
		IBook bookCreated = bookDao_.addBook(author_, title_, callNumber_);
		book_ = bookDao_.getBookByID(1);
		assertEquals(bookCreated, book_);
	}

	@Test
	public void testListBooks() {
		bookHelper_ = new BookHelper();
		bookDao_ = new BookMapDAO(bookHelper_);
		book_ = bookDao_.addBook(author_, title_, callNumber_);
		bookList_ = bookDao_.listBooks();
		assertEquals(1, bookList_);
	}

	@Test
	public void testFindBooksByAuthor() {
		book_ = bookDao_.addBook(author_, title_, callNumber_);
		assertEquals(author_, book_.getAuthor());
	}

	@Test
	public void testFindBooksByTitle() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindBooksByAuthorTitle() {
		fail("Not yet implemented");
	}

}
