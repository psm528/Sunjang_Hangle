package com.example.softheaven.sunjang_hangle;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;

/**
 * Created by softheaven on 2016-06-28.
 */
public class StudyBoard extends Activity {
    private int[] vocaImg;
    private String[] vocaEng;
    private String[] vocaKor;
    private String[] vocaPron;
    private String[][] exampleEng;
    private String[][] exampleKor;
    private String[][] examplePron;

    private ImageView vocaImgIV;
    private TextView vocaEngTV;
    private TextView vocaKorTV;
    private TextView vocaPronTV;
    private ImageView exampleKorIV;
    private ImageView exampleEngIV;
    private TextView exampleEngTV;
    private TextView exampleKorTV;
    private TextView examplePronTV;
    private TextView pageTV;

    private int pageNum = 0;
    private int pageMax = 9;
    private StringBuffer page;

    final static String PACKAGE_NAME = "com.example.softheaven.sunjang_hangle";
    final static String DB_NAME = "vocaDB.db";

    private float startX;
    private float lastX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studyvocaboard);

        init();
        playVocaSound();
    }

    public void onSpeakerClick(View view) {
        playVocaSound();
    }

    public void onFlagClick(View view) {
        switch (view.getId()) {
            case R.id.vocaleft:
                if (pageNum > 0) {
                    if (pageNum < 3) pageNum = 0;
                    else pageNum = ((pageNum - 3) / 3) * 3;
                    setVoca();
                    setPageTV();
                    playVocaSound();

                    vocaImgIV.setImageResource(vocaImg[pageNum / 3]);
                    vocaEngTV.setText(vocaEng[pageNum / 3]);
                    vocaKorTV.setText(vocaKor[pageNum / 3]);
                    vocaPronTV.setText(vocaPron[pageNum / 3]);
                }
                break;
            case R.id.vocaright:
                if (pageNum < (pageMax - 3)) {
                    pageNum = ((pageNum + 3) / 3) * 3;
                    setVoca();
                    setPageTV();
                    playVocaSound();

                    vocaImgIV.setImageResource(vocaImg[pageNum / 3]);
                    vocaEngTV.setText(vocaEng[pageNum / 3]);
                    vocaKorTV.setText(vocaKor[pageNum / 3]);
                    vocaPronTV.setText(vocaPron[pageNum / 3]);
                }
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                lastX = event.getX();
                if ((lastX - startX) < -200) {
                    if (!((pageMax - pageNum) <= 1)) {
                        pageNum++;
                        Log.v("page Number", ((Integer) pageNum).toString());
                        switch (pageNum % 3) {
                            case 0:
                                setVoca();
                                setPageTV();
                                playVocaSound();

                                vocaImgIV.setImageResource(vocaImg[pageNum / 3]);
                                vocaEngTV.setText(vocaEng[pageNum / 3]);
                                vocaKorTV.setText(vocaKor[pageNum / 3]);
                                vocaPronTV.setText(vocaPron[pageNum / 3]);
                                break;
                            case 1:
                                setExample();

                                exampleEngTV.setText(exampleEng[0][pageNum / 3]);
                                exampleKorTV.setText(exampleKor[0][pageNum / 3]);
                                examplePronTV.setText(examplePron[0][pageNum / 3]);
                                break;
                            case 2:
                                exampleEngTV.setText(exampleEng[1][pageNum / 3]);
                                exampleKorTV.setText(exampleKor[1][pageNum / 3]);
                                examplePronTV.setText(examplePron[1][pageNum / 3]);
                                break;
                        }

                    }
                } else if ((lastX - startX) > 200) {
                    if (pageNum != 0) {
                        pageNum--;
                        Log.v("page Number", ((Integer) pageNum).toString());
                        switch (pageNum % 3) {
                            case 0:
                                setVoca();

                                vocaImgIV.setImageResource(vocaImg[pageNum / 3]);
                                vocaEngTV.setText(vocaEng[pageNum / 3]);
                                vocaKorTV.setText(vocaKor[pageNum / 3]);
                                vocaPronTV.setText(vocaPron[pageNum / 3]);
                                break;
                            case 1:
                                Log.v("index", ((Integer) (pageNum / 3)).toString());
                                exampleEngTV.setText(exampleEng[0][pageNum / 3]);
                                exampleKorTV.setText(exampleKor[0][pageNum / 3]);
                                examplePronTV.setText(examplePron[0][pageNum / 3]);
                                break;
                            case 2:
                                Log.v("index", ((Integer) (pageNum / 3)).toString());
                                setExample();
                                setPageTV();
                                playVocaSound();

                                exampleEngTV.setText(exampleEng[1][pageNum / 3]);
                                exampleKorTV.setText(exampleKor[1][pageNum / 3]);
                                examplePronTV.setText(examplePron[1][pageNum / 3]);
                                break;
                        }
                    }
                }
                break;
        }
        return true;
    }


    private void setVoca() {
        vocaImgIV.setVisibility(View.VISIBLE);
        vocaEngTV.setVisibility(View.VISIBLE);
        vocaKorTV.setVisibility(View.VISIBLE);
        vocaPronTV.setVisibility(View.VISIBLE);

        exampleEngIV.setVisibility(View.INVISIBLE);
        exampleKorIV.setVisibility(View.INVISIBLE);
        exampleEngTV.setVisibility(View.INVISIBLE);
        exampleKorTV.setVisibility(View.INVISIBLE);
        examplePronTV.setVisibility(View.INVISIBLE);
    }

    private void setExample() {
        vocaImgIV.setVisibility(View.INVISIBLE);
        vocaEngTV.setVisibility(View.INVISIBLE);
        vocaKorTV.setVisibility(View.INVISIBLE);
        vocaPronTV.setVisibility(View.INVISIBLE);

        exampleEngIV.setVisibility(View.VISIBLE);
        exampleKorIV.setVisibility(View.VISIBLE);
        exampleEngTV.setVisibility(View.VISIBLE);
        exampleKorTV.setVisibility(View.VISIBLE);
        examplePronTV.setVisibility(View.VISIBLE);
    }

    private void setPageTV() {
        page.delete(0, page.capacity());
        page.append(pageNum / 3 + 1);
        page.append("/");
        page.append(pageMax / 3);

        pageTV.setText(page);
    }

    private void playVocaSound() {
        SoundManager.getInstance().playStudySound(pageNum / 3);
    }

    private void init() {

        vocaImgIV = (ImageView) findViewById(R.id.vocaimg);
        vocaEngTV = (TextView) findViewById(R.id.vocaEng);
        vocaKorTV = (TextView) findViewById(R.id.vocaHan);
        vocaPronTV = (TextView) findViewById(R.id.vocapron);
        exampleKorIV = (ImageView) findViewById(R.id.examplekorimg);
        exampleEngIV = (ImageView) findViewById(R.id.exampleengimg);
        exampleEngTV = (TextView) findViewById(R.id.exampleengtext);
        exampleKorTV = (TextView) findViewById(R.id.examplekortext);
        examplePronTV = (TextView) findViewById(R.id.exampleprontext);
        pageTV = (TextView) findViewById(R.id.studypagenum);

        exampleEngIV.setVisibility(View.INVISIBLE);
        exampleKorIV.setVisibility(View.INVISIBLE);
        exampleEngTV.setVisibility(View.INVISIBLE);
        exampleKorTV.setVisibility(View.INVISIBLE);
        examplePronTV.setVisibility(View.INVISIBLE);

        page = new StringBuffer();

        SoundManager.getInstance().loadStudySound(this, StudyResource.NOUN_SOUND);
        vocaImg = StudyResource.NOUN_IMG;
        getDataToDB();
    }

    //디비에서 정보를 받아옴
    private void getDataToDB() {
        isCheckDB();

        VocaDBHelper openHelper = new VocaDBHelper(this, DB_NAME, null, 3);
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Test_Table", null);
        cursor.moveToFirst();

        int max = cursor.getCount();

        pageMax = max * 3;
        vocaEng = new String[max];
        vocaKor = new String[max];
        vocaPron = new String[max];
        exampleEng = new String[2][max];
        exampleKor = new String[2][max];
        examplePron = new String[2][max];

        setPageTV();

        int i = 0;
        do {

            if (cursor != null && cursor.getCount() != 0) {
                vocaKor[i] = cursor.getString(1);
                vocaPron[i] = cursor.getString(2);
                vocaEng[i] = cursor.getString(3);
                exampleKor[0][i] = cursor.getString(4);
                exampleKor[1][i] = cursor.getString(5);
                exampleEng[0][i] = cursor.getString(6);
                exampleEng[1][i] = cursor.getString(7);
                examplePron[0][i] = cursor.getString(8);
                examplePron[1][i] = cursor.getString(9);
                i++;
            }
        } while (cursor.moveToNext());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (SoundManager.getInstance().isChanged(SoundManager.MAIN_SONG))
            SoundManager.getInstance().changeBGM(this, SoundManager.MAIN_SONG);

        SoundManager.getInstance().playBg();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SoundManager.getInstance().stopBg();
    }


    //about db
    public void isCheckDB() {
        AssetManager manager = this.getAssets();
        String folderPath = "/data/data/" + PACKAGE_NAME + "/databases/";
        String filePath = "/data/data/" + PACKAGE_NAME + "/databases/" + DB_NAME;
        File folder = new File(folderPath);
        File file = new File(filePath);

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        if (!file.exists()) {
            try {
                InputStream is = manager.open("db/" + DB_NAME);
                BufferedInputStream bis = new BufferedInputStream(is);

                if (!folder.exists()) folder.mkdirs();
                if (file.exists()) {
                    file.delete();
                    file.createNewFile();
                }

                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                int read = -1;
                byte[] buffer = new byte[1024];
                while ((read = bis.read(buffer, 0, 1024)) != -1) {
                    bos.write(buffer, 0, read);
                }

                bos.flush();
                bos.close();
                fos.close();
                is.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class VocaDBHelper extends SQLiteOpenHelper {
        public VocaDBHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
