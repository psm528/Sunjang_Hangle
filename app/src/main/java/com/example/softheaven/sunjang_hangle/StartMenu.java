package com.example.softheaven.sunjang_hangle;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;

public class StartMenu extends Activity {
    private long backKeyPressedTime = 0;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waveAnimation();
        startMusic();
    }

    public void onStartBtnClick(View view) {
        SoundManager.getInstance().setBGMStoppable(true);
        startActivity(new Intent(this.getApplicationContext(), MainMenu.class));
    }

    private void startMusic() {
        SoundManager.getInstance().load(this.getApplicationContext());
        SoundManager.getInstance().playHorn();
        SoundManager.getInstance().playBg();
    }

    private void waveAnimation() {
        final HorizontalScrollView[] wave = new HorizontalScrollView[5];
        final ImageView[] waveimg = new ImageView[5];
        Animation wave_right = AnimationUtils.loadAnimation(this, R.anim.wave_right);
        Animation wave_left = AnimationUtils.loadAnimation(this, R.anim.wave_left);
        int[] waveName = {R.id.waveScroll1, R.id.waveScroll2, R.id.waveScroll3, R.id.waveScroll4, R.id.waveScroll5};
        int[] waveImgName = {R.id.startwave1, R.id.startwave2, R.id.startwave3, R.id.startwave4, R.id.startwave5};
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
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(StartMenu.this, "'뒤로'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
           finish();
            toast.cancel();
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SoundManager.getInstance().release();
    }
}
