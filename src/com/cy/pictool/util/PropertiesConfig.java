package com.cy.pictool.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

 
public class PropertiesConfig {
 
	private static Properties properties;

	public static final String CONFIGFILE_PATH = System.getProperty("user.dir") + File.separator + "path.cfg";
	public static String CONFIGFILE;
	public static final String folderkey = "RES.FOLDER";
	public static final String indexkey = "RES.INDEX";
	
	public Properties getProperties() {
		return properties;
	}

	private static PropertiesConfig instance = new PropertiesConfig();
	
	public static PropertiesConfig getInstance() {
		return instance;
	}

	private PropertiesConfig() {
		properties = new Properties();
		
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(CONFIGFILE_PATH)); 
			Properties prop_tmp = new Properties();
			prop_tmp.load(in);
			CONFIGFILE = prop_tmp.getProperty("CONF.PATH");

			in = new BufferedInputStream(new FileInputStream(CONFIGFILE)); 
			properties.load(in);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} 
 
	public static String getConfig(String key){

		return PropertiesConfig.getInstance().getProperties().getProperty(key);
	}
}
