package com.applause.auto.web.views.my_account;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.qameta.allure.Step;

@Implementation(is = MyAccountOrderHistoryPage.class, on = Platform.WEB)
public class MyAccountOrderHistoryPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = ".ac-orders", on = Platform.WEB)
  private TextBox mainContainer;

  @Locate(css = "ul.ac-table__list > li", on = Platform.WEB)
  private ContainerElement orderHistoryItem;

  /* -------- Actions -------- */

  @Override
  public void afterInit() {
    super.afterInit();
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
  }

  @Step("Verify list of orders is displayed")
  public boolean orderHistoryItemIsDisplayed() {
    return orderHistoryItem.isDisplayed();
  }
}
