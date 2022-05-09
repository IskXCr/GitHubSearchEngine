package search.requests;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public abstract class SearchRequest {


    StringBuilder requestBuilder;

    public SearchRequest() {
        requestBuilder = new StringBuilder();
    }

    public String getRequestStringRaw() {
        return requestBuilder.toString();
    }

    public String getRequestString() {
        return requestBuilder.toString().replace(' ', '+');
    }

    public StringBuilder getRequestBuilder() {
        return this.requestBuilder;
    }

}
