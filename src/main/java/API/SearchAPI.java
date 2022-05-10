package API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.code.CodeResult;
import API.search.requests.CodeSearchRequest;
import API.search.requests.SearchRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;

public class SearchAPI extends RestAPI {

    SearchAPI(String OAuthToken) {
        super(OAuthToken);
    }

    SearchAPI(){
        super();
    }

    public CodeResult searchCode(CodeSearchRequest request) throws IOException, InterruptedException {
        String s = searchCodeRaw(request);
        CodeResult result = objectMapper.readValue(s, CodeResult.class);
        return result;
    }

    public String searchCodeRaw(SearchRequest request) throws IOException, InterruptedException {
        return search(request).body();
    }

    public HttpResponse<String> search(SearchRequest request) throws IOException, InterruptedException {
        return getHttpResponse(URI.create("https://api.github.com/search/" + request.getRequestString()));
    }


    public static SearchAPI registerAPI(String OAuthToken) {
        return new SearchAPI(OAuthToken);
    }

    public static CodeResult convert(String jsonContent) {
        ObjectMapper objMpr = new ObjectMapper();
        try {
            return objMpr.readValue(jsonContent, CodeResult.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
