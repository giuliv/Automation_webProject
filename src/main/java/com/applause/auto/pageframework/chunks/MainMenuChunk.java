package com.applause.auto.pageframework.chunks;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.UIData;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPageChunk;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.pages.LandingPage;
import com.applause.auto.pageframework.pages.ShopEquipmentPage;
import com.applause.auto.pageframework.pages.ShopTeaPage;
import com.applause.auto.pageframework.testdata.TestConstants;

@WebDesktopImplementation(MainMenuChunk.class)
@WebTabletImplementation(MainMenuChunk.class)
@WebPhoneImplementation(MainMenuChunk.class)
public class MainMenuChunk extends AbstractPageChunk {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public MainMenuChunk(UIData parent, String selector) {
		super(parent, selector);
	}

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public actions
	 */

	/**
	 * Hover over main menu category
	 *
	 */
	public void hoverCategory(String category) {
		LOGGER.info("Hover a category in the main menu");
		getMainMenuCategoryButton(category).hover();
	}

	/**
	 * Click a Sub-Menu under a category
	 *
	 */
	public void clickCategorySubmenu(String category, String subMenu) {
		LOGGER.info(String.format("Accessing Sub-Menu '%s' under category '%s'", subMenu, category));
		hoverCategory(category);
		getCategorySubmenuButton(subMenu).click();
	}

	/**
	 * Access Sub-Menu Tea under Category Shop from main menu
	 *
	 */
	public ShopTeaPage accessShopTea() {
		LOGGER.info("Accessing Shop-Tea");
		clickCategorySubmenu(TestConstants.TestMainMenu.NAV_CATEGORY_SHOP, TestConstants.TestMainMenu.NAV_SUBMENU_TEA);
		return PageFactory.create(ShopTeaPage.class);
	}

	/**
	 * Access Sub-Menu Equipment under Category Shop from main menu
	 *
	 */
	public ShopEquipmentPage accessShopEquipment() {
		LOGGER.info("Accessing Shop-Equipment");
		clickCategorySubmenu(TestConstants.TestMainMenu.NAV_CATEGORY_SHOP,
				TestConstants.TestMainMenu.NAV_SUBMENU_EQUIPMENT);
		return PageFactory.create(ShopEquipmentPage.class);
	}

	/**
	 * Click Peets Coffee logo
	 *
	 * @return LandingPage
	 */
	public LandingPage clickHeaderLogo() {
		LOGGER.info("Click Peets Coffee logo");
		getHeaderLogo().click();
		return PageFactory.create(LandingPage.class);
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = ".page-header-container")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "//ul[@class='navigation']/li[contains(.,'%s')]")
	protected Button getMainMenuCategoryButton(String category) {
		return new Button(this, String.format(getLocator(this, "getMainMenuCategoryButton"), category));
	}

	@WebElementLocator(webDesktop = "//a[@class='drop-link' and contains(.,'%s')]")
	protected Button getCategorySubmenuButton(String subMenu) {
		return new Button(this, String.format(getLocator(this, "getCategorySubmenuButton"), subMenu));
	}

	@WebElementLocator(webDesktop = ".logo")
	protected Button getHeaderLogo() {
		return new Button(this, getLocator(this, "getHeaderLogo"));
	}

}
