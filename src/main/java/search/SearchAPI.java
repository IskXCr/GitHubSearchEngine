package search;

import search.requests.SearchRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SearchAPI {

    private final String OAuthToken;

    private final HttpClient client;

//    private final HashMap<ETag, HttpResponse<String>> historyMap;

    SearchAPI(String OAuthToken) {
        this.OAuthToken = OAuthToken;
        client = HttpClient.newHttpClient();
//        historyMap = new HashMap<>();
    }

    SearchAPI() {
        this(null);
    }

    public String searchRaw(SearchRequest request) throws IOException, InterruptedException {
        return search(request).body();
    }

    public HttpResponse<String> search(SearchRequest request) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder().headers("Authorization", "Bearer " + OAuthToken, "Accept", "application/vnd.github.v3.text-match+json")
                .uri(URI.create("https://api.github.com/search/" + request.getRequestString())).build();
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
}
