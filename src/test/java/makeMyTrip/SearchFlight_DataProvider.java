package makeMyTrip;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class SearchFlight_DataProvider {

	public WebDriver driver;

	@BeforeMethod
	public void launchBrowser() throws Exception {
		File resourcesDirectory = new File("src/test/resources/chromedriver.exe");
		String absolutePath = resourcesDirectory.getAbsolutePath();
		System.setProperty("webdriver.chrome.driver", absolutePath);

		driver = new ChromeDriver();
		driver.get("https://www.makemytrip.com/flights/");
		driver.manage().window().maximize();
		driver.navigate().refresh();

	}

	@AfterMethod
	public void closeBrowser() {
		driver.quit();
	}

	/*
	 * @DataProvider(name = "PassengerTravelInfo") public static Object[][]
	 * passengerTravelInfo() {
	 * 
	 * return new Object[][] { { "New York", "Kolkata", "18-April-2019" }, {
	 * "Bangalore", "Delhi", "23-April-2020" }, { "Gwalior", "Mumbai",
	 * "29-April-2020" } }; }
	 */

	@DataProvider(name = "PassengerTravelInfoByPropertyFile")
	public static Object[][] passengerTravelInfoByPropertyFile() {

		Properties pro = new Properties();

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream stream = loader.getResourceAsStream("application.properties");
		try {
			pro.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String sourceCities = pro.getProperty("sourceCity");
		String destinationCities = pro.getProperty("destinationCity");
		String travelDates = pro.getProperty("travelDate");

		String sourceCitiesArray[] = sourceCities.split(",");
		String destinationCitiesArray[] = destinationCities.split(",");
		String travelDatesArray[] = travelDates.split(",");

		int rowSize = sourceCitiesArray.length;
		String passengerInfo1[][] = new String[rowSize][3];

		for (int i = 0; i < rowSize; i++) {
			passengerInfo1[i][0] = sourceCitiesArray[i];
			passengerInfo1[i][1] = destinationCitiesArray[i];
			passengerInfo1[i][2] = travelDatesArray[i];
		}
		return passengerInfo1;
	}

	@Test(dataProvider = "PassengerTravelInfoByPropertyFile")
	public void searchFlight(String sourceCity, String destinationCity, String travelDate) throws Exception {
		Thread.sleep(5000);

		driver.findElement(By.xpath("//input[@id='fromCity']")).sendKeys(sourceCity);
		WebDriverWait wait = new WebDriverWait(driver, 10000);
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//li//div//p[contains(text(), '" + sourceCity + "')]")));
		Thread.sleep(2000);
		driver.findElement(By.xpath("//li//div//p[contains(text(), '" + sourceCity + "')]")).click();

		Thread.sleep(5000);

		driver.findElement(By.xpath("//input[@id='toCity']")).sendKeys(destinationCity);
		Thread.sleep(5000);
		wait = new WebDriverWait(driver, 10000);
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//li//div//p[contains(text(), '" + destinationCity + "')]")));
		Thread.sleep(2000);
		driver.findElement(By.xpath("//li//div//p[contains(text(), '" + destinationCity + "')]")).click();

		Thread.sleep(2000);

		String currentMonthYear = driver.findElement(By.xpath("//div[@class='DayPicker-Month'][1]//div[1]//div"))
				.getText();
		String currentYear = currentMonthYear.split(" ")[1];

		String expectedDate = travelDate.split("-")[0];
		String expectedMonth = travelDate.split("-")[1];
		String expectedYear = travelDate.split("-")[2];

		Date travelDepartDate = new SimpleDateFormat("dd-MMMM-yyyy").parse(travelDate);

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy");
		Date todaysDate = new Date();
		System.out.println(formatter.format(todaysDate));

		long diffInMillies = Math.abs(travelDepartDate.getTime() - todaysDate.getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

		if (todaysDate.compareTo(travelDepartDate) > 0) {
			System.out.println("Date is disabled as travel date can not be selected before todays date.");
		} else if (diff > 365) {
			System.out.println("Date is disabled as travel date can not be selected after one year of todays date.");
		} else {
			// Selection of year
			if (currentYear.equals(expectedYear)) {
				System.out.println("Expected Year is already selected.");
			} else {
				for (int i = 1; i < 12; i++) {
					driver.findElement(By.xpath("//span[@aria-label='Next Month']")).click();
					Thread.sleep(1000);
					currentMonthYear = driver.findElement(By.xpath("//div[@class='DayPicker-Month'][1]//div[1]//div"))
							.getText();
					currentYear = currentMonthYear.split(" ")[1];
					if (currentYear.equals(expectedYear)) {
						System.out.println("Expected year is get selected now.");
						break;
					}
				}
			}

			// Selection of Month
			int next = 0;
			String currentMonth = currentMonthYear.split(" ")[0];
			if (currentMonth.equals(expectedMonth)) {
				next = 1;
				System.out.println("Expected month is already selected");
			} else {
				for (int i = 1; i < 12; i++) {
					if (driver.findElement(By.xpath("//span[@aria-label='Next Month']")).isDisplayed()) {
						driver.findElement(By.xpath("//span[@aria-label='Next Month']")).click();

						Thread.sleep(1000);
						currentMonthYear = driver
								.findElement(By.xpath("//div[@class='DayPicker-Month'][1]//div[1]//div")).getText();
						currentMonth = currentMonthYear.split(" ")[0];
						if (currentMonth.equals(expectedMonth)) {
							next = 1;
							System.out.println("Expected month is get selected now.");
							break;
						}

					} else {
						currentMonthYear = driver
								.findElement(By.xpath("//div[@class='DayPicker-Month'][2]//div[1]//div")).getText();
						currentMonth = currentMonthYear.split(" ")[0];
						if (currentMonth.equals(expectedMonth)) {
							next = 2;
							System.out.println("Expected month is get selected now.");
							break;
						}
					}

				}
			}
			// Selection of Date
			if (next == 1) {
				driver.findElement(By.xpath("//div[@class='DayPicker-Month'][1]//p[text()=" + expectedDate + "]"))
						.click();
			} else if (next == 2) {
				driver.findElement(By.xpath("//div[@class='DayPicker-Month'][2]//p[text()=" + expectedDate + "]"))
						.click();
			}
			// Search flights
			driver.findElement(By.xpath("//a[contains(text(),'Search')]")).click();
		}

	}
}
