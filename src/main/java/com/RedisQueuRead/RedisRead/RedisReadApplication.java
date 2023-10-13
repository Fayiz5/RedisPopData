package com.RedisQueuRead.RedisRead;

import com.RedisQueuRead.RedisRead.Service.ReadData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RedisReadApplication {

	public static void main(String[] args) {
		ApplicationContext context =SpringApplication.run(RedisReadApplication.class, args);

		ReadData read = context.getBean(ReadData.class);

				read.rpoptoother();




	}

}
