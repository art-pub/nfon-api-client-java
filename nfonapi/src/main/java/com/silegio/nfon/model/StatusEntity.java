package com.silegio.nfon.model;

public class StatusEntity {
    private String href;
    private String version;
    private String host;
    private String buildTime;
    
    public String getHref() {
        return href;
    }
    public void setHref(String href) {
        this.href = href;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public String getBuildTime() {
        return buildTime;
    }
    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
}