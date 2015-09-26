package library.entities;

import java.util.Date;

import library.interfaces.entities.ELoanState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class Loan implements ILoan{
	private IBook book_;
	private IMember borrower_;
	@SuppressWarnings("unused")
	private Date borrowDate_; //Seems borrowDate_ is never used.
	private Date dueDate_;
	private int loanId_;
	private ELoanState eLoanState_;
	
	public Loan(IBook book, IMember borrower, Date borrowDate, Date dueDate)
	throws IllegalArgumentException
	{
		if(book == null)
		{
			throw new IllegalArgumentException("Error, missing book.");
		}
		if(borrower == null)
		{
			throw new IllegalArgumentException("Error, missing borrower.");
		}
		if(borrowDate == null)
		{
			throw new IllegalArgumentException("Error, missing borrow date.");
		}
		if(dueDate == null)
		{
			throw new IllegalArgumentException("Error, missing due date.");
		}
		//Check if dueDate is before borrowDate
		if(dueDate.compareTo(borrowDate)<=0)
		{
			throw new IllegalArgumentException("Error, due date must be equal to, or after borrow date.");
		}
		//All variables are valid, construct the object.
		if((book != null) && (borrower != null) && (borrowDate != null) && (dueDate != null) && (dueDate.compareTo(borrowDate)>0))
		{
			book_ = book;
			borrower_ = borrower;
			borrowDate_ = borrowDate;
			dueDate_ = dueDate;
			loanId_= 0;
		}
	}
	
	@SuppressWarnings("static-access")
	public void commit(int id) {
		if (id < 0)
		{
			throw new IllegalArgumentException("Error, loan identification number cannot be less than zero.");			
		}
		if (eLoanState_ != eLoanState_.PENDING)
		{
			throw new RuntimeException("Error, the loan must be in a 'pending' state in order to be committed.");				
		}
		if ((id >= 0) && (eLoanState_ == eLoanState_.PENDING))
		{
			eLoanState_ = eLoanState_.CURRENT;
			book_.borrow(this);
			borrower_.addLoan(this);		
		}	
	}

	@SuppressWarnings("static-access")
	public void complete() {
		if((eLoanState_ == eLoanState_.CURRENT) || (eLoanState_ != eLoanState_.OVERDUE)){
			eLoanState_ = eLoanState_.COMPLETE;
		}
		else
		{
			throw new RuntimeException("Error, cannot complete loan if the loan is not current or overdue.");
		}
		
	}

	@SuppressWarnings("static-access")
	public boolean isOverDue() {
		if(eLoanState_ == eLoanState_.OVERDUE)
		{
			return true;
		}
		else{
			return false;
		}
	}

	@SuppressWarnings("static-access")
	public boolean checkOverDue(Date currentDate) {
		if ((eLoanState_ == eLoanState_.CURRENT) || (eLoanState_ == eLoanState_.OVERDUE))
		{
			if(currentDate.compareTo(dueDate_)>0)
			{
				eLoanState_ = eLoanState_.OVERDUE;
				return true;
			}
			else
			{
				return false;
			}
		}
		else{
			throw new RuntimeException("Error, the loan must be in a state of current or overdue.");
		}
	}

	public IMember getBorrower() {
		return borrower_;
	}

	public IBook getBook() {
		return book_;
	}

	public int getID() {
		return loanId_;
	}

	@SuppressWarnings("static-access")
	public boolean isCurrent() {
		if(eLoanState_ == eLoanState_.CURRENT)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
