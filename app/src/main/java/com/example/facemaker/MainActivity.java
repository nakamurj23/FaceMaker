package com.example.facemaker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;


//@author Jordan Nakamura
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Face view = (Face)findViewById(R.id.faceView);

        Spinner spinner = findViewById(R.id.selectHairStyle);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.features, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(view);

        SeekBar red = findViewById(R.id.seekBarRed);
        SeekBar green = findViewById(R.id.seekBarGreen);
        SeekBar blue = findViewById(R.id.seekBarBlue);
        red.setOnSeekBarChangeListener(view);
        green.setOnSeekBarChangeListener(view);
        blue.setOnSeekBarChangeListener(view);

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.selectFeatureGroup);
        radioGroup.setOnCheckedChangeListener(view);

        Button random = findViewById(R.id.buttonRandomFace);
        random.setOnClickListener(view);

        view.setSeekBar(red, green, blue);
    }

}