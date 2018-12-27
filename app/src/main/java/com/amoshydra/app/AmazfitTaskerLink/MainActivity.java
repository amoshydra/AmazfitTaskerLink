package com.amoshydra.app.AmazfitTaskerLink;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    private TaskerTransporter taskerTransporter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskerTransporter = new TaskerTransporter(this);
    }

    @Override
    public void onStop(){
        super.onStop();
    }
}
