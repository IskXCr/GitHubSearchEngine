package API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.content.ContentDirectory;
import models.content.ContentFile;
import models.repository.Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;

public class ContentAPI extends RestAPI {

    private static final Logger logger = LogManager.getLogger(RestAPI.class);

    ContentAPI(String OAuthToken) {
        super(OAuthToken);

        logger.info("Initialized " + (OAuthToken != null ? OAuthToken.substring(0, 8) : "<null>") + "...(hidden)");
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
        return getContentRaw(URI.create("https://api.github.com/repos/" + unwrapSlashes(repo.getFullName()) + "/contents" + (path.charAt(0) != '/' ? '/' + path : path)));

    }

    public String getContentRaw(URI uri) throws IOException, InterruptedException {
        return getContentDirect(uri).body();
    }

    public HttpResponse<String> getContentDirect(URI uri) throws IOException, InterruptedException {

        return getHttpResponse(uri);
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
