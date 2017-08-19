package mypackage;

import net.rim.device.api.io.LineReader;
import net.rim.device.api.lbs.maps.Boxable;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.component.TextSpinBoxField;
import net.rim.device.api.ui.component.table.DataModel;
import net.rim.device.api.ui.component.table.DataTemplate;
import net.rim.device.api.ui.component.table.DataView;
import net.rim.device.api.ui.component.table.RichList;
import net.rim.device.api.ui.component.table.RichListDataTemplate;
import net.rim.device.api.ui.component.table.TableModel;
import net.rim.device.api.ui.component.table.TableView;
import net.rim.device.api.ui.component.table.TemplateColumnProperties;
import net.rim.device.api.ui.component.table.TemplateRowProperties;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.SpinBoxFieldManager;
import net.rim.device.api.ui.container.VerticalFieldManager;

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
		final int numberOfChapter = this.selectedBook.getNumberOfChapter();
		
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
			topTitle = new LabelField("Chapters (1 - " + String.valueOf(numberOfChapter) + ")", LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH);
		else if (AppSettings.getInstance().appLanguage.compareTo(AppSettings.APP_LANGUAGE_VIETNAMESE) == 0)
			topTitle = new LabelField("Chương (1 - " + String.valueOf(numberOfChapter) + ")", LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH);
		topTitle.setMargin(0, 0, 0, 10);
		topBar.add(topTitle);
		
		// =========================================================================================
		// middle (chapter selection table)
		VerticalFieldManager mid = new VerticalFieldManager(VerticalFieldManager.NO_VERTICAL_SCROLL | VerticalFieldManager.NO_VERTICAL_SCROLLBAR);
		
		HorizontalFieldManager horManager = new HorizontalFieldManager();
		
		// left: text spin box
		{
			SpinBoxFieldManager spinManager = new SpinBoxFieldManager();
			spinManager.setVisibleRows(3);
			
			String[] listChapter = new String[numberOfChapter];
			for (int i = 0; i < numberOfChapter; i++)
			{
				listChapter[i] = new String("  " + String.valueOf(i + 1) + "  ");
			}
			
			final TextSpinBoxField chapterSpin = new TextSpinBoxField(listChapter);
			spinManager.add(chapterSpin);
			
			
			VerticalFieldManager subManager = new VerticalFieldManager();
			
			ButtonField buttonSpin = new ButtonField("Select");
			buttonSpin.setChangeListener(new FieldChangeListener() 
			{	
				public void fieldChanged(Field field, int context) 
				{
					try {
						
						String selectedChapter = (String) chapterSpin.get(chapterSpin.getSelectedIndex());
						AppSettings.getInstance().selectedChapter = selectedChapter;
						App.getInstance().pushScreen(new ReadingScreen());
						
					} catch (Exception e) {
						
					}
				}
			});
			
			subManager.add(new LabelField());
			subManager.add(new LabelField());
			subManager.add(buttonSpin);
			
			horManager.add(spinManager);
			horManager.add(subManager);
		}
		
		horManager.add(new LabelField("  "));
		horManager.add(new SeparatorField(SeparatorField.LINE_VERTICAL));
		horManager.add(new LabelField("  "));
		
		// right: basic edit field and button field
		{
			VerticalFieldManager verManager = new VerticalFieldManager();
			
			final BasicEditField chapterInput = new BasicEditField(BasicEditField.FILTER_NUMERIC);
			chapterInput.setLabel("Enter chapter: ");
			chapterInput.setMaxSize(3);
			chapterInput.setChangeListener(new FieldChangeListener() 
			{
				public void fieldChanged(Field field, int context) 
				{
					BasicEditField chapterInput = (BasicEditField) field;
					int value = Integer.valueOf(chapterInput.getText()).intValue();
					if (value > numberOfChapter)
					{
						chapterInput.setText(String.valueOf(numberOfChapter));
					}
				}
			});
			
			ButtonField buttonSelect = new ButtonField("Select");
			buttonSelect.setChangeListener(new FieldChangeListener() 
			{	
				public void fieldChanged(Field field, int context) 
				{
					try {
						
						if (chapterInput.getText().length() == 0)
							return;
						
						AppSettings.getInstance().selectedChapter = chapterInput.getText();
						App.getInstance().pushScreen(new ReadingScreen());
					
					} catch (Exception e) {
						
					}
				}
			});
			
			HorizontalFieldManager subManager = new HorizontalFieldManager();
			subManager.add(new LabelField("                "));
			subManager.add(buttonSelect);
			
			verManager.add(chapterInput);
			verManager.add(subManager);
			
			horManager.add(verManager);
		}
		
		mid.add(horManager);
		
		// =========================================================================================
		// add ui to screen
		this.add(topBar);
		this.add(new SeparatorField(SeparatorField.LINE_HORIZONTAL));
		this.add(new LabelField());
		this.add(mid);
	}
	
	
	private class ChapterDataTemplate extends DataTemplate
	{
		
		public ChapterDataTemplate(TableView view)
		{
			super(view, 1, 10); // 10 column on each row
		}

		public Field[] getDataFields(int modelRowIndex) {
			Object[] data = (Object[]) ((TableModel)getView().getModel()).getRow(modelRowIndex);
			Field[] fields = new Field[10];
			for (int i = 0; i < 10; i++)
			{
				fields[i] = new LabelField(data[i], DrawStyle.ELLIPSIS);
			}
			
			return fields;
		}
		
	}
}
