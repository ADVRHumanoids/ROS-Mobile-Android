package com.schneewittchen.rosandroid.widgets.speech;

import android.content.Context;
import android.util.Log;

import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.model.repositories.rosRepo.node.BaseData;

import org.ros.internal.message.Message;
import org.ros.node.topic.Publisher;


/**
 * TODO: Speech to ROS topic
 *
 * @author Luca Muratore
 * @version 1.0.0
 * @created on 20.12.22
 * @updated on 20.12.22
 * @modified by
 */
public class SpeechData extends BaseData {

    private java.lang.String current_speech_to_text;


    public SpeechData(Context contex, java.lang.String speech) {

        this.setCurrent_speech_to_text(speech);
        Log.d("SpeechData", "Speech Construction: " + speech);

    }


    @Override
    public Message toRosMessage(Publisher<Message> publisher, BaseEntity widget) {
        std_msgs.String message = (std_msgs.String) publisher.newMessage();
        message.setData(getCurrent_speech_to_text());
        return message;
    }

    public String getCurrent_speech_to_text() {
        return current_speech_to_text;
    }

    public void setCurrent_speech_to_text(String current_speech_to_text) {
        this.current_speech_to_text = current_speech_to_text;
    }
}
