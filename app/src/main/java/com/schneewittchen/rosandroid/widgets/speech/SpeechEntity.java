package com.schneewittchen.rosandroid.widgets.speech;

import com.schneewittchen.rosandroid.model.entities.widgets.PublisherWidgetEntity;
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic;

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
public class SpeechEntity extends PublisherWidgetEntity {

    public java.lang.String text;
    public int rotation;
    public String speech;

    public SpeechEntity() {
        this.width = 5;
        this.height = 3;
        this.topic = new Topic("verbal", String._TYPE);
        this.immediatePublish = true;
        this.text = "Speech";
        this.rotation = 0;

    }

}
