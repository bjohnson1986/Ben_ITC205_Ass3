package stubs;

import library.interfaces.hardware.IPrinter;

public class PrinterStub implements IPrinter{
	
	private String text_;
	
	public PrinterStub()
	{
		
	}
	
	public void print(String data)
	{
		text_ = data;
	}
	
	public String getText()
	{
		return text_;
	}


}
