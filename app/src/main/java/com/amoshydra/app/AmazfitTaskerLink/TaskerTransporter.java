package com.amoshydra.app.AmazfitTaskerLink;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.huami.watch.transport.DataBundle;
import com.kieronquinn.library.amazfitcommunication.Transporter;
import com.kieronquinn.library.amazfitcommunication.TransporterClassic;

/**
 * Created by amosh on 12/28/2018.
 */

public class TaskerTransporter {
    private TransporterClassic transporter;
    private Context applicationContext;

    public TaskerTransporter(Context context) {
        applicationContext = context;

        transporter = (TransporterClassic) Transporter.get(applicationContext , "example_module");
        transporter.addChannelListener(ready -> {
            //Transporter is ready if ready is true, send an action now. This will **NOT** work before the transporter is ready!
            //You can change the action to whatever you want, there's also an option for a data bundle to be added (see below)
            if(ready) {
                sendActionWithData("action-name");
            }
        });
        transporter.addDataListener(transportDataItem -> {
            Log.d("TransporterExample", "Item received action: " + transportDataItem.getAction());
            if(transportDataItem.getAction().equals("SEND_TASKER_INTENT")) {
                DataBundle receivedData = transportDataItem.getData();
                sendTaskerIntent(receivedData.getString("DATA"));
            }
        });
        transporter.connectTransportService();
    }

    private void sendTaskerIntent(String actionName) {
        Intent intent = new Intent();
        intent.setAction("net.dinglisch.android.tasker.ACTION_TASK");
        intent.putExtra("task_name", "watch_task");
        intent.putExtra(Intent.EXTRA_TEXT, actionName);
        applicationContext.sendBroadcast(intent);
    }

    private void sendActionWithData(String dataString){
        //Create a bundle of data
        DataBundle dataBundle = new DataBundle();
        //Key value pair
        dataBundle.putString("DATA", dataString);
        //Send action
        transporter.send("SEND_TASKER_INTENT", dataBundle, (dataTransportResult) -> {
            Log.d("TransporterExample", "onResultBack result code " + dataTransportResult.getResultCode());
        });
    }
}
