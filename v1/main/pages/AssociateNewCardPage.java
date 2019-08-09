package com.applause.auto.pageframework.pages;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.EditField;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.helpers.WebHelper;

@WebDesktopImplementation(AssociateNewCardPage.class)
@WebTabletImplementation(AssociateNewCardPage.class)
@WebPhoneImplementation(AssociateNewCardPage.class)
public class AssociateNewCardPage extends AbstractPage {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	// Public actions

	/**
	 * Enter Peets Card Number
	 *
	 * @param cardNum
	 */
	public void enterCardNumber(String cardNum) {
		LOGGER.info("Entering card number");
		getCardNumberField().setText(cardNum);
	}

	/**
	 * Enter Peets PIN Number
	 *
	 * @param pinNum
	 */
	public void enterPinNumber(String pinNum) {
		LOGGER.info("Entering PIN number");
		getPinNumberField().setText(pinNum);
	}

	/**
	 * Click Associate Card
	 *
	 * @return PaymentMethodsPage
	 */
	public PaymentMethodsPage clickAssociateCard() {
		LOGGER.info("Clicking Associate Card");
		getAssociateCardButton().click();
		return PageFactory.create(PaymentMethodsPage.class);
	}

	// Protected getters
	@WebElementLocator(webDesktop = "div.main-container.col2-left-layout > div > div.col-main > div > div.page-title.title-buttons > h1")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "#card_number")
	protected EditField getCardNumberField() {
		return new EditField(this, getLocator(this, "getCardNumberField"));
	}

	@WebElementLocator(webDesktop = "#pin_number")
	protected EditField getPinNumberField() {
		return new EditField(this, getLocator(this, "getPinNumberField"));
	}

	@WebElementLocator(webDesktop = "button.btn-dark")
	protected Button getAssociateCardButton() {
		return new Button(this, getLocator(this, "getAssociateCardButton"));
	}
}
