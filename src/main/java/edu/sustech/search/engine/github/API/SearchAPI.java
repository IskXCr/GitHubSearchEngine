package edu.sustech.search.engine.github.API;

import edu.sustech.search.engine.github.API.rate.RateLimitResult;
import edu.sustech.search.engine.github.API.search.requests.*;
import edu.sustech.search.engine.github.models.commit.CommitResult;
import edu.sustech.search.engine.github.models.issue.IPRResult;
import edu.sustech.search.engine.github.models.label.LabelResult;
import edu.sustech.search.engine.github.models.repository.RepositoryResult;
import edu.sustech.search.engine.github.models.topic.TopicResult;
import edu.sustech.search.engine.github.models.user.UserResult;
import edu.sustech.search.engine.github.models.AppendableResult;
import edu.sustech.search.engine.github.models.code.CodeResult;
import edu.sustech.search.engine.github.transformer.Transformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;

public class SearchAPI extends RestAPI {

    private static final Logger logger = LogManager.getLogger(SearchAPI.class);

    private static final int DEFAULT_INTERVAL = 15000; //Unit: ms

    private static String acceptSchema = "application/vnd.github.v3.text-match+json";
    private boolean isProvidingTextMatchEnabled = false;

    SearchAPI(String... OAuthTokens) {
        super(OAuthTokens);
        logger.info("Initialized " + (OAuthTokens[0] != null ? OAuthTokens[0].substring(0, 8) : "<null>") + "...(hidden)");
    }

    SearchAPI(List<String> OAuthTokens) {
        super(OAuthTokens);
        logger.info("Initialized " + (OAuthTokens.get(0) != null ? OAuthTokens.get(0).substring(0, 8) : "<null>") + "...(hidden)");
    }

    SearchAPI() {
        this((String) null);
    }

