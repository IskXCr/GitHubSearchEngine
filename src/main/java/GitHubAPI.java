import repository.RepositoryAPI;
import search.SearchAPI;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GitHubAPI {

    private final String OAuthToken;
    private final HttpClient client;

    public final SearchAPI searchAPI;
    public final RepositoryAPI repositoryAPI;

    public GitHubAPI(String OAuthToken) {
        this.OAuthToken = new String(OAuthToken);

        client = HttpClient.newHttpClient();

        searchAPI = SearchAPI.registerAPI(OAuthToken);
        repositoryAPI = RepositoryAPI.registerAPI(OAuthToken);
    }

    public String getRESTRawResponse(String requestURL) throws IOException, InterruptedException {
        HttpResponse<String> response = sendRESTRequest(requestURL);
        return response.body();
    }

    public HttpResponse<String> sendRESTRequest(String requestURL) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder().headers("Authorization", "Bearer " + OAuthToken, "Accept", "application/vnd.github.v3.text-match+json")
                .uri(URI.create(requestURL)).build();
        System.out.println("Sending REST request: " + httpRequest.uri());

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            System.out.println("Error.");
        }
        return response;
    }
}
