package com.example.softheaven.sunjang_hangle;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

/**
 * Created by softheaven on 2016-06-28.
 */
public class SoundManager {

    final static int LOGO_SONG = 1;
    final static int MAIN_SONG = 2;
    private static int NOW_PLAYING;
    private boolean dontStopBgm = false;

    private static SoundManager uniqueInstance;
    private SoundPool soundPool;
    private MediaPlayer mediaPlayer;
    private int hornId;
    private int[] studySoundId = new int[25];

    private SoundManager() {
    }

    public static SoundManager getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SoundManager();
        }
        return uniqueInstance;
    }

    public void load(Context context) {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        hornId = soundPool.load(context, R.raw.shiphorn1, 1);
        mediaPlayer = MediaPlayer.create(context, R.raw.logosong);
        NOW_PLAYING = LOGO_SONG;
        mediaPlayer.setLooping(true);
    }

    public void changeBGM(Context context, int bgmNAME) {
        switch (bgmNAME) {
            case LOGO_SONG:
                mediaPlayer = MediaPlayer.create(context, R.raw.logosong);
                NOW_PLAYING = bgmNAME;
                break;
            case MAIN_SONG:
                mediaPlayer = MediaPlayer.create(context, R.raw.mainsong);
                NOW_PLAYING = bgmNAME;
                break;
        }
    }

    public boolean isChanged(int bgmNAME) {
        if (bgmNAME != NOW_PLAYING) return true;
        else return false;
    }

    public void playBg() {
        mediaPlayer.start();
    }

    public void stopBg() {
        mediaPlayer.pause();
    }

    public void playHorn() {
        soundPool.play(hornId, 1.0F, 1.0F, 1, 0, 1.0F);
    }

    public void loadStudySound(Context contexts, int[] country) {
        for(int i = 0; i<country.length; i++) {
            studySoundId[i]=soundPool.load(contexts, country[i],1);
        }
    }

    public void playStudySound(int index) {
        soundPool.play(studySoundId[index], 1.0F, 1.0F, 1, 0, 1.0F);
    }

    public void release() {
        soundPool.release();
        mediaPlayer.release();
    }

    public void setBGMStoppable(boolean bool){
        dontStopBgm = bool;
    }

    public boolean getBGMStoppable() {
        return dontStopBgm;
    }
}
