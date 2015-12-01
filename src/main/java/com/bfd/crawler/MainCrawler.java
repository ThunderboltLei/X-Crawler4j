/*
 * Copyright (C) 2015 The Piramid by ThunderboltLei
 */

package com.bfd.crawler;

import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.bfd.utils.ConfigUtils;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author: lm8212
 * @description:
 */
public class MainCrawler extends WebCrawler {

	private static final Logger logger = Logger.getLogger(MainCrawler.class);

	private final static Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|gif|jpg" + "|png|mp3|mp3|zip|gz))$");

	/**
	 * This method receives two parameters. The first parameter is the page in
	 * which we have discovered this new url and the second parameter is the new
	 * url. You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic). In this example,
	 * we are instructing the crawler to ignore urls that have css, js, git, ...
	 * extensions and to only accept urls that start with
	 * "http://www.ics.uci.edu/". In this case, we didn't need the referringPage
	 * parameter to make the decision.
	 */
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		String prefix = ConfigUtils.getPropsVal("crawler.prefix");
//		logger.info("prefix: " + prefix);
		return !FILTERS.matcher(href).matches()
		// && href.startsWith("http://www.ics.uci.edu/");
				&& href.startsWith(prefix);
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		logger.info("URL: " + url);

		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();
			Set<WebURL> links = htmlParseData.getOutgoingUrls();

			// logger.info("Text: " + text);
			logger.info("Text length: " + text.length());
			logger.info("Html length: " + html.length());
			logger.info("Number of outgoing links: " + links.size());
		}
	}

}