    /**
     * Search Issues and Pull-requests.
     * This method is a type-restricted implementation of the method <code>searchType</code>.
     * For detailed documentation, see <code>searchType</code>
     *
     * @param request1
     * @param count
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public IPRResult searchIPR(IPRSearchRequest request1, int count) throws IOException, InterruptedException {
        return searchIPR(request1, count, DEFAULT_INTERVAL);
    }

    /**
     * Search Issues and Pull-requests.
     * This method is a type-restricted implementation of the method <code>searchType</code>.
     * For detailed documentation, see <code>searchType</code>
     *
     * @param request1
     * @param count
     * @param timeIntervalMillis
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public IPRResult searchIPR(IPRSearchRequest request1, int count, long timeIntervalMillis) throws IOException, InterruptedException {
        return searchType(request1, IPRResult.class, count, timeIntervalMillis);
    }

    /**
     * This method is a type-restricted implementation of the method <code>searchType</code>.
     * For detailed documentation, see <code>searchType</code>
     *
     * @param request1
     * @param count
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public CommitResult searchCommit(CommitSearchRequest request1, int count) throws IOException, InterruptedException {
        return searchCommit(request1, count, DEFAULT_INTERVAL);
    }

    /**
     * This method is a type-restricted implementation of the method <code>searchType</code>.
     * For detailed documentation, see <code>searchType</code>
     *
     * @param request1
     * @param count
     * @param timeIntervalMillis
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public CommitResult searchCommit(CommitSearchRequest request1, int count, long timeIntervalMillis) throws IOException, InterruptedException {
        return searchType(request1, CommitResult.class, count, timeIntervalMillis);
    }

    /**
     * This method is a type-restricted implementation of the method <code>searchType</code>.
     * For detailed documentation, see <code>searchType</code>
     *
     * @param request1
     * @param count
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public LabelResult searchLabel(LabelSearchRequest request1, int count) throws IOException, InterruptedException {
        return searchLabel(request1, count, DEFAULT_INTERVAL);
    }

    /**
     * This method is a type-restricted implementation of the method <code>searchType</code>.
     * For detailed documentation, see <code>searchType</code>
     *
     * @param request1
     * @param count
     * @param timeIntervalMillis
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public LabelResult searchLabel(LabelSearchRequest request1, int count, long timeIntervalMillis) throws IOException, InterruptedException {
        return searchType(request1, LabelResult.class, count, timeIntervalMillis);
    }

    /**
     * This method is a type-restricted implementation of the method <code>searchType</code>.
     * For detailed documentation, see <code>searchType</code>
     *
     * @param request1
     * @param count
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public TopicResult searchTopic(TopicSearchRequest request1, int count) throws IOException, InterruptedException {
        return searchTopic(request1, count, DEFAULT_INTERVAL);
    }

    /**
     * This method is a type-restricted implementation of the method <code>searchType</code>.
     * For detailed documentation, see <code>searchType</code>
     *
     * @param request1
     * @param count
     * @param timeIntervalMillis
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public TopicResult searchTopic(TopicSearchRequest request1, int count, long timeIntervalMillis) throws IOException, InterruptedException {
        return searchType(request1, TopicResult.class, count, timeIntervalMillis);
    }

    /**
     * This method is a type-restricted implementation of the method <code>searchType</code>.
     * For detailed documentation, see <code>searchType</code>
     *
     * @param request1
     * @param count
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public UserResult searchUser(UserSearchRequest request1, int count) throws IOException, InterruptedException {
        return searchUser(request1, count, DEFAULT_INTERVAL);
    }

    /**
     * This method is a type-restricted implementation of the method <code>searchType</code>.
     * For detailed documentation, see <code>searchType</code>
     *
     * @param request1
     * @param count
     * @param timeIntervalMillis
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public UserResult searchUser(UserSearchRequest request1, int count, long timeIntervalMillis) throws IOException, InterruptedException {
        return searchType(request1, UserResult.class, count, timeIntervalMillis);
    }

    /**
     * This method is a type-restricted implementation of the method <code>searchType</code>.
     * For detailed documentation, see <code>searchType</code>
     *
     * @param request1
     * @param count
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public CodeResult searchCode(CodeSearchRequest request1, int count) throws IOException, InterruptedException {
        return searchCode(request1, count, DEFAULT_INTERVAL);
    }

    /**
     * This method is a type-restricted implementation of the method <code>searchType</code>.
     * For detailed documentation, see <code>searchType</code>
     *
     * @param request1
     * @param count
     * @param timeIntervalMillis
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public CodeResult searchCode(CodeSearchRequest request1, int count, long timeIntervalMillis) throws IOException, InterruptedException {
        return searchType(request1, CodeResult.class, count, timeIntervalMillis);
    }

    /**
     * This method is a type-restricted implementation of the method <code>searchType</code>.
     * For detailed documentation, see <code>searchType</code>
     *
     * @param request1
     * @param count
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public RepositoryResult searchRepo(RepoSearchRequest request1, int count) throws IOException, InterruptedException {
        return searchRepo(request1, count, DEFAULT_INTERVAL);
    }

    /**
     * This method is a type-restricted implementation of the method <code>searchType</code>.
     * For detailed documentation, see <code>searchType</code>
     *
     * @param request1
     * @param count
     * @param timeIntervalMillis
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public RepositoryResult searchRepo(RepoSearchRequest request1, int count, long timeIntervalMillis) throws IOException, InterruptedException {
        return searchType(request1, RepositoryResult.class, count, timeIntervalMillis);
    }

    /**
     * This method uses while loop to request for search results.
     * Please notice that the GitHub REST API may severely restrict your ability to query the result.
     * <br>
     * If too often the secondary rate limit is encountered, please increase the <code>timeIntervalMillis</code>
     * (a typical recommendation might be <code>15000</code>), and run this method in another thread.
     * <br>
     * If multiple OAuthTokens are used, then the time interval between each request can be noticeably reduced.
     *
     * @param <T>                Result type
     * @param request1           Request (will create another copy)
     * @param targetClazz        Target class for object mapper
     * @param count              Target item count. Note that the actual items retrieved might be more
     * @param timeIntervalMillis Preferred time interval between requests
     * @return CodeResult
     * @throws InterruptedException Typically when time interval is interrupted
     * @throws IOException          Typically when connection between this API and the server fails to establish or accidentally closed.
     */
    @SuppressWarnings("unchecked")
    public <T extends AppendableResult> T searchType(SearchRequest request1, Class<T> targetClazz, int count, long timeIntervalMillis) throws IOException, InterruptedException {
        return (T) searchLoopFetching(request1, s -> convert(s, targetClazz), count, timeIntervalMillis); //It must be T, so no worry.
    }

