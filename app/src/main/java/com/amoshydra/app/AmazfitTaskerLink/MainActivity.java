package com.amoshydra.app.AmazfitTaskerLink;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;

public class MainActivity extends Activity {
    TransporterService transporterService;
    TriggerRenderer triggerRenderer;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent serviceIntent = new Intent(this, TransporterService.class);
        startService(serviceIntent);
        bindService(serviceIntent, mConnection, this.BIND_AUTO_CREATE);

        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStop(){
        super.onStop();
        unbindService(mConnection);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (!mBound) return true;

        switch (keyCode) {
            case KeyEvent.KEYCODE_F5:
                return triggerRenderer.navigate(-1);
            case KeyEvent.KEYCODE_F6:
                return triggerRenderer.navigate(1);
            case KeyEvent.KEYCODE_F10:
                return triggerRenderer.navigate(0);
            default:
                return true;
        }
    }

    public void onAppServiceReady() {
        // Get list to render
        final String[] renderInstructions = {
                "Take picture",
                "Lock phone",
                "Play music",
                "Trigger 1",
                "Trigger 2",
        };

        // Render list
        triggerRenderer = new TriggerRenderer(this, renderInstructions);
        triggerRenderer.render(
                R.id.trigger_button_container,
                (view, label, action) -> transporterService.sendAction(action)
        );
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            TransporterService.TransporterServiceBinder binder = (TransporterService.TransporterServiceBinder) service;
            transporterService = binder.getService();
            mBound = true;
            onAppServiceReady();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}
