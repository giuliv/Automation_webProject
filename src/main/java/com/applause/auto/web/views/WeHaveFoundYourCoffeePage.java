package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = WeHaveFoundYourCoffeePage.class, on = Platform.WEB)
@Implementation(is = WeHaveFoundYourCoffeePage.class, on = Platform.WEB_MOBILE_PHONE)
public class WeHaveFoundYourCoffeePage extends Base {

  @Locate(css = ".coffee-finder-embed", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(xpath = "//div[@data-name='Results']//div[contains(@class, 'title')]", on = Platform.WEB)
  private Text title;

  @Locate(xpath = "//picture[contains(@class, 'image-product')]", on = Platform.WEB)
  private Image productImage;

  @Locate(
      xpath = "//div[contains(@class, 'product') and contains(@class, 'title')]",
      on = Platform.WEB)
  private Text productName;

  @Locate(
      xpath =
          "//div[contains(@class, 'column-information')]//div[contains(@class, 'item') and contains(@class, 'price')]//div[contains(@class, 'item') and contains(@class, 'price')]",
      on = Platform.WEB)
  private Text productPrice;

  @Locate(
      xpath = "//div[contains(@class, 'add_to_cart')]/div[contains(@class, 'add_to_cart')]",
      on = Platform.WEB)
  private Button addToCartButton;

  @Locate(
      xpath =
          "//div[contains(@class, 'product-details')]/div[contains(@class, 'product-details')][1]",
      on = Platform.WEB)
  private Button productDetailsButton;

  @Locate(
      xpath = "//div[contains(@class, 'shop-all')]/div[contains(@class, 'shop-all')][1]",
      on = Platform.WEB)
  private Button shopAllCoffeeButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainContainer).visible().setTimeout(Duration.ofSeconds(40)));
  }

  @Step("Get Page Title")
  public String getTitle() {
    String text = WebHelper.cleanString(title.getText());
    logger.info("Title - [{}]", text);
    return text;
  }

  @Step("Check if Product Image is Displayed")
  public boolean isProductImageDisplayed() {
    return WebHelper.isDisplayed(productImage);
  }

  @Step("Check if Product Name is Displayed")
  public boolean isProductNameDisplayed() {
    return WebHelper.isTextDisplayedAndNotEmpty(productName);
  }

  @Step("Check if Product Price is Displayed")
  public boolean isProductPriceDisplayed() {
    return WebHelper.isTextDisplayedAndNotEmpty(productPrice);
  }

  @Step("Check if 'Add To cart' is Displayed")
  public boolean isAddToCartDisplayed() {
    return WebHelper.isDisplayed(addToCartButton);
  }

  @Step("Check if 'Product Details' is Displayed")
  public boolean isProductDetailsDisplayed() {
    return WebHelper.isDisplayed(productDetailsButton);
  }

  @Step("Check if 'Shop all Coffee' is Displayed")
  public boolean isShopAllCoffeeDisplayed() {
    return WebHelper.isDisplayed(shopAllCoffeeButton);
  }
}
