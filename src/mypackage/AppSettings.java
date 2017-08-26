package mypackage;


public class AppSettings 
{
	// ============================================================
	// singleton design pattern
	private static AppSettings instance = new AppSettings();
	
	public static AppSettings getInstance()
	{
		if(instance == null)
		{
			instance = new AppSettings();
		}
		return instance;
	}
	// ============================================================
	
	public static final String SD_CARD						= "file:///SDCard/BlackBerry/";
	public static final String APP_DATA_ON_SD_CARD			= "file:///SDCard/BlackBerry/appdata/";
	
	public static final String OLD_TESTAMENT				= "old_testament";
	public static final String NEW_TESTAMENT				= "new_testament";
	
	public static final int NUMBER_OF_OLD_TESTAMENT_BOOK	= 39;
	public static final int NUMBER_OF_NEW_TESTAMENT_BOOK	= 27;
	
	public static final String APP_LANGUAGE_ENGLISH 		= "English";
	public static final String APP_LANGUAGE_VIETNAMESE 		= "Vietnamese";
	
	public static final String END_OF_FILE					= "__EOF__";
	
	// ============================================================
	// setting attributes
	
	public String appLanguage;
	public String selectedTestament;
	public String selectedBookName;
	public String selectedChapter;
	
	private Book selectedBook;
	
	// ============================================================
	// public methods
	
	public Book getSelectedBook() throws Exception 
	{
		if (this.selectedBook == null)
		{
			this.selectedBook = new Book();
		}
		
		this.selectedBook.setBook(this.selectedBookName);
		
		return this.selectedBook;
	}
	
	public void selectTestament(String testament) throws Exception
	{
		if (testament.compareTo(OLD_TESTAMENT) == 0)
		{
			this.selectedTestament = OLD_TESTAMENT;
		}
		
		else if (testament.compareTo(NEW_TESTAMENT) == 0)
		{
			this.selectedTestament = NEW_TESTAMENT;
		}
		
		else
		{
			throw new Exception("Unknown testament");
		}
	}
	
	public void setAppLanguage(String lang) throws Exception
	{
		if (lang.compareTo(APP_LANGUAGE_ENGLISH) == 0)
		{
			this.appLanguage = APP_LANGUAGE_ENGLISH;
		}
		
		else if (lang.compareTo(APP_LANGUAGE_VIETNAMESE) == 0)
		{
			this.appLanguage = APP_LANGUAGE_VIETNAMESE;
		}
		
		else
		{
			this.appLanguage = APP_LANGUAGE_ENGLISH; // default
		}
	}
	
	// ============================================================
	// private methods
	
	private AppSettings()
	{
		appLanguage 		= APP_LANGUAGE_ENGLISH; // default language
		selectedTestament 	= OLD_TESTAMENT;
		selectedBookName	= "1_Genesis";
		selectedChapter		= "1";
	}

}
