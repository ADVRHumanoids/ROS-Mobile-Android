package com.schneewittchen.rosandroid.widgets.speech;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.ui.views.details.PublisherWidgetViewHolder;
import com.schneewittchen.rosandroid.utility.Utils;
import com.schneewittchen.rosandroid.widgets.speech.SpeechEntity;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import std_msgs.String;


/**
 * TODO: Speech to ROS topic
 *
 * @author Luca Muratore
 * @version 1.0.0
 * @created on 20.12.22
 * @updated on 20.12.22
 * @modified by
 */
public class SpeechDetailVH extends PublisherWidgetViewHolder {

    private EditText textText;
    private Spinner rotationSpinner;
    private ArrayAdapter<CharSequence> rotationAdapter;


    @Override
    public void initView(View view) {
        textText = view.findViewById(R.id.btnTextTypeText);
        rotationSpinner = view.findViewById(R.id.btnTextRotation);

        // Init spinner
        rotationAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.button_rotation, android.R.layout.simple_spinner_dropdown_item);

        rotationSpinner.setAdapter(rotationAdapter);
    }

    @Override
    protected void bindEntity(BaseEntity entity) {
        SpeechEntity speechEntity = (SpeechEntity) entity;

        textText.setText(speechEntity.text);
        java.lang.String degrees = Utils.numberToDegrees(speechEntity.rotation);
        rotationSpinner.setSelection(rotationAdapter.getPosition(degrees));
    }

    @Override
    protected void updateEntity(BaseEntity entity) {
        SpeechEntity speechEntity = (SpeechEntity)entity;

        speechEntity.text = textText.getText().toString();
        java.lang.String degrees = rotationSpinner.getSelectedItem().toString();
        speechEntity.rotation = Utils.degreesToNumber(degrees);
    }

    @Override
    public List< java.lang.String> getTopicTypes() {
        return Collections.singletonList(std_msgs.String._TYPE);
    }
}
