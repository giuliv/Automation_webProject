package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.UIData;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.Landing;
import java.lang.invoke.MethodHandles;

@Implementation(is = AccountMenuChunk.class, on = Platform.WEB_DESKTOP)
@Implementation(is = AccountMenuChunk.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = AccountMenuChunk.class, on = Platform.WEB_MOBILE_PHONE)
public class AccountMenuChunk extends BaseComponent {

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public AccountMenuChunk(UIData parent, String selector) {
		super(parent, selector);
	}

	/*
	 * Public actions
	 */

	/**
	 * Click Close Button on explore-dashboard-modal
	 *
	 * @return DashboardPage
	 */
	public Landing signOut() {
		logger.info("Expanding menu");
		getExpandMenuButton.click();
		logger.info("Click on Sign Out button");
		getSignOutButton.click();
		return ComponentFactory.create(Landing.class);
	}

	/*
	 * Protected Getters
	 */

	@Locate(jQuery = "a[href='#header-account']", on = Platform.WEB_DESKTOP)
	protected Button getExpandMenuButton;

	@Locate(jQuery = "#header-account a[title='Log Out']", on = Platform.WEB_DESKTOP)
	protected Button getSignOutButton;

}
