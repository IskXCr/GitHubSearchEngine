package API;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;

public class GitHubAPI extends RestAPI{

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

    public static GitHubAPI registerAPI(String OAuthToken) {
        return new GitHubAPI(OAuthToken);
    }
}
