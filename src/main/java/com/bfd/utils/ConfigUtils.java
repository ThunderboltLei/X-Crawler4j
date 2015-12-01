/*
 * Copyright (C) 2015 The Piramid by ThunderboltLei
 */

package com.bfd.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author: lm8212
 * @description:
 */
public class ConfigUtils {

	private static final Logger logger = Logger.getLogger(ConfigUtils.class);

	private static Properties configProps;

	static {
		configProps = new Properties();
		try {
			configProps.load(ConfigUtils.class.getClassLoader()
					.getResourceAsStream("config.properties"));
		} catch (IOException e) {
			try {
				throw new Exception("Load configurations error...");
			} catch (Exception e1) {
				logger.error(e.fillInStackTrace());
			}
		}
	}

	/**
	 * @return the configProps
	 */
	public static String getPropsVal(String key) {
		return configProps.getProperty(key);
	}

	public static void main(String[] args) {

		String val = ConfigUtils.getPropsVal("crawler.seeds");
		logger.info("val: " + val);

	}

}
