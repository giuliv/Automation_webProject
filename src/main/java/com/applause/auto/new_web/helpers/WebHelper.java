package com.applause.auto.new_web.helpers;

import com.applause.auto.integrations.helpers.SdkHelper;
import com.applause.auto.pageobjectmodel.elements.BaseElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.interactions.Actions;

import java.lang.invoke.MethodHandles;

public class WebHelper extends SdkHelper {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  public static void hoverByAction(BaseElement element) {
    logger.info("Hover over...");
    Actions actions = new Actions(getDriver());
    actions.moveToElement(element.getWebElement()).perform();
    getSyncHelper().sleep(1000); // Wait for action
  }
}
