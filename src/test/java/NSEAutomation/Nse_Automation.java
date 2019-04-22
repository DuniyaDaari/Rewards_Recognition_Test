package NSEAutomation;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Nse_Automation {

    public static String URL = "https://www.nseindia.com/products/content/equities/indices/historical_index_data.htm";
    WebDriver driver;

    @BeforeClass
    public void launchUrl() throws Exception {

        //Setting chromedriver path
        File resourcesDirectory = new File("src/test/resources/chromedriver.exe");
        String absolutePath = resourcesDirectory.getAbsolutePath();
        System.setProperty("webdriver.chrome.driver", absolutePath);

        // initializing webdriver
        driver = new ChromeDriver();

        //Launching NSE website
        driver.get(URL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Maximizing the window
        driver.manage().window().maximize();
    }

    @AfterClass
    public void closeUrl() throws Exception {
        Thread.sleep(2000);
        driver.close();
    }

    @Test
    public void downloadHistoricalDataForNifty50() throws Exception {
        // Verifying whether desired page is loaded properly
        WebElement actualTitle = driver.findElement(By.xpath("//h2[contains(text(),'Historical Index Data')]"));
        Assert.assertEquals(actualTitle.getText().toString(), "Historical Index Data", "Page is incorrect");

        //entering to and from date
        String fromDate = "01-01-1990";
//        String toDate = "31-12-1990";

        Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromDate);

        Date endDate;//=new SimpleDateFormat("dd-MM-yyyy").parse(toDate);


        Date today = Calendar.getInstance().getTime();

        ZoneId defaultZoneId = ZoneId.systemDefault();
        //Converting the date to Instant
        Instant instantStart = startDate.toInstant();
//        Instant instantEnd = endDate.toInstant();
        //Converting the Date to LocalDate
        LocalDate localStartDate = instantStart.atZone(defaultZoneId).toLocalDate();
        LocalDate localEndDate;// = instantEnd.atZone(defaultZoneId).toLocalDate();

        Boolean hasReachedToday = false;

        while (!hasReachedToday) {
            // changing Start and end date for next iteration
            localEndDate = localStartDate.plusDays(364);

            endDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (endDate.compareTo(today) >= 0) {
                endDate = today;
                hasReachedToday = true;
            }

            driver.navigate().refresh();
            Select dropDown = new Select(driver.findElement(By.id("indexType")));
            dropDown.selectByValue("NIFTY 50");

            enterDate(startDate, endDate);

            // Click on get Time button
            driver.findElement(By.xpath("//input[@id='get']")).click();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            //Click on download link
            WebElement element = driver.findElement(By.xpath("//a[contains(text(),'Download file in csv format')]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(500);
            element.click();

            localStartDate = localStartDate.plusDays(365);
            startDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        }
    }

    private void enterDate(Date fromDate, Date toDate) throws Exception {
        String pattern = "dd-MM-yyyy";
        DateFormat df = new SimpleDateFormat(pattern);


        String strDate = df.format(fromDate);
        String endDate = df.format(toDate);

        //Entering start date
        driver.findElement(By.xpath("//input[@id='fromDate']")).sendKeys(strDate);
        //Entering to date
        driver.findElement(By.xpath("//input[@id='toDate']")).sendKeys(endDate);
    }
}