    /**
     * This method uses while loop to request for search results.
     * Please notice that the GitHub REST API may severely restrict your ability to query the result.
     * <br>
     * If too often the secondary rate limit is encountered, please increase the <code>timeIntervalMillis</code>
     * (a typical recommendation might be <code>15000</code>), and run this method in another thread.
     * <br>
     * If multiple OAuthTokens are used, then the time interval between each request can be noticeably reduced.
     *
     * @param request1           Request (will create another copy)
     * @param p                  A functional interface used to parse the result
     * @param count              Target item count. Note that the actual items retrieved might be more
     * @param timeIntervalMillis Preferred time interval between requests
     * @return Results gathered through loop fetching
     * @throws InterruptedException Typically when time interval is interrupted
     * @throws IOException          Typically when connection between this API and the server fails to establish or accidentally closed.
     */
    public AppendableResult searchLoopFetching(SearchRequest request1, AppendableResultParser p, int count, long timeIntervalMillis) throws InterruptedException, IOException {
        return searchLoopFetching(request1, null, p, count, timeIntervalMillis);
    }


    /**
     * This method uses while loop to request for search results.
     * Please notice that the GitHub REST API may severely restrict your ability to query the result.
     * <br>
     * If too often the secondary rate limit is encountered, please increase the <code>timeIntervalMillis</code>
     * (a typical recommendation might be <code>15000</code>), and run this method in another thread.
     * <br>
     * If multiple OAuthTokens are used, then the time interval between each request can be noticeably reduced.
     *
     * @param request1           Request (will create another copy)
     * @param origin             The origin result that waits to be appended on. If <code>null</code>, then a new empty origin will be created.
     * @param p                  A functional interface used to parse the result
     * @param count              Target item count. Note that the actual items retrieved might be more
     * @param timeIntervalMillis Preferred time interval between requests
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
    public AppendableResult searchLoopFetching(SearchRequest request1, AppendableResult origin, AppendableResultParser p, int count, long timeIntervalMillis) throws InterruptedException, IOException {

        SearchRequest request = new SearchRequest(request1);
        request.setResultPerPage(count);
        logger.info("Starting to fetch results on request[" + request.getFullRequestStringWithoutPage() + "]. Target number: " + count);

        int cnt = 0;
        int endPageCount = Integer.MAX_VALUE;
        HttpResponse<String> response;
        AppendableResult result = origin;


        setSuppressResponseError(true);

        for (int loopCnt = 1; cnt < count && request.getResultPage() <= endPageCount; loopCnt++) {
            logger.info("Looping: " + loopCnt);
            response = search(request);

            if (response != null) {
                if (endPageCount == Integer.MAX_VALUE) {
                    endPageCount = parseEndPageCount(response);
                }

                AppendableResult result1 = p.parse(response.body());

                if (result == null) {
                    if (result1 != null && result1.getItemCount() != 0) {
                        result = result1;
                        cnt = result.getItemCount();
                        request.incrResultPage(1);
                    }
                } else {
                    int incr = result.appendItems(result1);
                    if (incr != 0) {
                        request.incrResultPage(1);
                        cnt += incr;
                    } else {
                        if (!suppressRateError) {
                            printRateLimit(response);
                            RateLimitResult res = getRateLimit();
                            logger.error("The search rate limit has been captured and will be shown on the next error:");
                            logger.error(res.getResources().getSearch());
                        }
                    }
                }
            }

            logger.info("Result fetched: " + cnt);

            if (cnt < count && request.getResultPage() <= endPageCount) {
                logger.debug("Waiting on time interval (millis) to fetch the next result: " + timeIntervalMillis);
                Thread.sleep(timeIntervalMillis);
            }
        }

        request.setResultPage(1);

        setSuppressResponseError(false);

        logger.info("Results have been gathered on request [" + request.getFullRequestStringWithoutPage() + "]");

        return result;
    }

    /**
     * Each time a successful response is received from the server, the <code>parse</code> method will be invoked to parse the
     * raw response body (in <code>String</code>) into a POJO.
     */
    @FunctionalInterface
    public interface AppendableResultParser {
        AppendableResult parse(String s);
    }

