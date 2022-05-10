package API;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

import org.apache.logging.log4j.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class RestAPI {
    public static final Duration timeout = Duration.ofSeconds(10);
    private static final Logger logger = LogManager.getLogger(RestAPI.class);

    final String OAuthToken;
    HttpClient client;
    final ObjectMapper objectMapper;

    public RestAPI(String OAuthToken) {
        this.OAuthToken = OAuthToken;
        objectMapper = new ObjectMapper();
    }

    public RestAPI() {
        this(null);
    }

    public HttpResponse<String> getHttpResponse(URI uri) throws IOException, InterruptedException {

        HttpRequest.Builder builder = HttpRequest.newBuilder();
        if (OAuthToken != null) {
            builder.headers("Authorization", "token " + OAuthToken);
        }
        HttpRequest httpRequest = builder.headers("Accept", "application/vnd.github.v3+json")
                .uri(uri).timeout(timeout).build();
        logger.info("Sending request: " + httpRequest.uri());
        client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            logger.error("Error upon receiving REST response from API. Check parameters, request intervals and etc. You may try again.");
            logger.error("Request URL = " + uri.toString());
            logger.error("Http response code: " + response.statusCode());

            APIErrorMessage message = objectMapper.readValue(response.body(), APIErrorMessage.class);
            logger.warn(message.getMessage());

            if (message.getMessage().contains("secondary rate limit")) {
                logger.error("Secondary rate limit exceeded.", new RequestRateExceededException());
            }

        }
        return response;
    }

}
