package com.applause.auto.mobile.views;

import static com.applause.auto.mobile.helpers.MobileHelper.getElementTextAttribute;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.helpers.ItemOptions;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import org.openqa.selenium.NoSuchElementException;

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
      xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == 'Close' OR name == 'Back'`]",
      on = Platform.MOBILE_IOS)
  protected Button closeButton;

  @Locate(
      xpath =
          "//*[contains(@resource-id, 'productRecyclerView')]//*[contains(@resource-id, 'productImage')]",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      xpath =
          "//XCUIElementTypeOther[@name='Your Order']/following-sibling::*[child::*[contains(@name,'$')]"
              + " and following-sibling::*[@name='Available Rewards']]",
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

  @Locate(
      xpath =
          "//android.widget.TextView[@resource-id='com.wearehathway.peets.development:id/productName' and @text='%s']/following-sibling::android.widget.TextView[@resource-id='com.wearehathway.peets.development:id/productOptions']",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"%s\"]/preceding-sibling::XCUIElementTypeStaticText[1]",
      on = Platform.MOBILE_IOS)
  protected Text itemOptionsText;

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"%s\"]/preceding-sibling::XCUIElementTypeStaticText",
      on = Platform.MOBILE_IOS)
  protected LazyList<Text> itemOptionsList;

  @Locate(xpath = "//XCUIElementTypeStaticText[@name=\"%s\"]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.TextView[@resource-id='com.wearehathway.peets.development:id/productName' and @text='%s']",
      on = Platform.MOBILE_ANDROID)
  protected Text productItemElement;

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"%s\"]/following-sibling::XCUIElementTypeButton[@name=\"DELETE\"]",
      on = Platform.MOBILE_IOS)
  protected Button productItemDeleteButton;

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"%s\"]/following-sibling::XCUIElementTypeButton[contains(@name,\"Delete\")]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.view.ViewGroup[.//android.widget.TextView[contains(@text, '%s')]]/*[contains(@resource-id, 'id/delete')]",
      on = Platform.MOBILE_ANDROID)
  protected Button productItemDeleteByEditButton;

  @Locate(accessibilityId = "Edit", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/editButton\")",
      on = Platform.MOBILE_ANDROID)
  protected Button editButton;

  @Locate(
      xpath =
          "//android.widget.TextView[@resource-id='com.wearehathway.peets.development:id/productName' and @text='%s']/following-sibling::android.widget.TextView[@resource-id='com.wearehathway.peets.development:id/productQuantity']",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"%s\"]/preceding-sibling::XCUIElementTypeStaticText[2]",
      on = Platform.MOBILE_IOS)
  protected Text itemQtyText;

  @Locate(
      xpath =
          "//android.widget.TextView[@resource-id='com.wearehathway.peets.development:id/productName' and @text='%s']/../..//android.widget.TextView[@resource-id='com.wearehathway.peets.development:id/productCost']",
      on = Platform.MOBILE_ANDROID)
  protected Text itemCostText;

  @Override
  public void afterInit() {
    logger.info(">>>>>>" + ((AppiumDriver) SdkHelper.getDriver()).getContextHandles());
    SdkHelper.getSyncHelper().sleep(10000);
    logger.info(">>>>>>" + ((AppiumDriver) SdkHelper.getDriver()).getContextHandles());
  }

  /* -------- Actions -------- */

  /**
   * Place order t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T placeOrder(Class<T> clazz) {
    logger.info("Tap place order");
    MobileHelper.scrollDownHalfScreen(2);
    getPlaceOrderButton.click();
    return SdkHelper.create(clazz);
  }

  /**
   * Click on reward item.
   *
   * @param awardText the reward
   */
  public CheckoutView clickOnAwardItem(String awardText) {
    logger.info("Selecting reward: " + awardText);
    SdkHelper.getSyncHelper().sleep(3000);
    MobileHelper.swipeAcrossScreenCoordinates(0.5, 0.8, 0.5, 0.5, 100);

    boolean areAvailableRewardsDisplayed;
    try {
      areAvailableRewardsDisplayed = !availableRewards.isEmpty();
    } catch (Exception e) {
      areAvailableRewardsDisplayed = false;
    }

    if (areAvailableRewardsDisplayed) {
      availableRewards.stream()
          .filter(item -> getElementTextAttribute(item).startsWith(awardText))
          .findAny()
          .orElseThrow(
              () ->
                  new IllegalStateException(
                      String.format("Award starting with %s was not found", awardText)))
          .click();
      SdkHelper.getSyncHelper().waitUntil(condition -> radeemButton.isEnabled());
      radeemButton.click();
      SdkHelper.getSyncHelper().sleep(5000);
    }

    return SdkHelper.create(CheckoutView.class);
  }

  /** Click on you might also like item. */
  public ProductDetailsView clickYouMightAlsoLikeItem() {
    logger.info("Clicking on you might also like item");
    if (youMightAlsoLikeProducts.isEmpty()) {
      throw new IllegalStateException("No 'You Might Also Like' items were found");
    }
    youMightAlsoLikeProducts.get(0).click();

    return SdkHelper.create(ProductDetailsView.class);
  }

  /**
   * Get total order value
   *
   * @return order total value in $
   */
  public String getOrderTotal() {
    int attempt = 5;
    try {
      orderTotal.initialize();
    } catch (Throwable th) {
      IntStream.range(0, attempt)
          .forEach(
              i -> {
                MobileHelper.scrollUpCloseToMiddleAlgorithm();
              });
    }
    while (attempt-- > 0 && !orderTotal.exists()) {
      MobileHelper.scrollDownCloseToMiddleAlgorithm();
    }
    String result = orderTotal.getAttributeValue("value");
    IntStream.range(0, 5).forEach((i) -> MobileHelper.scrollUpCloseToMiddleAlgorithm());
    return result;
  }

  /** Get total ordered items count */
  public int getOrderedItemsCount() {
    return orderedItems.size();
  }

  /** unused */
  private void waitForRewardIsNotValid() {
    logger.info("Waiting for 'reward not valid' message");
    try {
      SdkHelper.getSyncHelper().waitUntil(condition -> selectedRewardIsNotValid.isEnabled());
      logger.info("'reward not valid' message appeared");
      SdkHelper.getSyncHelper().sleep(1000);
      okayPopUpButton.click();
      SdkHelper.getDeviceControl().tapElementCenter(okayPopUpButton);
    } catch (Exception e) {
      logger.info("'reward not valid' message didn't appear");
    }
    SdkHelper.getSyncHelper().sleep(1000);
  }

  public ItemOptions getItemOptions(String itemName) {
    ((IOSDriver) SdkHelper.getDriver()).setSetting("snapshotMaxDepth", 99);
    MobileHelper.scrollElementIntoView(itemOptionsText.format(itemName));
    itemOptionsList.format(itemName).initialize();
    itemOptionsList.stream()
        .forEach(
            i -> {
              logger.info("Found options: " + i.getText());
            });
    return new ItemOptions(itemOptionsList);
  }

  public CheckoutView refreshView() {
    return SdkHelper.create(CheckoutView.class);
  }

  /**
   * Close new order view.
   *
   * @return the new order view
   */
  public OrderView close() {
    logger.info("Close Checkout view");
    SdkHelper.getSyncHelper().wait(Until.uiElement(closeButton).visible());
    MobileHelper.tapOnElementCenter(closeButton);
    return SdkHelper.create(OrderView.class);
  }

  /**
   * Select product product details view.
   *
   * @param productName the product name
   * @return the product details view
   */
  public ProductDetailsView selectProduct(String productName) {
    int attempt = 2;
    try {
      productItemElement.format(productName).initialize();
    } catch (Throwable th) {
      IntStream.range(0, attempt)
          .forEach(
              i -> {
                MobileHelper.scrollUpCloseToMiddleAlgorithm();
              });
    }
    while (attempt-- > 0 && !productItemElement.exists()) {
      MobileHelper.scrollDownCloseToMiddleAlgorithm();
      SdkHelper.getSyncHelper().sleep(1000);
    }
    productItemElement.format(productName).click();
    return SdkHelper.create(ProductDetailsView.class);
  }

  /**
   * Is product displayed boolean.
   *
   * @param productName the product name
   * @return the boolean
   */
  public boolean isProductDisplayed(String productName) {
    int attempt = 2;
    try {
      productItemElement.format(productName).initialize();
    } catch (Throwable th) {
      IntStream.range(0, attempt)
          .forEach(
              i -> {
                MobileHelper.scrollUpCloseToMiddleAlgorithm();
              });
    }
    while (attempt-- > 0 && !productItemElement.exists()) {
      MobileHelper.scrollDownCloseToMiddleAlgorithm();
      SdkHelper.getSyncHelper().sleep(1000);
    }
    try {
      productItemElement.format(productName).initialize();
      return true;
    } catch (Throwable th) {
      return false;
    }
  }

  /**
   * Cost of string.
   *
   * @param productName the product name
   * @return the string
   */
  public String costOf(String productName) {
    int attempt = 2;
    try {
      itemCostText.format(productName).initialize();
    } catch (Throwable th) {
      IntStream.range(0, attempt)
          .forEach(
              i -> {
                MobileHelper.scrollUpCloseToMiddleAlgorithm();
              });
    }

    while (attempt-- > 0 && !itemCostText.exists()) {
      MobileHelper.scrollDownCloseToMiddleAlgorithm();
      SdkHelper.getSyncHelper().sleep(1000);
    }

    itemCostText.format(productName).initialize();
    return itemCostText.getText();
  }

  /**
   * Delete by swipe checkout view.
   *
   * @param productName the product name
   * @return the checkout view
   */
  public CheckoutView deleteBySwipe(String productName) {
    logger.info("Swiping product: " + productName);
    SdkHelper.getDeviceControl()
        .swipeAcrossElementWithDirection(
            productItemElement.format(productName), SwipeDirection.LEFT);
    productItemDeleteButton.format(productName).click();
    return SdkHelper.create(CheckoutView.class);
  }

  /**
   * Delete by edit button checkout view.
   *
   * @param productName the product name
   * @return the checkout view
   */
  public CheckoutView deleteByEditButton(String productName) {
    logger.info("Click edit button");
    IntStream.range(0, 2)
        .forEach(
            i -> {
              MobileHelper.scrollUpCloseToMiddleAlgorithm();
            });
    editButton.click();

    logger.info("Scroll down and up [in Order to elements be populated]");
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action
    MobileHelper.scrollDownHalfScreen(3);

    SdkHelper.getSyncHelper().sleep(2000); // Wait for scroll
    MobileHelper.scrollElementIntoView(productItemDeleteByEditButton.format(productName));
    SdkHelper.getSyncHelper().sleep(2000); // Wait for scroll

    productItemDeleteByEditButton.format(productName).click();

    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(productItemDeleteButton.format(productName)).present());
    productItemDeleteButton.format(productName).click();
    return SdkHelper.create(CheckoutView.class);
  }
}

class AndroidCheckoutView extends CheckoutView {

  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/editButton\").text(\"Done\")",
      on = Platform.MOBILE_ANDROID)
  protected Button doneButton;

  @Override
  public void afterInit() {
    try {
      logger.info("Trying to click OKAY if present");
      SdkHelper.getSyncHelper().sleep(3000);
      okayPopUpButton.initialize();
      okayPopUpButton.click();
      SdkHelper.getSyncHelper().sleep(3000);
      okayPopUpButton.initialize();
      okayPopUpButton.click();
    } catch (NoSuchElementException nse) {

    }
  }

  public <T extends BaseComponent> T placeOrder(Class<T> clazz) {
    logger.info("Tap place order");
    MobileHelper.swipeWithCount(SwipeDirection.UP, 4);
    getPlaceOrderButton.click();
    return SdkHelper.create(clazz);
  }

  /**
   * Get total order value
   *
   * @return order total value in $
   */
  public String getOrderTotal() {
    MobileHelper.swipeWithCount(SwipeDirection.UP, 2);
    return orderTotal.getText();
  }

  public ItemOptions getItemOptions(String itemName) {
    int attempt = 2;
    try {
      itemOptionsText.format(itemName).initialize();
    } catch (Throwable th) {
      IntStream.range(0, attempt)
          .forEach(
              i -> {
                MobileHelper.scrollUpCloseToMiddleAlgorithm();
              });
    }

    while (attempt-- > 0 && !itemOptionsText.exists()) {
      MobileHelper.scrollDownCloseToMiddleAlgorithm();
      SdkHelper.getSyncHelper().sleep(1000);
    }
    List<String> result =
        new ArrayList<String>(Arrays.asList(itemOptionsText.getText().split("\n")));
    try {
      itemQtyText.format(itemName).initialize();
      result.add(itemQtyText.getText());
    } catch (NoSuchElementException nse) {
      logger.warn("Item quantity option does not found");
    }
    result.forEach(
        i -> {
          logger.info("Found option: " + i);
        });
    return new ItemOptions(result);
  }

  @Override
  public CheckoutView deleteByEditButton(String productName) {
    logger.info("Click edit button");
    IntStream.range(0, 2)
        .forEach(
            i -> {
              MobileHelper.scrollUpCloseToMiddleAlgorithm();
            });
    editButton.click();

    logger.info("Scroll down and up [in Order to elements be populated]");
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action
    MobileHelper.scrollDownHalfScreen(3);

    SdkHelper.getSyncHelper().sleep(2000); // Wait for scroll
    MobileHelper.scrollElementIntoView(productItemDeleteByEditButton.format(productName));

    logger.info("Tapping on 'Delete' button");
    productItemDeleteByEditButton.format(productName).click();

    logger.info("Tapping on 'Done'");
    SdkHelper.getSyncHelper().wait(Until.uiElement(doneButton).clickable());
    doneButton.click();
    return SdkHelper.create(CheckoutView.class);
  }
}
