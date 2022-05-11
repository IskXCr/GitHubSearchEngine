package API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.repository.Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;

public class RepositoryAPI extends RestAPI {

    private final Logger logger = LogManager.getLogger(RepositoryAPI.class);

    RepositoryAPI(String OAuthToken) {
        super(OAuthToken);

        logger.info("Initialized " + (OAuthToken != null ? OAuthToken.substring(0, 8) : "<null>") + "...(hidden)");
    }

    RepositoryAPI() {
        this(null);
    }

    public Repository getRepository(URI uri) throws IOException, InterruptedException {
        return convert(getRepositoryInfoDirect(uri).body(), Repository.class);
    }

    public Repository getRepository(String repoFullName) throws IOException, InterruptedException {
        return convert(getRepositoryInfoRaw(repoFullName), Repository.class);
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

}
