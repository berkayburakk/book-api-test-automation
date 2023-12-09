package com.presentation.config;

import org.aeonbits.owner.ConfigCache;
import org.aeonbits.owner.ConfigFactory;

public class ConfigurationManager {

    private static final String ENVIRONMENT = "environment";
    private static final String DEFAULT_ENVIRONMENT = "env";

    private ConfigurationManager() {
    }

    public static Configuration getConfiguration() {
        setSystemProperties();
        return ConfigCache.getOrCreate(Configuration.class);
    }

    private static void setSystemProperties() {
        setSystemProperty(ENVIRONMENT, DEFAULT_ENVIRONMENT);
    }

    private static void setSystemProperty(String key, String defaultValue) {
        String value = System.getProperty(key);
        String actualValue = value == null ? defaultValue : value;

        System.setProperty(key, actualValue);
        ConfigFactory.setProperty(key, actualValue);
    }


}
