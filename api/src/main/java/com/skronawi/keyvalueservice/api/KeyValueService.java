package com.skronawi.keyvalueservice.api;

public interface KeyValueService {

    LifeCycle getLifeCycle();

    KeyValueStore getKeyValueStore() throws NotInitializedException;
}
