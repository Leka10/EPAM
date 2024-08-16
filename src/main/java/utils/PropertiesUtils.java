package utils;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils extends LoggerUtils{

    private static final String sPropertiesPath = "common.properties";

    private static final Properties properties = loadPropertiesFile();


    public static Properties loadPropertiesFile(String sFilePath) {
        InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(sFilePath);
        Properties properties = new Properties();
        if(inputStream == null) {
            throw new RuntimeException("Cannot find " + sFilePath + " file in the classpath!");
        }
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            Assert.fail("Cannot load " + sFilePath + " file! Message: " + e.getMessage());
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Failed to close input stream! Message: " + e.getMessage(), e);
            }
        }
        return properties;

    }
    private static Properties loadPropertiesFile() {
        return loadPropertiesFile(sPropertiesPath);
    }

    private static String getProperty(String sProperty) {
        String sResult = properties.getProperty(sProperty);
        Assert.assertNotNull(sResult, "Cannot find property '" + sProperty + "' in " + sPropertiesPath + " file!");
        return sResult;
    }

    public static String getEnvironment(){
        return getProperty("environment");
    }
    public static String getBaseUrl() {
        return getProperty("testBaseURL");
    }
    public static String getBrowser() {
        return getProperty("browser");
    }
    public static boolean getRemote() {
        return Boolean.parseBoolean(getProperty("remote"));
    }
    public static boolean getHeadless() {
        return Boolean.parseBoolean(getProperty("headless"));
    }
    public static String getDriversFolder() {
        return getProperty("driversFolder");
    }


}
