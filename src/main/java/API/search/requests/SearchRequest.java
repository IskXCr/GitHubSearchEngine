package API.search.requests;

public class SearchRequest {

    StringBuilder requestBuilder;
    int page;

    SearchRequest() {
        requestBuilder = new StringBuilder();
        page = 1;
    }

    public SearchRequest(SearchRequest request) {
        requestBuilder = new StringBuilder(request.requestBuilder.toString());
        page = request.page;
    }

    /**
     * Set the result page number.<br>
     * If <code>page<=0</code>, then the default page = 1 will be used. Otherwise,
     * the corresponding result on <code>page</code> will be retrieved.
     *
     * @param page
     */
    public void setResultPage(int page) {
        if (page <= 0) {
            this.page = 1;
        } else {
            this.page = page;
        }
    }

    /**
     * Increase result page number by <code>amount</code>
     *
     * @param amount amount
     */
    public void incrResultPage(int amount) {
        if (amount >= 0) {
            page += amount;
        }
    }

    public int getResultPage() {
        return this.page;
    }

    public String getRequestStringRaw() {
        return requestBuilder.toString().concat("&page=" + page);
    }

    public String getRequestString() {
        return requestBuilder.toString().replace(' ', '+').concat("&page=" + page);
    }

    public StringBuilder getRequestBuilder() {
        return this.requestBuilder;
    }


    //Below are utility methods

    static String wrapIfRequired(String wrapped) {
        if (wrapped.contains(" ")) {
            return '"' + wrapped + '"';
        } else {
            return wrapped;
        }
    }

}
