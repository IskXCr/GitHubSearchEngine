package API;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;

public class FileAPI extends RestAPI{

    FileAPI(String OAuthToken) {
        super(OAuthToken);
    }

    FileAPI() {
        super();
    }


    public String getFileRaw(URI uri) throws IOException, InterruptedException {
        return getFileDirect(uri).body();
    }

    public HttpResponse<String> getFileDirect(URI uri) throws IOException, InterruptedException {

        return getHttpResponse(uri);
    }


    public static FileAPI registerAPI(String OAuthToken) {
        return new FileAPI(OAuthToken);
    }
}
