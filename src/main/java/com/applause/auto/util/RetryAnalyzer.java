package com.applause.auto.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.concurrent.atomic.AtomicInteger;

public class RetryAnalyzer implements IRetryAnalyzer {

  private static final Logger logger = LogManager.getLogger(RetryAnalyzer.class);

  private AtomicInteger retryCounter = new AtomicInteger(1);
  private int retryCountLimit = 3;
  /*
   * (non-Javadoc)
   * @see org.testng.IRetryAnalyzer#retry(org.testng.ITestResult)
   *
   * This method decides how many times a test needs to be rerun.
   * TestNg will call this method every time a test fails. So we
   * can put some code in here to decide when to rerun the test.
   *
   * Note: This method will return true if a tests needs to be retried
   * and false it not.
   *
   */

  @Override
  public boolean retry(ITestResult result) {
    logger.info("TEST METHOD TESTING STATUS IS: " + result.getStatus());
    printTestNGTestCaseStatuses();
    if (retryCounter.get() < retryCountLimit) {
      //    if (retryCounter.get() <= retryCountLimit && isRetryTestExceptionThrown(result)) {
      retryCounter.incrementAndGet();
      return true;
    }

    return false;
  }

  private boolean isRetryTestExceptionThrown(ITestResult result) {
    if (result.getThrowable() instanceof RetryTestException) {
      logger.info("RetryTestException was detected");
      return true;
    }
    logger.info("RetryTestException was not detected");
    return false;
  }

  private void printTestNGTestCaseStatuses() {
    logger.info("STATUSES:");
    logger.info("CREATED = -1");
    logger.info("SUCCESS = 1");
    logger.info("FAILURE = 2");
    logger.info("SKIP = 3");
    logger.info("SUCCESS_PERCENTAGE_FAILURE = 4");
    logger.info("STARTED = 16");
  }
}
