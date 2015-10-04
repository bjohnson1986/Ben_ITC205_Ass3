package stubs;

import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.ICardReaderListener;

public class CardReaderStub implements ICardReader{
	private boolean isEnabled_;
	private ICardReaderListener listener_;
	
	
	public CardReaderStub()
	{
		isEnabled_ = false;
	}	
	
	public boolean getEnabled()
	{
		return isEnabled_;
	}

	public void setEnabled(boolean enabler) {
		isEnabled_ = enabler;
	}

	public void addListener(ICardReaderListener listener) {
		this.listener_ = listener;
	}
	
	public ICardReaderListener getListener(ICardReaderListener listener) {
		return this.listener_;
	}
	
}
