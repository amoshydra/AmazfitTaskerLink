package com.amoshydra.app.AmazfitTaskerLink;

import android.content.Intent;

/**
 * Created by amosh on 12/28/2018.
 */

public class TaskerIntent extends Intent {
    public static final String TASKER_INTENT_ACTION = "com.amoshydra.AmazfitTaskerLink";
    public static final String TASKER_INTENT_ACTION_KEY = "trigger_action";

    public TaskerIntent(String actionName) {
        super();
        this.setAction(TASKER_INTENT_ACTION);
        this.putExtra(TASKER_INTENT_ACTION_KEY, actionName);
    }
}
