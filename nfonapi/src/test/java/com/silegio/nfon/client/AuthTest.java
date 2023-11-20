package com.silegio.nfon.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;

import com.silegio.nfon.model.ApiConfig;
import com.silegio.nfon.model.ApiRequest;

public class AuthTest {

    @Test
    public void testGetAuthenticationWithDate() {
        // given
        ApiConfig apiConfig = new ApiConfig("", "", "4f5f5402-da77-410a-9aad-fd5ef74f746e");
        ApiRequest apiRequest = new ApiRequest("", "customers", "GET", "", "application/json");
        String date = "Sun, 31 Dec 2023 12:00:00 GMT";

        // when
        AuthResult ar = Auth.getAuthenticationWithDate(date, apiRequest, apiConfig);

        // then
        String expectedContentType = "application/json";
	    String expectedContentMD5 = "d41d8cd98f00b204e9800998ecf8427e";
	    String expectedSignature = "2kbei1CrOlKo4pkwcMJ1aOPkYzw=";
        
        assertEquals("Content-Type expected: " + expectedContentType+ ", got: " + ar.getContentType(), expectedContentType, ar.getContentType());
        assertEquals("Content-MD5 expected: '" + expectedContentMD5+ "'', got: '" + ar.getContentMD5() +"'", expectedContentMD5, ar.getContentMD5());
        assertEquals("Signature expected: " + expectedSignature+ ", got: " + ar.getSignature(), expectedSignature, ar.getSignature());

    }

    @Test
    public void testGetAuthentication() {
        // given
        ApiConfig apiConfig = new ApiConfig("", "", "4f5f5402-da77-410a-9aad-fd5ef74f746e");
        ApiRequest apiRequest = new ApiRequest("", "customers", "GET", "something weird", "application/json");

        // when
        AuthResult ar = Auth.getAuthentication(apiRequest, apiConfig);

        // then
        String expectedContentType = "application/json";
	    String expectedContentMD5 = "faec6b0502a1b21e17b658e183c18996";
        
        assertEquals("Content-Type expected: " + expectedContentType+ ", got: " + ar.getContentType(), expectedContentType, ar.getContentType());
        assertEquals("Content-MD5 expected: '" + expectedContentMD5+ "'', got: '" + ar.getContentMD5() +"'", expectedContentMD5, ar.getContentMD5());
        assertTrue("Signature with '=' at the end expected, got: " + ar.getSignature(), ar.getSignature().endsWith("="));
        assertTrue("Signature has 28 chars, got: " + ar.getSignature().length(), (ar.getSignature().length()==28));
    }
}
