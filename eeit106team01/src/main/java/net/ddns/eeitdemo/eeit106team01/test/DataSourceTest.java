package net.ddns.eeitdemo.eeit106team01.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSourceTest implements CommandLineRunner {

	@Autowired
	private DataSource dataSource;
	
	@Override
	public void run(String... args) throws Exception {
		Connection connection = dataSource.getConnection();
		PreparedStatement pstmt = connection.prepareStatement("select * from dept");
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			System.out.println(rs.getString(2));
		}
		rs.close();
		pstmt.close();
		connection.close();
	}

}
