package mypackage;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;

public class ChapterSelectionScreen extends MainScreen {

	private Book 	selectedBook;
	private String 	selectedBookFullPath;
	
	
	// ==========================================================================
	// public methods
	
	public ChapterSelectionScreen() throws Exception {
		super();
		
		this.selectedBook 			= AppSettings.getInstance().getSelectedBook();
		this.selectedBookFullPath 	= AppSettings.APP_DATA_ON_SD_CARD + this.selectedBook.getRelativeDir();
		initUI();	
	}

	
	// ==========================================================================
	// private methods
	
	private void initUI()
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
		LabelField topTitle = null;
		if (AppSettings.getInstance().appLanguage.compareTo(AppSettings.APP_LANGUAGE_ENGLISH) == 0)
			topTitle = new LabelField(this.selectedBookFullPath, LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH);
		else if (AppSettings.getInstance().appLanguage.compareTo(AppSettings.APP_LANGUAGE_VIETNAMESE) == 0)
			topTitle = new LabelField("Chương", LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH);
		topTitle.setMargin(0, 0, 0, 10);
		topBar.add(topTitle);
		
		// =========================================================================================
		// add ui to screen
		this.add(topBar);
		this.add(new SeparatorField(SeparatorField.LINE_HORIZONTAL));
	}
}
