import com.fasterxml.jackson.databind.ObjectMapper;
import models.search.code.CodeResult;
import models.search.code.CodeResultItem;
import models.Repository;
import search.requests.CodeSearchRequest;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

public class Test {
    private static final String token = "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";

    public static void main(String[] args) throws IOException, InterruptedException {
//        testConn();
        GitHubAPI gitHubAPI = new GitHubAPI(token);


        CodeSearchRequest req = CodeSearchRequest.newBuilder()
                .addSearchField(CodeSearchRequest.SearchBy.Filename, "pom.xml").build();

        String result = gitHubAPI.searchAPI.searchRaw(req);

//        String result = Files.readString(Path.of("result.json"));

        ObjectMapper objMapper = new ObjectMapper();
        CodeResult cr = objMapper.readValue(result, CodeResult.class);

        for (CodeResultItem codeResultItem : cr.getItems()) {

            Repository r = codeResultItem.getRepository();

            String result1 = gitHubAPI.getRESTRawResponse(r.getUrl().toString());

            codeResultItem.setRepository(objMapper.readValue(result1, Repository.class));

            r = codeResultItem.getRepository();

            System.out.println(r.getFullName() + ", created at " + r.getCreatedAt());
        }
    }

    public static void testConn() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .headers("Authorization", "Bearer " + token, "Accept", "application/vnd.github.v3.text-match+json")
                .uri(URI.create("https://api.github.com/search/code?q=filename:pom.xml")).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
