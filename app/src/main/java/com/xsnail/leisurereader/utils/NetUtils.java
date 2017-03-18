package com.xsnail.leisurereader.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by Administrator on 2017/2/14.
 */

public class NetUtils {

    public static String getWifiName(Context context){
        WifiManager wifiMgr = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiMgr.getConnectionInfo();
        return info != null ? info.getSSID() : null;
    }

    public static String getWifiIp(Context context){
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        if(ipAddress==0)return null;
        return ((ipAddress & 0xff)+"."+(ipAddress>>8 & 0xff)+"."
                +(ipAddress>>16 & 0xff)+"."+(ipAddress>>24 & 0xff));
    }
}
