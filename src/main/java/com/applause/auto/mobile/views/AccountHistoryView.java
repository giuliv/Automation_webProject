package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;

import java.util.List;

@Implementation(is = AccountHistoryView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = AccountHistoryView.class, on = Platform.MOBILE_IOS)
public class AccountHistoryView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath = "//XCUIElementTypeNavigationBar[@name=\"ACCOUNT HISTORY\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.TextView[@text='ACCOUNT HISTORY']",
      on = Platform.MOBILE_ANDROID)
  protected Text getSignatureText;

  @Locate(xpath = "//XCUIElementTypeTable/XCUIElementTypeOther", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/sectionHeaderText",
      on = Platform.MOBILE_ANDROID)
  protected List<Text> getTransactionDividersText;

  @Locate(
      iOSClassChain =
          "**/XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[`name MATCHES[c] '.*[\\$].*'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/transactionAmount",
      on = Platform.MOBILE_ANDROID)
  protected List<Text> getTransactionAmountText;

  @Locate(
      iOSClassChain =
          "**/XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[`name MATCHES[c] '.*[,][ ].*'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/transactionDate",
      on = Platform.MOBILE_ANDROID)
  protected List<Text> getTransactionDatesText;

  /* -------- Actions -------- */

  /**
   * Gets transaction date.
   *
   * @param index the index
   * @return the transaction date
   */
  public String getTransactionDate(int index) {
    logger.info(">>>>>" + getDriver().getPageSource());
    return getTransactionDatesText.get(index).getText();
  }

  /**
   * Gets transaction date divider.
   *
   * @param index the index
   * @return the transaction date divider
   */
  public String getTransactionDateDivider(int index) {
    return getTransactionDividersText.get(index).getAttributeValue("name");
  }

  /**
   * Gets transaction amount.
   *
   * @param index the index
   * @return the transaction amount
   */
  public String getTransactionAmount(int index) {
    return getTransactionAmountText.get(index).getText();
  }
}
