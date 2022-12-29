package com.applause.auto.web.views.my_account;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.web.components.my_account.MyOrderItemComponent;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Implementation(is = OrderHistoryPage.class, on = Platform.WEB)
@Implementation(is = OrderHistoryPage.class, on = Platform.WEB_MOBILE_PHONE)
public class OrderHistoryPage extends BaseComponent {

  @Locate(
      xpath = "//div[contains(@class, 'orders') and contains(@class, 'returns')]",
      on = Platform.WEB)
  private Text baseElement;

  @Locate(
      xpath = "//div[contains(@class, 'table') and contains(@class, 'header')]/h3",
      on = Platform.WEB)
  private List<Text> tableTitleList;

  @Locate(xpath = "//ul/li[@class='ac-table-row']", on = Platform.WEB)
  private List<MyOrderItemComponent> myOrderItemList;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(baseElement).visible().setTimeout(Duration.ofSeconds(40)));
    logger.info("Order History page URL: " + getDriver().getCurrentUrl());
  }

  @Step("Check if 'Order History' is Displayed")
  public boolean isDisplayed() {
    return WebHelper.isDisplayed(baseElement);
  }

  @Step("Get list of Table Titles")
  public List<String> getTableTitleList() {
    ((LazyList<?>) tableTitleList).initialize();
    return tableTitleList
        .stream()
        .map(item -> WebHelper.cleanString(item.getText().toLowerCase()))
        .collect(Collectors.toList());
  }

  @Step("Check if 'My Orders' Table is Fully Displayed")
  public boolean isMyOrdersTableFullyDisplayed() {
    return getMyOrderItemList().stream().allMatch(item -> item.isDisplayedCorrectly());
  }

  @Step("Get My Order Item List")
  public List<MyOrderItemComponent> getMyOrderItemList() {
    ((LazyList<?>) myOrderItemList).initialize();
    return myOrderItemList;
  }
}
