package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.content.ContentDirectory;
import models.content.ContentFile;
import models.repository.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FileAPI {
    private final String OAuthToken;

    private final HttpClient client;

    private final ObjectMapper objectMapper;

//    private final HashMap<ETag, HttpResponse<String>> historyMap;

    FileAPI(String OAuthToken) {
        this.OAuthToken = OAuthToken;
        client = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
//        historyMap = new HashMap<>();
    }

    FileAPI() {
        this(null);
    }


    public String getFileRaw(URI uri) throws IOException, InterruptedException {
        return getFileDirect(uri).body();
    }

    public HttpResponse<String> getFileDirect(URI uri) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder().headers("Authorization", "Bearer " + OAuthToken, "Accept", "application/vnd.github.v3+json")
                .uri(uri).build();
        System.out.println("Sending file request: " + httpRequest.uri());

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return response;
    }


    public static FileAPI registerAPI(String OAuthToken) {
        return new FileAPI(OAuthToken);
    }
}
