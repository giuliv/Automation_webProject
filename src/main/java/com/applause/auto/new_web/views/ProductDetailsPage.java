package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.helpers.sync.Until;

@Implementation(is = ProductDetailsPage.class, on = Platform.WEB)
@Implementation(is = ProductDetailsPage.class, on = Platform.WEB_MOBILE_PHONE)
public class ProductDetailsPage extends Base {

  @Locate(id = "pvEssentials", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = "h1.pv-title", on = Platform.WEB)
  private Text productName;

  @Locate(css = "select#grind + div", on = Platform.WEB)
  private Text grindSelected;

  @Locate(
      css = "button[id*='productViewQuantityButton'].is-selected,#quantityText",
      on = Platform.WEB)
  private Text productQuantity;

  @Locate(id = "quantityText", on = Platform.WEB)
  private Text productQuantityBox;

  @Locate(id = "btnAddToBag", on = Platform.WEB)
  private Button addToCartButton;

  @Locate(id = "productViewQuantityButton3", on = Platform.WEB)
  private Button moreThanThreeProducts;

  @Locate(css = "button.plus", on = Platform.WEB)
  private Button addOneMore;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
    logger.info("Product Details Page URL: " + SdkHelper.getDriver().getCurrentUrl());
  }

  /* -------- Actions -------- */

  public String getProductName() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productName).visible());
    logger.info("[PDP] Product Name: " + productName.getText());

    return productName.getText().toLowerCase();
  }

  public String getGrindSelected() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(grindSelected).visible());
    logger.info("[PDP] Grind Selected: " + grindSelected.getText());

    return grindSelected.getText().toLowerCase();
  }

  public int getProductQuantity() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productQuantity).visible());

    String quantity = "";
    if (productQuantity.getAttributeValue("data-quantity") != null) {
      quantity = productQuantity.getAttributeValue("data-quantity");
    } else {
      quantity = productQuantity.getText();
    }

    logger.info("[PDP] Product Quantity: " + quantity);
    return Integer.parseInt(quantity);
  }

  public int getProductQuantityFromBox() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productQuantityBox).visible());

    logger.info("[PDP] Product Quantity: " + productQuantityBox.getText());
    return Integer.parseInt(productQuantityBox.getText());
  }

  public MiniCart clickAddToMiniCart() {
    logger.info("Adding to MiniCart");
    SdkHelper.getSyncHelper().wait(Until.uiElement(addToCartButton).visible());
    addToCartButton.click();

    return SdkHelper.create(MiniCart.class);
  }

  public CartPage clickAddToCartPage() {
    logger.info("Adding to Cart");
    SdkHelper.getSyncHelper().wait(Until.uiElement(addToCartButton).visible());
    addToCartButton.click();

    return SdkHelper.create(CartPage.class);
  }

  public void addMoreProducts(int totalProducts) {
    logger.info("[PDP] Total products: " + totalProducts);

    if (totalProducts > 3) {
      logger.info("Adding more than 3 products, clicking 3+ button");
      moreThanThreeProducts.click();

      SdkHelper.getSyncHelper().wait(Until.uiElement(addOneMore).visible());
      for (int i = 3; i < totalProducts; i++) {
        addOneMore.click();
        SdkHelper.getSyncHelper().sleep(500); // Wait for action
      }
    } else if (totalProducts > 1) {
      logger.info("Adding 1 more product");
      addOneMore.click();
      SdkHelper.getSyncHelper().sleep(500); // Wait for action
    }
  }
}
