package com.skronawi.keyvalueservice.core;

import com.skronawi.keyvalueservice.api.AlreadyInitializedException;
import com.skronawi.keyvalueservice.api.KeyValueServiceConfiguration;
import com.skronawi.keyvalueservice.api.KeyValueStore;
import com.skronawi.keyvalueservice.api.NotInitializedException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LifeCycleTest {

    @Test
    public void lifeCycleTest() throws Exception {

        LifeCycleImpl lifeCycle = new LifeCycleImpl();

        Assert.assertFalse(lifeCycle.isInitialized());
        try {
            lifeCycle.getKeyValueStore();
            Assert.fail();
        } catch (NotInitializedException e) {
            //correct
        }

        try {
            lifeCycle.getServiceConfiguration();
            Assert.fail();
        } catch (NotInitializedException e) {
            //correct
        }

        KeyValueServiceConfiguration keyValueServiceConfiguration = keyValueServiceConfiguration();
        lifeCycle.init(keyValueServiceConfiguration);

        Assert.assertTrue(lifeCycle.isInitialized());
        Assert.assertNotNull(lifeCycle.getKeyValueStore());

        lifeCycle.teardown();

        Assert.assertFalse(lifeCycle.isInitialized());
        try {
            lifeCycle.getKeyValueStore();
            Assert.fail();
        } catch (NotInitializedException e) {
            //correct
        }
        try {
            lifeCycle.getServiceConfiguration();
            Assert.fail();
        } catch (NotInitializedException e) {
            //correct
        }
    }

    @Test
    public void teardownWithoutSetupTest() throws Exception {
        LifeCycleImpl lifeCycle = new LifeCycleImpl();
        lifeCycle.teardown();
    }

    @Test
    public void getConfig() throws Exception {

        LifeCycleImpl lifeCycle = new LifeCycleImpl();

        KeyValueServiceConfiguration keyValueServiceConfiguration = keyValueServiceConfiguration();
        lifeCycle.init(keyValueServiceConfiguration);

        Assert.assertEquals(lifeCycle.getServiceConfiguration(), keyValueServiceConfiguration);
    }

    @Test
    public void reInitNotPossible() throws Exception {

        LifeCycleImpl lifeCycle = new LifeCycleImpl();

        KeyValueServiceConfiguration keyValueServiceConfiguration = keyValueServiceConfiguration();
        lifeCycle.init(keyValueServiceConfiguration);

        try {
            lifeCycle.init(keyValueServiceConfiguration);
            Assert.fail();
        } catch (AlreadyInitializedException e) {
            //ok
        }

        //also after teardown
        lifeCycle.teardown();

        try {
            lifeCycle.init(keyValueServiceConfiguration);
            Assert.fail();
        } catch (AlreadyInitializedException e) {
            //ok
        }
    }

    @Test
    public void tearDownedLifeCycleDoesNotProvideKeystore() throws Exception {

        LifeCycleImpl lifeCycle = new LifeCycleImpl();

        KeyValueServiceConfiguration keyValueServiceConfiguration = keyValueServiceConfiguration();
        lifeCycle.init(keyValueServiceConfiguration);

        lifeCycle.teardown();

        try {
            lifeCycle.getKeyValueStore();
            Assert.fail();
        } catch (NotInitializedException e) {
            //ok
        }
    }

    @Test
    public void tearDownedLifeCycleDoesNotProvideConfig() throws Exception {

        LifeCycleImpl lifeCycle = new LifeCycleImpl();

        KeyValueServiceConfiguration keyValueServiceConfiguration = keyValueServiceConfiguration();
        lifeCycle.init(keyValueServiceConfiguration);

        lifeCycle.teardown();

        try {
            lifeCycle.getServiceConfiguration();
            Assert.fail();
        } catch (NotInitializedException e) {
            //ok
        }
    }

    @Test
    public void lifeCycleTearDownIncludesKeystore() throws Exception {

        LifeCycleImpl lifeCycle = new LifeCycleImpl();

        KeyValueServiceConfiguration keyValueServiceConfiguration = keyValueServiceConfiguration();
        lifeCycle.init(keyValueServiceConfiguration);

        KeyValueStore keyValueStore = lifeCycle.getKeyValueStore();
        keyValueStore.getKeys();

        lifeCycle.teardown();

        try {
            keyValueStore.getKeys();
            Assert.fail();
        } catch (NotInitializedException e) {
            //ok
        }
    }

    private KeyValueServiceConfiguration keyValueServiceConfiguration() {
        KeyValueServiceConfiguration keyValueServiceConfiguration = new KeyValueServiceConfiguration();
        keyValueServiceConfiguration.getKeyValueStoreConfiguration().setKeyValueStoreType(
                KeyValueServiceConfiguration.KeyValueStoreType.IN_MEMORY);
        return keyValueServiceConfiguration;
    }
}
