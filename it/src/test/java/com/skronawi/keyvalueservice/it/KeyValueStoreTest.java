package com.skronawi.keyvalueservice.it;

import com.skronawi.keyvalueservice.api.KeyNotExistingException;
import com.skronawi.keyvalueservice.api.KeyValueStore;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class KeyValueStoreTest {

    protected KeyValueStore keyValueStore;
    private Map<String, String> keyValues;

    @BeforeMethod
    public void populate() throws Exception {
        keyValues = insertMultiple();
    }

    @AfterMethod
    public void cleanup() {
        keyValueStore.deleteAll();
    }

    @AfterClass(alwaysRun = true)
    public void teardown() throws Exception {
        if (keyValueStore != null) {
            keyValueStore.teardown();
        }
    }

    @Test
    public void get() throws Exception {
        Map.Entry<String, String> entry = keyValues.entrySet().iterator().next();
        Assert.assertEquals(keyValueStore.get(entry.getKey()), entry.getValue());
    }

    @Test
    public void getAll() throws Exception {
        Map<String, String> all = keyValueStore.getAll();
        Assert.assertEquals(all, keyValues);
    }

    @Test
    public void getByPrefix_implicitAll() throws Exception {
        Map<String, String> allByPrefix = keyValueStore.getByPrefix("");
        Assert.assertEquals(allByPrefix, keyValues);
    }

    @Test
    public void getByPrefix_explicitAll() throws Exception {
        Map<String, String> allByPrefix = keyValueStore.getByPrefix("k");
        Assert.assertEquals(allByPrefix, keyValues);
    }

    @Test
    public void getByPrefix_some() throws Exception {
        Map<String, String> allByPrefix = keyValueStore.getByPrefix("k1");
        keyValues.remove("k2");
        Assert.assertEquals(allByPrefix, keyValues);
    }

    @Test
    public void deleteOne() throws Exception {
        keyValueStore.delete("k1");
        try {
            keyValueStore.get("k1");
            Assert.fail();
        } catch (KeyNotExistingException e) {
        }
    }

    @Test
    public void deleteByPrefix_implicitAll() throws Exception {
        keyValueStore.deleteByPrefix("");
        Map<String, String> all = keyValueStore.getAll();
        keyValues.clear();
        Assert.assertEquals(all, keyValues);
    }

    @Test
    public void deleteByPrefix_explicitAll() throws Exception {
        keyValueStore.deleteByPrefix("k");
        Map<String, String> all = keyValueStore.getAll();
        keyValues.clear();
        Assert.assertEquals(all, keyValues);
    }

    @Test
    public void deleteByPrefix_some() throws Exception {
        keyValueStore.deleteByPrefix("k2");
        Map<String, String> all = keyValueStore.getAll();
        keyValues.remove("k2");
        Assert.assertEquals(all, keyValues);
    }

    @Test
    public void deleteAll() throws Exception {
        keyValueStore.deleteAll();
        Assert.assertTrue(keyValueStore.getAll().isEmpty());
    }

    @Test
    public void getKeys() throws Exception {
        Set<String> keys = keyValueStore.getKeys();
        Assert.assertEquals(keys, keyValues.keySet());
    }

    @Test
    public void setOverwritesExistingKeys() throws Exception {
        keyValueStore.set("k1", "otherValue");
        Assert.assertEquals(keyValueStore.get("k1"), "otherValue");
    }

    @Test
    public void howToTestTransactionality() throws Exception {
        //TODO
    }

    private Map<String, String> insertMultiple() {
        Map<String, String> keyValues = new HashMap<>();
        keyValues.put("k1", "v1");
        keyValues.put("k2", "v2");
        keyValueStore.setAll(keyValues);
        return keyValues;
    }
}
