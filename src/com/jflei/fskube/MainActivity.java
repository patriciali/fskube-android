
package com.jflei.fskube;

import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final int SAMPLE_RATE = 44100;
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    private Handler mHandler;
    private AudioRecord mAudioRecord;
    private RecordRunnable mRecordRunnable;
    private TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler();

        LayoutInflater inflater =
                (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.activity_main, null);
        setContentView(vg);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTextView = (TextView) findViewById(R.id.textview);
        mTextView.setText("hello");

        mHandler.postDelayed(new PublishRunnable(), 1000);

        int minBufSize = 8 * 1024;
        // AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG,
        // AUDIO_FORMAT);
        Log.d("patricia", "" + minBufSize);
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE,
                CHANNEL_CONFIG, AUDIO_FORMAT, minBufSize);

        mRecordRunnable = new RecordRunnable(mAudioRecord, minBufSize);
        new Thread(mRecordRunnable).start();

        Log.d("patricia", "" + new FSKubeWrapper().sayHello(5));
    }

    private class RecordRunnable implements Runnable {

        private AudioRecord mRecord;
        private short[] mBuffer;

        public volatile double mTimeDisplay;

        public RecordRunnable(AudioRecord record, int bufSize) {
            mRecord = record;
            mBuffer = new short[bufSize];
        }

        @Override
        public void run() {
            int max = 0;
            int min = 0;

            mRecord.startRecording();
            while (true) {
                int bytesRead = 0;
                while (bytesRead < mBuffer.length) {
                    bytesRead += mRecord.read(mBuffer, bytesRead, mBuffer.length - bytesRead);
                }
                int sum = 0;
                for (int i = 0; i < mBuffer.length; i++) {
                    max = Math.max(max, mBuffer[i]);
                    min = Math.min(min, mBuffer[i]);
                    sum += mBuffer[i];
                }
                // Log.d("patricia", "max: " + max + ", min: " + min);
                mTimeDisplay = (sum + 0.0) / mBuffer.length;
            }
        }

    }

    private class PublishRunnable implements Runnable {

        @Override
        public void run() {
            if (mTextView != null) {
                mTextView.setText("" + mRecordRunnable.mTimeDisplay);
            }
            mHandler.postDelayed(this, 1000);
        }

    }
}
