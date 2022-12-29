package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.ProductDetailsPage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

@Implementation(is = SearchBoxItemComponent.class, on = Platform.WEB)
@Implementation(is = SearchBoxItemComponentMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class SearchBoxItemComponent extends BaseComponent {

  @Locate(css = " h3", on = Platform.WEB)
  protected Text itemName;

  @Locate(css = " .search-item__price", on = Platform.WEB)
  protected Text itemPrice;

  @Locate(css = " img", on = Platform.WEB)
  protected Image itemImage;

  @Step("Verify autocomplete product contains image, name and price.")
  public boolean isAutocompleteResultDisplayedCorrectly() {
    if (!itemName.isDisplayed() | itemName.getText().isEmpty()) {
      logger.info("The item Name isn't displayed");
      return false;
    }

    if (!itemImage.exists()) {
      logger.info("The item Image isn't displayed for product [{}]", itemName.getText());
      return false;
    }

    if (!itemPrice.isDisplayed() | itemPrice.getText().isEmpty()) {
      logger.info("The item Price isn't displayed for product [{}]", itemName.getText());
      return false;
    }

    return true;
  }

  /**
   * Get product name
   *
   * @return String
   */
  public String getName() {
    return itemName.getText().trim();
  }

  /**
   * Get product price
   *
   * @return String
   */
  public String getPrice() {
    return itemPrice.getText().trim();
  }

  /**
   * Get product image png name
   *
   * @return String
   */
  public String getImagePngName() {
    return itemImage.getImageSrc().split("products/")[1].split(".png")[0];
  }

  /**
   * Click on item to open details page
   *
   * @return ProductDetailsPage
   */
  public ProductDetailsPage clickOnItem() {
    logger.info("Clicking on the product with name [{}]", getName());
    if (WebHelper.isMobile()) {
      WebHelper.hideKeyboard();
    }

    itemImage.click();

    return SdkHelper.create(ProductDetailsPage.class);
  }
}

class SearchBoxItemComponentMobile extends SearchBoxItemComponent {

  @Step("Verify autocomplete product contains image, name and price.")
  public boolean isAutocompleteResultDisplayedCorrectly() {
    if (!WebHelper.isTextDisplayedAndNotEmpty(itemName)) {
      logger.info("The item Name isn't displayed");
      return false;
    }

    if (!itemImage.exists()) {
      logger.info("The item Image isn't displayed for product [{}]", itemName.getText());
      return false;
    }

    if (!WebHelper.isTextDisplayedAndNotEmpty(itemPrice)) {
      logger.info("The item Price isn't displayed for product [{}]", itemName.getText());
      return false;
    }

    return true;
  }
}
