package com.example.webservice;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Domenico Anastasio - 2018 ©
 */
@SpringBootApplication
public class WebServiceApplication {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		SpringApplication.run(WebServiceApplication.class, args);
		new Init();
	}
}
