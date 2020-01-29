package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.control.DeviceControl;
import com.applause.auto.util.helper.SyncHelper;

import java.util.List;

import static com.applause.auto.mobile.helpers.MobileHelper.getElementTextAttribute;

@Implementation(is = AndroidCheckoutView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = CheckoutView.class, on = Platform.MOBILE_IOS)
public class CheckoutView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//android.widget.TextView[@text='CHECKOUT']", on = Platform.MOBILE_ANDROID)
  @Locate(xpath = "//XCUIElementTypeNavigationBar[@name=\"CHECKOUT\"]", on = Platform.MOBILE_IOS)
  protected Text getHeadingText;

  @Locate(id = "com.wearehathway.peets.development:id/checkoutButton", on = Platform.MOBILE_ANDROID)
  @Locate(id = "Place Order", on = Platform.MOBILE_IOS)
  protected Button getPlaceOrderButton;

  @Locate(
      xpath =
          "//*[@text='Available Rewards']/following-sibling::*[contains(@resource-id, 'rewardRecyclerView')]//*[contains(@resource-id, 'rewardTitle')]",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      xpath =
          "//XCUIElementTypeOther[@name='Available Rewards']/following-sibling::*[1]//XCUIElementTypeStaticText",
      on = Platform.MOBILE_IOS)
  protected List<ContainerElement> availableRewards;

  @Locate(id = "detailButton", on = Platform.MOBILE_ANDROID)
  @Locate(xpath = "//*[@name='Redeem']", on = Platform.MOBILE_IOS)
  protected Button radeemButton;

  @Locate(
      xpath = "//*[contains(@text, 'Selected reward is not valid')]",
      on = Platform.MOBILE_ANDROID)
  @Locate(xpath = "//*[contains(@label, 'Selected reward is not valid')]", on = Platform.MOBILE_IOS)
  protected Button selectedRewardIsNotValid;

  @Locate(id = "android:id/button1", on = Platform.MOBILE_ANDROID)
  @Locate(xpath = "//XCUIElementTypeButton[@name='Ok']", on = Platform.MOBILE_IOS)
  protected Button okayPopUpButton;

  /* -------- Actions -------- */

  public <T extends BaseComponent> T placeOrder(Class<T> clazz) {
    logger.info("Tap place order");
    MobileHelper.scrollDownHalfScreen(2);
    getPlaceOrderButton.click();
    return ComponentFactory.create(clazz);
  }

  /**
   * Gets reward items.
   *
   * @param awardText the reward
   */
  public CheckoutView clickOnAwardItem(String awardText) {
    logger.info("Select reward: " + awardText);
    SyncHelper.sleep(5000);
    MobileHelper.swipeAcrossScreenCoordinates(0.5, 0.8, 0.5, 0.5, 100);

    boolean areAvailableRewardsDisplayed;
    try {
      areAvailableRewardsDisplayed = !availableRewards.isEmpty();
    } catch (Exception e) {
      areAvailableRewardsDisplayed = false;
    }

    if (areAvailableRewardsDisplayed) {
      availableRewards
          .stream()
          .filter(item -> getElementTextAttribute(item).startsWith(awardText))
          .findAny()
          .orElseThrow(
              () ->
                  new IllegalStateException(
                      String.format("Award starting with %s was not found", awardText)))
          .click();
      SyncHelper.waitUntil(condition -> radeemButton.isEnabled());
      radeemButton.click();

      waitForRewardIsNotValid();
    }

    return ComponentFactory.create(CheckoutView.class);
  }

  private void waitForRewardIsNotValid() {
    logger.info("Waiting for 'reward not valid' message");
    try {
      SyncHelper.waitUntil(condition -> selectedRewardIsNotValid.isEnabled());
      logger.info("'reward not valid' message appeared");
      SyncHelper.sleep(1000);
      okayPopUpButton.click();
      DeviceControl.tapElementCenter(okayPopUpButton);
    } catch (Exception e) {
      logger.info("'reward not valid' message didn't appear");
    }
    SyncHelper.sleep(1000);
  }
}

class AndroidCheckoutView extends CheckoutView {

  public <T extends BaseComponent> T placeOrder(Class<T> clazz) {
    logger.info("Tap place order");
    MobileHelper.swipeAcrossScreenCoordinates(0.5, 0.8, 0.5, 0.2, 100);
    getPlaceOrderButton.click();
    return ComponentFactory.create(clazz);
  }
}
