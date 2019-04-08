package com.applause.auto.pageframework.pages;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openqa.selenium.WebElement;

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
import com.applause.auto.pageframework.chunks.DatePickerChunk;
import com.applause.auto.pageframework.chunks.ShopRunnerChunk;
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
		WebHelper.waitForDocument();
		syncHelper.waitForElementToDisappear(getProgressWrapper().getAbsoluteSelector());
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public Actions
	 */

	public ShopRunnerChunk signInShopRunner() {
		LOGGER.info("Click on Sign In shop runner");
		getSignInShopRunnerButton().click();
		return ChunkFactory.create(ShopRunnerChunk.class, this, "");
	}

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
	 * Fill unique shipping info.
	 *
	 * @param uniqPrefix
	 *            the uniq prefix
	 */
	public void fillUniqueShippingInfo(String uniqPrefix) {
		LOGGER.info("Filling shipping info");
		getFirstNameEditField().setText(uniqPrefix + TestConstants.TestData.FIRST_NAME);
		getLastNameEditField().setText(TestConstants.TestData.LAST_NAME);
		getPhoneNumberEditField().setText(TestConstants.TestData.PHONE);
		getMainAddressEditField().setText(TestConstants.TestData.ADDRESS_2);
		getZipCodeEditField().setText(TestConstants.TestData.ZIP_CODE_2);
		getCityEditField().setText(TestConstants.TestData.CITY);
		webHelper.jsSelect(getStateDropdown().getWebElement(), TestConstants.TestData.STATE);
	}

	/**
	 * Modify shipping info by delta.
	 *
	 * @param uniqPrefix
	 *            the uniq prefix
	 */
	public void modifyShippingInfoByDelta(String uniqPrefix) {
		LOGGER.info("Filling shipping info");
		getFirstNameModifyEditField().clearText();
		getFirstNameModifyEditField().setText(uniqPrefix + TestConstants.TestData.FIRST_NAME);
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
	 * Continue add new address checkout shipping info page.
	 *
	 * @return the checkout shipping info page
	 */
	public CheckoutShippingInfoPage continueAddNewAddress() {
		LOGGER.info("Click Continue on contact section");
		getNewAddressContinueButton().click();
		syncHelper.suspend(20000);
		WebHelper.waitForDocument();
		return PageFactory.create(CheckoutShippingInfoPage.class);
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

	public CheckoutPaymentMethodPage setShippingMethod() {
		LOGGER.info("Click continue");
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
		WebHelper.scrollToElement(getShippingInfoContinueButton().getWebElement());

		syncHelper.suspend(10000);
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

	/**
	 * Add new address checkout shipping info page.
	 *
	 * @return the checkout shipping info page
	 */
	public CheckoutShippingInfoPage addNewAddress() {
		LOGGER.info("Click on Add new address button");
		getAddNewAddressButton().click();
		return PageFactory.create(CheckoutShippingInfoPage.class);
	}

	/**
	 * Edit address checkout shipping info page.
	 *
	 * @param addressKey
	 *            the address key
	 * @return the checkout shipping info page
	 */
	public CheckoutShippingInfoPage editAddress(String addressKey) {
		LOGGER.info("Click on edit link for address: " + addressKey);
		List<String> addresses = getAddresses();
		int index = IntStream.range(0, addresses.size()).filter(i -> addresses.get(i).contains(addressKey)).findFirst()
				.getAsInt();
		getAddressesEditButton().get(index).click();
		return PageFactory.create(CheckoutShippingInfoPage.class);
	}

	/**
	 * Edit shipping date date picker chunk.
	 *
	 * @return the date picker chunk
	 */
	public DatePickerChunk editShippingDate() {
		LOGGER.info("Click on edit shipping date");
		getEditShippingDateButton().click();
		return ChunkFactory.create(DatePickerChunk.class, this, "");
	}

	/**
	 * Gets addresses.
	 *
	 * @return the addresses
	 */
	public List<String> getAddresses() {
		List<String> result = getAddressesText().stream().map(item -> {
			String text = item.getText();
			LOGGER.info("Found address: " + text);
			return text;
		}).collect(Collectors.toList());
		return result;
	}

	/**
	 * Gets shipping date.
	 *
	 * @return the shipping date
	 */
	public String getShippingDate() {
		return getShippingDateText().getText();
	}

	/**
	 * Update after shipping info modification checkout shipping info page.
	 *
	 * @return the checkout shipping info page
	 */
	public CheckoutShippingInfoPage updateAfterShippingInfoModification() {
		LOGGER.info("Click on Update button");
		getModifiedAddressUpdateButton().click();
		return PageFactory.create(CheckoutShippingInfoPage.class);
	}

	/*
	 * Protected Getters
	 */
	@WebElementLocator(webDesktop = "//div[@id='srd_so']//a[text()='sign in']")
	protected Button getSignInShopRunnerButton() {
		return new Button(this, getLocator(this, "getSignInShopRunnerButton"));
	}

	@WebElementLocator(webDesktop = "h2#checkout-title-opc-shipping.active")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "input[id='shipping:firstname']")
	protected EditField getFirstNameEditField() {
		return new EditField(this, getLocator(this, "getFirstNameEditField"));
	}

	@WebElementLocator(webDesktop = ".editing input[id^='shipping:firstname']")
	protected EditField getFirstNameModifyEditField() {
		return new EditField(this, getLocator(this, "getFirstNameModifyEditField"));
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

	@WebElementLocator(webDesktop = "button[title='Continue']")
	protected Button getNewAddressContinueButton() {
		return new Button(this, getLocator(this, "getNewAddressContinueButton"));
	}

	@WebElementLocator(webDesktop = ".editing button[title='Update']")
	protected Button getModifiedAddressUpdateButton() {
		return new Button(this, getLocator(this, "getModifiedAddressUpdateButton"));
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

	@WebElementLocator(webDesktop = ".shipping-add-address")
	protected Button getAddNewAddressButton() {
		return new Button(this, getLocator(this, "getAddNewAddressButton"));
	}

	@WebElementLocator(webDesktop = "#checkout-step-shipping .edit-shipping-date")
	protected Button getEditShippingDateButton() {
		return new Button(this, getLocator(this, "getEditShippingDateButton"));
	}

	@WebElementLocator(webDesktop = "[title^='Please wait...']")
	protected Text getProgressWrapper() {
		return new Text(this, getLocator(this, "getProgressWrapper"));
	}

	@WebElementLocator(webDesktop = "#checkout-step-shipping .shipping-date #ddate-selected-date")
	protected Text getShippingDateText() {
		return new Text(this, getLocator(this, "getShippingDateText"));
	}

	@WebElementLocator(web = ".checkout-section-content > .shipping-address-item ol li label")
	protected List<WebElement> getAddressesText() {
		return queryHelper.findElementsByExtendedCss(getLocator(this, "getAddressesText"));
	}

	@WebElementLocator(web = ".checkout-section-content > .shipping-address-item ol li > a")
	protected List<WebElement> getAddressesEditButton() {
		return queryHelper.findElementsByExtendedCss(getLocator(this, "getAddressesEditButton"));
	}

	@WebElementLocator(web = ".checkout-section-content > .shipping-address-item ol li label")
	protected List<WebElement> getEditAddressButtonByKey() {
		return queryHelper.findElementsByExtendedCss(getLocator(this, "getEditAddressButtonByKey"));
	}
}
