package library.daos;

import java.util.ArrayList;
import java.util.List;

import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;

public class BookDAO implements IBookDAO{
	private IBookHelper iBookHelper_;
	private List<IBook> bookList_;
	private int nextBookId_;
	
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
			bookList_ = new ArrayList<IBook>();
			//Start unique identification count.
			nextBookId_ = 1;
		}
	}

	public IBook addBook(String author, String title, String callNo) {
		IBook book = iBookHelper_.makeBook(author, title, callNo, nextBookId_);
		bookList_.add(book);
		nextBookId_ ++;
		return book;
	}

	public IBook getBookByID(int id) {
		try
		{
			//Book exists
			return bookList_.get(id);
		}
		catch (Exception e){
			//Book does not exist
			return null;
		}
	}

	public List<IBook> listBooks() {
		return bookList_;
	}

	public List<IBook> findBooksByAuthor(String author) {
		ArrayList<IBook> authorMatchList = new ArrayList<IBook>();
		//Loop over book list, books that have a matching author are added to the match list.
		for (IBook book : bookList_)
		{
			if (book.getAuthor() == author)
			{
				authorMatchList.add(book);
			}
		}
		return authorMatchList;
	}

	public List<IBook> findBooksByTitle(String title) {
		ArrayList<IBook> titleMatchList = new ArrayList<IBook>();
		//Loop over book list, books that have a matching title are added to the match list.
		for (IBook book : bookList_)
		{
			if (book.getTitle() == title)
			{
				titleMatchList.add(book);
			}
		}
		return titleMatchList;
	}

	public List<IBook> findBooksByAuthorTitle(String author, String title) {
		ArrayList<IBook> authorAndTitleMatchList = new ArrayList<IBook>();
		//Loop over book list, books that have a matching author and title are added to the match list.
		for (IBook book : bookList_)
		{
			if ((book.getTitle() == title) && (book.getAuthor() == author))
			{
				authorAndTitleMatchList.add(book);
			}
		}
		return authorAndTitleMatchList;
	}

}
