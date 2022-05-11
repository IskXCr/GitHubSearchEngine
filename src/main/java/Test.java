import API.GitHubAPI;
import API.search.requests.RepoSearchRequest;
import API.search.requests.TopicSearchRequest;
import models.code.CodeResult;
import API.search.requests.CodeSearchRequest;
import models.repository.RepositoryResult;
import models.topic.TopicResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    private static final Logger logger = LogManager.getLogger(Test.class);

    static String OAuthToken;

    static {
        try {
            OAuthToken = Files.readString(Path.of("TestToken.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

//        testMatcher();
//        testConn();
        GitHubAPI gitHubAPI = GitHubAPI.registerAPI(OAuthToken);


//        CodeSearchRequest req = CodeSearchRequest.newBuilder()
//                .addSearchKeyword("org.apache.logging.log4j")
//                .addSearchField(CodeSearchRequest.SearchBy.Filename, "pom.xml")
//                .addLanguageOption("Maven POM")
//                .build();

//        RepoSearchRequest req = RepoSearchRequest.newBuilder()
//                .addSearchKeyword("Data")
//                .addSearchField(RepoSearchRequest.SearchBy.Topic, "java")
//                .addDateOption(RepoSearchRequest.DateOption.Created, "2012-01-01", ">=")
//                .build();

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


    public static int testMatcher() throws IOException, InterruptedException {
        int result = Integer.MAX_VALUE;
        try {
            String s1 = "sorry&page=233>; rel=\"last\"";
            Pattern p1 = Pattern.compile("&page=(\\d+)");
            logger.debug("The given header to read is: " + s1);
            logger.debug("The given index of the rel=\"last\" is: " + s1.indexOf("rel=\"last\""));
            Matcher matcher1 = p1.matcher(s1.substring((
                    Math.max(s1.indexOf("rel=\"last\"") - 30, 0) //practice value 30
            )));
            if (matcher1.find()) {
                String result1 = matcher1.toMatchResult().group(1);
                logger.debug("Matcher result: " + result1);
                result = Integer.parseInt(result1.substring(5));
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return result;
    }
}
