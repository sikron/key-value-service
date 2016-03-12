package com.skronawi.keyvalueservice.api;

public interface LifeCycle {

    void init(KeyValueServiceConfiguration keyValueServiceConfiguration) throws AlreadyInitializedException;

    void teardown();

    boolean isInitialized();

    KeyValueServiceConfiguration getServiceConfiguration() throws NotInitializedException;
}
