package com.applause.auto.new_web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

@Implementation(is = SearchBoxItemComponent.class, on = Platform.WEB)
public class SearchBoxItemComponent extends BaseComponent {

  @Locate(css = " h3", on = Platform.WEB)
  private Text itemName;

  @Locate(css = " .search-item__price", on = Platform.WEB)
  private Text itemPrice;

  @Locate(css = " img", on = Platform.WEB)
  private Image itemImage;

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
    itemImage.click();
    return SdkHelper.create(ProductDetailsPage.class);
  }
}
