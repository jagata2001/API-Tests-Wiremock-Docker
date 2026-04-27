package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import mocks.MockServerManager;
import mocks.UsersMock;

public class ServerSetUp {
    public static void main(String[] args) throws JsonProcessingException {
        MockServerManager.startServer();
        UsersMock.setupUsersFilterableStub();
    }
}
