package codeGen_TS;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Util {

    /**
     * Reads locators (key-value pairs) from an Excel file and stores them in a HashMap.
     *
     * @param filePath  The path to the Excel file.
     * @param sheetName The name of the sheet containing the locators.
     * @return A HashMap containing locators where the key is the locator name and the value is the locator value.
     * @throws IOException If there is an error while reading the Excel file.
     */
    public static HashMap<String, String> readLocatorFromExcel(String filePath, String sheetName) throws IOException {
        // Create a new HashMap to store the locators.
        HashMap<String, String> locatorMap = new HashMap<>();

        // Open the Excel file for reading using FileInputStream.
        FileInputStream fis = new FileInputStream(filePath);
        // Create a new XSSFWorkbook to represent the Excel workbook.
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        // Get the specified sheet from the workbook.
        XSSFSheet sheet = workbook.getSheet(sheetName);

        // Iterate through each row in the sheet to read the locators.
        for (Row row : sheet) {
            // Get the cell containing the locator key (column index 1).
            Cell keyCell = row.getCell(0);
            // Get the cell containing the locator value (column index 2).
            Cell valueCell = row.getCell(2);

            // Check if both key and value cells are not null.
            if (keyCell != null && valueCell != null) {
                // Read the key and value as strings from the cells.
                String key = keyCell.getStringCellValue();
                String value = valueCell.getStringCellValue();
                // Put the key-value pair into the locatorMap.
                locatorMap.put(key, value);
            }
        }

        // Close the workbook and FileInputStream to release resources.
        workbook.close();
        fis.close();

        // Return the HashMap containing the locators.
        return locatorMap;
    }

    /**
     * Reads test data (key-value pairs) for a specific test scenario from an Excel file and stores them in a HashMap.
     *
     * @param filePath     The path to the Excel file.
     * @param sheetName    The name of the sheet containing the test data.
     * @param testScenario The specific test scenario to read data for.
     * @return A HashMap containing test data where the key is the data field and the value is the corresponding value.
     * @throws IOException If there is an error while reading the Excel file.
     */
    public static HashMap<String, String> readTestDataFromExcel(String filePath, String sheetName, String testScenario) throws IOException {
        // Create a new HashMap to store the test data.
        HashMap<String, String> testDataMap = new HashMap<>();

        // Open the Excel file for reading using FileInputStream.
        FileInputStream fis = new FileInputStream(filePath);
        // Create a new XSSFWorkbook to represent the Excel workbook.
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        // Get the specified sheet from the workbook.
        XSSFSheet sheet = workbook.getSheet(sheetName);

        // Get the header row to determine the column indexes of the test data.
        Row headerRow = sheet.getRow(0);
        int testScenarioIndex = -1;
        int emailIndex = -1;
        int passwordIndex = -1;

        // Find the column indexes for the test data fields.
        for (Cell cell : headerRow) {
            String headerValue = cell.getStringCellValue().trim();
            if (headerValue.equalsIgnoreCase("TestScenario")) {
                testScenarioIndex = cell.getColumnIndex();
            } else if (headerValue.equalsIgnoreCase("email")) {
                emailIndex = cell.getColumnIndex();
            } else if (headerValue.equalsIgnoreCase("password")) {
                passwordIndex = cell.getColumnIndex();
            }
        }

        // Check if all required columns are found.
        if (testScenarioIndex == -1 || emailIndex == -1 || passwordIndex == -1) {
            extracted();
        }

        // Iterate through each row in the sheet (skipping the header row).
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);

            // Read the test scenario value from the current row.
            Cell scenarioCell = row.getCell(testScenarioIndex);
            if (scenarioCell != null) {
                String scenario = scenarioCell.getStringCellValue().trim();
                // Check if the scenario matches the specified testScenario.
                if (scenario.equalsIgnoreCase(testScenario)) {
                    // Read the email and password values from the current row.
                    Cell emailCell = row.getCell(emailIndex);
                    Cell passwordCell = row.getCell(passwordIndex);

                    // Check if both email and password cells are not null.
                    if (emailCell != null && passwordCell != null) {
                        // Read the email and password as strings from the cells.
                        String email = emailCell.getStringCellValue().trim();
                        String password = passwordCell.getStringCellValue().trim();
                        // Put the email and password into the testDataMap.
                        testDataMap.put("email", email);
                        testDataMap.put("password", password);
                        break; // Stop iterating after finding the matching test scenario data.
                    }
                }
            }
        }

        // Close the workbook and FileInputStream to release resources.
        workbook.close();
        fis.close();

        // Return the HashMap containing the test data.
        return testDataMap;
    }

	private static void extracted() {
		throw new RuntimeException("Required columns not found in TestData sheet.");
	}
}