package mypackage;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;


public class Book
{

	// ===================================================
	// private properties
	
	private String 			name;
	private String			type; // old_testament or new_testament
	private int				bookIndex;
	private int 			numberOfChapter;
	
	private String			relativeDir; // in res folder
	
	
	// ===================================================
	// public methods
	
	public Book()
	{
		name = "";
		type = "";
		bookIndex = 0;
		numberOfChapter = 0;
		relativeDir = "";
	}
	
	public Book(String type, String name) throws Exception
	{
		this.setBook(type, name);
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
	
	public int getNumberOfChapter()
	{
		return this.numberOfChapter;
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
			
			this.relativeDir 	= "books/" + AppSettings.getInstance().appLanguage + "/" + this.type + "/" + String.valueOf(this.bookIndex) + "_" + name;
			
			// check folder exist or not
			String absoluteDir = AppSettings.APP_DATA_ON_SD_CARD + this.relativeDir;
			FileConnection fConnection = (FileConnection) Connector.open(absoluteDir);
			if(!fConnection.exists())
			{
				fConnection.close();
				throw new Exception("This book is not existed!");
			}
			
			// counting number of chapter
			Enumeration enumFolder = fConnection.list();
			int count = 0;
			while (enumFolder.hasMoreElements())
			{
				enumFolder.nextElement();
				count++;
			}
			this.numberOfChapter = count;
			
			fConnection.close();
		}
		else
		{
			throw new Exception("Invalid book's name! Book's name should have a dash character.");
		}
	}
	
	public void setBook(String type, String name) throws Exception
	{
		int firstDashCharacterIndex = name.indexOf('_');
		if (firstDashCharacterIndex != -1)
		{
			this.type 			= type;
			this.bookIndex 		= Integer.valueOf(name.substring(0, firstDashCharacterIndex)).intValue();
			this.name 			= name.substring(firstDashCharacterIndex + 1);
			
			this.relativeDir 	= "books/" + AppSettings.getInstance().appLanguage + "/" + this.type + "/" + String.valueOf(this.bookIndex) + "_" + name;
			
			// check folder exist or not
			String absoluteDir = AppSettings.APP_DATA_ON_SD_CARD + this.relativeDir;
			FileConnection fConnection = (FileConnection) Connector.open(absoluteDir);
			if(!fConnection.exists())
			{
				fConnection.close();
				throw new Exception("This book is not existed!");
			}
			
			// counting number of chapter
			Enumeration enumFolder = fConnection.list();
			int count = 0;
			while (enumFolder.hasMoreElements())
			{
				enumFolder.nextElement();
				count++;
			}
			this.numberOfChapter = count;
			
			fConnection.close();
		}
		else
		{
			throw new Exception("Invalid book's name! Book's name should have a dash character.");
		}
	}
	
	public Vector readChapter(int chapter) throws IOException
	{
		if(chapter <= 0 || chapter > numberOfChapter)
			return null;
		
		Vector verses = new Vector();
		
		String relativeDir = "books/" + AppSettings.getInstance().appLanguage + "/" + this.type + "/" + String.valueOf(this.bookIndex) + "_" + name;
		String fullPath = relativeDir + "/" + String.valueOf(chapter) + "/content.bible";
		String absolutePath = AppSettings.APP_DATA_ON_SD_CARD + fullPath;
		
		FileConnection fConnection = (FileConnection) Connector.open(absolutePath);
		DataInputStream iStream = fConnection.openDataInputStream();
		
		// reading
		{
			// just for debug
			verses.addElement("(1) Verses 1.");
			verses.addElement("(2) Verses 2.");
			verses.addElement("(3) Verses 3.");
		}
		
		iStream.close();
		fConnection.close();
		return verses;
	}
	
	// ===================================================
	// private methods
	
	
};