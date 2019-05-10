package net.ddns.eeitdemo.eeit106team01;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;

import net.ddns.eeitdemo.eeit106team01.model.CustomerBean;
import net.ddns.eeitdemo.eeit106team01.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.model.ShopBean;

@SpringBootApplication
public class Eeit106team01Application {

	@Autowired
	private Environment environment;
	
	public static void main(String[] args) {
		SpringApplication.run(Eeit106team01Application.class, args);
	}

	@Bean
	public SessionFactory sessionFactory(@Autowired DataSource dataSource) {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource);
		builder.addAnnotatedClass(CustomerBean.class);
		builder.addAnnotatedClass(ProductBean.class);
		builder.addAnnotatedClass(ShopBean.class);
		
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
		props.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
		props.setProperty("hibernate.format_sql", environment.getProperty("hibernate.format_sql"));
		props.setProperty("hibernate.use_sql_comments", environment.getProperty("hibernate.use_sql_comments"));
		props.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
		
		builder.addProperties(props);
		return builder.buildSessionFactory();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(@Autowired SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}
	
}
