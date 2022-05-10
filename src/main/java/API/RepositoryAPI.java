package API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.repository.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;

public class RepositoryAPI extends RestAPI {

    RepositoryAPI(String OAuthToken) {
        super(OAuthToken);
    }

    RepositoryAPI() {
        super();
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
        return getRepositoryInfoDirect(URI.create("https://api.github.com/repos/" + repoFullName));
    }

    public HttpResponse<String> getRepositoryInfoDirect(URI uri) throws IOException, InterruptedException {
        return getHttpResponse(uri);
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
