package library.daos;

import java.util.List;

import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;

public class BookDAO implements IBookDAO{
	private IBookHelper iBookHelper_;
	private List<IBook> bookList_;
	private int nextBookId_ = 0;
	
	private BookDAO(IBookHelper iBookHelper)
	throws IllegalArgumentException
	{
		if(iBookHelper == null)
		{
			throw new IllegalArgumentException("Error, missing IBookHelper.");
		}
		else
		{
			iBookHelper_ = iBookHelper;
		}
	}

	@Override
	public IBook addBook(String author, String title, String callNo) {
		IBook book = iBookHelper_.makeBook(author, title, callNo, nextBookId_);
		return book;
	}

	@Override
	public IBook getBookByID(int id) {
		try
		{
			return bookList_.get(id);
		}
		catch (Exception e){
			return null;
		}
	}

	@Override
	public List<IBook> listBooks() {
		return bookList_;
	}

	@Override
	public List<IBook> findBooksByAuthor(String author) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IBook> findBooksByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IBook> findBooksByAuthorTitle(String author, String title) {
		// TODO Auto-generated method stub
		return null;
	}

}
