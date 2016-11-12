package com.hbs.keywords;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.android.Connection;
import io.appium.java_client.android.HasNetworkConnection;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.GetPage;

public class WalmartDemo extends GetPage{

	private String searchTerm = null;
	public WalmartDemo(WebDriver driver) {
		super(driver, "walmart");
	}


	public void openHikeUserChat(String User) {
		element("ContactOnHikeUser",User).click();
	}

	public void typeAndSendOnChat(String Message) {
		element("msg_compose").click();
		element("msg_compose").sendKeys(Message);
		element("send_message").click();
		navigateToBackPage();
	}

	public void checkRecivedMessage(String message) {
		int size	=	elements("conversations_list").size();
		System.out.println(size);
		String recivedMessage	=	elements("conversations_list").get(size-1).getText();
		Assert.assertEquals(recivedMessage, message);
		navigateToBackPage();
	}


	public void WiFiOff() {
		((AndroidDriver) driver).setConnection(Connection.ALL);
		((AndroidDriver) driver).setConnection(Connection.AIRPLANE);
	}


	public void openApplication() {
		Assert.assertTrue(driver.findElement(By.xpath("//android.widget.TextView[@text='QA InfoTech Demo App'][@enabled='true']")).isDisplayed());
	
	}


	public void minimizeApp(int time) {
		//((AndroidDriver) driver).runAppInBackground(time);
		((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.HOME);
		logMessage("Pressed Android Home Button ");
	}

/**
 * WEB
 * @param string3 
 * @param string2 
 * @param string 
 */
	public void openConsole(String URL, String UserName, String password) {
	driver.get(URL);
	element("Email").sendKeys(UserName);
	element("next").click();
	wait.hardWait(2);
	element("Passwd").sendKeys(password);
	element("signIn").click();
	wait.hardWait(2);
	driver.get(URL);
	logMessage("Opened URL : "+URL+" And logged from : "+UserName);
	}


public void _sendMessage(String message) {
	int timeout	=	wait.timeout;
	wait.resetImplicitTimeout(30);
	element("NewMessage").click();
	element("messageText").sendKeys(message);
	element("Selectapp").click();
	element("AppID").click();
	element("onSendMessage").click();
	element("controllerConfirm").click();
	logMessage("Send Message : "+message+"  From console ");
}

public void openNotificationBarAndVerifyMessage(String message) {
	((AndroidDriver) driver).openNotifications();
	isElementDisplayed("notificationValue", message);
	}


public void openMessageFromNotification(String message) {
	element("notificationValue", message).click();
	logMessage("Open Notification : "+message);
}


public void verifyNoNotificationIsDisplayed(String message) {
	((AndroidDriver) driver).openNotifications();
	Assert.assertTrue(elementsWithOutWait("notificationValue", message).size()==0);
	logMessage("No Notification received during app is opened ");
}
	
	
}
