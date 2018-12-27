package com.amoshydra.app.AmazfitTaskerLink;

import android.content.Intent;

/**
 * Created by amosh on 12/28/2018.
 */

public class TaskerIntent extends Intent {
    public static final String TASKER_INTENT_ACTION = "net.dinglisch.android.tasker.ACTION_TASK";
    public static final String TASKER_INTENT_ACTION_KEY = "task_name";
    public static final String TASKER_INTENT_ACTION_VALUE = "com.amoshydra.AmazfitTaskerLink";
    public static final String TASKER_INTENT_ACTION_NAME = "action_name";

    public TaskerIntent(String actionName) {
        super();
        this.setAction(TASKER_INTENT_ACTION);
        this.putExtra(TASKER_INTENT_ACTION_KEY, TASKER_INTENT_ACTION_VALUE);
        this.putExtra(TASKER_INTENT_ACTION_NAME, actionName);
    }
}
