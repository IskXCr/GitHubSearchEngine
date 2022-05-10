package API;

import API.search.requests.RepoSearchRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.code.CodeItem;
import models.code.CodeResult;
import API.search.requests.CodeSearchRequest;
import API.search.requests.SearchRequest;
import models.repository.Repository;
import models.repository.RepositoryResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchAPI extends RestAPI {

    private static final Logger logger = LogManager.getLogger(SearchAPI.class);

    private static final int DEFAULT_INTERVAL = 3000; //Unit: ms

    private final RateAPI rateAPI;

    SearchAPI(String OAuthToken) {
        super(OAuthToken);
        rateAPI = new RateAPI(OAuthToken);

        logger.info("Initialized " + (OAuthToken != null ? OAuthToken.substring(0, 8) : "<null>") + "...(hidden)");
    }

    SearchAPI() {
        this(null);
    }

    /**
     * This method uses while loop to request for search results.
     * Please notice that the GitHub REST API may severely restrict your ability to query the code result.
     * If too often the secondary rate limit is encountered, please consider using the other method with increased
     * <code>timeIntervalMillis</code>, where <code>default = 3000</code>, and run this method in another thread.
     *
     * @param request1 Request (will create another copy)
     * @param count    Target Item count
     * @return CodeResult
     * @throws IOException
     * @throws InterruptedException
     */
    public CodeResult searchCode(CodeSearchRequest request1, int count) throws IOException, InterruptedException {
        return searchCode(request1, count, DEFAULT_INTERVAL);
    }

    /**
     * This method uses while loop to request for search results.
     * Please notice that the GitHub REST API may severely restrict your ability to query the code result.
     * If too often the secondary rate limit is encountered, please increase the <code>timeIntervalMillis</code>
     * (a typical recommendation might be <code>180000</code>), and run this method in another thread.
     *
     * @param request1           Request (will create another copy)
     * @param count              Target Item count
     * @param timeIntervalMillis Preferred time interval between request.
     * @return CodeResult
     * @throws IOException
     * @throws InterruptedException
     */
    public CodeResult searchCode(CodeSearchRequest request1, int count, long timeIntervalMillis) throws IOException, InterruptedException {
        logger.info("Starting to fetch results. Target number: " + count);

        SearchRequest request = new SearchRequest(request1);

        int cnt = 0;
        HttpResponse<String> response = search(request);
        int endPage = parseEndPageCount(response);

        String s = response.body();
        CodeResult result = objectMapper.readValue(s, CodeResult.class);
        cnt += result.getItems().size();
        request.incrResultPage(1);


        int loopCnt = 2;
        while (cnt < count && request.getResultPage() <= endPage) {
            logger.info("Looping: " + loopCnt++);
            response = search(request);

            if (endPage == Integer.MAX_VALUE) {
                endPage = parseEndPageCount(response);
            }

            logger.info("Waiting on time interval (millis):" + timeIntervalMillis);
            Thread.sleep(timeIntervalMillis);
            CodeResult result1 = convert(response.body(), CodeResult.class);

            for (CodeItem i : result1.getItems()) {
                result.getItems().add(i);
            }

            if (result1.getItems().size() != 0) {
                if (result.getTotalCount() == null) {
                    result.setTotalCount(result1.getTotalCount());
                }
                request.incrResultPage(1);
                cnt += result1.getItems().size();
            }

            logger.info("Result fetched: " + cnt);
        }
        request.setResultPage(1);
        return result;
    }

    /**
     * This method uses while loop to request for search results.
     * Please notice that the GitHub REST API may severely restrict your ability to query the code result.
     * If too often the secondary rate limit is encountered, please increase the <code>timeIntervalMillis</code>
     * (a typical recommendation might be <code>180000</code>), and run this method in another thread.
     *
     * @param request1           Request (will create another copy)
     * @param count              Target Item count
     * @param timeIntervalMillis Preferred time interval between request.
     * @return CodeResult
     * @throws IOException
     * @throws InterruptedException
     */
    public RepositoryResult searchRepo(RepoSearchRequest request1, int count, long timeIntervalMillis) throws IOException, InterruptedException {
        logger.info("Starting to fetch results. Target number: " + count);

        SearchRequest request = new SearchRequest(request1);

        int cnt = 0;
        int endPage = Integer.MAX_VALUE;
        HttpResponse<String> response;
        RepositoryResult result = null;

        int loopCnt = 1;
        do {
            logger.info("Looping: " + loopCnt++);
            response = search(request);

            if (endPage == Integer.MAX_VALUE) {
                endPage = parseEndPageCount(response);
            }

            RepositoryResult result1 = convert(response.body(), RepositoryResult.class);
            if (result == null && result1.getItems().size() != 0) {
                result = result1;
            } else {
                for (Repository r : result1.getItems()) {
                    result.getItems().add(r);
                }
            }

            if (result1.getItems().size() != 0) {
                if (result.getTotalCount() == null) {
                    result.setTotalCount(result1.getTotalCount());
                }
                request.incrResultPage(1);
                cnt += result1.getItems().size();
            }

            logger.info("Result fetched: " + cnt);

            logger.info("Waiting on time interval (millis) to fetch the next result: " + timeIntervalMillis);
            Thread.sleep(timeIntervalMillis);
        } while (cnt < count && request.getResultPage() <= endPage);

        request.setResultPage(1);
        return result;
    }


    public void searchLoopFetching(SearchRequest request, Performable p, int count, long timeIntervalMillis) {
        logger.info("Starting to fetch results. Target number: " + count);

    }

    public interface Performable {
        public void run();
    }

    public interface Accumulator {
        public int accumulate();
    }

    public <T> T searchResult(SearchRequest request, Class<T> clazz) throws IOException, InterruptedException {
        String s = searchRaw(request);
        T result = objectMapper.readValue(s, clazz);
        return result;
    }

    public String searchRaw(SearchRequest request) throws IOException, InterruptedException {
        return search(request).body();
    }

    public HttpResponse<String> search(SearchRequest request) throws IOException, InterruptedException {
        return getHttpResponse(URI.create("https://api.github.com/search/" + request.getRequestString()));
    }


    public static SearchAPI registerAPI(String OAuthToken) {
        return new SearchAPI(OAuthToken);
    }

    public static <T> T convert(String jsonContent, Class<T> clazz) {
        ObjectMapper objMpr = new ObjectMapper();
        try {
            return objMpr.readValue(jsonContent, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int parseEndPageCount(HttpResponse<String> response) {

        //Check the max page number allowed
//        System.out.println(response.headers());

        int result = Integer.MAX_VALUE;
        if (response.headers().firstValue("Link").isPresent()) {
            try {
                String s1 = response.headers().firstValue("Link").get();
                Pattern p1 = Pattern.compile("&page=(\\d+)");
                logger.debug("The given header to read is: " + s1);
                logger.debug("The given index of the rel=\"last\" is: " + s1.indexOf("rel=\"last\""));
                Matcher matcher1 = p1.matcher(s1.substring((
                        Math.max(s1.indexOf("rel=\"last\"") - 30, 0) //practice value 30
                )));
                if (matcher1.find()) {
                    String result1 = matcher1.toMatchResult().group(1);
                    logger.debug("Matcher result: " + result1);
                    result = Integer.parseInt(result1);
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
        if (result != Integer.MAX_VALUE) {
            logger.info("New end page number found: " + result);
        }
        return result;
    }
}
