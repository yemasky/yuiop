package com.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Properties;

public class ConfigUtil {
	private static HashMap<String, InputStream> HM_SStream = new HashMap<String, InputStream>();

	private InputStream IS_PropertiesFile(String key) {
		InputStream IStream = HM_SStream.get(key);
		if (IStream == null) {
			IStream = this.getClass().getClassLoader().getResourceAsStream("resources/" + key);
			HM_SStream.put(key, IStream);
		}
		return IStream;
	}

	public String getProperties(String file, String key, String type) throws IOException {
		InputStream IStream = IS_PropertiesFile(file);
		if (key == null) {
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			int len = -1;
			byte[] buffer = new byte[1024];
			while ((len = IStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			IStream.close();
			String remess = new String(outSteam.toByteArray());
			return remess;
		}
		Properties properties = new Properties();
		if (type == "xml") {
			properties.loadFromXML(IStream);
		} else {
			properties.load(IStream);
		}
		return properties.getProperty(key);
	}

	public boolean saveProperties(String file, String commentsData) throws IOException {
		Properties p = new Properties();
		PrintStream PStream = new PrintStream(new File("resources/" + file));
		p.storeToXML(PStream, commentsData);
		p.store(PStream, commentsData);
		return true;
	}

	public boolean checkPropertiesExists(String file) {
		File PropFile = new File("resources/" + file);
		return PropFile.exists();
	}

	public static void main(String[] args) throws IOException {
		//WebConfig wc = new WebConfig();
		//System.out.println(wc.getProperties("supplier/tourico.xml", null, null));
		// "dat:username", "xml"));
	}
}
