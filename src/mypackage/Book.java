package mypackage;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;


public class Book
{

	// ===================================================
	// private properties
	
	private String			language;
	private String 			name;
	private String			type; // old_testament or new_testament
	private int				bookIndex;
	private int 			numberOfChapter;
	
	private String			relativeDir; // in res folder
	
	
	// ===================================================
	// public methods
	
	public Book()
	{
		language = "English"; // default
		name = "";
		numberOfChapter = 0;
	}
	
	public String getLanguage()
	{
		return this.language;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public String getRelativeDir()
	{
		return this.relativeDir;
	}
	
	public void setLanguage(String lang)
	{
		this.language = lang;
	}
	
	public void setTypeAndName(String t, String n) throws Exception
	{
		int firstDashCharacterIndex = name.indexOf('_');
		if (firstDashCharacterIndex != -1)
		{
			if (t.compareTo("old_testament") != 0 || t.compareTo("new_testament") != 0)
			{
				throw new Exception("Invalid type of book! (old_testament or new_testament");
			}

			this.type 			= t;
			this.bookIndex 		= Integer.valueOf(name.substring(0, firstDashCharacterIndex)).intValue();
			this.name 			= name.substring(firstDashCharacterIndex + 1);
			
			this.relativeDir 	= "books/" + this.language + "/" + this.type + "/" + name;
			
			// check folder exist or not
			FileConnection fConnection = (FileConnection) Connector.open(this.relativeDir);
			if(!fConnection.exists())
			{
				fConnection.close();
				throw new Exception("This book is not existed!");
			}
			fConnection.close();
		}
		else
		{
			throw new Exception("Invalid book's name! Book's name should have a dash character.");
		}
	}
	
	public void setBook(String language, String type, String name) throws Exception
	{
		int firstDashCharacterIndex = name.indexOf('_');
		if (firstDashCharacterIndex != -1)
		{
			if (language.compareTo("English") != 0 || language.compareTo("Vietnamese") != 0)
			{
				throw new Exception("Invalid language! (English or Vietnamese)");
			}
			
			if (type.compareTo("old_testament") != 0 || type.compareTo("new_testament") != 0)
			{
				throw new Exception("Invalid type of book! (old_testament or new_testament");
			}
			
			this.language		= language;
			this.type 			= type;
			this.bookIndex 		= Integer.valueOf(name.substring(0, firstDashCharacterIndex)).intValue();
			this.name 			= name.substring(firstDashCharacterIndex + 1);
			
			this.relativeDir 	= "books/" + this.language + "/" + this.type + "/" + this.name;
			
			// check folder exist or not
			FileConnection fConnection = (FileConnection) Connector.open(this.relativeDir);
			if(!fConnection.exists())
			{
				fConnection.close();
				throw new Exception("This book is not existed!");
			}
			fConnection.close();
		}
		else
		{
			throw new Exception("Invalid book's name! Book's name should have a dash character.");
		}
	}
	
	public int getNumberOfChapter()
	{
		return 0;
	}
	
	// ===================================================
	// private methods
	
	
};