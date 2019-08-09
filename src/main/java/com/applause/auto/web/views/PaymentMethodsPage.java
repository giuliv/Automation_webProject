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

@Implementation(is = PaymentMethodsPage.class, on = Platform.WEB)
public class PaymentMethodsPage extends BaseComponent {

	/* -------- Elements -------- */

	/* -------- Actions -------- */


	/**
	 * Verify Add Credit Card Button is Displayed
	 *
	 * @return boolean
	 */
	public boolean isAddCreditCardButtonDisplayed() {
		logger.info("Verifying Add Credit Card button is displayed");
		return getAddACreditCardButton.isDisplayed();
	}

	/**
	 * Verify Add Paypal Account Button is Displayed
	 *
	 * @return boolean
	 */
	public boolean isAddPaypalAccountButtonDisplayed() {
		logger.info("Verifying Add Paypal Account button is displayed");
		return getAddAPaypalAccountButton.isDisplayed();
	}

	/**
	 * Verify Add Peets Card Button is Displayed
	 *
	 * @return boolean
	 */
	public boolean isAddPeetsCardButtonDisplayed() {
		logger.info("Verifying Add Peets Card button is displayed");
		return getAddPeetsCardButton.isDisplayed();
	}

	/**
	 * Verify Credit Card is Displayed
	 *
	 * @return boolean
	 */
	public boolean isCreditCardDisplayed() {
		logger.info("Verifying Credit Card is displayed");
		return getCreditCardBlock.isDisplayed();
	}

	/**
	 * Verify Peets Card is Displayed
	 *
	 * @return boolean
	 */
	public boolean isPeetsCardDisplayed() {
		logger.info("Verifying Peets Card is displayed");
		return getPeetsCardBlock.isDisplayed();
	}

	/**
	 * Click Edit Credit Card
	 *
	 * @return EditPaymentMethodPage
	 */
	public EditPaymentMethodPage clickEditCreditCard() {
		logger.info("Clicking Edit Credit Card");
		getEditCreditCardButton.click();
		return ComponentFactory.create(EditPaymentMethodPage.class);
	}

	/**
	 * Get Name on Credit Card
	 *
	 * @return String
	 */
	public String getNameOnCreditCard() {
		logger.info("Getting Name on Credit Card");
		return getNameOnCreditCardText.getText();
	}

	/**
	 * Click Delete Credit Card
	 */
	public void clickDeletePeetsCard() {
		logger.info("Clicking Delete Credit Card");
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		jse.executeScript("scroll(0,250)", "");
		getDeletePeetsCardButton.click();
		Alert alert = getDriver().switchTo().alert();
		alert.accept();
	}

	/**
	 * Verify Peets Card Deletion Text
	 *
	 * @return boolean
	 */
	public boolean didPeetsCardDelete() {
		logger.info("Verifying Peets Card was removed");
		return getSuccessfulDeleteText.isDisplayed();
	}

	/**
	 * Click Add New Peets Card
	 *
	 * @return AssociateNewCardPage
	 */
	public AssociateNewCardPage addPeetsCard() {
		logger.info("Clicking Add New Peets Card");
		getAddPeetsCardButton.click();
		return ComponentFactory.create(AssociateNewCardPage.class);
	}

	// Protected getters
	@Locate(css = "ol.payment-cards-list", on = Platform.WEB)
	protected Text getViewSignature;

	@Locate(css = "ol.payment-cards-list", on = Platform.WEB)
	protected Text getCreditCardBlock;

	@Locate(css = "div.main-section li.peets-account-block:nth-child(1) p:nth-child(4)", on = Platform.WEB)
	protected Text getNameOnCreditCardText;

	@Locate(css = "div.main-section li.peets-account-block:nth-child(2) a.link-remove", on = Platform.WEB)
	protected Button getDeletePeetsCardButton;

	@Locate(css = "div.main-section li.peets-account-block:nth-child(2) div.highlighted", on = Platform.WEB)
	protected Text getPeetsCardBlock;

	@Locate(xpath = "//a[text()='Add a credit card']", on = Platform.WEB)
	protected Button getAddACreditCardButton;

	@Locate(xpath = "//a[text()='Add a PayPal account']", on = Platform.WEB)
	protected Button getAddAPaypalAccountButton;

	@Locate(css = "div.col-main > div > div.payment-card-holder.account-container > div.title > div > a:nth-child(3)", on = Platform.WEB)
	protected Button getAddPeetsCardButton;

	@Locate(css = "div.payment-card-holder.account-container > div.main-section > ol > li:nth-child(1) > div.actions-col > ul > li:nth-child(1) > a", on = Platform.WEB)
	protected Button getEditCreditCardButton;

	@Locate(css = "div.main-container.col2-left-layout > div > div.col-main > div > div.payment-card-holder.account-container > ul > li > ul > li", on = Platform.WEB)
	protected Text getSuccessfulDeleteText;
}
