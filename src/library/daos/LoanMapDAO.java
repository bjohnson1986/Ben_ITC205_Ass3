package library.daos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class LoanMapDAO implements ILoanDAO{
	
	private ILoanHelper iLoanHelper_;
	private List<ILoan> loanList_;
	private int nextLoanId_;

	public LoanMapDAO(ILoanHelper iLoanHelper)
	throws IllegalArgumentException
	{
		if(iLoanHelper == null)
		{
			throw new IllegalArgumentException("Error, ILoanHelper cannot be null.");
		}
		else
		{
			iLoanHelper_ = iLoanHelper;
			loanList_ = new ArrayList<ILoan>();
			nextLoanId_ = 0;
		}
	}
	@SuppressWarnings("static-access")
	public ILoan createLoan(IMember borrower, IBook book) {
		ILoan loan = null;
		if(borrower == null)
		{
			throw new IllegalArgumentException("Error, borrower cannot be null.");
		}
		if(book == null)
		{
			throw new IllegalArgumentException("Error, book cannot be null.");
		}
		if((borrower != null) && (book != null))
		{
			Calendar dueDateCal = Calendar.getInstance();
			Date borrowDate = new Date();
			dueDateCal.add(Calendar.DAY_OF_MONTH, loan.LOAN_PERIOD);//Might need rework.
			Date dueDate = dueDateCal.getTime();
			loan = iLoanHelper_.makeLoan(book, borrower, borrowDate, dueDate);
		}
		return loan;
	}

	@Override
	public void commitLoan(ILoan loan) {
		loan.commit(nextLoanId_);
		loanList_.add(loan);
		nextLoanId_ ++;
	}

	@Override
	public ILoan getLoanByID(int id) {
		try
		{
			//Loan exists
			return loanList_.get(id);
		}
		catch (Exception e){
			//Loan does not exist
			return null;
		}
	}

	@Override
	public List<ILoan> listLoans() {
		return loanList_;
	}

	public List<ILoan> findLoansByBorrower(IMember borrower) {
		ArrayList<ILoan> loanMatchesBorrower = new ArrayList<ILoan>();
		//Loop over loan list, if a loan is associated with the borrower, then add to the match list.
		for (ILoan loan : loanList_)
		{
			if (loan.getBorrower() == borrower)
			{
				loanMatchesBorrower.add(loan);
			}
		}
		return loanMatchesBorrower;
	}

	public List<ILoan> findLoansByBookTitle(String title) {
		ArrayList<ILoan> loanMatchesBookTitle = new ArrayList<ILoan>();
		//Loop over loan list, if a loan is associated with the book title, then add to the match list.
		for (ILoan loan : loanList_)
		{
			IBook book = loan.getBook();
			String bookTitle = book.getTitle();
			if(bookTitle == title)
			{
				loanMatchesBookTitle.add(loan);
			}
		}
		return loanMatchesBookTitle;
	}

	@Override
	public void updateOverDueStatus(Date currentDate) {
		//Loop over loan list and update overdue status if overdue.
		for (ILoan loan : loanList_)
		{
			loan.checkOverDue(currentDate);
		}
	}

	@Override
	public List<ILoan> findOverDueLoans() {
		ArrayList<ILoan> overdueLoans = new ArrayList<ILoan>();
		//Loop over loan list, if a loan is overdue, then add to the overdue list.
		for (ILoan loan : loanList_)
		{
			if(loan.isOverDue())
			{
				overdueLoans.add(loan);
			}
		}
		return overdueLoans;
	}

}
