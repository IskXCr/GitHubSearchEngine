package API;

import API.search.requests.TopicSearchRequest;
import models.topic.TopicResult;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class SearchAPITest {

    @Test
    void test1() throws IOException, InterruptedException {
        String OAuthToken = Files.readString(Path.of("TestToken.txt"));

        GitHubAPI gitHubAPI = GitHubAPI.registerAPI(OAuthToken);

        TopicSearchRequest req = TopicSearchRequest.newBuilder()
                .addSearchKeyword("Java")
                .addSearchKeyword("Data")
                .addSearchKeyword("Visualization")
                .build();

        TopicResult result = gitHubAPI.searchAPI.searchTopic(req, 100);
//        CodeResult result = SearchAPI.convert(s);

        System.out.println(result.getTotalCount());
        System.out.println(result.getItems().size());
    }
}