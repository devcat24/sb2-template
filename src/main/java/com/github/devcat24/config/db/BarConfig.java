//        package com.github.devcat24.config.db;
//
//        import org.springframework.beans.factory.annotation.Qualifier;
//        import org.springframework.beans.factory.annotation.Value;
//        import org.springframework.boot.context.properties.ConfigurationProperties;
//        import org.springframework.boot.jdbc.DataSourceBuilder;
//        import org.springframework.context.annotation.Bean;
//        import org.springframework.context.annotation.Configuration;
//        import org.springframework.context.annotation.EnableMBeanExport;
//        import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//        import org.springframework.jmx.support.RegistrationPolicy;
//        import org.springframework.orm.jpa.JpaTransactionManager;
//        import org.springframework.orm.jpa.JpaVendorAdapter;
//        import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//        import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//        import org.springframework.transaction.PlatformTransactionManager;
//        import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//        import javax.persistence.EntityManagerFactory;
//        import javax.sql.DataSource;
//        import java.util.Properties;
//
//        //import org.springframework.jmx.export.MBeanExporter;
//        //import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//
//        @Configuration
//        @EnableTransactionManagement
//        @EnableJpaRepositories(
//                entityManagerFactoryRef = "barEntityManagerFactory",
//                transactionManagerRef = "barTransactionManager",
//                basePackages = {
//                        "com.github.devcat24.mvc.db.entity.fi" ,
//                        "com.github.devcat24.mvc.db.repo.fi"
//                        // define for both Entity Objects and JpaRepository
//                }
//        )
//        @EnableMBeanExport(registration= RegistrationPolicy.IGNORE_EXISTING)
//        public class BarConfig {
//            //private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BarConfig.class);
//            @Value("${bar.datasource.hikari.jpa.hibernate.dialect}")
//            private String hibernateDialect;
//
//            @Value("${bar.datasource.hikari.jpa.hibernate.ddl-auto}")
//            private String hibernateHbm2ddlAuto;
//
//            @Value("${bar.datasource.hikari.jpa.hibernate.show_sql}")
//            private String hibernateShowSQL;
//
//            @Value("${bar.datasource.hikari.jpa.hibernate.format_sql}")
//            private String formatSQL;
//
//            @Bean(name = "barDataSource")
//            @ConfigurationProperties(prefix="bar.datasource.hikari")
//            public DataSource dataSource() {
//                return DataSourceBuilder.create().build();
//            }
//
//            //@SuppressWarnings("Duplicates")
//            @SuppressWarnings("Duplicates")
//            @ConfigurationProperties(prefix="bar.datasource")
//            @Bean(name = "barEntityManagerFactory")
//            public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//
//                // Creating 'LocalContainerEntityManagerFactoryBean' using 'JpaVendorAdapter'
//                LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//                emf.setDataSource(dataSource());
//                //noinspection RedundantArrayCreation
//                emf.setPackagesToScan(new String[] {
//                        "com.github.devcat24.mvc.svc.db.entity.fi"
//                });
//                // define only for the Entity Objects (-> LocalContainerEntityManagerFactoryBean)
//
//                JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//                // Available Options  -> 'org.hibernate.cfg.AvailableSettings.java'
//
//                Properties prop = new Properties();
//                prop.setProperty("hibernate.dialect", hibernateDialect);
//                // prop.setProperty("hibernate.hbm2ddl.auto", "validate");   // <- can reduce operational mistake !
//                prop.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
//                prop.setProperty("hibernate.show_sql", hibernateShowSQL);
//                prop.setProperty("hibernate.format_sql", formatSQL);
//
//                emf.setJpaVendorAdapter(vendorAdapter);
//                emf.setMappingResources("META-INF/orm_bar.xml");
//                emf.setJpaProperties(prop);
//
//                return emf;
//            }
//            @Bean(name = "barTransactionManager")
//            public PlatformTransactionManager transactionManager(
//                    @Qualifier("barEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//                return new JpaTransactionManager(entityManagerFactory);
//            }
//
//
//
//            /*  // Do not used following MBeanExporter
//                      -> can be problem in multiple datasource (Hikari connection pool)
//                      -> instead, add 'spring.jmx.default-domain=sb2-jmx' to 'application.properties'
//
//            // JMX for Hikari Connection Pool
//            //   -> Hikari uses two JMXBeans 'Pool' & 'PoolConfig',
//            //      Spring autoconfig tries to register these two things twice with same name -> makes startup failure
//            //   -> https://github.com/brettwooldridge/HikariCP/wiki/MBean-(JMX)-Monitoring-and-Management
//            //   -> This method prevents Spring tries to register JMXBean twice
//            @ConditionalOnClass(name="com.zaxxer.hikari.HikariDataSource")
//            @Bean("BarMBeanExporter")
//            public MBeanExporter exporter() {
//                final MBeanExporter exporter = new MBeanExporter();
//                // Spring Boot register hikari JMXBean automatically
//                //    -> if 'spring.datasource.hikari.registerMbeans=true' used, it makes duplication error on start-up
//                exporter.setExcludedBeans( "barDataSource");
//                return exporter;
//            }
//            */
//
//        }
