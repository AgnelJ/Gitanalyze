package codeGen_TS;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;

public class LoginPage extends BasePage {

    private HashMap<String, String> locators;

    public LoginPage(HashMap<String, String> locators) {
        this.locators = locators;
    }

    public void openApplication() {
        driver.get("https://ava-eus-cdgen-np-webapp.azurewebsites.net/");
    }

    public void clickElement(String elementKey) {
        String xpath = locators.get(elementKey);
        if (xpath != null) {
            try {
                // Wait for the element to be visible before clicking
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
                element.click();
            } catch (Exception e) {
                // Handle any exception that may occur during the click
                System.out.println("Error while clicking the element: " + e.getMessage());
            }
        } else {
            System.out.println("Element not found in the locators map: " + elementKey);
        }
    }

    public void enterText(String elementKey, String text) {
        String xpath = locators.get(elementKey);
        if (xpath != null) {
            try {
                // Wait for the element to be visible before entering text
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
                element.sendKeys(text);
            } catch (Exception e) {
                // Handle any exception that may occur during text entry
                System.out.println("Error while entering text: " + e.getMessage());
            }
        } else {
            System.out.println("Element not found in the locators map: " + elementKey);
        }
    }

    public boolean isElementPresent(String elementKey) {
        String xpath = locators.get(elementKey);
        if (xpath != null) {
            return !driver.findElements(By.xpath(xpath)).isEmpty();
        } else {
            System.out.println("Element not found in the locators map: " + elementKey);
            return false;
        }
    }

    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    
}
