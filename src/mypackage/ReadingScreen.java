package mypackage;

import java.io.IOException;
import java.util.Vector;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class ReadingScreen extends MainScreen {

	private LabelField 	topTitle;
	
	private Book 		selectedBook;
	
	// ==========================================================================
	// public methods
	
	public ReadingScreen() throws Exception {
		super(MainScreen.VERTICAL_SCROLL | MainScreen.VERTICAL_SCROLLBAR);
		
		this.selectedBook 				= AppSettings.getInstance().getSelectedBook();
		
		initUI();
	}

	// ==========================================================================
	// private methods
	
	private void initUI() throws IOException
	{
		// =========================================================================================
		// top bar title
		HorizontalFieldManager topBar = new HorizontalFieldManager();
		
		// bible icon
		Bitmap topIconBMP = Bitmap.getBitmapResource("bible_small.png");
		BitmapField topIcon = new BitmapField();
		topIcon.setBitmap(topIconBMP);
		topBar.add(topIcon);
		
		// bible title
		if (AppSettings.getInstance().appLanguage.compareTo(AppSettings.APP_LANGUAGE_ENGLISH) == 0)
			topTitle = new LabelField(this.selectedBook.getName() + " - Chapter " + AppSettings.getInstance().selectedChapter, LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH);
		else
			topTitle = new LabelField(this.selectedBook.getName() + " - " + AppSettings.getInstance().selectedChapter, LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH);
		topTitle.setMargin(0, 0, 0, 10);
		topBar.add(topTitle);
		
		// =========================================================================================
		// middle
		VerticalFieldManager mid = new VerticalFieldManager();
		
		int chapter = Integer.valueOf(AppSettings.getInstance().selectedChapter).intValue();
		Vector content = this.selectedBook.readChapter(chapter);
		for (int i = 0; i < content.size(); i++)
		{
			LabelField verse = new LabelField("", LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH);
			verse.setText(content.elementAt(i));
			
			mid.add(verse);
		}
		
		// =========================================================================================
		// add ui to screen
		this.add(topBar);
		this.add(new SeparatorField(SeparatorField.LINE_HORIZONTAL));
		this.add(mid);
	}
}
