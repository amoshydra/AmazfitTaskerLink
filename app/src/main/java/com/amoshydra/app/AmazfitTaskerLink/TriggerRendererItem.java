package com.amoshydra.app.AmazfitTaskerLink;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TriggerRendererItem {
    public String label;
    public String action;

    private TriggerRendererButtonTriggeredCallback callback;
    private View containerView;
    private View buttonView;

    public TriggerRendererItem(String _label, String _action, TriggerRendererButtonTriggeredCallback _callback) {
        callback = _callback;
        label = _label;
        action = _action;
    }

    public View render(Context context) {
        final float densityPixelFactor = context.getResources().getDisplayMetrics().density;

        containerView = new LinearLayout(context);

        buttonView = new Button(context);
        buttonView.setFocusable(true);
        buttonView.setFocusableInTouchMode(true);

        ((Button)buttonView).setGravity(Gravity.CENTER_VERTICAL);
        ((LinearLayout) containerView).addView(buttonView, new LinearLayout.LayoutParams(
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
        ((LinearLayout) containerView).addView(textView, new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                65
        ));
        buttonView.setOnClickListener(view -> trigger());
        return containerView;
    }

    public void trigger() {
        callback.callAfterTriggerHandler(buttonView, label, action);
    }
    public void focus() {
        buttonView.requestFocus();
    }

    public View getView() {
        return containerView;
    }
}
