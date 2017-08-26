package mypackage;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import net.rim.device.api.io.ScanLine;


public class Book
{

	// ===================================================
	// private properties
	
	private boolean			reachedEndOfFile;
	
	private String 			name;
	private String			typeTestament; // old_testament or new_testament
	private int				bookIndex;
	private int 			numberOfChapter;
	
	private String			relativeDir; // in res folder
	
	
	// ===================================================
	// public methods
	
	public Book()
	{
		reachedEndOfFile 	= false;
		name 				= "";
		typeTestament 		= "";
		bookIndex 			= 0;
		numberOfChapter 	= 0;
		relativeDir 		= "";
	}
	
	public Book(String name) throws Exception
	{
		this.setBook(name);
	}
	
	
	public String toString()
	{
		return this.name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getType()
	{
		return this.typeTestament;
	}
	
	public int getIndex()
	{
		return this.bookIndex;
	}
	
	public String getRelativeDir()
	{
		return this.relativeDir;
	}
	
	public int getNumberOfChapter()
	{
		return this.numberOfChapter;
	}
	
	public void setTypeAndName(String n) throws Exception
	{
		int firstDashCharacterIndex = name.indexOf('_');
		if (firstDashCharacterIndex != -1)
		{
			this.typeTestament 	= AppSettings.getInstance().selectedTestament;
			this.bookIndex 		= Integer.valueOf(name.substring(0, firstDashCharacterIndex)).intValue();
			this.name 			= name.substring(firstDashCharacterIndex + 1);
			
			this.relativeDir 	= "books/" + AppSettings.getInstance().appLanguage + "/" + AppSettings.getInstance().selectedTestament + "/" + String.valueOf(this.bookIndex) + "_" + this.name;
			
			// check folder exist or not
			String absoluteDir = AppSettings.APP_DATA_ON_SD_CARD + this.relativeDir;
			FileConnection fConnection = (FileConnection) Connector.open(absoluteDir);
			
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
	}
	
	public void setBook(String name) throws Exception
	{
		reachedEndOfFile = false;
		
		int firstDashCharacterIndex = name.indexOf('_');
		if (firstDashCharacterIndex != -1)
		{
			this.typeTestament 	= AppSettings.getInstance().selectedTestament;
			this.bookIndex 		= Integer.valueOf(name.substring(0, firstDashCharacterIndex)).intValue();
			this.name 			= name.substring(firstDashCharacterIndex + 1);
			
			this.relativeDir 	= "books/" + AppSettings.getInstance().appLanguage + "/" + AppSettings.getInstance().selectedTestament + "/" + String.valueOf(this.bookIndex) + "_" + this.name;
			
			String absoluteDir = AppSettings.APP_DATA_ON_SD_CARD + this.relativeDir;
			FileConnection fConnection = (FileConnection) Connector.open(absoluteDir);
			
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
	}
	
	public Vector readChapter(int chapter) throws IOException
	{
		if(chapter <= 0 || chapter > numberOfChapter)
			return null;
		
		reachedEndOfFile = false;
		Vector verses = new Vector();
		
		String relativeDir = "books/" + AppSettings.getInstance().appLanguage + "/" + AppSettings.getInstance().selectedTestament + "/" + String.valueOf(this.bookIndex) + "_" + name;
		String fullPath = relativeDir + "/" + String.valueOf(chapter) + "/content.bible";
		String absolutePath = AppSettings.APP_DATA_ON_SD_CARD + fullPath;
		
		FileConnection fConnection = (FileConnection) Connector.open(absolutePath);
		InputStream iStream = fConnection.openInputStream();
		// reading
		{
			// just for debug
//			verses.addElement("(1) Verses 1.");
//			verses.addElement("(2) Verses 2.");
//			verses.addElement("(3) Verses 3.");
			
			while (true)
			{
				String verse = readLine(iStream);
				verses.addElement(verse);
				
				if (reachedEndOfFile)
					break;
			}
		}
		
		iStream.close();
		fConnection.close();
		return verses;
	}
	
	// ===================================================
	// private methods
	
	private String readLine(InputStream in) throws IOException
	{
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		
		while (true)
		{
			int b = in.read();
			
			if (b < 0)
			{
				reachedEndOfFile = true;
				break;
			}
			
			if (b == 0x0A)
			{
				break; // '\n' character
			}
			
			buffer.write(b);
		}
		
		return new String(buffer.toByteArray());
	}
};