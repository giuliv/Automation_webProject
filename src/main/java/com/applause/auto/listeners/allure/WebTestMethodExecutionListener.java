package com.applause.auto.listeners.allure;

import static com.applause.auto.util.AllureUtils.attachCurrentURLOnFailure;
import static com.applause.auto.util.AllureUtils.attachScreenshotOnFailure;

import java.util.Objects;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class WebTestMethodExecutionListener implements IInvokedMethodListener {

  @Override
  public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
    // TODO empty for now
  }

  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    // TODO implement interface for each provider type with a method that will return allure report
    // artifacts data to attach.
    if (method.isTestMethod()) {
      // attaching screenshot & failure URL
      if (Objects.nonNull(testResult.getThrowable())) {
        attachCurrentURLOnFailure();
        attachScreenshotOnFailure();
      }
    }
  }
}
