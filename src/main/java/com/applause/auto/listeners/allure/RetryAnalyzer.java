package com.applause.auto.listeners.allure;

import static com.applause.auto.configuration.ExecutionConfigurationOwner.executionConfiguration;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

@Slf4j
@Component
public class RetryAnalyzer implements IRetryAnalyzer {

  private static final int RETRY_LIMIT = 2;
  private final AtomicInteger counter = new AtomicInteger(1);

  @Override
  public boolean retry(ITestResult result) {
    log.info("TEST CASE STATUS IS: " + result.getStatus());
    if (counter.get() < RETRY_LIMIT && executionConfiguration.retryOnFailure()) {
      counter.incrementAndGet();
      return true;
    }
    return false;
  }
}
