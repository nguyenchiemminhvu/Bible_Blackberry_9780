package mypackage;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.Dialog;
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
		
		// old testament
		LabelField oldLabel = null;
		if(AppSettings.getInstance().appLanguage.compareTo("English") == 0)
			oldLabel = new LabelField("Old Testament", LabelField.USE_ALL_WIDTH | LabelField.ELLIPSIS);
		else if(AppSettings.getInstance().appLanguage.compareTo("Vietnamese") == 0)
			oldLabel = new LabelField("Cựu ước", LabelField.USE_ALL_WIDTH | LabelField.ELLIPSIS);
			
		midManager.add(oldLabel);
		
		oldTestament = new SimpleList(midManager);
		
		// load old testament books
		{
			String oldTestamentDir = AppSettings.getInstance().APP_DATA_ON_SD_CARD;
			oldTestamentDir += "books/" + AppSettings.getInstance().appLanguage + "/old_testament/";
			
			FileConnection fConnection = null;
			
			try {
				fConnection = (FileConnection) Connector.open(oldTestamentDir);
				
				if (fConnection.exists())
				{
					//todo
					Vector books = new Vector();
					Enumeration listBook = fConnection.list();
					int numberOfOldTestamentBook = 0;
					
					while(listBook.hasMoreElements())
					{
						String book = (String) listBook.nextElement();
						book = book.substring(0, book.length() - 1);
						books.addElement(book);
						numberOfOldTestamentBook++;
					}
				}
				
				fConnection.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				if(fConnection != null && fConnection.isOpen())
					fConnection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		SeparatorField midLine = new SeparatorField(SeparatorField.LINE_HORIZONTAL);
		midLine.setMargin(0, 0, 5, 0);
		midManager.add(midLine);
		
		// new testament
		LabelField newLabel = null;
		if(AppSettings.getInstance().appLanguage.compareTo("English") == 0)
			newLabel = new LabelField("New Testament", LabelField.USE_ALL_WIDTH | LabelField.ELLIPSIS);
		else if(AppSettings.getInstance().appLanguage.compareTo("Vietnamese") == 0)
			newLabel = new LabelField("Tân ước", LabelField.USE_ALL_WIDTH | LabelField.ELLIPSIS);
		
		midManager.add(newLabel);
		
		newTestament = new SimpleList(midManager);
		
		// load new testament books
		{
			String oldTestamentDir = AppSettings.getInstance().APP_DATA_ON_SD_CARD;
			oldTestamentDir += "books/" + AppSettings.getInstance().appLanguage + "/new_testament/";
			
			FileConnection fConnection = null;
			
			try {
				fConnection = (FileConnection) Connector.open(oldTestamentDir);
				
				if (fConnection.exists())
				{
					//todo
					Vector books = new Vector();
					Enumeration listBook = fConnection.list();
					int numberOfNewTestamentBook = 0;
					
					while(listBook.hasMoreElements())
					{
						String book = (String) listBook.nextElement();
						book = book.substring(0, book.length() - 1);
						books.addElement(book);
						numberOfNewTestamentBook++;
					}
				}
				
				fConnection.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				if(fConnection != null && fConnection.isOpen())
					fConnection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void initBotManager()
	{
		botManager = new HorizontalFieldManager();
	}
}
