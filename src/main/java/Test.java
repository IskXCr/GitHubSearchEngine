import com.fasterxml.jackson.databind.ObjectMapper;
import models.code.CodeResult;
import models.code.CodeItem;
import models.repository.Repository;
import search.SearchAPI;
import search.requests.CodeSearchRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

public class Test {
    private static final String OAuthToken = "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";

    public static void main(String[] args) throws IOException, InterruptedException {
//        testConn();
        GitHubAPI gitHubAPI = GitHubAPI.registerAPI(OAuthToken);


        CodeSearchRequest req = CodeSearchRequest.newBuilder()
                .addSearchField(CodeSearchRequest.SearchBy.Filename, "pom.xml").build();

        String s = Files.readString(Path.of("result.json"));

        CodeResult result = gitHubAPI.searchAPI.searchCode(req);
//        CodeResult result = SearchAPI.convert(s);


        System.out.println(result.getTotalCount());
        System.out.println(result.getItems().size());
//        System.out.println(result);

//        for (CodeItem codeItem : result.getItems()) {
//
//            Repository r = codeItem.getRepository();
//
//            Repository r1 = gitHubAPI.repositoryAPI.getRepository(r.getUrl());
//
//            codeItem.setRepository(r1);
//
//
//            System.out.println(r1.getFullName() + ", created at " + r1.getCreatedAt());
//        }
    }

    public static void testConn() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .headers("Authorization", "Bearer " + OAuthToken, "Accept", "application/vnd.github.v3.text-match+json")
                .uri(URI.create("https://api.github.com/search/code?q=filename:pom.xml")).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
