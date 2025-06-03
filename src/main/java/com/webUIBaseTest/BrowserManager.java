package com.webUIBaseTest;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class BrowserManager {


	public BrowserManager() {
	  }

	  private static Map<Long, LinkedHashMap<SessionId, Session>> _sessions = new ConcurrentHashMap<>();
	  private static Map<Long, Session> _activeSessions = new ConcurrentHashMap<>();


	  public static WebDriver startWebSession(Method testMethod) throws Exception {
		    return startWebSession(testMethod, null);
		  }

	  public static WebDriver startWebSession(Method testMethod, Map<String, Object> customCapabilities) throws Exception {
		    Session session = null;
		    try {

		      DesiredCapabilities capabilities = getWebCaps(testMethod, customCapabilities);
		      WebDriver driver = getWebDriver(capabilities);
		      session = addSession(driver);
		      session.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		    } catch (Exception e) {
		      if (session == null) {
		        throw new Exception("Unable to create Session due to " + e.getMessage(), e);
		      }
		    }
		    return session.getDriver();
		  }

	  private static WebDriver getWebDriver(DesiredCapabilities capabilities) throws Exception {
		    try {
		      String browserType = System.getProperty("browser");
		      String host = "localhost";
		      String gridUrl = "gridUrl";
		      String port = "port";

		      if (host.equals("localhost")) {
		        switch (browserType) {
		          case "firefox":
		            return new FirefoxDriver(capabilities);
		          case "chrome":
		            return new ChromeDriver(capabilities);
		          case "iexplore":
		            return new InternetExplorerDriver(capabilities);
		          default:
		            throw new Exception("Invalid browser: " + browserType);
		        }
		      } else if (host.contains("grid")){
		        String remoteUrl = "http://" + gridUrl + "/wd/hub";
		        return new RemoteWebDriver(new URL(remoteUrl), capabilities);
		      } else {
		       String remoteUrl = "http://" + host + ":"
		            + port + "/wd/hub";
		        return new RemoteWebDriver(new URL(remoteUrl), capabilities);
		      }
		    } catch (MalformedURLException e) {
		      throw new Exception("Unable to get web driver instance: " + e.getMessage());
		    }
		  }

	  private static DesiredCapabilities getWebCaps(Method testMethod, Map<String, Object> customCapabilities) throws Exception {
		    String browserType = System.getProperty("browser");
		    DesiredCapabilities capabilities = null;
		    switch (browserType) {
		      case "firefox":
		        FirefoxProfile profile = createFirefoxProfile();
		        //capabilities = DesiredCapabilities.firefox();
		        capabilities.setCapability("marionette", true);
		       // capabilities.setCapability(FirefoxDriver.PROFILE, profile);
		        String os = null;
	            String firefoxDriver = "geckodriver";
		        if (SystemUtils.IS_OS_MAC_OSX) {
		              os = "mac";
		              System.out.println("firefox os :"+ os);
		            } else if (SystemUtils.IS_OS_LINUX) {
		              os = "linux";
		              System.out.println("firefox os :"+ os);
		              firefoxDriver += System.getProperty("sun.arch.data.model");
		            } else if(SystemUtils.IS_OS_WINDOWS){
		            	os = "windows";
		            	System.out.println("firefox os :"+ os);
		            	firefoxDriver +=".exe";
		            }
		        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/src/main/resources/Drivers/firefox/"+os+"/"+firefoxDriver);
		        break;
		      case "chrome":
		        ChromeOptions options = createChromeOptions();
		        //capabilities = DesiredCapabilities.chrome();
		        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		        capabilities.setPlatform(Platform.LINUX);
		            os = null;
		            String chromeDriver = "chromedriver";
		            if (SystemUtils.IS_OS_MAC_OSX) {
		              os = "mac";
		            } else if (SystemUtils.IS_OS_LINUX) {
		              os = "linux";
		              chromeDriver += System.getProperty("sun.arch.data.model");
		            } else if(SystemUtils.IS_OS_WINDOWS){
		            	os = "windows";
		            	chromeDriver +=".exe";
		            }
		        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/com/Drivers/chrome/"+os+"/"+chromeDriver);
		        break;
		      case "iexplore":
		       // capabilities = DesiredCapabilities.internetExplorer();
		        break;
		      default:
		        throw new Exception("Invalid browser: " + browserType);
		    }

		    return capabilities;
		  }

	  private static FirefoxProfile createFirefoxProfile() {
		    FirefoxProfile profile = new FirefoxProfile();
		    profile.setPreference("network.proxy.type", 2);
		    profile.setPreference("network.proxy.ssl", "localhost");
		    profile.setPreference("network.proxy.ssl_port", 4444);
		    return profile;
		  }

	  private static ChromeOptions createChromeOptions() {
		    ChromeOptions chromeOptions = new ChromeOptions();
		    chromeOptions.addArguments("start-maximized");
		    return chromeOptions;
		  }

	  private static Session addSession(WebDriver driver) {
		    long threadId = Thread.currentThread().getId();
		    LinkedHashMap<SessionId, Session> sessions = _sessions.get(threadId);
		    if (sessions == null) {
		      sessions = new LinkedHashMap<>();
		      _sessions.put(threadId, sessions);
		    }
		    Session session = new Session(driver);
		    sessions.put(session.getSessionId(), session);
		    _activeSessions.put(threadId, session);
		    return session;
		  }

	  public static WebDriver getDriver() throws Exception {
		    return getSession().getDriver();
		  }

	  private static Session getSession() throws Exception {
		    long threadId = Thread.currentThread().getId();
		    LinkedHashMap<SessionId, Session> sessions = _sessions.get(threadId);
		    if (sessions == null || sessions.isEmpty()) {
		      throw new Exception("No valid session available");
		    }
		    return _activeSessions.get(threadId);
		  }

	  public static void killAllSessions(boolean quitSession) {
		    long threadId = Thread.currentThread().getId();
		    LinkedHashMap<SessionId, Session> sessions = _sessions.get(threadId);
		    if (sessions == null) {
		      return;
		    }
		    while (!sessions.isEmpty()) {
		      killSession(quitSession);
		    }
		    _sessions.remove(threadId);
		  }

	  public static void killSession(boolean quitSession) {
		    long threadId = Thread.currentThread().getId();
		    LinkedHashMap<SessionId, Session> sessions = _sessions.get(threadId);
		    if (sessions == null) {
		      return;
		    }
		    if (!sessions.isEmpty()) {
		      try {
		        Session session = getSession();
		        if (quitSession) {
		          session.getDriver().quit();
		        }
		        sessions.remove(session.getSessionId());
		        if (sessions.size() > 0) {
		          List<Entry<SessionId, Session>> s = new ArrayList<>(sessions.entrySet());
		          _activeSessions.put(threadId, s.get(s.size() - 1).getValue());
		        } else {
		          _activeSessions.remove(threadId);
		        }
		      } catch ( Exception e) {

		      }
		      if (sessions.isEmpty()) {
		        _sessions.remove(threadId);
		      }
		    }
		  }

	  private static class Session {
		    private WebDriver _driver;
		    private SessionId _sessionId;

		    public Session(WebDriver driver) {
		      _driver = driver;
		      _sessionId = ((RemoteWebDriver) _driver).getSessionId();
		    }

		    public WebDriver getDriver() {
		      return _driver;
		    }

		    public SessionId getSessionId() {
		      return _sessionId;
		    }

		  }

}
