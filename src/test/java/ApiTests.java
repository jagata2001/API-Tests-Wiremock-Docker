import data.DataProviders;
import io.restassured.response.ValidatableResponse;
import models.ErrorResponse;
import models.Users;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.TestResultListener;
import utils.TestSetUp;

import java.util.Map;

import static clients.Users.getUsers;
import static data.Data.*;

@Listeners(TestResultListener.class)
public class ApiTests extends TestSetUp {
    @Test
    public void getAllUsersTest() {
        ValidatableResponse response = getUsers(allUserParams);

        response.assertThat().statusCode(200);
        Users users = response.extract().as(Users.class);
        Assert.assertEquals(users.compare(users, usersData), 0, "Response data do not match");
    }

    @Test(dataProvider = "filterProviderPositive", dataProviderClass = DataProviders.class)
    public void filterTests(String testName, Map<String, Object> queryParams, int expectedStatus, Users expectedResponse, int expectedTotalCount) {

        ValidatableResponse response = getUsers(queryParams);
        response.assertThat().statusCode(expectedStatus);

        Users users = response.extract().as(Users.class);
        Assert.assertEquals(users.compare(users, expectedResponse), 0, "Response data do not match on test: %s".formatted(testName));
        Assert.assertEquals(users.getTotal(), expectedTotalCount, "Response do not contain expected total value on test %s".formatted(testName));
    }

    @Test(dataProvider = "sortProviderPositive", dataProviderClass = DataProviders.class)
    public void sortTests(String testName, Map<String, Object> queryParams, int expectedStatus, Users expectedResponse) {
        ValidatableResponse response = getUsers(queryParams);
        response.assertThat().statusCode(expectedStatus);

        Users users = response.extract().as(Users.class);
        Assert.assertEquals(users.compare(users, expectedResponse), 0, "Response data do not match on test: %s".formatted(testName));
        Assert.assertEquals(users.getTotal(), expectedResponse.getTotal(), "Response do not contain expected total value on test %s".formatted(testName));
    }

    @Test(dataProvider = "paginationProviderPositive", dataProviderClass = DataProviders.class)
    public void paginationTests(String testName, Map<String, Object> queryParams, int expectedStatus, Users expectedResponse) {
        ValidatableResponse response = getUsers(queryParams);
        response.assertThat().statusCode(expectedStatus);

        Users users = response.extract().as(Users.class);
        Assert.assertEquals(users.compare(users, expectedResponse), 0, "Response data do not match on test: %s".formatted(testName));
        Assert.assertEquals(users.getTotal(), expectedResponse.getTotal(), "Response do not contain expected total value on test %s".formatted(testName));
        Assert.assertEquals(users.getSize(), expectedResponse.getSize(), "Response do not contain expected size value on test %s".formatted(testName));
        Assert.assertEquals(users.getPage(), expectedResponse.getPage(), "Response do not contain expected page value on test %s".formatted(testName));
    }

    @Test(dataProvider = "invalidParameterProviderNegative", dataProviderClass = DataProviders.class)
    public void invalidParameterValueTests(String testName, Map<String, Object> queryParams, int expectedStatus, ErrorResponse expectedResponse) {
        ValidatableResponse response = getUsers(queryParams);
        response.assertThat().statusCode(expectedStatus);

        ErrorResponse errorResponse = response.extract().as(ErrorResponse.class);
        Assert.assertEquals(errorResponse, expectedResponse, "Response data do not match on test: %s".formatted(testName));
    }

    @Test
    public void getUsersInternalServerErrorTest() {
        ValidatableResponse response = getUsers(emptyParams);
        response.assertThat().statusCode(500);
    }
}
