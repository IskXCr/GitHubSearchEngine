package repository;

import com.fasterxml.jackson.core.JsonProcessingException;
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

public class ContentAPI {

    private final String OAuthToken;

    private final HttpClient client;

    private final ObjectMapper objectMapper;

//    private final HashMap<ETag, HttpResponse<String>> historyMap;

    ContentAPI(String OAuthToken) {
        this.OAuthToken = OAuthToken;
        client = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
//        historyMap = new HashMap<>();
    }

    ContentAPI() {
        this(null);
    }

    public ContentDirectory getContentDirectory(URI uri) throws IOException, InterruptedException {
        return objectMapper.readValue(getContentDirect(uri).body(), ContentDirectory.class);
    }

    public ContentFile getContentFile(URI uri) throws IOException, InterruptedException {
        return objectMapper.readValue(getContentDirect(uri).body(), ContentFile.class);
    }

    public String getContentRaw(Repository repo, String path) throws IOException, InterruptedException {
        try {
            return getContentRaw(new URI("https://api.github.com/repos/" + unwrapSlashes(repo.getFullName()) + "/contents" + (path.charAt(0) != '/' ? '/' + path : path)));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getContentRaw(URI uri) throws IOException, InterruptedException {
        return getContentDirect(uri).body();
    }

    public HttpResponse<String> getContentDirect(URI uri) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder().headers("Authorization", "Bearer " + OAuthToken, "Accept", "application/vnd.github.v3+json")
                .uri(uri).build();
        System.out.println("Sending content request: " + httpRequest.uri());

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return response;
    }


    public static ContentAPI registerAPI(String OAuthToken) {
        return new ContentAPI(OAuthToken);
    }

    public static <T> T convert(String jsonContent, Class<T> clazz) {
        ObjectMapper objMpr = new ObjectMapper();
        try {
            return objMpr.readValue(jsonContent, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String unwrapSlashes(String s) {
        StringBuilder sb = new StringBuilder(s);
        if (s.charAt(0) == '/') {
            sb.deleteCharAt(0);
        }
        if (s.charAt(s.length() - 1) == '/') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
