package com.webUIBaseTest;

import org.apache.commons.io.FileUtils;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

public class WebBaseTestCore {

public Connection connnectionObject;

	@BeforeTest(alwaysRun = true)
	public void suiteSetup(ITestContext context) throws Exception {
		FileUtils.deleteDirectory(new File("./TestFailureImages/"));
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method method) throws Exception {
		BrowserManager.startWebSession(method);
		BrowserManager.getDriver().get("https://qa-int-us.gif-cloud-login-services.prod.us.walmart.net/gif-cloud-login-services/resources/v1/login");
		BrowserManager.getDriver().manage().window().maximize();
		BrowserManager.getDriver().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(Method methd) throws Exception {
		String debugMode = "true";
		if (debugMode.equals("true")) {
			//com.webUIBaseTest.BrowserManager.killAllSessions(true);
		}
	}

}
