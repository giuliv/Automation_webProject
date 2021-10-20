package com.applause.auto.util;

import com.applause.auto.framework.SdkHelper;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;
import java.util.Objects;

import static com.applause.auto.framework.SdkHelper.getDriver;

public class AllureUtils {

    private static final Logger logger = LogManager.getLogger(AllureUtils.class);

    public static void attachScreenshotOnFailure() {
        if (Objects.nonNull(getDriver())) {
            logger.info("Taking screenshot on test failure");
            try {
                Allure.addAttachment(
                        "Screenshot on test failure",
                        "image/png",
                        new ByteArrayInputStream(
                                ((TakesScreenshot) SdkHelper.getDriver()).getScreenshotAs(OutputType.BYTES)),
                        "png");
            } catch (Exception e) {
                logger.error("Error taking screenshot on test failure: " + e.getMessage());
            }
        }
    }

    public static void attachCurrentURLOnFailure() {
        if (Objects.nonNull(getDriver())) {
            logger.info("Taking current URL on test failure");
            try {
                Allure.addAttachment(
                        "Current URL on test failure",
                        "text/plain",
                        SdkHelper.getDriver().getCurrentUrl(),
                        ".log");
            } catch (Exception e) {
                logger.error("Error taking current URL on test failure: " + e.getMessage());
            }
        }
    }

    public static void attachCurrentPageSourceOnFailure() {
        if (Objects.nonNull(getDriver())) {
            logger.info("Taking page source on test failure");
            try {
                Allure.addAttachment(
                        "Current Page source on test failure",
                        "text/plain",
                        SdkHelper.getDriver().getPageSource(),
                        ".log");
            } catch (Exception e) {
                logger.error("Error taking current page source on test failure: " + e.getMessage());
            }
        }
    }
}
