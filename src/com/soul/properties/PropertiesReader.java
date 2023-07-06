package com.soul.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertiesReader {

	/**
	 * Extract all of the properties under the same publisher. Publisher Id will be
	 * removed from the key of property in the new Properties.
	 * 
	 * @param publisherId
	 * @return
	 */
	public static Properties extractPropertiesById(Properties properties, String publisherId) {
		Properties prop = new Properties();
		List<String> keyList = extractPropertiesKeysById(properties, publisherId);
		if ("".equals(publisherId)) {
			for (String key : keyList) {
				prop.put(key, properties.get(key));
			}
		} else {
			for (String key : keyList) {
				prop.put(key.replaceFirst(publisherId + ".", ""), properties.get(key));
			}
		}

		return prop;
	}

	/**
	 * Extract all of the properties under the same publisher. Publisher Id will be
	 * kept in the new Properties.
	 * 
	 * @param publisherID
	 * @return
	 */
	public static Properties extractPropertiesByIdWithFullKey(Properties properties, String publisherId) {
		Properties prop = new Properties();
		List<String> keyList = extractPropertiesKeysById(properties, publisherId);
		for (String key : keyList) {
			prop.put(key, properties.get(key));
		}
		return prop;
	}

	/**
	 * Extract properties key list by publisher ID.
	 * 
	 * @param publisherId
	 * @return
	 */
	public static List<String> extractPropertiesKeysById(Properties properties, String publisherId) {
		ArrayList<String> keyList = new ArrayList<String>();
		for (String key : properties.stringPropertyNames()) {
			if ("".equals(publisherId.trim()) || key.indexOf(publisherId + ".") == 0) {
				keyList.add(key);
			}
		}

		return keyList;
	}

}
