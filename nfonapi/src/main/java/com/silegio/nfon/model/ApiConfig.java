package com.silegio.nfon.model;

public class ApiConfig {
    private String BaseURL;
    private String APIPublic;
    private String APISecret;
    
    public ApiConfig(String baseURL, String aPIPublic, String aPISecret) {
        BaseURL = baseURL;
        APIPublic = aPIPublic;
        APISecret = aPISecret;
    }
    public String getBaseURL() {
        return BaseURL;
    }
    public void setBaseURL(String baseURL) {
        BaseURL = baseURL;
    }
    public String getAPIPublic() {
        return APIPublic;
    }
    public void setAPIPublic(String aPIPublic) {
        APIPublic = aPIPublic;
    }
    public String getAPISecret() {
        return APISecret;
    }
    public void setAPISecret(String aPISecret) {
        APISecret = aPISecret;
    }

}
