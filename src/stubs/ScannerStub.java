package stubs;

import library.interfaces.hardware.IScannerListener;


public class ScannerStub {
	private IScannerListener listener_;
	private boolean isEnabled_;

	public ScannerStub() {
		isEnabled_ = false;
	}
	
	public IScannerListener getListener(IScannerListener listener) {
		return this.listener_;
	}
	
	public void addListener(IScannerListener listener) {
		this.listener_ = listener;	
	}
	
	public boolean getEnabled() {
		return isEnabled_;
	}
	
	public void setEnabled(boolean enabler) {
		isEnabled_ = enabler;
	}
	

}
