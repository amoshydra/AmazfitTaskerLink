package com.amoshydra.app.AmazfitTaskerLink;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by amosh on 12/30/2018.
 */
interface TriggerRendererButtonTriggeredCallback {
    void callAfterTriggerHandler(View view, String label, String action);
}

public class TriggerRenderer {
    private float densityPixelFactor;
    private String[] renderInstructions;
    private Context context;

    public TriggerRenderer(Context _context, String[] _renderInstructions) {
        // Store context and data
        context = _context;
        renderInstructions = _renderInstructions;

        // Initialize params
        densityPixelFactor = context.getResources().getDisplayMetrics().density;
    }

    public void render(int containerId, TriggerRendererButtonTriggeredCallback callback) {
        final LinearLayout triggerButtonContainer = ((Activity) context).findViewById(containerId);
        final LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int)(60 * densityPixelFactor),
                1
        );

        for (String renderInstruction : renderInstructions) {
            Button button = createButton(
                    renderInstruction,
                    renderInstruction,
                    callback
            );
            triggerButtonContainer.addView(button, buttonLayoutParams);
        }
    }

    private Button createButton(String label, String action, TriggerRendererButtonTriggeredCallback callback) {
        Button button = new Button(context);
        button.setText(label);
        button.setOnLongClickListener(view -> {
            callback.callAfterTriggerHandler(view, label, action);
            return true;
        });
        return button;
    }
}
