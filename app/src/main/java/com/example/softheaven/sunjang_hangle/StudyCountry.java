package com.example.softheaven.sunjang_hangle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by softheaven on 2016-06-28.
 */
public class StudyCountry extends Activity {
    final static int ADJECTIVE_COUNTRY = 0;
    final static int NOUN_COUNTRY = 1;
    final static int VERB_COUNTRY = 2;
    final static int ADVERB_COUNTRY = 3;

    private int coinNumber = 1;
    private int nowCountry = 0;
    private ImageView chaWomanIV;
    private ImageView tutorialIV;
    private Animation wark_to_coin1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studycountry);
        initCountry();

        setWalkAnim();
    }

    public void initCountry() {
        Intent intent = getIntent();
        final ImageView mapNameIV = (ImageView) findViewById(R.id.mapname);
        tutorialIV = (ImageView)  findViewById(R.id.tutostudyvoca);

        nowCountry = intent.getIntExtra("CountryName", 0);
        switch (nowCountry) {
            case ADJECTIVE_COUNTRY:
                mapNameIV.setImageResource(R.drawable.imgstudymapname1);
                break;
            case NOUN_COUNTRY:
                mapNameIV.setImageResource(R.drawable.imgstudymapname2);
                break;
            case VERB_COUNTRY:
                mapNameIV.setImageResource(R.drawable.imgstudymapname3);
                break;
            case ADVERB_COUNTRY:
                mapNameIV.setImageResource(R.drawable.imgstudymapname4);
                break;
        }
    }

    public void onCoinClick(View view) {
        wark_to_coin1 = AnimationUtils.loadAnimation(this, R.anim.wark_to_coin1);
        wark_to_coin1.setFillAfter(true);

        switch (view.getId()) {
            case R.id.studymapcoin1:
                coinNumber = 1;
                break;
            case R.id.studymapcoin2:
                coinNumber = 2;
                break;
            case R.id.studymapcoin3:
                coinNumber = 3;
                break;
            case R.id.studymapcoin4:
                coinNumber = 4;
                break;
        }

        chaWomanIV.startAnimation(wark_to_coin1);
        chaWomanIV.postDelayed(new Runnable() {
            @Override
            public void run() {
                SoundManager.getInstance().playHorn();
                tutorialIV.setVisibility(View.VISIBLE);
            }
        }, 1900);
    }

    public void onTutoVocaClick(View view) {
        Intent intent = new Intent(this, StudyBoard.class);
        intent.putExtra("Country", nowCountry);
        intent.putExtra("Coin", coinNumber);
        startActivity(intent);
    }

    public void setWalkAnim() {
        chaWomanIV = (ImageView) findViewById(R.id.movechawoman);
        AnimationDrawable walk = (AnimationDrawable) chaWomanIV.getDrawable();
        walk.start();
    }

    @Override
    public void onBackPressed(){
        SoundManager.getInstance().setBGMStoppable(true);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SoundManager.getInstance().isChanged(SoundManager.LOGO_SONG))
            SoundManager.getInstance().changeBGM(this, SoundManager.LOGO_SONG);

        tutorialIV.setVisibility(View.GONE);
        SoundManager.getInstance().setBGMStoppable(false);
        SoundManager.getInstance().playBg();

        if (wark_to_coin1 != null)
            wark_to_coin1.setFillAfter(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(SoundManager.getInstance().getBGMStoppable()!=true) SoundManager.getInstance().stopBg();
    }
}
