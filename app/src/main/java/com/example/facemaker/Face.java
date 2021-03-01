package com.example.facemaker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

//@author Jordan Nakamura
public class Face extends SurfaceView implements AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    // Instance Variables
    private int skinColor;
    private int eyeColor;
    private int hairColor;
    private int hairStyle;
    private int r;
    private int g;
    private int b;
    private int changeFeature;
    private SeekBar red;
    private SeekBar green;
    private SeekBar blue;

    private float canvasHeight = 900.0f;
    private float canvasWidth = 1667.0f;

    Paint color = new Paint();
    Paint face = new Paint();
    Paint blackStroke = new Paint();
    Paint white = new Paint();
    Paint hair = new Paint();
    Paint eyes = new Paint();
    Paint blackFill = new Paint();

    public Face(Context context, AttributeSet attrs) {
        super(context, attrs);

        setWillNotDraw(false);

        setBackgroundColor(Color.WHITE);

        randomize();

        // Initial Palette
        blackStroke.setColor(Color.BLACK);
        blackStroke.setStyle(Paint.Style.STROKE);
        blackFill.setColor(Color.BLACK);
        white.setColor(Color.WHITE);
        face.setColor(skinColor);
        hair.setColor(hairColor);
        eyes.setColor(eyeColor);
    }

    /** Initializes all instance variables to random values */
    public void randomize(){
        skinColor = Color.rgb((int)((Math.random()*255)), (int)((Math.random()*255)),(int)((Math.random()*255)));
        eyeColor = Color.rgb((int)((Math.random()*255)), (int)((Math.random()*255)),(int)((Math.random()*255)));
        hairColor = Color.rgb((int)((Math.random()*255)), (int)((Math.random()*255)),(int)((Math.random()*255)));

        hairStyle = (int)((Math.random()*3) + 1);

        face.setColor(skinColor);
        hair.setColor(hairColor);
        eyes.setColor(eyeColor);
    }


    /** Draws face */
    public void onDraw(Canvas canvas){

        // Head
        canvas.drawOval((canvasWidth/2) - 280,(canvasHeight/2) - 375,(canvasWidth/2) + 280, (canvasHeight/2) + 375, face);
        // Nose
        canvas.drawArc((canvasWidth/2) - 45,(canvasHeight/2) - 45 ,(canvasWidth/2) + 45, (canvasHeight/2) + 45, 90, 180, false, blackStroke);
        // Mouth
        canvas.drawArc((canvasWidth/2) - 100,(canvasHeight/2) + 200 ,(canvasWidth/2) + 100, (canvasHeight/2) + 250, 180, -180, false, blackStroke);

        drawHair(canvas);

        drawEyes(canvas);
    }

    /** Draws eyes. */
    public void drawEyes(Canvas canvas){
        // Left eye
        canvas.drawCircle((canvasWidth/2) - 100, (canvasHeight/2) - 140, 52, blackStroke);
        canvas.drawCircle((canvasWidth/2) - 100, (canvasHeight/2) - 140, 50, white);
        canvas.drawCircle((canvasWidth/2) - 100, (canvasHeight/2) - 140, 35, eyes);
        canvas.drawCircle((canvasWidth/2) - 100, (canvasHeight/2) - 140, 20, blackFill);

        // Right eye
        canvas.drawCircle((canvasWidth/2) + 100, (canvasHeight/2) - 140, 52, blackStroke);
        canvas.drawCircle((canvasWidth/2) + 100, (canvasHeight/2) - 140, 50, white);
        canvas.drawCircle((canvasWidth/2) + 100, (canvasHeight/2) - 140, 35, eyes);
        canvas.drawCircle((canvasWidth/2) + 100, (canvasHeight/2) - 140, 20, blackFill);
    }

    /** Draws hair based on hairStyle value */
    public void drawHair(Canvas canvas){
        if(hairStyle != 3) {
            if(hairStyle == 1) {
                // Short Hair
                canvas.drawArc((canvasWidth / 2) - 280, (canvasHeight / 2) - 385, (canvasWidth / 2) + 280, (canvasHeight / 2) - 80, -180, 180, true, hair);
            } else{
                // Long Hair
                canvas.drawArc((canvasWidth / 2) - 280, (canvasHeight / 2) - 385, (canvasWidth / 2) + 280, (canvasHeight / 2) - 80, -180, 180, true, hair);
                canvas.drawArc((canvasWidth / 2) - 320, (canvasHeight / 2) - 335, (canvasWidth / 2) - 60, (canvasHeight / 2) + 400, 90, 180, true, hair);
                canvas.drawArc((canvasWidth / 2) + 60, (canvasHeight / 2) - 335, (canvasWidth / 2) + 320, (canvasHeight / 2) + 400, 90, -180, true, hair);
            }
        }
    }

    // Spinner methods
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long rowId) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

        switch((int)rowId){ // Sets hairStyle value based on which row of spinner is selected.
            case 1:
                hairStyle = 1;
                break;
            case 2:
                hairStyle = 2;
                break;

            case 3:
                hairStyle = 3;
                break;
            default:
                break;
        }
        invalidate();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // Seek bar methods
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean user) {

        switch (seekBar.getId()){ // Sets rgb values according to progress and seekBar id.
            case R.id.seekBarRed:
                r = progress;
                break;

            case R.id.seekBarGreen:
                g = progress;
                break;

            case R.id.seekBarBlue:
                b = progress;
                break;
            default:
                break;
        }

        color.setColor(Color.rgb(r,g,b));

        if(changeFeature == 1){
            hairColor = Color.rgb(r,g,b);
            hair.setColor(hairColor);
        }else if(changeFeature == 2){
            eyeColor = color.getColor();
            eyes.setColor(eyeColor);
        }else if(changeFeature == 3){
            skinColor = color.getColor();
            face.setColor(skinColor);
        }

        invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    // Radio Button Methods
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {

        switch (id){ // Decides which feature to change based on id.
            case R.id.radioButtonHair:
                changeFeature = 1;
                setRGB(hairColor);
                break;

            case R.id.radioButtonEyes:
                changeFeature = 2;
                setRGB(eyeColor);
                break;

            case R.id.radioButtonSkin:
                changeFeature = 3;
                setRGB(skinColor);
                break;
            default:
                changeFeature = 0;
                break;

        }
        red.setProgress(r);
        green.setProgress(g);
        blue.setProgress(b);
        invalidate();
    }

    /** sets SeekBar progress according to instance variable seekBar values. Used to pass values to MainActivity */
    public void setSeekBar(SeekBar red, SeekBar green, SeekBar blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /** Randomizes rgb values for hair, skin, and eyes. Also updates seekBar values according to feature selected by radio buttons. */
    @Override
    public void onClick(View view) {
        randomize();
        if (changeFeature != 0) {
            if (changeFeature == 1) {
                setRGB(hairColor);
            } else if (changeFeature == 2) {
                setRGB(eyeColor);
            } else {
                setRGB(skinColor);
            }
        }
        red.setProgress(r);
        green.setProgress(g);
        blue.setProgress(b);
        invalidate();
    }

    /** Takes in an int value and sets individual r g b values */
    public void setRGB(int color){
        r = (color >> 16) & 0xFF;
        g = (color >> 8) & 0xFF;
        b = color & 0xFF;
    }
}
