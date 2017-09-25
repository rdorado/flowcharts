package util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyFileManager {
	static Properties properties=null;
	static final String propertiesFileName = "config.properties";

	static void loadPropertiesFile(){
		try {
			File f = new File(propertiesFileName);
			if(!f.exists()) f.createNewFile();
			InputStream input = new FileInputStream(f);
			properties = new Properties();
			properties.load(input);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	static void savePropertiesFile(){
		if(properties!=null){
			try {
				OutputStream output = new FileOutputStream(propertiesFileName);
				properties.store(output, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	public static String getProperty(String propertyName){
		if(properties==null) loadPropertiesFile();
		return properties.getProperty(propertyName);
	}

	public static void setProperty(String propertyName, String value){
		if(properties==null) loadPropertiesFile();
		properties.setProperty(propertyName,value);
		savePropertiesFile();
	}



}
