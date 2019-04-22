package uiAutomation_Selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class RR_AdminManagerTest {

    public WebDriver driver;


    @BeforeClass
    public void launchAdminPage() throws Exception {
        String browserName = Common.getPropertyValue("browserName");
        driver = Common.getDriver(browserName);
        String baseUrl = Common.getPropertyValue("baseUrl");
        String adminPageUrl = baseUrl + "";

        driver.get(adminPageUrl);
        driver.navigate().refresh();
        driver.manage().window().maximize();
    }

/*    @AfterClass
    public void closeAdminPage() throws Exception {
        driver.close();
    }*/

    @Test(priority = 0)
    public void login() throws Exception {

        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("alok.shrivastava1989@gmail.com");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Admin#222");
        driver.findElement(By.xpath("//input[@id='okta-signin-submit']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.titleContains("Rewards & Recognition"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(@title,'')]")));
        String expectedHomePageTitle = "Welcome to your dashboard!";
        String actualHomePageTitle = driver.findElement(By.xpath("//h1[contains(@title,'')]")).getText().toString();
        Assert.assertEquals(actualHomePageTitle, expectedHomePageTitle);
    }

    @Test(priority = 1)
    public void assignReward() throws Exception {

        String employeeName = "Sachin Nikam";
        String rewardName = "Pat on the back reward";
        // Click on team
        Thread.sleep(5000);
        // driver.findElement(By.linkText("Get Started ->")).click();
        // driver.findElement(By.xpath("//div[@class='col-sm-3'][2]/div/div/div/a")).click();

        System.out.println(driver.findElement(By.xpath("//h5[text()='View my teams']/following-sibling::a")).getText());
        driver.findElement(By.xpath("//h5[text()='View my teams']/following-sibling::a")).click();
        Thread.sleep(5000);
        try {
            if (driver.findElement(By.xpath("//button[text()='ACTIVE']")).isDisplayed()) {
                driver.findElement(By.xpath("//a[text()='Details']")).click();
            }
        } catch (Exception e) {
            throw new Exception("No team is Active");
        }
        // to fetch the given employee row and then click on reward button .

        //WebElement table = driver.findElement(By.className("table table-striped table-bordered table-hover table-responsive-xl"));
        Thread.sleep(5000);

        List<WebElement> allColumns = driver.findElements(By.xpath("//table/thead/tr[1]/th"));
        int colSize = allColumns.size();
       // int rowSize = driver.findElements(By.xpath("//table/tbody/tr")).size();
        System.out.println(colSize);

        List<WebElement> allRows = driver.findElements(By.xpath("//table/tbody/tr"));
        int rowSize = allRows.size();
        System.out.println(rowSize);
        int rowNo =0;
        int colNo =0;
        outerloop:
        for (int row = 1; row <= rowSize; row++) {
            for (int col = 1; col < colSize; col++) {
                System.out.println(driver.findElement(By.xpath("//table/tbody/tr[" + row + "]/td[" + col + "]")).getText());
                if (driver.findElement(By.xpath("//table/tbody/tr[" + row + "]/td[" + col + "]")).getText().contains(employeeName)) {
                    System.out.println("Employee Found");
                    System.out.println(col + " " + row);
                    rowNo = row;
                    colNo = col;
                    break outerloop;
                }
            }
        }
    }
}

