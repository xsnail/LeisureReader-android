package com.xsnail.leisurereader.utils;

import android.content.Context;
import android.telephony.TelephonyManager;


/**
 * Created by xsnail on 2017/2/14.
 */

public class DeviceUtils {
    /**
     * 获取设备唯一标识IMEI
     * @param context
     * @return
     */
    public static String getIMEI(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        return imei;
    }
}
