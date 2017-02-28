package com.github.devcat24.config.db;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jdbc.pool.DataSourceProxy;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "barEntityManagerFactory",
        transactionManagerRef = "barTransactionManager",
        basePackages = {
                            "com.github.devcat24.mvc.entity.fi" ,
                            "com.github.devcat24.mvc.repo.fi"
                            // define for both Entity Objects and JpaRepository
                        }
        )
public class BarConfig {
    //private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BarConfig.class);

    @Value("${bar.datasource.jpa.hibernate.dialect}")
    private String hibernateDialect;

    @Value("${bar.datasource.jpa.hibernate.ddl-auto}")
    private String hibernateHbm2ddlAuto;

    @Value("${bar.datasource.jpa.hibernate.show_sql}")
    private String hibernateShowSQL;

    @Value("${bar.datasource.jpa.hibernate.format_sql}")
    private String formatSQL;

    @Bean(name = "barDataSource")
    @ConfigurationProperties(prefix="bar.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @ConditionalOnClass(name = "org.apache.tomcat.jdbc.pool.DataSourceProxy")
    @Bean(name="BarDBJMX")
    public Object dataSourceMBean(@Qualifier("barDataSource") DataSource dataSource) {
        if (dataSource instanceof DataSourceProxy) {
            try {
                return ((DataSourceProxy) dataSource).createPool().getJmxPool();
            }
            catch (SQLException ex) {
                log.warn("Cannot expose DataSource to JMX (could not connect)");
            }
        }
        return null;
    }

    @ConfigurationProperties(prefix="bar.datasource")
    @Bean(name = "barEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        // Creating 'LocalContainerEntityManagerFactoryBean' using 'JpaVendorAdapter'
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        //noinspection RedundantArrayCreation
        emf.setPackagesToScan(new String[] {
                "com.github.devcat24.mvc.entity.fi"
        });
        // define only for the Entity Objects (-> LocalContainerEntityManagerFactoryBean)

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        // Available Options  -> 'org.hibernate.cfg.AvailableSettings.java'
        emf.setJpaVendorAdapter(vendorAdapter);

        Properties prop = new Properties();
        prop.setProperty("hibernate.dialect", hibernateDialect);
        prop.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
        prop.setProperty("hibernate.show_sql", hibernateShowSQL);
        prop.setProperty("hibernate.format_sql", formatSQL);

        emf.setMappingResources("META-INF/orm_bar.xml");
        emf.setJpaProperties(prop);

        return emf;
    }

    @Bean(name = "barTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("barEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
