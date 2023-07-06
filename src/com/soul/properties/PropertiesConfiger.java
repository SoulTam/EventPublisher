package com.soul.properties;

import java.util.Properties;

public class PropertiesConfiger {

	private static final String systemPropertiesSymbol = "System";

	/**
	 * To extract and return the properties by Id, and install the properties to
	 * System.properties if any item defined start with [ID].System.
	 * 
	 * @param properteis
	 * @param id
	 * @return
	 */
	public static Properties installProperties(Properties properteis, String id) {
		// Extract properties by id
		Properties prop = PropertiesReader.extractPropertiesById(properteis, id);

		// Extract System properties
		Properties sysProp = PropertiesReader.extractPropertiesById(prop, systemPropertiesSymbol);
		if (!sysProp.isEmpty()) {
			for (String key : sysProp.stringPropertyNames()) {
				System.getProperties().put(key, sysProp.get(key));
			}
		}

		return prop;
	}

}
