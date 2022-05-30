package com.applause.auto.new_web.components.plp;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.components.QuickViewComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

@Implementation(is = PlpLearnMoreOverlappingComponent.class, on = Platform.WEB)
@Implementation(is = PlpLearnMoreOverlappingComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class PlpLearnMoreOverlappingComponent extends BaseComponent {

  @Locate(xpath = "//button[@class='sr-modal__footer-close-button']", on = Platform.WEB)
  private Button clickElement;

  @Locate(
      xpath =
          "//div[starts-with(@class,'GradientBackground-')]/div/p/span[starts-with(@class,'signup__BenefitsMessage-')]",
      on = Platform.WEB)
  private Text shippingText;

  @Locate(xpath = "//iframe[@title='ShopRunner Modal Container']", on = Platform.WEB)
  private ContainerElement frame;

  @Locate(css = "iframe.zoid-component-frame", on = Platform.WEB)
  private ContainerElement innerFrame;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(frame).visible());
    logger.info("Loading to the ShoppRunner Modal frame");
    WebHelper.switchToIFrame(frame);
  }

  @Step("Click On Close Button")
  public QuickViewComponent clickCloseButton() {
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
    return SdkHelper.create(QuickViewComponent.class);
  }

  @Step("Get Free Shipping Text")
  public String getShippingText() {
    logger.info("Switching to innerframe");
    WebHelper.switchToIFrame(innerFrame);
    String name = WebHelper.cleanString(shippingText.getText());
    logger.info("Product name [{}]", name);
    logger.info("switching back to the ShoppRunner Modal frame");
    SdkHelper.getDriver().switchTo().defaultContent();
    WebHelper.switchToIFrame(frame);
    return name;
  }
}
