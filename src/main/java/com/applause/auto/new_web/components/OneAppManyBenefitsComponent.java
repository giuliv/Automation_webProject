package com.applause.auto.new_web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.CommonWebPage;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

public class OneAppManyBenefitsComponent extends BaseComponent {

  @Locate(xpath = "//section[@class='store-locator__app']", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(
      xpath = "//section[@class='store-locator__app']//p[@class='info-cta__description']",
      on = Platform.WEB)
  private Text descriptionText;

  @Locate(
      xpath = "//section[@class='store-locator__app']//a[text()='Download the App']",
      on = Platform.WEB)
  private Link downloadAppButton;

  @Override
  public void afterInit() {
    super.afterInit();
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
    WebHelper.scrollToElement(mainContainer);
  }

  @Step("Verify modal is displayed properly with description and 'Download the app' button")
  public boolean isDisplayedProperly() {
    if (!WebHelper.isDisplayed(descriptionText)) {
      logger.error("Description isn't displayed");
      return false;
    }

    if (!WebHelper.isDisplayed(downloadAppButton)) {
      logger.error("'Download the app' button isn't displayed");
      return false;
    }

    return true;
  }

  @Step("Click on the 'Download the app' button")
  public CommonWebPage clickDownloadApp() {
    logger.info("Clicking on the 'Download the app' button");
    String windowHandle = SdkHelper.getDriver().getWindowHandle();
    downloadAppButton.click();
    WebHelper.switchToNewTab(windowHandle);
    return SdkHelper.create(CommonWebPage.class);
  }
}
