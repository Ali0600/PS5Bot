package com.muhammadhassan.ps5bot.service;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class AmazonBot {
	private static final String URL = "https://www.amazon.com/gp/product/B08FC5L3RG/";
	//private static final String URL2 = "https://www.amazon.com/gp/product/B07WLT1C27/";
	
	private final ChromeDriver driver;
	
	public AmazonBot(ChromeDriver driver) {
		this.driver = driver;
		startAmazonBot();
	}
	
	public void startAmazonBot() {
		driver.get(URL);
		
		driver.findElement(By.xpath("//*[@id=\"nav-link-accountList\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"ap_email\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"ap_email\"]")).sendKeys("email");
		driver.findElement(By.xpath("//*[@id=\"continue\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"ap_password\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"ap_password\"]")).sendKeys("password");
		driver.findElement(By.xpath("//*[@id=\"signInSubmit\"]")).click();
		
		boolean addToCart = driver.findElements(By.xpath("//*[@id=\"add-to-cart-button\"]")).size() != 0;
		
		while(!addToCart) {
			driver.navigate().refresh();
			addToCart = driver.findElements(By.xpath("//*[@id=\"add-to-cart-button\"]")).size() != 0;
			System.out.println("Present: " + addToCart);
		}
		driver.findElement(By.xpath("//*[@id=\"add-to-cart-button\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"sc-buy-box-ptc-button\"]/span/input")).click();
		driver.findElement(By.xpath("//*[@id=\"submitOrderButtonId\"]/span/input")).click();
		driver.quit();
	}
}
