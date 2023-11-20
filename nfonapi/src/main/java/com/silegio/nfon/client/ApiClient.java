package com.silegio.nfon.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.silegio.nfon.model.ApiConfig;
import com.silegio.nfon.model.ApiRequest;

public class ApiClient {
    /**
     * Request - create a HttpURLConnection for NFON API requests
     * @param apiConfig
     * @param apiRequest
     * @param authResult
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public static HttpURLConnection Request(ApiConfig apiConfig, ApiRequest apiRequest, AuthResult authResult) throws URISyntaxException, IOException  {
        String apiRequestPath = apiConfig.getBaseURL() + apiRequest.getPath();

        URL url = new URI(apiRequestPath).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // set the METHOD
        connection.setRequestMethod(apiRequest.getMethod());

        // set the NFON API specific request headers
        connection.setRequestProperty("Authorization", "NFON-API " + apiConfig.getAPIPublic()+":"+authResult.getSignature());
        connection.setRequestProperty("x-nfon-date", authResult.getDateString());
        connection.setRequestProperty("date", authResult.getDateString());
        
        if (authResult.getContentMD5() != "") {
            connection.setRequestProperty("Content-MD5", authResult.getContentMD5());
        }

        int length = apiRequest.getBody().length();
        if (length > 0) {
            connection.setRequestProperty("Content-Length", ""+length);
        }

        String contentType = apiRequest.getContentType();
        if (contentType != "") {
            connection.setRequestProperty("Content-Type", ""+contentType);
        }

        return connection;
    }
}
