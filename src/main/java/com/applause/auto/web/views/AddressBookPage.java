package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;

@Implementation(is = AddressBookPage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = AddressBookPage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = AddressBookPage.class, on = Platform.WEB_MOBILE_PHONE)
public class AddressBookPage extends BaseComponent {

	// Public actions

	/**
	 * Verify Address Changed Text is Displayed
	 *
	 * @return boolean
	 */
	public boolean isAddressSavedTextDisplayed() {
		logger.info("Verifying Address Changed text is displayed");
		return getAddressSavedBannerText.isDisplayed();
	}

	/**
	 * Get Billing Address
	 *
	 * @return String
	 */
	public String getBillingAddress() {
		logger.info("Getting Billing Address");
		return getBillingAddressText.getText();
	}

	/**
	 * Get Shipping Address
	 *
	 * @return
	 */
	public String getShippingAddress() {
		logger.info("Getting Shipping Address");
		return getShippingAddressText.getText();
	}

	/**
	 * Delete Billing Address
	 */
	public void deleteBillingAddress() {
		logger.info("Deleting Billing Address");
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		jse.executeScript("scroll(0,450)", "");
		getDeleteBillingAddressButton.click();
		Alert alert = getDriver().switchTo().alert();
		alert.accept();
	}

	/**
	 * Delete Shipping Address
	 */
	public void deleteShippingAddress() {
		logger.info("Deleting Shipping Address");
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		jse.executeScript("scroll(0,450)", "");
		getDeleteShippingAddressButton.click();
		Alert alert = getDriver().switchTo().alert();
		alert.accept();
	}

	/**
	 * Verify Billing Address was Deleted
	 *
	 * @return boolean
	 */
	public boolean isBillingAddressDeleted() {
		logger.info("Verifying Billing Address was deleted");
		return getNoBillingAddressText.isDisplayed();
	}

	/**
	 * Verify Shipping Address was Deleted
	 *
	 * @return boolean
	 */
	public boolean isShippingAddressDeleted() {
		logger.info("Verifying Shipping Address was deleted");
		return getNoShippingAddressText.isDisplayed();
	}

	/**
	 * Add New Billing Address
	 *
	 * @return AddBillingAddressPage
	 */
	public AddBillingAddressPage clickAddNewBillingAddress() {
		logger.info("Clicking Add a New Billing Address");
		getAddBillingAddressButton.click();
		return ComponentFactory.create(AddBillingAddressPage.class);
	}

	/**
	 * Add New Shipping Address
	 *
	 * @return AddShippingAddressPage
	 */
	public AddShippingAddressPage clickAddNewShippingAddress() {
		logger.info("Clicking Add a New Shipping Address");
		getAddShippingAddressButton.click();
		return ComponentFactory.create(AddShippingAddressPage.class);
	}

	// Protected getters
	@Locate(jQuery = "div.account-container.account-inner-section > div.addresses-list > div:nth-child(1) > div.account-inner-title > div.left > h2", on = Platform.WEB_DESKTOP)
	protected Text getViewSignature;

	@Locate(xpath = "//span[contains(.,'address has been saved')]", on = Platform.WEB_DESKTOP)
	protected Text getAddressSavedBannerText;

	@Locate(jQuery = "#billing_form > ol > li > div.info-col > p", on = Platform.WEB_DESKTOP)
	protected Text getBillingAddressText;

	@Locate(jQuery = "#shipping_form > ol > li > div.info-col > p", on = Platform.WEB_DESKTOP)
	protected Text getShippingAddressText;

	@Locate(jQuery = "#billing_form > ol > li > div.actions-col > ul > li:nth-child(2) > a", on = Platform.WEB_DESKTOP)
	protected Button getDeleteBillingAddressButton;

	@Locate(jQuery = "#shipping_form > ol > li > div.actions-col > ul > li:nth-child(2) > a", on = Platform.WEB_DESKTOP)
	protected Button getDeleteShippingAddressButton;

	@Locate(jQuery = "body > div.wrapper > div > div.main-container.col2-left-layout > div > div.col-main > div > div.account-container.account-inner-section > div.addresses-list > div:nth-child(2) > div.main-section > div", on = Platform.WEB_DESKTOP)
	protected Text getNoBillingAddressText;

	@Locate(jQuery = "body > div.wrapper > div > div.main-container.col2-left-layout > div > div.col-main > div > div.account-container.account-inner-section > div.addresses-list > div:nth-child(1) > div.main-section > div", on = Platform.WEB_DESKTOP)
	protected Text getNoShippingAddressText;

	@Locate(jQuery = "div.account-container.account-inner-section > div.addresses-list > div:nth-child(2) > div.account-inner-title > div.right > a", on = Platform.WEB_DESKTOP)
	protected Button getAddBillingAddressButton;

	@Locate(jQuery = "div.account-container.account-inner-section > div.addresses-list > div:nth-child(1) > div.account-inner-title > div.right > a", on = Platform.WEB_DESKTOP)
	protected Button getAddShippingAddressButton;
}
