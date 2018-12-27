package com.amoshydra.app.AmazfitTaskerLink;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity {

    private TaskerTransporter taskerTransporter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskerTransporter = new TaskerTransporter(this);

        // Setup Interface
        setContentView(R.layout.activity_main);
        final Button button = findViewById(R.id.trigger_button_main);
        button.setOnClickListener(v -> taskerTransporter.sendAction("trigger-action"));
    }

    @Override
    public void onStop(){
        super.onStop();
    }
}
