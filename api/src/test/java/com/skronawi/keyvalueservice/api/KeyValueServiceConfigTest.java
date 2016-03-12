package com.skronawi.keyvalueservice.api;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

public class KeyValueServiceConfigTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void unknownType() throws Exception {
        KeyValueServiceConfiguration.KeyValueStoreType.from("notExisting");
    }

    @DataProvider
    public static Object[][] ignoreCases() {
        return new Object[][]{
                new Object[]{KeyValueServiceConfiguration.KeyValueStoreType.IN_MEMORY.getType().toLowerCase(),
                        KeyValueServiceConfiguration.KeyValueStoreType.IN_MEMORY},
                new Object[]{KeyValueServiceConfiguration.KeyValueStoreType.IN_MEMORY.getType().toUpperCase(),
                        KeyValueServiceConfiguration.KeyValueStoreType.IN_MEMORY},
        };
    }

    @Test(dataProvider = "ignoreCases")
    public void fromIgnoresCase(String name, KeyValueServiceConfiguration.KeyValueStoreType type) throws Exception {
        Assert.assertEquals(KeyValueServiceConfiguration.KeyValueStoreType.from(name), type);
    }

    @Test
    public void builderFromMap() throws Exception {

        HashMap<String, String> properties = new HashMap<>();
        properties.put("keyvalueservice.store.type", "inmemory");
        properties.put("keyvalueservice.store.credentials", "simon:says");
        properties.put("keyvalueservice.store.driverClassName", "postgre");
        properties.put("keyvalueservice.store.url", "loc");
        //add other properties

        KeyValueServiceConfiguration kvsc = KeyValueServiceConfiguration.Builder.from(properties);
        Assert.assertNotNull(kvsc);
        Assert.assertNotNull(kvsc.getKeyValueStoreConfiguration().getCredentials());
        Assert.assertEquals(kvsc.getKeyValueStoreConfiguration().getCredentials().getUsername(), "simon");
        Assert.assertEquals(kvsc.getKeyValueStoreConfiguration().getCredentials().getPassword(), "says");
        Assert.assertEquals(kvsc.getKeyValueStoreConfiguration().getKeyValueStoreType(),
                KeyValueServiceConfiguration.KeyValueStoreType.IN_MEMORY);
        Assert.assertEquals(kvsc.getKeyValueStoreConfiguration().getDriverClassName(), "postgre");
        Assert.assertEquals(kvsc.getKeyValueStoreConfiguration().getUrl(), "loc");
    }

    @Test
    public void builderOmitsWrongKeyValueServicePrefix() throws Exception {

        HashMap<String, String> properties = new HashMap<>();
        properties.put("keyvalueservice_wrong.store.type", "inmemory");

        KeyValueServiceConfiguration kvsc = KeyValueServiceConfiguration.Builder.from(properties);
        Assert.assertNotNull(kvsc);
        Assert.assertNull(kvsc.getKeyValueStoreConfiguration().getKeyValueStoreType());
    }

    @Test
    public void builderOmitsWrongStorePrefix() throws Exception {

        HashMap<String, String> properties = new HashMap<>();
        properties.put("keyvalueservice.store_wrong.type", "inmemory");

        KeyValueServiceConfiguration kvsc = KeyValueServiceConfiguration.Builder.from(properties);
        Assert.assertNotNull(kvsc);
        Assert.assertNull(kvsc.getKeyValueStoreConfiguration().getKeyValueStoreType());
    }

    //TODO enable, when assertions are integrated
//    @Test(expectedExceptions = IllegalArgumentException.class)
//    public void testCredentials() throws Exception {
//
//        HashMap<String, String> properties = new HashMap<>();
//        properties.put("keyvalueservice.store.credentials", "no_colon");
//
//        KeyValueServiceConfiguration.Builder.from(properties);
//    }
}
