package com.xsnail.leisurereader.wifi;

import android.os.Environment;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by xsnail on 2017/2/12.
 */

public class UploadTxtHandler implements IResourceUrlHandler {
    private String acceptPrefix = "/upload_txt/";

    @Override
    public boolean accept(String uri) {
        return uri.startsWith(acceptPrefix);
    }

    @Override
    public void handle(String uri, HttpContext httpContext) throws IOException {
        String length = httpContext.getRequestHeaderValue("Content-Length").replace("\r\n","").trim();
        String tmpPath = Environment.getExternalStorageDirectory().getPath() + "/test_txt.txt";
        Log.d("UploadImageHandler","长度为:"+length+"文件地址为:"+tmpPath);
        long totalLength = Long.parseLong(length);
        FileOutputStream fos = new FileOutputStream(tmpPath);
        InputStream nis = httpContext.getUnderlySocket().getInputStream();
        byte[] buffer = new byte[1024*1024*10];
        int nReaded = 0;
        long nLeftLength = totalLength;
        while(nLeftLength > 0 && (nReaded = nis.read(buffer)) > 0){
            fos.write(buffer,0,nReaded);
            nLeftLength -= nReaded;
        }
        fos.close();

        OutputStream nos = httpContext.getUnderlySocket().getOutputStream();
        PrintStream printer = new PrintStream(nos);
        printer.println("HTTP/1.1 200 OK");
        printer.println();
        nos.close();

    }
}
