package com.applause.auto.new_web.views;

import com.applause.auto.common.data.enums.Attribute;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.List;

@Implementation(is = ProductDetailsPage.class, on = Platform.WEB)
@Implementation(is = ProductDetailsPageMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class ProductDetailsPage extends Base {

  @Locate(id = "pvEssentials", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = ".pv-title", on = Platform.WEB)
  private Text productName;

  @Locate(css = "select#grind + div", on = Platform.WEB)
  private Text grindSelected;

  @Locate(css = "select#grind option", on = Platform.WEB)
  private List<Text> grindListSelected;

  @Locate(
      css = "button[id*='productViewQuantityButton'].is-selected,#quantityText",
      on = Platform.WEB)
  private Text productQuantity;

  @Locate(id = "quantityText", on = Platform.WEB)
  private Text productQuantityBox;

  @Locate(id = "btnAddToBag", on = Platform.WEB)
  private Button addToCartButton;

  @Locate(id = "productViewQuantityButton3", on = Platform.WEB)
  protected Button threeProductsButton;

  @Locate(id = "productViewQuantityButton2", on = Platform.WEB)
  private Button twoProductsButton;

  @Locate(css = "button.plus", on = Platform.WEB)
  private Button addOneMore;

  @Locate(css = "[test='regularEligible'] button.og-optin-btn", on = Platform.WEB)
  private Button subscribeType;

  @Locate(css = "form#notifyForm", on = Platform.WEB)
  private ContainerElement outOfStockNotifyMeSection;

  @Locate(css = "#pvEssentials #productPrice .pv-price__original", on = Platform.WEB)
  private Text itemPrice;

  @Locate(css = "#photos img", on = Platform.WEB)
  private Image itemImage;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainContainer).present().setTimeout(Duration.ofSeconds(40)));
    logger.info("Product Details Page URL: " + SdkHelper.getDriver().getCurrentUrl());
  }

  /* -------- Actions -------- */

  @Step("Get product name")
  public String getProductName() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productName).visible());
    logger.info("[PDP] Product Name: " + productName.getText().toLowerCase().trim());

    return productName.getText().toLowerCase().trim();
  }

  @Step("Get item is available")
  public boolean isItemAvailable() {
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    return outOfStockNotifyMeSection.isDisplayed();
  }

  @Step("Get grind selected")
  public String getGrindSelected() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(grindSelected).visible());
    logger.info("[PDP] Grind Selected: " + grindSelected.getText().toLowerCase().trim());

    if (WebHelper.isSafari()) {
      String text =
          grindListSelected.stream()
              .filter(x -> x.getWebElement().isSelected())
              .findFirst()
              .get()
              .getText()
              .toLowerCase()
              .trim();

      logger.info("[PDP] Grind Selected: " + text);
      return text;
    }

    return grindSelected.getText().toLowerCase().trim();
  }

  @Step("Get product price")
  public String getProductPrice() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(itemPrice).visible());
    logger.info("[PDP] Product price: " + itemPrice.getText().trim());

    return itemPrice.getText().trim();
  }

  @Step("Get product quantity")
  public int getProductQuantitySelected() {
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

  @Step("Get product quantity from box")
  public int getProductQuantityFromBox() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productQuantityBox).visible());

    logger.info("[PDP] Product Quantity: " + productQuantityBox.getText());
    return Integer.parseInt(productQuantityBox.getText());
  }

  @Step("Select subscribe")
  public void selectSubscribeType() {
    logger.info("Selecting Subscribe Order");
    SdkHelper.getSyncHelper().wait(Until.uiElement(subscribeType).visible());
    subscribeType.click();
  }

  @Step("Click Add to minicart")
  public MiniCart clickAddToMiniCart() {
    logger.info("Adding to MiniCart");
    SdkHelper.getSyncHelper().wait(Until.uiElement(addToCartButton).clickable());
    if (!WebHelper.isDesktop()) {
      WebHelper.scrollToElement(addToCartButton);
      SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    }

    addToCartButton.click();
    return SdkHelper.create(MiniCart.class);
  }

  @Step("Click Add to cart")
  public CartPage clickAddToCartPage() {
    logger.info("Adding to Cart");
    SdkHelper.getSyncHelper().wait(Until.uiElement(addToCartButton).clickable());
    if (!WebHelper.isDesktop()) {
      WebHelper.scrollToElement(addToCartButton);
      SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    }

    addToCartButton.click();
    return SdkHelper.create(CartPage.class);
  }

  @Step("Click Add more products")
  public void addMoreProducts(int totalProducts) {
    logger.info("[PDP] Total products: " + totalProducts);

    if (totalProducts > 3) {
      logger.info("Adding more than 3 products, clicking 3+ button");
      clickOnThreeProductsButton();

      SdkHelper.getSyncHelper().wait(Until.uiElement(addOneMore).present());
      if (!WebHelper.isDesktop()) {
        WebHelper.scrollToElement(addOneMore);
        SdkHelper.getSyncHelper().sleep(1000); // Wait for action
      }

      for (int i = 3; i < totalProducts; i++) {
        addOneMore.click();
        SdkHelper.getSyncHelper().sleep(1000); // Wait for action
      }
    } else if (totalProducts == 2) {
      if (twoProductsButton.exists()) {
        logger.info("Selecting 2 products button");
        SdkHelper.getSyncHelper().wait(Until.uiElement(twoProductsButton).clickable());
        twoProductsButton.click();
      } else {
        SdkHelper.getSyncHelper().wait(Until.uiElement(addOneMore).present());
        if (!WebHelper.isDesktop()) {
          WebHelper.scrollToElement(addOneMore);
          SdkHelper.getSyncHelper().sleep(1000); // Wait for action
        }

        logger.info("Adding 1 more product");
        addOneMore.click();
      }
    } else if (totalProducts == 3) {
      logger.info("Selecting 3 products button");
      SdkHelper.getSyncHelper().wait(Until.uiElement(threeProductsButton).clickable());
      WebHelper.jsClick(threeProductsButton.getWebElement());
    }
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action
  }

  @Step("Verify expected image is displayed")
  public boolean isExpectedImageDisplayed(String imageSrc) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(itemImage).visible());
    String imageSrcset = itemImage.getAttributeValue(Attribute.SRCSET.getValue());
    if (!imageSrcset.contains(imageSrc)) {
      logger.info(
          "Wrong image is displayed. Expected src - [{}]. Actual src - [{}]",
          imageSrc,
          imageSrcset);
      return false;
    }

    return true;
  }

  protected void clickOnThreeProductsButton() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(threeProductsButton).clickable());
    threeProductsButton.click();
  }
}

class ProductDetailsPageMobile extends ProductDetailsPage {

  @Override
  protected void clickOnThreeProductsButton() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(threeProductsButton).clickable());
    WebHelper.clickOnElementAndScrollUpIfNeeded(threeProductsButton, -90);
  }
}
