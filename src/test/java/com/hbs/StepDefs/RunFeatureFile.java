package com.hbs.StepDefs;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.hbs.test.ExtendedCucumberRunner;

import cucumber.api.CucumberOptions;

@CucumberOptions(plugin = { "html:target/cucumber-html-report", "json:target/cucumber.json",
		"pretty:target/cucumber-pretty.txt",
		"usage:target/cucumber-usage.json" }, features = "src/test/resources/features")

@RunWith(ExtendedCucumberRunner.class)
public class RunFeatureFile {
	@AfterClass
	public static void tearDown()
	{
	 CucumberHooks.test.closeBrowserSession();
	}
}
