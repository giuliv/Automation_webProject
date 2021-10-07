package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import java.time.Duration;
import java.util.stream.IntStream;
import org.openqa.selenium.NoSuchElementException;

@Implementation(is = NewOrderView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = IosNewOrderView.class, on = Platform.MOBILE_IOS)
public class NewOrderView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//XCUIElementTypeNavigationBar[@name=\"Order\"]", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.TextView[@text='ORDER']", on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  @Locate(iOSClassChain = "**/XCUIElementTypeButton[`label == \"%s\"`]", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.TextView[@text=\"%s\"]", on = Platform.MOBILE_ANDROID)
  protected ContainerElement getCategoryItem;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"%s\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.TextView[@text=\"%s\"]", on = Platform.MOBILE_ANDROID)
  protected ContainerElement getProductItem;

  @Locate(
      xpath =
          "//XCUIElementTypeButton[@name=\"%s\"]/following-sibling::XCUIElementTypeButton[@name='%s']",
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

  @Locate(
      id = "com.wearehathway.peets.development:id/changeLocationButton",
      on = Platform.MOBILE_ANDROID)
  protected Button getChangeStoreButton;

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

  @Locate(id = "com.wearehathway.peets.development:id/basketFABText", on = Platform.MOBILE_ANDROID)
  @Locate(
      xpath =
          "//XCUIElementTypeNavigationBar/following-sibling::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeStaticText",
      on = Platform.MOBILE_IOS)
  protected Text fabAmountText;

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"Seasonal Favorites\"]/preceding-sibling::XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell[@name=\"%s\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//androidx.appcompat.widget.LinearLayoutCompat[@resource-id='com.wearehathway.peets.development:id/seasonalProductContainer']/android.widget.TextView[@resource-id='com.wearehathway.peets.development:id/productNameTextView' and @text='%s']",
      on = Platform.MOBILE_ANDROID)
  protected Text seasonalFavoriteProductItemText;

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
    int attempt = 5;
    IntStream.range(0, attempt)
        .forEach(
            i -> {
              MobileHelper.scrollUpCloseToMiddleAlgorithm();
            });

    try {
      getCategoryItem.format(category).initialize();
    } catch (NoSuchElementException nse) {
    }
    SdkHelper.getSyncHelper().sleep(5000);
    MobileHelper.scrollElementIntoView(getCategoryItem);
    try {
      getCategorySubItem.format(category, subCategory).initialize();
    } catch (NoSuchElementException nse) {
      SdkHelper.getDeviceControl().tapElementCenter(getCategoryItem);
      SdkHelper.getSyncHelper().sleep(2000);
      getCategorySubItem.format(category, subCategory).initialize();
    }
    getCategorySubItem.click();
    SdkHelper.getSyncHelper().sleep(1000);
  }

  public void selectSubCategoryUnderCategory(String category, String subCategory) {
    logger.info("Select sub-category: " + subCategory);
    getCategorySubItem.format(category, subCategory);
    getCategorySubItem.click();
    SdkHelper.getSyncHelper().sleep(1000);
  }

  /**
   * Select product product details view.
   *
   * @param category the category
   * @return the product details view
   */
  public ProductDetailsView selectProduct(String category) {
    logger.info("Select product: " + category);
    int attempt = 5;
    getProductItem.format(category);
    try {
      getProductItem.initialize();
    } catch (NoSuchElementException nse) {
      IntStream.range(0, attempt)
          .forEach(
              i -> {
                MobileHelper.scrollUpCloseToMiddleAlgorithm();
              });
      SdkHelper.getSyncHelper().sleep(1000);
      while (attempt-- > 0 && !getProductItem.exists()) {
        MobileHelper.scrollDownCloseToMiddleAlgorithm();
        SdkHelper.getSyncHelper().sleep(1000);
      }
    }
    SdkHelper.getSyncHelper().sleep(1000);
    SdkHelper.getDeviceControl().tapElementCenter(getProductItem);
    return SdkHelper.create(ProductDetailsView.class);
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
    return SdkHelper.create(SearchResultsView.class);
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

  public NewOrderView checkoutAtom() {
    logger.info("Tap on Cart button");
    getCartButton.click();
    return this;
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

  public CheckoutView confirmStore() {
    getConfirmStoreButton.click();
    return SdkHelper.create(CheckoutView.class);
  }

  public boolean isConfirmStoreButtonDisplayed() {
    return getConfirmStoreButton.exists() && getConfirmStoreButton.isDisplayed();
  }

  public boolean isChangeStoreButtonDisplayed() {
    return getChangeStoreButton.exists() && getChangeStoreButton.isDisplayed();
  }

  /**
   * Recents new order view.
   *
   * @return the new order view
   */
  public NewOrderView recents() {
    logger.info("Click on recents tab");
    recentsTabButton.click();
    return SdkHelper.create(NewOrderView.class);
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
    return SdkHelper.create(NewOrderView.class);
  }

  /**
   * Start new order favorites new order view.
   *
   * @return the new order view
   */
  public NewOrderView startNewOrderFavorites() {
    logger.info("Click <start new order> button");
    startNewOrderFavoritesButton.click();
    return SdkHelper.create(NewOrderView.class);
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
    return SdkHelper.create(NewOrderView.class);
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

  public NewOrderView addToOrders() {
    addToOrderButton.click();
    SdkHelper.getSyncHelper().sleep(2000);
    return SdkHelper.create(NewOrderView.class);
  }

  public String getFabAmount() {
    String result = fabAmountText.getText();
    logger.info("Cart items amout: " + result);
    return result;
  }

  public ProductDetailsView selectSeasonalFavorites(String name) {
    IntStream.range(0, 5)
        .forEach(
            i -> {
              MobileHelper.scrollUpCloseToMiddleAlgorithm();
            });
    seasonalFavoriteProductItemText.format(name).click();
    return SdkHelper.create(ProductDetailsView.class);
  }
}

class IosNewOrderView extends NewOrderView {
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

  // @Override
  // public void selectCategoryAndSubCategory(String category, String subCategory) {
  // logger.info("Select category ios: " + category);
  // try {
  // getCategoryItem.format(category).initialize();
  // } catch (NoSuchElementException nse) {
  //
  // }
  // MobileHelper.scrollElementIntoView(getCategoryItem);
  // SdkHelper.getDeviceControl().tapElementCenter(getCategoryItem);
  // SdkHelper.getSyncHelper().sleep(2000);
  // getCategorySubItem.format(category, subCategory).initialize();
  // MobileHelper.scrollElementIntoView(getCategorySubItem);
  // SdkHelper.getDeviceControl().tapElementCenter(getCategorySubItem);
  // SdkHelper.getSyncHelper().sleep(1000);
  // }

  // @Override
  // public ProductDetailsView selectProduct(String category) {
  // logger.info("Select product ios: " + category);
  // try {
  // getProductItem.format(category).initialize();
  // } catch (NoSuchElementException nse) {
  //
  // }
  // MobileHelper.scrollElementIntoView(getProductItem);
  // SdkHelper.getSyncHelper().sleep(1000);
  // getProductItem.click();
  // return SdkHelper.create(ProductDetailsView.class);
  // }
}
