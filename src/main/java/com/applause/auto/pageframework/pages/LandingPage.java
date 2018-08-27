package com.applause.auto.pageframework.pages;

import java.lang.invoke.MethodHandles;

import org.openqa.selenium.NoSuchElementException;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.ChunkFactory;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.chunks.MainMenuChunk;

@WebDesktopImplementation(LandingPage.class)
@WebTabletImplementation(LandingPage.class)
@WebPhoneImplementation(LandingPage.class)
public class LandingPage extends AbstractPage {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		dismissPopup();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public Actions
	 */

	/**
	 * Taps the sign in button.
	 * 
	 * @return a Login
	 */
	public SignInPage clickSignInButton() {
		getSignInButton().click();
		LOGGER.info("Tap on Signin Button");
		return PageFactory.create(SignInPage.class);
	}

	/**
	 * Click Shop Coffee Button
	 *
	 * @return a Shop Coffee Page
	 */
	public ShopCoffeePage clickShopCoffeeButton() {
		LOGGER.info("Tap on Shop Coffee Button");
		getShopCoffeeButton().click();
		return PageFactory.create(ShopCoffeePage.class);
	}

	/**
	 * Click Shop Tea Button
	 *
	 * @return a Shop Tea Page
	 */
	public ShopTeaPage clickTeaCoffeeButton() {
		LOGGER.info("Tap on Shop Tea Button");
		getShopCoffeeButton().click();
		return PageFactory.create(ShopTeaPage.class);
	}

	/**
	 * Gets Main Menu
	 *
	 * @return MainMenuChunk
	 */
	public MainMenuChunk getMainMenu() {
		LOGGER.info("Getting Main Menu");
		return ChunkFactory.create(MainMenuChunk.class, this, "");
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = "header .logo")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = ".account-link")
	protected Button getSignInButton() {
		return new Button(this, getLocator(this, "getSignInButton"));
	}

	@WebElementLocator(webDesktop = ".close-button")
	protected Button getDismissPopupButton() {
		return new Button(this, getLocator(this, "getDismissPopupButton"));
	}

	@WebElementLocator(webDesktop = "//div[@id='shop-peets']//a[contains(.,'Shop Coffee')]")
	protected Button getShopCoffeeButton() {
		return new Button(this, getLocator(this, "getShopCoffeeButton"));
	}

	private void dismissPopup() {
		try {
			LOGGER.info("Attempting to dismiss popup");
			syncHelper.waitForElementToAppear(getDismissPopupButton());
			getDismissPopupButton().click();
		} catch (NoSuchElementException e) {
			LOGGER.info("Popup not found, moving on");
		}
	}
}
