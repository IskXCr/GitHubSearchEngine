package API;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.time.Duration;

public class GitHubAPI extends RestAPI{

    public static final Duration timeout = Duration.ofSeconds(10);

    private static final Logger logger = LogManager.getLogger(GitHubAPI.class);

    public final SearchAPI searchAPI;
    public final RepositoryAPI repositoryAPI;
    public final UserAPI userAPI;
    public final ContentAPI contentAPI;
    public final FileAPI fileAPI;

    GitHubAPI(String OAuthToken) {
        super(OAuthToken);

        searchAPI = SearchAPI.registerAPI(OAuthToken);
        repositoryAPI = RepositoryAPI.registerAPI(OAuthToken);
        userAPI = UserAPI.registerAPI(OAuthToken);
        contentAPI = ContentAPI.registerAPI(OAuthToken);
        fileAPI = FileAPI.registerAPI(OAuthToken);

        logger.info("Initialized " + (OAuthToken != null ? OAuthToken.substring(0, 8) : "<null>") + "...(hidden)");
    }

    GitHubAPI(){
        this(null);
    }

    public String getRESTRawResponse(String requestURL) throws IOException, InterruptedException {
        HttpResponse<String> response = sendRESTRequest(requestURL);
        return response.body();
    }

    public HttpResponse<String> sendRESTRequest(String requestURL) throws IOException, InterruptedException {
        return getHttpResponse(URI.create(requestURL));
    }

    public static GitHubAPI registerAPI(String OAuthToken) {
        return new GitHubAPI(OAuthToken);
    }
}
