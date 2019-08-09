package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.web.components.MainMenuChunk;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = DashboardPage.class, on = Platform.WEB)
public class DashboardPage extends BaseComponent {

	/* -------- Elements -------- */

	/* -------- Actions -------- */

	/*
	 * Public Actions
	 */

	/**
	 * Gets Main Menu
	 *
	 * @return MainMenuChunk
	 */
	public MainMenuChunk getMainMenu() {
		logger.info("Getting Main Menu");
		return ComponentFactory.create(MainMenuChunk.class);
	}

	/*
	 * Protected Getters
	 */

	@Locate(css = ".dashboard", on = Platform.WEB)
	protected Text getViewSignature;

}
