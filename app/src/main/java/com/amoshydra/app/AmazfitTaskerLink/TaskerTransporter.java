package com.amoshydra.app.AmazfitTaskerLink;

import android.content.Context;
import android.util.Log;

import com.huami.watch.transport.DataBundle;
import com.kieronquinn.library.amazfitcommunication.Transporter;
import com.kieronquinn.library.amazfitcommunication.TransporterClassic;

/**
 * Created by amosh on 12/28/2018.
 */

public class TaskerTransporter {
    public static final String TASKER_TRANSPORTER_ACTION_NAME = "SEND_TASKER_INTENT";
    public static final String DEFAULT_CHANNEL_NAME = "com.amoshydra.app.AmazfitTaskerLink";
    public static final String DATA_BUNDLE_ACTION_KEY = "ACTION_NAME";
    public static final String CLASS_NAME = "TaskerTransporter";

    private TransporterClassic transporter;
    private Context applicationContext;
    private TaskerIntentManager taskerIntentManager;

    public TaskerTransporter(Context context) {
        this(context, DEFAULT_CHANNEL_NAME);
    }

    public TaskerTransporter(Context context, String channelName) {
        applicationContext = context;
        taskerIntentManager = new TaskerIntentManager(context);

        transporter = (TransporterClassic) Transporter.get(applicationContext , channelName);
        transporter.addChannelListener(ready -> {
            //Transporter is ready if ready is true, send an action now. This will **NOT** work before the transporter is ready!
            //You can change the action to whatever you want, there's also an option for a data bundle to be added (see below)
            if(ready) {
                sendAction("link-ready");
            }
        });
        transporter.addDataListener(transportDataItem -> {
            Log.d(CLASS_NAME, "Item received action: " + transportDataItem.getAction());
            taskerIntentManager.broadcastTaskerIntent(
                    transportDataItem
                            .getData()
                            .getString(DATA_BUNDLE_ACTION_KEY)
            );
        });
        transporter.connectTransportService();
    }

    public void sendAction(String actionName){
        DataBundle dataBundle = new DataBundle();
        dataBundle.putString(DATA_BUNDLE_ACTION_KEY, actionName);

        //Send action
        transporter.send(TASKER_TRANSPORTER_ACTION_NAME, dataBundle, (dataTransportResult) -> {
            Log.d(CLASS_NAME, "onResultBack result code " + dataTransportResult.getResultCode());
        });
    }
}
