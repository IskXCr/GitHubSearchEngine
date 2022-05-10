package API.search.requests;

public abstract class SearchRequest {

    StringBuilder requestBuilder;
    int page;

    public SearchRequest() {
        requestBuilder = new StringBuilder();
        page = 0;
    }

    public void setResultPage(int page) {
        this.page = page;
    }

    public String getRequestStringRaw() {
        return requestBuilder.toString().concat(page > 0 ? ("&page=" + page) : "");
    }

    public String getRequestString() {
        return requestBuilder.toString().replace(' ', '+').concat(page > 0 ? ("&page=" + page) : "");
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
