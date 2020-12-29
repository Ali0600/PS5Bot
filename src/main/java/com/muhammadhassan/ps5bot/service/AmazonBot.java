package com.muhammadhassan.ps5bot.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class AmazonBot implements Runnable{
	private static final String URL = "https://www.amazon.com/gp/product/B08FC5L3RG/";
	private static final String URL2 = "https://www.amazon.com/Yakuza-Like-Dragon-Day-Ichi-PlayStation/dp/B0883VX67Y/";
	private static boolean captchaFlag = false;
	
	private ChromeDriver driver;
	private Random r;
	
	public AmazonBot(ChromeDriver driver) {
		this.driver = driver;
		r = new Random();
	}
	
	@Override
	public void run() {
		startAmazonBot();
	}
	
	//This is just a function to use Thread.sleep()
	public void sleepThread(int time){
		System.out.println("Sleep time: " + time);
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//This is used to 
	public void checkCaptcha(boolean one) {
		if(one) {
			System.out.println("Captcha is present, restarting chrome process.");
			driver.close();
			captchaFlag = true;
			startAmazonBot();
		}
	}
	
	//This is used to emulate as if a human was typing to avoid captcha and being detected as a bot.
	public void typeSlow(String string, String path) {
		String [] stringArr = string.split("(?!^)");
		
		for (String s: stringArr) {
			driver.findElement(By.xpath(path)).sendKeys(s);
			//Random range of 100 and 250 milliseconds
			sleepThread(100 + r.nextInt(150));
		}
	}
	
	public void startAmazonBot() {
		//This is for when there is a captcha screen that can be solved with just restarting Chrome. The restart happens below, but if the startAmazonBot function
		//is triggered again inside itself, the flag is set to true so this can create a new ChromeDriver.
		if(captchaFlag) {
			ChromeOptions chromeOptions = new ChromeOptions();
			//chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("--disable-blink-features");
			chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
			driver = new ChromeDriver(chromeOptions);
		}
		driver.get(URL2);
		//This captcha is a hard one where I have to enter captcha or I can't finish the login.
		boolean captchaHard = driver.findElements(By.xpath("//*[@id=\"auth-captcha-image\"]")).size() != 0;
		System.out.println("captcha3 present: " + captchaHard);
		if(captchaHard) {
			driver.quit();
			System.out.println("A hard captcha is present. Login and enter captcha.");
			return;
		}
		//There is a captcha screen that can appear, this can be solved with just restarting chrome.
		boolean captcha1 = driver.findElements(By.xpath("/html/body/div/div[1]/div[3]/div/div/form/div[1]/div/div/div[2]/div/div[2]/a")).size() != 0;
		checkCaptcha(captcha1);
		
		boolean addToCartBtn = driver.findElements(By.xpath("//*[@id=\"add-to-cart-button\"]")).size() != 0;
		//While addToCartBtn is false, ie, not present, keep refreshing page.
		while(!addToCartBtn) {
			checkCaptcha(captcha1);
			//This has a random range of 10 seconds to 20 seconds.
			//This sleep is needed to prevent being detected as a bot and not overloading the website.
			sleepThread(10000 + r.nextInt(10000));
			driver.navigate().refresh();
			addToCartBtn = driver.findElements(By.xpath("//*[@id=\"add-to-cart-button\"]")).size() != 0;
			captcha1 = driver.findElements(By.xpath("/html/body/div/div[1]/div[3]/div/div/form/div[1]/div/div/div[2]/div/div[2]/a")).size() != 0;
			System.out.println("Add to Cart Present: " + addToCartBtn);
		}
		driver.findElement(By.xpath("//*[@id=\"nav-link-accountList\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"ap_email\"]")).click();
		sleepThread(1000);
		typeSlow("email", "//*[@id=\"ap_email\"]");
		driver.findElement(By.xpath("//*[@id=\"continue\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"ap_password\"]")).click();
		typeSlow("password", "//*[@id=\"ap_password\"]");
		driver.findElement(By.xpath("//*[@id=\"signInSubmit\"]")).click();
		sleepThread(2000);
		
		boolean buyNowBtn = driver.findElements(By.xpath("//*[@id=\"submit.buy-now\"]")).size() != 0;
		//This checks if there is a Buy Now button that skips going to the cart. 
		if(buyNowBtn) {
			driver.findElement(By.xpath("//*[@id=\"submit.buy-now\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"submitOrderButtonId\"]/span/input")).click();
			driver.quit();
			return;
		}
		driver.findElement(By.xpath("//*[@id=\"add-to-cart-button\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"hlb-ptc-btn-native\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"submitOrderButtonId\"]/span/input")).click();
		driver.quit();
	}
}
