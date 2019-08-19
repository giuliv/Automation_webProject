package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.appium.java_client.MobileElement;
import java.util.List;

@Implementation(is = AccountHistoryView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = AccountHistoryView.class, on = Platform.MOBILE_IOS)
public class AccountHistoryView extends BaseComponent {

	/* -------- Elements -------- */

	@Locate(xpath = "//XCUIElementTypeNavigationBar[@name=\"ACCOUNT HISTORY\"]", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//android.widget.TextView[@text='ACCOUNT HISTORY']", on = Platform.MOBILE_ANDROID)
	protected Text getSignatureText;

	@Locate(xpath = "//XCUIElementTypeTable/XCUIElementTypeOther", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/sectionHeaderText", on = Platform.MOBILE_ANDROID)
	protected List<MobileElement> getTransactionDividersText;

	@Locate(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[3]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/transactionAmount", on = Platform.MOBILE_ANDROID)
	protected List<MobileElement> getTransactionAmountText;

	@Locate(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[2]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/transactionTitle", on = Platform.MOBILE_ANDROID)
	protected List<MobileElement> getTransactionNamesText;

	@Locate(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[1]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/transactionDate", on = Platform.MOBILE_ANDROID)
	protected List<MobileElement> getTransactionDatesText;

	/* -------- Actions -------- */

	/**
	 * Gets transaction date.
	 *
	 * @param index
	 *            the index
	 * @return the transaction date
	 */
	public String getTransactionDate(int index) {
		return getTransactionDatesText.get(index).getText();
	}

	/**
	 * Gets transaction date divider.
	 *
	 * @param index
	 *            the index
	 * @return the transaction date divider
	 */
	public String getTransactionDateDivider(int index) {
		return getTransactionDividersText.get(index).getAttribute("name");
	}

	/**
	 * Gets transaction amount.
	 *
	 * @param index
	 *            the index
	 * @return the transaction amount
	 */
	public String getTransactionAmount(int index) {
		return getTransactionAmountText.get(index).getText();
	}
}
