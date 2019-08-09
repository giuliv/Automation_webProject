package com.applause.auto.web.views;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.util.webDrivers.BrowserType;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = AddShippingAddressPage.class, on = Platform.WEB)
public class AddShippingAddressPage extends BaseComponent {

	/* -------- Elements -------- */

	/* -------- Actions -------- */

	WebHelper webHelper = new WebHelper();

	// Public actions

	/**
	 * Enter Address Line 1
	 *
	 * @param address
	 */
	public void enterAddressLine1(String address) {
		logger.info("Entering Address");
		getAddressLine1Field.sendKeys(address);
	}

	/**
	 * Enter Address Line 2
	 *
	 * @param address
	 */
	public void enterAddressLine2(String address) {
		logger.info("Entering Address");
		getAddressLine2Field.sendKeys(address);
	}

	/**
	 * Enter Zip Code
	 *
	 * @param zipCode
	 */
	public void enterZipCode(String zipCode) {
		logger.info("Entering Zip Code");
		getZipcodeField.sendKeys(zipCode);
	}

	/**
	 * Select State
	 *
	 * @param state
	 */
	public void selectState(String state) {
		logger.info("Selecting State");
		if (EnvironmentHelper.isSafari(DriverManager.getDriver())) {
			webHelper.jsSelect(getStateSelectList.getWebElement(), Constants.TestData.STATE);
		} else {
			getStateSelectList.select(state);
		}
	}

	/**
	 * Enter City
	 *
	 * @param city
	 */
	public void enterCity(String city) {
		logger.info("Entering City");
		getCityField.sendKeys(city);
	}

	/**
	 * Enter Phone Number
	 *
	 * @param phoneNum
	 */
	public void enterPhoneNumber(String phoneNum) {
		logger.info("Entering Phone Number");
		getPhoneNumberField.sendKeys(phoneNum);
	}

	/**
	 * Click Save Address
	 *
	 * @return AddressBookPage
	 */
	public AddressBookPage clickSaveAddress() {
		logger.info("Clicking Save Address");
		getSaveAddressButton.click();
		SyncHelper.sleep(3000);
		if (getUseAddressAsEnteredButton.visible()) {
			getUseAddressAsEnteredButton.click();
		}
		return ComponentFactory.create(AddressBookPage.class);
	}

	// Protected getters
	@Locate(css = "body > div.wrapper > div > div.main-container.col2-left-layout > div > div.col-main > div > div.page-title > h1", on = Platform.WEB)
	protected Text getViewSignature;

	@Locate(css = "#street_1", on = Platform.WEB)
	protected TextBox getAddressLine1Field;

	@Locate(css = "#street_2", on = Platform.WEB)
	protected TextBox getAddressLine2Field;

	@Locate(css = "#zip", on = Platform.WEB)
	protected TextBox getZipcodeField;

	@Locate(css = "#region_id", on = Platform.WEB)
	protected SelectList getStateSelectList;

	@Locate(css = "#city", on = Platform.WEB)
	protected TextBox getCityField;

	@Locate(css = "#telephone", on = Platform.WEB)
	protected TextBox getPhoneNumberField;

	@Locate(css = "button.button.btn-save", on = Platform.WEB)
	protected Button getSaveAddressButton;

	@Locate(css = "#qas-popup > div.modal-content-area > div > div.two-columns > div.right-column > div.qas-box-content > button", on = Platform.WEB)
	protected Button getUseAddressAsEnteredButton;

}
