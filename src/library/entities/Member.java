package library.entities;

import java.util.List;

import library.interfaces.entities.EMemberState;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class Member implements IMember{

	private String firstName_;
	private String lastName_;
	private String contactPhone_;
	private String emailAddress_;
	private int id_;
	private EMemberState eMemberState_;
	private List<ILoan> loanList_;
	private float finesOwing_;
	private int currentLoans_;
	
	public Member(String firstName, String lastName, String contactPhone, String emailAddress, int id)
	throws IllegalArgumentException
	{
		if(firstName == null)
		{
			throw new RuntimeException("Error, first name cannot be null.");
		}
		if(firstName == "")
		{
			throw new RuntimeException("Error, first name cannot be blank.");
		}
		if(lastName == null)
		{
			throw new RuntimeException("Error, last name cannot be null.");
		}
		if(lastName == "")
		{
			throw new RuntimeException("Error, last name cannot be blank.");
		}
		if(contactPhone == null)
		{
			throw new RuntimeException("Error, phone number cannot be null.");
		}
		if(contactPhone == "")
		{
			throw new RuntimeException("Error, phone number cannot be blank.");
		}
		if(emailAddress == null)
		{
			throw new RuntimeException("Error, email address cannot be null.");
		}
		if(emailAddress == "")
		{
			throw new RuntimeException("Error, email address cannot be blank.");
		}
		if(id < 0)//Specification stipulates "less than or equal to zero" - but why?
		{
			throw new RuntimeException("Error, member identification cannot be less than zero.");			
		}
		//All variables are valid, construct the object.
		if(((firstName != null) && (firstName != "")) && ((lastName != null) && (lastName != "")) && ((contactPhone != null) && (contactPhone != "") && ((emailAddress != null) && (emailAddress != "")) && (id >= 0)))
		{
			firstName_ = firstName;
			lastName_ = lastName;
			contactPhone_ = contactPhone;
			emailAddress_ = emailAddress;
			id_ = id;
		}
	}
	
	public boolean hasOverDueLoans() {
		boolean isOverdue = false;
		for(ILoan i : loanList_)
		{
			if(i.isOverDue())
			{
				isOverdue = true;
			}
		}
		return isOverdue;
	}

	public boolean hasReachedLoanLimit() {
		if(currentLoans_ == LOAN_LIMIT)
		{
			return true;
		}
		else{
			return false;
		}
	}

	public boolean hasFinesPayable() {
		if(finesOwing_ > 0)
		{
			return true;
		}
		else{
			return false;
		}
	}

	public boolean hasReachedFineLimit() {
		if(finesOwing_ >= FINE_LIMIT)
		{
			return true;
		}
		else{
			return false;
		}
	}

	public float getFineAmount() {
		return finesOwing_;
	}

	public void addFine(float fine) {
	if(fine >= 0)
	{
		finesOwing_ += fine;		
	}
	else{
		throw new IllegalArgumentException("Error, fines must be non-negative amounts.");
	}	
	}

	public void payFine(float payment) {
		if((payment >= 0) && (payment <= finesOwing_))
		{
			finesOwing_ = finesOwing_ - payment;
		}
		if(payment < 0)
		{
			throw new IllegalArgumentException("Error, payment must be a non-negative amount.");
		}
		if(payment > finesOwing_)
		{
			throw new IllegalArgumentException("Error, payment cannot be more than due amount.");			
		}
		
	}

	@SuppressWarnings("static-access")
	public void addLoan(ILoan loan) {
		if(loan == null)
		{
			throw new IllegalArgumentException("Error, loan cannot be null.");
		}
		if(eMemberState_ == eMemberState_.BORROWING_DISALLOWED){
			throw new IllegalArgumentException("Error, member cannot borrow at this time.");
		}
		if((loan != null) && (eMemberState_ != eMemberState_.BORROWING_DISALLOWED))
		{
			loanList_.add(loan);
		}
	}

	public List<ILoan> getLoans() {
		return loanList_;		
	}

	public void removeLoan(ILoan loan) {
		
		boolean isFound = false;
		
		for(ILoan i : loanList_)
		{
			if(i == loan)
			{
				isFound = true;
			}
		}
		if(loan == null)
		{
			throw new IllegalArgumentException("Error, loan cannot be null.");
		}
		if(!isFound){
			throw new IllegalArgumentException("Error, loan does not exist.");
		}
		if((loan != null) && (isFound))
		{
			loanList_.remove(loan);
		}
	}

	public EMemberState getState() {
		return eMemberState_;
	}

	public String getFirstName() {
		return firstName_;
	}

	public String getLastName() {
		return lastName_;
	}

	public String getContactPhone() {
		return contactPhone_;
	}

	public String getEmailAddress() {
		return emailAddress_;
	}

	public int getID() {
		return id_;
	}

}
