package com.xsnail.leisurereader.manager;


import com.xsnail.leisurereader.data.support.RefreshBookShelfEvent;
import com.xsnail.leisurereader.data.support.RefreshUserEvent;

import org.greenrobot.eventbus.EventBus;

public class EventManager {

    public static void refreshBookShelf() {
        EventBus.getDefault().post(new RefreshBookShelfEvent());
    }

    public static void refreshUser() {
        EventBus.getDefault().post(new RefreshUserEvent());
    }


}
