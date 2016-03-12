package com.skronawi.keyvalueservice.it;

import com.skronawi.keyvalueservice.api.AlreadyInitializedException;
import com.skronawi.keyvalueservice.api.KeyValueStore;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public abstract class KeyValueStoreReInitializeTest {

    protected KeyValueStore keyValueStore;

    @BeforeMethod
    public void createKeyValueStore() throws Exception {
        create();
    }

    @AfterMethod(alwaysRun = true)
    public void teardownKeyValueStore() throws Exception {
        teardown();
    }

    protected abstract void teardown();

    protected abstract void create();

    @Test
    public void reInitializeWithoutTeardown() throws Exception {

        keyValueStore.init();

        try {
            keyValueStore.init();
            Assert.fail();
        } catch (AlreadyInitializedException e) {
            //ok
        }
    }

    @Test
    public void reInitializeWithTeardown() throws Exception {

        keyValueStore.init();

        keyValueStore.teardown();

        try {
            keyValueStore.init();
            Assert.fail();
        } catch (AlreadyInitializedException e) {
            //ok
        }
    }
}
