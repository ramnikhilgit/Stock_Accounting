package utilites;

import java.io.FileInputStream;

import java.util.Properties;

public class PropertyFileUtil {
	public static String getValueforKey(String Key) throws Throwable, Throwable 
	{
		Properties conpro = new Properties();
		//load file
		
		conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
		return conpro.getProperty(Key);
		
		
	}
	
	

}
