package rowan.recordtouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        final TextView broke  = (TextView) findViewById(R.id.broken);
        final Button play = (Button) findViewById(R.id.play);
        broke.setVisibility(View.INVISIBLE);


    }


    public void generateClick(View view) {
        final TextView broke  = (TextView) findViewById(R.id.broken);
        final Button play = (Button) findViewById(R.id.play);
        Animation drop = AnimationUtils.loadAnimation(this, R.anim.anim);

        findViewById(R.id.play).setAnimation(drop);


        drop.setAnimationListener(new Animation.AnimationListener() {


                                      public void onAnimationEnd(Animation animation) {

                                          play.setVisibility(View.INVISIBLE);
                                          broke.setVisibility(View.VISIBLE);

                                          setContentView(R.layout.activity_main_menu);
                                          findViewById(R.id.button).performClick();

                                      }

                                      @Override
                                      public void onAnimationStart(Animation animation) {

                                      }

                                      @Override
                                      public void onAnimationRepeat(Animation animation) {


                                      }


                                  }

        );
        view.startAnimation(drop);


    }

    public void generateClick2(View view) {
        view.invalidate();
        view.refreshDrawableState();
        final Button play = (Button) findViewById(R.id.play);
        boolean invisible = play.getVisibility() == View.GONE;
        if (invisible) {
            try{
                Thread.sleep(3000);
            }
            catch (Exception e){

            }
        }

        Intent i = new Intent(getApplicationContext(), RecordTouch.class);
        startActivity(i);
    }
}



