package com.applause.auto.reportportal;

import com.epam.reportportal.service.Launch;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Base64;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReportPortalHelper {
  private static final Logger logger = LogManager.getLogger(ReportPortalHelper.class);

  public static boolean isCurrentLaunchDisabled() {
    return Objects.isNull(Launch.currentLaunch().getParameters().getEnable())
        || Boolean.FALSE.equals(Launch.currentLaunch().getParameters().getEnable());
  }

  public static void logReportPortal(byte[] bytes, String message) {
    logReportPortal(Base64.getEncoder().encodeToString(bytes), message);
  }

  public static void logReportPortal(String base64, String message) {
    logger.info("RP_MESSAGE#BASE64#{}#{}", base64, message);
  }
}
