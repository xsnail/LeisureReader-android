package com.xsnail.leisurereader.utils;

import android.util.Log;

import com.xsnail.leisurereader.data.config.AppConfig;


/**
 * Created by xsnail on 2017/2/14.
 */

public class LogUtils {

    private static final int NOTHING = AppConfig.DEBUG_LEVEL;

    private static final int VERBOSE = 1;

    private static final int DEBUG = 2;

    private static final int INFO = 3;

    private static final int WARN = 4;

    private static final int ERROR = 5;

    public static void v(String text) {
        if (DEBUG >= NOTHING) {
            Log.v(AppConfig.TAG, text);
        }
    }

    public static void v(String tag, String text) {
        if (VERBOSE >= NOTHING) {
            Log.v(tag, text);
        }
    }

    public static void d(String text) {
        if (DEBUG >= NOTHING) {
            Log.d(AppConfig.TAG, text);
        }
    }

    public static void d(String tag, String text) {
        if (DEBUG >= NOTHING) {
            Log.d(tag, text);
        }
    }

    public static void i(String text) {
        if (DEBUG >= NOTHING) {
            Log.i(AppConfig.TAG, text);
        }
    }

    public static void i(String tag, String text) {
        if (INFO >= NOTHING) {
            Log.i(tag, text);
        }
    }

    public static void w(String text) {
        if (DEBUG >= NOTHING) {
            Log.w(AppConfig.TAG, text);
        }
    }

    public static void w(String tag, String text) {
        if (WARN >= NOTHING) {
            Log.w(tag, text);
        }
    }

    public static void e(String text) {
        if (DEBUG >= NOTHING) {
            Log.e(AppConfig.TAG, text);
        }
    }

    public static void e(String tag, String text) {
        if (ERROR >= NOTHING) {
            Log.e(tag, text);
        }
    }

}
