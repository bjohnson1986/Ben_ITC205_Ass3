package stubs;

import javax.swing.JPanel;

public class DisplayStub {
	
	private JPanel display_;
	private String id_;
	
	public DisplayStub()
	{
		
	}

	public JPanel getDisplay()
	{
		return display_;
	}
	
	public void setDisplay(JPanel display, String id)
	{
		display_ = display;
		id_ = id;
	}
	
	public String getId()
	{
		return id_;
	}

}
