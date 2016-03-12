package com.skronawi.keyvalueservice.api;

public class KeyValueStoreConfiguration {

    private KeyValueServiceConfiguration.KeyValueStoreType keyValueStoreType;
    private String driverClassName;
    private Credentials credentials;
    private String url;
    private String name;

    public KeyValueServiceConfiguration.KeyValueStoreType getKeyValueStoreType() {
        return keyValueStoreType;
    }

    public void setKeyValueStoreType(KeyValueServiceConfiguration.KeyValueStoreType keyValueStoreType) {
        this.keyValueStoreType = keyValueStoreType;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //TODO use
    public void assertValid() {
        assert keyValueStoreType != null;
    }

    @Override
    public String toString() {
        return "KeyValueStoreConfiguration{" +
                "keyValueStoreType=" + keyValueStoreType +
                ", driverClassName='" + driverClassName + '\'' +
                ", credentials=" + credentials +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
