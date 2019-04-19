package com.applause.auto.pageframework.helpers;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.pageframework.testdata.TestConstants;
import com.applause.auto.pageframework.views.DashboardView;
import com.applause.auto.pageframework.views.LandingView;
import com.applause.auto.pageframework.views.SignInView;
import org.apache.log4j.Logger;

import java.lang.invoke.MethodHandles;

@AndroidImplementation(PeetsMobileHelper.class)
@IosImplementation(PeetsMobileHelper.class)
public class PeetsMobileHelper extends AbstractDeviceView {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().getClass());

    @Override
    protected void waitUntilVisible() {

    }

    public DashboardView signIn(LandingView landingView) {

        landingView.skipOnboarding();

        LOGGER.info("Tap Sign In");
        SignInView signInView = landingView.signIn();

        LOGGER.info("Tap on Email Address field and enter valid email address");
        String username = TestConstants.MyAccountTestData.EMAIL;
        signInView.setUsername(username);

        LOGGER.info("Enter valid password");
        signInView.setPassword(TestConstants.MyAccountTestData.PASSWORD);

        LOGGER.info("Tap Sign In button");
        return signInView.signIn();
    }
}
