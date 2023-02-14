package com.applause.auto.mobile.views;

import com.applause.auto.common.data.enums.OrderMenuCategory;
import com.applause.auto.common.data.enums.OrderMenuSubCategory;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.components.FreeDeliveryModalChunk;
import com.applause.auto.mobile.components.OrderMenuChunk;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import java.time.Duration;
import lombok.Getter;

@Implementation(is = OrderView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = IosOrderView.class, on = Platform.MOBILE_IOS)
public class OrderView extends BaseView {

  /* -------- Elements -------- */

  @Locate(xpath = "//XCUIElementTypeNavigationBar[@name=\"ORDER\"]", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.TextView[@text='ORDER']", on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/basketFABContainer\")",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      xpath =
          "//XCUIElementTypeOther[child:: XCUIElementTypeImage and XCUIElementTypeStaticText[@name>0]]",
      on = Platform.MOBILE_IOS)
  protected Button getCartButton;

  @Locate(id = "com.wearehathway.peets.development:id/confirmButton", on = Platform.MOBILE_ANDROID)
  @Locate(id = "Menu", on = Platform.MOBILE_IOS)
  protected Button getConfirmStoreButton;

  @Locate(xpath = "//android.widget.TextView[@text='Recents']", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"Recents\"`]",
      on = Platform.MOBILE_IOS)
  protected Button recentsTabButton;

  @Locate(
      xpath = "//android.widget.TextView[@text='No Recent Orders']",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"No Recent Orders\"`]",
      on = Platform.MOBILE_IOS)
  protected Button titleNoRecentOrdersText;

  @Locate(
      xpath = "//android.widget.TextView[@text='No Favorited Orders']",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"No Favorited Orders\"`]",
      on = Platform.MOBILE_IOS)
  protected Button titleNoFavoriteOrders;

  @Locate(xpath = "//android.widget.TextView[@text='Favorites']", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"Favorites\"`]",
      on = Platform.MOBILE_IOS)
  protected Button favoritesTabButton;

  @Locate(
      xpath = "//android.widget.Button[@text=\"Start New Order\"]",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"Start New Order\"`][1]",
      on = Platform.MOBILE_IOS)
  protected Button startNewOrderRecentsButton;

  @Locate(
      xpath = "//android.widget.Button[@text=\"Start New Order\"]",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"Start New Order\"`][2]",
      on = Platform.MOBILE_IOS)
  protected Button startNewOrderFavoritesButton;

  @Locate(id = "com.wearehathway.peets.development:id/orderTabLayout", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Menu\"`][2]",
      on = Platform.MOBILE_IOS)
  protected Text menuSignatureText;

  @Locate(xpath = "//android.widget.TextView[@text='%s']", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"%s\"`]",
      on = Platform.MOBILE_IOS)
  protected Text messageText;

