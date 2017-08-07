package mypackage;

import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.container.MainScreen;

public class TestamentSelectionScreen extends MainScreen {


	private ButtonField buttonOldTestament;
	private ButtonField buttonNewTestament;
	
	
	// ==========================================================================
	// public methods

	public TestamentSelectionScreen()
	{
		super();
		initUI();
	}
	
	public boolean onClose()
	{
		System.exit(0);
		return true;
	}
	
	
	// ==========================================================================
	// private methods
	
	private void initUI()
	{
		
	}
}
