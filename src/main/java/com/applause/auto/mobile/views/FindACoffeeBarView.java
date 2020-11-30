package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.components.CoffeeStoreContainerChuck;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;

@Implementation(is = FindACoffeeBarView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = FindACoffeeBarView.class, on = Platform.MOBILE_IOS)
public class FindACoffeeBarView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(iOSClassChain = "**/*[`label == 'Order'`]", on = Platform.MOBILE_IOS)
  protected TextBox orderButton;

  /* -------- Actions -------- */

  /**
   * Open new Order view.
   *
   * @return new order view
   */
  public NewOrderView selectFirstRecentCoffeeBar() {
    logger.info("Select First Recent Coffee Bar");
    orderButton.click();
    return this.create(NewOrderView.class);
  }

  /**
   * Gets coffee store container chuck.
   *
   * @return the coffee store container chuck
   */
  public CoffeeStoreContainerChuck getCoffeeStoreContainerChuck() {
    return this.create(CoffeeStoreContainerChuck.class);
  }
}
