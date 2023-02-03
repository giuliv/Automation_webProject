package com.applause.auto.web.components.plp;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.helpers.WebHelper;
import io.qameta.allure.Step;

@Implementation(is = PlpLearnMoreOverlappingComponent.class, on = Platform.WEB)
@Implementation(is = PlpLearnMoreOverlappingComponentMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class PlpLearnMoreOverlappingComponent extends BaseComponent {

  @Locate(xpath = "//button[@class='sr-modal__footer-close-button']", on = Platform.WEB)
  private Button clickElement;

  @Locate(
      xpath =
          "//div[starts-with(@class,'GradientBackground-')]/div/p/span[contains(@class,'signup__BenefitsMessage-')]",
      on = Platform.WEB)
  protected Text shippingText;

  @Locate(xpath = "//XCUIElementTypeStaticText[@name=\"Free 2-Day Shipping\"]", on = Platform.WEB)
  protected Text shippingTextIOS;

  @Locate(xpath = "//XCUIElementTypeStaticText[@name=\"& Free Returns\"]", on = Platform.WEB)
  protected Text shippingTextPart2IOS;

  @Locate(xpath = "//iframe[@title='ShopRunner Modal Container']", on = Platform.WEB)
  protected ContainerElement frame;

  @Locate(css = "iframe.zoid-component-frame", on = Platform.WEB)
  protected ContainerElement innerFrame;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(frame).visible());
    logger.info("Loading to the ShoppRunner Modal frame");
    WebHelper.switchToIFrame(frame);
  }

  @Step("Click On Close Button")
  public <T extends BaseComponent> T clickCloseButton(Class<T> expectedPage) {
    WebHelper.scrollToElement(clickElement);

    SdkHelper.getSyncHelper().wait(Until.uiElement(clickElement).visible());
    WebHelper.scrollToElement(clickElement);

    if (WebHelper.isSafari() && !WebHelper.isMobile()) {
      logger.info("Safari Desktop");
      WebHelper.jsClick(clickElement.getWebElement());
    } else {
      clickElement.click();
    }
    SdkHelper.getDriver().switchTo().defaultContent();
    return SdkHelper.create(expectedPage);
  }

  @Step("Get Free Shipping Text")
  public String getShippingText() {
    logger.info("Switching to innerFrame");
    WebHelper.switchToIFrame(innerFrame);
    String name = WebHelper.cleanString(shippingText.getText());
    logger.info("Product name [{}]", name);
    logger.info("switching back to the ShoppRunner Modal frame");
    SdkHelper.getDriver().switchTo().defaultContent();
    WebHelper.switchToIFrame(frame);
    return name;
  }
}

class PlpLearnMoreOverlappingComponentMobile extends PlpLearnMoreOverlappingComponent {
  public String getShippingText() {
    String name;
    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      logger.info("Running cases on Mobile iOS");
      String oldContext = WebHelper.getCurrentContext();
      WebHelper.switchToNativeContext();
      SdkHelper.getSyncHelper().sleep(3000); // Extra wait due to context change
      if (shippingTextIOS.isDisplayed() & shippingTextPart2IOS.isDisplayed()) {
        name = "Free 2-Day Shipping & Free Returns";
      } else {
        logger.info(
            "Shipping text [{}] [{}]", shippingTextIOS.getText(), shippingTextPart2IOS.getText());
        name = "Shipping text is not correct";
      }
      logger.info("switching back to the ShoppRunner Modal");
      WebHelper.switchToWeb(oldContext);
      SdkHelper.getSyncHelper().sleep(3000); // Extra wait due to context change
    } else {
      logger.info("Switching to innerFrame");
      WebHelper.switchToIFrame(innerFrame);
      name = WebHelper.cleanString(shippingText.getText());
      logger.info("Shipping text [{}]", name);
      logger.info("switching back to the ShoppRunner Modal frame");
      SdkHelper.getDriver().switchTo().defaultContent();
      WebHelper.switchToIFrame(frame);
    }

    return name;
  }
}
