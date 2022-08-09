package com.applause.auto.new_web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

@Implementation(is = PeetnikRewardsComponent.class, on = Platform.WEB)
public class PeetnikRewardsComponent extends BaseComponent {

  @Locate(xpath = "//section[@class='store-locator__rewards']", on = Platform.WEB)
  protected ContainerElement mainContainer;

  @Locate(xpath = "//section[@class='store-locator__rewards']//img", on = Platform.WEB)
  protected Image bannerImage;

  @Locate(xpath = "//section[@class='store-locator__rewards']//h3", on = Platform.WEB)
  protected Text titleText;

  @Locate(xpath = "//section[@class='store-locator__rewards']//p", on = Platform.WEB)
  protected Text descriptionText;

  @Locate(
      xpath = "//section[@class='store-locator__rewards']//a[contains(@class,'btn--primary')]",
      on = Platform.WEB)
  protected Link learnMoreButton;

  @Override
  public void afterInit() {
    super.afterInit();
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
    WebHelper.scrollToElement(mainContainer);
  }

  @Step("Validate the user is shown with banner, description and Learn more button")
  public boolean isDisplayed() {
    logger.info("Checking the user is shown with banner, description and Learn more button");
    if (!WebHelper.isDisplayed(bannerImage)) {
      logger.error("Banner isn't displayed");
      return false;
    }

    if (!WebHelper.isDisplayed(descriptionText)) {
      logger.error("Description isn't displayed");
      return false;
    }

    if (!WebHelper.isDisplayed(learnMoreButton)) {
      logger.error("Learn More button isn't displayed");
      return false;
    }

    return true;
  }

  @Step("Click on the Learn More button")
  public void clickLearnMore() {
    logger.info("Clicking on the Learn More button");
    learnMoreButton.click();
  }
}
