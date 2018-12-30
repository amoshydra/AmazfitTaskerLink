package com.amoshydra.app.AmazfitTaskerLink;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import static android.content.ContentValues.TAG;

/**
 * Created by amosh on 12/30/2018.
 */

public class TriggerRenderer {
    private float densityPixelFactor;
    private String[] renderInstructions;
    private TransporterService transporterService;
    private Context context;

    public TriggerRenderer(Context _context, TransporterService _transporterService, String[] _renderInstructions) {
        // Store context and data
        context = _context;
        renderInstructions = _renderInstructions;
        transporterService = _transporterService; // TODO: Explore callback function to remove reliance on this

        // Initialize params
        densityPixelFactor = context.getResources().getDisplayMetrics().density;
    }

    public void render(int containerId) {
        final LinearLayout triggerButtonContainer = ((Activity) context).findViewById(containerId);
        final LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int)(60 * densityPixelFactor),
                1
        );

        for (String renderInstruction : renderInstructions) {
            Button button = createButton(
                    renderInstruction,
                    renderInstruction
            );
            triggerButtonContainer.addView(button, buttonLayoutParams);
        }
    }

    private Button createButton(String label, String action) {
        Button button = new Button(context);
        button.setText(label);
        Log.d(TAG, "createButton: Creating button");
        button.setOnLongClickListener(v -> {
            Log.d(TAG, "createButton: Sending action");
            Log.d(TAG, "createButton: Has transporterService" + String.valueOf(transporterService));
            transporterService.sendAction(action);
            button.requestFocus();
            return true;
        });
        return button;
    }
}
