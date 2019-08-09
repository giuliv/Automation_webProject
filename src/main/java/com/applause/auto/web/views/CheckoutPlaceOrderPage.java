package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = CheckoutPlaceOrderPage.class, on = Platform.WEB)
public class CheckoutPlaceOrderPage extends BaseComponent {

	/* -------- Elements -------- */

	/* -------- Actions -------- */

	/*
	 * Public Actions
	 */

	/**
	 * Click Place Order Button
	 * 
	 * @return CheckoutConfirmationPage
	 */
	public CheckoutConfirmationPage placeOrder() {
		logger.info("Click Place Order Button");
		SyncHelper.waitUntilElementPresent(getLocator(this, "getPlaceOrderButton"));
		SyncHelper.sleep(7000); // Required time to trigger spinner animation if shown

		getPlaceOrderButton.click();
		SyncHelper.sleep(2000); // Required time to trigger spinner animation if shown
		SyncHelper.waitUntilElementNotPresent(getLocator(this, "getPlaceOrderSpinner"));
		return ComponentFactory.create(CheckoutConfirmationPage.class);
	}

	/**
	 * Click Place Order Button without payment method selected
	 * 
	 * @return CheckoutPaymentMethodPage
	 */
	public CheckoutPaymentMethodPage placeOrderMissingPayment() {
		logger.info("Click Place Order Button");
		getPlaceOrderButton.click();
		return ComponentFactory.create(CheckoutPaymentMethodPage.class);
	}

	/**
	 * Get Gift Order Message
	 *
	 * @return String
	 */
	public String getGiftMessage() {
		logger.info("Getting gift Message");
		return getGiftMessageTextBox.getCurrentText();
	}

	/**
	 * Get Product Name
	 *
	 * @return String
	 */
	public String getProductName() {
		logger.info("Getting product name");
		return getProductNameText.getText();
	}

	/*
	 * Protected Getters
	 */

	@Locate(css = "h2#checkout-title-opc-review.active", on = Platform.WEB)
	protected Text getViewSignature;

	@Locate(css = "button[title='Place Order']", on = Platform.WEB)
	protected Button getPlaceOrderButton;

	@Locate(css = "#gift-message-whole-message", on = Platform.WEB)
	protected TextBox getGiftMessageTextBox;

	@Locate(css = "#cart-table-standard > tbody > tr > td.product-info-cell.last > h3 > a", on = Platform.WEB)
	protected Text getProductNameText;

	@Locate(css = "span#opc-please-wait.please-wait-review", on = Platform.WEB)
	protected ContainerElement getPlaceOrderSpinner;

}
