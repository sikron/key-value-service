package com.skronawi.keyvalueservice.api;

//a configuration for a in-memory key-value service
public class DefaultKeyValueServiceConfiguration extends KeyValueServiceConfiguration {

    private final KeyValueStoreConfiguration keyValueStoreConfiguration;

    public DefaultKeyValueServiceConfiguration() {
        keyValueStoreConfiguration = new KeyValueStoreConfiguration();
        keyValueStoreConfiguration.setKeyValueStoreType(KeyValueStoreType.IN_MEMORY);
    }

    @Override
    public KeyValueStoreConfiguration getKeyValueStoreConfiguration() {
        return keyValueStoreConfiguration;
    }
}
