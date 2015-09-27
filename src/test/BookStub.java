package test;

import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;


public class BookStub implements IBook{

	private String author_;
	private String title_;
	private String callNumber_;
	private int bookId_;
	private EBookState eBookState_;
	
	@SuppressWarnings("static-access")
	public BookStub(String author, String title, String callNumber, int bookId)
	{
		author_ = author;
		title_ = title;
		callNumber_ = callNumber;
		bookId_ = bookId;
		eBookState_ = eBookState_.AVAILABLE;
	}
	
	@Override
	public void borrow(ILoan loan) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ILoan getLoan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void returnBook(boolean damaged) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void repair() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EBookState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCallNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}


}
