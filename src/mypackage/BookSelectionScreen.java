package mypackage;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.ButtonFieldFactory;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.image.Image;
import net.rim.device.api.ui.image.ImageFactory;

public class BookSelectionScreen extends MainScreen {

	// ==========================================================================
	// private properties
	
	private HorizontalFieldManager 	topManager;
	private VerticalFieldManager	midManager;
	private HorizontalFieldManager	botManager;
	
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
		this.add(botManager);
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
		midManager = new VerticalFieldManager();
		
		// ===================================================================
		// button listeners
		FieldChangeListener oldTestamentListener = new FieldChangeListener()
		{	
			public void fieldChanged(Field field, int context) 
			{
				((ButtonField)field).setLabel(((ButtonField)field).getLabel() + ".");
			}
		};
		
		FieldChangeListener newTestamentListener = new FieldChangeListener() 
		{	
			public void fieldChanged(Field field, int context) 
			{
				((ButtonField)field).setLabel(((ButtonField)field).getLabel() + ".");
			}
		};
		
		// ==================================================================
		// buttons
		ButtonField oldTestament = new ButtonField("Old Testament");
		oldTestament.setChangeListener(oldTestamentListener);
		
		ButtonField newTestament = new ButtonField("New Testament");
		newTestament.setChangeListener(newTestamentListener);
		
		midManager.add(oldTestament);
		midManager.add(newTestament);
	}
	
	private void initBotManager()
	{
		botManager = new HorizontalFieldManager();
	}
}
