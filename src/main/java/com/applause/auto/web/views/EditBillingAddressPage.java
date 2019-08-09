package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = EditBillingAddressPage.class, on = Platform.WEB)
public class EditBillingAddressPage extends BaseComponent {

	/* -------- Elements -------- */

	/* -------- Actions -------- */

	WebHelper webHelper = new WebHelper();

	// Public actions

	/**
	 * Enter Address
	 *
	 * @param address1
	 * @param address2
	 * @return String
	 */
	public String enterAddress(String address1, String address2) {
		logger.info("Entering Address");
		address1 = webHelper.getTimestamp(address1);
		getAddressLine1Field.sendKeys(address1);
		getAddressLine2Field.sendKeys(address2);
		return address1;
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
		try {
			getUseAddressAsEnteredButton.click();
		} catch (Exception ex) {
			logger.info("Popup not displayed");
		}
		return ComponentFactory.create(AddressBookPage.class);
	}

	// Protected getters
	@Locate(css = "div.main-container.col2-left-layout > div > div.col-main > div > div.page-title > h1", on = Platform.WEB)
	protected Text getViewSignature;

	@Locate(css = "#street_1", on = Platform.WEB)
	protected TextBox getAddressLine1Field;

	@Locate(css = "#street_2", on = Platform.WEB)
	protected TextBox getAddressLine2Field;

	@Locate(css = "button.button.btn-save", on = Platform.WEB)
	protected Button getSaveAddressButton;

	@Locate(css = "#qas-popup > div.modal-content-area > div > div.two-columns > div.right-column > div.qas-box-content > button", on = Platform.WEB)
	protected Button getUseAddressAsEnteredButton;
}
