package com.prodian.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties 
public class CsvFileReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvFileReaderApplication.class, args);
	}

}
