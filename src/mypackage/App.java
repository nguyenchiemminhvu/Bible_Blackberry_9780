package mypackage;

import java.io.IOException;

import net.rim.device.api.ui.UiApplication;

/**
 * This class extends the UiApplication class, providing a
 * graphical user interface.
 */
public class App extends UiApplication
{
    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     * @throws Exception 
     */ 
    public static void main(String[] args) throws Exception
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        App.getInstance().enterEventDispatcher();
    }
    

    private static App appInstance = null;
    
    public static App getInstance() throws Exception
    {
    	if(appInstance == null)
    	{
    		appInstance = new App();
    	}
    	return appInstance;
    }
    
    /**
     * Creates a new MyApp object
     * @throws Exception 
     */
    public App() throws Exception
    {        
    	AppSettings.getInstance();
    	
        // Push a screen onto the UI stack for rendering.
        pushScreen(new TestamentSelectionScreen());
    }    
}
