package codeGen_TS;

// Import statements for necessary classes and libraries
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class LoginTest {
    // Class-level variables
    private LoginPage loginPage;
    private SoftAssert softAssert = new SoftAssert(); // Soft assert to continue test execution even if some assertions fail

    private static ExtentTest extentTest; // ExtentTest object to log test steps and results in Extent Report
    private boolean isLoginSuccess = true; // Flag to track if login was successful

    @BeforeClass
    public void setUp() throws IOException {
        // Setting up the test environment before executing the test case
        BasePage basePage = new BasePage();
        basePage.initialize();

        // Read locators from the "Sheet1" of the Excel file to interact with UI elements
        String filePath = "./src/main/resources/Application Locators.xlsx";
        HashMap<String, String> locator = Util.readLocatorFromExcel(filePath, "Sheet1");
        loginPage = new LoginPage(locator);

        // Setup Extent Report for test reporting
        ExtentReportUtil.initializeExtentReport();
        extentTest = ExtentReportUtil.createTest("CodeGen Login Test", "Verify HTML to FRD functionality");
    }

    @Test
    public void testLogin() throws InterruptedException, IOException {
        // Start logging the test steps in the Extent Report
        extentTest.log(Status.INFO, "Starting the Login Test");

        // Prepare test data for login using the "Login Test" scenario from the Excel file
        String testScenario = "Login Test";
        String filePath = "src/main/resources/Application Locators.xlsx";
        HashMap<String, String> testData = Util.readTestDataFromExcel(filePath, "TestData", testScenario);

        // Print the test data (for debugging purposes)
        System.out.println("Test Data: " + testData);

        // Perform the login steps
        loginPage.openApplication();
        loginPage.sleep(2);
        loginPage.clickElement("login-btn");
        loginPage.sleep(2);

        // Switch the WebDriver's focus to the pop-up window
        String mainWindowHandle = BasePage.driver.getWindowHandle();
        for (String handle : BasePage.driver.getWindowHandles()) {
            if (!handle.equals(mainWindowHandle)) {
                BasePage.driver.switchTo().window(handle);
                break;
            }
        }

        // Interact with the pop-up window elements
        loginPage.enterText("email-field", testData.get("email"));
        loginPage.sleep(2);
        loginPage.clickElement("submit-btn");
        loginPage.sleep(2);
        loginPage.enterText("password-field", testData.get("password"));
        loginPage.sleep(2);
        loginPage.clickElement("sign-in-btn");
        loginPage.sleep(5);
        loginPage.clickElement("call-btn");
        loginPage.sleep(5);
        loginPage.clickElement("checkbox-KmsiCheckboxField");
        loginPage.sleep(5);
        loginPage.clickElement("button-idSIButton9");
        loginPage.sleep(20);

        // Switch back the WebDriver's focus to the main window
        BasePage.driver.switchTo().window(mainWindowHandle);
        loginPage.clickElement("button-HTML to FRD#46ACA492-E042-4729-A340-A08FB587BC35");
        loginPage.sleep(5);

        // Scroll to the bottom of the page
        loginPage.scrollToBottom();

        loginPage.clickElement("link-fill out this form");
        loginPage.sleep(2);
        loginPage.enterText("input-HTML Code", "<form>\n <label for=\"username\">Username:</label>\n</form>");
        loginPage.sleep(2);

        // Soft assert login success or failure based on your application behavior
        isLoginSuccess = isLoginSuccessful();

     // Take a screenshot in case of test failure
        if (!isLoginSuccess) {
            String screenshotPath = takeScreenshot("login_test_failure.png");
            extentTest.addScreenCaptureFromPath(screenshotPath); // Add the screenshot to the Extent Report
            extentTest.log(Status.FAIL, "Login Test Failed");
        } else {
            extentTest.log(Status.PASS, "Login successful!");
        }

        // Perform soft assert to check for failures
        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            // Re-throw the assertion error to fail the test
            throw e;
        }
    }

    // Helper method to check if login was successful based on the presence of a welcome message
    private boolean isLoginSuccessful() {
        String welcomeMessageLocator = "//*[@id=\"root\"]/nav/div/a[2]/img"; // Replace with the actual locator of the welcome message element
        return loginPage.isElementPresent(welcomeMessageLocator);
    }

    // Helper method to take a screenshot and save it to a file
    private String takeScreenshot(String fileName) throws IOException {
        File srcFile = ((TakesScreenshot) BasePage.driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(fileName);
        FileUtils.copyFile(srcFile, destFile);
        return destFile.getAbsolutePath(); // Return the absolute path of the screenshot
    }


    @AfterClass
    public void tearDown() {
        loginPage.quit();
        ExtentReportUtil.flushReport(); // Flush the report after the test execution
    }

}