  @Locate(
      id = "com.wearehathway.peets.development:id/addOrUpdateProductButton",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"Add to Order\"`]",
      on = Platform.MOBILE_IOS)
  protected Button addToOrderButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeOther[$name == 'Pickup'$][-1]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/storePinImage\")",
      on = Platform.MOBILE_ANDROID)
  protected Button getLocateCoffeeBars;

  @Locate(iOSClassChain = "**/XCUIElementTypeButton[`name == '%s'`]", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator =
          "new UiSelector().resourceIdMatches(\".*id/productNameTextView\").text(\"%s\")",
      on = Platform.MOBILE_ANDROID)
  protected Button menuCategoryButton;

  @Locate(iOSClassChain = "**/XCUIElementTypeButton[`name == '%s'`]", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator =
          "new UiSelector().resourceIdMatches(\".*id/categoryTextView\").textStartsWith(\"%s\")",
      on = Platform.MOBILE_ANDROID)
  protected Button menuSubCategoryButton;

  @Locate(iOSClassChain = "**/XCUIElementTypeButton[`name == 'Back'`]", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator =
          "new UiSelector().resourceIdMatches(\".*id/toolbar\").childSelector(new UiSelector().className(\"android.widget.ImageButton\"))",
      on = Platform.MOBILE_ANDROID)
  protected Text backButton;

  @Locate(
      iOSClassChain =
          "**/XCUIElementTypeOther[$name == 'Pickup'$][-1]/XCUIElementTypeStaticText[1]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/storeTextView\")",
      on = Platform.MOBILE_ANDROID)
  protected Text storeName;

  @Getter @Locate OrderMenuChunk orderMenuChunk;

  /* -------- Actions -------- */
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(menuSignatureText).present().setTimeout(Duration.ofSeconds(15)));
  }

  /**
   * Get the text vaalue of the heading
   *
   * @return
   */
  public String getHeadingTextValue() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(getHeadingText).present());
    String heading = getHeadingText.getText();
    logger.info("Heading Text  - [{}]", heading);
    return heading;
  }

  public OrderView selectCategory(OrderMenuCategory category) {
    logger.info("Tapping on [{}] category", category.getCategory());
    menuCategoryButton.format(category.getCategory());
    MobileHelper.scrollDownToElementCloseToMiddle(menuCategoryButton, 4);
    menuCategoryButton.click();
    return this;
  }

  public SubCategoryView selectSubCategory(OrderMenuSubCategory subCategory) {
    logger.info("Tapping on [{}] sub category", subCategory.getSubCategory());
    menuSubCategoryButton.format(subCategory.getSubCategory());
    menuSubCategoryButton.click();
    return SdkHelper.create(SubCategoryView.class);
  }

  /**
   * Checkout
   *
   * @return CheckoutView
   */
  public CheckoutView checkout() {
    logger.info("Tap on Cart button");
    getCartButton.click();
    try {
      logger.info("Waiting for confirmation button");
      SdkHelper.getSyncHelper()
          .wait(Until.uiElement(getConfirmStoreButton).present().setTimeout(Duration.ofSeconds(5)));
      getConfirmStoreButton.click();
    } catch (Exception e) {
      logger.info("Confirmation button is not present");
    }
    return SdkHelper.create(CheckoutView.class);
  }

  /**
   * Checkout atom t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T checkoutAtom(Class<T> clazz) {
    logger.info("Tap on Cart button");
    getCartButton.click();
    return SdkHelper.create(clazz);
  }

  /**
   * Recents new order view.
   *
   * @return the new order view
   */
  public OrderView recents() {
    logger.info("Click on recents tab");
    recentsTabButton.click();
    return SdkHelper.create(OrderView.class);
  }

  /**
   * Is title no recent orders displayed boolean.
   *
   * @return the boolean
   */
  public boolean isTitleNoRecentOrdersDisplayed() {
    logger.info("Checking if title <No recent orders> displayed");
    return titleNoRecentOrdersText.isDisplayed();
  }

  /**
   * Is message displayed boolean.
   *
   * @param message the message
   * @return the boolean
   */
  public boolean isMessageDisplayed(String message) {
    logger.info("Checking if message displayed: " + message);
    return messageText.format(message).isDisplayed();
  }

  /**
   * Is start new order button displayed boolean.
   *
   * @return the boolean
   */
  public boolean isStartNewOrderRecentsButtonDisplayed() {
    logger.info("Checking if <start new order> button displayed");
    return startNewOrderRecentsButton.isDisplayed();
  }

  /**
   * Is start new order favorites button displayed boolean.
   *
   * @return the boolean
   */
  public boolean isStartNewOrderFavoritesButtonDisplayed() {
    logger.info("Checking if <start new order> button displayed");
    return startNewOrderFavoritesButton.isDisplayed();
  }

  /**
   * Start new order new order view.
   *
   * @return the new order view
   */
  public OrderView startNewOrderRecents() {
    logger.info("Click <start new order> button");
    startNewOrderRecentsButton.click();
    return SdkHelper.create(OrderView.class);
  }

  /**
   * Start new order favorites new order view.
   *
   * @return the new order view
   */
  public OrderView startNewOrderFavorites() {
    logger.info("Click <start new order> button");
    startNewOrderFavoritesButton.click();
    return SdkHelper.create(OrderView.class);
  }

  /**
   * Is menu categories displayed boolean.
   *
   * @return the boolean
   */
  public boolean isMenuCategoriesDisplayed() {
    logger.info("Checking if Menu categories are visible");
    return menuSignatureText.isDisplayed();
  }

  /**
   * Favorites new order view.
   *
   * @return the new order view
   */
  public OrderView favorites() {
    logger.info("Click Favorites tab");
    favoritesTabButton.click();
    return SdkHelper.create(OrderView.class);
  }

  /**
   * Is title no favorite orders displayed boolean.
   *
   * @return the boolean
   */
  public boolean isTitleNoFavoriteOrdersDisplayed() {
    logger.info("Checking if favourite tab displayed");
    return titleNoFavoriteOrders.isDisplayed();
  }

  public OrderView addToOrders() {
    logger.info("Tapping on 'Add To Order' button");
    addToOrderButton.click();
    SdkHelper.getSyncHelper().sleep(2000);
    return SdkHelper.create(OrderView.class);
  }

  /**
   * Tap to locate Coffeebars.
   *
   * @return the select coffee bar view by location
   */
  public <T extends BaseComponent> T locateCoffeebars(Class<T> clazz) {
    logger.info("Tap to locate Coffeebars");
    getLocateCoffeeBars.click();
    return SdkHelper.create(clazz);
  }

  public <T extends BaseComponent> T back(Class<T> clazz) {
    backButton.click();
    return SdkHelper.create(clazz);
  }

  /**
   * Gets store name.
   *
   * @return the store name
   */
  public String getStoreName() {
    return storeName.getText();
  }
}

class IosOrderView extends OrderView {
  @Override
  public void afterInit() {
    super.afterInit();
    SdkHelper.create(FreeDeliveryModalChunk.class).dismissFreeDelivery();
  }

  @Override
  public <T extends BaseComponent> T checkoutAtom(Class<T> clazz) {
    logger.info("Tap on Cart button");
    SdkHelper.getDeviceControl().tapElementCenter(getCartButton);
    return SdkHelper.create(clazz);
  }

  @Override
  public CheckoutView checkout() {
    logger.info("Tap on Cart button");
    getCartButton.click();
    // for some reasons iOS is getting stuck here for couple of secs
    logger.info("10 sec wait for checkout on iOS");
    SdkHelper.getSyncHelper().sleep(10000);
    return SdkHelper.create(CheckoutView.class);
  }
}
