package com.applause.auto.new_web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.views.CheckOutPage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.helpers.sync.Until;

@Implementation(is = MiniCart.class, on = Platform.WEB)
@Implementation(is = MiniCart.class, on = Platform.WEB_MOBILE_PHONE)
public class MiniCart extends BaseComponent {

  @Locate(id = "bagForm", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = "h4.bag-item__title", on = Platform.WEB)
  private Text productName;

  @Locate(css = "span.bag-item__option-name ", on = Platform.WEB)
  private Text grindSelected;

  @Locate(css = ".bag-item__action--qty input[name='quantity']", on = Platform.WEB)
  private Text productQuantity;

  @Locate(css = "a[href*='checkout']", on = Platform.WEB)
  private Button checkOutButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
  }

  /* -------- Actions -------- */

  public String getProductName() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productName).visible());
    logger.info("[MiniCart] Product Name: " + productName.getText());

    return productName.getText().toLowerCase();
  }

  public String getGrindSelected() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(grindSelected).visible());
    logger.info("[MiniCart] Grind Selected: " + grindSelected.getText());

    return grindSelected.getText().toLowerCase();
  }

  public int getProductQuantity() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productQuantity).present());
    logger.info("[MiniCart] Product Quantity: " + productQuantity.getAttributeValue("value"));

    return Integer.parseInt(productQuantity.getAttributeValue("value"));
  }

  public CheckOutPage clickContinueToCheckOut() {
    logger.info("Clicking CheckOut");
    SdkHelper.getSyncHelper().wait(Until.uiElement(checkOutButton).visible());
    checkOutButton.click();

    return SdkHelper.create(CheckOutPage.class);
  }
}
