package com.skronawi.keyvalueservice.it;

import com.skronawi.keyvalueservice.api.Credentials;
import com.skronawi.keyvalueservice.api.KeyValueServiceConfiguration;
import com.skronawi.keyvalueservice.api.KeyValueStoreConfiguration;
import com.skronawi.keyvalueservice.core.keystores.DatabaseKeyValueStore;

public class DatabaseKeyValueStoreReInitTest extends KeyValueStoreReInitializeTest {

    public static final String DRIVER_CLASS_NAME = "org.hsqldb.jdbcDriver";
    public static final String URL = "jdbc:hsqldb:mem:test";
    public static final String USER = "sa";
    public static final String PASSWORD = "";
    public static final String TABLE = "key_values";

    @Override
    protected void teardown() {
        if (keyValueStore != null) {
            keyValueStore.teardown();
        }
    }

    @Override
    protected void create() {
        keyValueStore = new DatabaseKeyValueStore(keyValueStoreConfig());
    }

    private KeyValueStoreConfiguration keyValueStoreConfig() {
        KeyValueStoreConfiguration kvsc = new KeyValueStoreConfiguration();
        kvsc.setCredentials(new Credentials(USER, PASSWORD));
        kvsc.setKeyValueStoreType(KeyValueServiceConfiguration.KeyValueStoreType.DB);
        kvsc.setDriverClassName(DRIVER_CLASS_NAME);
        kvsc.setUrl(URL);
        kvsc.setName(TABLE);
        return kvsc;
    }
}
