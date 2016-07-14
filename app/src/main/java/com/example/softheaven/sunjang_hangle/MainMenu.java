package com.example.softheaven.sunjang_hangle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

/**
 * Created by softheaven on 2016-06-28.
 */
public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        waveAnimation();
    }

    public void onStudyBtnClick(View view) {
        SoundManager.getInstance().setBGMStoppable(true);
        startActivity(new Intent(this.getApplicationContext(), StudyMenu.class));
    }

    private void waveAnimation() {
        final HorizontalScrollView[] wave = new HorizontalScrollView[5];
        final ImageView[] waveimg = new ImageView[5];
        Animation wave_right = AnimationUtils.loadAnimation(this, R.anim.wave_right);
        Animation wave_left = AnimationUtils.loadAnimation(this, R.anim.wave_left);
        int[] waveName = {R.id.waveScroll1, R.id.waveScroll2, R.id.waveScroll3, R.id.waveScroll4, R.id.waveScroll5};
        int[] waveImgName = {R.id.mainwave1, R.id.mainwave2, R.id.mainwave3, R.id.mainwave4, R.id.mainwave5};
        int i;

        for (i = 0; i < 5; i++) {
            wave[i] = (HorizontalScrollView) findViewById(waveName[i]);
            waveimg[i] = (ImageView) findViewById(waveImgName[i]);

            wave[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_MOVE:
                            event.setAction(MotionEvent.ACTION_CANCEL);
                    }
                    return false;
                }
            });

            if (i % 2 == 0) waveimg[i].startAnimation(wave_left);
            else waveimg[i].startAnimation(wave_right);
        }
    }

    @Override
    public void onBackPressed(){
        SoundManager.getInstance().setBGMStoppable(true);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SoundManager.getInstance().setBGMStoppable(false);
        SoundManager.getInstance().playBg();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (SoundManager.getInstance().getBGMStoppable() != true) SoundManager.getInstance().stopBg();
    }
}
