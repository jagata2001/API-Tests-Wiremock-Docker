package mocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static data.Configuration.port;

public class MockServerManager {
    private static WireMockServer server;

    public static void startServer() {
        if (server == null) {
            server = new WireMockServer(
                    wireMockConfig()
                            .port(port)
                            .bindAddress("0.0.0.0")
                            .extensions(new UserResponseTransformer())
                    //.notifier(new ConsoleNotifier(true))

            );
            server.start();
        }
    }

    public static void stopServer() {
        if (server != null && server.isRunning()) {
            server.stop();
        }
    }

    public static WireMockServer getInstance() {
        return server;
    }
}
