package com.xsnail.leisurereader.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/2/12.
 */

public class StreamUtils {
    public static final String readLine(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        int c1 = 0;
        int c2 = 0;
        while (c2 != -1 && !(c1 == '\r' && c2 == '\n')){
            c1 = c2;
            c2 = is.read();
            sb.append((char) c2);
        }
        return sb.toString();
    }

    public static byte[] readRawFromStream(InputStream fis) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024*1024*10];
        int nReaded;
        while((nReaded = fis.read(buffer)) > 0){
            bos.write(buffer,0,nReaded);
        }
        return bos.toByteArray();
    }
}
