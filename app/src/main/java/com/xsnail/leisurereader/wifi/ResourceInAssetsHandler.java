package com.xsnail.leisurereader.wifi;

import android.content.Context;

import com.xsnail.leisurereader.utils.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by xsnail on 2017/2/12.
 */

public class ResourceInAssetsHandler implements IResourceUrlHandler {

    private Context context;

    public String acceptPrefix = "/upload/";

    public ResourceInAssetsHandler(Context context) {
        this.context = context;
    }

    @Override
    public boolean accept(String uri) {
        return uri.startsWith(acceptPrefix);
    }

    @Override
    public void handle(String uri, HttpContext httpContext) throws IOException {
        int startIndex = acceptPrefix.length();
        String assetsPath = uri.substring(startIndex);
//        String assetsPath = "upload/index.html";
        InputStream fis = context.getAssets().open(assetsPath);
        byte[] raw = StreamUtils.readRawFromStream(fis);
        fis.close();
        OutputStream nos = httpContext.getUnderlySocket().getOutputStream();
        PrintStream printStream = new PrintStream(nos);
        printStream.println("HTTP/1.1 200 OK");
        printStream.println("Content-Length:"+raw.length);
        if(assetsPath.endsWith(".html")){
            printStream.println("Content-Type:text/html");
        }else if(assetsPath.endsWith(".js")){
            printStream.println("Content-Type:text/js");
        }else if(assetsPath.endsWith(".css")){
            printStream.println("Content-Type:text/css");
        }else if(assetsPath.endsWith(".jpg")){
            printStream.println("Content-Type:text/jpg");
        }else if(assetsPath.endsWith(".png")){
            printStream.println("Content-Type:text/png");
        }
        printStream.println();
        printStream.write(raw);
        nos.close();

    }
}
