package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.web.helpers.WebHelper;
import io.qameta.allure.Step;

@Implementation(is = ShopRunnerComponent.class, on = Platform.WEB)
@Implementation(is = ShopRunnerComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class ShopRunnerComponent extends BaseComponent {

  @Locate(css = "iframe[title='ShopRunner Modal Container']", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = ".zoid-outlet iframe[src]", on = Platform.WEB)
  private ContainerElement secondaryIframe;

  @Locate(css = "input[value='Spend And Get']", on = Platform.WEB)
  private ContainerElement learnMoreContent;

  @Locate(css = "div[class*='signin__ContentWrapper']", on = Platform.WEB)
  private ContainerElement signInContent;

  @Locate(css = "button.sr-modal__footer-close-button", on = Platform.WEB)
  protected Button closeOverlay;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
  }

  @Step("Go to iFrame")
  private void navigateToiFrame() {
    logger.info("Switching to main frame");
    WebHelper.switchToIFrame(mainContainer);

    logger.info("Switching to secondary frame");
    WebHelper.switchToIFrame(secondaryIframe);
  }

  /**
   * Review if learnMore modal is displayed
   *
   * @return boolean
   */
  @Step("Get Learn more modal")
  public boolean isLearnMoreModalDisplayed() {
    navigateToiFrame();
    boolean result = learnMoreContent.exists();
    SdkHelper.getDriver().switchTo().defaultContent();
    return result;
  }

  @Step("Close Shoprunner Overlay")
  public MiniCart closeOverlay() {
    logger.info("Closing overlay by clicking the X");
    WebHelper.switchToIFrame(mainContainer);
    SdkHelper.getSyncHelper().wait(Until.uiElement(closeOverlay).clickable()).click();
    SdkHelper.getDriver().switchTo().defaultContent();
    return SdkHelper.create(MiniCart.class);
  }

  /**
   * Review if signIn modal is displayed
   *
   * @return boolean
   */
  @Step("Get sign in")
  public boolean isSignInModalDisplayed() {
    navigateToiFrame();
    boolean result = signInContent.exists();
    SdkHelper.getDriver().switchTo().defaultContent();
    return result;
  }
}
