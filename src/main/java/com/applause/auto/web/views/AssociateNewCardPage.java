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

@Implementation(is = AssociateNewCardPage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = AssociateNewCardPage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = AssociateNewCardPage.class, on = Platform.WEB_MOBILE_PHONE)
public class AssociateNewCardPage extends BaseComponent {

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
	@Locate(jQuery = "div.main-container.col2-left-layout > div > div.col-main > div > div.page-title.title-buttons > h1", on = Platform.WEB_DESKTOP)
	protected Text getViewSignature;

	@Locate(jQuery = "#card_number", on = Platform.WEB_DESKTOP)
	protected TextBox getCardNumberField;

	@Locate(jQuery = "#pin_number", on = Platform.WEB_DESKTOP)
	protected TextBox getPinNumberField;

	@Locate(jQuery = "button.btn-dark", on = Platform.WEB_DESKTOP)
	protected Button getAssociateCardButton;
}
