package rowan.recordtouch;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Timer;

import static android.support.v4.view.MotionEventCompat.getPointerCount;

public class RecordTouch extends MainMenu {
    public static String finalResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_record_touch);

        final TextView fromEdgeReport = (TextView) findViewById(R.id.fromEdgeReport);
        final View touchRecord = findViewById(R.id.touchRecord);
        final Button timerButton = (Button) findViewById(R.id.timerClick);
        final TextView timeReport = (TextView) findViewById(R.id.timeReport);
        final TextView finalDistance = (TextView) findViewById(R.id.finalDistanceOutput);
        finalDistance.setVisibility(View.INVISIBLE);


        final CountDownTimer countDown = new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeReport.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {

                timeReport.setText("Check your final distance!");
                finalDistance.setVisibility(View.VISIBLE);
                finalDistance.setText(finalResult);
                touchRecord.setBackgroundColor(0xFF00FF00);

                if(Double.parseDouble(finalResult)>1){
                    timeReport.setText("You suck!");
                }else if(Double.parseDouble(finalResult)>.5){
                    timeReport.setText("Getting there!");
                }
                else if(Double.parseDouble(finalResult)>.05){
                    timeReport.setText("Awesome");
                }
                else{
                    timeReport.setText("You dropped your phone...");
                }

            }

        };


        touchRecord.setOnTouchListener(new View.OnTouchListener() {


            public boolean onTouch(View v, MotionEvent event) {


                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;

                float fromEdgeX = width - event.getX();
                float fromEdgeY = height - event.getY();

                DecimalFormat df = new DecimalFormat("0.000");
                final float minDistance = Math.min(Math.min(fromEdgeX, fromEdgeY), Math.min(event.getX(), event.getY()));
                finalResult = df.format(dpiToCm(pixelToDpi(minDistance, getApplicationContext())));
                if(Double.parseDouble(finalResult)>1){
                    touchRecord.setBackgroundColor(Color.GREEN);
                }
                else if(Double.parseDouble(finalResult)>0.1){
                    touchRecord.setBackgroundColor(Color.YELLOW);
                }
                else{
                    touchRecord.setBackgroundColor(Color.RED);
                }

                int pointerCount = event.getPointerCount();



                fromEdgeReport.setText(String.format("" +
                        finalResult));

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    countDown.cancel();


                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    countDown.start();

                }







                return true;


            }


        });
    }










    public float pixelToDpi(float pixel, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return pixel/density;
    }
    public double dpiToCm(float dpi) {
        return dpi*.016;
    }

}
