package com.amoshydra.app.AmazfitTaskerLink;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by amosh on 12/30/2018.
 */
interface TriggerRendererButtonTriggeredCallback {
    void callAfterTriggerHandler(View view, String label, String action);
}

public class TriggerRenderer {

    private String[] renderInstructions;
    private TriggerRendererItem[] rendererItems;
    private int currentlyFocused = 0;
    private Context context;

    public TriggerRenderer(Context _context, String[] _renderInstructions) {
        // Store context and data
        context = _context;
        renderInstructions = _renderInstructions;
    }

    public void render(int containerId, TriggerRendererButtonTriggeredCallback callback) {
        rendererItems = new TriggerRendererItem[renderInstructions.length];
        final LinearLayout triggerButtonContainer = ((Activity) context).findViewById(containerId);
        final LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
        );

        // Create button views
        for (int i = 0; i < renderInstructions.length; i++) {
            rendererItems[i] = createButton(
                    renderInstructions[i],
                    renderInstructions[i],
                    callback
            );
        }
        // Render button views
        for (TriggerRendererItem item: rendererItems) {
            triggerButtonContainer.addView(item.getView(), buttonLayoutParams);
        }
    }

    private TriggerRendererItem createButton(String label, String action, TriggerRendererButtonTriggeredCallback callback) {
        TriggerRendererItem triggerRendererItem = new TriggerRendererItem(label, action, callback);
        triggerRendererItem.render(context);
        return triggerRendererItem;
    }

    public boolean navigate(int direction) {
        if (direction == 0) {
            rendererItems[currentlyFocused].trigger();
        } else {
            int newFocusItem = Math.max(0, Math.min(rendererItems.length - 1, currentlyFocused += direction));

            rendererItems[newFocusItem].focus();
            currentlyFocused = newFocusItem;
        }

        return true;
    }
}
