package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = AssociateNewCardPage.class, on = Platform.WEB)
public class AssociateNewCardPage extends BaseComponent {

	/* -------- Elements -------- */

	/* -------- Actions -------- */

	// Public actions

	/**
	 * Enter Peets Card Number
	 *
	 * @param cardNum
	 */
	public void enterCardNumber(String cardNum) {
		logger.info("Entering card number");
		getCardNumberField.sendKeys(cardNum);
	}

	/**
	 * Enter Peets PIN Number
	 *
	 * @param pinNum
	 */
	public void enterPinNumber(String pinNum) {
		logger.info("Entering PIN number");
		getPinNumberField.sendKeys(pinNum);
	}

	/**
	 * Click Associate Card
	 *
	 * @return PaymentMethodsPage
	 */
	public PaymentMethodsPage clickAssociateCard() {
		logger.info("Clicking Associate Card");
		getAssociateCardButton.click();
		return ComponentFactory.create(PaymentMethodsPage.class);
	}

	// Protected getters
	@Locate(css = "div.main-container.col2-left-layout > div > div.col-main > div > div.page-title.title-buttons > h1", on = Platform.WEB)
	protected Text getViewSignature;

	@Locate(css = "#card_number", on = Platform.WEB)
	protected TextBox getCardNumberField;

	@Locate(css = "#pin_number", on = Platform.WEB)
	protected TextBox getPinNumberField;

	@Locate(css = "button.btn-dark", on = Platform.WEB)
	protected Button getAssociateCardButton;
}
