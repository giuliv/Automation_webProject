package com.applause.auto.pageframework.pages;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.util.drivers.BrowserType;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.ChunkFactory;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.BaseHtmlElement;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Dropdown;
import com.applause.auto.framework.pageframework.webcontrols.EditField;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.chunks.VerifyYourAddressDetailsChunk;
import com.applause.auto.pageframework.helpers.WebHelper;
import com.applause.auto.pageframework.testdata.TestConstants;

@WebDesktopImplementation(CheckoutShippingInfoPage.class)
@WebTabletImplementation(CheckoutShippingInfoPage.class)
@WebPhoneImplementation(CheckoutShippingInfoPage.class)
public class CheckoutShippingInfoPage extends AbstractPage {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());
	WebHelper webHelper = new WebHelper();

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public Actions
	 */

	/**
	 * Continue after entering required Shipping info
	 * 
	 */
	public VerifyYourAddressDetailsChunk continueAfterFillingRequiredContactInfo() {
		LOGGER.info("Clicking Continue after filling shipping info");
		fillShippingInfo();
		continueAfterContactInfo();
		return ChunkFactory.create(VerifyYourAddressDetailsChunk.class, this, "");
	}

	/**
	 * Fill Required Fields for Shipping
	 * 
	 */
	public void fillShippingInfo() {
		LOGGER.info("Filling shipping info");
		getFirstNameEditField().setText(TestConstants.TestData.FIRST_NAME);
		getLastNameEditField().setText(TestConstants.TestData.LAST_NAME);
		getPhoneNumberEditField().setText(TestConstants.TestData.PHONE);
		getMainAddressEditField().setText(TestConstants.TestData.ADDRESS);
		getZipCodeEditField().setText(TestConstants.TestData.ZIP_CODE);
		getCityEditField().setText(TestConstants.TestData.CITY);
		webHelper.jsSelect(getStateDropdown().getWebElement(), TestConstants.TestData.STATE);
	}

	/**
	 * Click continue after contact info section
	 * 
	 */
	public void continueAfterContactInfo() {
		LOGGER.info("Click Continue on contact section");
		getContactInfoContinueButton().click();
	}

	/**
	 * Continue after selecting Shipping Method
	 * 
	 */
	public CheckoutPaymentMethodPage setShippingMethod(String shippingMethod) {
		LOGGER.info("Set Shipping Method");
		selectShippingMethod(shippingMethod);
		continueAfterShippingInfo();
		return PageFactory.create(CheckoutPaymentMethodPage.class);
	}

	/**
	 * Select Shipping Method
	 * 
	 */
	public void selectShippingMethod(String shippingMethod) {
		LOGGER.info("Select Shipping Method");
		getShippingMethodButton(shippingMethod).click();
	}

	/**
	 * Click continue after shipping info section
	 * 
	 */
	public void continueAfterShippingInfo() {
		LOGGER.info("Click Continue on shipping section");
		getShippingInfoContinueButton().click();
	}

	/**
	 * Get Gift Order Message
	 *
	 * @return String
	 */
	public String getGiftMessage() {
		LOGGER.info("Getting gift Message");
		return getGiftMessageEditField().getText();
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = "h2#checkout-title-opc-shipping.active")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "input[id='shipping:firstname']")
	protected EditField getFirstNameEditField() {
		return new EditField(this, getLocator(this, "getFirstNameEditField"));
	}

	@WebElementLocator(webDesktop = "input[id='shipping:lastname']")
	protected EditField getLastNameEditField() {
		return new EditField(this, getLocator(this, "getLastNameEditField"));
	}

	@WebElementLocator(webDesktop = "input[id='shipping:telephone']")
	protected EditField getPhoneNumberEditField() {
		return new EditField(this, getLocator(this, "getPhoneNumberEditField"));
	}

	@WebElementLocator(webDesktop = "input[id='shipping:street1']")
	protected EditField getMainAddressEditField() {
		return new EditField(this, getLocator(this, "getMainAddressEditField"));
	}

	@WebElementLocator(webDesktop = "input[id='shipping:postcode']")
	protected EditField getZipCodeEditField() {
		return new EditField(this, getLocator(this, "getZipCodeEditField"));
	}

	@WebElementLocator(webDesktop = "input[id='shipping:city']")
	protected EditField getCityEditField() {
		return new EditField(this, getLocator(this, "getCityEditField"));
	}

	@WebElementLocator(webDesktop = "select[id='shipping:region_id']")
	protected Dropdown getStateDropdown() {
		return new Dropdown(this, getLocator(this, "getStateDropdown"));
	}

	@WebElementLocator(webDesktop = ".shipping-buttons-set button")
	protected Button getContactInfoContinueButton() {
		return new Button(this, getLocator(this, "getContactInfoContinueButton"));
	}

	@WebElementLocator(webDesktop = ".opc-please-wait")
	protected BaseHtmlElement getShippingLoadingSpinner() {
		return new BaseHtmlElement(this, getLocator(this, "getShippingLoadingSpinner"));
	}

	@WebElementLocator(webDesktop = "//strong[@class='shipping-method-name' and contains(.,'%s')]")
	// @WebElementLocator(webDesktop = "//strong[class='shipping-method-name'][text()='%s']")
	protected Button getShippingMethodButton(String shippingMethod) {
		return new Button(this, String.format(getLocator(this, "getShippingMethodButton"), shippingMethod));
	}

	@WebElementLocator(webDesktop = "#gift-message-whole-message")
	protected EditField getGiftMessageEditField() {
		return new EditField(this, getLocator(this, "getGiftMessageEditField"));
	}

	@WebElementLocator(webDesktop = "#co-shipping-method-form button")
	protected Button getShippingInfoContinueButton() {
		return new Button(this, getLocator(this, "getShippingInfoContinueButton"));
	}

}
