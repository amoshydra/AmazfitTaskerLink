package com.amoshydra.app.AmazfitTaskerLink;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;

public class MainActivity extends Activity {
    TransporterService transporterService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent serviceIntent = new Intent(this, TransporterService.class);
        startService(serviceIntent);
        bindService(serviceIntent, mConnection, this.BIND_AUTO_CREATE);

        // Setup Interface
        setContentView(R.layout.activity_main);
        final Button button = findViewById(R.id.trigger_button_main);
        button.setOnClickListener(v -> transporterService.sendAction("trigger-action"));
    }

    @Override
    public void onStop(){
        super.onStop();
        unbindService(mConnection);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            TransporterService.TransporterServiceBinder binder = (TransporterService.TransporterServiceBinder) service;
            transporterService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}
