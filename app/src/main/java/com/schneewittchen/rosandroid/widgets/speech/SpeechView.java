package com.schneewittchen.rosandroid.widgets.speech;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.ui.views.widgets.PublisherWidgetView;


/**
 * TODO: Speech to ROS topic
 *
 * @author Luca Muratore
 * @version 1.0.0
 * @created on 20.12.22
 * @updated on 20.12.22
 * @modified by
 */
public class SpeechView extends PublisherWidgetView {

    public static final String TAG = SpeechView.class.getSimpleName();

    Paint buttonPaint;
    TextPaint textPaint;
    StaticLayout staticLayout;
    BroadcastReceiver broadcastReceiver;
    java.lang.String current_speech_to_text = "none";


    public SpeechView(Context context) {
        super(context);
        init();
    }

    public SpeechView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        buttonPaint = new Paint();
        buttonPaint.setColor(getResources().getColor(R.color.colorPrimary));
        buttonPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        textPaint = new TextPaint();
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextSize(26 * getResources().getDisplayMetrics().density);

        // Set up a broadcast receiver to receive the results
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get the transcript from the intent
                current_speech_to_text = intent.getStringExtra("transcript");
                Log.d("SpeechView", "Intent Speech Received: " + current_speech_to_text);
            }
        };

        IntentFilter filter = new IntentFilter("com.example.speechrecognitionapp.SPEECH_RECOGNITION_RESULTS");
        getContext().registerReceiver(broadcastReceiver, filter);


    }

    private void changeState() {

        this.publishViewData(new SpeechData(getContext(), current_speech_to_text));
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.editMode) {
            return super.onTouchEvent(event);
        }

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_UP:
                buttonPaint.setColor(getResources().getColor(R.color.colorPrimary));
                changeState();
                break;
            case MotionEvent.ACTION_DOWN:
                buttonPaint.setColor(getResources().getColor(R.color.color_attention));

                Intent intent = new Intent(getContext(), SpeechRecognitionService.class);
                getContext().startService(intent);

                break;
            default:
                return false;
        }

        return true;
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();
        float textLayoutWidth = width;

        SpeechEntity entity = (SpeechEntity) widgetEntity;

        if (entity.rotation == 90 || entity.rotation == 270) {
            textLayoutWidth = height;
        }

        canvas.drawRect(new Rect(0, 0, (int) width, (int) height), buttonPaint);

        staticLayout = new StaticLayout(entity.text,
                textPaint,
                (int) textLayoutWidth,
                Layout.Alignment.ALIGN_CENTER,
                1.0f,
                0,
                false);
        canvas.save();
        canvas.rotate(entity.rotation, width / 2, height / 2);
        canvas.translate(((width / 2) - staticLayout.getWidth() / 2), height / 2 - staticLayout.getHeight() / 2);
        staticLayout.draw(canvas);
        canvas.restore();
    }
}
