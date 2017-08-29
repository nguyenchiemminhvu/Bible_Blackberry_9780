package mypackage;

import java.io.IOException;
import java.util.Vector;

import net.rim.device.api.crypto.Key;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EventInjector.KeyCodeEvent;
import net.rim.device.api.system.KeyListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.FontManager;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.Trackball;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class ReadingScreen extends MainScreen {

	private float					horizontalNavigation;
	private LabelField 				topTitle;
	private Book 					selectedBook;
	private VerticalFieldManager 	mid;
	
	// ==========================================================================
	// public methods
	
	public ReadingScreen() throws Exception {
		super(MainScreen.VERTICAL_SCROLL | MainScreen.VERTICAL_SCROLLBAR);
		
		this.horizontalNavigation		= (float) 0.0;
		this.selectedBook 				= AppSettings.getInstance().getSelectedBook();
		
		initUI();
		
		this.addKeyListener(new KeyListener() {
			
			public boolean keyUp(int keycode, int time) {
				// Y 5832704
				// T 5505024
				
				switch (keycode) {
				case 5832704: // key Y
					
					try {
						readNextChapter();
					} catch (IOException e) {
					} catch (ClassNotFoundException e) {
					}
					
					break;

				case 5505024: // key T
					
					try {
						readPrevChapter();
					} catch (IOException e) {
					} catch (ClassNotFoundException e) {
					}
					
					break;
					
				default:
					horizontalNavigation = 0;
					break;
				}
				
				return false;
			}
			
			public boolean keyStatus(int keycode, int time) {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean keyRepeat(int keycode, int time) {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean keyDown(int keycode, int time) {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean keyChar(char key, int status, int time) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}
	
	// ==========================================================================
	// protected methods

	protected boolean navigationMovement(int dx, int dy, int status, int time)
	{
		horizontalNavigation += dx;
		if (horizontalNavigation > 4.0)
		{
			try {
				
				readNextChapter();
				
			} catch (IOException e) {

			} catch (ClassNotFoundException e) {

			}
			
			return true;
		}
		
		if (horizontalNavigation < -4.0)
		{
			try {
				
				readPrevChapter();
				
			} catch (IOException e) {

			} catch (ClassNotFoundException e) {

			}
			
			return true;
		}
		
		return false;
	}
	
	// ==========================================================================
	// private methods
	
	private void initUI() throws IOException, ClassNotFoundException
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
		mid = new VerticalFieldManager(VERTICAL_SCROLL);
		
		int chapter = Integer.valueOf(AppSettings.getInstance().selectedChapter).intValue();
		Vector content = this.selectedBook.readChapter(chapter);
		
		for (int i = 0; i < content.size(); i++)
		{
			HorizontalFieldManager hor = new HorizontalFieldManager();
			VerticalFieldManager ver = new VerticalFieldManager();
			
			TextField verse = new TextField(TextField.FOCUSABLE | TextField.READONLY);
			verse.setText((String) content.elementAt(i));
			verse.setEditable(false);
			
			FontFamily fFamily = FontFamily.forName("Times New Roman");
			if (!isTopicSentence((String) content.elementAt(i)))
			{
				Font font = fFamily.getFont(Font.BOLD, 35);
				verse.setFont(font);
			}
			else
			{
				Font font = fFamily.getFont(Font.PLAIN, 30);
				verse.setFont(font);
			}
			
			hor.add(verse);
			ver.add(hor);
			
			mid.add(ver);
		}
		
		
		// =========================================================================================
		// add ui to screen
		this.add(topBar);
		this.add(new SeparatorField(SeparatorField.LINE_HORIZONTAL));
		this.add(mid);
	}
	
	private void readNextChapter() throws IOException, ClassNotFoundException
	{
		horizontalNavigation = 0;
		
		int currentChapter = Integer.valueOf(AppSettings.getInstance().selectedChapter).intValue();
		if (currentChapter + 1 <= this.selectedBook.getNumberOfChapter())
		{
			AppSettings.getInstance().selectedChapter = String.valueOf(currentChapter + 1);
			
			mid.deleteAll();
			Vector content = this.selectedBook.readChapter(currentChapter + 1);
			for (int i = 0; i < content.size(); i++)
			{
				HorizontalFieldManager hor = new HorizontalFieldManager();
				VerticalFieldManager ver = new VerticalFieldManager();
				
				TextField verse = new TextField(TextField.FOCUSABLE | TextField.READONLY);
				verse.setText((String) content.elementAt(i));
				verse.setEditable(false);
				
				FontFamily fFamily = FontFamily.forName("Times New Roman");
				if (!isTopicSentence((String) content.elementAt(i)))
				{
					Font font = fFamily.getFont(Font.BOLD, 35);
					verse.setFont(font);
				}
				else
				{
					Font font = fFamily.getFont(Font.PLAIN, 30);
					verse.setFont(font);
				}
				
				hor.add(verse);
				ver.add(hor);
				
				mid.add(ver);
			}
			
			// bible title
			if (AppSettings.getInstance().appLanguage.compareTo(AppSettings.APP_LANGUAGE_ENGLISH) == 0)
				topTitle.setText(this.selectedBook.getName() + " - Chapter " + AppSettings.getInstance().selectedChapter);
			else
				topTitle.setText(this.selectedBook.getName() + " - " + AppSettings.getInstance().selectedChapter);
		}
	}
	
	private void readPrevChapter() throws IOException, ClassNotFoundException
	{
		horizontalNavigation = 0;
		
		int currentChapter = Integer.valueOf(AppSettings.getInstance().selectedChapter).intValue();
		if (currentChapter - 1 >= 1)
		{
			AppSettings.getInstance().selectedChapter = String.valueOf(currentChapter - 1);
			
			mid.deleteAll();
			Vector content = this.selectedBook.readChapter(currentChapter - 1);
			for (int i = 0; i < content.size(); i++)
			{
				HorizontalFieldManager hor = new HorizontalFieldManager();
				VerticalFieldManager ver = new VerticalFieldManager();
				
				TextField verse = new TextField(TextField.FOCUSABLE | TextField.READONLY);
				verse.setText((String) content.elementAt(i));
				verse.setEditable(false);
				
				FontFamily fFamily = FontFamily.forName("Times New Roman");
				if (!isTopicSentence((String) content.elementAt(i)))
				{
					Font font = fFamily.getFont(Font.BOLD, 35);
					verse.setFont(font);
				}
				else
				{
					Font font = fFamily.getFont(Font.PLAIN, 30);
					verse.setFont(font);
				}
				
				hor.add(verse);
				ver.add(hor);
				
				mid.add(ver);
			}
			
			// bible title
			if (AppSettings.getInstance().appLanguage.compareTo(AppSettings.APP_LANGUAGE_ENGLISH) == 0)
				topTitle.setText(this.selectedBook.getName() + " - Chapter " + AppSettings.getInstance().selectedChapter);
			else
				topTitle.setText(this.selectedBook.getName() + " - " + AppSettings.getInstance().selectedChapter);
		}
	}
	
	private boolean isTopicSentence(String s)
	{
		if (s.length() == 0)
			return false;
		
		if (Character.isDigit(s.charAt(0)))
			return true;
		
		return false;
	}
}
