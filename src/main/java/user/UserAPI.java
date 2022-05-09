package user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.user.User;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserAPI {

    private final String OAuthToken;

    private final HttpClient client;

    private final ObjectMapper objectMapper;

//    private final HashMap<ETag, HttpResponse<String>> historyMap;

    UserAPI(String OAuthToken) {
        this.OAuthToken = OAuthToken;
        client = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
//        historyMap = new HashMap<>();
    }

    UserAPI() {
        this(null);
    }

    public User getUser(URI uri) throws IOException, InterruptedException {
        String s = sendUserRequestDirect(uri).body();
        User user = objectMapper.readValue(s, User.class);
        return user;
    }

    public User getUser(String username) throws IOException, InterruptedException {
        String s = getUserInfoRaw(username);
        User user = objectMapper.readValue(s, User.class);
        return user;
    }

    public String getUserInfoRaw(String username) throws IOException, InterruptedException {
        HttpResponse<String> response = sendUserRequest(username);
        return response.body();
    }


    public HttpResponse<String> sendUserRequest(String username) throws IOException, InterruptedException {
        try {
            return sendUserRequestDirect(new URI("https://api.github.com/users/" + username));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("This exception shall not happen.");
        }
        return null;
    }

    public HttpResponse<String> sendUserRequestDirect(URI uri) throws IOException, InterruptedException {

        HttpRequest httpRequest = HttpRequest.newBuilder().headers("Authorization", "Bearer " + OAuthToken, "Accept", "application/vnd.github.v3+json")
                .uri(uri).build();
        System.out.println("Sending user request: " + httpRequest.uri());

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    public static UserAPI registerAPI(String OAuthToken) {
        return new UserAPI(OAuthToken);
    }

    public static User convert(String jsonContent) {
        ObjectMapper objMpr = new ObjectMapper();
        try {
            return objMpr.readValue(jsonContent, User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
