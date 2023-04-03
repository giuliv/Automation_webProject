package com.applause.auto.web.components.plp;

import com.applause.auto.common.data.Constants.SortType;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.components.QuickViewComponent;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.ProductDetailsPage;
import io.qameta.allure.Step;

@Implementation(is = PlpItemComponent.class, on = Platform.WEB)
@Implementation(is = MobilePlpItemComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class PlpItemComponent extends BaseComponent {

  @Locate(xpath = ".//a[contains(@href, '/products')]/div[1]", on = Platform.WEB)
  private Button clickElement;

  @Locate(xpath = ".//h3/a", on = Platform.WEB)
  private Text productName;

  // locate product name onboarding
  @Locate(xpath = ".//h3[contains(@class, 'pi__title')]", on = Platform.WEB)
  private Text productName2;

  @Locate(xpath = ".//p[contains(@class, 'price')]", on = Platform.WEB)
  private Text productPrice;

  @Locate(
      xpath = ".//p[contains(@class, 'price')]/span[contains(@class,'pi__price--sale')]",
      on = Platform.WEB)
  private Text productSalePrice;

  @Locate(
      xpath = ".//p[contains(@class, 'price')]/span[contains(@class,'pi__price--compare')]",
      on = Platform.WEB)
  private Text productPreSalePrice;

  @Locate(xpath = ".//div[@class='pi__img-default']/div[1]/img", on = Platform.WEB)
  private Image productImage;

  @Locate(xpath = ".//p[@class='pi__flavor-notes']", on = Platform.WEB)
  private Text productDescription;

  @Locate(xpath = ".//div[@class='pi__img-default']/div[1]/img", on = Platform.WEB)
  private Text outOfScopeText;

  @Locate(xpath = ".//a[normalize-space()='View Product']", on = Platform.WEB)
  protected Button viewProductButton;

  @Locate(xpath = ".//div[@class='pi__quick-add']/button", on = Platform.WEB)
  protected Button quickViewButton;

  @Locate(xpath = ".//div[@class='pi__quick-add']/button", on = Platform.WEB)
  protected Button addToCartButton;

  @Step("Click On Product")
  public ProductDetailsPage clickOnProduct() {
    WebHelper.scrollToElement(clickElement);

    SdkHelper.getSyncHelper().wait(Until.uiElement(clickElement).visible());
    WebHelper.scrollToElement(clickElement);

    if (WebHelper.isSafari() && !WebHelper.isMobile()) {
      logger.info("Safari Desktop");
      WebHelper.jsClick(clickElement.getWebElement());
    } else {
      clickElement.click();
    }

    return SdkHelper.create(ProductDetailsPage.class);
  }

  @Step("Get Product Name")
  public String getProductName() {
    String name = WebHelper.cleanString(productName.getText());
    logger.info("Product name [{}]", name);
    return name;
  }

  // onboarding
  @Step("Get product name")
  public String getProductName2() {
    String name = WebHelper.cleanString(productName2.getText());
    logger.info("Product name [{}]", name);
    return name;
  }

  @Step("Get Product Price")
  public String getProductPrice() {
    String price;
    if (WebHelper.isDisplayed(productSalePrice)) {
      price = WebHelper.cleanString(productSalePrice.getText());
    } else {
      price = WebHelper.cleanString(productPrice.getText());
    }
    logger.info("Product price [{}]", price);
    return price;
  }

  @Step("Get Product double Price")
  public Double getProductDoublePrice(SortType sortType) {
    String price;
    double doublePrice;
    if (WebHelper.isDisplayed(productSalePrice)) {
      price = WebHelper.cleanString(productSalePrice.getText());
    } else if (WebHelper.isDisplayed(productPrice)) {
      price = WebHelper.cleanString(productPrice.getText());

      // For such prices: $26.97 - $107.88
      //      if (price.contains("-") && sortType.equals(SortType.PRICE_HIGH_TO_LOW)) {
      //        price = price.split("-")[0].trim();
      //      } else if (price.contains("-") && sortType.equals(SortType.PRICE_LOW_TO_HIGH)) {
      //        price = price.split("-")[1].trim();
      //      }

      if (price.contains("-")) {
        price = null;
      }

    } else {
      price = "0";
    }
    if (price != null) {
      doublePrice =
          Double.parseDouble(price.split(" ")[0].replace(",", ".").replace("$", "").trim());
    } else {
      doublePrice = -1;
    }

    logger.info("Product price [{}]", doublePrice);
    return doublePrice;
  }

  @Step("Get Product double Price")
  public Double getProductDoublePrice() {
    String price = WebHelper.cleanString(productSalePrice.getText());
    double doublePrice =
        Double.parseDouble(price.split(" ")[0].replace(",", ".").replace("$", "").trim());

    logger.info("Product price [{}]", doublePrice);
    return doublePrice;
  }

  @Step("Verify product image is displayed")
  public boolean isImageDisplayed() {
    WebHelper.scrollToElement(productImage);
    SdkHelper.getSyncHelper().sleep(300);
    return WebHelper.isDisplayed(productImage);
  }

  @Step("Verify product name is displayed")
  public boolean isNameDisplayed() {
    return WebHelper.isDisplayed(productName);
  }

  // onboarding
  @Step("Verify product name is displayed")
  public boolean isNameDisplayed2() {
    return WebHelper.isDisplayed(productName2);
  }

  public Button getClickElement() {
    return clickElement;
  }

  @Step("Verify product price is displayed")
  public boolean isPriceDisplayed() {
    return WebHelper.isDisplayed(productPrice);
  }

  @Step("Verify product description is displayed")
  public boolean isDescriptionDisplayed() {
    return WebHelper.isDisplayed(productDescription);
  }

  @Step("Verify item is out of scope")
  public boolean isOutOfScope() {
    WebHelper.scrollToElement(productName);
    return WebHelper.isDisplayed(outOfScopeText);
  }

  @Step("Verify 'View product' button is displayed")
  public boolean isViewProductButtonDisplayed() {
    logger.info(
        "Checking 'View product' button is displayed for the product [{}]", getProductName());
    if (!viewProductButton.exists()) {
      return false;
    }

    WebHelper.hoverByAction(viewProductButton);
    return WebHelper.isDisplayed(viewProductButton);
  }

  @Step("Click on the View product button")
  public ProductDetailsPage clickViewProduct() {
    logger.info("Clicking on the 'View product' button");
    WebHelper.hoverByAction(viewProductButton);
    SdkHelper.getSyncHelper().sleep(500);
    viewProductButton.click();
    return SdkHelper.create(ProductDetailsPage.class);
  }

  @Step("Verify 'Quick view' button is displayed")
  public boolean isQuickViewButtonDisplayed() {
    logger.info("Checking 'Quick view' button is displayed for the product [{}]", getProductName());
    if (!quickViewButton.exists()) {
      return false;
    }

    WebHelper.scrollToElement(quickViewButton);
    WebHelper.hoverByAction(quickViewButton);
    return WebHelper.isDisplayed(quickViewButton);
  }

  @Step("Click on the Quick view button")
  public QuickViewComponent clickQuickView() {
    logger.info("Clicking on the 'Quick view' button");
    WebHelper.hoverByAction(quickViewButton);
    SdkHelper.getSyncHelper().sleep(500);
    quickViewButton.click();
    return SdkHelper.create(QuickViewComponent.class);
  }

  @Step("Return product description")
  public String getProductDescription() {
    String description = productDescription.getText().trim();
    logger.info("Product description: {}", description);
    return description;
  }

  @Step("Verify 'Add to cart' button is displayed")
  public boolean isAddToCartButtonDisplayed() {
    logger.info(
        "Checking 'Add to cart' button is displayed for the product [{}]", getProductName());
    WebHelper.scrollToElement(addToCartButton);
    WebHelper.hoverByAction(addToCartButton);
    return WebHelper.isDisplayed(addToCartButton);
  }

  @Step("Click on the Add to cart button")
  public QuickViewComponent clickAddToCart() {
    logger.info("Clicking on the 'Add to cart' button");
    WebHelper.hoverByAction(addToCartButton);
    SdkHelper.getSyncHelper().sleep(500);
    addToCartButton.click();
    return SdkHelper.create(QuickViewComponent.class);
  }

  @Step("Verify 'Quick view' button exist")
  public boolean isQuickViewButtonExist() {
    return quickViewButton.exists();
  }

  @Step("Verify 'View Product' button exist")
  public boolean isViewProductButtonExist() {
    return viewProductButton.exists();
  }

  @Step("GEt presales price value in double")
  public double getPreSalePrice() {
    if (!WebHelper.isDisplayed(productPreSalePrice)) {
      throw new RuntimeException(
          "Presale price is not displayed for the product: " + getProductName());
    }

    double presalePrice =
        Double.parseDouble(productPreSalePrice.getText().replaceAll("[^\\d.]", "").trim());

    logger.info("Presale price [{}] for the produce [{}]", presalePrice, getProductName());
    return presalePrice;
  }
}

class MobilePlpItemComponent extends PlpItemComponent {

  @Override
  @Step("Verify 'Quick view' button is displayed")
  public boolean isQuickViewButtonDisplayed() {
    logger.info("Checking 'Quick view' button is displayed for the product [{}]", getProductName());
    WebHelper.scrollToElement(quickViewButton);
    return WebHelper.isDisplayed(quickViewButton);
  }

  @Override
  @Step("Click on the Quick view button")
  public QuickViewComponent clickQuickView() {
    logger.info("Clicking on the 'Quick view' button");
    WebHelper.jsClick(quickViewButton.getWebElement());
    return SdkHelper.create(QuickViewComponent.class);
  }

  @Override
  @Step("Verify 'View product' button is displayed")
  public boolean isViewProductButtonDisplayed() {
    logger.info(
        "Checking 'View product' button is displayed for the product [{}]", getProductName());
    return WebHelper.isDisplayed(viewProductButton);
  }

  @Override
  @Step("Click on the View product button")
  public ProductDetailsPage clickViewProduct() {
    logger.info("Clicking on the 'View product' button");
    WebHelper.scrollToElement(viewProductButton);
    viewProductButton.click();
    return SdkHelper.create(ProductDetailsPage.class);
  }

  @Override
  @Step("Verify 'Add to cart' button is displayed")
  public boolean isAddToCartButtonDisplayed() {
    logger.info(
        "Checking 'Add to cart' button is displayed for the product [{}]", getProductName());
    WebHelper.scrollToElement(addToCartButton);
    return WebHelper.isDisplayed(addToCartButton);
  }

  @Override
  @Step("Click on the Add to cart button")
  public QuickViewComponent clickAddToCart() {
    logger.info("Clicking on the 'Add to cart' button");
    WebHelper.jsClick(addToCartButton.getWebElement());
    return SdkHelper.create(QuickViewComponent.class);
  }
}
