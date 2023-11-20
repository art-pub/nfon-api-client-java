package com.silegio.nfon.model;

public class ApiRequest {
    private String URI;
    private String path;
    private String method;
    private String body;
    private String contentType;

    public ApiRequest(String uRI, String path, String method, String body, String contentType) {
        URI = uRI;
        this.path = path;
        this.method = method;
        this.body = body;
        this.contentType = contentType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String uRI) {
        URI = uRI;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    
}
