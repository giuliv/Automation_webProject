package com.applause.auto.test.mobile.helpers;

import java.lang.invoke.MethodHandles;

import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;

import com.applause.auto.mobile.views.DashboardView;
import com.applause.auto.mobile.views.LandingView;
import com.applause.auto.mobile.views.SignInView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Implementation(is = TestHelper.class, on = Platform.MOBILE)
public class TestHelper extends BaseComponent {
	private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

	/**
	 * Sign in dashboard view.
	 *
	 * @param landingView
	 *            the landing view
	 * @return the dashboard view
	 */
	public DashboardView signIn(LandingView landingView) {
		return signIn(landingView, MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD,
				DashboardView.class);
	}

	/**
	 * Sign in t.
	 *
	 * @param <T>
	 *            the type parameter
	 * @param landingView
	 *            the landing view
	 * @param clazz
	 *            the clazz
	 * @return the t
	 */
	public <T extends BaseComponent> T signIn(LandingView landingView, String username, String password,
			Class<T> clazz) {

		landingView.skipOnboarding();

		logger.info("Tap Sign In");
		SignInView signInView = landingView.signIn();

		logger.info("Tap on Email Address field and enter valid email address");
		signInView.setUsername(username);

		logger.info("Enter valid password");
		signInView.setPassword(password);

		logger.info("Tap Sign In button");
		return signInView.signIn(clazz);
	}
}
