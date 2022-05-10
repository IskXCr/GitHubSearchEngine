import API.GitHubAPI;
import models.code.CodeResult;
import API.search.requests.CodeSearchRequest;
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


        CodeSearchRequest req = CodeSearchRequest.newBuilder()
                .addSearchKeyword("org.apache.logging.log4j")
//                .addSearchKeyword("org.fasterxml.jackson.core")
                .addSearchField(CodeSearchRequest.SearchBy.Filename, "pom.xml")
                .addLanguageOption("Maven POM")
//                .addDateOption("2020-03-21", ">=")
                .build();

        GitHubAPI raw = GitHubAPI.registerAPI(null);
//        String s = Files.readString(Path.of("result.json"));

        CodeResult result = gitHubAPI.searchAPI.searchCode(req, 100);
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
