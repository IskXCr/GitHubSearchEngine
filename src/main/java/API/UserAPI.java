package API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.user.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;

public class UserAPI extends RestAPI {

    UserAPI(String OAuthToken) {
        super(OAuthToken);
    }

    UserAPI() {
        super();
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
        return sendUserRequestDirect(URI.create("https://api.github.com/users/" + username));
    }

    public HttpResponse<String> sendUserRequestDirect(URI uri) throws IOException, InterruptedException {

        return getHttpResponse(uri);
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
