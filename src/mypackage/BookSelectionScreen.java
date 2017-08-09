package mypackage;

import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import com.google.zxing.common.Collections;
import com.google.zxing.common.Comparator;

import net.rim.device.api.command.Command;
import net.rim.device.api.command.CommandHandler;
import net.rim.device.api.command.ReadOnlyCommandMetadata;
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
	
	private SimpleList				books;
	
	// ==========================================================================
	// public methods
	
	public BookSelectionScreen() throws Exception
	{
		super();
		initUI();
	}
	

	// ==========================================================================
	// private methods

	private void initUI() throws Exception
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
		LabelField topTitle = null;
		if (AppSettings.getInstance().selectedTestament.compareTo(AppSettings.OLD_TESTAMENT) == 0)
			topTitle = new LabelField("Old Testament", LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH);
		else if (AppSettings.getInstance().selectedTestament.compareTo(AppSettings.NEW_TESTAMENT) == 0)
			topTitle = new LabelField("New Testament", LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH);
		topTitle.setMargin(0, 0, 0, 10);
		topManager.add(topTitle);
	}
	
	private void initMidManager() throws Exception
	{
		midManager = new VerticalFieldManager(VERTICAL_SCROLLBAR);
		
		// ======================================================
		// init list of book
		
		{
			books = new SimpleList(midManager);
			
			// load testament books
			{
				String testamentDir = AppSettings.getInstance().APP_DATA_ON_SD_CARD;
				testamentDir += "books/" + AppSettings.getInstance().appLanguage + "/" + AppSettings.getInstance().selectedTestament + "/";
				
				FileConnection fConnection = null;
				
				fConnection = (FileConnection) Connector.open(testamentDir);
				
				if (fConnection.exists())
				{
					Vector vBook = new Vector();
					
					Enumeration listBook = fConnection.list();
					
					while(listBook.hasMoreElements())
					{
						String bookName = (String) listBook.nextElement();
						bookName = bookName.substring(0, bookName.length() - 1); // remove the '/' at the end
						Book book = new Book(bookName);
						vBook.addElement(book);
					}
					
					Collections.insertionSort(vBook, new Comparator() {
						public int compare(Object o1, Object o2) {
							if(((Book)o1).getIndex() < ((Book)o2).getIndex())
								return -1;
							else if(((Book)o1).getIndex() == ((Book)o2).getIndex())
								return 0;
							else
								return 1;
						}
					});
					
					for(int i = 0; i < vBook.size(); i++)
					{
						if(vBook.elementAt(i) != null)
							books.add( ((Book)(vBook.elementAt(i))).getName() );
					}
				}
				
				fConnection.close();

				books.setCommand(new ListBookCommand(), null, books);
			}
		}
	}
	
	private void initBotManager()
	{
		botManager = new HorizontalFieldManager();
	}
	
	
	// ==============================================================================
	// command handlers
	
	private class ListBookCommand extends CommandHandler
	{

		public void execute(ReadOnlyCommandMetadata metadata, Object context) 
		{
			
		}
		
	}
}
