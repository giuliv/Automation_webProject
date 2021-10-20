package com.applause.auto.configuration;

import org.aeonbits.owner.ConfigFactory;

public class ExecutionConfigurationOwner {

  public static ExecutionConfiguration executionConfiguration =
      ConfigFactory.create(ExecutionConfiguration.class, System.getProperties(), System.getenv());
}
