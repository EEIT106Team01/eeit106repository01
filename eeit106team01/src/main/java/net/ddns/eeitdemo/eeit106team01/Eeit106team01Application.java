package net.ddns.eeitdemo.eeit106team01;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.RequestMapping;

import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleContentCurrentBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleTopicCurrentBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.MemberBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.VideoBean;
import net.ddns.eeitdemo.eeit106team01.shop.MemberBeanTest;
import net.ddns.eeitdemo.eeit106team01.shop.model.DeliverTypeBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.OrderBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.OrderDetailBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundDetailBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.ReviewBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.AntiTheftBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.CarCareBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.CarGpsBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.CarRecorderBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.CarSeatBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.EmergencyToolBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.HelmetRecorderBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.MotorcycleGpsBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.MotorcycleRecorderBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.product.automotive.TireGaugeBean;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { HibernateJpaAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class })
public class Eeit106team01Application {

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(Eeit106team01Application.class, args);
	}

	@RequestMapping("/")
	public String index() {
		return "forward:index.html";
	}

//	@Bean
	public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
		TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection securityCollection = new SecurityCollection();
				securityCollection.addPattern("/*");
				securityConstraint.addCollection(securityCollection);
				context.addConstraint(securityConstraint);
			}
		};
		tomcatServletWebServerFactory.addAdditionalTomcatConnectors(initiateHttpConnector());
		return tomcatServletWebServerFactory;
	}

	private Connector initiateHttpConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(80);
		connector.setSecure(false);
		connector.setRedirectPort(443);
		return connector;
	}

	@Bean
	public SessionFactory sessionFactory(@Autowired DataSource dataSource) {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource);
		// Shop
		builder.addAnnotatedClass(MemberBeanTest.class);
		builder.addAnnotatedClass(ProductBean.class);
		builder.addAnnotatedClass(ReviewBean.class);
		builder.addAnnotatedClass(OrderBean.class);
		builder.addAnnotatedClass(OrderDetailBean.class);
		builder.addAnnotatedClass(RefundBean.class);
		builder.addAnnotatedClass(RefundDetailBean.class);
		builder.addAnnotatedClass(DeliverTypeBean.class);
		// Cancel

		// Product
		builder.addAnnotatedClass(AntiTheftBean.class);
		builder.addAnnotatedClass(CarCareBean.class);
		builder.addAnnotatedClass(CarGpsBean.class);
		builder.addAnnotatedClass(CarRecorderBean.class);
		builder.addAnnotatedClass(CarSeatBean.class);
		builder.addAnnotatedClass(EmergencyToolBean.class);
		builder.addAnnotatedClass(HelmetRecorderBean.class);
		builder.addAnnotatedClass(MotorcycleGpsBean.class);
		builder.addAnnotatedClass(MotorcycleRecorderBean.class);
		builder.addAnnotatedClass(TireGaugeBean.class);

		// Forum
		builder.addAnnotatedClass(ArticleContentCurrentBean.class);
		builder.addAnnotatedClass(ArticleTopicCurrentBean.class);
		builder.addAnnotatedClass(VideoBean.class);
		builder.addAnnotatedClass(MemberBean.class);

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
