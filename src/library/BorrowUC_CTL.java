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
	
	private ICardReader reader;
	private IScanner scanner; 
	private IPrinter printer; 
	private IDisplay display;
	//private String state;
	private int scanCount = 0;
	private IBorrowUI ui;
	private EBorrowState borrowState; 
	private EBookState bookState;
	private IBookDAO bookDAO;
	private IMemberDAO memberDAO;
	private ILoanDAO loanDAO;
	
	private List<IBook> bookList;
	private List<ILoan> loanList;
	private IMember borrower;
	
	private JPanel previous;
	private ConfirmLoanPanel confirmLoanPanel;


	public BorrowUC_CTL(ICardReader reader, IScanner scanner, 
			IPrinter printer, IDisplay display,
			IBookDAO bookDAO, ILoanDAO loanDAO, IMemberDAO memberDAO ) {

		this.reader = reader;
		this.scanner = scanner;
		//this.confirmLoanPanel = confi
		this.display = display;
		this.ui = new BorrowUC_UI(this);
		borrowState = EBorrowState.CREATED;
	}
	
	@SuppressWarnings("static-access")
	public void initialise() {
		previous = display.getDisplay();
		display.setDisplay((JPanel) ui, "Borrow UI");
		reader.addListener(this);
		scanner.addListener(this);
		setState(borrowState.INITIALIZED);
	}
	
	public void close() {
		display.setDisplay(previous, "Main Menu");
	}

	@SuppressWarnings("static-access")
	@Override
	public void cardSwiped(int memberID) {
		if (borrowState == borrowState.INITIALIZED)
		{
			memberDAO.getMemberByID(memberID);
			if(borrower.hasOverDueLoans() || borrower.hasReachedLoanLimit() || borrower.hasFinesPayable() || borrower.hasReachedFineLimit())
				 {
				 ui.displayMemberDetails(borrower.getID(), borrower.getFirstName(), borrower.getLastName());
				 ui.displayExistingLoan("");//Need to parse loan info
				 ui.displayOutstandingFineMessage(borrower.getFineAmount());
				 ui.displayOverDueMessage();
				 ui.displayAtLoanLimitMessage();
				 ui.displayErrorMessage("");//Set error message
				 }
			 else
			 {
				setState(borrowState.SCANNING_BOOKS);
				reader.setEnabled(true);
				scanner.setEnabled(true);
				ui.setState(borrowState.SCANNING_BOOKS);			 
				ui.displayScannedBookDetails("");//Add param
				ui.displayPendingLoan("");//Add param
				ui.displayOutstandingFineMessage(0.0f);//Add param
			 
				reader.setEnabled(true);
				scanner.setEnabled(true);
				
				String loanDetails = "";
				List <ILoan> loanList = borrower.getLoans();
				for (ILoan loan : loanList)
				{
					loanDetails = loanDetails + loan.toString();
				}
				ui.displayExistingLoan(loanDetails);
				
			 }
			 
		}
		else{
			throw new RuntimeException("The system is not initialized.");		
		}
		
	}
	
		
	@SuppressWarnings("static-access")
	@Override
	public void bookScanned(int barcode) {
        if (borrowState == borrowState.SCANNING_BOOKS)
        {
            IBook book = bookDAO.getBookByID(barcode);
            if (book == null)
            {
                ui.displayErrorMessage("The scanned item does not belong to this library.");
            }
            else
            {
            	bookState = book.getState();
            	int id = book.getID();
            	boolean bookOnList = bookList.contains(book);
            			
            	if (bookState != bookState.AVAILABLE)
            	{
            		ui.displayErrorMessage("This item is not currently available.");
            	}
            	if (!bookOnList)
            	{
            		ui.displayErrorMessage("This item has already been scanned.");
            	}
            	if (bookState == bookState.AVAILABLE)
            	{
            		ILoan newLoan = loanDAO.createLoan(borrower, book);
            		scanCount = scanCount + 1;
            		bookList.add(book);
            		loanList.add(newLoan);
            		
            		String bookDetails = book.getID() + " " + book.getTitle() + " " + book.getAuthor() + " " + book.getCallNumber();
            		ui.displayScannedBookDetails(bookDetails);
    				String loanDetails = "";
    				for (ILoan loan : loanList)
    				{
    					loanDetails = loanDetails + loan.toString();
    				}
            		ui.displayPendingLoan(loanDetails);
            		
            		
            	}
            }
	}
	}
	
	private void setState(EBorrowState state) {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public void cancelled() {
		//System.out.println("Flag");
		close();
	}
	
	@Override
	public void scansCompleted() {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public void loansConfirmed() {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public void loansRejected() {
		throw new RuntimeException("Not implemented yet");
	}

	private String buildLoanListDisplay(List<ILoan> loans) {
		StringBuilder bld = new StringBuilder();
		for (ILoan ln : loans) {
			if (bld.length() > 0) bld.append("\n\n");
			bld.append(ln.toString());
		}
		return bld.toString();		
	}

}
