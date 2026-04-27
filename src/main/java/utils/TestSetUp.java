package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.client.WireMock;
import db.SetUp;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
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
        MockServerManager.startServer();
        UsersMock.setupUsersFilterableStub();
        // RestAssured.defaultParser = Parser.JSON;
        // MockServerManager.getInstance().saveMappings();
        new SetUp().createTables().closeConn();
    }

    @AfterSuite
    public void suitTearDown() {
        MockServerManager.stopServer();
    }
}
