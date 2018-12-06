package com.profsoft.smsnotifications.model.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author filips
 */
@Configuration
@EnableTransactionManagement
@ComponentScan({"com.profsoft.smsnotifications"})
public class HibernateConfig {

	private static final Logger L = LoggerFactory.getLogger(HibernateConfig.class);

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean sessionFactory = new LocalContainerEntityManagerFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		sessionFactory.setPackagesToScan(new String[]{"com.profsoft.smsnotifications.model.data"});
		sessionFactory.setJpaProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean(destroyMethod = "")
	public DataSource dataSource() {
		L.info("#init DATASOURCE");
		final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
		dsLookup.setResourceRef(true);
		DataSource dataSource = dsLookup.getDataSource("SmsNotificDB");
		return dataSource;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		properties.put("hibernate.show_sql", "true");//FIXME to powinno być konfigurowane w WildFly
		properties.put("hibernate.format_sql", "true");//FIXME to powinno być konfigurowane w WildFly
		//properties.put("hibernate.cache.use.query_cache", "false");
		//properties.put("hibernate.cache.use_second_level_cache", "false");
		return properties;
	}

	@Bean
	@Autowired
	public JpaTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}

}
