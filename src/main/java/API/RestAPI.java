package API;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class RestAPI {
    public static final Duration timeout = Duration.ofSeconds(10);

    final String OAuthToken;
    final HttpClient client;
    final ObjectMapper objectMapper;

    public RestAPI(String OAuthToken) {
        this.OAuthToken = OAuthToken;
        client = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
    }

    public RestAPI() {
        this(null);
    }

    public HttpResponse<String> getHttpResponse(URI uri) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder().headers("Authorization", "Bearer " + OAuthToken, "Accept", "application/vnd.github.v3+json")
                .uri(uri).timeout(timeout).build();
        System.out.println("Sending request: " + httpRequest.uri());
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            System.out.println("Error upon receiving REST response from API. This is usually caused by secondary rate limit. Please try again.\n" +
                    "Request URL=" + uri.toString() +
                    "Http response info returned:\n");
            System.out.println(response.headers().toString());
            System.out.println(response.body());
            try {
                throw new RequestRateExceededException();
            } catch (RequestRateExceededException e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}
