package com.skronawi.keyvalueservice.core;

import com.skronawi.keyvalueservice.api.KeyValueServiceConfiguration;
import com.skronawi.keyvalueservice.api.KeyValueStore;
import com.skronawi.keyvalueservice.api.KeyValueStoreConfiguration;
import com.skronawi.keyvalueservice.core.keystores.DatabaseKeyValueStore;
import com.skronawi.keyvalueservice.core.keystores.InMemoryKeyValueStore;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class KeyValueStoreFactoryTest {

    //TODO enable, when assertions are integrated
//    @Test(expectedExceptions = IllegalArgumentException.class)
//    public void unknownType() throws Exception {
//        KeyValueStoreFactory.from(null);
//    }

    @DataProvider
    public Object[][] types() {
        return new Object[][]{
                new Object[]{KeyValueServiceConfiguration.KeyValueStoreType.IN_MEMORY, InMemoryKeyValueStore.class},
                new Object[]{KeyValueServiceConfiguration.KeyValueStoreType.DB, DatabaseKeyValueStore.class}
        };
    }

    @Test(dataProvider = "types")
    public void testFactory(KeyValueServiceConfiguration.KeyValueStoreType type, Class clazz) {
        KeyValueStoreConfiguration kvsc = new KeyValueStoreConfiguration();
        kvsc.setKeyValueStoreType(type);
        KeyValueStore keyValueStore = KeyValueStoreFactory.from(kvsc);
        Assert.assertTrue(keyValueStore.getClass().equals(clazz));
    }
}
