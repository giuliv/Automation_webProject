package com.applause.auto.reportportal;

import com.epam.reportportal.listeners.ListenerParameters;
import com.epam.reportportal.service.Launch;
import com.epam.reportportal.service.ReportPortal;
import com.epam.reportportal.testng.TestNGService;
import com.epam.reportportal.utils.AttributeParser;
import com.epam.reportportal.utils.properties.PropertiesLoader;
import com.epam.ta.reportportal.ws.model.attribute.ItemAttributesRQ;
import com.epam.ta.reportportal.ws.model.launch.StartLaunchRQ;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class ReportPortalParamOverrideService extends TestNGService {
  private static final Logger logger = LogManager.getLogger(ReportPortalParamOverrideService.class);

  // Environment variables
  private static final String RP_LAUNCH_NAME = "launchName";
  private static final String RP_ATTRIBUTES = "RP_ATTRIBUTES";
  private static final String LOG_TO_RP = "logToRp";
  private static final List<String> RP_LIST_ATTRIBUTES =
      Arrays.asList(
          "device", "environment", "product", "rungroup", "rpruntype", "platformrungroup");

  // Properties
  private static final String RP_CI_BUILD_URL = "BUILD_URL";

  private static final String EXTERNAL_LINK_TEMPLATE =
      "<a target=\"_blank\" rel=\"noopener noreferrer\" title=\"%s\" href=\"%s\">%s</a>";

  public ReportPortalParamOverrideService() {
    super(getLaunchOverriddenProperties());
  }

  private static Supplier<Launch> getLaunchOverriddenProperties() {

    ListenerParameters parameters = new ListenerParameters(PropertiesLoader.load());
    if (!Boolean.parseBoolean(System.getProperty(LOG_TO_RP, "false"))) {
      logger.info("Report Portal logger is disabled");
      parameters.setEnable(false);
      ReportPortal reportPortal = ReportPortal.builder().withParameters(parameters).build();
      return () -> reportPortal.newLaunch(buildStartLaunch(reportPortal.getParameters()));
    }
    final Map<String, String> env = readReportPortalEnvVars(parameters);
    final Map<String, String> props = readSystemProperties();
    if (env.entrySet().stream()
        .allMatch(stringStringEntry -> StringUtils.isEmpty(stringStringEntry.getKey()))) {
      env.put(RP_LAUNCH_NAME, parameters.getLaunchName());
    }

    if (!requiredParametersArePresent(env)) {
      logger.info("Required parameters were not found. Disabling logging to Report Portal");
      parameters.setEnable(false);
    }

    parameters.setLaunchName(env.get(RP_LAUNCH_NAME));
    appendPresentAttributes(parameters, env, props);
    addLinksToCIBuild(parameters, props);

    ReportPortal reportPortal = ReportPortal.builder().withParameters(parameters).build();
    return () -> reportPortal.newLaunch(buildStartLaunch(reportPortal.getParameters()));
  }

  private static void appendPresentAttributes(
      ListenerParameters parameters, Map<String, String> env, Map<String, String> props) {
    Set<ItemAttributesRQ> itemAttributes = parameters.getAttributes();
    itemAttributes.addAll(buildAdditionalAttributes(env.get(RP_ATTRIBUTES)));
    itemAttributes.addAll(
        buildAdditionalAttributes(
            props.entrySet().stream()
                .map(
                    stringStringEntry ->
                        stringStringEntry.getKey() + ":" + stringStringEntry.getValue())
                .collect(Collectors.joining(";"))));
    parameters.setAttributes(itemAttributes);
  }

  private static Set<ItemAttributesRQ> buildAdditionalAttributes(String vars) {
    return AttributeParser.parseAsSet(vars);
  }

  private static StartLaunchRQ buildStartLaunch(ListenerParameters parameters) {
    StartLaunchRQ rq = new StartLaunchRQ();
    rq.setName(parameters.getLaunchName());
    rq.setStartTime(Calendar.getInstance().getTime());
    rq.setAttributes(parameters.getAttributes());
    rq.setMode(parameters.getLaunchRunningMode());
    if (!Strings.isNullOrEmpty(parameters.getDescription())) {
      rq.setDescription(parameters.getDescription());
    }

    return rq;
  }

  private static Map<String, String> readReportPortalEnvVars(ListenerParameters parameters) {
    Map<String, String> result = new HashMap<>();
    result.put(
        RP_LAUNCH_NAME,
        StringUtils.isEmpty(System.getProperty(RP_LAUNCH_NAME))
            ? parameters.getLaunchName()
            : System.getProperty(RP_LAUNCH_NAME));
    return result;
  }

  private static Map<String, String> readSystemProperties() {
    Map<String, String> result = new HashMap<>();
    RP_LIST_ATTRIBUTES.stream()
        .forEach(
            attr -> {
              if (StringUtils.isNotEmpty(System.getProperty(attr))) {
                result.put(attr, System.getProperty(attr));
              }
            });
    return result;
  }

  private static boolean requiredParametersArePresent(Map<String, String> params) {
    return isNotEmpty(params.get(RP_LAUNCH_NAME));
  }

  private static void addLinksToCIBuild(ListenerParameters parameters, Map<String, String> props) {
    String description = buildExternalLinks(props);
    if (isNotEmpty(description)) {
      parameters.setDescription(description);
    }
  }

  private static String buildExternalLinks(Map<String, String> props) {
    final String ciBuildUrl = props.get(RP_CI_BUILD_URL);

    final StringJoiner stringJoiner = new StringJoiner(" - ");
    if (isNotEmpty(ciBuildUrl)) {
      stringJoiner.add(buildLink("CI build", ciBuildUrl));
    }
    return stringJoiner.toString();
  }

  private static String buildLink(String name, String url) {
    return String.format(EXTERNAL_LINK_TEMPLATE, name, url, name);
  }
}
