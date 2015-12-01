/*
 * Copyright (C) 2015 The Piramid by ThunderboltLei
 */

package com.bfd.main;

import org.apache.log4j.Logger;

import com.bfd.crawler.MainCrawler;
import com.bfd.utils.ConfigUtils;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * @author: lm8212
 * @description:
 */
public class MainDoor {

	private static final Logger logger = Logger.getLogger(MainDoor.class);

	public static void toCrawl() {
		try {
			String crawlStorageFolder = "/opt/eclipse-jee-luna-SR2/projects/Crawler4jDemo/src/main/resources/data";
			int numberOfCrawlers = 7;

			CrawlConfig config = new CrawlConfig();
			config.setCrawlStorageFolder(crawlStorageFolder);

			/*
			 * Instantiate the controller for this crawl.
			 */
			PageFetcher pageFetcher = new PageFetcher(config);
			RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
			RobotstxtServer robotstxtServer = new RobotstxtServer(
					robotstxtConfig, pageFetcher);
			CrawlController controller = new CrawlController(config,
					pageFetcher, robotstxtServer);

			/*
			 * For each crawl, you need to add some seed urls. These are the
			 * first URLs that are fetched and then the crawler starts following
			 * links which are found in these pages
			 */
			// controller.addSeed("http://www.ics.uci.edu/~lopes/");
			// controller.addSeed("http://www.ics.uci.edu/~welling/");
			// controller.addSeed("http://www.ics.uci.edu/");

			// controller.addSeed(ConfigUtils.getPropsVal("crawler.seeds"));

			String[] seeds = ConfigUtils.getPropsVal("crawler.seeds")
					.split(",");
			for (String s : seeds) {
				if (null != s || !"".equals(s)) {
					// logger.info(s);
					controller.addSeed(s);
				}
			}

			/*
			 * Start the crawl. This is a blocking operation, meaning that your
			 * code will reach the line after this only when crawling is
			 * finished.
			 */
			controller.start(MainCrawler.class, numberOfCrawlers);
		} catch (Exception e) {
			logger.error("e: " + e.fillInStackTrace());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MainDoor.toCrawl();

	}

}
