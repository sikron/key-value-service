package com.skronawi.keyvalueservice.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class KeyValueServiceConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyValueServiceConfiguration.class);

    public enum KeyValueStoreType {
        IN_MEMORY("inmemory"),
        DB("database");

        private final String type;

        KeyValueStoreType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public static KeyValueStoreType from(String type) {
            for (KeyValueStoreType keyValueStoreType : KeyValueStoreType.values()) {
                if (keyValueStoreType.getType().equalsIgnoreCase(type)) {
                    return keyValueStoreType;
                }
            }
            throw new IllegalArgumentException("unknown type: " + type);
        }
    }

    public static class Builder {

        /*
        keyvalueservice.store.type=inmemory
        keyvalueservice.store.url=...
        */

        public static final String KEYVALUESERVICE = "keyvalueservice";
        public static final String STORE = "store";

        public static final String TYPE = "type";
        public static final String DRIVER_CLASS_NAME = "driverClassName";
        public static final String CREDENTIALS = "credentials";
        public static final String URL = "url";

        private static final String KEYVALUESERVICE_PREFIX = KEYVALUESERVICE + ".";
        private static final String KEYVALUESERVICE_STORE_PREFIX = KEYVALUESERVICE + "." + STORE + ".";

        private Builder() {
        }

        public static KeyValueServiceConfiguration from(Map<String, String> keyValueStoreKeyValueConfiguration) {

            final KeyValueServiceConfiguration keyValueServiceConfiguration = new KeyValueServiceConfiguration();

            keyValueStoreKeyValueConfiguration.entrySet().forEach(entry -> {
                String key = entry.getKey();
                String value = entry.getValue();

                LOGGER.debug("handling now: " + key + " : " + value);

                if (!key.startsWith(KEYVALUESERVICE_PREFIX)) {
                    LOGGER.debug("omitting key: " + key);
                    return;
                }

                if (key.startsWith(KEYVALUESERVICE_STORE_PREFIX)) {
                    handleStoreParam(key, value, keyValueServiceConfiguration);
                }
            });

            LOGGER.debug("resulting keyValueStoreConfiguration: " + keyValueServiceConfiguration);

            return keyValueServiceConfiguration;
        }

        private static void handleStoreParam(String key, String value, KeyValueServiceConfiguration kvsc) {
            if (key.equals(KEYVALUESERVICE_STORE_PREFIX + TYPE)) {
                kvsc.getKeyValueStoreConfiguration().setKeyValueStoreType(KeyValueStoreType.from(value));
            }
            if (key.equals(KEYVALUESERVICE_STORE_PREFIX + DRIVER_CLASS_NAME)) {
                kvsc.getKeyValueStoreConfiguration().setDriverClassName(value);
            }
            if (key.equals(KEYVALUESERVICE_STORE_PREFIX + CREDENTIALS)) {
                String[] usernamePassword = value.split(":");
                kvsc.getKeyValueStoreConfiguration().setCredentials(new Credentials(usernamePassword[0], usernamePassword[1]));
            }
            if (key.equals(KEYVALUESERVICE_STORE_PREFIX + URL)) {
                kvsc.getKeyValueStoreConfiguration().setUrl(value);
            }
        }
    }

    private KeyValueStoreConfiguration keyValueStoreConfiguration;

    public KeyValueServiceConfiguration() {
        keyValueStoreConfiguration = new KeyValueStoreConfiguration();
    }

    public KeyValueStoreConfiguration getKeyValueStoreConfiguration() {
        return keyValueStoreConfiguration;
    }

    public void setKeyValueStoreConfiguration(KeyValueStoreConfiguration keyValueStoreConfiguration) {
        this.keyValueStoreConfiguration = keyValueStoreConfiguration;
    }
}
