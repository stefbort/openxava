package org.openxava.test.tests;

import java.util.*;

import org.openqa.selenium.*;

public class DateCalendarTest extends WebDriverTestBase {
	
	private WebDriver driver;

	public void setUp() throws Exception {
		driver = createWebDriver();
	}

	public void testNavigation() throws Exception {
		forTestMultipleDateWithOnChange();
	}

	public void tearDown() throws Exception {
		driver.quit();
	}
	
	private void forTestMultipleDateWithOnChange() throws Exception {
		driver.get("http://localhost:8080/openxavatest/m/Event");
		wait(driver);
		acceptInDialogJS(driver);
		moveToListView(driver);
		WebElement element = driver.findElement(By.xpath("//a[@onclicke=\"javascript:openxava.executeAction('openxavatest', 'Event', '', false, 'List.viewDetail', 'row=0')\"]"));
		element.click();
		wait(driver);
		List<WebElement> iconElements = driver.findElements(By.cssSelector("i.mdi.mdi-calendar"));
		if (!iconElements.isEmpty()) {
		    WebElement firstIconElement = iconElements.get(0);
		    firstIconElement.click();
		}
		Thread.sleep(500);
		List<WebElement> spanElements = driver.findElements(By.xpath("//div[@class='dayContainer']//span[@class='flatpickr-day ' and text()='8']"));
		if (!spanElements.isEmpty()) {
		    WebElement firstSpanElement = spanElements.get(0);
		    firstSpanElement.click();
		}
		wait(driver);
		WebElement divElement = driver.findElement(By.cssSelector("div.ox-message-box"));
		String message = divElement.getText();
		String expectedMessage = "Calendar changed";

		assertEquals(expectedMessage, message);
		goToListFromDetailView(driver, "Event");
		acceptInDialogJS(driver);
	}
	

	
}
