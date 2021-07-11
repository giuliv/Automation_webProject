package com.applause.auto.new_web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.CheckOutPage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.helpers.sync.Until;

import java.util.List;

@Implementation(is = MiniCart.class, on = Platform.WEB)
@Implementation(is = MiniCart.class, on = Platform.WEB_MOBILE_PHONE)
public class MiniCart extends BaseComponent {

  @Locate(id = "bagForm", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = "h4.bag-item__title", on = Platform.WEB)
  private List<Text> productName;

  @Locate(css = "span.bag-item__option-name ", on = Platform.WEB)
  private Text grindSelected;

  @Locate(css = ".bag-item__action--qty input[name='quantity']", on = Platform.WEB)
  private List<Text> productQuantity;

  @Locate(css = "a[href*='checkout']", on = Platform.WEB)
  private Button checkOutButton;

  @Locate(id = "bagContinue", on = Platform.WEB)
  private Button closeButton;

  @Locate(css = "button[data-action='add']", on = Platform.WEB)
  private Button addButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
  }

  /* -------- Actions -------- */

  public String getProductNameByIndex(int index) {
    if (WebHelper.isSafari()) {
      SdkHelper.getSyncHelper().wait(Until.uiElement(productName.get(index)).present());
    } else {
      SdkHelper.getSyncHelper().wait(Until.uiElement(productName.get(index)).visible());
    }

    logger.info(
        "[MiniCart] Product Name: " + productName.get(index).getText().toLowerCase().trim());

    return productName.get(index).getText().toLowerCase().trim();
  }

  public String getGrindSelected() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(grindSelected).visible());
    logger.info("[MiniCart] Grind Selected: " + grindSelected.getText().toLowerCase().trim());

    return grindSelected.getText().toLowerCase().trim();
  }

  public int getProductQuantityByIndex(int index) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productQuantity.get(index)).present());
    logger.info(
        "[MiniCart] Product Quantity: " + productQuantity.get(index).getAttributeValue("value"));

    return Integer.parseInt(productQuantity.get(index).getAttributeValue("value"));
  }

  public void addOneMoreItem() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(addButton).clickable());
    addButton.click();
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action
  }

  public CheckOutPage clickContinueToCheckOut() {
    logger.info("Clicking CheckOut");
    SdkHelper.getSyncHelper().wait(Until.uiElement(checkOutButton).visible());
    checkOutButton.click();

    return SdkHelper.create(CheckOutPage.class);
  }

  public <V extends BaseComponent> V closeMiniCart(Class<V> expectedClass) {
    logger.info("Closing miniCart");
    SdkHelper.getSyncHelper().wait(Until.uiElement(closeButton).visible());
    closeButton.click();

    return SdkHelper.create(expectedClass);
  }
}
