package API.search.requests;

public abstract class SearchRequest {

    StringBuilder requestBuilder;
    int page;

    public SearchRequest() {
        requestBuilder = new StringBuilder();
        page = 1;
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

    static StringBuilder removeTrailingSpace(StringBuilder stringBuilder) {
        if (stringBuilder.lastIndexOf("  ") != -1) {
            int index = stringBuilder.lastIndexOf("  ");
            stringBuilder.replace(index, index + 2, " ");
        }
        if (stringBuilder.charAt(stringBuilder.length() - 1) == ' ') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder;
    }

    static String wrapIfRequired(String wrapped) {
        if (wrapped.contains(" ")) {
            return '"' + wrapped + '"';
        } else {
            return wrapped;
        }
    }

}
