package com.ax.jdbc;

import com.zaxxer.hikari.util.DriverDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.SQLErrorCodes;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author ax
 */
@SpringBootApplication
@Slf4j
@EnableTransactionManagement
public class SpringJdbcApplication implements CommandLineRunner {


    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(SpringJdbcApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        showConnection();
    }

    private void showConnection() throws Exception {


//        log.info("dataSource" + dataSource.toString());

        Connection connection = dataSource.getConnection();

//        log.info(connection.toString());

        connection.close();

    }

//    @Bean
//    public DataSource dataSource() {
//
//        Properties properties = new Properties();
//        properties.setProperty("driverClassName", "org.h2.Driver");
//        properties.setProperty("url", "jdbc:h2:mem:testDb");
//        properties.setProperty("username", "sa");
//
//        return new DriverDataSource("jdbc:h2:mem:testDb", "org.h2.Driver", null, "sa", null);
//
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(dataSource);
//    }


}

























