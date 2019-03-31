package com.applause.auto.pageframework.chunks;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.UIData;
import com.applause.auto.framework.pageframework.util.drivers.BrowserType;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPageChunk;
import com.applause.auto.framework.pageframework.web.ChunkFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Dropdown;
import com.applause.auto.framework.pageframework.webcontrols.EditField;
import com.applause.auto.pageframework.helpers.WebHelper;
import com.applause.auto.pageframework.testdata.TestConstants;

@WebDesktopImplementation(CreateSubscriptionChunk.class)
@WebTabletImplementation(CreateSubscriptionChunk.class)
@WebPhoneImplementation(CreateSubscriptionChunk.class)
public class CreateSubscriptionChunk extends AbstractPageChunk {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public CreateSubscriptionChunk(UIData parent, String selector) {
		super(parent, selector);
	}

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getNewSubscriptionFrequencyDropdown());
	}

	public void selectNewSubscription() {
		logger.info("Select New Subscription");
		syncHelper.waitForElementToAppear(getNewSubscriptionButton().getAbsoluteSelector());
		WebHelper.waitForElementToBeClickable(getNewSubscriptionButton().getWebElement());
		getNewSubscriptionButton().click();
	}

	/*
	 * Public actions
	 */

	/**
	 * Sets new subscription name.
	 *
	 * @param subscriptionName
	 *            the subscription name
	 */
	public void setNewSubscriptionName(String subscriptionName) {
		LOGGER.info("Set subscription name: " + subscriptionName);
		getNewSubscriptionNameEditField().setText(subscriptionName);
	}

	/**
	 * Select frequency.
	 *
	 * @param frequency
	 *            the frequency
	 */
	public void selectFrequency(TestConstants.SubscriptionTerm frequency) {
		LOGGER.info("Set frequency: " + frequency);
		if (env.getBrowserType() == BrowserType.SAFARI) {
			WebHelper webHelper = new WebHelper();
			webHelper.jsSelect(getNewSubscriptionFrequencyDropdown().getWebElement(), frequency.miniCartSpell);
		} else {
			getNewSubscriptionFrequencyDropdown().select(frequency.miniCartSpell);
		}
	}

	/**
	 * Create subscription mini cart container chunk.
	 *
	 * @return the mini cart container chunk
	 */
	public MiniCartContainerChunk createSubscription() {
		LOGGER.info("Create subscription");
		getNewSubscriptionCreateButton().click();
		return ChunkFactory.create(MiniCartContainerChunk.class, this, "");
	}

	@WebElementLocator(webDesktop = "#subscription_name")
	protected EditField getNewSubscriptionNameEditField() {
		return new EditField(this, getLocator(this, "getNewSubscriptionNameEditField"));
	}

	@WebElementLocator(webDesktop = "li#subscription-order-new label")
	protected Button getNewSubscriptionButton() {
		return new Button(this, getLocator(this, "getNewSubscriptionButton"));
	}

	@WebElementLocator(webDesktop = "[title='Create subscription']")
	protected Button getNewSubscriptionCreateButton() {
		return new Button(this, getLocator(this, "getNewSubscriptionCreateButton"));
	}

	@WebElementLocator(webDesktop = "#subscription_period")
	protected Dropdown getNewSubscriptionFrequencyDropdown() {
		return new Dropdown(this, getLocator(this, "getNewSubscriptionFrequencyDropdown"));
	}

}
