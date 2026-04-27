package data;

public class Configuration {
    public static final String host = "localhost";
    public static final String baseUrl = "http://%s".formatted(host);
    public static final String basePath = "/api";
    public static final int port = 8088;
    public static final String databaseName = "TestData";
}
