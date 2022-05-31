package edu.sustech.search.engine.github.API;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;

public class FileAPI extends RestAPI {

    private static final Logger logger = LogManager.getLogger(FileAPI.class);

    FileAPI(String... OAuthTokens){
        super(OAuthTokens);
        logger.info("Initialized " + (OAuthTokens[0] != null ? OAuthTokens[0].substring(0, 8) : "<null>") + "...(hidden)");
    }

    FileAPI(List<String> OAuthTokens){
        super(OAuthTokens);
        logger.info("Initialized " + (OAuthTokens.get(0) != null ? OAuthTokens.get(0).substring(0, 8) : "<null>") + "...(hidden)");
    }

    FileAPI() {
        this((String) null);
    }


    public String getFileRaw(URI uri) throws IOException, InterruptedException {
        HttpResponse<String> response = getFileDirect(uri);
        return response == null ? null : getFileDirect(uri).body();
    }

    public HttpResponse<String> getFileDirect(URI uri) throws IOException, InterruptedException {
        logger.info("Getting raw file from " + uri.toString());
        return getHttpResponse(uri);
    }


    /**
     * Register a new FileAPI based on OAuthTokens provided
     * @param OAuthTokens Array of OAuthTokens
     * @return
     */
    public static FileAPI registerAPI(String... OAuthTokens) {
        return new FileAPI(OAuthTokens);
    }

    /**
     * Register a new FileAPI based on OAuthTokens provided
     * @param OAuthTokens List of OAuthTokens
     * @return
     */
    public static FileAPI registerAPI(List<String> OAuthTokens) {
        return new FileAPI(OAuthTokens);
    }
}
