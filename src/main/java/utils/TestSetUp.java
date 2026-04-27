package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import db.SetUp;
import io.restassured.RestAssured;
import mocks.MockServerManager;
import mocks.UsersMock;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import static data.Configuration.*;

public class TestSetUp {
    @BeforeSuite
    public void suitSeUp() throws JsonProcessingException {
        RestAssured.baseURI = baseUrl+basePath;
        RestAssured.port = port;
        if(!dockerIsRunning) {
            System.out.println("Running from local");
            MockServerManager.startServer();
            UsersMock.setupUsersFilterableStub();
        }
        new SetUp().createTables().closeConn();
    }

    @AfterSuite
    public void suitTearDown() {
        MockServerManager.stopServer();
    }
}
