package utils;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static utils.PropertiesUtils.getBaseUrl;

public class WebDriverUtils extends LoggerUtils{

    public static WebDriver setUpDriver() {
        WebDriver driver = null;

        String sBrowser = PropertiesUtils.getBrowser();
        boolean bRemote = PropertiesUtils.getRemote();
        boolean bHeadless = PropertiesUtils.getHeadless();
        String sDriversFolder = PropertiesUtils.getDriversFolder();
        String sHubUrl = "";

        String sPathDriverChrome = sDriversFolder + "chromedriver.exe";
        String sPathDriverFirefox = sDriversFolder + "geckodriver.exe";
        String sPathDriverEdge = sDriversFolder + "msedgedriver.exe";

        String sRemote = "";
        if (bRemote) {
            sRemote = "Remote";
        }

        log.debug("setUP" + sRemote + "setUpDriver(" + sBrowser + ", Is headless: " + bHeadless + ")");

        try {
            switch (sBrowser) {
                case "chrome": {
                    ChromeOptions options = new ChromeOptions();
//                    options.addArguments("--headless=new");
//                    options.addArguments("--window-size=1600x900");
                    if (bRemote) {
                        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(sHubUrl), options);
//                        remoteDriver.setFileDetector(new LocalFileDetector());
                        driver = remoteDriver;

                    } else {
                        System.setProperty("webdriver.chrome.driver", sPathDriverChrome);
                        driver = new ChromeDriver(options);
                    }
                    break;
                }
                case "firefox": {
                    FirefoxOptions options = new FirefoxOptions();
//                options.addArguments("--headless=new");
//                    options.addArguments("--window-size=1600x900");

                    if (bRemote) {
                        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(sHubUrl), options);
//                        remoteDriver.setFileDetector(new LocalFileDetector());
                        driver = remoteDriver;

                    } else {
                        System.setProperty("webdriver.gecko.driver", sPathDriverFirefox);
                        driver = new FirefoxDriver(options);
                    }
                    break;
                }
                case "edge": {
                    EdgeOptions options = new EdgeOptions();
//                options.addArguments("--headless=new");
//                    options.addArguments("--window-size=1600x900");

                    if (bRemote) {
                        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(sHubUrl), options);
//                        remoteDriver.setFileDetector(new LocalFileDetector());
                        driver = remoteDriver;

                    } else {
                        System.setProperty("webdriver.edge.driver", sPathDriverEdge);
                        driver = new EdgeDriver(options);
                    }
                    break;
                }
                default: {
                    Assert.fail("Cannot create driver! Broswer '" + sBrowser + "' driver is not correct!");
                }
            }
        } catch (MalformedURLException e) {
            Assert.fail("Cannot create driver! Path to browser '" + sBrowser + "' driver is not correct!");
        }



        // Maximize Browser
        driver.manage().window().maximize();
        return driver;
    }

    public static boolean hasDriverQuit(WebDriver driver) {
        if(driver != null) {
            return ((RemoteWebDriver) driver).getSessionId() == null;
        } else {
            return true;
        }
    }

    public static void quitDriver(WebDriver driver) {
        if (!hasDriverQuit(driver)) {
            driver.quit();
        }
    }


}
