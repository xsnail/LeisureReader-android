package com.xsnail.leisurereader.wifi;


import com.xsnail.leisurereader.utils.LogUtils;
import com.xsnail.leisurereader.utils.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xsnail on 2017/1/29.
 */

public class SimpleHttpServer {
    private static final String TAG = "SimpleHttpServer";

    private boolean isEnable;
    private WebConfiguration webConfig;
    private ServerSocket socket;
    private ExecutorService threadPool;
    private Set<IResourceUrlHandler> resourceUrlHandlers;

    public SimpleHttpServer(WebConfiguration webConfiguration) {
        this.webConfig = webConfiguration;
        this.threadPool = Executors.newCachedThreadPool();
        resourceUrlHandlers = new HashSet<>();
    }

    /**
     * 启动server
     */
    public void startAsync(){
        isEnable = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                doProcSync();
            }
        }).start();
    }

    /**
     * 停止server
     */
    public void stopAsync(){
        if(!isEnable){
          return;
        }
        isEnable = false;
        try {
            socket.close();
            socket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doProcSync() {
        try {
            InetSocketAddress socketAddress = new InetSocketAddress(webConfig.getPort());
            socket = new ServerSocket();
            socket.bind(socketAddress);
            while (isEnable){
                final Socket remotePeer = socket.accept();
                threadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.d(TAG,"a remote peer accepted..." + remotePeer.getRemoteSocketAddress().toString());
                        onAcceptRemotePeer(remotePeer);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.d(TAG,e.toString());
        }
    }

    public void registResourceHandler(IResourceUrlHandler handler){
        resourceUrlHandlers.add(handler);
    }

    private void onAcceptRemotePeer(Socket remotePeer) {
        try {
//            remotePeer.getOutputStream().write("congratulations, connected successful".getBytes());
            HttpContext httpContext = new HttpContext();
            httpContext.setUnderlySocket(remotePeer);
            InputStream is = remotePeer.getInputStream();
            String headerLine = null;
            String resourceUri = StreamUtils.readLine(is).split(" ")[1];
            LogUtils.d(TAG,resourceUri);
            while((headerLine = StreamUtils.readLine(is)) != null){
                if(headerLine.equals("\r\n")){
                    break;
                }
                String[] pair = headerLine.split(":");
                if(pair.length > 1) {
                    httpContext.addRequestHeader(pair[0], pair[1]);
                }
                LogUtils.d(TAG,headerLine);
            }

            for(IResourceUrlHandler handler : resourceUrlHandlers){
                if(!handler.accept(resourceUri)){
                    continue;
                }
                handler.handle(resourceUri,httpContext);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.d(TAG,e.toString());
        }
    }
}
