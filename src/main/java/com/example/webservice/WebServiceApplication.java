package com.example.webservice;

import java.io.FileNotFoundException;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author Domenico Anastasio - 2018 ©
 */
@SpringBootApplication
public class WebServiceApplication {

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
		SpringApplication.run(WebServiceApplication.class, args);
		new Init();
	}
}
