/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation.utils;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TakeScreenshot {
    
    WebDriver driver;
    String testname;
    String screenshotPath = "target"+File.separator+"Screenshots";
    
    public TakeScreenshot(String testname, WebDriver driver) {
        this.driver = driver;
        this.testname = testname;
    }
    
    
 public void takeScreenShotForCucumber(String screenshotFileName){
    	
    	File file = new  File(System.getProperty("user.dir")+File.separator+screenshotPath);
    	if(!file.isDirectory())
    		file.mkdir();
    	 File scrFile = ((TakesScreenshot) driver)
                 .getScreenshotAs(OutputType.FILE);
    	 String saveImgFile = System.getProperty("user.dir") +File.separator+ screenshotPath +File.separator+ "Failure_Screenshot_"+screenshotFileName+".jpg";
    	 System.out.println("Save Image File Path : " + saveImgFile);
         try {
			FileUtils.copyFile(scrFile, new File(saveImgFile));
		} catch (IOException e) {
			System.out.println("Error in saving file");
			e.printStackTrace();
		}
    	
    }
    
    public void takeScreenshot() {
        screenshotPath = (getProperty("screenshot-path") != null) ? getProperty("screenshot-path") : screenshotPath;
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_a");
        Date date = new Date();
        String date_time = dateFormat.format(date);
        File file = new File(System.getProperty("user.dir") + File.separator + screenshotPath + File.separator + this.testname
                + File.separator + date_time);
        boolean exists = file.exists();
        if (!exists) {
            new File(System.getProperty("user.dir") + File.separator + screenshotPath + File.separator + this.testname
                    + File.separator + date_time).mkdir();
        }
        
        File scrFile = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);
        try {
            String saveImgFile = System.getProperty("user.dir") + File.separator + screenshotPath
                    + File.separator + this.testname + File.separator + date_time
                    + File.separator + "screenshot.png";
            System.out.println("Save Image File Path : " + saveImgFile);
            FileUtils.copyFile(scrFile, new File(saveImgFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void takeScreenShotOnException() {
        /*String takeScreenshot = getProperty("take-screenshot");
        if (result.getStatus() == ITestResult.FAILURE) {
            Reporter.log("FAILURE occured at " + DateUtil.converttimestamp(System.currentTimeMillis()), true);
            if (takeScreenshot.equalsIgnoreCase("true") || takeScreenshot.equalsIgnoreCase("yes")) {
                try {
                    if (driver != null) {
                        takeScreenshot();
                    }
                } catch (Exception ex) {
                    Reporter.log("Driver is null while taking screen shot", true);
                }
            }
        }*/
    }
    
}
