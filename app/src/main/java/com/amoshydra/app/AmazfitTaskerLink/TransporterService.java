package com.amoshydra.app.AmazfitTaskerLink;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by amosh on 12/28/2018.
 */

public class TransporterService extends Service {
    private TaskerTransporter taskerTransporter;
    private IBinder mBinder = new TransporterServiceBinder();

    public class TransporterServiceBinder extends Binder {
        TransporterService getService() {
            // Return this instance of LocalService so clients can call public methods
            return TransporterService.this;
        }
    }

    @Override
    public void onCreate() {
        taskerTransporter = new TaskerTransporter(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void sendAction(String actionName) {
        taskerTransporter.sendAction(actionName);
    }
}
