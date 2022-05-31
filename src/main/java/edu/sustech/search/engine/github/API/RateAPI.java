package edu.sustech.search.engine.github.API;

import edu.sustech.search.engine.github.API.rate.RateLimitResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;

public class RateAPI extends RestAPI {

    private static final Logger logger = LogManager.getLogger(RateAPI.class);

    RateAPI(String... OAuthTokens){
        super(OAuthTokens);
        logger.info("Initialized " + (OAuthTokens[0] != null ? OAuthTokens[0].substring(0, 8) : "<null>") + "...(hidden)");
    }

    RateAPI(List<String> OAuthTokens){
        super(OAuthTokens);
        logger.info("Initialized " + (OAuthTokens.get(0) != null ? OAuthTokens.get(0).substring(0, 8) : "<null>") + "...(hidden)");
    }

    RateAPI() {
        this((String) null);
    }

    public RateLimitResult getRateLimit() throws IOException, InterruptedException {
        HttpResponse<String> response = getHttpResponse(URI.create("https://api.github.com/rate_limit"));
        return convert(response.body(), RateLimitResult.class);
    }

    /**
     * Register a new RateAPI based on OAuthTokens provided
     * @param OAuthTokens Array of OAuthTokens
     * @return
     */
    public static RateAPI registerAPI(String... OAuthTokens) {
        return new RateAPI(OAuthTokens);
    }

    /**
     * Register a new RateAPI based on OAuthTokens provided
     * @param OAuthTokens List of OAuthTokens
     * @return
     */
    public static RateAPI registerAPI(List<String> OAuthTokens) {
        return new RateAPI(OAuthTokens);
    }
}
