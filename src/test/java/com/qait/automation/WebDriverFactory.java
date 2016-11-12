/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testobject.rest.api.appium.common.TestObjectCapabilities;

public class WebDriverFactory {

	private static String browser;
	private static final DesiredCapabilities capabilities = new DesiredCapabilities();
	private static final String chromeDriverPath = System.getProperty("user.dir")+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"Drivers"+File.separator+"chromedriver";

	private static final String ieDriverPath = System.getProperty("user.dir")+File.separator+"src"+File.separator+
			"test"+File.separator+"resources"+File.separator+"Drivers"+File.separator+"IEDriverServer.exe";
	private WebDriver driver;
	private AndroidDriver driverAndroid;


	public WebDriver getDriver(Map<String, String> seleniumconfig) {
		browser = seleniumconfig.get("browser").toLowerCase();
		System.out.println("Test Browser is :" +browser);
		String serverHost;
		if(System.getProperty("server")==null)
			serverHost = seleniumconfig.get("seleniumServer");
		else
			serverHost = System.getProperty("seleniumServer");
		if (serverHost.equalsIgnoreCase("local")) {
			if (browser.equalsIgnoreCase("firefox")) {
				return getFirefoxDriver();
			} else if (browser.equalsIgnoreCase("chrome")) {//"src"+File.separator+"test"+File.separator+"resources"+File.separator+"driver"+File.separator+"
				return getChromeDriver(chromeDriverPath);
			} else if (browser.equalsIgnoreCase("Safari")) {
				return getSafariDriver();
			} else if ((browser.equalsIgnoreCase("ie"))
					|| (browser.equalsIgnoreCase("internetexplorer"))
					|| (browser.equalsIgnoreCase("internet explorer"))) {
				return getInternetExplorerDriver(ieDriverPath);
			}
			else if (browser.equalsIgnoreCase("mobile")||browser.equalsIgnoreCase("cloud") && seleniumconfig.get("ostype").equalsIgnoreCase("ios")) {
				return  setupIosDriver(seleniumconfig);
			} else if (browser.equalsIgnoreCase("mobile")||browser.equalsIgnoreCase("cloud") && seleniumconfig.get("ostype").equalsIgnoreCase("android")) {
				return setMobileAndroidDriver(seleniumconfig);
			}

		}
		if (serverHost.equalsIgnoreCase("remote")) {
			return setRemoteDriver(seleniumconfig);
		}
		return driver;
	}

