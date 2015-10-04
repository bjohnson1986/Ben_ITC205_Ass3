package library;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.IBorrowUIListener;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.ICardReaderListener;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;
import library.interfaces.hardware.IScannerListener;
import library.panels.borrow.ConfirmLoanPanel;

@SuppressWarnings("unused")
public class BorrowUC_CTL implements ICardReaderListener, 
									 IScannerListener, 
									 IBorrowUIListener {
	
	private ICardReader reader_;
	private IScanner scanner_; 
	private IPrinter printer_; 
	private IDisplay display_;
	private int scanCount_ = 0;
	private IBorrowUI ui_;
	private EBorrowState borrowState_; 
	private EBookState bookState_;
	private IBookDAO bookDAO_;
	private IMemberDAO memberDAO_;
	private ILoanDAO loanDAO_;
	
	private List<IBook> bookList_;
	private List<ILoan> loanList_;
	private IMember borrower_;
	
	private JPanel previous_;
	private ConfirmLoanPanel confirmLoanPanel_;

	public BorrowUC_CTL(ICardReader reader, IScanner scanner, 
			IPrinter printer, IDisplay display_,
			IBookDAO bookDAO, ILoanDAO loanDAO, IMemberDAO memberDAO ) {

		this.reader_ = reader;
		this.scanner_ = scanner;
		this.display_ = display_;
		this.printer_ = printer;
        this.bookDAO_ = bookDAO;
        this.loanDAO_ = loanDAO;
        this.bookList_ = new ArrayList<>();
        this.loanList_ = new ArrayList<>();
        this.memberDAO_ = memberDAO;
		this.reader_.setEnabled(false);
		this.scanner_.setEnabled(false);
		this.ui_ = new BorrowUC_UI(this);
		borrowState_ = EBorrowState.CREATED;
	}
	
	@SuppressWarnings("static-access")
	public void initialise() {
		if (borrowState_ == borrowState_.CREATED)
		{
		previous_ = display_.getDisplay();
		display_.setDisplay((JPanel) ui_, "Borrow UI");
		reader_.addListener(this);
		scanner_.addListener(this);
		this.reader_.setEnabled(true);
		this.scanner_.setEnabled(false);
		setState(borrowState_.INITIALIZED);
		}
		else
		{
			throw new RuntimeException("The system is not created state.");				
		}
	}
	
	public void close() {
		display_.setDisplay(previous_, "Main Menu");
	}

	@SuppressWarnings("static-access")
	@Override
	public void cardSwiped(int memberID) {
		if (borrowState_ == borrowState_.INITIALIZED)
		{
				borrower_ = memberDAO_.getMemberByID(memberID);
				if(borrower_.hasOverDueLoans() || borrower_.hasReachedLoanLimit() || borrower_.hasFinesPayable() || borrower_.hasReachedFineLimit())
				{
					setState(borrowState_.BORROWING_RESTRICTED);
					ui_.displayMemberDetails(borrower_.getID(), borrower_.getFirstName(), borrower_.getLastName());
					List <ILoan> currentLoans = borrower_.getLoans();
					ui_.displayExistingLoan(buildLoanListDisplay(currentLoans));
					ui_.displayOutstandingFineMessage(borrower_.getFineAmount());
					ui_.displayOverDueMessage();
					ui_.displayAtLoanLimitMessage();
					ui_.displayErrorMessage("Error, you cannot currently borrow items.");
					 }
				 else
				 {
					setState(borrowState_.SCANNING_BOOKS);
					reader_.setEnabled(false);
					scanner_.setEnabled(true);
					ui_.displayMemberDetails(borrower_.getID(), borrower_.getFirstName(), borrower_.getLastName());
					List <ILoan> currentLoans = borrower_.getLoans();
					if (currentLoans != null){
						ui_.displayExistingLoan(buildLoanListDisplay(currentLoans));
					}
					if (borrower_.getFineAmount() > 0){
						ui_.displayOutstandingFineMessage(borrower_.getFineAmount());
					}
					reader_.setEnabled(false);
					scanner_.setEnabled(true);			
				 }			 
		}
		else{
			throw new RuntimeException("The system is not initialized.");		
		}
		
	}
	
		
	@SuppressWarnings("static-access")
	@Override
	public void bookScanned(int barcode) {
        if (borrowState_ == borrowState_.SCANNING_BOOKS)
        {
            IBook book = bookDAO_.getBookByID(barcode);
            if (book == null)
            {
                ui_.displayErrorMessage("The scanned item does not belong to this library.");
            }
            else
            {
            	bookState_ = book.getState();
            	boolean bookOnList = bookList_.contains(book);
            			
            	if (bookState_ != bookState_.AVAILABLE)
            	{
            		ui_.displayErrorMessage("This item is not currently available.");
            	}
            	if (bookList_.contains(book))
            	{
            		ui_.displayErrorMessage("This item has already been scanned.");
            	}
            	if ((bookState_ == bookState_.AVAILABLE) && (!bookList_.contains(book)))
            	{
            		ILoan newLoan = loanDAO_.createLoan(borrower_, book);
            		scanCount_ = scanCount_ + 1;
            		bookList_.add(book);
            		loanList_.add(newLoan);
            		
            		String bookDetails = book.getID() + " " + book.getTitle() + " " + book.getAuthor() + " " + book.getCallNumber();
            		ui_.displayScannedBookDetails(bookDetails);
            		
            		
            		String loanDetails = buildLoanList(loanList_);
            		ui_.displayPendingLoan(loanDetails);
            		
            		
            	}
            }
	}
	}
	
	@SuppressWarnings("static-access")
	private void setState(EBorrowState state) {
		this.borrowState_ = state;
        this.ui_.setState(state);

        
        if(borrowState_ == borrowState_.INITIALIZED)
        {
            reader_.setEnabled(true);
            scanner_.setEnabled(false);
        }
        else if(borrowState_ == borrowState_.SCANNING_BOOKS)
        {
            reader_.setEnabled(false);
            scanner_.setEnabled(true);
        }
        else if(borrowState_ == borrowState_.CONFIRMING_LOANS)
        {
            reader_.setEnabled(false);
            scanner_.setEnabled(false);
        }
        else if(borrowState_ == borrowState_.COMPLETED)
        {
            reader_.setEnabled(false);
            scanner_.setEnabled(false);
            display_.setDisplay(previous_, "");
        }
        else if(borrowState_ == borrowState_.BORROWING_RESTRICTED)
        {
            reader_.setEnabled(false);
            scanner_.setEnabled(false);
        }
        else if(borrowState_ == borrowState_.CANCELLED)
        {
            reader_.setEnabled(false);
            scanner_.setEnabled(false);
            display_.setDisplay(previous_, "");
        }
        else
        {
            throw new RuntimeException("An error has occurred.");
        }
	}

	public EBorrowState getState() {
		return borrowState_;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void cancelled() {
		reader_.setEnabled(false);
		scanner_.setEnabled(false);
		setState(borrowState_.CANCELLED);
		close(); //Sets display_ to previous
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void scansCompleted() {
        if(borrowState_ == borrowState_.SCANNING_BOOKS)
        {
            if (loanList_.size() > 0)
            {
        		reader_.setEnabled(false);
        		scanner_.setEnabled(false);
                setState(EBorrowState.CONFIRMING_LOANS);
				String loanDetails = "";
				for (ILoan loan : loanList_)
				{
					loanDetails = loanDetails + loan.toString();
				}
                ui_.displayConfirmingLoan(loanDetails);              
            }
            else
            {
                ui_.displayErrorMessage("Please scan an item, cannot process an empty loan list.");
            }
        }
        else
        {
            throw new RuntimeException("Error, either you are not yet at the stage of completing the transaction, or you are past it.");
        }		
	}

	@SuppressWarnings("static-access")
	@Override
	public void loansConfirmed() {
        if (borrowState_ == borrowState_.CONFIRMING_LOANS)
        {
            if (loanList_.size() > 0)
            {
            	setState(borrowState_.COMPLETED);
        		String loanDetails = "";	
				for (ILoan loan : loanList_)
				{
					loanDAO_.commitLoan(loan);
					loanDetails = loanDetails + loan.toString();
				}
				printer_.print(loanDetails);

				reader_.setEnabled(false);
				scanner_.setEnabled(false);
				display_.setDisplay((JPanel) ui_, "Previous");
            }
            else
	        {
            	throw new RuntimeException("Please scan an item, cannot process an empty loan list.");
	        }
        }
        else
        {
            throw new RuntimeException("Error, either you are not yet at the stage of completing the transaction, or you are past it.");
	    }
		
	}

	@SuppressWarnings("static-access")
	@Override
	public void loansRejected() {
	        if (borrowState_ == borrowState_.CONFIRMING_LOANS)
	        {
	            if (loanList_.size() > 0)
	            {
	                setState(borrowState_.SCANNING_BOOKS);
	                loanList_ = new ArrayList<>();
	                bookList_ = new ArrayList<>();
	                scanCount_ = 0;
					ui_.displayMemberDetails(borrower_.getID(), borrower_.getFirstName(), borrower_.getLastName());
					List <ILoan> currentLoans = borrower_.getLoans();
					ui_.displayExistingLoan(buildLoanList(currentLoans));
	            }
	            else
	            {
	                throw new RuntimeException("No loans exist.");
	            }
	        }
	        else
	        {
	            throw new RuntimeException("The borrower has not completed scanning items.");
	        }
	    }
	private String buildLoanListDisplay(List<ILoan> loans) {
		System.out.println(loans.size());
		StringBuilder bld = new StringBuilder();
		for (ILoan ln : loans) {
			if (bld.length() > 0) bld.append("\n\n");
			bld.append(ln.toString());
		}
		return bld.toString();		
	}
	
    private String buildLoanList(List<ILoan> loanList)
    {
        String loanDetail = "";
        if (loanList.size() > 0)
        {
	        for (ILoan loanList1: loanList)
	        {
	        	if (loanList1 != null)
	        	{
	            loanDetail = loanDetail + loanList1.toString();
	        	}
	        }
        }
        return loanDetail;
    }
    
	public List<ILoan> getLoanList()
	{
		return loanList_;
	}
	public int getScanCount()
	{
		return scanCount_;
	}

}
