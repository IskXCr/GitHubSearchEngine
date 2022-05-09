package search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.code.CodeResult;
import search.requests.CodeSearchRequest;
import search.requests.SearchRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

public class SearchAPI {

    private final String OAuthToken;

    private final HttpClient client;

    private final ObjectMapper objectMapper;

//    private final HashMap<ETag, HttpResponse<String>> historyMap;

    SearchAPI(String OAuthToken) {
        this.OAuthToken = OAuthToken;
        client = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
//        historyMap = new HashMap<>();
    }

    SearchAPI() {
        this(null);
    }

    public CodeResult searchCode(CodeSearchRequest request) throws IOException, InterruptedException {
        String s = searchCodeRaw(request);
        CodeResult result = objectMapper.readValue(s, CodeResult.class);
        return result;
    }

    public String searchCodeRaw(SearchRequest request) throws IOException, InterruptedException {
        return search(request).body();
    }

    public HttpResponse<String> search(SearchRequest request) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder().headers("Authorization", "Bearer " + OAuthToken, "Accept", "application/vnd.github.v3+json")
                .uri(URI.create("https://api.github.com/search/" + request.getRequestString())).timeout(Duration.ofSeconds(10)).build();
        System.out.println("Sending search request: " + httpRequest.uri());
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            System.out.println("Error. Http response info returned:");
            System.out.println(response.headers().toString());
        }
        return response;
    }


    public static SearchAPI registerAPI(String OAuthToken) {
        return new SearchAPI(OAuthToken);
    }

    public static CodeResult convert(String jsonContent) {
        ObjectMapper objMpr = new ObjectMapper();
        try {
            return objMpr.readValue(jsonContent, CodeResult.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
