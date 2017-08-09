package mypackage;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

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
			topTitle = new LabelField("Bible", LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH);
		else if (AppSettings.getInstance().appLanguage.compareTo(AppSettings.APP_LANGUAGE_VIETNAMESE) == 0)
			topTitle = new LabelField("Kinh Thánh", LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH);
		topTitle.setMargin(0, 0, 0, 10);
		topBar.add(topTitle);
		
		// =========================================================================================
		// old testament
		buttonOldTestament = new ButtonField();
		if (AppSettings.getInstance().appLanguage.compareTo(AppSettings.APP_LANGUAGE_ENGLISH) == 0)
			buttonOldTestament.setLabel("Old Testament ");
		else if (AppSettings.getInstance().appLanguage.compareTo(AppSettings.APP_LANGUAGE_VIETNAMESE) == 0)
			buttonOldTestament.setLabel("Cựu ước");
		
		buttonOldTestament.setChangeListener(new FieldChangeListener() {
			
			public void fieldChanged(Field field, int context) {
				AppSettings.getInstance().selectedTestament = AppSettings.OLD_TESTAMENT;
				
				try {
					App.getInstance().pushScreen(new BookSelectionScreen());
				} catch (Exception e) {
					System.exit(-1);
				}
			}
		});
		
		// =========================================================================================
		// new testament
		buttonNewTestament = new ButtonField();
		if (AppSettings.getInstance().appLanguage.compareTo(AppSettings.APP_LANGUAGE_ENGLISH) == 0)
			buttonNewTestament.setLabel("New Testament");
		else if (AppSettings.getInstance().appLanguage.compareTo(AppSettings.APP_LANGUAGE_VIETNAMESE) == 0)
			buttonNewTestament.setLabel("Tân ước");
		
		buttonNewTestament.setChangeListener(new FieldChangeListener() {
			
			public void fieldChanged(Field field, int context) {
				AppSettings.getInstance().selectedTestament = AppSettings.NEW_TESTAMENT;
				
				try {
					App.getInstance().pushScreen(new BookSelectionScreen());
				} catch (Exception e) {
					System.exit(-1);
				}
			}
		});
		
		// =========================================================================================
		// middle
		VerticalFieldManager middle = new VerticalFieldManager();
		middle.add(buttonOldTestament);
		middle.add(buttonNewTestament);
		
		// =========================================================================================
		// add ui to screen
		this.add(topBar);
		this.add(new SeparatorField(SeparatorField.LINE_HORIZONTAL));
		this.add(middle);
	}
}
