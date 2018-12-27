package com.amoshydra.app.AmazfitTaskerLink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.huami.watch.transport.DataBundle;
import com.huami.watch.transport.DataTransportResult;
import com.huami.watch.transport.TransportDataItem;
import com.kieronquinn.library.amazfitcommunication.Transporter;
import com.kieronquinn.library.amazfitcommunication.TransporterClassic;

public class MainActivity extends Activity {

    private TransporterClassic transporter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTransporter();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    private void setupTransporter() {
        //Create the transporter **WARNING** The second parameter MUST be the same on both your watch and phone companion apps!
        //Please change the module name to something unique, but keep it the same for both apps!
        transporter = (TransporterClassic) Transporter.get(this, "example_module");
        //Add a channel listener to listen for ready event
        transporter.addChannelListener(new Transporter.ChannelListener() {
            @Override
            public void onChannelChanged(boolean ready) {
                //Transporter is ready if ready is true, send an action now. This will **NOT** work before the transporter is ready!
                //You can change the action to whatever you want, there's also an option for a data bundle to be added (see below)
                if(ready) {
                    sendActionWithData("action-name");
                }
            }
        });
        transporter.addDataListener(new Transporter.DataListener() {
            @Override
            public void onDataReceived(TransportDataItem transportDataItem) {
                Log.d("TransporterExample", "Item received action: " + transportDataItem.getAction());
                if(transportDataItem.getAction().equals("SEND_TASKER_INTENT")) {
                    DataBundle receivedData = transportDataItem.getData();
                    //Do whatever with your action & data. You can send data back in the same way using the same transporter

                    Log.d("TransporterExample", "Broadcasting intent");

                    Intent intent = new Intent();
                    intent.setAction("net.dinglisch.android.tasker.ACTION_TASK");
                    intent.putExtra("task_name", "watch_task");
                    intent.putExtra(Intent.EXTRA_TEXT, receivedData.getString("DATA"));
                    sendBroadcast(intent);
                }
            }
        });
        transporter.connectTransportService();
    }

    private void sendActionWithData(String dataString){
        //Create a bundle of data
        DataBundle dataBundle = new DataBundle();
        //Key value pair
        dataBundle.putString("DATA", dataString);
        //Send action
        transporter.send("SEND_TASKER_INTENT", dataBundle, new Transporter.DataSendResultCallback() {
            @Override
            public void onResultBack(DataTransportResult dataTransportResult) {
            Log.d("TransporterExample", "onResultBack result code " + dataTransportResult.getResultCode());
            }
        });
    }
}
