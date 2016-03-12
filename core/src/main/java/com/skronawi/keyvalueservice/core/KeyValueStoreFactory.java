package com.skronawi.keyvalueservice.core;

import com.skronawi.keyvalueservice.api.KeyValueServiceConfiguration;
import com.skronawi.keyvalueservice.api.KeyValueStore;
import com.skronawi.keyvalueservice.api.KeyValueStoreConfiguration;
import com.skronawi.keyvalueservice.core.keystores.DatabaseKeyValueStore;
import com.skronawi.keyvalueservice.core.keystores.InMemoryKeyValueStore;

public class KeyValueStoreFactory {

    public static KeyValueStore from(KeyValueStoreConfiguration keyValueStoreConfiguration) {

        KeyValueServiceConfiguration.KeyValueStoreType keyValueStoreType =
                keyValueStoreConfiguration.getKeyValueStoreType();

        if (keyValueStoreType == KeyValueServiceConfiguration.KeyValueStoreType.IN_MEMORY) {
            return new InMemoryKeyValueStore();
        }
        if (keyValueStoreType == KeyValueServiceConfiguration.KeyValueStoreType.DB) {
            return new DatabaseKeyValueStore(keyValueStoreConfiguration);
        }
        throw new IllegalArgumentException("unknown type: " + keyValueStoreType);
    }
}