    /**
     * Automatically cast request result to the desired form.
     *
     * @param request Request
     * @param clazz   Target type class instance
     * @param <T>     Target type
     * @return Object result
     * @throws IOException
     * @throws InterruptedException
     */
    public <T> T searchResult(SearchRequest request, Class<T> clazz) throws IOException, InterruptedException {
        String s = searchRaw(request);
        return objectMapper.readValue(s, clazz);
    }

    public String searchRaw(SearchRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = search(request);
        return response == null ? null : response.body();
    }

    /**
     * @param request            Request (will create another copy)
     * @param targetPageCount    Target page count.
     * @param timeIntervalMillis Preferred time interval between requests
     * @return Request result
     * @throws IOException
     * @throws InterruptedException
     */
    public List<HttpResponse<String>> searchLoop(SearchRequest request, int targetPageCount, long timeIntervalMillis) throws IOException, InterruptedException {
        if (isProvidingTextMatchEnabled) {
            return getHttpResponseLoopFetching(URI.create(Transformer.preTransformURI("https://api.github.com/search/" + request.getFullRequestStringWithoutPage())), acceptSchema, targetPageCount, timeIntervalMillis);
        } else {
            return getHttpResponseLoopFetching(URI.create(Transformer.preTransformURI("https://api.github.com/search/" + request.getFullRequestStringWithoutPage())), targetPageCount, timeIntervalMillis);
        }
    }

    /**
     * @param request            Request (will create another copy)
     * @return Request result
     * @throws IOException
     * @throws InterruptedException
     */
    public HttpResponse<String> search(SearchRequest request) throws IOException, InterruptedException {
        if (isProvidingTextMatchEnabled) {
            return getHttpResponse(URI.create(Transformer.preTransformURI("https://api.github.com/search/" + request.getRequestString())), acceptSchema);
        } else {
            return getHttpResponse(URI.create(Transformer.preTransformURI("https://api.github.com/search/" + request.getRequestString())));
        }
    }

    /**
     * If set to <code>true</code>, the search result will provide text-match metadata.
     *
     * @param enabled Whether the search result shall enable text-match providing.
     */
    public void setProvidingTextMatch(boolean enabled) {
        isProvidingTextMatchEnabled = enabled;
    }


    /**
     * Register a new SearchAPI based on OAuthTokens provided
     *
     * @param OAuthTokens Array of OAuthTokens
     * @return
     */
    public static SearchAPI registerAPI(String... OAuthTokens) {
        return new SearchAPI(OAuthTokens);
    }

    /**
     * Register a new SearchAPI based on OAuthTokens provided
     *
     * @param OAuthTokens List of OAuthTokens
     * @return
     */
    public static SearchAPI registerAPI(List<String> OAuthTokens) {
        return new SearchAPI(OAuthTokens);
    }
}
