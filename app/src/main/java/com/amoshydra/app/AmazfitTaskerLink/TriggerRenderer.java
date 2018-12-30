package com.amoshydra.app.AmazfitTaskerLink;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
        );

        for (String renderInstruction : renderInstructions) {
            View view = createButton(
                    renderInstruction,
                    renderInstruction,
                    callback
            );
            triggerButtonContainer.addView(view, buttonLayoutParams);
        }
    }

    private View createButton(String label, String action, TriggerRendererButtonTriggeredCallback callback) {
        LinearLayout buttonContainer = new LinearLayout(context);

        Button button = new Button(context);
        button.setGravity(Gravity.CENTER_VERTICAL);
        buttonContainer.addView(button, new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                35
        ));

        TextView textView = new TextView(context);
        textView.setText(label);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(
                (int)(4 * densityPixelFactor),
                0,
                (int)(20 * densityPixelFactor),
                0
        );
        buttonContainer.addView(textView, new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                65
        ));
        button.setOnClickListener(view -> {
            callback.callAfterTriggerHandler(view, label, action);
        });
        return buttonContainer;
    }
}
