package com.xsnail.leisurereader.wifi;

import java.net.Socket;
import java.util.HashMap;

/**
 * Created by xsnail on 2017/2/12.
 */
public class HttpContext {
    private final HashMap<String,String> requestHeaders;

    public HttpContext() {
        requestHeaders = new HashMap<>();
    }

    private Socket underlySocket;

    public void setUnderlySocket(Socket underlySocket) {
        this.underlySocket = underlySocket;
    }

    public Socket getUnderlySocket() {
        return underlySocket;
    }

    public void addRequestHeader(String headerName, String headerValue) {
        requestHeaders.put(headerName,headerValue);
    }

    public String getRequestHeaderValue(String headerName) {
        return requestHeaders.get(headerName);
    }



}
