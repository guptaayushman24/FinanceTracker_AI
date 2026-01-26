package com.example.facerecognition.configuration;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Properties;

@Configuration("financetracker")
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "financeTrackerEntityManagerFactory",
        basePackages = {"com.example.financetrackerai.repository"},
        transactionManagerRef = "financetrackerTransactionManager"
)
public class Configfinancetracker {
    @Autowired
    Environment env;

    @Primary
    @Bean(name="financetrackerDataSource")
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(env.getProperty("financetracker.datasource.url"));
        ds.setUsername(env.getProperty("financetracker.datasource.username"));
        ds.setPassword(env.getProperty("financetracker.datasource.password"));
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");

        return ds;
    }

    @Primary
    @Bean(name = "financeTrackerEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManager(){
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource());
        bean.setPackagesToScan("com.example.financetrackerai.model");

        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(adapter);
        HashMap<String,Object> properties = new HashMap<String,Object>();
        properties.put("hibernate.ddl-auto","update");
        properties.put("properties","org.hibernate.dialect.MySQLDialect");
        bean.setJpaPropertyMap(properties);

        return bean;

    }

    @Primary
    @Bean("financetrackerTransactionManager")
    public PlatformTransactionManager transactionManager (@Qualifier("financeTrackerEntityManagerFactory")EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
}