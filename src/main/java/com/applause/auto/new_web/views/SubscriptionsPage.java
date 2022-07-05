package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;

public class SubscriptionsPage extends BaseComponent {

  @Locate(id = "subscriptions", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Override
  public void afterInit() {
    super.afterInit();
    WebHelper.waitForElementToAppear(mainContainer, 30);
  }
}
