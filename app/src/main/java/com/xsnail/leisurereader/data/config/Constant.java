package com.xsnail.leisurereader.data.config;

import android.graphics.Color;

import com.xsnail.leisurereader.App;
import com.xsnail.leisurereader.utils.FileUtils;

/**
 * Created by xsnail on 2017/3/21.
 */

public class Constant {
    public static final String IMG_BASE_URL = "http://statics.zhuishushenqi.com";

    public static String PATH_DATA = FileUtils.createRootPath(App.getInstance()) + "/cache";

    public static String PATH_COLLECT = FileUtils.createRootPath(App.getInstance()) + "/collect";

    public static String BASE_PATH = App.getInstance().getCacheDir().getPath();

    public static final String SUFFIX_TXT = ".txt";
    public static final String SUFFIX_PDF = ".pdf";

    public static final String ISNIGHT = "isNight";
    public static final String FLIP_STYLE = "flipStyle";


    public static final int[] tagColors = new int[]{
            Color.parseColor("#90C5F0"),
            Color.parseColor("#91CED5"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#E78F8F"),
            Color.parseColor("#67CCB7"),
            Color.parseColor("#F6BC7E")
    };

    public static final String ISBYUPDATESORT = "isByUpdateSort";
}