	private WebDriver setRemoteDriver(Map<String, String> selConfig) {
		DesiredCapabilities cap = null;
		browser = TestSessionInitiator.getBrowser();
		String ipAddress;
		if(System.getProperty("remoteaddress")==null)
			ipAddress = selConfig.get("seleniumServerHost");
		else
			ipAddress = System.getProperty("remoteaddress");
		if (browser.equalsIgnoreCase("firefox")) {
			cap = DesiredCapabilities.firefox();
		} else if (browser.equalsIgnoreCase("chrome")) {
			cap = DesiredCapabilities.chrome();
		} else if (browser.equalsIgnoreCase("Safari")) {
			cap = DesiredCapabilities.safari();
		} else if ((browser.equalsIgnoreCase("ie"))
				|| (browser.equalsIgnoreCase("internetexplorer"))
				|| (browser.equalsIgnoreCase("internet explorer"))) {
			cap = DesiredCapabilities.internetExplorer();
		}
		URL selserverhost = null;
		try {
			selserverhost = new URL(ipAddress);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		cap.setJavascriptEnabled(true);
		return new RemoteWebDriver(selserverhost, cap);
	}

	private static WebDriver getChromeDriver(String driverpath) {
		System.setProperty("webdriver.chrome.driver", driverpath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");
		options.addArguments("test-type");
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		return new ChromeDriver(cap);
	}

	private static WebDriver getInternetExplorerDriver(String driverpath) {
		System.setProperty("webdriver.ie.driver", driverpath);
		capabilities.setCapability("ignoreZoomSetting", true);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);  
		return new InternetExplorerDriver(capabilities);
	}

	private static WebDriver getSafariDriver() {
		return new SafariDriver();
	}

	private static WebDriver getFirefoxDriver() {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.cache.disk.enable", false);
		return new FirefoxDriver(profile);
	}
	public WebDriver setupIosDriver(Map<String, String> selConfig)  {
		File app=null;; 
		DesiredCapabilities capabilities = new DesiredCapabilities();

		if(selConfig.get("browser").equals("mobile"))
		{
			File appDir = new File("App\\"+selConfig.get("tier").toUpperCase());
			String[] filesInDir = appDir.list();
			app = new File(appDir, filesInDir[0]);
			System.out.println(app.getAbsolutePath());
			capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
			System.out.println(app.getAbsolutePath());
		}
		String [] appiumDeviceConfig = selConfig.get("mobileDevice").split(":"); 

		capabilities.setCapability(CapabilityType.VERSION, "9.3.2");
		capabilities.setCapability(CapabilityType.PLATFORM, "iOS");
		capabilities.setCapability("autoAcceptAlerts", true);
		// handleAlert();
		capabilities.setCapability("noReset",false);
		capabilities.setCapability("deviceName", appiumDeviceConfig[0]);

		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
		URL appiumServerHost = null ;
		try {
			String appiumServerHostUrl = selConfig.get("appiumServer");
			appiumServerHost = new URL(appiumServerHostUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		IOSDriver iosDriver = new IOSDriver(appiumServerHost, capabilities);
		iosDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		WebDriver driver = iosDriver;
		return driver;
	}	

	public WebDriver setMobileAndroidDriver(Map<String, String> selConfig) {
		System.out.println("Mobile driver initialization started . . . ...");
		if (browser.equalsIgnoreCase("cloud")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			capabilities.setCapability("testobject_api_key", "6F5AFF19DA52459496C844ADE6DD565D");
			capabilities.setCapability("testobject_app_id", "2");
			capabilities.setCapability("testobject_device", "Motorola_Moto_E_2nd_gen_free");
			capabilities.setCapability("newCommandTimeout", 500);
			capabilities.setCapability("noReset",true);
			driverAndroid = new AndroidDriver(TestObjectCapabilities.TESTOBJECT_APPIUM_ENDPOINT, capabilities);
			WebDriver driver = driverAndroid;
			System.out.println("Test live view: " + driverAndroid.getCapabilities().getCapability("testobject_test_live_view_url"));
			System.out.println("Test report: " + driverAndroid.getCapabilities().getCapability("testobject_test_report_url"));
			return driver;
		}else {
			File classpathRoot = new File(System.getProperty("user.dir"));
			String tier = getProperty("tier").toUpperCase();
			File appDir = new File(classpathRoot, "app/"+tier+"/");
			File location = new File(appDir,"QAIT Demo-debug.apk");
			String appPath = location.getAbsolutePath().trim().toString();

			//		capabilities = DesiredCapabilities.android();
			DesiredCapabilities cap = new DesiredCapabilities().android();


			//	 capabilities.setCapability("udid", devices);
			//		capabilities.setCapability("platformVersion", platformVersion);
			cap.setCapability("deviceName", "Android");
			cap.setCapability("platformName", "Android");
			cap.setCapability("--session-override",true);
			cap.setCapability("app", appPath);
			cap.setCapability(CapabilityType.VERSION, "5.0.2");
			capabilities.setCapability("noReset",true);
			//		cap.setCapability("browserName", "Chrome");
			//		capabilities.setCapability("chrome.switches", Arrays.asList("--incognito"));
			String appiumServerHostUrl = selConfig.get("appiumServer");
			URL appiumServerHost = null;
			try {
				appiumServerHost = new URL(appiumServerHostUrl);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			cap.setJavascriptEnabled(true);
			System.out.println(appiumServerHostUrl);
			AndroidDriver driverAndroid = new AndroidDriver(appiumServerHost, cap);
			WebDriver driver = driverAndroid;
			return driver;
		}
	}

	public WebDriver setMobileAndroidDriver(Map<String, String> selConfig, String devicesName,
			String port) {
		System.out.println("Mobile driver initialization started . . . ...");

		File classpathRoot = new File(System.getProperty("user.dir"));
		String tier = getProperty("tier").toUpperCase();
		File appDir = new File(classpathRoot, "app/"+tier+"/");
		File location = new File(appDir,"QAIT Demo-debug.apk");
		String appPath = location.getAbsolutePath().trim().toString();
		/***************************************/
		DesiredCapabilities cap = new DesiredCapabilities().android();
		cap.setCapability("udid", devicesName);
		cap.setCapability("deviceName", devicesName);
		cap.setCapability("platformName", "Android");
		cap.setCapability("--session-override",true);
		cap.setCapability("app", appPath);
		cap.setCapability(CapabilityType.VERSION, "5.0.2");
		cap.setCapability("newCommandTimeout", 500);
		cap.setCapability("noReset",true);
		//		cap.setCapability("browserName", "Chrome");
		//		capabilities.setCapability("chrome.switches", Arrays.asList("--incognito"));
		System.out.println(port);
		String appiumServerHostUrl = "http://127.0.0.1:"+port+"/wd/hub".toString().trim();
		System.out.println(appiumServerHostUrl);
		URL appiumServerHost = null;
		try {
			appiumServerHost = new URL(appiumServerHostUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		cap.setJavascriptEnabled(true);
		AndroidDriver driverAndroid = new AndroidDriver(appiumServerHost, cap);
		WebDriver driver = driverAndroid;
		System.out.println("Mobile driver initialization Completed  . . . ...");
		return driver;
	}
}
