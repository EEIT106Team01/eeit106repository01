package net.ddns.eeitdemo.eeit106team01;

import java.io.IOException;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import net.ddns.eeitdemo.eeit106team01.chat.model.PrivateMessageBean;
import net.ddns.eeitdemo.eeit106team01.chat.model.RegionMessageBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleContentCurrentBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleTopicCurrentBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.MemberBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.VideoBean;
import net.ddns.eeitdemo.eeit106team01.forum.utils.FFmpegUtils;
import net.ddns.eeitdemo.eeit106team01.shop.model.DataBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.Member;
import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.PurchaseListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.RefundListBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.ReviewBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.SerialNumberBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.TopSearchBean;

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

	@Bean("objectMapper")
	public ObjectMapper myMapper() {
		return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
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
		builder.addAnnotatedClass(Member.class);
		builder.addAnnotatedClass(ProductBean.class);
		builder.addAnnotatedClass(ReviewBean.class);
		builder.addAnnotatedClass(PurchaseBean.class);
		builder.addAnnotatedClass(PurchaseListBean.class);
		builder.addAnnotatedClass(RefundBean.class);
		builder.addAnnotatedClass(RefundListBean.class);
		builder.addAnnotatedClass(SerialNumberBean.class);
		builder.addAnnotatedClass(TopSearchBean.class);
		builder.addAnnotatedClass(DataBean.class);

		// Forum
		builder.addAnnotatedClass(ArticleContentCurrentBean.class);
		builder.addAnnotatedClass(ArticleTopicCurrentBean.class);
		builder.addAnnotatedClass(VideoBean.class);
		builder.addAnnotatedClass(MemberBean.class);

		// Chat
		builder.addAnnotatedClass(RegionMessageBean.class);
		builder.addAnnotatedClass(PrivateMessageBean.class);

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

	@Bean
	public FFmpegUtils fFmpegUtils() throws IOException {
		FFmpegUtils ffu = null;
		String ffmpegPath = environment.getProperty("path.ffmpeg");
		String ffprobePath = environment.getProperty("path.ffprobe");
		if (ffmpegPath != null && ffmpegPath.length() != 0 && ffprobePath != null && ffprobePath.length() != 0) {
			System.out.println("use specify path of ffmpeg and ffprobe");
			ffu = new FFmpegUtils(ffmpegPath, ffprobePath);
		} else {
			System.out.println("use default path of ffmpeg and ffprobe");
			ffu = new FFmpegUtils();
		}
		return ffu;
	}

}
