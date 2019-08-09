package com.applause.auto.test.mobile.helpers;

import java.lang.invoke.MethodHandles;

import org.apache.log4j.Logger;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.common.data.TestConstants;
import com.applause.auto.mobile.views.DashboardView;
import com.applause.auto.mobile.views.LandingView;
import com.applause.auto.mobile.views.SignInView;

@AndroidImplementation(TestHelper.class)
@IosImplementation(TestHelper.class)
public class TestHelper extends AbstractDeviceView {
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {

	}

	/**
	 * Sign in dashboard view.
	 *
	 * @param landingView
	 *            the landing view
	 * @return the dashboard view
	 */
	public DashboardView signIn(LandingView landingView) {
		return signIn(landingView, TestConstants.MyAccountTestData.EMAIL, TestConstants.MyAccountTestData.PASSWORD,
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
	public <T extends AbstractDeviceView> T signIn(LandingView landingView, String username, String password,
			Class<T> clazz) {

		landingView.skipOnboarding();

		LOGGER.info("Tap Sign In");
		SignInView signInView = landingView.signIn();

		LOGGER.info("Tap on Email Address field and enter valid email address");
		signInView.setUsername(username);

		LOGGER.info("Enter valid password");
		signInView.setPassword(password);

		LOGGER.info("Tap Sign In button");
		return signInView.signIn(clazz);
	}
}
