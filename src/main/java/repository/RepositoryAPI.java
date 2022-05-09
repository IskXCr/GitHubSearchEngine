package repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.repository.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class RepositoryAPI {

    private final String OAuthToken;

    private final HttpClient client;

    private final ObjectMapper objectMapper;

//    private final HashMap<ETag, HttpResponse<String>> historyMap;

    RepositoryAPI(String OAuthToken) {
        this.OAuthToken = OAuthToken;
        client = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
//        historyMap = new HashMap<>();
    }

    RepositoryAPI() {
        this(null);
    }

    public Repository getRepository(URI uri) throws IOException, InterruptedException {
        String s = getRepositoryInfoDirect(uri).body();
        Repository repository = objectMapper.readValue(s, Repository.class);
        return repository;
    }

    public Repository getRepository(String repoFullName) throws IOException, InterruptedException {
        String s = getRepositoryInfoRaw(repoFullName);
        Repository repository = objectMapper.readValue(s, Repository.class);
        return repository;
    }

    public String getRepositoryInfoRaw(String repoFullName) throws IOException, InterruptedException {
        HttpResponse<String> response = getRepositoryInfo(repoFullName);
        return response.body();
    }

    public HttpResponse<String> getRepositoryInfo(String repoFullName) throws IOException, InterruptedException {
        try {
            return getRepositoryInfoDirect(new URI("https://api.github.com/repos/" + repoFullName));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("This exception shall not happen.");
        }
        return null;
    }

    public HttpResponse<String> getRepositoryInfoDirect(URI uri) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder().headers("Authorization", "Bearer " + OAuthToken, "Accept", "application/vnd.github.v3+json")
                .uri(uri).timeout(Duration.ofSeconds(10)).build();
        System.out.println("Sending repo request: " + httpRequest.uri());

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return response;
    }


    public static RepositoryAPI registerAPI(String OAuthToken) {
        return new RepositoryAPI(OAuthToken);
    }

    public static Repository convert(String jsonContent) {
        ObjectMapper objMpr = new ObjectMapper();
        try {
            return objMpr.readValue(jsonContent, Repository.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
