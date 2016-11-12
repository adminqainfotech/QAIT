package com.hbs.StepDefs;


import java.util.HashMap;
import java.util.Map;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;

public class Stepdefs_Demo {

	TestSessionInitiator mobile = CucumberHooks.test;
	

	@Given("^I am at Application Home Page$")
	public void  _OpenApplicationHomePage_(){
		mobile.Demo.openApplication();
	}
	
	@And("^Minimize it$")
	public void  _MinimizeIt (){
		mobile.Demo.minimizeApp(20);
	}
	/*
	 * When I Login into the console: https://console.firebase.google.com/project/demoapplication-5f90b/notification (qaitdemo@gmail.com/qainfotech)
	 */
	
	TestSessionInitiator browser;
	private long time;
	private String message;
	
	
	@When("^I Login into the console$")
	public void open_console() throws Throwable {
		Map<String, String> cap = new HashMap();
		cap.put("tier", "stage");
		cap.put("browser", "chrome");
		cap.put("seleniumServer", "local");
		cap.put("ostype", "Android");
		cap.put("appiumServer", "");
		browser = new TestSessionInitiator("Local ", cap);
		browser.Demo.openConsole("https://console.firebase.google.com/project/demoapplication-5f90b/notification","qaitdemo@gmail.com","qainfotech");	
	}
	
	@And("^Send a message to device through console$")
	public void  _SendMessageThroughConsole (){
		time	=	System.currentTimeMillis();
		 message	=	"From Automation:"+time;
		browser.Demo._sendMessage(message);
	}
	
	@Then("^Verify message is received in notification window$")
	public void MessageReceivedNotificationWindow(){
		mobile.Demo.openNotificationBarAndVerifyMessage(message);
	}
	@When("^open the Notification$")
	public void openNotificationFromNotification() {
		mobile.Demo.openMessageFromNotification(message);
	}
	
	@Then("^No Notification received$")
	public void openNotificationBar() {
		mobile.Demo.verifyNoNotificationIsDisplayed(message);
	}

}
