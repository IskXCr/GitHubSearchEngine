package edu.sustech.search.engine.github.API;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Provides the entry to interact with the GitHub RestAPI.
 * The class needs to be instantiated with an OAuthToken to unlock all functionalities.
 */
public class GitHubAPI extends RestAPI {

    private static final Logger logger = LogManager.getLogger(GitHubAPI.class);

    public final SearchAPI searchAPI;
    public final RepositoryAPI repositoryAPI;
    public final UserAPI userAPI;
    public final ContentAPI contentAPI;
    public final FileAPI fileAPI;

    /**
     * Initialize a new GitHubAPI instance to interact with.
     * This method is only used internally to preserve the interface to interact with.
     * @param OAuthTokens Array of Personal Access Tokens, or OAuthTokens, which will be used for authentication.
     *                   If set to <code>null</code>, then no OAuthToken will be used.
     */
    GitHubAPI(String... OAuthTokens) {
        super(OAuthTokens);

        searchAPI = SearchAPI.registerAPI(OAuthTokens);
        repositoryAPI = RepositoryAPI.registerAPI(OAuthTokens);
        userAPI = UserAPI.registerAPI(OAuthTokens);
        contentAPI = ContentAPI.registerAPI(OAuthTokens);
        fileAPI = FileAPI.registerAPI(OAuthTokens);

        logger.info("Initialized " + (OAuthTokens[0] != null ? OAuthTokens[0].substring(0, 8) : "<null>") + "...(hidden)");
    }

    /**
     * Initialize a new GitHubAPI instance to interact with.
     * This method is only used internally to preserve the interface to interact with.
     * @param OAuthTokens List of Personal Access Tokens, or OAuthTokens, which will be used for authentication.
     *                   If set to <code>null</code>, then no OAuthToken will be used.
     */
    GitHubAPI(List<String> OAuthTokens) {
        super(OAuthTokens);

        searchAPI = SearchAPI.registerAPI(OAuthTokens);
        repositoryAPI = RepositoryAPI.registerAPI(OAuthTokens);
        userAPI = UserAPI.registerAPI(OAuthTokens);
        contentAPI = ContentAPI.registerAPI(OAuthTokens);
        fileAPI = FileAPI.registerAPI(OAuthTokens);

        logger.info("Initialized " + (OAuthTokens.get(0) != null ? OAuthTokens.get(0).substring(0, 8) : "<null>") + "...(hidden)");
    }


    /**
     * Initialize a new GitHubAPI instance with no OAuthToken.
     */
    GitHubAPI(){
        this((String) null);
    }

    /**
     * Initialize a new GitHubAPI instance to interact with.
     * @param OAuthTokens Array of Personal Access Token, or OAuthToken, which will be used for authentication.
     *                   If set to <code>null</code>, then no OAuthToken will be used.
     * @return A new GitHubAPI with specific OAuthToken initialized as the certificate for authentication.
     */
    public static GitHubAPI registerAPI(String... OAuthTokens) {
        return new GitHubAPI(OAuthTokens);
    }


    /**
     * Initialize a new GitHubAPI instance to interact with.
     * @param OAuthTokens List of Personal Access Token, or OAuthToken, which will be used for authentication.
     *                   If set to <code>null</code>, then no OAuthToken will be used.
     * @return A new GitHubAPI with specific OAuthToken initialized as the certificate for authentication.
     */
    public static GitHubAPI registerAPI(List<String> OAuthTokens) {
        return new GitHubAPI(OAuthTokens);
    }
}
