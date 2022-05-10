import API.GitHubAPI;
import models.code.CodeResult;
import API.search.requests.CodeSearchRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

public class Test {

    static String OAuthToken;
    static{
        try {
            OAuthToken = Files.readString(Path.of("TestToken.txt"));
            System.out.println(OAuthToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
//        testConn();
        GitHubAPI gitHubAPI = GitHubAPI.registerAPI(OAuthToken);


        CodeSearchRequest req = CodeSearchRequest.newBuilder()
                .addSearchKeyword("org.log4j")
                .addSearchField(CodeSearchRequest.SearchBy.Filename, "pom.xml")
                .addLanguageOption("Maven POM")
                .build();

        String s = Files.readString(Path.of("result.json"));

        for (int i = 1; i < 2; i++) {
            req.setResultPage(i);
            CodeResult result = gitHubAPI.searchAPI.searchCode(req);
//        CodeResult result = SearchAPI.convert(s);

            System.out.println(result.getTotalCount());
            System.out.println(result.getItems().size());
        }

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
