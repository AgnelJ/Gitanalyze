package codeGen_TS;

import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BasePage {
    public static WebDriver driver;

    public void initialize() {
        // Create a new instance of ChromeDriver
        driver = new ChromeDriver();
        // Maximize the browser window
        driver.manage().window().maximize();
    }

    public void quit() {
        // Check if the driver instance is not null
        if (driver != null) {
            // Quit the driver
            driver.quit();
        }
    }

    public void scrollToBottom() {
        // Create a new instance of JavascriptExecutor
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        // Execute JavaScript code to scroll to the bottom of the page
        jsExecutor.executeScript("window.scrollTo(0, document.documentElement.scrollHeight)");
    }

    public void switchToPopupWindow() {
        // Get all window handles
        Set<String> windowHandles = driver.getWindowHandles();

        // Find the handle of the pop-up window
        for (String handle : windowHandles) {
            // Check if the handle is not the current window handle
            if (!handle.equals(driver.getWindowHandle())) {
                // Switch to the pop-up window
                driver.switchTo().window(handle);
                break;
            }
        }
    }
}
