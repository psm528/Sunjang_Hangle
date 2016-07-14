package com.example.softheaven.sunjang_hangle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by softheaven on 2016-06-28.
 */
public class StudyMenu extends Activity {
    final static int ADJECTIVE_COUNTRY = 0;
    final static int NOUN_COUNTRY = 1;
    final static int VERB_COUNTRY = 2;
    final static int ADVERB_COUNTRY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studymenu);
    }

    public void onCountryClick(View view) {
        SoundManager.getInstance().setBGMStoppable(true);
        Intent intent = new Intent(this, StudyCountry.class);
        switch (view.getId()) {
            case R.id.studymenuadjective:
                intent.putExtra("CountryName", ADJECTIVE_COUNTRY);
                break;
            case R.id.studymenunoum:
                intent.putExtra("CountryName", NOUN_COUNTRY);
                break;
            case R.id.studymenuverb:
                intent.putExtra("CountryName", VERB_COUNTRY);
                break;
            case R.id.studymenuadverb:
                intent.putExtra("CountryName", ADVERB_COUNTRY);
                break;
            default:
                intent.putExtra("CountryName", ADJECTIVE_COUNTRY);
                break;
        }
        startActivity(intent);
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
