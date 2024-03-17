package com.example.zhaoshichuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan //开启了对servlet组件的支持
@SpringBootApplication
public class ZhaoshichuanApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZhaoshichuanApplication.class, args);
	}

}
