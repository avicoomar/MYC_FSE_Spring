package config;

import java.util.Properties;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.orm.jpa.JpaTransactionManager;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = {"controller", "util"}) 
@EnableJpaRepositories(basePackages = "repository")
@EnableWebMvc
public class AppConfig{
	
	@Bean
	public DataSource dataSource() {
	    	DriverManagerDataSource dmd = new DriverManagerDataSource("jdbc:mysql://localhost:3306/MYC","gand","gand");
	    	dmd.setDriverClassName("com.mysql.jdbc.Driver");
		return dmd;
    	}
    	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(); 
		vendorAdapter.setGenerateDdl(true); 
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setJpaProperties(myHibernateProperties());
		factory.setPackagesToScan("model");
		factory.setDataSource(dataSource());
		
		return factory;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
	}
	
	private Properties myHibernateProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create-drop"); 
		properties.setProperty("hibernate.show_sql", "true");
		return properties;
    	}

}
