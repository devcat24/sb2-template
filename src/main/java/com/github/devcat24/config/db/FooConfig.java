package com.github.devcat24.config.db;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jdbc.pool.DataSourceProxy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
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
        entityManagerFactoryRef = "fooEntityManagerFactory",
        transactionManagerRef = "fooTransactionManager",
        basePackages = {
                            "com.github.devcat24.mvc.entity.hr" ,
                            "com.github.devcat24.mvc.repo.hr",
                            "com.github.devcat24.mvc.entity.mm",
                            "com.github.devcat24.mvc.repo.mm"
                            // define for both Entity Objects and JpaRepository
                        }
        )
public class FooConfig {
    //private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FooConfig.class);

    @Value("${foo.datasource.jpa.hibernate.dialect}")
    private String hibernateDialect;

    @Value("${foo.datasource.jpa.hibernate.ddl-auto}")
    private String hibernateHbm2ddlAuto;

    @Value("${foo.datasource.jpa.hibernate.show_sql}")
    private String hibernateShowSQL;

    @Value("${foo.datasource.jpa.hibernate.format_sql}")
    private String formatSQL;


    @Primary
    @Bean(name = "fooDataSource")
    @ConfigurationProperties(prefix="foo.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
    // JMX for Tomcat Connection Pool
    //  1. using 'spring.datasource.jmx-enabled=true' in application.properties
    //     only works for the connection which has 'spring.datasource' prefix
    //     (refer to 'org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration')
    //  2. to create & register 'multiple datasource connection pools' in a spring boot application
    //     it is required to change 'spring.datasource' -> cannot use 'spring.datasource' property
    //  3. to solve the problem, it is required to create custom JMX Bean.
    //  4. to distinguish datasources, '@Qualifier' can be used.
    @ConditionalOnClass(name = "org.apache.tomcat.jdbc.pool.DataSourceProxy")
    @Bean(name="FooDBJMX")
    public Object dataSourceMBean(@Qualifier("fooDataSource") DataSource dataSource) {
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

/*  // --> bind each *DAO
        ==> https://docs.spring.io/spring/docs/current/spring-framework-reference/html/jdbc.html
   @Primary
    @Bean(name = "fooJDBCTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("fooDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }*/


    @Primary
    // '@Primary' annotation should declared only once in a project
    @Bean(name = "fooEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @SuppressWarnings("SpringJavaAutowiringInspection") EntityManagerFactoryBuilder builder,
            @Qualifier("fooDataSource") DataSource dataSource) {
        // When  more than two datasources required,using '@ConfigurationProperties(prefix="bar.datasource")' annotation does not work properly.
        // To create 'EntityManager(LocalContainerEntityManagerFactoryBean)', there are two common ways.
        // In both ways, setting property with 'Properties' or 'HashMap<String, Object>' is required


        // case1. using JpaVendorAdapter
        //noinspection RedundantArrayCreation
        String [] scanPkg = new String[] {
                "com.github.devcat24.mvc.entity.hr",
                "com.github.devcat24.mvc.entity.mm"
        };

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan(scanPkg);
        // define only for the Entity Objects (-> LocalContainerEntityManagerFactoryBean)

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        // Available Options  -> 'org.hibernate.cfg.AvailableSettings.java'
        emf.setJpaVendorAdapter(vendorAdapter);

        Properties prop = new Properties();
        prop.setProperty("hibernate.dialect", hibernateDialect);
        prop.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
        prop.setProperty("hibernate.show_sql", hibernateShowSQL);
        prop.setProperty("hibernate.format_sql", formatSQL);
        // prop.setProperty("hibernate.generate_statistics", genStatistics);
        //    prop.setProperty("hibernate.default_catalog", showSQL);
        //    prop.setProperty("hibernate.default_schema", showSQL);
        //
        //    prop.setProperty("hibernate.jmx.enabled", "true");
        //    prop.setProperty("hibernate.jmx.usePlatformServer", "true");
        //    prop.setProperty("hibernate.jmx.agentId", "MySQLJMX");
        //    prop.setProperty("hibernate.jmx.defaultDomain", "Spring.JPA");
        //    DataSource related JMX is not working !!!

        emf.setMappingResources("META-INF/orm_foo.xml", "META-INF/orm_foo2.xml");
        // emf.setMappingResources("META-INF/orm_foo.xml", "META-INF/orm_foo2.xml");

        emf.setJpaProperties(prop);
        emf.setPersistenceUnitName("fooPersistence");

        return emf;


        /* // case2. Creating 'LocalContainerEntityManagerFactoryBean' using 'EntityManagerFactoryBuilder'
        String [] scanPkg = new String[] {
                "com.github.devcat24.mvc.entity.fi"
                // define only for the Entity Objects (-> LocalContainerEntityManagerFactoryBean)
        };
        Map<String, Object> jpaProp = new HashMap<>();
        jpaProp.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        jpaProp.put("hibernate.hbm2ddl.auto", "create-drop");
        jpaProp.put("hibernate.show_sql", "true");
        jpaProp.put("hibernate.format_sql", "true");

        return builder
                .dataSource(dataSource)
                .packages(scanPkg)
                .properties(jpaProp)
                //.persistenceUnit("bar")
                .build();
        */
    }

    @Primary
    @Bean(name = "fooTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("fooEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
