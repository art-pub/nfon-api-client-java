package com.silegio.nfon.examples;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import org.json.*;

import com.silegio.nfon.client.ApiClient;
import com.silegio.nfon.client.Auth;
import com.silegio.nfon.client.AuthResult;
import com.silegio.nfon.model.ApiConfig;
import com.silegio.nfon.model.ApiRequest;
import com.silegio.nfon.model.StatusEntity;

/**
 * StatusRequest - do a very simple GET Request.
 * 
 * Please note: The status request no longer needs an authentication.
 */
public class StatusRequest {

    public static void main(String[] args) throws IOException, URISyntaxException {

        if (args.length < 1) {
            System.err.println("Missing parameter API root URL.");
            return;
        }

        String apiRootURL =  args[0];

        // apiSecret cannot be empty, so "-" is set here:
        ApiConfig config = new ApiConfig(apiRootURL, "", "-");
        ApiRequest request = new ApiRequest("", "/api/version", "GET", "", "application/json");

        // get the signature for the requests if needed
        AuthResult result = Auth.getAuthentication(request, config);

        // get the client
        HttpURLConnection connection = ApiClient.Request(config, request, result);

        // execute the request
        int responseCode = connection.getResponseCode();

        // check response
        if (responseCode == 200) {
            // fetch result
            String responseBody = new String(connection.getInputStream().readAllBytes());

            // parse the result - single response
            JSONObject obj = new JSONObject(responseBody);

            StatusEntity stat = new StatusEntity();
            stat.setHref(obj.getString("href"));

            JSONArray arr = obj.getJSONArray("data");
            for (int i=0; i<arr.length(); i++) {
                switch (arr.getJSONObject(i).getString("name")) {
                    case "version":
                        stat.setVersion(arr.getJSONObject(i).getString("value"));
                        break;
                
                    case "host":
                        stat.setHost(arr.getJSONObject(i).getString("value"));
                        break;

                    case "buildTime":
                        stat.setBuildTime(arr.getJSONObject(i).getString("value"));
                        break;

                    default:
                        break;
                }
            }

            // show result
            System.out.printf("Version: %s, BuilTime: %s, Host: %s\n", stat.getVersion(), stat.getBuildTime(), stat.getHost());
        } else {
            // error handling
            System.out.println("Error: " + responseCode);
        }
    }
}
