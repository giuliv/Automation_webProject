package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.control.DeviceControl;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.util.helper.sync.Until;

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

  /* -------- Actions -------- */

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
    DeviceControl.tapElementCenter(getCategoryItem);
    SyncHelper.sleep(1000);
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
    DeviceControl.tapElementCenter(getCategoryItem);
    return ComponentFactory.create(ProductDetailsView.class);
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
    return ComponentFactory.create(SearchResultsView.class);
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
      SyncHelper.wait(
          Until.uiElement(getConfirmStoreButton).present().setTimeout(Duration.ofSeconds(5)));
      getConfirmStoreButton.click();
    } catch (Exception e) {
      logger.info("Confirmation button is not present");
    }
    return ComponentFactory.create(CheckoutView.class);
  }
}

class IosNewOrderView extends NewOrderView {

  public CheckoutView checkout() {
    logger.info("Tap on Cart button");
    getCartButton.click();
    // for some reasons iOS is getting stuck here for couple of secs
    logger.info("10 sec wait for checkout on iOS");
    SyncHelper.sleep(10000);
    return ComponentFactory.create(CheckoutView.class);
  }
}
