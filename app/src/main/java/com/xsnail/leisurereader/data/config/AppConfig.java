package com.xsnail.leisurereader.data.config;

import com.xsnail.leisurereader.App;
import com.xsnail.leisurereader.utils.FileUtils;

/**
 * Created by xsnail on 2017/2/14.
 */

public interface AppConfig {
    String TAG = "xsnail";

    int DEBUG_LEVEL = 0;

    String PATH_DATA = FileUtils.createRootPath(App.getInstance()) + "/cache";

    String PATH_COLLECT = FileUtils.createRootPath(App.getInstance()) + "/shelf";

    String PATH_TXT = PATH_DATA + "/book/";

}
