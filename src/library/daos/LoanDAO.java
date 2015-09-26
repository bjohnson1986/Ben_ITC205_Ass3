package library.daos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class LoanDAO implements ILoanDAO{
	
	private ILoanHelper iLoanHelper_;
	private List<ILoan> loanList_;
	private int nextLoanId_;

	public LoanDAO(ILoanHelper iLoanHelper)
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
	public ILoan createLoan(IMember borrower, IBook book) {
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
			//ILoan loan = iLoanHelper_.makeLoan(book, borrower); - How do I know the borrowDate and dueDate
			//return loan;
		}
	}

	@Override
	public void commitLoan(ILoan loan) {
		//Assigns the loan a unique Id
		//Stores the loan
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
	public ILoan getLoanByBook(IBook book) {
//		ArrayList<ILoan> loanMatchesBook = new ArrayList<ILoan>();
//		//Loop over loan list, if a loan is associated with the book then add to the match list.
//		for (ILoan loan : loanList_)
//		{
//			if (book.getAuthor() == author)
//			{
//				authorMatchList.add(book);
//			}
//		}
//		return authorMatchList;
	}

	@Override
	public List<ILoan> listLoans() {
		// TODO Auto-generated method stub
		return null;
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
//		ArrayList<ILoan> loanMatchesBookTitle = new ArrayList<ILoan>();
//		//Loop over loan list, if a loan is associated with the book title, then add to the match list.
//		for (ILoan loan : loanList_)
//		{
//			//Verify Book Title
//		}
//		return loanMatchesBookTitle;
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
