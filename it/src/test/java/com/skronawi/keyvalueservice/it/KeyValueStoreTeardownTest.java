package com.skronawi.keyvalueservice.it;

import com.skronawi.keyvalueservice.api.KeyValueStore;
import com.skronawi.keyvalueservice.api.NotInitializedException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public abstract class KeyValueStoreTeardownTest {

    protected KeyValueStore keyValueStore;

    protected abstract void initKeystore() throws Exception;

    @BeforeMethod
    public void init() throws Exception{
        initKeystore();
        teardownKeystore();
    }

    private void teardownKeystore() {
        keyValueStore.teardown();
    }

    @Test(expectedExceptions = NotInitializedException.class)
    public void get() throws Exception {
        keyValueStore.get("");
    }

    @Test(expectedExceptions = NotInitializedException.class)
    public void getAll() throws Exception {
        keyValueStore.getAll();
    }

    @Test(expectedExceptions = NotInitializedException.class)
    public void getByPrefix_implicitAll() throws Exception {
        keyValueStore.getByPrefix("");
    }

    @Test(expectedExceptions = NotInitializedException.class)
    public void getByPrefix_explicitAll() throws Exception {
        keyValueStore.getByPrefix("");
    }

    @Test(expectedExceptions = NotInitializedException.class)
    public void getByPrefix_some() throws Exception {
        keyValueStore.getByPrefix("");
    }

    @Test(expectedExceptions = NotInitializedException.class)
    public void deleteOne() throws Exception {
        keyValueStore.delete("");
    }

    @Test(expectedExceptions = NotInitializedException.class)
    public void deleteByPrefix() throws Exception {
        keyValueStore.deleteByPrefix("");
    }

    @Test(expectedExceptions = NotInitializedException.class)
    public void deleteAll() throws Exception {
        keyValueStore.deleteAll();
    }

    @Test(expectedExceptions = NotInitializedException.class)
    public void getKeys() throws Exception {
        keyValueStore.getKeys();
    }

    @Test(expectedExceptions = NotInitializedException.class)
    public void set() throws Exception {
        keyValueStore.set("", "");
    }
}
