package com.applause.auto.mobile.views;

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
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.SupportsContextSwitching;
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

  @Locate(
      xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == 'Close' OR name == 'Back'`]",
      on = Platform.MOBILE_IOS)
  protected Button closeButton;

  @Locate(id = "android:id/button1", on = Platform.MOBILE_ANDROID)
  @Locate(xpath = "//XCUIElementTypeButton[@name='Ok']", on = Platform.MOBILE_IOS)
  protected Button okayPopUpButton;

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

  @Override
  public void afterInit() {
    logger.info(">>>>>>" + ((SupportsContextSwitching) SdkHelper.getDriver()).getContextHandles());
    SdkHelper.getSyncHelper().sleep(10000);
    logger.info(">>>>>>" + ((SupportsContextSwitching) SdkHelper.getDriver()).getContextHandles());
  }

  /* -------- Actions -------- */

  public ItemOptions getItemOptions(String itemName) {
    ((IOSDriver) SdkHelper.getDriver()).setSetting("snapshotMaxDepth", 99);
    MobileHelper.scrollElementIntoView(itemOptionsText.format(itemName));
    itemOptionsList.format(itemName).initialize();
    itemOptionsList
        .stream()
        .forEach(
            i -> {
              logger.info("Found options: " + i.getText());
            });
    return new ItemOptions(itemOptionsList);
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
