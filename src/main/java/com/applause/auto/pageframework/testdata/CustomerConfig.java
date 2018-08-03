package com.applause.auto.pageframework.testdata;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.util.environment.EnvironmentUtil;
import com.applause.auto.framework.pageframework.util.environment.s3.S3Utility;
import com.applause.auto.framework.pageframework.util.logger.LogController;

/**
 * This class sets up all relevant customer configuration for reporting to the Applause Automation Dashboard.
 */
public class CustomerConfig {

	private static final LogController LOGGER = new LogController(MethodHandles.lookup().getClass());
	private static EnvironmentUtil env = EnvironmentUtil.getInstance();
	private static S3Utility s3;

	private static String productIdWeb = "16503";
	private static String productIdMobileWeb = "16506";
	private static String buildIdWeb = "108000";
	private static String buildIdMobileWeb = "108006";
	private static String idCompany = "4333";

	static {
		s3 = S3Utility.getInstance();
		
		env.setProductWebId(productIdWeb);
		env.setProductMobileWebId(productIdMobileWeb);

		env.setCustomerS3BucketName(idCompany);
		env.setCompanyId(idCompany);

		if (!s3.doesCustomerBucketExist()) {
			s3.createCustomerBucket();
		}

		if (env.getIsMobileWebTest()) {
			env.setBuildId(buildIdMobileWeb);
		} else {
			env.setBuildId(buildIdWeb);
		}

		LOGGER.info("Customer configuration complete.");
	}
}
