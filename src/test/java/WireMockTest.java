import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import com.github.tomakehurst.wiremock.client.WireMock;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@Slf4j
public class WireMockTest {
    private static GenericContainer<?> wireMockContainer;

    @BeforeAll
    public static void setUp() {
        wireMockContainer = new GenericContainer<>(DockerImageName.parse("rodolpheche/wiremock"))
                .withExposedPorts(8080)
                .withCommand("--verbose")
                .withStartupTimeout(Duration.ofMinutes(2))
                .withLogConsumer(new Slf4jLogConsumer(log));
        wireMockContainer.start();
        log.info("WireMock container started at: {}:{}", wireMockContainer.getHost(), wireMockContainer.getMappedPort(8080));

        WireMock.configureFor(wireMockContainer.getHost(), wireMockContainer.getMappedPort(8080));
        stubFor(get(urlEqualTo("/hellowiremock"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"message\": \"Hello, WireMock!\"}")));

        await().atMost(10, TimeUnit.SECONDS).until(wireMockContainer::isRunning);
    }

    @AfterAll
    public static void tearDown() {
        if (wireMockContainer != null && wireMockContainer.isRunning()) {
            wireMockContainer.stop();
            log.info("WireMock container stopped.");
        }
    }

    @Test
    public void testWireMock() {
        if (wireMockContainer == null || !wireMockContainer.isRunning()) {
            throw new IllegalStateException("WireMock container is not running");
        }

        String wireMockUrl = "http://" + wireMockContainer.getHost() + ":" + wireMockContainer.getMappedPort(8080);

        try (GenericContainer<?> ignored = wireMockContainer) {
            given()
                    .when()
                    .get(wireMockUrl + "/hellowiremock")
                    .then()
                    .statusCode(200)
                    .body("message", equalTo("Hello, WireMock!"));
        } catch (Exception e) {
            log.error("Error during WireMock test", e);
        }
    }
}