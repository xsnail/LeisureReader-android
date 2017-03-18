package com.xsnail.leisurereader.wifi;

import java.io.IOException;

/**
 * Created by xsnail on 2017/2/12.
 */

public interface IResourceUrlHandler {
    boolean accept(String uri);
    void handle(String uri, HttpContext httpContext) throws IOException;
}
