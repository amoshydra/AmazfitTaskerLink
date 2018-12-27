package com.amoshydra.app.AmazfitTaskerLink;

import android.content.Context;

public class TaskerIntentManager {
    private Context applicationContext;

    public TaskerIntentManager(Context context) {
        applicationContext = context;
    }

    public void broadcastTaskerIntent(String actionName) {
        applicationContext.sendBroadcast(
                new TaskerIntent(actionName)
        );
    }
}
