package models;

public record OAuthToken(String OAuthToken) {

    public OAuthToken(String OAuthToken){
        this.OAuthToken = OAuthToken;
    }

    @Override
    public String OAuthToken() {
        return OAuthToken;
    }
}
