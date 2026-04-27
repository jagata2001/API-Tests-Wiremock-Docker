package utils;

import db.TestResults;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Objects;

public class TestResultListener implements ITestListener {
    private final TestResults testResults = new TestResults();
    @Override
    public void onTestSuccess(ITestResult result) {
        saveResult(result, "PASSED");

    }

    @Override
    public void onTestFailure(ITestResult result) {
        saveResult(result, "FAILED");
    }

    public void saveResult(ITestResult result, String status) {
        Object[] params = result.getParameters();
        String subName = params.length > 0 ? params[0].toString() : "";
        String testName = Objects.equals(subName, "") ? result.getName() : "%s_%s".formatted(result.getName(), subName);
        try {
            testResults.insertResult(testName, status, new Date(System.currentTimeMillis()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}