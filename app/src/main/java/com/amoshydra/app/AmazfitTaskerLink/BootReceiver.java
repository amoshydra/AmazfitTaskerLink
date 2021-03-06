package com.amoshydra.app.AmazfitTaskerLink;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by amosh on 12/28/2018.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, TransporterService.class));
        }else{
            context.startService(new Intent(context, TransporterService.class));
        }
    }
}
