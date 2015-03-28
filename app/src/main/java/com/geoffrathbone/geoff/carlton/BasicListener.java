package com.geoffrathbone.geoff.carlton;

import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;

import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;

/**
 * Class that listens for the keywords
 * Created by Geoff on 3/27/2015.
 */
public class BasicListener implements RecognitionListener {

    public BasicListener(MainActivity activity) {
        this.mainActivity = activity;
        this.kickoff();
        this.mainActivity.notifyListenerSetupResult(MainActivity.ListenerStatusEnum.LOADING);
    }

    public void listen() {
        recognizer.startListening(optionsSearch);
        this.mainActivity.notifyListenerSetupResult(MainActivity.ListenerStatusEnum.LISTENING);
    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null)
            return;

        String text = hypothesis.getHypstr();
        switch (text) {
            case textKeyword:
                this.stopListening();
                Commander.launchSMS(mainActivity);
                break;
            case callKeyword:
                this.stopListening();
                Commander.launchDialer(mainActivity);
                break;
            case weatherKeyword:
                this.stopListening();
                Commander.launchWeatherActivity(mainActivity);
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(Exception ex) {
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
    }

    @Override
    public void onTimeout() {
    }

    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onEndOfSpeech() {
    }

    public void stopListening() {
        recognizer.cancel();
        this.mainActivity.notifyListenerSetupResult(MainActivity.ListenerStatusEnum.LOADED);
    }

    private void kickoff() {
        // code borrowed from pocketsphinx example project.
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(mainActivity);
                    File assetDir = assets.syncAssets();
                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                    mainActivity.notifyListenerSetupResult(MainActivity.ListenerStatusEnum.FAILED);
                    return;
                }
                // enable button
                mainActivity.notifyListenerSetupResult(MainActivity.ListenerStatusEnum.LOADED);
            }
        }.execute();
    }

    // borrowed and modified from example project
    private void setupRecognizer(File assetsDir) throws IOException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them

        recognizer = defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))

                        // To disable logging of raw audio comment out this call (takes a lot of space on the device)
                .setRawLogDir(assetsDir)

                        // Threshold to tune for keyphrase to balance between false alarms and misses
                .setKeywordThreshold(1e-45f)

                        // Use context-independent phonetic search, context-dependent is too slow for mobile
                .setBoolean("-allphone_ci", true)

                .getRecognizer();
        recognizer.addListener(this);

        // create keyword-activation searches
        File carltonGrammar = new File(assetsDir, "options.gram");
        recognizer.addGrammarSearch(optionsSearch, carltonGrammar);
    }

    final private String textKeyword = "text";
    final private String callKeyword = "call";
    final private String weatherKeyword = "weather";
    final private String optionsSearch = "options";
    final private MainActivity mainActivity ;
    private SpeechRecognizer recognizer ;
}
