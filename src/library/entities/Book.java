package library.entities;

import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;

public class Book implements IBook{
	private String author_;
	private String title_;
	private String callNumber_;
	private int bookId_;
	private EBookState eBookState_;
	private ILoan associatedLoan_;
	
	@SuppressWarnings("static-access")
	public Book(String author, String title, String callNumber, int bookId)
	throws IllegalArgumentException
	{
		if (author == null || author.isEmpty())
		{
			throw new IllegalArgumentException("The Book's author cannot be null or an empty string.");
		}
		if (title == null || title.isEmpty())
		{
			throw new IllegalArgumentException("The Book's title cannot be null or an empty string.");
		}
		if (callNumber == null || callNumber.isEmpty())
		{
			throw new IllegalArgumentException("The Book's call number cannot be null or an empty string.");
		}
		if (bookId <= 0)
		{
			throw new IllegalArgumentException("The Book's identification number cannot be less than or equal to zero.");
		}
		//All variables are valid, construct the object.
		if (((author != null && !author.isEmpty()) && (title != null && !title.isEmpty()) && ((callNumber != null && !callNumber.isEmpty())) && (bookId > 0)))
		{
			author_ = author;
			title_ = title;
			callNumber_ = callNumber;
			bookId_ = bookId;
			//Verify if Book is available as soon as it is created.
			eBookState_ = eBookState_.AVAILABLE;
			
		}
	}
	
	
	@SuppressWarnings("static-access")
	public void borrow(ILoan loan) {
		if (eBookState_ == eBookState_.AVAILABLE)
		{
			associatedLoan_ = loan;
			eBookState_ = eBookState_.ON_LOAN;
		}
		else
		{
			throw new RuntimeException("Cannot associate a new loan with a book that is not available.");
		}
		
	}

	@SuppressWarnings("static-access")
	public ILoan getLoan() {
		ILoan loan = null;
		if (eBookState_ == eBookState_.ON_LOAN)
		{
			loan = associatedLoan_;
		}
		return loan;
	}

	@SuppressWarnings("static-access")
	public void returnBook(boolean damaged) {
		if (eBookState_ == eBookState_.ON_LOAN && damaged == true)
		{
			eBookState_ = eBookState_.DAMAGED;
		}
		else if (eBookState_ == eBookState_.ON_LOAN && damaged == false)
		{
			eBookState_ = eBookState_.AVAILABLE;			
		}
		else
		{
			throw new RuntimeException("This book cannot be returned as it is not 'on loan'.");			
		}
	}

	@SuppressWarnings("static-access")
	public void lose() {
		if (eBookState_ == eBookState_.ON_LOAN)
		{
			eBookState_ = eBookState_.LOST;
		}
		else
		{
			throw new RuntimeException("This book cannot be deemed as lost because it is not 'on loan'.");
		}	
	}

	@SuppressWarnings("static-access")
	public void repair() {
		if (eBookState_ == eBookState_.DAMAGED)
		{
			eBookState_ = eBookState_.AVAILABLE;
		}
		else
		{
			throw new RuntimeException("This book is not in a damaged state.");
		}
	}

	@SuppressWarnings("static-access")
	public void dispose() {
		if (eBookState_ == eBookState_.AVAILABLE || eBookState_ == eBookState_.DAMAGED || eBookState_ == eBookState_.LOST)
		{
			eBookState_ = eBookState_.DISPOSED;
		}
		else
		{
			throw new RuntimeException("This book cannot be disposed because it is either 'on loan' or has already been disposed.");
		}
	}

	public EBookState getState() {
		return eBookState_;
	}

	public String getAuthor() {
		return author_;
	}

	public String getTitle() {
		return title_;
	}

	public String getCallNumber() {
		return callNumber_;
	}

	public int getID() {
		return bookId_;
	}

}
