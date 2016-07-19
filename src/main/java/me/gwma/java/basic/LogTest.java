package me.gwma.java.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {
	private final static Logger logger = LoggerFactory.getLogger(LogTest.class);

	public static void main(String[] args) {
		logger.debug("debug ....");
		logger.info("info ...");
		logger.error("error ...");

		System.out.println("System.out.println : hello");
	}
}
