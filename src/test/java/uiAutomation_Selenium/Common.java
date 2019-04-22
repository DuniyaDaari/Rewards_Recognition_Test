package uiAutomation_Selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Common {

    private static WebDriver driver;

    private static Properties pro = new Properties();

    public static String getPropertyValue(String propertyKey) throws IOException {
        String propertyValue= null;
        try{
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream("application.properties");
            pro.load(stream);
            propertyValue =  pro.getProperty(propertyKey);
        } catch(Exception e){
        e.getMessage();
        }
        return propertyValue;
    }

    public static WebDriver getDriver(String browserName) throws Exception {
        File resourcesDirectory = new File("src/test/resources/chromedriver.exe");
        String absolutePath = resourcesDirectory.getAbsolutePath();
        switch (browserName) {
            case "CHROME":
                System.setProperty("webdriver.chrome.driver", absolutePath);
                driver = new ChromeDriver();
                break;
            case "FIREFOX":
                driver = new FirefoxDriver();
                break;
            case "IE":
                driver = new InternetExplorerDriver();
                break;
            default:
                throw new IllegalArgumentException("Given Browser Name is not applicable for RR portal.");
        }
        return driver;
    }

    public static String getBaseUrl() throws Exception {
        return Common.getPropertyValue("baseUrl");
    }

}