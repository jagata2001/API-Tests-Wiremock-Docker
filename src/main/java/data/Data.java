package data;

import models.Users;

import java.util.HashMap;
import java.util.Map;

import static utils.Helpers.loadData;

public class Data {
    public static final Users usersData = loadData("userData.json");

    public static final Map<String, Object> allUserParams = Map.of(
            "page", 1,
            "size", 2000
    );

    public static final Map<String, Object> emptyParams = Map.of();
}
