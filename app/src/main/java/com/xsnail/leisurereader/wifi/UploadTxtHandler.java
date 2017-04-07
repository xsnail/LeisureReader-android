package com.xsnail.leisurereader.wifi;

import android.util.Log;

import com.xsnail.leisurereader.App;
import com.xsnail.leisurereader.data.bean.Recommend;
import com.xsnail.leisurereader.manager.CollectionsManager;
import com.xsnail.leisurereader.manager.EventManager;
import com.xsnail.leisurereader.utils.FileUtils;
import com.xsnail.leisurereader.utils.LogUtils;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.Map;

/**
 * Created by xsnail on 2017/2/12.
 */

public class UploadTxtHandler implements IResourceUrlHandler {
    private String acceptPrefix = "/upload_txt/";
    private String fileName = "test";

    @Override
    public boolean accept(String uri) {
        return uri.startsWith(acceptPrefix);
    }

    @Override
    public void handle(String uri, HttpContext httpContext) throws IOException {
        Map<String,String> map = httpContext.getRequestHeaders();
        for(String s : map.keySet()){
            Log.d("test",s +" "+ map.get(s));
        }
        String length = httpContext.getRequestHeaderValue("Content-Length").replace("\r\n","").trim();
        String tmpPath = FileUtils.createRootPath(App.getInstance()) + "/hello.txt";
        File file = new File(tmpPath);
        if (!file.exists())
            FileUtils.createFile(file);
        Log.d("UploadTxtHandler","长度为:"+length+"文件地址为:"+tmpPath);

        InputStream nis = httpContext.getUnderlySocket().getInputStream();

        parseRequest(nis,file);

        // 创建目标文件
        File desc = FileUtils.createWifiTranfesFile(fileName);
        LogUtils.i("--" + desc.getAbsolutePath());

        FileUtils.fileChannelCopy(file, desc);

        // 添加到收藏
        addToCollection(fileName);

        OutputStream nos = httpContext.getUnderlySocket().getOutputStream();
        PrintStream printer = new PrintStream(nos);
        printer.println("HTTP/1.1 200 OK");
        printer.println();
        printer.write("上传成功了".getBytes("UTF-8"));
        nos.close();

    }

    private void addToCollection(String fileName){
        Recommend.RecommendBooks books = new Recommend.RecommendBooks();
        books.isFromSD = true;

        books._id = fileName;
        books.title = fileName;

        if (CollectionsManager.getInstance().add(books)) {
            EventManager.refreshCollectionList();
        }
    }


    private void parseRequest(InputStream inputStream, File tmpFile) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        String str = null;

        String boundary = br.readLine();
        //解析消息体
        while ((str = br.readLine()) != null) {
            //说明是文件上传
            if (str.indexOf("Content-Disposition:") >= 0 && str.indexOf("filename") > 0) {
                str = str.substring("Content-Disposition:".length());
                String[] strs = str.split(";");
                fileName = strs[strs.length - 1].replace("\"", "").split("=")[1];
                Log.d("fileName = ", fileName);
                //这一行是Content-Type
                br.readLine();
                //这一行是换行
                br.readLine();
                //正式去读文件的内容
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new OutputStreamWriter(new
                            FileOutputStream(tmpFile),"UTF-8"));
                    while (true) {
                        str = br.readLine();
                        if (str.startsWith(boundary)) {
                            break;
                        }
                        Log.d("test",new String(str.getBytes(),"UTF-8"));
                        bw.write(new String(str.getBytes(),"UTF-8"));
                        bw.newLine();
                    }
                    bw.flush();
                } catch (Exception e) {

                } finally {
                    if (bw != null) {
                        bw.close();
                    }
                }
            }

            //解析结束
            if (str.equals(boundary + "--")) {
                break;
            }
        }
    }
}
