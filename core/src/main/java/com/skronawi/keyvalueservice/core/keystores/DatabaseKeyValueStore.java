package com.skronawi.keyvalueservice.core.keystores;

import com.skronawi.keyvalueservice.api.*;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class DatabaseKeyValueStore extends AbstractKeyValueStore {

    private final KeyValueStoreConfiguration keyValueStoreConfiguration;
    private DatabaseKeyValueRepository databaseKeyValueRepository;

    public DatabaseKeyValueStore(KeyValueStoreConfiguration keyValueStoreConfiguration) {
        this.keyValueStoreConfiguration = keyValueStoreConfiguration;
    }

    @Override
    public String get(String key) throws KeyNotExistingException, NotInitializedException {
        assertInitialized();
        return databaseKeyValueRepository.get(key);
    }

    @Override
    public Set<String> getKeys() throws NotInitializedException {
        assertInitialized();
        return databaseKeyValueRepository.getKeys();
    }

    @Override
    public Map<String, String> getAll() throws NotInitializedException {
        assertInitialized();
        return databaseKeyValueRepository.getAll();
    }

    @Override
    public Map<String, String> getByPrefix(String keyPrefix) throws NotInitializedException {
        assertInitialized();
        return databaseKeyValueRepository.getByPrefix(keyPrefix);
    }

    @Override
    public void set(String key, String value) throws NotInitializedException {
        assertInitialized();
        databaseKeyValueRepository.set(key, value);
    }

    @Override
    public void setAll(Map<String, String> keyValues) throws NotInitializedException {
        assertInitialized();
        databaseKeyValueRepository.setAll(keyValues);
    }

    @Override
    public void delete(String key) throws NotInitializedException {
        assertInitialized();
        databaseKeyValueRepository.delete(key);
    }

    @Override
    public void deleteByPrefix(String keyPrefix) throws NotInitializedException {
        assertInitialized();
        databaseKeyValueRepository.deleteByPrefix(keyPrefix);
    }

    @Override
    public void deleteAll() throws NotInitializedException {
        assertInitialized();
        databaseKeyValueRepository.deleteAll();
    }

    @Override
    public void init() throws AlreadyInitializedException {
        super.init();
        check(keyValueStoreConfiguration);
        Properties properties = new Properties();
        properties.put("kvsc", keyValueStoreConfiguration);
        databaseKeyValueRepository = DatabaseKeyValueRepositoryConfig.createContext(properties)
                .getBean(DatabaseKeyValueRepository.class);
    }

    private void check(KeyValueStoreConfiguration keyValueStoreConfiguration) {
        assert keyValueStoreConfiguration != null;
        assert keyValueStoreConfiguration.getKeyValueStoreType() == KeyValueServiceConfiguration.KeyValueStoreType.DB;
        assert keyValueStoreConfiguration.getName() != null && keyValueStoreConfiguration.getName().length() > 0;
        assert keyValueStoreConfiguration.getCredentials() != null;
//        assert keyValueStoreConfiguration.getCredentials().getPassword() != null
//                && keyValueStoreConfiguration.getCredentials().getPassword().length() > 0;
        assert keyValueStoreConfiguration.getCredentials().getUsername() != null
                && keyValueStoreConfiguration.getCredentials().getUsername().length() > 0;
        assert keyValueStoreConfiguration.getDriverClassName() != null
                && keyValueStoreConfiguration.getDriverClassName().length() > 0;
        assert keyValueStoreConfiguration.getUrl() != null && keyValueStoreConfiguration.getUrl().length() > 0;
    }

    @Override
    public void teardown() {
        super.teardown();
        if (databaseKeyValueRepository != null) {
            databaseKeyValueRepository.teardown();
        }
    }
}
