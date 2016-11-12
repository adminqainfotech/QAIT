package com.qait.automation.getpageobjects;

import static com.qait.automation.getpageobjects.ObjectFileReader.getELementFromFile;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class GetPage extends BaseUi {

	protected WebDriver webdriver;
	String pageName;
	//LayoutValidation layouttest;

	public GetPage(WebDriver driver, String pageName) {
		super(driver, pageName);
		this.webdriver = driver;
		this.pageName = pageName;
		//layouttest = new LayoutValidation(driver, pageName);
	}

	/*public void testPageLayout(List<String> tagsToBeTested) {
		layouttest.checklayout(tagsToBeTested);
	}*/

	/*public void testPageLayout(String tagToBeTested) {
		testPageLayout(Arrays.asList(tagToBeTested));
	}

	public void testPageLayout() {
		testPageLayout(Arrays.asList(getProperty("./Config.properties",
				"browser")));
	}*/

	// TODO: put this in right place, create dedicated class for frame and
	// window handlers
	protected void switchToNestedFrames(String frameNames) {
		switchToDefaultContent();
		String[] frameIdentifiers = frameNames.split(":");
		for (String frameId : frameIdentifiers) {
			wait.waitForFrameToBeAvailableAndSwitchToIt(getLocator(frameId
					.trim()));
		}
	}

	protected WebElement element(String elementToken)
			throws NoSuchElementException {
		return element(elementToken, "");
	}
	
	protected String getElementLocatorValue(String elementToken){
		return getLocatorValue(elementToken, "");
	}
	
	protected WebElement element(String elementToken, String replacement)
			throws NoSuchElementException {
		WebElement elem = null;
		By locator = getLocator(elementToken, replacement);
		try {
			elem = wait.waitForElementToBeVisible(webdriver
					.findElement(locator));
		} catch (TimeoutException excp) {
			throw new NoSuchElementException("Element " + elementToken
					+ " with locator " + locator.toString().substring(2)
					+ " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}
		
	protected WebElement elementWithoutWait(String elementToken){
		return driver.findElement(getLocator(elementToken));
	}
	protected WebElement elementWithoutWait(String elementToken,String replacement){
		return driver.findElement(getLocator(elementToken,replacement));
	}

	
	protected List<WebElement> elements(String elementToken, String replacement) {
		return wait.waitForElementsToBeVisible(webdriver
				.findElements(getLocator(elementToken, replacement)));
	}

	protected List<WebElement> elements(String elementToken) {
		return elements(elementToken, "");
	}
	protected List<WebElement> elementsWithOutWait(String elementToken, String replacement) {
		return webdriver
				.findElements(getLocator(elementToken, replacement));
	}

	/*protected void isStringMatching(String actual, String expected) {
		Assert.assertEquals(actual, expected);
		logMessage("ACTUAL STRING : " + actual);
		logMessage("EXPECTED STRING :" + expected);
		logMessage("String compare Assertion passed.");
	}*/

	protected void enterText(WebElement element,String text){
		wait.waitForElementToBeClickable(element);
		if(getBrowser().equalsIgnoreCase("chrome")){
			clickUsingActionScript(element);
		}else
			element.click();
		element.clear();
		element.sendKeys(text);
		logMessage("Entered Text "+ text  );
	}
	
	
	protected void isElementNotDisplayed(String elementName) {
		 wait.resetImplicitTimeout(5);
		  Boolean status = false;
		  try {
			  status = elementWithoutWait(elementName).isDisplayed();
			  Assert.assertFalse("Assertion Failed : Element is displayed",status);
			  logMessage("Assertion Passed : Element "+elementName+" is not displayed ");
		  } catch (NoSuchElementException e) {
		   logMessage("Assertion Passed : Element "+elementName+" is not displayed ");
		  }
		  finally{
		  wait.resetImplicitTimeout(wait.timeout);
		  }
	}
	
	protected boolean isElementDisplayed(String elementName,
			String elementTextReplace) {
		wait.waitForElementToBeVisible(element(elementName, elementTextReplace));
		boolean result = element(elementName, elementTextReplace).isDisplayed();
		org.junit.Assert.assertTrue("ASSERT FAILED: element '" + elementName
				+ "with text " + elementTextReplace + "' is not displayed.", result);
		logMessage("ASSERT PASSED: element " + elementName + " with text "
				+ elementTextReplace + " is displayed.");
		return result;
	}

	protected void verifyElementText(String elementName, String expectedText) {
		wait.waitForElementToBeVisible(element(elementName));
		Assert.assertEquals("ASSERT FAILED: element '" + elementName
				+ "' Text is not as expected: ",element(elementName).getText().trim(), expectedText);
		logMessage("ASSERT PASSED: element " + elementName
				+ " is visible and Text is " + expectedText);
	}

	protected void verifyElementTextContains(String elementName,
			String expectedText) {
		String actualText =  element(elementName).getText();
		wait.waitForElementToBeVisible(element(elementName));
		Assert.assertTrue("ASSERT FAILED: element '" + elementName
				+ "' Text is not as expected, actual text : "+actualText+", Expected text : - "+expectedText, actualText
				.trim().toUpperCase().contains(expectedText.toUpperCase()));
		logMessage("ASSERT PASSED: element " + elementName
				+ " is visible and Text is " + expectedText);
	}

	
	protected boolean isElementDisplayed(String elementName)
			throws NoSuchElementException {
		boolean result = wait.waitForElementToBeVisible(element(elementName))
				.isDisplayed();
		Assert.assertTrue( "ASSERT FAILED: element '" + elementName
				+ "' is not displayed.",result);
		logMessage("ASSERT PASSED: element " + elementName + " is displayed.");
		return result;
	}

	public void hoverusingjavascript(WebElement e){
		hardWaitForIEBrowser(2);
		 String javascript= "var evObj = document.createEvent('MouseEvents');" +
		                "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
		                "arguments[0].dispatchEvent(evObj)";
		 ((JavascriptExecutor) driver).executeScript(javascript, e);
	}
	
	public void hoverOutusingjavascript(WebElement e){
		hardWaitForIEBrowser(2);
		 String javascript= "var evObj = document.createEvent('MouseEvents');" +
		                "evObj.initMouseEvent(\"mouseout\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
		                "arguments[0].dispatchEvent(evObj)";
		 ((JavascriptExecutor) driver).executeScript(javascript, e);
	}
	
	
	protected boolean isElementEnabled(String elementName, boolean expected) {
		wait.waitForElementToBeVisible(element(elementName));
		boolean result = expected && element(elementName).isEnabled();
		Assert.assertTrue( "ASSERT FAILED: element '" + elementName
				+ "' is  ENABLED :- " + !expected, result);
		logMessage("ASSERT PASSED: element " + elementName + " is enabled :- "
				+ expected);
		return result;
	}
	
	

	protected By getLocator(String elementToken) {
		return getLocator(elementToken, "");
	}

	public void clickAndHold(WebElement element) {
		Actions act = new Actions(driver);
		act.clickAndHold(element).build().perform();
	}
	
	public void clickUsingActionScript(WebElement element){
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}
	
	protected By getLocator(String elementToken, String replacement) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceAll("\\$\\{.+\\}", replacement);
		return getBy(locator[1].trim(), locator[2].trim());
	}
	
	protected String getLocatorValue(String elementToken, String replacement) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceAll("\\$\\{.+\\}", replacement);
		return locator[2].trim();
	}

	private By getBy(String locatorType, String locatorValue) {
		switch (Locators.valueOf(locatorType)) {
		case id:
			return By.id(locatorValue);
		case xpath:
			return By.xpath(locatorValue);
		case css:
			return By.cssSelector(locatorValue);
		case name:
			return By.name(locatorValue);
		case classname:
			return By.className(locatorValue);
		case linktext:
			return By.linkText(locatorValue);
		default:
			return By.id(locatorValue);
		}
	}

}
