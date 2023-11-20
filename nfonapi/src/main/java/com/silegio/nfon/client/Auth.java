package com.silegio.nfon.client;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import com.silegio.nfon.model.ApiConfig;
import com.silegio.nfon.model.ApiRequest;

/**
 * NFON API Authentication
 */
public class Auth {

    public static String DEFAULT_CONTENT_TYPE = "application/json";

    /**
     * getAuthentication - calculate the signature and needed data
     * @param apiRequest
     * @param apiConfig
     * @return
     */
    public static AuthResult getAuthentication(ApiRequest apiRequest, ApiConfig apiConfig) {

        final Date currentTime = new Date();

        final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);

        // Give it to me in GMT time.
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        String dateString = sdf.format(currentTime);

        return getAuthenticationWithDate(dateString, apiRequest, apiConfig);
    }

    /**
     * getAuthenticationWithDate - calculate the signature and needed data based on the dateString
     * @param dateString
     * @param apiRequest
     * @param apiConfig
     * @return
     */
    public static AuthResult getAuthenticationWithDate(String dateString, ApiRequest apiRequest, ApiConfig apiConfig) {

        String contentMD5 = DigestUtils.md5Hex(apiRequest.getBody());

        String stringToSign = apiRequest.getMethod() + "\n" + contentMD5 + "\n" + apiRequest.getContentType() + "\n" + dateString + "\n" + apiRequest.getPath();

        byte[] macString = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, apiConfig.getAPISecret()).hmac(stringToSign);

        String signature = Base64.getEncoder().encodeToString(macString);

        return new AuthResult(signature, dateString, apiRequest.getContentType(), contentMD5);
    }
}
