package stubs;

public class PrinterStub {
	
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
