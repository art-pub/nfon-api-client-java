package com.silegio.nfon.examples;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.silegio.nfon.client.ApiClient;
import com.silegio.nfon.client.Auth;
import com.silegio.nfon.client.AuthResult;
import com.silegio.nfon.model.ApiConfig;
import com.silegio.nfon.model.ApiRequest;

/**
 * PhoneBookRequest - Example with multiple results in the response
 */
public class PhoneBookRequest {
    public static void main(String[] args) throws URISyntaxException, IOException {
        if (args.length < 4) {
            System.err.println("Missing API parameters.");
            return;
        }

        String apiRootURL = args[0];
        String apiKey = args[1];
        String apiSecret = args[2];
        String customer = args[3];

        if (apiRootURL == "" || apiKey == "" || apiSecret == "") {
            System.err.println("Missing API parameters.");
            return;
        }

        ApiConfig apiConfig = new ApiConfig(apiRootURL, apiKey, apiSecret);
        ApiRequest apiRequest = new ApiRequest(apiRootURL, "/api/customers/" + customer + "/phone-books?_pagesize=3",
                "GET", "", "application/json");

        AuthResult result = Auth.getAuthentication(apiRequest, apiConfig);

        // get the client
        HttpURLConnection connection = ApiClient.Request(apiConfig, apiRequest, result);

        // execute the request
        int responseCode = connection.getResponseCode();

        // check response
        if (responseCode == 200) {
            // fetch result
            String responseBody = new String(connection.getInputStream().readAllBytes());
            System.out.println("-> " + responseBody);

            ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();

            // parse the multiple result objects in the response
            JSONObject obj = new JSONObject(responseBody);

            // if there is not a single result, the results are in the array "items"
            List<String> itemsList = getValuesInArray(obj.getJSONArray("items"), "data");
            
            // put the result in the items ArrayList
            for (int i = 0; i < itemsList.size(); i++) {
                HashMap<String, String> data = new HashMap<String, String>();
                JSONArray item = new JSONArray(itemsList.get(i));
                for (int j=0; j< item.length(); j++ ) {
                    Object value = item.getJSONObject(j).get("value");
                    data.put(item.getJSONObject(j).getString("name"), value.toString());
                }
                items.add(data);
            }

            // show the results:
            for (int i=0; i<items.size(); i++) {
                System.out.println("-> " + items.get(i).get("displayName") + ": " + items.get(i).get("displayNumber"));
            }
        } else {
            // error handling
            System.out.println("Error: " + responseCode);
        }
    }

    /**
     * Helper (c) https://www.baeldung.com/java-jsonobject-get-value
     * @param jsonObject
     * @param key
     * @return
     */
    public static List<String> getValuesInObject(JSONObject jsonObject, String key) {
        List<String> accumulatedValues = new ArrayList<>();
        for (String currentKey : jsonObject.keySet()) {
            Object value = jsonObject.get(currentKey);
            if (currentKey.equals(key)) {
                accumulatedValues.add(value.toString());
            }

            if (value instanceof JSONObject) {
                accumulatedValues.addAll(getValuesInObject((JSONObject) value, key));
            } else if (value instanceof JSONArray) {
                accumulatedValues.addAll(getValuesInArray((JSONArray) value, key));
            }
        }

        return accumulatedValues;
    }

    /**
     * Helper (c) https://www.baeldung.com/java-jsonobject-get-value
     * @param jsonArray
     * @param key
     * @return
     */
    public static List<String> getValuesInArray(JSONArray jsonArray, String key) {
        List<String> accumulatedValues = new ArrayList<>();
        for (Object obj : jsonArray) {
            if (obj instanceof JSONArray) {
                accumulatedValues.addAll(getValuesInArray((JSONArray) obj, key));
            } else if (obj instanceof JSONObject) {
                accumulatedValues.addAll(getValuesInObject((JSONObject) obj, key));
            }
        }

        return accumulatedValues;
    }
}
