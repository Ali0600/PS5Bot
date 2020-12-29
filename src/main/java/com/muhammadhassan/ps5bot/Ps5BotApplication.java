package com.muhammadhassan.ps5bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.muhammadhassan.ps5bot.service.AmazonBot;

@SpringBootApplication(scanBasePackages={
"com.muhammadhassan.ps5bot", "com.muhammadhassan.ps5bot.configuration", "com.muhammadhassan.ps5bot.service"})
public class Ps5BotApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =  SpringApplication.run(Ps5BotApplication.class, args);
		AmazonBot a = context.getBean(AmazonBot.class);
		Thread t1 = new Thread(a);
		t1.start();
	}

}
