package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.helper.sync.Until;
import java.time.Duration;

@Implementation(is = NewOrderView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = IosNewOrderView.class, on = Platform.MOBILE_IOS)
public class NewOrderView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//XCUIElementTypeNavigationBar[@name=\"Order\"]", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.TextView[@text='ORDER']", on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  @Locate(xpath = "//XCUIElementTypeStaticText[@name=\"%s\"]", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.TextView[@text=\"%s\"]", on = Platform.MOBILE_ANDROID)
  protected ContainerElement getCategoryItem;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name=\"%s\"]/preceding-sibling::*[@name='%s']",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.TextView[@text=\"%s\"]//parent::*/following-sibling::*[contains(@resource-id, 'subcategories')]//*[contains(@text, '%s')]",
      on = Platform.MOBILE_ANDROID)
  protected ContainerElement getCategorySubItem;

  @Locate(id = "Menu", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/searchContainer",
      on = Platform.MOBILE_ANDROID)
  protected Button getSearchMagnifierButton;

  @Locate(xpath = "//XCUIElementTypeSearchField[@name=\"Search Menu\"]", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/search_src_text",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getSearchMenuEditField;

  @Locate(
      id = "com.wearehathway.peets.development:id/basketFABContainer",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      xpath =
          "//XCUIElementTypeOther[child:: XCUIElementTypeImage and XCUIElementTypeStaticText[@name>0]]",
      on = Platform.MOBILE_IOS)
  protected Button getCartButton;

  @Locate(id = "com.wearehathway.peets.development:id/confirmButton", on = Platform.MOBILE_ANDROID)
  @Locate(id = "Menu", on = Platform.MOBILE_IOS)
  protected Button getConfirmStoreButton;

  @Locate(id = "com.wearehathway.peets.development:id/confirmButton", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"Recents\"`]",
      on = Platform.MOBILE_IOS)
  protected Button recentsTabButton;

  @Locate(id = "com.wearehathway.peets.development:id/confirmButton", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"No Recent Orders\"`]",
      on = Platform.MOBILE_IOS)
  protected Button titleNoRecentOrdersText;

  @Locate(id = "com.wearehathway.peets.development:id/confirmButton", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"No Favorited Orders\"`]",
      on = Platform.MOBILE_IOS)
  protected Button titleNoFavoriteOrders;

  @Locate(id = "com.wearehathway.peets.development:id/confirmButton", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"Favorites\"`]",
      on = Platform.MOBILE_IOS)
  protected Button favoritesTabButton;

  @Locate(id = "com.wearehathway.peets.development:id/confirmButton", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"Start New Order\"`][1]",
      on = Platform.MOBILE_IOS)
  protected Button startNewOrderRecentsButton;

  @Locate(id = "com.wearehathway.peets.development:id/confirmButton", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"Start New Order\"`][2]",
      on = Platform.MOBILE_IOS)
  protected Button startNewOrderFavoritesButton;

  @Locate(id = "com.wearehathway.peets.development:id/confirmButton", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Menu\"`][2]",
      on = Platform.MOBILE_IOS)
  protected Text menuSignatureText;

  @Locate(id = "com.wearehathway.peets.development:id/confirmButton", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"%s\"`]",
      on = Platform.MOBILE_IOS)
  protected Text messageText;

  /* -------- Actions -------- */
  public void afterInit() {
    getSyncHelper()
        .wait(Until.uiElement(menuSignatureText).present().setTimeout(Duration.ofSeconds(15)));
  }

  /**
   * Get the text vaalue of the heading
   *
   * @return
   */
  public String getHeadingTextValue() {
    return getHeadingText.getText();
  }

  /**
   * Select category and subcategory.
   *
   * @param category the category
   * @param category the subCategory
   */
  public void selectCategoryAndSubCategory(String category, String subCategory) {
    logger.info("Select category: " + category);
    getCategoryItem.initializeWithFormat(category);
    getDeviceControl().tapElementCenter(getCategoryItem);
    getSyncHelper().sleep(2000);
    getCategorySubItem.initializeWithFormat(category, subCategory);
    getCategorySubItem.click();
  }

  /**
   * Select product product details view.
   *
   * @param category the category
   * @return the product details view
   */
  public ProductDetailsView selectProduct(String category) {
    logger.info("Select product: " + category);
    getCategoryItem.initializeWithFormat(category);
    getDeviceControl().tapElementCenter(getCategoryItem);
    return this.create(ProductDetailsView.class);
  }

  /**
   * Search search results view.
   *
   * @param searchItem the search item
   * @return the search results view
   */
  public SearchResultsView search(String searchItem) {
    logger.info("Searching for: " + searchItem);
    getSearchMagnifierButton.click();
    getSearchMenuEditField.sendKeys(searchItem);
    return this.create(SearchResultsView.class);
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
      getSyncHelper()
          .wait(Until.uiElement(getConfirmStoreButton).present().setTimeout(Duration.ofSeconds(5)));
      getConfirmStoreButton.click();
    } catch (Exception e) {
      logger.info("Confirmation button is not present");
    }
    return this.create(CheckoutView.class);
  }

  /**
   * Recents new order view.
   *
   * @return the new order view
   */
  public NewOrderView recents() {
    logger.info("Click on recents tab");
    recentsTabButton.click();
    return this.create(NewOrderView.class);
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
  public NewOrderView startNewOrderRecents() {
    logger.info("Click <start new order> button");
    startNewOrderRecentsButton.click();
    return this.create(NewOrderView.class);
  }

  /**
   * Start new order favorites new order view.
   *
   * @return the new order view
   */
  public NewOrderView startNewOrderFavorites() {
    logger.info("Click <start new order> button");
    startNewOrderFavoritesButton.click();
    return this.create(NewOrderView.class);
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
  public NewOrderView favorites() {
    logger.info("Click Favorites tab");
    favoritesTabButton.click();
    return this.create(NewOrderView.class);
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
}

class IosNewOrderView extends NewOrderView {

  public CheckoutView checkout() {
    logger.info("Tap on Cart button");
    getCartButton.click();
    // for some reasons iOS is getting stuck here for couple of secs
    logger.info("10 sec wait for checkout on iOS");
    getSyncHelper().sleep(10000);
    return this.create(CheckoutView.class);
  }
}
