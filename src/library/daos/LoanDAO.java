package library.daos;

import java.util.Date;
import java.util.List;

import library.interfaces.daos.ILoanDAO;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class LoanDAO implements ILoanDAO{

	@Override
	public ILoan createLoan(IMember borrower, IBook book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void commitLoan(ILoan loan) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ILoan getLoanByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILoan getLoanByBook(IBook book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ILoan> listLoans() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ILoan> findLoansByBorrower(IMember borrower) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ILoan> findLoansByBookTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateOverDueStatus(Date currentDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ILoan> findOverDueLoans() {
		// TODO Auto-generated method stub
		return null;
	}

}
