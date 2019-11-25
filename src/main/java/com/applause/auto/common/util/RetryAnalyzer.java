package com.applause.auto.common.util;

import org.testng.ITestResult;

public class RetryAnalyzer { // implements IRetryAnalyzer {

  int counter = 0;
  int retryLimit = 2;

  // @Override
  public void retry(ITestResult iTestResult) {
    /* if (counter < retryLimit) {
      counter++;
      return true;
    }
    return false; */
  }
}
