package com.example.yash.voicetotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "VTT";
        private Button btnToOpenMic;
    private TextView tvShowVoiceOutput;
    private final int REQ_CODE_SPEECH_OUTPUT=143;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnToOpenMic = (Button) findViewById(R.id.btnToOpenMic);
        tvShowVoiceOutput = (TextView) findViewById(R.id.tvShowVoiceOutput);

        btnToOpenMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                openMic();
            }
        });

    }
    private void openMic()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hi Speak Now");

        try{
            startActivityForResult(intent,REQ_CODE_SPEECH_OUTPUT);
        }
        catch (ActivityNotFoundException anfe)
        {
            Toast.makeText(this, "Activity Not Found.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case REQ_CODE_SPEECH_OUTPUT:
            {
                if (resultCode== RESULT_OK && null!=data)
                {
                    ArrayList<String> voiceInText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tvShowVoiceOutput.setText(voiceInText.get(0));
                }
                break;
            }
        }
    }
}
