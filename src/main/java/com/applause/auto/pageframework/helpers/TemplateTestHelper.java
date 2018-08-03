package com.applause.auto.pageframework.helpers;

import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.pageframework.pages.LoginPage;
import com.applause.auto.pageframework.pages.SchedulePage;

public class TemplateTestHelper {

	public SchedulePage logIntoAccount(String username, String password, SchedulePage schedule) {

		LoginPage loginPage = schedule.clickonLoginMenu();

		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		loginPage.clickonLoginButton();

		return PageFactory.create(SchedulePage.class);
	}

}
