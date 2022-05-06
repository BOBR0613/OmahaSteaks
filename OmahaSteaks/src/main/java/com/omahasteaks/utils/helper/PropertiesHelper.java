package com.omahasteaks.utils.helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Properties;

public class PropertiesHelper {
	/*
	 * Properties mode
	 */
	private static Properties _conf, _profile, _default, _props;

	private static Properties _propsForName(String propFileName) {
		InputStream inputStream = null;
		try {
			System.out.println("Loading properties : " + propFileName);
			inputStream = PropertiesHelper.class.getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				Properties prop = new Properties();
				prop.load(inputStream);
				return prop;
			} else {
				// throw new FileNotFoundException("property file '" +
				// propFileName + "' not found in the classpath" );
				System.err.println(propFileName + " not found !");
				return null;
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	private static void _initProps() {
		if (_conf == null) {
			_conf = _propsForName("conf.properties");
			_default = _propsForName("profiles/default.properties");
			// Profile is passed by arguments via Jenkins
			String profile = System.getProperty("profile");
			if (profile != null)
				_profile = _propsForName("profiles/" + profile + ".properties");
			String props = System.getProperty("properties");
			if (props != null) {
				_props = new Properties();
				try {
					_props.load(new StringReader(props));
				} catch (IOException e) {
				}
			}
		}
	}

	public static String getPropValue(String key) {
		return getPropValue(key, null);
	}

	public static String getPropValue(String key, String defaultValue) {
		_initProps();
		if (System.getProperty(key) != null)
			return System.getProperty(key);
		if (_props != null && _props.containsKey(key))
			return _props.getProperty(key);
		if (_profile != null && _profile.containsKey(key))
			return _profile.getProperty(key);
		if (_default != null && _default.containsKey(key))
			return _default.getProperty(key);
		if (_conf.containsKey(key))
			return _conf.getProperty(key);
		return defaultValue;
	}
}
