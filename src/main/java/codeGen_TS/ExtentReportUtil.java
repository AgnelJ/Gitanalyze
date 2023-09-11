package codeGen_TS;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportUtil {
    private static ExtentReports extentReports;
    private static ExtentTest extentTest;

    // Initialize the Extent Report
    public static void initializeExtentReport() {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        htmlReporter.config().setDocumentTitle("Extent Report");
        htmlReporter.config().setReportName("Test Automation Report");

        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
    }

    // Create a new test in the Extent Report
    public static ExtentTest createTest(String testName, String testDescription) {
        extentTest = extentReports.createTest(testName, testDescription);
        return extentTest;
    }

    // Log a pass status in the Extent Report
    public static void logPass(String message) {
        extentTest.log(Status.PASS, message);
    }

    // Log a fail status in the Extent Report
    public static void logFail(String message) {
        extentTest.log(Status.FAIL, message);
    }

    // Add a screenshot to the Extent Report
    public static void addScreenshot(String screenshotPath) {
        extentTest.addScreenCaptureFromPath(screenshotPath);
    }

    // Flush the Extent Report to generate the final report
    public static void flushReport() {
        extentReports.flush();
    }
}
