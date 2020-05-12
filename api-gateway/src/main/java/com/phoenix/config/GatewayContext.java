
package com.phoenix.config;

import java.io.Serializable;

public class GatewayContext implements Serializable {

    private static final long serialVersionUID = 7420632808330120030L;
    private boolean doNext = true;
    private boolean black;
    @Deprecated
    private String ssoToken;
    private String userToken;
    private String path;
    private String redirectUrl;

    public boolean isDoNext() {
        return doNext;
    }

    public void setDoNext(boolean doNext) {
        this.doNext = doNext;
    }

    public boolean isBlack() {
        return black;
    }

    public void setBlack(boolean black) {
        this.black = black;
    }

    public String getSsoToken() {
        return ssoToken;
    }

    public void setSsoToken(String ssoToken) {
        this.ssoToken = ssoToken;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public String toString() {
        return "GatewayContext{" +
                "doNext=" + doNext +
                ", black=" + black +
                ", ssoToken='" + ssoToken + '\'' +
                ", userToken='" + userToken + '\'' +
                ", path='" + path + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                '}';
    }
}
