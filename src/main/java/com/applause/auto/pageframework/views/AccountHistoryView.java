package com.applause.auto.pageframework.views;

import java.lang.invoke.MethodHandles;
import java.util.List;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;

import io.appium.java_client.MobileElement;

@AndroidImplementation(AccountHistoryView.class)
@IosImplementation(AccountHistoryView.class)
public class AccountHistoryView extends AbstractDeviceView {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getSignatureText(), 120000);
	}

	/**
	 * Gets transaction date.
	 *
	 * @param index
	 *            the index
	 * @return the transaction date
	 */
	public String getTransactionDate(int index) {
		return getTransactionDatesText().get(index).getText();
	}

	/**
	 * Gets transaction date divider.
	 *
	 * @param index
	 *            the index
	 * @return the transaction date divider
	 */
	public String getTransactionDateDivider(int index) {
		return getTransactionDividersText().get(index).getAttribute("name");
	}

	/**
	 * Gets transaction amount.
	 *
	 * @param index
	 *            the index
	 * @return the transaction amount
	 */
	public String getTransactionAmount(int index) {
		return getTransactionAmountText().get(index).getText();
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "//android.widget.TextView[@text='Account History']", iOS = "//XCUIElementTypeNavigationBar[@name=\"Account History\"]")
	protected Text getSignatureText() {
		return new Text(getLocator(this, "getSignatureText"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/sectionHeaderText", iOS = "//XCUIElementTypeTable/XCUIElementTypeOther")
	protected List<MobileElement> getTransactionDividersText() {
		return queryHelper.findElements(getLocator(this, "getTransactionDividersText"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/transactionAmount", iOS = "//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[3]")
	protected List<MobileElement> getTransactionAmountText() {
		return queryHelper.findElements(getLocator(this, "getTransactionAmountText"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/transactionTitle", iOS = "//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[2]")
	protected List<MobileElement> getTransactionNamesText() {
		return queryHelper.findElements(getLocator(this, "getTransactionNamesText"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/transactionDate", iOS = "//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[1]")
	protected List<MobileElement> getTransactionDatesText() {
		return queryHelper.findElements(getLocator(this, "getTransactionDatesText"));
	}
}
