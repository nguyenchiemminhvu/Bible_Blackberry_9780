package mypackage;

import net.rim.device.api.ui.component.Dialog;

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
	
	public static final String SD_CARD					= "file:///SDCard/";
	
	public static final String APP_LANGUAGE_ENGLISH 	= "English";
	public static final String APP_LANGUAGE_VIETNAMESE 	= "Vietnamese";
	
	// ============================================================
	// setting attributes
	
	public String appLanguage;
	
	
	// ============================================================
	// public methods
	
	public String getAppLanguage()
	{
		return this.appLanguage;
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
			throw new Exception("Unsupported language!");
		}
		
		Dialog.alert("Restart the application to apply " + this.appLanguage + " language!");
	}
	
	// ============================================================
	// private methods
	
	private AppSettings()
	{
		appLanguage = "English"; // default language
	}
}
