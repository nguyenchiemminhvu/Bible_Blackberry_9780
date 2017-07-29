package mypackage;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.component.table.SimpleList;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class BookSelectionScreen extends MainScreen {

	// ==========================================================================
	// private properties
	
	private HorizontalFieldManager 	topManager;
	private VerticalFieldManager	midManager;
	private HorizontalFieldManager	botManager;
	
	private SimpleList				oldTestament;
	private SimpleList				newTestament;
	
	// ==========================================================================
	// public methods
	
	public BookSelectionScreen() 
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
	// protected methods
	
	

	// ==========================================================================
	// private methods
	
	private void initUI()
	{
		initTopManager();
		initMidManager();
		initBotManager();
		
		this.add(topManager);
		this.add(new SeparatorField(SeparatorField.LINE_HORIZONTAL));
		this.add(midManager);
		this.add(new SeparatorField(SeparatorField.LINE_HORIZONTAL));
	}
	
	private void initTopManager()
	{
		topManager = new HorizontalFieldManager();
		
		// bible icon
		Bitmap topIconBMP = Bitmap.getBitmapResource("bible_small.png");
		BitmapField topIcon = new BitmapField();
		topIcon.setBitmap(topIconBMP);
		topManager.add(topIcon);
		
		// bible title
		LabelField topTitle = new LabelField("Bible", LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH);
		topTitle.setMargin(0, 0, 0, 10);
		topManager.add(topTitle);
	}
	
	private void initMidManager()
	{
		midManager = new VerticalFieldManager(VERTICAL_SCROLLBAR);
		
		// ======================================================
		// init list of book
		
		LabelField oldLabel = new LabelField("Old Testament", LabelField.USE_ALL_WIDTH | LabelField.ELLIPSIS);
		midManager.add(oldLabel);
		
		oldTestament = new SimpleList(midManager);
		
		
		SeparatorField midLine = new SeparatorField(SeparatorField.LINE_HORIZONTAL);
		midLine.setMargin(0, 0, 5, 0);
		midManager.add(midLine);
		
		LabelField newLabel = new LabelField("New Testament", LabelField.USE_ALL_WIDTH | LabelField.ELLIPSIS);
		midManager.add(newLabel);
		
		newTestament = new SimpleList(midManager);
		
	}
	
	private void initBotManager()
	{
		botManager = new HorizontalFieldManager();
	}
}
