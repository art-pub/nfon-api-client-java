package com.silegio.nfon.client;

public class AuthResult {
    private String signature;
    private String dateString;
    private String contentType;
    private String contentMD5;
    
    public AuthResult(String signature, String dateString, String contentType, String contentMD5) {
        this.signature = signature;
        this.dateString = dateString;
        this.contentType = contentType;
        this.contentMD5 = contentMD5;
    }
    public String getSignature() {
        return signature;
    }
    public String getDateString() {
        return dateString;
    }
    public String getContentType() {
        return contentType;
    }
    public String getContentMD5() {
        return contentMD5;
    }
}
