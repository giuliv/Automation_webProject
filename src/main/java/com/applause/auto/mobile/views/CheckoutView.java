package com.applause.auto.mobile.views;

import static com.applause.auto.mobile.helpers.MobileHelper.getElementTextAttribute;

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
          "//*[contains(@resource-id, 'productRecyclerView')]//*[contains(@resource-id, 'productImage')]",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      xpath =
          "//XCUIElementTypeOther[@name='Your Order']/following-sibling::*[child::*[contains(@name,'$')]"
              + " and following-sibling::*[@name='Payment Method']]",
      on = Platform.MOBILE_IOS)
  protected List<ContainerElement> orderedItems;

  @Locate(
      xpath =
          "//*[@text='Available Rewards']/following-sibling::*[contains(@resource-id, 'rewardRecyclerView')]"
              + "//*[contains(@resource-id, 'rewardTitle')]",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      xpath =
          "//XCUIElementTypeOther[@name='Available Rewards']/following-sibling::*[1]"
              + "//XCUIElementTypeStaticText",
      on = Platform.MOBILE_IOS)
  protected List<ContainerElement> availableRewards;

  @Locate(
      xpath =
          "//*[@text='You might also like']/following-sibling::*//*[contains(@resource-id, 'image')]",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      xpath =
          "//*[@name='You might also like']/following-sibling::*[contains(@type, 'XCUIElementTypeCell') and following-sibling::*[contains(@name, 'Available Rewards')]]/*[last()]",
      on = Platform.MOBILE_IOS)
  protected List<ContainerElement> youMightAlsoLikeProducts;

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

  @Locate(
      xpath = "//*[contains(@resource-id, 'totalLabel')]/following-sibling::*[1]",
      on = Platform.MOBILE_ANDROID)
  @Locate(xpath = "//*[@name='Order Total']/following-sibling::*[1]", on = Platform.MOBILE_IOS)
  protected Text orderTotal;

  /* -------- Actions -------- */

  public <T extends BaseComponent> T placeOrder(Class<T> clazz) {
    logger.info("Tap place order");
    MobileHelper.scrollDownHalfScreen(2);
    getPlaceOrderButton.click();
    return ComponentFactory.create(clazz);
  }

  /**
   * Click on reward item.
   *
   * @param awardText the reward
   */
  public CheckoutView clickOnAwardItem(String awardText) {
    logger.info("Selecting reward: " + awardText);
    SyncHelper.sleep(3000);
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
      SyncHelper.sleep(5000);
    }

    return ComponentFactory.create(CheckoutView.class);
  }

  /** Click on you might also like item. */
  public ProductDetailsView clickYouMightAlsoLikeItem() {
    logger.info("Clicking on you might also like item");
    if (youMightAlsoLikeProducts.isEmpty()) {
      throw new IllegalStateException("No 'You Might Also Like' items were found");
    }
    youMightAlsoLikeProducts.get(0).click();

    return ComponentFactory.create(ProductDetailsView.class);
  }

  /**
   * Get total order value
   *
   * @return order total value in $
   */
  public String getOrderTotal() {
    return orderTotal.getAttributeValue("value");
  }

  /** Get total ordered items count */
  public int getOrderedItemsCount() {
    return orderedItems.size();
  }

  /** unused */
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

  /**
   * Get total order value
   *
   * @return order total value in $
   */
  public String getOrderTotal() {
    return orderTotal.getText();
  }
}
