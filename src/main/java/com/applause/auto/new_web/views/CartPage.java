package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import java.time.Duration;
import java.util.List;

@Implementation(is = CartPage.class, on = Platform.WEB)
@Implementation(is = CartPage.class, on = Platform.WEB_MOBILE_PHONE)
public class CartPage extends BaseComponent {

  @Locate(id = "your-shopping-cart", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = "h4.bag-item__title", on = Platform.WEB)
  private List<Text> productName;

  @Locate(css = "span.bag-item__option-name", on = Platform.WEB)
  private List<Text> grindSelected;

  @Locate(css = "input[name='quantity']", on = Platform.WEB)
  private List<Text> productQuantity;

  @Locate(css = "a[href*='checkout']", on = Platform.WEB)
  private Button checkOutButton;

  @Locate(id = "bagContinue", on = Platform.WEB)
  private Button closeButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
  }

  /* -------- Actions -------- */

  public String getProductNameByIndex(int index) {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(productName.get(index)).visible().setTimeout(Duration.ofSeconds(30)));
    logger.info("[Cart] Product Name: " + productName.get(index).getText().toLowerCase().trim());

    return productName.get(index).getText().toLowerCase().trim();
  }

  public String getGrindSelectedByIndex(int index) {
    logger.info("Grind Size elements: " + grindSelected.size());
    if (grindSelected.size() <= 1) {
      index = 0;
    }
    SdkHelper.getSyncHelper().wait(Until.uiElement(grindSelected.get(index)).visible());
    logger.info(
        "[Cart] Grind Selected: " + grindSelected.get(index).getText().toLowerCase().trim());

    return grindSelected.get(index).getText().toLowerCase().trim();
  }

  public int getProductQuantityByIndex(int index) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productQuantity.get(index)).present());
    logger.info(
        "[Cart] Product Quantity: " + productQuantity.get(index).getAttributeValue("value"));

    return Integer.parseInt(productQuantity.get(index).getAttributeValue("value"));
  }

  public CheckOutPage clickContinueToCheckOut() {
    logger.info("Clicking CheckOut");
    SdkHelper.getSyncHelper().wait(Until.uiElement(checkOutButton).visible());
    checkOutButton.click();

    return SdkHelper.create(CheckOutPage.class);
  }
}
