package com.amoshydra.app.AmazfitTaskerLink;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    TransporterService transporterService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent serviceIntent = new Intent(this, TransporterService.class);
        startService(serviceIntent);
        bindService(serviceIntent, mConnection, this.BIND_AUTO_CREATE);

        setContentView(R.layout.activity_main);
        final float densityPixelFactor = this.getResources().getDisplayMetrics().density;
        final LinearLayout triggerButtonContainer = findViewById(R.id.trigger_button_container);
        final LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int)(60 * densityPixelFactor),
                1
        );

        for (int i = 0; i < 5; i++) {
            String indexString = String.valueOf(i + 1);

            Button button = new Button(this);
            button.setText("Trigger " + indexString );
            triggerButtonContainer.addView(button, buttonLayoutParams);

            button.setOnLongClickListener(v -> {
                transporterService.sendAction("trigger-" + indexString );
                button.requestFocus();
                return true;
            });
        }
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
