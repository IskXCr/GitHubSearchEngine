package API;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sustech.search.engine.github.API.GitHubAPI;
import edu.sustech.search.engine.github.API.RestAPI;
import edu.sustech.search.engine.github.API.search.requests.CodeSearchRequest;
import edu.sustech.search.engine.github.analyzer.maven.DependencyAnalyzer;
import edu.sustech.search.engine.github.analyzer.models.Dependency;
import edu.sustech.search.engine.github.models.code.CodeItem;
import edu.sustech.search.engine.github.models.code.CodeResult;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class SearchAPITest {

    @Test
    void testConvert() throws IOException {
        CodeResult result = RestAPI.convert(Files.readString(Path.of("result.json")), CodeResult.class);
        System.out.println(result);
    }

    @Test
    void test1() throws IOException, InterruptedException {
        String OAuthToken = Files.readString(Path.of("TestToken.txt"));

        GitHubAPI gitHubAPI = GitHubAPI.registerAPI(OAuthToken);

        CodeSearchRequest req = CodeSearchRequest.newBuilder()
                .addSearchKeyword("Data")
                .addLanguageOption("Maven POM")
                .addSearchField(CodeSearchRequest.SearchBy.Filename, "pom.xml")
                .build();

        CodeResult result = gitHubAPI.searchAPI.searchCode(req, 24);
//        CodeResult result = SearchAPI.convert(s);

        for (CodeItem item : result.getItems()) {
            System.out.println(item.getName());
            if (item.getName().equals("pom.xml")) {
                System.out.print("File from:" + item.getRepository().getFullName() + ", dependencies: \n");

                String s = gitHubAPI.fileAPI.getFileRaw(item.getRawFileURI());
                List<Dependency> dependencies = DependencyAnalyzer.parseDependency(s);
                for (Dependency d : dependencies) {
                    System.out.print(d + " ");
                }
                System.out.println();
            }
        }
    }
}