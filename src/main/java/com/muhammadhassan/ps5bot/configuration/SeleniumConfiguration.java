package com.muhammadhassan.ps5bot.configuration;

import javax.annotation.PostConstruct;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SeleniumConfiguration {

	@PostConstruct
	void postConstruct() {
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver_win32\\chromedriver.exe");
	}
	
	@Bean
	@Scope(value="prototype")
	public ChromeDriver driver() {
		
		ChromeOptions chromeOptions = new ChromeOptions();
		//chromeOptions.addArguments("--headless");
		//chromeOptions.addArguments("--proxy-server=http://190.215.155.196:80");
		chromeOptions.addArguments("--disable-blink-features");
		chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
		//chromeOptions.add_experimental_option("excludeSwitches", ["enable-automation"]);
		//chromeOptions.add_experimental_option('useAutomationExtension', False);
		return new ChromeDriver(chromeOptions);
	}

}
