package com.applause.auto.reportportal;

import com.applause.auto.framework.SdkHelper;
import com.epam.reportportal.listeners.LogLevel;
import com.epam.reportportal.message.ReportPortalMessage;
import com.epam.reportportal.service.ReportPortal;
import com.epam.reportportal.testng.BaseTestNGListener;
import com.epam.reportportal.testng.ITestNGService;
import java.util.Calendar;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import rp.com.google.common.base.Supplier;
import rp.com.google.common.base.Suppliers;
import rp.com.google.common.io.ByteSource;

@Log4j2
public class ReportPortalExtendedTestFailureListener extends BaseTestNGListener {
  private static final Supplier<ITestNGService> SERVICE =
      Suppliers.memoize(ReportPortalParamOverrideService::new);

  public ReportPortalExtendedTestFailureListener() {
    super(SERVICE.get());
  }

  @Override
  public void onTestFailure(ITestResult iTestResult) {
    try {
      ReportPortalMessage screenshotMessage;
      screenshotMessage =
          new ReportPortalMessage(
              ByteSource.wrap(
                  ((TakesScreenshot) SdkHelper.getDriver()).getScreenshotAs(OutputType.BYTES)),
              "image/png",
              "Failed step screenshot");
      ReportPortal.emitLog(
          screenshotMessage, LogLevel.TRACE.name(), Calendar.getInstance().getTime());
    } catch (Exception e) {
      // log.error("Error when attaching issue:", e);
      // ignore
    }
    super.onTestFailure(iTestResult);
  }
}
