package com.schneewittchen.rosandroid.widgets.speech;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.List;

public class SpeechRecognitionService extends Service {
    private SpeechRecognizer speechRecognizer;
    public java.lang.String actual_speech;

    @Override
    public void onCreate() {
        super.onCreate();

        // Create a new SpeechRecognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        // Set up a listener to receive results
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                // The user is now ready to speak
                Log.d("SpeechRecognition", "ready to speech");
            }

            @Override
            public void onBeginningOfSpeech() {
                // The user has started speaking
                Log.d("SpeechRecognition", "start of speech");
            }

            @Override
            public void onRmsChanged(float rmsdB) {
                // The volume of the user's voice has changed
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                // A buffer of audio data has been received
            }

            @Override
            public void onEndOfSpeech() {
                // The user has finished speaking
                Log.d("SpeechRecognition", "end of speech");
            }

            @Override
            public void onError(int error) {
                // An error has occurred
                Log.d("SpeechRecognition", "error " + Integer.toString(error));
            }

            @Override
            public void onResults(Bundle results) {
                // Results are available
                List<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String transcript = matches.get(0);
                Log.d("SpeechRecognition", transcript);
                actual_speech = transcript;

                // Send a broadcast intent with the results
                Intent intent = new Intent("com.example.speechrecognitionapp.SPEECH_RECOGNITION_RESULTS");
                intent.putExtra("transcript", actual_speech);
                sendBroadcast(intent);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                // Partial results are available
                List<String> matches = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String transcript = matches.get(0);
                Log.d("Partial SpeechRecognition", transcript);
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                // An event has occurred
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("SpeechRecognition", "Starting Speech Recognition");

        // Create an intent to start the speech recognition service
        Intent recognitionIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognitionIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognitionIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now");

        // Start the speech recognition service
        speechRecognizer.startListening(recognitionIntent);

        // Return the service's start command
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // This service does not support binding
        return null;
    }
}