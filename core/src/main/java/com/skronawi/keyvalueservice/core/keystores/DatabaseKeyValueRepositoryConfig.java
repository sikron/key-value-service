package com.skronawi.keyvalueservice.core.keystores;

import com.skronawi.keyvalueservice.api.KeyValueStoreConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Component
@EnableTransactionManagement
public class DatabaseKeyValueRepositoryConfig {

    //does not work -> error "cannot convert from String to KeyValueStoreConfiguration (or vice-versa)"
//    @Value("${kvs.kvsc}")
//    private KeyValueStoreConfiguration keyValueStoreConfiguration;

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        KeyValueStoreConfiguration keyValueStoreConfiguration = keyValueStoreConfiguration();
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(keyValueStoreConfiguration.getDriverClassName());
        driverManagerDataSource.setPassword(keyValueStoreConfiguration.getCredentials().getPassword());
        driverManagerDataSource.setUsername(keyValueStoreConfiguration.getCredentials().getUsername());
        driverManagerDataSource.setUrl(keyValueStoreConfiguration.getUrl());
        return driverManagerDataSource;
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DatabaseKeyValueRepository databaseKeyValueRepository() {
        KeyValueStoreConfiguration keyValueStoreConfiguration = keyValueStoreConfiguration();
        return new DatabaseKeyValueRepository(keyValueStoreConfiguration);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public KeyValueStoreConfiguration keyValueStoreConfiguration() {
        return env.getProperty("kvsc", KeyValueStoreConfiguration.class);
    }

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }

    //https://kubosj.wordpress.com/2015/07/31/add-dynamic-properties-into-spring-using-annotations/
    public static AnnotationConfigApplicationContext createContext(Properties properties) {
        PropertiesPropertySource propertiesSource = new PropertiesPropertySource("kvs", properties);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().getPropertySources().addLast(propertiesSource);
        context.register(DatabaseKeyValueRepositoryConfig.class);
        context.refresh();
        return context;
    }
}
